package com.evsward.server.vo.importcomp;

/**
 * 比赛进阶导入时候使用的临时对象
 */
public class CompTable {

	private int tableNO;
	private int compID;
	public int getTableNO() {
		return tableNO;
	}
	public void setTableNO(int tableNO) {
		this.tableNO = tableNO;
	}
	public int getCompID() {
		return compID;
	}
	public void setCompID(int compID) {
		this.compID = compID;
	}
	public CompTable(int tableNO, int compID) {
		super();
		this.tableNO = tableNO;
		this.compID = compID;
	}
	public CompTable() {
		super();
		// TODO Auto-generated constructor stub
	}
}
