package com.evsward.server.thread;

import java.util.concurrent.atomic.AtomicBoolean;
import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dance.core.utils.spring.SpringContextHolder;
import com.evsward.server.service.CompetitionService;
import com.evsward.server.util.Application;
import com.evsward.server.vo.CompetitionInfo;
import com.evsward.server.vo.tcpmsg.triggmsg.SystemBasePushTriggMsg;

/**
 * 1、新建比赛成功后会启动该线程； 2、系统启动初始化，会启动该线程
 */
public class CompServerManage implements Runnable {

	private static Logger logger = (Logger) LoggerFactory.getLogger(CompServerManage.class);

	private volatile AtomicBoolean run = new AtomicBoolean(false);
	@Resource
	private CompetitionService compService;
	// 比赛
	private CompetitionInfo compInfo;
	// 比赛ID
	private int compID;

	private long time = System.currentTimeMillis();

	public int getCompID() {
		return compID;
	}

	public void setCompID(int compID) {
		this.compID = compID;
	}

	public CompetitionInfo getCompInfo() {
		return compInfo;
	}

	// public void setCompInfo(CompetitionInfo compInfo) {
	// this.compInfo = compInfo;
	// }

	public CompServerManage(int compID) {
		compService = SpringContextHolder.getBean("compService");
		this.compID = compID;
		try {
			compInfo = this.compService.getCompInfoByCompID(compID);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	public CompServerManage(CompetitionInfo compInfo) {
		compService = SpringContextHolder.getBean("compService");
		this.compInfo = compInfo;
		this.compID = compInfo.getCompID();
	}

	@Override
	public void run() {
		logger.info("比赛后台维护线程启动,【compID = " + this.compID + ", compName = " + this.compInfo.getCompName() + "】");
		int res = 0;
		long startTimeOrigin = compInfo.getStartTime().getTime();
		long compStartTimeLong = startTimeOrigin - 900*1000;// 倒计时开始;
		while (compInfo.getSysType() == Application.SYSTYPE.USECOMPMANAGE
				&& compInfo.getCompState() != CompetitionInfo.COMPSTATE.STATE_DEL
				&& compInfo.getCompState() != CompetitionInfo.COMPSTATE.STATE_END) {
			if (!run.get()) {
				if (System.currentTimeMillis() >= compStartTimeLong) {// 比赛时间到，比赛可以开始了。
					if (compInfo.getCompPause() == CompetitionInfo.PAUSESTATE.NOPAUSE) {// 比赛未暂停
						if (compInfo.getCompState() != CompetitionInfo.COMPSTATE.STATE_NOREG) {
							run.set(true);
							try {
								if (compInfo.getCompState() == CompetitionInfo.COMPSTATE.STATE_REGING_NOBEGIN) {// 正在报名，比赛未开始->倒计时，添加比赛当前运行盲注信息
									this.compService.updateStartCompetition_Serv(compInfo,
											CompetitionInfo.COMPSTATE.COUNTDOWN_STATE_WAIT,false);
									Application.setTriggingMsgForPushing(
											new SystemBasePushTriggMsg(Application.OPERTYPE.COMPTHREADSERV, 0, compID));
								}
								if (compInfo
										.getCompState() == CompetitionInfo.COMPSTATE.COUNTDOWN_STATE_WAIT) {// 倒计时
										this.compService.updateStartCompetition_Serv(compInfo,
												CompetitionInfo.COMPSTATE.STATE_REGING_BEGINED,true);
									Application.setTriggingMsgForPushing(
											new SystemBasePushTriggMsg(Application.OPERTYPE.COMPTHREADSERV, 0, compID));
								} else if (compInfo.getCompState() == CompetitionInfo.COMPSTATE.STATE_REGEND_NOBEGIN) {// 停止报名，【比赛未开始->比赛进行中】，添加比赛当前运行盲注信息
									this.compService.updateStartCompetition_Serv(compInfo,
											CompetitionInfo.COMPSTATE.STATE_RUNNING,false);
									Application.setTriggingMsgForPushing(
											new SystemBasePushTriggMsg(Application.OPERTYPE.COMPTHREADSERV, 0, compID));
								} else {// 2、扫描比赛当前盲注信息。判断是否该进行下一盲注，是否该更新比赛状态compState=4。
									res = this.compService.updateCompRoundRun_Serv(compInfo);
									if (res == -1) {// 盲注跳到尽头，就结束该线程
										// 比赛后台维护线程结束，就删掉线程缓存对象
										Application.compServManageMap.remove(String.valueOf(compID));
										return;
									}
									if (res >= 1) {// 正常跳盲了
										Application.setTriggingMsgForPushing(new SystemBasePushTriggMsg(
												Application.OPERTYPE.COMPTHREADSERV, 0, compID));
									}
								}
							} catch (Exception e) {
								logger.error(e.getMessage(), e);
							} finally {
								run.set(false);
							}
						}
					}
				}
			}
			try {
				Thread.sleep(500);// 500毫秒一次
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			try {
				this.compInfo = this.compService.getCompetitionInfoByCompID(this.compID);
				// this.runningRound =
				// this.runningRoundService.getCurRoundOfComp(compID);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				return;
			}
//			compStartTimeLong = compInfo.getStartTime().getTime();
			if (System.currentTimeMillis() - this.time >= 60000) {// 一分钟输出一次日志
				logger.info("后台比赛维护线程，compID=" + this.compID + ",正在运行");
				this.time = System.currentTimeMillis();
			}
		}
		// 比赛后台维护线程结束，就删掉线程缓存对象
		Application.compServManageMap.remove(String.valueOf(compID));
		logger.info("后台比赛维护线程，compID=" + this.compID + ",结束运行");
	}

}
