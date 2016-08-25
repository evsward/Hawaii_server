package com.evsward.server.vo.compmanage;

import java.io.Serializable;

/**
 * 比赛管理--进程信息VO
 */
@SuppressWarnings("serial")
public class ComManRunningInfo implements Serializable {

	private int compID;
	private int curRank;
	private int roundType;//1、计时；0、休息
	private int restTime;//倒计时，单位秒
	private int initedStepLen;//盲注表里的步进值
	private int smallBlind;
	private int bigBlind;
	private int beforeChip;
	private int totalRegedPlayer;//总报名人数
	private int totalRegedPlayerEdit;//剔除若干选手后的总人数
	private int averChip;//总筹码/存活的选手数
	private int compPause;//0、未暂停（进行中）；2、人为暂停（人为点击暂停比赛）
	
	public int getCompPause() {
		return compPause;
	}
	public void setCompPause(int compPause) {
		this.compPause = compPause;
	}
	public int getRoundType() {
		return roundType;
	}
	public void setRoundType(int roundType) {
		this.roundType = roundType;
	}
	public int getInitedStepLen() {
		return initedStepLen;
	}
	public void setInitedStepLen(int initedStepLen) {
		this.initedStepLen = initedStepLen;
	}
	public int getTotalRegedPlayerEdit() {
		return totalRegedPlayerEdit;
	}
	public void setTotalRegedPlayerEdit(int totalRegedPlayerEdit) {
		this.totalRegedPlayerEdit = totalRegedPlayerEdit;
	}
	public int getCompID() {
		return compID;
	}
	public void setCompID(int compID) {
		this.compID = compID;
	}
	public int getCurRank() {
		return curRank;
	}
	public void setCurRank(int curRank) {
		this.curRank = curRank;
	}
	public int getRestTime() {
		return restTime;
	}
	public void setRestTime(int restTime) {
		this.restTime = restTime;
	}
	public int getSmallBlind() {
		return smallBlind;
	}
	public void setSmallBlind(int smallBlind) {
		this.smallBlind = smallBlind;
	}
	public int getBigBlind() {
		return bigBlind;
	}
	public void setBigBlind(int bigBlind) {
		this.bigBlind = bigBlind;
	}
	public int getBeforeChip() {
		return beforeChip;
	}
	public void setBeforeChip(int beforeChip) {
		this.beforeChip = beforeChip;
	}
	public int getTotalRegedPlayer() {
		return totalRegedPlayer;
	}
	public void setTotalRegedPlayer(int totalRegedPlayer) {
		this.totalRegedPlayer = totalRegedPlayer;
	}
	public int getAverChip() {
		return averChip;
	}
	public void setAverChip(int averChip) {
		this.averChip = averChip;
	}
	public ComManRunningInfo(int compID, int curRank, int roundType, int restTime,
			int smallBlind, int bigBlind, int beforeChip, int totalRegedPlayer,
			int totalRegedPlayerEdit, int averChip, int initedStepLen, int compPause) {
		super();
		this.compID = compID;
		this.curRank = curRank;
		this.roundType = roundType;
		this.restTime = restTime;
		this.smallBlind = smallBlind;
		this.bigBlind = bigBlind;
		this.beforeChip = beforeChip;
		this.totalRegedPlayer = totalRegedPlayer;
		this.totalRegedPlayerEdit = totalRegedPlayerEdit;
		this.averChip = averChip;
		this.initedStepLen = initedStepLen;
		this.compPause = compPause;
	}
	public ComManRunningInfo() {
		super();
	}
}
