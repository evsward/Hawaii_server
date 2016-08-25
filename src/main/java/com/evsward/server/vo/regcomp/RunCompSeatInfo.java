package com.evsward.server.vo.regcomp;

import java.io.Serializable;

@SuppressWarnings("serial")
public class RunCompSeatInfo implements Serializable {

	private int tableNO;
	private int seatNO;
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
	public RunCompSeatInfo() {
		super();
		// TODO Auto-generated constructor stub
	}
	public RunCompSeatInfo(int tableNO, int seatNO) {
		super();
		this.tableNO = tableNO;
		this.seatNO = seatNO;
	}
}
