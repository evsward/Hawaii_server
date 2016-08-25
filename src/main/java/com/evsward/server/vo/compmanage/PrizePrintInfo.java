package com.evsward.server.vo.compmanage;

import java.io.Serializable;

@SuppressWarnings("serial")
public class PrizePrintInfo implements Serializable {

	private int memID;
	private int compID;
	private String compName;
	private String memName;
	private int memSex;
	private String cardNO;
	private String memIdentNO;
	
	private int unit;
	private int amountInt;
	private int tableNO;
	private int seatNO;
	private int ranking;
	
	private String compStartTime;

	public String getCompName() {
		return compName;
	}

	public void setCompName(String compName) {
		this.compName = compName;
	}
	public int getMemID() {
		return memID;
	}

	public void setMemID(int memID) {
		this.memID = memID;
	}

	public int getCompID() {
		return compID;
	}

	public void setCompID(int compID) {
		this.compID = compID;
	}

	public String getMemName() {
		return memName;
	}

	public void setMemName(String memName) {
		this.memName = memName;
	}

	public int getMemSex() {
		return memSex;
	}

	public void setMemSex(int memSex) {
		this.memSex = memSex;
	}

	public String getCardNO() {
		return cardNO;
	}

	public void setCardNO(String cardNO) {
		this.cardNO = cardNO;
	}

	public String getMemIdentNO() {
		return memIdentNO;
	}

	public void setMemIdentNO(String memIdentNO) {
		this.memIdentNO = memIdentNO;
	}

	public int getUnit() {
		return unit;
	}

	public void setUnit(int unit) {
		this.unit = unit;
	}

	public int getAmountInt() {
		return amountInt;
	}

	public void setAmountInt(int amountInt) {
		this.amountInt = amountInt;
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

	public String getCompStartTime() {
		return compStartTime;
	}

	public void setCompStartTime(String compStartTime) {
		this.compStartTime = compStartTime;
	}
}
