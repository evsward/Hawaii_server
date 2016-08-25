package com.evsward.server.vo.screen;

import java.io.Serializable;

import com.evsward.server.vo.CompetitionInfo;

/**
 * 大屏幕显示比赛列表--比赛信息
 */
@SuppressWarnings("serial")
public class ScreenCompInfo implements Serializable {

	//比赛ID
	private int compID;
	//比赛名称
	private String compName;
	//比赛运行状态
	private int compState;
	//比赛运行状态文字描述
	private String compStateDesc;
	//比赛初始筹码
	private int beginChip;
	//比赛开始日期：yyyy-MM-dd
	private String compStartDateStr;
	//比赛开始时间：hh:mm
	private String compStartTime;
	//
	private int totalRegedPlayerCount;
	//比赛当前运行盲注级别
	private int curRoundRank;
	
	public ScreenCompInfo(int compID, String compName, int compState,
			int beginChip, String compStartDateStr, String compStartTime,
			int totalRegedPlayerCount, int curRoundRank) {
		super();
		this.compID = compID;
		this.compName = compName;
		this.compState = compState;
		this.beginChip = beginChip;
		this.compStartDateStr = compStartDateStr;
		this.compStartTime = compStartTime;
		this.totalRegedPlayerCount = totalRegedPlayerCount;
		this.curRoundRank = curRoundRank;
	}
	public ScreenCompInfo() {
		super();
	}
	public int getCompID() {
		return compID;
	}
	public void setCompID(int compID) {
		this.compID = compID;
	}
	public String getCompName() {
		return compName;
	}
	public void setCompName(String compName) {
		this.compName = compName;
	}
	public int getCompState() {
		return compState;
	}
	public void setCompState(int compState) {
		this.compState = compState;
	}
	public String getCompStateDesc() {
		switch(compState){
			case CompetitionInfo.COMPSTATE.STATE_NOREG:
				this.compStateDesc = CompetitionInfo.COMPSTATE.STATE_NOREGSHOW;
				break;
			case CompetitionInfo.COMPSTATE.STATE_REGING_NOBEGIN:
				this.compStateDesc = CompetitionInfo.COMPSTATE.STATE_REGING_NOBEGINSHOW;
				break;
			case CompetitionInfo.COMPSTATE.STATE_REGING_BEGINED:
				this.compStateDesc = CompetitionInfo.COMPSTATE.STATE_REGING_BEGINEDSHOW;
				break;
			case CompetitionInfo.COMPSTATE.STATE_REGEND_NOBEGIN:
				this.compStateDesc = CompetitionInfo.COMPSTATE.STATE_REGEND_NOBEGINSHOW;
				break;
			case CompetitionInfo.COMPSTATE.STATE_RUNNING:
				this.compStateDesc = CompetitionInfo.COMPSTATE.STATE_RUNNINGSHOW;
				break;
			default:
				this.compStateDesc = CompetitionInfo.COMPSTATE.STATE_ENDSHOW;
		}
		return compStateDesc;
	}
	public int getBeginChip() {
		return beginChip;
	}
	public void setBeginChip(int beginChip) {
		this.beginChip = beginChip;
	}
	public String getCompStartDateStr() {
		return compStartDateStr;
	}
	public void setCompStartDateStr(String compStartDateStr) {
		this.compStartDateStr = compStartDateStr;
	}
	public String getCompStartTime() {
		return compStartTime;
	}
	public void setCompStartTime(String compStartTime) {
		this.compStartTime = compStartTime;
	}
	public int getTotalRegedPlayerCount() {
		return totalRegedPlayerCount;
	}
	public void setTotalRegedPlayerCount(int totalRegedPlayerCount) {
		this.totalRegedPlayerCount = totalRegedPlayerCount;
	}
	public int getCurRoundRank() {
		return curRoundRank;
	}
	public void setCurRoundRank(int curRoundRank) {
		this.curRoundRank = curRoundRank;
	}
}
