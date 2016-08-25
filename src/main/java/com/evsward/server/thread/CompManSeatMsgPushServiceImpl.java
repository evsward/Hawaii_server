package com.evsward.server.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dance.core.utils.web.SpringContextUtils;
import com.evsward.server.facade.CompetitionManageFacade;
import com.evsward.server.netty.cache.CompManageCache;
import com.evsward.server.netty.cache.HIChannelCache;
import com.evsward.server.protobuf.WptMessage.WptAckMessage;
import com.evsward.server.util.Application;
import com.evsward.server.util.SystemCache;
import com.evsward.server.vo.tcpmsg.triggmsg.SystemBasePushTriggMsg;

/**
 * PAD端比赛管理--座位信息消息推送服务
 */
public class CompManSeatMsgPushServiceImpl extends PushMsg2TerminalService {

	public CompManSeatMsgPushServiceImpl(){
		this.compManageFacade = SpringContextUtils.getBean("compManageFacade");
		for (int i = 0; i < SystemCache.msgCmdList.size(); i++) {
			if(SystemCache.msgCmdList.get(i).getMsgID() == Application.MSGTYPE.PADCOMPSEAT){
				this.thrdDesc = SystemCache.msgCmdList.get(i).getMsgName();
			}
		}
		logger.info(Application.TPS + this.thrdDesc + "starting" + Application.TPS);
	}
	
	protected String getPushingMsg(SystemBasePushTriggMsg pushTriggMsg) {
		String jsonStr = "";
		try {
			jsonStr = this.compManageFacade.getCompMemsSeatInfo(pushTriggMsg.getCompID());
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
		CompManageCache cache = (CompManageCache)hiChCache;
		if(pushTriggMsg.getCompID() == cache.getCompID()){
			WptAckMessage.Builder result = WptAckMessage.newBuilder();
			result.setJsonAckMsg(this.getPushingMsg(pushTriggMsg));
			this.writeMsg2Client(cache, encode(hiChCache, result));
		}
	}
	
	private static Logger logger = LoggerFactory.getLogger(CompManSeatMsgPushServiceImpl.class);

	protected CompetitionManageFacade compManageFacade;
}
