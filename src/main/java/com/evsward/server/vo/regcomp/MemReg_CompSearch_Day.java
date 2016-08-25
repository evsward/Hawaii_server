package com.evsward.server.vo.regcomp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 会员报名模块里面，显示比赛信息的vo
 */
@SuppressWarnings("serial")
public class MemReg_CompSearch_Day implements Serializable {

	//比赛开始日期，格式：2015010-29 星期四
	private String day_week;
	private List<MemReg_CompSearch_compInfo> list = new ArrayList<MemReg_CompSearch_compInfo>();
	
	public List<MemReg_CompSearch_compInfo> getList() {
		return list;
	}
	public void setList(List<MemReg_CompSearch_compInfo> list) {
		this.list = list;
	}
	public String getDay_week() {
		return day_week;
	}
	public void setDay_week(String day_week) {
		this.day_week = day_week;
	}
	public MemReg_CompSearch_Day(String day_week) {
		super();
		this.day_week = day_week;
	}
	public MemReg_CompSearch_Day() {
		super();
	}
	
}
