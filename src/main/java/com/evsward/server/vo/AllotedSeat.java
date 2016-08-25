package com.evsward.server.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * 已分座位
 */
@SuppressWarnings("serial")
public class AllotedSeat implements Serializable {

	private int tableNO;
	private int seatNO;
	private int compID;
	private int memID;
	private Date createTime;
	
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public int getTableNO() {
		return tableNO;
	}
	public void setTableNO(int tableNO) {
		this.tableNO = tableNO;
	}
	public int getSeatNO() {
		return seatNO;
	}
	public void setSeatNO(int seatNO) {
		this.seatNO = seatNO;
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
	public AllotedSeat(int tableNO, int seatNO, int compID, int memID, Date createTime) {
		super();
		this.tableNO = tableNO;
		this.seatNO = seatNO;
		this.compID = compID;
		this.memID = memID;
		this.createTime = createTime;
	}
	public AllotedSeat() {
		super();
	}
}
