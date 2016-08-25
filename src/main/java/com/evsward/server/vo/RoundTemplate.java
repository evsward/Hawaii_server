package com.evsward.server.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class RoundTemplate implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int roundTempID;
	private String name;
	private Date createTime;
	private int sysType;
	private List<Round> rounds = null;
	public List<Round> getRounds() {
		return rounds;
	}
	public void setRounds(List<Round> rounds) {
		this.rounds = rounds;
	}
	public int getSysType() {
		return sysType;
	}
	public void setSysType(int sysType) {
		this.sysType = sysType;
	}
	public int getRoundTempID() {
		return roundTempID;
	}
	public void setRoundTempID(int roundTempID) {
		this.roundTempID = roundTempID;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public RoundTemplate(int roundTempID, String name, Date createTime) {
		super();
		this.roundTempID = roundTempID;
		this.name = name;
		this.createTime = createTime;
	}
	public RoundTemplate() {
		super();
	}
	@Override
	public String toString() {
		return "RoundTemplate [roundTempID=" + roundTempID + ", name=" + name
				+ ", createTime=" + createTime + "]";
	}
	
}
