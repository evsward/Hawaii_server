package com.evsward.server.vo;

import java.io.Serializable;
import java.util.Date;

public class Prize implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int prizeID;
	private int prizeTempID;
	private int allMin;
	private int allMax;
	private double percent;
	private int rankNO;
	private int rankNum;
	private int sysType;
	private Date createTime;
	
	public int getSysType() {
		return sysType;
	}
	public void setSysType(int sysType) {
		this.sysType = sysType;
	}
	
	public int getRankNum() {
		return rankNum;
	}
	public void setRankNum(int rankNum) {
		this.rankNum = rankNum;
	}
	public int getPrizeID() {
		return prizeID;
	}
	public void setPrizeID(int prizeID) {
		this.prizeID = prizeID;
	}
	public int getPrizeTempID() {
		return prizeTempID;
	}
	public void setPrizeTempID(int prizeTempID) {
		this.prizeTempID = prizeTempID;
	}
	public int getAllMin() {
		return allMin;
	}
	public void setAllMin(int allMin) {
		this.allMin = allMin;
	}
	public int getAllMax() {
		return allMax;
	}
	public void setAllMax(int allMax) {
		this.allMax = allMax;
	}
	public double getPercent() {
		return percent;
	}
	public void setPercent(double percent) {
		this.percent = percent;
	}
	public int getRankNO() {
		return rankNO;
	}
	public void setRankNO(int rankNO) {
		this.rankNO = rankNO;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Prize(int prizeID, int prizeTempID, int allMin, int allMax,
			double percent, int rankNO, int rankNum, Date createTime) {
		super();
		this.prizeID = prizeID;
		this.prizeTempID = prizeTempID;
		this.allMin = allMin;
		this.allMax = allMax;
		this.percent = percent;
		this.rankNO = rankNO;
		this.rankNum = rankNum;
		this.createTime = createTime;
	}
	public Prize() {
		super();
	}
	@Override
	public String toString() {
		return "Prize [prizeID=" + prizeID + ", prizeTempID=" + prizeTempID
				+ ", allMin=" + allMin + ", allMax=" + allMax + ", percent="
				+ percent + ", rankNO=" + rankNO + ", rankNum=" + rankNum
				+ ", createTime=" + createTime + "]";
	}
	
}
