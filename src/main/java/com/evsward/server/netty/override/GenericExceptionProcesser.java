package com.evsward.server.netty.override;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.handler.timeout.ReadTimeoutException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.evsward.server.thread.PushMsg2TerminalService;
import com.evsward.server.util.Application;
import com.netty.server.core.ExceptionProcesser;
import com.netty.server.core.MessageConverter;
import com.netty.server.core.MessageHandler;
import com.netty.server.core.ProcessController;
import com.netty.server.core.ServerContext;
import com.netty.server.core.exception.AlertException;
import com.netty.server.core.exception.ValidateException;
import com.netty.server.tcp.TcpServerContext;

/**
 * 请求统一处理 接收RequestMessage并进行业务处理，处理后返回ResponseMessage
 * 
 * R	表示 requestMessage
 * A	表示 responseMessage
 * BR	表示 bizRequest
 * BA	表示 bizResponse
 * 
 */
public abstract class GenericExceptionProcesser<R, A, BR, BA> implements ExceptionProcesser<A> {

	protected static Logger logger = LoggerFactory.getLogger(GenericExceptionProcesser.class);
	
	private static final String EXCEPTION_READTIMEOUT 			= "IDLE|TOT|%s";
	private static final String EXCEPTION_HANDLER_READTIMEOUT 	= "IDLE|HOT|%s";
	private static final String EXCEPTION_OTHER 				= "IDLE|EXC|%s";
	private static final String EXCEPTION_HANDLER 				= "%s|EXC|%s|%s";
	
	private boolean bizReadTimeoutReturnNone = false;
	
	private ProcessController<R, A, BR, BA> processController;

	@Override
	public A process(ChannelHandlerContext ctx, ExceptionEvent event) throws Exception {

		Throwable throwable = event.getCause();

		ServerContext serverContext = (ServerContext) ctx.getAttachment();

		String simpleError = parseSimpleError(throwable);
		PushMsg2TerminalService pushMsgService = null;
		if (serverContext == null) {
			if (throwable instanceof ReadTimeoutException) {
				logger.info(String.format(EXCEPTION_READTIMEOUT, event.getChannel().getId()));
			} else {
				logger.error(String.format(EXCEPTION_OTHER, simpleError), throwable);
			}
			Channel c = event.getChannel();
			if(c == null){
				return null;
			}
			Integer channelId = c.getId();
			if(channelId == null){
				return null;
			}
			Collection<PushMsg2TerminalService> collection = Application.pushMsg2TermServiceMap.values();
			Iterator<PushMsg2TerminalService> it = collection.iterator();
			
			while(it.hasNext()){
				pushMsgService = it.next();
				if(pushMsgService != null){
					pushMsgService.removeChannel(channelId);
				}
			}
			// 稍后会关闭channel
			return null;
		}else{
			if (throwable instanceof ReadTimeoutException) {
				if(bizReadTimeoutReturnNone){
					logger.info(String.format(EXCEPTION_HANDLER_READTIMEOUT, event.getChannel().getId()));
					return null;
				}
			}
		}
		
		serverContext.setOccurException(true);
		
		// 执行业务逻辑过程中，需要返回 DefaultErrorResponse（释放连接），不需要关闭channel
		return handlerExceptionProcess(ctx, event, serverContext, throwable);
	}
	
	public abstract BA alertExceptionProcess(ChannelHandlerContext ctx, ExceptionEvent event, ServerContext serverContext, AlertException throwable) throws IOException ;
	
	public abstract BA validateExceptionProcess(ChannelHandlerContext ctx, ExceptionEvent event, ServerContext serverContext, ValidateException throwable) throws IOException ;
		
	public A handlerExceptionProcess(ChannelHandlerContext ctx, ExceptionEvent event, ServerContext serverContext, Throwable throwable) throws IOException {
		
		BA ackMessage = null; 
				
		if (throwable instanceof AlertException) {
			// 系统级别处理异常，一般为配置问题或消息号错误，需要关闭channel
			serverContext.setCloseFuture(true);
			ackMessage = alertExceptionProcess(ctx, event, serverContext, (AlertException)throwable);
		} else if (throwable instanceof ValidateException) {
			// 业务级别校验异常，一般为非法请求，例如：请求Ip白名单校验失败，需要关闭channel
			serverContext.setCloseFuture(true);
			ackMessage = validateExceptionProcess(ctx, event, serverContext, (ValidateException)throwable);
		}else{
			String simpleError = parseSimpleError(throwable);
			
			if (throwable instanceof ReadTimeoutException){
				serverContext.setCloseFuture(true);
				// TCP|EXC|1301035978658108|
				logger.error(String.format(EXCEPTION_HANDLER, serverContext.getProtocol(), serverContext.getFlowId(), simpleError));
			}else{
				// TCP|EXC|1301035978658108|
				logger.error(String.format(EXCEPTION_HANDLER, serverContext.getProtocol(), serverContext.getFlowId(), simpleError), throwable);
			}
			
			// 1)获得 handler
			MessageHandler<BR, BA> messageHandler = processController.getMessageHandler(serverContext);

			// 2)获取默认响应
			ackMessage = messageHandler.defaultFailedResponse(serverContext, throwable, simpleError);	
			
		}

		MessageConverter<R, A, BR, BA> converter = processController.getMessageConverter(serverContext);
		removeHiCache(ctx, event, serverContext);
		return converter.toResponse(serverContext, ackMessage);		
	}
	
	private void removeHiCache(ChannelHandlerContext ctx, ExceptionEvent event, ServerContext serverContext){
		Channel c = event.getChannel();
		PushMsg2TerminalService pushMsgService = null;
		if(c != null && serverContext instanceof TcpServerContext){
			TcpServerContext tcpServerContext = (TcpServerContext)serverContext;
			int commandId = tcpServerContext.getCommandId();
			pushMsgService = Application.pushMsg2TermServiceMap.get(commandId);
			if(pushMsgService != null){
				pushMsgService.removeChannel(c.getId());
			}
		}
	}
	
	public String parseSimpleError(Throwable throwable){
		
		if(throwable.getMessage() == null){
			return throwable.getClass().getSimpleName();
		}
		
		return throwable.getClass().getSimpleName()+":"+throwable.getMessage();
	}
	
	public void setProcessController(ProcessController<R, A, BR, BA> processController) {
		this.processController = processController;
	}

	public boolean isBizReadTimeoutReturnNone() {
		return bizReadTimeoutReturnNone;
	}

	public void setBizReadTimeoutReturnNone(boolean bizReadTimeoutReturnNone) {
		this.bizReadTimeoutReturnNone = bizReadTimeoutReturnNone;
	}

}