package com.evsward.server.vo.complist;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 比赛列表显示比赛信息
 */
@SuppressWarnings("serial")
public class CompetitionInfo_Day implements Serializable {

	//比赛开始日期，格式：2015010-29 星期四
	private String day_week;
	private List<CompListInfo> compListInfo = new ArrayList<CompListInfo>();
	
	public String getDay_week() {
		return day_week;
	}

	public void setDay_week(String day_week) {
		this.day_week = day_week;
	}

	public List<CompListInfo> getCompListInfo() {
		return compListInfo;
	}

	public void setCompListInfo(List<CompListInfo> compListInfo) {
		this.compListInfo = compListInfo;
	}
}
