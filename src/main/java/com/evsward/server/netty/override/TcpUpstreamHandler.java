package com.evsward.server.netty.override;

import org.apache.commons.lang.StringUtils;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dance.core.utils.web.SpringContextUtils;
import com.evsward.server.facade.ScreenManageFacade;
import com.evsward.server.netty.cache.HIChannelCache;
import com.evsward.server.thread.PushMsg2TerminalService;
import com.evsward.server.util.Application;
import com.netty.server.core.support.GenericUpstreamHandler;
import com.netty.server.tcp.TcpServerContext;
import com.netty.server.tcp.message.TcpOriginalRequestMessage;
import com.netty.server.tcp.message.TcpOriginalResponseMessage;

/**
 * TCP上行消息统一处理入口
 * 
 */

public class TcpUpstreamHandler extends GenericUpstreamHandler<TcpOriginalRequestMessage, TcpOriginalResponseMessage> {

	private static Logger logger = LoggerFactory.getLogger(TcpUpstreamHandler.class);
	
	@Override
	public void channelClosed(ChannelHandlerContext ctx, ChannelStateEvent e)
			throws Exception {
		super.channelClosed(ctx, e);
		Channel ch = e.getChannel();
		if(ch != null){
			TcpServerContext tcpServerContext = (TcpServerContext)ctx.getAttachment();
			if(tcpServerContext != null){
				Channel c = ctx.getChannel();
				if(c == null){
					return;
				}
				int commandId = tcpServerContext.getCommandId();
				logger.info("关闭Channel消息号---------------commandId="+commandId);
				PushMsg2TerminalService pushMsgService = Application.pushMsg2TermServiceMap.get(commandId);
				if(pushMsgService != null){
					HIChannelCache cache = pushMsgService.removeChannel(ch.getId());
//					logger.info("关闭Channel消息号1---------------commandId="+commandId);
					try{
						if(cache != null){
//							logger.info("关闭Channel消息号2---------------commandId="+commandId);
							if(commandId == Application.MSGTYPE.SCREENSHOW){//大屏幕基础连接,断线后，更新设备的状态为断线状态。
//								logger.info("关闭Channel消息号3---------------commandId="+commandId);
								if(StringUtils.isNotEmpty(cache.getImei())){
//									logger.info("关闭Channel消息号4---------------commandId="+commandId);
									ScreenManageFacade screenManFacade = SpringContextUtils.getBean("screenFacade");
									if(screenManFacade != null){
//										logger.info("关闭Channel消息号5---------------commandId="+commandId);
										screenManFacade.updateScreenOffline(cache.getImei());
									}
								}
							}
							
						}
					}catch(Exception e1){
						logger.error(e1.getMessage(), e1);
					}
				}
			}
		}
	}
}
