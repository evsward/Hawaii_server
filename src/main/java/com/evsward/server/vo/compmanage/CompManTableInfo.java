package com.evsward.server.vo.compmanage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 比赛管理--座位--牌桌信息
 */
@SuppressWarnings("serial")
public class CompManTableInfo implements Serializable {

	public interface TABLEBUTOPER{
		public static final int LOCK = 0;//开启按钮（牌桌空闲，可以被开启）
		public static final int RELEASE = 1;//释放按钮（牌桌被比赛占用，桌上没有选手，可以被释放）
		public static final int BURSTTABLE = 2;//爆桌按钮（牌桌可以被爆掉）
		public static final int DEFAULT = -1;
	}
	
	private int tableNO;
	private int tableState;
	private int tableType;
	
	private int button = TABLEBUTOPER.DEFAULT;
	private List<CompManSeatInfo> memSeatInfoList= new ArrayList<CompManSeatInfo>();
	
	
	public CompManTableInfo() {
		super();
	}
	public CompManTableInfo(int tableNO, int tableState, int tableType) {
		super();
		this.tableNO = tableNO;
		this.tableState = tableState;
		this.tableType = tableType;
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
	public int getTableState() {
		return tableState;
	}
	public void setTableState(int tableState) {
		this.tableState = tableState;
	}
	public int getButton() {
		return button;
	}
	public void setButton(int button) {
		this.button = button;
	}
	public List<CompManSeatInfo> getMemSeatInfoList() {
		return memSeatInfoList;
	}
}
