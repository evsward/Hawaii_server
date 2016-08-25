package com.evsward.server.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * 比赛信息
 */
public class CompetitionInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public interface COMPSTATE{
		/** 比赛已删除 */
		public static final int STATE_DEL = -1;
		public static final String STATE_DELSHOW = "已删除";
		/** 未开放 */
		public static final int STATE_NOREG = 0;
		public static final String STATE_NOREGSHOW = "未开放";
		/** 正在报名，比赛未开赛 */
		public static final int STATE_REGING_NOBEGIN = 1;
		public static final String STATE_REGING_NOBEGINSHOW = "正在报名-比赛未开赛";
		/** 正在报名，比赛进行中 */
		public static final int STATE_REGING_BEGINED = 2;
		public static final String STATE_REGING_BEGINEDSHOW = "正在报名-比赛进行中";
		/** 停止报名，比赛未开始 */
		public static final int STATE_REGEND_NOBEGIN = 3;
		public static final String STATE_REGEND_NOBEGINSHOW = "停止报名-比赛未开始";
		/** 停止报名，比赛进行中 */
		public static final int STATE_RUNNING = 4;
		public static final String STATE_RUNNINGSHOW = "停止报名-比赛进行中";
		/** 比赛已结束 */
		public static final int STATE_END = 5;
		public static final String STATE_ENDSHOW = "已结束";
	}
	
	public interface PAUSESTATE{
		/** 未暂停 */
		public static final int NOPAUSE = 0;
		/** 自动暂停 */
		public static final int AUTOPAUSE = 1;
		/** 人工触发暂停 */
		public static final int MANUALPAUSE = 2;
	}
	
	public interface COMPTYPE{
		public static final int PROMOTION = 1;//晋级淘汰赛
		public static final int SINGLE = 0;//单场淘汰赛
	}
	
	public interface SYNCSTATE{
		public static final int SYNC = 1;
		public static final int NOSYNC = 0;
	}
	
	public interface MONEYUNIT{
		public static final int CNY = 1;
		public static final int USD = 0;
	}
	
	public interface AWORDSTATE{
		public static final int WITHAWORD = 1;
		public static final int NOAWORD = 0;
	}
	
	public interface ENTRYSTATE{
		public static final int ENABLEENTRY = 1;
		public static final int DISABLEENTRY = 0;
	}
	
	public interface TABLETYPE{
		public static final int TEN = 10;
		public static final int NINE = 9;
		public static final int SIX = 6;
	}
	
	public interface ASSIGNSEATSTATE{
		public static final int USE = 1;
		public static final int NOUSE = 0;
	}
	
	public interface USELEASTPRIZE{
		public static final int USE = 1;
		public static final int NOUSE = 0;
	}
	
	private int compID;
	private int roundTempID;
	private String compName;
	private int compState;
	private int compPause;
	private int compType;
	private int sysType;
	private int regFee;
	private int serviceFee;
	private int beginChip;
	private int amountUnit;
	private int aword;
	private int stopRegRank;
	private int leastPrize;
	private int subPlayerCount;//减除的人数
	private int totalPlayer;
	private int totalChip;
	private int syncData;
	private int tableType;
	private int assignSeat;
	private int reEntry;
	private Date startTime;
	private Date startRegTime;
	private Date createTime;
	private Date updateTime;
	private Date endTime;
	private Date pauseTime;
	//比赛当前运行盲注信息
	private CompRunningRound compRunningRound = null;
	
	public CompRunningRound getCompRunningRound() {
		return compRunningRound;
	}
	public void setCompRunningRound(CompRunningRound compRunningRound) {
		this.compRunningRound = compRunningRound;
	}
	public int getTotalPlayer() {
		return totalPlayer;
	}
	public void setTotalPlayer(int totalPlayer) {
		this.totalPlayer = totalPlayer;
	}
	public int getTotalChip() {
		return totalChip;
	}
	public void setTotalChip(int totalChip) {
		this.totalChip = totalChip;
	}
	public int getLeastPrize() {
		return leastPrize;
	}
	public void setLeastPrize(int leastPrize) {
		this.leastPrize = leastPrize;
	}
	public int getCompID() {
		return compID;
	}
	public void setCompID(int compID) {
		this.compID = compID;
	}
	public int getRoundTempID() {
		return roundTempID;
	}
	public void setRoundTempID(int roundTempID) {
		this.roundTempID = roundTempID;
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
	public int getCompPause() {
		return compPause;
	}
	public void setCompPause(int compPause) {
		this.compPause = compPause;
	}
	public int getCompType() {
		return compType;
	}
	public void setCompType(int compType) {
		this.compType = compType;
	}
	public int getSysType() {
		return sysType;
	}
	public void setSysType(int sysType) {
		this.sysType = sysType;
	}
	public int getRegFee() {
		return regFee;
	}
	public void setRegFee(int regFee) {
		this.regFee = regFee;
	}
	public int getServiceFee() {
		return serviceFee;
	}
	public void setServiceFee(int serviceFee) {
		this.serviceFee = serviceFee;
	}
	public int getBeginChip() {
		return beginChip;
	}
	public void setBeginChip(int beginChip) {
		this.beginChip = beginChip;
	}
	public int getAmountUnit() {
		return amountUnit;
	}
	public void setAmountUnit(int amountUnit) {
		this.amountUnit = amountUnit;
	}
	public int getAword() {
		return aword;
	}
	public void setAword(int aword) {
		this.aword = aword;
	}
	public int getStopRegRank() {
		return stopRegRank;
	}
	public void setStopRegRank(int stopRegRank) {
		this.stopRegRank = stopRegRank;
	}
	public int getSubPlayerCount() {
		return subPlayerCount;
	}
	public void setSubPlayerCount(int subPlayerCount) {
		this.subPlayerCount = subPlayerCount;
	}
	public int getSyncData() {
		return syncData;
	}
	public void setSyncData(int syncData) {
		this.syncData = syncData;
	}
	public int getTableType() {
		return tableType;
	}
	public void setTableType(int tableType) {
		this.tableType = tableType;
	}
	public int getAssignSeat() {
		return assignSeat;
	}
	public void setAssignSeat(int assignSeat) {
		this.assignSeat = assignSeat;
	}
	public int getReEntry() {
		return reEntry;
	}
	public void setReEntry(int reEntry) {
		this.reEntry = reEntry;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getStartRegTime() {
		return startRegTime;
	}
	public void setStartRegTime(Date startRegTime) {
		this.startRegTime = startRegTime;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public Date getPauseTime() {
		return pauseTime;
	}
	public void setPauseTime(Date pauseTime) {
		this.pauseTime = pauseTime;
	}
	public CompetitionInfo(int roundTempID, String compName,int compState, int compPause, int compType, 
			int regFee,int serviceFee, int beginChip, int amountUnit,int aword, int stopRegRank, 
			int leastPrize, int subPlayerCount, int totalPlayer, int totalChip, int syncData,int tableType, int sysType, int assignSeat, int reEntry,
			Date startTime, Date createTime, Date updateTime, Date endTime,Date pauseTime) {
		super();
		this.roundTempID = roundTempID;
		this.compName = compName;
		this.compState = compState;
		this.compPause = compPause;
		this.compType = compType;
		this.regFee = regFee;
		this.serviceFee = serviceFee;
		this.beginChip = beginChip;
		this.amountUnit = amountUnit;
		this.aword = aword;
		this.stopRegRank = stopRegRank;
		this.leastPrize = leastPrize;
		this.subPlayerCount = subPlayerCount;
		this.totalPlayer = totalPlayer;
		this.totalChip = totalChip;
		this.syncData = syncData;
		this.tableType = tableType;
		this.sysType = sysType;
		this.assignSeat = assignSeat;
		this.reEntry = reEntry;
		this.startTime = startTime;
		this.createTime = createTime;
		this.updateTime = updateTime;
		this.endTime = endTime;
		this.pauseTime = pauseTime;
	}
	public CompetitionInfo() {
		super();
	}
	@Override
	public String toString() {
		return "CompetitionInfo [compID=" + compID + ", roundTempID="
				+ roundTempID + ", compName=" + compName + ", compState="
				+ compState + ", compPause=" + compPause + ", compType="
				+ compType + ", sysType=" + sysType + ", regFee=" + regFee
				+ ", serviceFee=" + serviceFee + ", beginChip=" + beginChip
				+ ", amountUnit=" + amountUnit + ", aword=" + aword
				+ ", stopRegRank=" + stopRegRank + ", leastPrize=" + leastPrize
				+ ", syncData=" + syncData + ", tableType=" + tableType
				+ ", assignSeat=" + assignSeat + ", reEntry=" + reEntry
				+ ", startTime=" + startTime + ", startRegTime=" + startRegTime
				+ ", createTime=" + createTime + ", updateTime=" + updateTime
				+ ", endTime=" + endTime + ", pauseTime=" + pauseTime + "]";
	}
}
