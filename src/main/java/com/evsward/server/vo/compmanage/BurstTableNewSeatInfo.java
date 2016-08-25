package com.evsward.server.vo.compmanage;

import java.io.Serializable;

/**
 * 比赛爆桌后的新座位信息
 */
@SuppressWarnings("serial")
public class BurstTableNewSeatInfo implements Serializable {

	private int memID;
	private String cardNO;
	private String memName;
	private int tableNO;
	private int seatNO;
	private int memSex;
	
	public String getCardNO() {
		return cardNO;
	}
	public void setCardNO(String cardNO) {
		this.cardNO = cardNO;
	}
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
	public int getMemID() {
		return memID;
	}
	public void setMemID(int memID) {
		this.memID = memID;
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
	public BurstTableNewSeatInfo(int memID, String cardNO, String memName, int tableNO, int seatNO, int memSex) {
		super();
		this.memID = memID;
		this.cardNO = cardNO;
		this.memName = memName;
		this.tableNO = tableNO;
		this.seatNO = seatNO;
		this.memSex = memSex;
	}
	public BurstTableNewSeatInfo() {
		super();
	}
	@Override
	public String toString() {
		return "BurstTableNewSeatInfo [memID=" + memID + ", cardNO=" + cardNO + ", memName=" + memName
				+ ", tableNO=" + tableNO + ", seatNO=" + seatNO + ", memSex=" + memSex + "]";
	}
}
