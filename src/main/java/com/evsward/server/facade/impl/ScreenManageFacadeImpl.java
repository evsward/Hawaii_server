package com.evsward.server.facade.impl;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.evsward.server.facade.ScreenManageFacade;
import com.evsward.server.service.AdvertInfoService;
import com.evsward.server.service.CompCurRoundMangeService;
import com.evsward.server.service.CompetitionService;
import com.evsward.server.service.MemberManageService;
import com.evsward.server.service.ScreensManageService;
import com.evsward.server.util.Application;
import com.evsward.server.util.DateUtils;
import com.evsward.server.util.RspCodeValue;
import com.evsward.server.util.SystemCache;
import com.evsward.server.util.HISysProp;
import com.evsward.server.util.HIUtil;
import com.evsward.server.vo.AdvertInfo;
import com.evsward.server.vo.CompRunningRound;
import com.evsward.server.vo.CompetitionInfo;
import com.evsward.server.vo.MemberInfo;
import com.evsward.server.vo.Round;
import com.evsward.server.vo.Screen;
import com.evsward.server.vo.screen.ScreenCompCurRound;
import com.evsward.server.vo.screen.ScreenCompInfo;
import com.evsward.server.vo.screen.ScreenCompTimeInfo;
import com.evsward.server.vo.screen.ScreenWelcomeInfo;
import com.evsward.server.vo.tcpmsg.triggmsg.SystemBasePushTriggMsg;

@Component("screenFacade")
public class ScreenManageFacadeImpl implements ScreenManageFacade {

	private static Logger logger = LoggerFactory.getLogger(ScreenManageFacadeImpl.class);
	
	@Resource
	private ScreensManageService screenService;
	@Resource
	private CompetitionService compService;
	@Resource
	private CompCurRoundMangeService compCurRoundManService;
	@Resource
	private AdvertInfoService advertService;
	@Resource
	private HISysProp hiSysProp;
	@Resource
	private MemberManageService memService;
	
