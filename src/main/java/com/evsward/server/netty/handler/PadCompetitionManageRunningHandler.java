package com.evsward.server.netty.handler;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.dance.core.utils.encode.JsonBinder;
import com.evsward.server.facade.CompetitionManageFacade;
import com.evsward.server.netty.cache.CompManageCache;
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
 * pad端，比赛管理--进程信息handler
 */
@Component
@CommandIdBasedPolicy(504)
public class PadCompetitionManageRunningHandler implements ProtobufMessageHandler<WptReqMessage,WptAckMessage.Builder>{

	private static Logger logger = LoggerFactory.getLogger(PadCompetitionManageRunningHandler.class);
	
	@Resource
	private CompetitionManageFacade compManFacade;
	
	@Override
	public Builder defaultFailedResponse(ServerContext serverContext, Throwable throwable, String errorMessage) {
		String jsonRes = "";
		logger.error("---------------PadCompetitionManageRunningHandler.defaultFailedResponse");
		WptAckMessage.Builder result = WptAckMessage.newBuilder();
		jsonRes = HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$424.getRspCode(), RspCodeValue.$424.getMsg()));
		result.setJsonAckMsg(jsonRes);
		return result;
	}

	@Override
	public Builder execute(ServerContext serverContext, WptReqMessage reqMsg) {
		int compID = 0;
		String reqJson = reqMsg.getJsonReqMsg();//获取请求的json数据串
		logger.info("PadCompetitionManageRunningHandler.reqJson = " + reqJson);
		String imei = JsonBinder.getValue(JsonBinder.JSONObject, reqJson, Application.JSONKEY_IMEI);
		String compIDStr = JsonBinder.getValue(JsonBinder.JSONObject, reqJson, Application.JSONKEY_COMPID);
		if(!StringUtils.isEmpty(compIDStr)){
			compID = Integer.parseInt(compIDStr.trim());
		}
		TcpServerContext tcpContext = (TcpServerContext)serverContext;
		WptAckMessage.Builder result = WptAckMessage.newBuilder();
		String jsonRes = "";
		PushMsg2TerminalService pushMsgService = Application.pushMsg2TermServiceMap.get(Application.MSGTYPE.PADCOMPPROC);
		if(pushMsgService == null){
			logger.error("客户端比赛进程连接服务端，未找到后台推送线程，缓存channel失败，IMEI = " + imei + "消息号,cmdId = " + tcpContext.getCommandId());
			result.setJsonAckMsg(HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$_1.getRspCode(), RspCodeValue.$_1.getMsg())));
		}
		pushMsgService.cacheChannel(new CompManageCache(compID, tcpContext.getChannel(), tcpContext.getCommandId(), imei, tcpContext.getClientIp()));
		jsonRes = this.compManFacade.getCompRunningInfo(compID);
		result.setJsonAckMsg(jsonRes);
		return result;
	}

}
