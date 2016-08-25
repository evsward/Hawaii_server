package com.evsward.server.facade;

public interface PrizeFacade {

	/**
	 * 查询一个比赛对应的奖励区间信息
	 * @param compID
	 * @return
	 */
	public String getPrizeArea(int compID);
	
	/**
	 * 缓存奖励表信息
	 * @param prizeMap
	 * @throws Exception
	 */
	public void cachePrizeListMap()throws Exception;
}
