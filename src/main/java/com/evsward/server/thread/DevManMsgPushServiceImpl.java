package com.evsward.server.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dance.core.utils.web.SpringContextUtils;
import com.evsward.server.facade.ScreenManageFacade;
import com.evsward.server.netty.cache.DevManageCache;
import com.evsward.server.netty.cache.HIChannelCache;
import com.evsward.server.protobuf.HIMessage.HIAckMessage;
import com.evsward.server.util.Application;
import com.evsward.server.util.SystemCache;
import com.evsward.server.vo.tcpmsg.triggmsg.SystemBasePushTriggMsg;

/**
 * PAD端大屏幕设备管理消息推送服务
 */
public class DevManMsgPushServiceImpl extends PushMsg2TerminalService {

	public DevManMsgPushServiceImpl(){
		this.screenFacade = SpringContextUtils.getBean("screenFacade");
		for (int i = 0; i < SystemCache.msgCmdList.size(); i++) {
			if(SystemCache.msgCmdList.get(i).getMsgID() == Application.MSGTYPE.PADDEVIMAN){
				this.thrdDesc = SystemCache.msgCmdList.get(i).getMsgName();
			}
		}
		logger.info(Application.TPS + this.thrdDesc + "starting" + Application.TPS);
	}
	
	protected String getPushingMsg() {
		String jsonStr = "";
		try {
			jsonStr = this.screenFacade.findAllScreens(Application.SYSTYPE.USECOMPMANAGE);
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
		DevManageCache cache = (DevManageCache)hiChCache;
		HIAckMessage.Builder result = HIAckMessage.newBuilder();
		result.setJsonAckMsg(this.getPushingMsg());
		this.writeMsg2Client(cache, encode(hiChCache, result));
	}

	private static Logger logger = LoggerFactory.getLogger(CompManProcMshPushServiceImpl.class);
	protected ScreenManageFacade screenFacade;

}
