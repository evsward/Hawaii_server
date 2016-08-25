package com.evsward.server.service;

import com.dance.core.service.BaseService;
import com.evsward.server.vo.CompRunningRound;

public interface CompCurRoundMangeService extends BaseService<CompRunningRound, Integer> {

	/**
	 * 获取比赛当前运行盲注信息
	 * @param compID
	 * @return
	 * @throws Exception
	 */
	public CompRunningRound getCurRoundOfComp(int compID)throws Exception;
}
