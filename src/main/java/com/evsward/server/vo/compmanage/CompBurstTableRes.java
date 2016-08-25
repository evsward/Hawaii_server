package com.evsward.server.vo.compmanage;

import java.util.List;

public class CompBurstTableRes {
	
	public interface RESCODE{
		public static final int RESCODE_1 = -1;
		public static final int RESCODE_2 = -2;
		public static final int RESCODE_3 = -3;
		
		public static final int RESCODE1 = 1;
	}

	private int resCode;
	private int compID;
	private List<BurstTableNewSeatInfo> newSeatInfoList = null;
	public int getResCode() {
		return resCode;
	}
	public void setResCode(int resCode) {
		this.resCode = resCode;
	}
	public int getCompID() {
		return compID;
	}
	public void setCompID(int compID) {
		this.compID = compID;
	}
	public List<BurstTableNewSeatInfo> getNewSeatInfoList() {
		return newSeatInfoList;
	}
	public void setNewSeatInfoList(List<BurstTableNewSeatInfo> newSeatInfoList) {
		this.newSeatInfoList = newSeatInfoList;
	}
	
}
