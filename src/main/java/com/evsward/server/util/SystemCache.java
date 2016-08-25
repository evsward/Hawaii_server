package com.evsward.server.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.evsward.server.vo.Prize;
import com.evsward.server.vo.Round;
import com.evsward.server.vo.tcpmsg.MsgCmd;
import com.evsward.server.vo.tcpmsg.MsgPushFunction;

public class SystemCache {

	/** 奖励表换存集合 */
	public static Map<String, List<Prize>> prizeMap = new LinkedHashMap<String, List<Prize>>();
	
	/** 盲注模板、盲注记录缓存<RoundTempID, List<Round>> */
	public static Map<Integer, List<Round>> roundMap = new HashMap<Integer, List<Round>>();
	
	/** 操作类型--消息类型 */
	public static Map<Integer, List<MsgCmd>> funcMsgCmdListMap = new HashMap<Integer, List<MsgCmd>>();
	
	/** 会触发消息推送的操作 */
	public static List<MsgPushFunction> msgPushFuncList = new ArrayList<MsgPushFunction>();

	/** 系统定义的所有业务消息号 */
	public static List<MsgCmd> msgCmdList = new ArrayList<MsgCmd>();
	
//	/** 会触发消息推送的操作 */
//	public static List<MsgPushFunction> msgPushFuncList = new ArrayList<MsgPushFunction>(){{
//		add(new MsgPushFunction(1, "REG", ""));
//		add(new MsgPushFunction(10, "COMPTHREADSERV", ""));
//		add(new MsgPushFunction(30, "CREATECOMP", ""));
//		add(new MsgPushFunction(31, "OUTMEM", ""));
//		add(new MsgPushFunction(32, "BALANCE", ""));
//		add(new MsgPushFunction(33, "BURST", ""));
//		add(new MsgPushFunction(34, "GOFORWARDBLIND", ""));
//		add(new MsgPushFunction(35, "GOBACKBLIND", ""));
//		add(new MsgPushFunction(36, "EDITREGEDPLAYER", ""));
//		add(new MsgPushFunction(37, "JUMPBLINDTIME", ""));
//		
//		add(new MsgPushFunction(38, "PAUSECOMP", ""));
//		add(new MsgPushFunction(39, "ENDCOMP", ""));
//		add(new MsgPushFunction(40, "OPENTREG", ""));
//		add(new MsgPushFunction(41, "RELEASETABLE", ""));
//		add(new MsgPushFunction(42, "COMPBINDADVERT", ""));
//		add(new MsgPushFunction(44, "DELCOMP", ""));
//		add(new MsgPushFunction(101, "ENTRANCECHECK", ""));
//		add(new MsgPushFunction(212, "DEVIEDIT", ""));
//		add(new MsgPushFunction(213, "DEVIDEL", ""));
//		add(new MsgPushFunction(214, "DEVIREG", ""));
//		
//		add(new MsgPushFunction(215, "DEVIOFFLINE", ""));
//	}};
	
//	/** 系统定义的所有业务消息号 */
//	public static List<MsgCmd> msgCmdList = new ArrayList<MsgCmd>(){{
//		add(new MsgCmd(101, "SCREENSHOW", "大屏幕信息显示消息", "com.evsward.server.thread.ScreenShowMsgPushServiceImpl"));
//		add(new MsgCmd(501, "PADDEVIMAN", "PAD端大屏幕设备列表消息", "com.evsward.server.thread.DevManMsgPushServiceImpl"));
//		add(new MsgCmd(502, "PADCOMPLIST", "PAD端比赛列表消息", "com.evsward.server.thread.CompListMsgPushServiceImpl"));
//		add(new MsgCmd(503, "PADCOMPSEAT", "PAD端比赛管理--座位信息消息", "com.evsward.server.thread.CompManSeatMsgPushServiceImpl"));
//		add(new MsgCmd(504, "PADCOMPPROC", "PAD端比赛管理--进程信息消息", "com.evsward.server.thread.CompManProcMshPushServiceImpl"));
//	}};
}
