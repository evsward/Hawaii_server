package com.evsward.server.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;

import com.evsward.server.thread.CompServerManage;
import com.evsward.server.thread.PushMsg2TerminalService;
import com.evsward.server.vo.CompetitionInfo;
import com.evsward.server.vo.tcpmsg.MsgCmd;
import com.evsward.server.vo.tcpmsg.triggmsg.SystemBasePushTriggMsg;

/**
 * 
 */
public class Application {

	public static final String TPS = "------------------";

	/** root path */
	public static String PATH_ROOT = "";
	/** 头像图片上传目录 */
	public static final String PATH_IMAGE = "memimage";
	/** 头像图片上传目录 */
	public static final String PATH_IMAGE_SMALL = "small";
	/** 广告图片上传目录 */
	public static final String PATH_ADVERTISMENT = "advertisment";

	public static final String PATH_EXPORTFILE = "exportfile";

	public final static String JSONKEY_IMEI = "IMEI";
	public final static String JSONKEY_SYSTYPE = "sysType";
	public final static String JSONKEY_COMPID = "compID";

	public interface SYSTYPE {
		public static final int USECOMPMANAGE = 1;
		public static final int NOUSECOMPMANAGE = 0;
	}

	/**
	 * 会触发消息推送的操作类型
	 */
	public interface OPERTYPE {
		public static final int REG = 1;// 选手报名比赛

		public static final int COMPTHREADSERV = 10;// 比赛后台维护

		public static final int CREATECOMP = 30;// 新建比赛
		public static final int OUTMEM = 31;// 淘汰选手
		public static final int BALANCE = 32;// 平衡牌桌
		public static final int BURST = 33;// 爆桌
		public static final int GOFORWARDBLIND = 34;// 比赛管理--进程--进一级盲注
		public static final int GOBACKBLIND = 35;// 比赛管理--进程--退一级盲注
		public static final int EDITREGEDPLAYER = 36;// 编辑报名玩家数量
		public static final int JUMPBLINDTIME = 37;// 比赛管理-调整时间
		public static final int PAUSECOMP = 38;// 暂停、开始比赛
		public static final int ENDCOMP = 39;// 结束比赛
		public static final int OPENTREG = 40;// 比赛开启报名
		public static final int RELEASETABLE = 41;// 比赛释放牌桌
		public static final int COMPBINDADVERT = 42;// 比赛绑定广告
		public static final int DELCOMP = 44;// 删除比赛

		public static final int ENTRANCECHECK = 101;// 入场安检

		public static final int DEVIEDIT = 212;// 大屏幕设备编辑
		public static final int DEVIDEL = 213;// 大屏幕设备删除
		public static final int DEVIREG = 214;// 大屏幕设备注册
		public static final int DEVIOFFLINE = 215;// 大屏幕设备断线

	}

	/**
	 * 推送触发消息类型
	 */
	public interface MSGTYPE {
		/** pad端比赛列表 */
		public static final int PADCOMPLIST = 502;
		/** pad端比赛座位信息 */
		public static final int PADCOMPSEAT = 503;
		/** pad端比赛进程 */
		public static final int PADCOMPPROC = 504;
		/** pad端大屏幕设备管理 */
		public static final int PADDEVIMAN = 501;
		/** 大屏幕显示消息 */
		public static final int SCREENSHOW = 101;
	}

	/** 比赛后台维护线程组 */
	public static ThreadGroup compServThGroup = new ThreadGroup("compservice");
	// 所有比赛后台维护线程的缓存Map<compID, CompServerManage>
	public static Map<String, CompServerManage> compServManageMap = new HashMap<String, CompServerManage>();

	/** 消息推送线程组 */
	public static ThreadGroup pushMsgServThGroup = new ThreadGroup("pushMsg");
	/** 消息推送线程 <消息类型, PushMsg2TerminalService> */
	public static Map<Integer, PushMsg2TerminalService> pushMsg2TermServiceMap = new HashMap<Integer, PushMsg2TerminalService>();

	/**
	 * 通过比赛ID，后台启动比赛管理服务线程的入口
	 * 
	 * @param compID
	 */
	public static void startCompServEntrance(int compID) {
		String compIDStr = String.valueOf(compID);
		CompServerManage compServManage = new CompServerManage(compID);
		compServManageMap.put(compIDStr, compServManage);
		new Thread(compServThGroup, compServManage, compIDStr).start();
	}

	/**
	 * 通过比赛实体类，后台启动比赛管理服务线程的入口
	 * 
	 * @param compInfo
	 */
	public static void startCompServEntrance(CompetitionInfo compInfo) {
		String compIDStr = String.valueOf(compInfo.getCompID());
		CompServerManage compServManage = new CompServerManage(compInfo);
		compServManageMap.put(compIDStr, compServManage);
		new Thread(compServThGroup, compServManage, compIDStr).start();
	}

	/**
	 * 启动消息推送服务入口
	 */
	public static void startPushMsgServiceEntrance() throws Exception {
		String className = "";
		for (int i = 0; i < SystemCache.msgCmdList.size(); i++) {
			className = SystemCache.msgCmdList.get(i).getPushClass();
			if (!StringUtils.isEmpty(className)) {
				PushMsg2TerminalService pushMsgServiceThread = (PushMsg2TerminalService) Class.forName(className)
						.newInstance();
				pushMsg2TermServiceMap.put(SystemCache.msgCmdList.get(i).getMsgID(), pushMsgServiceThread);
				new Thread(pushMsgServThGroup, pushMsgServiceThread, SystemCache.msgCmdList.get(i).getMsgName())
						.start();
			}
		}
	}

	/**
	 * 向推送线程发送触发消息
	 * 
	 * @param compID
	 */
	public static void setTriggingMsgForPushing(SystemBasePushTriggMsg pushTriggMsg) {
		PushMsg2TerminalService pushMsgServiceThread = null;
		List<MsgCmd> msgList = SystemCache.funcMsgCmdListMap.get(pushTriggMsg.getFuncID());
		if (!msgList.isEmpty()) {
			for (int i = 0; i < msgList.size(); i++) {
				pushMsgServiceThread = Application.pushMsg2TermServiceMap.get(msgList.get(i).getMsgID());
				if (pushMsgServiceThread != null) {
					pushMsgServiceThread.putMsg(pushTriggMsg);
				}
			}
		}
	}
}