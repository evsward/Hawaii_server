package com.evsward.server.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dance.core.utils.web.SpringContextUtils;
import com.evsward.server.facade.ScreenManageFacade;
import com.evsward.server.netty.cache.ScreenShowInfoCache;
import com.evsward.server.netty.cache.HIChannelCache;
import com.evsward.server.protobuf.HIMessage.HIAckMessage;
import com.evsward.server.util.Application;
import com.evsward.server.util.SystemCache;
import com.evsward.server.vo.Screen;
import com.evsward.server.vo.tcpmsg.triggmsg.SystemBasePushTriggMsg;

/**
 * 大屏幕信息显示消息推送
 */
public class ScreenShowMsgPushServiceImpl extends PushMsg2TerminalService {

	public ScreenShowMsgPushServiceImpl(){
		this.screenFacade = SpringContextUtils.getBean("screenFacade");
		for (int i = 0; i < SystemCache.msgCmdList.size(); i++) {
			if(SystemCache.msgCmdList.get(i).getMsgID() == Application.MSGTYPE.SCREENSHOW){
				this.thrdDesc = SystemCache.msgCmdList.get(i).getMsgName();
			}
		}
		logger.info(Application.TPS + this.thrdDesc + "starting" + Application.TPS);
	}
	
	protected String getPushingMsg(SystemBasePushTriggMsg pushTriggMsg, ScreenShowInfoCache screenShowInfoCache, Screen screen) {
		String jsonStr = "";
		try {
			jsonStr = this.screenFacade.getScreenShowInfo(screen, pushTriggMsg.getMemID());
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
		ScreenShowInfoCache screenShowInfoCache = (ScreenShowInfoCache)hiChCache;
		String devImei = screenShowInfoCache.getImei();
		int compID = pushTriggMsg.getCompID();
		Screen screen = null;
		try{
			screen = this.screenFacade.getScreenByImei(devImei);
			if(screen == null){//设备不存在，扔掉该channel
				this.onlineChannelMap.remove(screenShowInfoCache.getChannelId());
				return;
			}
			if(screen.getPushType() == Screen.DEVPUSHTYPE.PUSHTYPE_TIME || screen.getPushType() == Screen.DEVPUSHTYPE.PUSHTYPE_MEM){//盒子推送类型是比赛计时和比赛选手信息，则校验是否同一个比赛，不是则不推
				if(compID != screen.getCompID()){
					return;
				}
			}else if(Screen.DEVPUSHTYPE.PUSHTYPE_CHECK == screen.getPushType()){//入场欢迎,选手memID==0，则不推，说明不是该类型的触发消息
//				if(pushTriggMsg.getMemID() == 0){
//					return;
//				}
			}
			HIAckMessage.Builder result = HIAckMessage.newBuilder();
			result.setJsonAckMsg(this.getPushingMsg(pushTriggMsg, screenShowInfoCache, screen));
			this.writeMsg2Client(screenShowInfoCache, encode(hiChCache, result));
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			return;
		}
	}
	
	private static Logger logger = LoggerFactory.getLogger(ScreenShowMsgPushServiceImpl.class);
	protected ScreenManageFacade screenFacade;

}
