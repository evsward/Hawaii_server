package com.evsward.server.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.evsward.server.vo.AdvertInfo;
import com.evsward.server.vo.CompRunningPrize;
import com.evsward.server.vo.CompRunningRound;
import com.evsward.server.vo.Round;
import com.evsward.server.vo.Screen;
import com.evsward.server.vo.screen.ScreenCompInfo;
import com.evsward.server.vo.screen.ScreenCompPlayerInfo;
import com.evsward.server.vo.screen.ScreenCompTimeInfo;
import com.evsward.server.vo.screen.ScreenWelcomeInfo;

public class Demo {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		screenDevInfo();
	}
	
	public static void welcome(){
//		ScreenWelcomeInfo welcome = new ScreenWelcomeInfo();
//		welcome.setMemID(1);
//		welcome.setMemName("欢迎欢迎");
//		welcome.setMemNickName("这是我的昵称");
//		Map<String, Object> map = new HashMap<String, Object>();
//		map.put("welcome", welcome);
//		Map<String, Object> resmap = HIUtil.createJsonResInitMap(RspCodeValue.$1.getRspCode(), RspCodeValue.$1.getMsg(), map);
//		String json = HIUtil.toJsonNormalField(resmap);
//		System.out.println(json);
	}
	
	public static void screenDevInfo(){
		Map<String, Object> map = new HashMap<String, Object>();
		Screen screen = new Screen();
		screen.setCompID(57);
		screen.setCompName("DEVTEST");
		screen.setCreateTime(new Date());
		screen.setDevImei("11111111");
		screen.setDevName("HIDEV1");
		screen.setDevState(1);
		screen.setIp(2887522626l);
		screen.setIpStr("172.28.25.66");
		screen.setLanguage(0);
		screen.setPushType(2);
		screen.setSysType(1);
		screen.setUpdateTime(new Date());
		map.put("screenDevInfo", screen);
		Map<String, Object> resmap = HIUtil.createJsonResInitMap(RspCodeValue.$1.getRspCode(), RspCodeValue.$1.getMsg(), map);
		String json = HIUtil.toJsonNormalField(resmap);
		System.out.println(json);
	}
	
	public static void playerInfo(){
		Map<String, Object> map = new HashMap<String, Object>();
		ScreenCompPlayerInfo playerInfo1 = new ScreenCompPlayerInfo(1, "000003", "选手以", 3, 5, 10000);
		ScreenCompPlayerInfo playerInfo2 = new ScreenCompPlayerInfo(2, "000002", "选手以1", 4, 5, 10000);
		ScreenCompPlayerInfo playerInfo3 = new ScreenCompPlayerInfo(3, "000001", "选手以2", 5, 5, 10000);
		ScreenCompPlayerInfo playerInfo4 = new ScreenCompPlayerInfo(4, "000023", "选手以3", 7, 5, 10000);
		ScreenCompPlayerInfo playerInfo5 = new ScreenCompPlayerInfo(5, "000056", "选手以4", 9, 5, 10000);
		List<ScreenCompPlayerInfo> list = new ArrayList<ScreenCompPlayerInfo>();
		list.add(playerInfo1);
		list.add(playerInfo2);
		list.add(playerInfo3);
		list.add(playerInfo4);
		list.add(playerInfo5);
		map.put("playerInfoList", list);
		Map<String, Object> resmap = HIUtil.createJsonResInitMap(RspCodeValue.$1.getRspCode(), RspCodeValue.$1.getMsg(), map);
		String json = HIUtil.toJsonNormalField(resmap);
		System.out.println(json);
	}
	
	public static void compList(){
//		Map<String, Object> map = new HashMap<String, Object>();
//		ScreenCompInfo compInfo1 = new ScreenCompInfo("abcdefg", 1, 1, "大屏测试比赛列表比赛1", 1, "正在报名，比赛未开始", 0, "2015-06-29", "15:00", 100, 0);
//		ScreenCompInfo compInfo2 = new ScreenCompInfo("abcdefg", 1, 1, "大屏测试比赛列表比赛2", 1, "正在报名，比赛未开始", 0, "2015-06-29", "16:00", 100, 0);
//		ScreenCompInfo compInfo3 = new ScreenCompInfo("abcdefg", 1, 1, "大屏测试比赛列表比赛3", 1, "正在报名，比赛未开始", 0, "2015-06-29", "17:00", 100, 0);
//		ScreenCompInfo compInfo4 = new ScreenCompInfo("abcdefg", 1, 1, "大屏测试比赛列表比赛4", 1, "正在报名，比赛未开始", 0, "2015-06-29", "18:00", 100, 0);
//		ScreenCompInfo compInfo5 = new ScreenCompInfo("abcdefg", 1, 1, "大屏测试比赛列表比赛5", 1, "正在报名，比赛未开始", 0, "2015-06-29", "19:00", 100, 0);
//		List<ScreenCompInfo> list = new ArrayList<ScreenCompInfo>();
//		list.add(compInfo1);
//		list.add(compInfo2);
//		list.add(compInfo3);
//		list.add(compInfo4);
//		list.add(compInfo5);
//		map.put("screenCompList", list);
//		Map<String, Object> resmap = HIUtil.createJsonResInitMap(RspCodeValue.$1.getRspCode(), RspCodeValue.$1.getMsg(), map);
//		String json = HIUtil.toJsonNormalField(resmap);
//		System.out.println(json);
	}

	public void compTimer(){
//		Map<String, Object> map = new HashMap<String, Object>();
//		ScreenCompTimeInfo timeInfo = new ScreenCompTimeInfo();
//		AdvertInfo compAdvertInfo = new AdvertInfo();
//		compAdvertInfo.setAdvertID(1);
//		compAdvertInfo.setAdvertName("hongniu");
//		compAdvertInfo.setCreateTime(new Date());
//		compAdvertInfo.setPath("hongniu");
//		compAdvertInfo.setSysType(1);
//		timeInfo.setCompAdvertInfo(compAdvertInfo);
//		
//		timeInfo.setCompID(1);
//		timeInfo.setCompName("计时测试比赛");
//		timeInfo.setCompPause(0);
//		timeInfo.setCompState(4);
//		timeInfo.setCompStateDesc("比赛进行中");
//		
//		CompRunningRound curRound = new CompRunningRound(1, 1, 100, 50, 1, 0, 1, 120, 1435289791171l, 1435289791171l, 1);
//		timeInfo.setCurRound(curRound);
//		
//		timeInfo.setImei("abcdefgh");
//		timeInfo.setLivedAvgChipCount(3000);
//		timeInfo.setLivedPlayerCount(100);
//		
//		Round nextBreakRound = new Round(15, 1, 1, 150, 75, 120, 1, 11, 50);
//		timeInfo.setNextBreakRound(nextBreakRound);
//		
//		Round nextRound = new Round(16, 1, 1, 0, 0, 30, 0, 13, 0);
//		timeInfo.setNextRound(nextRound);
//		
//		timeInfo.setPushType(2);
//		timeInfo.setTotalChipCount(300000);
//		timeInfo.setTotalRegedPlayerCount(500);
//		timeInfo.setLanguage(1);
//		
//		CompRunningPrize compPrize = new CompRunningPrize();
//		compPrize.setAmount(10000.29d);
//		compPrize.setAmountInt(10000);
//		compPrize.setCompID(1);
//		compPrize.setCreateTime(new Date());
//		compPrize.setMemID(2);
//		compPrize.setMemIdentNO("41060111111111");
//		compPrize.setMemName("我是一个选手");
//		compPrize.setPercent(0.2734);
//		compPrize.setRanking(29);
//		compPrize.setSeatNO(3);
//		compPrize.setSysType(1);
//		compPrize.setTableNO(5);
//		compPrize.setUpdateTime(new Date());
//		timeInfo.getCompRunningPrizeList().add(compPrize);
//		
//		map.put("compTimeInfo", timeInfo);
//		Map<String, Object> resmap = HIUtil.createJsonResInitMap(RspCodeValue.$1.getRspCode(), RspCodeValue.$1.getMsg(), map);
//		String json = HIUtil.toJsonNormalField(resmap);
//		System.out.println(json);
	}
}