	/**
	 * 大屏幕设备连接tcp请求，查询的大屏幕设备的信息（不包含比赛信息）
	 * @param devImei
	 * @return
	 */
	public String findScreenDevByImei(String devImei){
		Screen screen = null;
		String jsonRes = "";
		try {
			screen = this.screenService.findScreenByImei(devImei);
		} catch (Exception e) {
			logger.error("设备信息查询异常IMEI="+devImei, e);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("screen", screen);
			HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$303.getRspCode(), RspCodeValue.$303.getMsg(), map));
			return jsonRes;
		}
		if(screen == null){
			logger.error("大屏设备不存在IMEI="+devImei);
			return HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$302.getRspCode(), RspCodeValue.$302.getMsg()));
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("screenDevInfo", screen);
		jsonRes = HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$1.getRspCode(), RspCodeValue.$1.getMsg(), map));
		return jsonRes;
	}
	
	public String findScreenByImei(String devImei){
		Screen screen = null;
		String jsonRes = "";
		List<CompetitionInfo> noEndCompsOnday = null;
		try {
			screen = this.screenService.findScreenByImei(devImei);
		} catch (Exception e) {
			logger.error("设备信息查询异常IMEI="+devImei, e);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("screen", screen);
			HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$303.getRspCode(), RspCodeValue.$303.getMsg(), map));
			return jsonRes;
		}
		if(screen == null){
			logger.error("大屏设备不存在IMEI="+devImei);
			return HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$302.getRspCode(), RspCodeValue.$302.getMsg()));
		}
		//查询当天未结束赛事
		try {
			noEndCompsOnday = this.compService.getOndaynoEndComps(screen.getSysType());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$419.getRspCode(), RspCodeValue.$419.getMsg()));
		}
		if(noEndCompsOnday == null){
			noEndCompsOnday = Collections.emptyList();
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("screen", screen);
		map.put("noEndCompsOnday", noEndCompsOnday);
		jsonRes = HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$1.getRspCode(), RspCodeValue.$1.getMsg(), map));
		return jsonRes;
	}
	
	/**
	 * 根据devImei查询大屏幕设备
	 * @return
	 */
	public Screen getScreenByImei(String devImei){
		Screen screen = null;
		try {
			screen = this.screenService.findScreenByImei(devImei);
		} catch (Exception e) {
			logger.error("设备信息查询异常IMEI="+devImei, e);
		}
		return screen;
	}

	public String updateScreenOnpad(String devImei, String devName, int pushType, int compID, int language, int sysType) {
		CompetitionInfo compInfo = null;
		String jsonRes = "";
		Screen screen = new Screen();
		screen.setDevImei(devImei);
		screen.setDevName(devName);
		screen.setPushType(pushType);
		screen.setCompID(compID);
		screen.setLanguage(language);
		screen.setUpdateTime(new Date());
		try {
			if(pushType == Screen.DEVPUSHTYPE.PUSHTYPE_MEM || pushType == Screen.DEVPUSHTYPE.PUSHTYPE_TIME){//比赛选手信息、比赛计时信息
				compInfo = this.compService.getCompInfoByCompID(compID);
				if(compInfo == null){
					jsonRes = HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$405.getRspCode(), RspCodeValue.$405.getMsg()));
					return jsonRes;
				}
				screen.setCompName(compInfo.getCompName());
			}
			this.screenService.updateScreenOnpad(screen);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			jsonRes = HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$304.getRspCode(), RspCodeValue.$304.getMsg()));
			return jsonRes;
		}
		Application.setTriggingMsgForPushing(new SystemBasePushTriggMsg(Application.OPERTYPE.DEVIEDIT, 0, compID));
		jsonRes = HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$1.getRspCode(), RspCodeValue.$1.getMsg()));
		return jsonRes;
	}
	
	/**
	 * 查询大屏幕设备列表
	 * @param sysType
	 * @return
	 */
	public String findAllScreens(int sysType){
		String jsonRes = "";
		List<Screen> screens = null;
		try {
			screens = screenService.findAllScreens(sysType);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			jsonRes = HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$301.getRspCode(), RspCodeValue.$301.getMsg()));
			return jsonRes;
		}
		Map<String, Object> map = HIUtil.createJsonResInitMap(RspCodeValue.$1.getRspCode(), RspCodeValue.$1.getMsg());
		map.put("screens", screens);
		jsonRes = HIUtil.toJsonNormalField(map);
		return jsonRes;
	}
	
	/**
	 * 更新大屏幕为断线状态
	 * @param imei
	 * @return
	 */
	public int updateScreenOffline(String imei){
		try {
			this.screenService.updateScreenOffline(imei);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return -1;
		}
		Application.setTriggingMsgForPushing(new SystemBasePushTriggMsg(Application.OPERTYPE.DEVIOFFLINE, 0, 0));
		return 1;
	}
	
	/**
	 * 大屏幕注册
	 * @param devImei	设备号
	 * @param ipStr	客户端IP地址（172.28.25.66）
	 * @return
	 */
	public String registScreenDev(String devImei, String ipStr){
		String jsonStr = "";
		
		String[] ipArr = ipStr.split("\\.");
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < ipArr.length; i++) {
			String hex = Integer.toHexString(new Integer(ipArr[i]));
			if(hex.length() <= 1){
				hex = "0" + hex;
			}
			sb.append(hex.toUpperCase());
		}
		
		Screen screen = new Screen(devImei);
		screen.setIp(HIUtil.convertHex2Long(sb.toString()));
		screen.setDevState(Screen.DEVSTATE.DEVSTATE_ONLINE);
		screen.setCreateTime(new Date());
		screen.setPushType(Screen.DEVPUSHTYPE.PUSHTYPE_NO);
		screen.setLanguage(Screen.LANGUAGE.LANGUAGE_CN);
		screen.setCompID(0);
		screen.setDevName(StringUtils.EMPTY);
		screen.setUpdateTime(new Date());
		screen.setSysType(1);
		int res = 0;
		try {
			res = this.screenService.saveOrUpdateScreen(screen);
			screen = this.screenService.findScreenByImei(devImei);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			jsonStr = HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$483.getRspCode(), RspCodeValue.$483.getMsg()));
			return jsonStr;
		}
		if(res < 1){
			jsonStr = HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$483.getRspCode(), RspCodeValue.$483.getMsg()));
			return jsonStr;
		}
		//开始推送到大屏幕设备显示信息,放消息到消息队列中
		Application.setTriggingMsgForPushing(new SystemBasePushTriggMsg(Application.OPERTYPE.DEVIREG, 0, 0));
		Map<String, Object> map = HIUtil.createJsonResInitMap(RspCodeValue.$1.getRspCode(), RspCodeValue.$1.getMsg());
		map.put("screenDevInfo", screen);
		jsonStr = HIUtil.toJsonNormalField(map);
		return jsonStr;
	}
	
	
	/**
	 * 大屏幕tcp请求，获取大屏幕显示信息
	 * @param devImei
	 * @return
	 */
	public String getScreenShowInfo(Screen screen, int memID){
		String jsonStr = "";
		if(screen == null){
			jsonStr = HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$302.getRspCode(), RspCodeValue.$302.getMsg()));
			return jsonStr;
		}
		ScreenWelcomeInfo screenWelcomeInfo = null;//welcome
		List<ScreenCompInfo> screenComplist = null;//screenCompList
