package com.evsward.server.facade;

public interface AdvertInfoFacade {

	/**
	 * 查询所有广告图片以及比赛和广告的关系
	 * @param compID
	 * @param sysType
	 * @return
	 */
	public String getAdvertInfoByCompWithAll(int compID, int sysType);
	
	/**
	 * 为比赛添加广告
	 * @param compID
	 * @param advertID
	 * @param sysType
	 * @return
	 */
	public String addAdvertForComp(int compID, int advertID, int sysType);
}
