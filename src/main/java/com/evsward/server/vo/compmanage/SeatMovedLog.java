package com.evsward.server.vo.compmanage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.evsward.server.vo.CompMovedSeatLog;

@SuppressWarnings("serial")
public class SeatMovedLog implements Serializable {

	private int tableNO;
	private int opType;//1、爆桌；2、平衡
	
	private List<CompMovedSeatLog> logList = new ArrayList<CompMovedSeatLog>();

	public int getTableNO() {
		return tableNO;
	}

	public void setTableNO(int tableNO) {
		this.tableNO = tableNO;
	}

	public int getOpType() {
		return opType;
	}

	public void setOpType(int opType) {
		this.opType = opType;
	}

	public List<CompMovedSeatLog> getLogList() {
		return logList;
	}
}
