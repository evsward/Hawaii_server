package com.evsward.server.netty.handler;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.dance.core.utils.encode.JsonBinder;
import com.evsward.server.facade.CompetitionManageFacade;
import com.evsward.server.netty.cache.CompManageCache;
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
 * pad端，比赛管理--座位信息handler
 */
@Component
@CommandIdBasedPolicy(503)
public class PadCompetitionManageSeatsHandler implements ProtobufMessageHandler<HIReqMessage,HIAckMessage.Builder>{

	private static Logger logger = LoggerFactory.getLogger(PadCompetitionManageSeatsHandler.class);
	
	@Resource
	private CompetitionManageFacade compManFacade;
	
	@Override
	public Builder defaultFailedResponse(ServerContext serverContext, Throwable throwable, String errorMessage) {
		String jsonRes = "";
		logger.error("---------------PadCompetitionManageSeatsHandler.defaultFailedResponse");
		logger.error(errorMessage, throwable);
		HIAckMessage.Builder result = HIAckMessage.newBuilder();
		jsonRes = HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$423.getRspCode(), RspCodeValue.$423.getMsg()));
		result.setJsonAckMsg(jsonRes);
		return result;
	}

	@Override
	public Builder execute(ServerContext serverContext, HIReqMessage reqMsg) {
		int compID = 0;
		String reqJson = reqMsg.getJsonReqMsg();//获取请求的json数据串
		logger.info("PadCompetitionManageSeatsHandler.reqJson = " + reqJson);
		String imei = JsonBinder.getValue(JsonBinder.JSONObject, reqJson, Application.JSONKEY_IMEI);
		String compIDStr = JsonBinder.getValue(JsonBinder.JSONObject, reqJson, Application.JSONKEY_COMPID);
		if(!StringUtils.isEmpty(compIDStr)){
			compID = Integer.parseInt(compIDStr.trim());
		}
		TcpServerContext tcpContext = (TcpServerContext)serverContext;
		HIAckMessage.Builder result = HIAckMessage.newBuilder();
		String jsonRes = "";
		PushMsg2TerminalService pushMsgService = Application.pushMsg2TermServiceMap.get(Application.MSGTYPE.PADCOMPSEAT);
		if(pushMsgService == null){
			logger.error("客户端比赛列表连接服务端，缓存channel失败，该消息推送线程对象为null，IMEI = " + imei);
			result.setJsonAckMsg(HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$_1.getRspCode(), RspCodeValue.$_1.getMsg())));
		}
		pushMsgService.cacheChannel(new CompManageCache(compID, tcpContext.getChannel(), tcpContext.getCommandId(), imei, tcpContext.getClientIp()));
		jsonRes = this.compManFacade.getCompMemsSeatInfo(compID);
		result.setJsonAckMsg(jsonRes);
		return result;
	}

}
