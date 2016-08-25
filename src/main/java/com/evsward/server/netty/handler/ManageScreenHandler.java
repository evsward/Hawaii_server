package com.evsward.server.netty.handler;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.dance.core.utils.encode.JsonBinder;
import com.evsward.server.facade.ScreenManageFacade;
import com.evsward.server.netty.cache.DevManageCache;
import com.evsward.server.protobuf.WptMessage.WptAckMessage;
import com.evsward.server.protobuf.WptMessage.WptReqMessage;
import com.evsward.server.protobuf.WptMessage.WptAckMessage.Builder;
import com.evsward.server.thread.PushMsg2TerminalService;
import com.evsward.server.util.Application;
import com.evsward.server.util.RspCodeValue;
import com.evsward.server.util.HIUtil;
import com.netty.server.core.ServerContext;
import com.netty.server.core.annotation.CommandIdBasedPolicy;
import com.netty.server.tcp.TcpServerContext;
import com.netty.server.tcp.handler.ProtobufMessageHandler;

/**
 * 设备管理Handler
 */
@Component
@CommandIdBasedPolicy(501)
public class ManageScreenHandler implements ProtobufMessageHandler<WptReqMessage,WptAckMessage.Builder> {

	private static Logger logger = LoggerFactory.getLogger(ManageScreenHandler.class);
	
	@Resource
	private ScreenManageFacade screenFacade;

	public Builder execute(ServerContext serverContext, WptReqMessage reqMsg){
		String reqJson = reqMsg.getJsonReqMsg();//获取请求的json数据串
		logger.info("ManageScreenHandler.reqJson = " + reqJson);
		//缓存Channel
		String imei = JsonBinder.getValue(JsonBinder.JSONObject, reqJson, Application.JSONKEY_IMEI);
		String sysTypeStr = JsonBinder.getValue(JsonBinder.JSONObject, reqJson, Application.JSONKEY_SYSTYPE);
		int sysType = Integer.parseInt(sysTypeStr);
		TcpServerContext tcpContext = (TcpServerContext)serverContext;
		WptAckMessage.Builder result = WptAckMessage.newBuilder();
		String jsonRes = "";
		PushMsg2TerminalService pushMsgService = Application.pushMsg2TermServiceMap.get(Application.MSGTYPE.PADDEVIMAN);
		if(pushMsgService == null){
			logger.error("pad端大屏幕设备管理, 连接服务端，缓存channel失败，该消息推送线程对象为null，IMEI = " + imei);
			result.setJsonAckMsg(HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$_1.getRspCode(), RspCodeValue.$_1.getMsg())));
		}
		pushMsgService.cacheChannel(new DevManageCache(tcpContext.getChannel(), tcpContext.getCommandId(), imei, tcpContext.getClientIp()));
		jsonRes = this.screenFacade.findAllScreens(sysType);
		result.setJsonAckMsg(jsonRes);
		return result;
	}

	public Builder defaultFailedResponse(ServerContext arg0, Throwable throwable, String errorMsg) {
		String jsonRes = "";
		logger.error("---------------ManageScreenHandler.defaultFailedResponse");
		WptAckMessage.Builder result = WptAckMessage.newBuilder();
		jsonRes = HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$301.getRspCode(), RspCodeValue.$301.getMsg()));
		result.setJsonAckMsg(jsonRes);
		return result;
	}

	
}
