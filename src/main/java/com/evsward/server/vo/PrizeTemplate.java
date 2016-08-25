package com.evsward.server.vo;

import java.io.Serializable;
import java.util.Date;

public class PrizeTemplate implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int prizeTempID;
	private int maxRanking;
	private String prizeTempName;
	private Date createTime;
	public int getPrizeTempID() {
		return prizeTempID;
	}
	public void setPrizeTempID(int prizeTempID) {
		this.prizeTempID = prizeTempID;
	}
	public int getMaxRanking() {
		return maxRanking;
	}
	public void setMaxRanking(int maxRanking) {
		this.maxRanking = maxRanking;
	}
	public String getPrizeTempName() {
		return prizeTempName;
	}
	public void setPrizeTempName(String prizeTempName) {
		this.prizeTempName = prizeTempName;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public PrizeTemplate(int prizeTempID, int maxRanking, String prizeTempName,
			Date createTime) {
		super();
		this.prizeTempID = prizeTempID;
		this.maxRanking = maxRanking;
		this.prizeTempName = prizeTempName;
		this.createTime = createTime;
	}
	public PrizeTemplate() {
		super();
	}
	@Override
	public String toString() {
		return "PrizeTemplate [prizeTempID=" + prizeTempID + ", maxRanking="
				+ maxRanking + ", prizeTempName=" + prizeTempName
				+ ", createTime=" + createTime + "]";
	}
	
}
