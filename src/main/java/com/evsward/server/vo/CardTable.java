package com.evsward.server.vo;

import java.io.Serializable;

public class CardTable implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public interface TABLESTATE{
		public static final int TABLEUSING = 1;//被比赛占用
		public static final int TABLEIDLE = 0;//空闲
		public static final int TABLEDISABLED = -1;//不可用
	}

	private int tableNO;
	private int compID;
	private String compName;
	private int tableState;
	private long uuidLong;
	private String address;
	private int sysType;
	
	public String getCompName() {
		return compName;
	}
	public void setCompName(String compName) {
		this.compName = compName;
	}
	public int getSysType() {
		return sysType;
	}
	public void setSysType(int sysType) {
		this.sysType = sysType;
	}
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
	public int getTableState() {
		return tableState;
	}
	public void setTableState(int tableState) {
		this.tableState = tableState;
	}
	public long getUuidLong() {
		return uuidLong;
	}
	public void setUuidLong(long uuidLong) {
		this.uuidLong = uuidLong;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public CardTable(int tableNO, int compID, String compName, int tableState,
			long uuidLong, String address, int sysType) {
		super();
		this.tableNO = tableNO;
		this.compID = compID;
		this.compName = compName;
		this.tableState = tableState;
		this.uuidLong = uuidLong;
		this.address = address;
		this.sysType = sysType;
	}
	public CardTable(int tableNO, int compID, String compName, int sysType) {
		super();
		this.tableNO = tableNO;
		this.compID = compID;
		this.compName = compName;
		this.sysType = sysType;
	}
	public CardTable() {
		super();
	}
	@Override
	public String toString() {
		return "CardTable [tableNO=" + tableNO + ", compID=" + compID
				+ ", compName=" + compName + ", tableState=" + tableState
				+ ", uuidLong=" + uuidLong + ", address=" + address
				+ ", sysType=" + sysType + "]";
	}
}
