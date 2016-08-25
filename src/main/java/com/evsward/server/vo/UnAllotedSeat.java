package com.evsward.server.vo;

import java.io.Serializable;

/**
 * 待分座位
 */
@SuppressWarnings("serial")
public class UnAllotedSeat implements Serializable {

	public interface AllotedState{
		public static final int SUCCESS = 1;
		public static final int FAIL = 0;
	}
	private int tableNO;
	private int seatNO;
	private int compID;
	private int tableType;
	private long createTime;//毫秒级
	/**
	 * 待分配的级别:
	 * 	10人桌：234678为level=1;1为level=2;5为level=3;9为level=4;10为level=5
	 *   9人桌：234678为level=1;1为level=2;5为level=3;9为level=4
	 *   6人桌：2345为level=1;1为level=2;6为level=3
	 */
	private int level;
	private int sysType;
	
	public long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}
	public int getSysType() {
		return sysType;
	}
	public void setSysType(int sysType) {
		this.sysType = sysType;
	}
	public UnAllotedSeat() {
		super();
	}
	public UnAllotedSeat(int tableNO, int seatNO) {
		super();
		this.tableNO = tableNO;
		this.seatNO = seatNO;
	}
	public UnAllotedSeat(int tableNO, int seatNO, int compID) {
		super();
		this.tableNO = tableNO;
		this.seatNO = seatNO;
		this.compID = compID;
	}
	public UnAllotedSeat(int tableNO, int seatNO, int compID, long createTime,
			int level, int tableType, int sysType) {
		super();
		this.tableNO = tableNO;
		this.seatNO = seatNO;
		this.compID = compID;
		this.createTime = createTime;
		this.level = level;
		this.tableType = tableType;
		this.sysType = sysType;
	}
	
	/**
	 * 待分配的级别:
	 * 	10人桌：234678为level=1;1为level=2;5为level=3;9为level=4;10为level=5
	 *   9人桌：234678为level=1;1为level=2;5为level=3;9为level=4
	 *   6人桌：2345为level=1;1为level=2;6为level=3
	 */
	public int getLevel() {
		return level;
	}
	/**
	 * 待分配的级别:
	 * 	10人桌：234678为level=1;1为level=2;5为level=3;9为level=4;10为level=5
	 *   9人桌：234678为level=1;1为level=2;5为level=3;9为level=4
	 *   6人桌：2345为level=1;1为level=2;6为level=3
	 */
	public void setLevel(int level) {
		this.level = level;
	}
	public int getTableType() {
		return tableType;
	}
	public void setTableType(int tableType) {
		this.tableType = tableType;
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
	@Override
	public String toString() {
		return "UnAllotedSeat [tableNO=" + tableNO + ", seatNO="
				+ seatNO + ", compID=" + compID + ", tableType=" + tableType
				+ ", createTime=" + createTime + ", level=" + level + ", sysType="
				+ sysType + "]";
	}
}
