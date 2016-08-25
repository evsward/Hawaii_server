package com.evsward.server.netty.handler;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.dance.core.utils.encode.JsonBinder;
import com.evsward.server.facade.CompetitionManageFacade;
import com.evsward.server.netty.cache.CompListCache;
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
 * pad端，比赛列表handler
 */
@Component
@CommandIdBasedPolicy(502)
public class PadCompetitionListHandler implements ProtobufMessageHandler<WptReqMessage,WptAckMessage.Builder>{

	private static Logger logger = LoggerFactory.getLogger(PadCompetitionListHandler.class);
	
	@Resource
	private CompetitionManageFacade compManageFacade;
	
	@Override
	public Builder defaultFailedResponse(ServerContext serverContext, Throwable throwable, String errorMessage) {
		String jsonRes = "";
		logger.error("---------------PadCompetitionListHandler.defaultFailedResponse");
		WptAckMessage.Builder result = WptAckMessage.newBuilder();
		jsonRes = HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$414.getRspCode(), RspCodeValue.$414.getMsg()));
		result.setJsonAckMsg(jsonRes);
		return result;
	}

	@Override
	public Builder execute(ServerContext serverContext, WptReqMessage reqMsg) {
		String reqJson = reqMsg.getJsonReqMsg();//获取请求的json数据串
		logger.info("PadCompetitionListHandler.reqJson = " + reqJson);
		String imei = JsonBinder.getValue(JsonBinder.JSONObject, reqJson, Application.JSONKEY_IMEI);
		String sysTypeStr = JsonBinder.getValue(JsonBinder.JSONObject, reqJson, Application.JSONKEY_SYSTYPE);
		int sysType = Integer.parseInt(sysTypeStr.trim());
		TcpServerContext tcpContext = (TcpServerContext)serverContext;
		WptAckMessage.Builder result = WptAckMessage.newBuilder();
		String jsonRes = "";
		PushMsg2TerminalService pushMsgService = Application.pushMsg2TermServiceMap.get(Application.MSGTYPE.PADCOMPLIST);
		if(pushMsgService == null){
			logger.error("客户端比赛列表连接服务端，缓存channel失败，该消息推送线程对象为null，IMEI = " + imei);
			result.setJsonAckMsg(HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$_1.getRspCode(), RspCodeValue.$_1.getMsg())));
		}
		pushMsgService.cacheChannel(new CompListCache(tcpContext.getChannel(), tcpContext.getCommandId(), imei, tcpContext.getClientIp()));
		jsonRes = this.compManageFacade.getAllComptitionsNoDel(sysType);
		result.setJsonAckMsg(jsonRes);
		return result;
	}

}
