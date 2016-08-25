package com.evsward.server.service;

import java.util.List;
import java.util.Map;

import com.dance.core.service.BaseService;
import com.evsward.server.vo.CompMovedSeatLog;

public interface CompHistoryLogService extends BaseService<CompMovedSeatLog, Integer> {

	/**
	 * 查询一个比赛的座位移动记录
	 * @param compID
	 * @return
	 * @throws Exception
	 */
	public Map<Integer, List<CompMovedSeatLog>> getMoveSeatsHistoryLog(int compID)throws Exception;
}
