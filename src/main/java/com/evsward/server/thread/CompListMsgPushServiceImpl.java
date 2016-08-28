package com.evsward.server.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dance.core.utils.web.SpringContextUtils;
import com.evsward.server.facade.CompetitionManageFacade;
import com.evsward.server.netty.cache.HIChannelCache;
import com.evsward.server.protobuf.HIMessage.HIAckMessage;
import com.evsward.server.util.Application;
import com.evsward.server.util.SystemCache;
import com.evsward.server.vo.tcpmsg.triggmsg.SystemBasePushTriggMsg;

/**
 * PAD端比赛列表消息推送服务
 */
public class CompListMsgPushServiceImpl extends PushMsg2TerminalService {

	private static Logger logger = LoggerFactory.getLogger(CompListMsgPushServiceImpl.class);
	
	public CompListMsgPushServiceImpl() {
		this.compManageFacade = SpringContextUtils.getBean("compManageFacade");
		for (int i = 0; i < SystemCache.msgCmdList.size(); i++) {
			if(SystemCache.msgCmdList.get(i).getMsgID() == Application.MSGTYPE.PADCOMPLIST){
				this.thrdDesc = SystemCache.msgCmdList.get(i).getMsgName();
			}
		}
		logger.info(Application.TPS + this.thrdDesc + "starting" + Application.TPS);
	}

	protected String getPushingMsg(SystemBasePushTriggMsg pushTriggMsg){
		String jsonStr = "";
		try {
			jsonStr = this.compManageFacade.getAllComptitionsNoDel(Application.SYSTYPE.USECOMPMANAGE);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return jsonStr;
	}
	
	/**
	 * 消息推送终端
	 */
	@Override
	public void pushMsg2Terminal(SystemBasePushTriggMsg pushTriggMsg, HIChannelCache hiChCache) {
		HIAckMessage.Builder result = HIAckMessage.newBuilder();
		result.setJsonAckMsg(this.getPushingMsg(pushTriggMsg));
		this.writeMsg2Client(hiChCache, encode(hiChCache, result));
	}
	
	protected CompetitionManageFacade compManageFacade;
}
