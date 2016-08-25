package com.evsward.server.dao;

import java.util.List;

import com.dance.core.orm.BaseDao;
import com.evsward.server.vo.CompMovedSeatLog;

public interface CompHistoryLogDao extends BaseDao<CompMovedSeatLog, Integer> {

	/**
	 * 查询一个比赛的爆桌记录
	 * @param compID
	 * @return
	 * @throws Exception
	 */
	public List<CompMovedSeatLog> getBurstTableHistoryLog(int compID)throws Exception;
	
	/**
	 * 查询一个比赛的平衡记录记录
	 * @param compID
	 * @return
	 * @throws Exception
	 */
	public List<CompMovedSeatLog> getBalanceHistoryLog(int compID)throws Exception;
}
