package com.evsward.server.dao.impl;

import java.util.List;

import org.springframework.stereotype.Component;

import com.dance.core.orm.mybatis.MyBatisDaoImpl;
import com.evsward.server.dao.CompHistoryLogDao;
import com.evsward.server.vo.CompMovedSeatLog;

@Component("compHisLogDao")
public class CompHistoryLogDaoImpl extends MyBatisDaoImpl<CompMovedSeatLog, Integer> implements
		CompHistoryLogDao {


	@Override
	public List<CompMovedSeatLog> getBurstTableHistoryLog(
			int compID) throws Exception {
		return this.find("getBurstTableHistoryLog", compID);
	}

	@Override
	public List<CompMovedSeatLog> getBalanceHistoryLog(
			int compID) throws Exception {
		return this.find("getBalanceHistoryLog", compID);
	}
}
