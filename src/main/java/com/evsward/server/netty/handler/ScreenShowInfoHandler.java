package com.evsward.server.netty.handler;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.dance.core.utils.encode.JsonBinder;
import com.evsward.server.facade.ScreenManageFacade;
import com.evsward.server.netty.cache.ScreenShowInfoCache;
import com.evsward.server.protobuf.HIMessage.HIAckMessage;
import com.evsward.server.protobuf.HIMessage.HIReqMessage;
import com.evsward.server.protobuf.HIMessage.HIAckMessage.Builder;
import com.evsward.server.thread.PushMsg2TerminalService;
import com.evsward.server.util.Application;
import com.evsward.server.util.RspCodeValue;
import com.evsward.server.util.HIUtil;
import com.netty.server.core.ServerContext;
import com.netty.server.core.annotation.CommandIdBasedPolicy;
import com.netty.server.tcp.TcpServerContext;
import com.netty.server.tcp.handler.ProtobufMessageHandler;

/**
 * 大屏幕信息显示handler
 */
@Component
@CommandIdBasedPolicy(101)
public class ScreenShowInfoHandler implements ProtobufMessageHandler<HIReqMessage,HIAckMessage.Builder> {

	private static Logger logger = LoggerFactory.getLogger(ScreenShowInfoHandler.class);
	
	@Resource
	private ScreenManageFacade screenFacade;
	
	
	public Builder defaultFailedResponse(ServerContext serverContext,Throwable throwable, String errorMessage) {
		String jsonRes = "";
		HIAckMessage.Builder result = HIAckMessage.newBuilder();
		jsonRes = HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$_1.getRspCode(), RspCodeValue.$_1.getMsg()));
		result.setJsonAckMsg(jsonRes);
		return result;
	}
	
	/**
	 * 大屏幕设备tcp请求，发送响应消息，缓存tcp连接
	 */
	public Builder execute(ServerContext serverContext, HIReqMessage reqMsg) {
		String reqJson = reqMsg.getJsonReqMsg();//获取请求的json数据串
		logger.info("ScreenShowInfoHandler.reqJson = " + reqJson);
		//缓存TcpServerContext
		String devImei = JsonBinder.getValue(JsonBinder.JSONObject, reqJson, Application.JSONKEY_IMEI);
		TcpServerContext tcpContext = (TcpServerContext)serverContext;
		HIAckMessage.Builder result = HIAckMessage.newBuilder();
		String jsonRes = "";
		PushMsg2TerminalService pushMsgService = Application.pushMsg2TermServiceMap.get(Application.MSGTYPE.SCREENSHOW);
		if(pushMsgService == null){
			logger.error("客户端比赛列表连接服务端，缓存channel失败，该消息推送线程对象为null，IMEI = " + devImei);
			result.setJsonAckMsg(HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$_1.getRspCode(), RspCodeValue.$_1.getMsg())));
		}
		pushMsgService.cacheChannel(new ScreenShowInfoCache(tcpContext.getChannel(), tcpContext.getCommandId(), devImei, tcpContext.getClientIp()));
		jsonRes = this.screenFacade.getScreenShowInfo(devImei);
		result.setJsonAckMsg(jsonRes);
		return result;
	}
}
