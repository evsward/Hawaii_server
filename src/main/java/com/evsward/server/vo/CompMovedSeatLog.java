package com.evsward.server.vo;

import java.io.Serializable;
/**
 * 比赛爆桌历史记录日志
 */
@SuppressWarnings("serial")
public class CompMovedSeatLog implements Serializable {

	public interface OPTYPE{
		public static final int BURST = 1;
		public static final int BALANCE = 2;
	}

	private int logID;
	private int compID;
	private int memID;
	private String memName;
	private int memSex;
	private String cardNO;
	private int oldTableNO;
	private int oldSeatNO;
	private String empUuid;
	private int newTableNO;
	private int newSeatNO;
	private int opType;//类型：1、爆桌；2、平衡
	private long createTime;
	private int sysType;
	
	public int getMemSex() {
		return memSex;
	}
	public void setMemSex(int memSex) {
		this.memSex = memSex;
	}
	public String getMemName() {
		return memName;
	}
	public void setMemName(String memName) {
		this.memName = memName;
	}
	public String getCardNO() {
		return cardNO;
	}
	public void setCardNO(String cardNO) {
		this.cardNO = cardNO;
	}
	public int getOpType() {
		return opType;
	}
	public void setOpType(int opType) {
		this.opType = opType;
	}
	public int getSysType() {
		return sysType;
	}
	public void setSysType(int sysType) {
		this.sysType = sysType;
	}
	public int getLogID() {
		return logID;
	}
	public void setLogID(int logID) {
		this.logID = logID;
	}
	public int getCompID() {
		return compID;
	}
	public void setCompID(int compID) {
		this.compID = compID;
	}
	public int getMemID() {
		return memID;
	}
	public void setMemID(int memID) {
		this.memID = memID;
	}
	public int getOldTableNO() {
		return oldTableNO;
	}
	public void setOldTableNO(int oldTableNO) {
		this.oldTableNO = oldTableNO;
	}
	public int getOldSeatNO() {
		return oldSeatNO;
	}
	public void setOldSeatNO(int oldSeatNO) {
		this.oldSeatNO = oldSeatNO;
	}
	public String getEmpUuid() {
		return empUuid;
	}
	public void setEmpUuid(String empUuid) {
		this.empUuid = empUuid;
	}
	public int getNewTableNO() {
		return newTableNO;
	}
	public void setNewTableNO(int newTableNO) {
		this.newTableNO = newTableNO;
	}
	public int getNewSeatNO() {
		return newSeatNO;
	}
	public void setNewSeatNO(int newSeatNO) {
		this.newSeatNO = newSeatNO;
	}
	public long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}
	public CompMovedSeatLog(int compID, int memID,
			int oldTableNO, int oldSeatNO, String empUuid, int newTableNO,
			int newSeatNO, int opType, long createTime, int sysType) {
		super();
		this.compID = compID;
		this.memID = memID;
		this.oldTableNO = oldTableNO;
		this.oldSeatNO = oldSeatNO;
		this.empUuid = empUuid;
		this.newTableNO = newTableNO;
		this.newSeatNO = newSeatNO;
		this.opType = opType;
		this.createTime = createTime;
		this.sysType = sysType;
	}
	public CompMovedSeatLog() {
		super();
	}
	@Override
	public String toString() {
		return "CompetitionHistoryLog [logID=" + logID + ", compID=" + compID
				+ ", memID=" + memID + ", memSex=" + memSex +", memName=" + memName + ", cardNO="
				+ cardNO + ", oldTableNO=" + oldTableNO + ", oldSeatNO="
				+ oldSeatNO + ", empUuid=" + empUuid + ", newTableNO="
				+ newTableNO + ", newSeatNO=" + newSeatNO + ", opType="
				+ opType + ", createTime=" + createTime + ", sysType="
				+ sysType + "]";
	}
}
