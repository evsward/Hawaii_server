package com.evsward.server.service;

import com.dance.core.service.BaseService;
import com.evsward.server.vo.Round;

public interface RoundService extends BaseService<Round, Integer> {

	/**
	 * 根据级别查询盲注
	 * @param roundTempID
	 * @param rank
	 * @param roundType
	 * @return
	 * @throws Exception
	 */
	public Round getRoundByRank(int roundTempID, int rank, int roundType)throws Exception;
}