//		List<ScreenCompPlayerInfo> compPlayerlist = null;//playerInfoList
		ScreenCompTimeInfo screenCompTimer = null;//compTimeInfo
		
		MemberInfo memInfo = null;
		try {
			switch(screen.getPushType()){
				case Screen.DEVPUSHTYPE.PUSHTYPE_CHECK://入场安检
					if(memID != 0){
						memInfo = this.memService.findMemByID(memID);
						screenWelcomeInfo = new ScreenWelcomeInfo(memID, memInfo.getMemName());
					}else{
						screenWelcomeInfo = new ScreenWelcomeInfo();
					}
					break;
				case Screen.DEVPUSHTYPE.PUSHTYPE_TIME://比赛计时
					screenCompTimer = this.getScreenTimerInfo(screen, screen.getCompID());
					if(screenCompTimer == null){//发生了异常
						jsonStr = HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$482.getRspCode(), RspCodeValue.$482.getMsg()));
						return jsonStr;
					}
//				case Screen.DEVPUSHTYPE.PUSHTYPE_MEM://比赛选手信息
//					compPlayerlist = this.compService.getScreenCompPlayerInfoList(screen.getCompID());
//					break;
				case Screen.DEVPUSHTYPE.PUSHTYPE_LIST://比赛列表
					Date onday = new Date();
					String dayStr = DateUtils.formatDate(onday, DateUtils.PATTERN2);
					Date start = DateUtils.getFormatDate(dayStr + " 00:00:00", DateUtils.PATTERN1);
					Date end = DateUtils.getFormatDate(dayStr + " 23:59:59", DateUtils.PATTERN1);
					screenComplist = this.compService.getScreenCompList(start, end, 1);
					break;
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			jsonStr = HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$487.getRspCode(), RspCodeValue.$487.getMsg()));
			return jsonStr;
		}
		Map<String, Object> map = HIUtil.createJsonResInitMap(RspCodeValue.$1.getRspCode(), RspCodeValue.$1.getMsg());
		map.put("screenDevInfo", screen);
		switch(screen.getPushType()){
			case Screen.DEVPUSHTYPE.PUSHTYPE_CHECK://入场安检
				map.put("welcome", screenWelcomeInfo);
				break;
			case Screen.DEVPUSHTYPE.PUSHTYPE_TIME://比赛计时
				map.put("compTimeInfo", screenCompTimer);
				break;
//			case Screen.DEVPUSHTYPE.PUSHTYPE_MEM://比赛选手信息
//				map.put("playerInfoList", compPlayerlist);
//				break;
			case Screen.DEVPUSHTYPE.PUSHTYPE_LIST://比赛列表
				map.put("screenCompList", screenComplist);
		}
		jsonStr = HIUtil.toJsonNormalField(map);
		return jsonStr;
		
	}
	
	/**
	 * 大屏幕tcp请求，获取大屏幕显示信息
	 * @param devImei
	 * @return
	 */
	public String getScreenShowInfo(String devImei){
		String jsonStr = "";
		Screen screen = null;
		try {
			screen = this.screenService.findScreenByImei(devImei);
			if(screen == null){
				jsonStr = HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$302.getRspCode(), RspCodeValue.$302.getMsg()));
				return jsonStr;
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			
		}
		jsonStr = this.getScreenShowInfo(screen, 0);
		return jsonStr;
	}
	
	/**
	 * 查询大屏幕比赛计时信息
	 * @param compID
	 * @return
	 */
	private ScreenCompTimeInfo getScreenTimerInfo(Screen screen, int compID){
		ScreenCompTimeInfo screenCompTimer = new ScreenCompTimeInfo();
		ScreenCompCurRound screenCompCurRound = null;
		
		CompetitionInfo compInfo = null;
		CompRunningRound curRunningRound = null;
		Round nextRound = null;
		Round nextBreakRound = null;
		List<Round> roundList = null;
		AdvertInfo advertInfo = null;
		int livedPlayerCount = 0;
		int livedAvgChipCount = 0;
		int totalRegedPlayerCount = 0;
		int totalChipCount = 0;
		try{
			compInfo = this.compService.getCompInfoByCompID(compID);
			livedPlayerCount = this.compService.getCompAllLivedPlayerCount(compID);
			if(livedPlayerCount == 0){
				livedAvgChipCount = 0;
			}else{
				livedAvgChipCount = (int)((compInfo.getTotalPlayer() - compInfo.getSubPlayerCount()) * compInfo.getBeginChip() / livedPlayerCount);
			}
			totalRegedPlayerCount = compInfo.getTotalPlayer() - compInfo.getSubPlayerCount();
			totalChipCount = (compInfo.getTotalPlayer() - compInfo.getSubPlayerCount()) * compInfo.getBeginChip();
			
			curRunningRound = this.compCurRoundManService.getCurRoundOfComp(compID);
			roundList = SystemCache.roundMap.get(compInfo.getRoundTempID());
			if(curRunningRound != null){
				Round temp = null;
				for (int i = 0; i < roundList.size(); i++) {
					temp = roundList.get(i);
					if(temp.getRoundrank() == curRunningRound.getCurRank() + 1){
						nextRound = temp;
						break;
					}
				}
				for (int i = 0; i < roundList.size(); i++) {
					temp = roundList.get(i);
					if(curRunningRound.getCurType() == Round.ROUNDTYPE.REST){//当前盲注级别是休息
						if(temp.getRoundType() == Round.ROUNDTYPE.REST && temp.getRoundrank() > curRunningRound.getCurRank()){
							nextBreakRound = temp;
							break;
						}
					}else{//当前盲注是正常计时
						if(temp.getRoundType() == Round.ROUNDTYPE.REST && temp.getRoundrank() >= curRunningRound.getCurRank()){
							nextBreakRound = temp;
							break;
						}
					}
				}
				
				screenCompCurRound = new ScreenCompCurRound();
				screenCompCurRound.setCurBeforeChip(curRunningRound.getCurBeforeChip());
				screenCompCurRound.setCurBigBlind(curRunningRound.getCurBigBlind());
				screenCompCurRound.setCurRank(curRunningRound.getCurRank());
				screenCompCurRound.setCurSmallBlind(curRunningRound.getCurSmallBlind());
				screenCompCurRound.setCurType(curRunningRound.getCurType());
				//倒计时,秒
				int restTime = 0;
				long start = (long)(curRunningRound.getReStartTime() / 1000);
				int stepLen = curRunningRound.getStepLen();
				long cur = (long)(new Date().getTime() / 1000);
				restTime = (int)(stepLen - (cur - start));
				if(restTime <= 0){//比赛当前运行盲注已经计时过了。
					restTime = 0;
				}
				screenCompCurRound.setRestTime(restTime);
				screenCompTimer.setCurRound(screenCompCurRound);
				
				screenCompTimer.setNextRound(nextRound);
				screenCompTimer.setNextBreakRound(nextBreakRound);
			}
			//这个必须要放在外面，比赛未开始的情况下。开启牌桌的操作，会改变比赛的状态
			screenCompTimer.setAword(compInfo.getAword());
			screenCompTimer.setCompID(compInfo.getCompID());
			screenCompTimer.setCompName(compInfo.getCompName());
			screenCompTimer.setCompPause(compInfo.getCompPause());
			screenCompTimer.setCompState(compInfo.getCompState());
			screenCompTimer.setLivedAvgChipCount(livedAvgChipCount);
			screenCompTimer.setLivedPlayerCount(livedPlayerCount);
			screenCompTimer.setTotalChipCount(totalChipCount);
			screenCompTimer.setTotalRegedPlayerCount(totalRegedPlayerCount);
			
			advertInfo = this.advertService.getAdvertInfoByComp(compID);
			screenCompTimer.setCompAdvertInfo(advertInfo);
			
			if(screen.getLanguage() == Screen.LANGUAGE.LANGUAGE_CN){
				screenCompTimer.setTopic(hiSysProp.getPropParams().get("screen.topic.cn"));
			}else{
				screenCompTimer.setTopic(hiSysProp.getPropParams().get("screen.topic.en"));
			}
			
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			screenCompTimer = null;
		}
		return screenCompTimer;
	}
}
