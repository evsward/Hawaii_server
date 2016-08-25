package com.evsward.server.vo.compmanage;

import java.io.Serializable;

/**
 * 比赛管理--奖池信息
 */
@SuppressWarnings("serial")
public class CompManPrizeInfo implements Serializable{

	private int ranking;
	private int compID;
	private int memID;
	private int tableNO;
	private int seatNO;
	private String percent;
	private int amountInt;
	private String memName;
	private String memIdentNO;
	private int memSex;
	
	public int getMemSex() {
		return memSex;
	}
	public void setMemSex(int memSex) {
		this.memSex = memSex;
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
	public int getRanking() {
		return ranking;
	}
	public void setRanking(int ranking) {
		this.ranking = ranking;
	}
	public String getPercent() {
		return percent;
	}
	public void setPercent(String percent) {
		this.percent = percent;
	}
	public int getAmountInt() {
		return amountInt;
	}
	public void setAmountInt(int amountInt) {
		this.amountInt = amountInt;
	}
	public String getMemName() {
		return memName;
	}
	public void setMemName(String memName) {
		this.memName = memName;
	}
	public String getMemIdentNO() {
		return memIdentNO;
	}
	public void setMemIdentNO(String memIdentNO) {
		this.memIdentNO = memIdentNO;
	}
	public CompManPrizeInfo() {
		super();
	}
	public CompManPrizeInfo(int compID, int memID, int tableNO,
			int seatNO, int ranking, String percent, int amountInt,
			String memName, String memIdentNO, int memSex) {
		super();
		this.compID = compID;
		this.memID = memID;
		this.tableNO = tableNO;
		this.seatNO = seatNO;
		this.ranking = ranking;
		this.percent = percent;
		this.amountInt = amountInt;
		this.memName = memName;
		this.memIdentNO = memIdentNO;
	}
}
