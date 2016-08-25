package com.evsward.server.service;

import java.util.List;

import com.dance.core.service.BaseService;
import com.evsward.server.vo.AdvertInfo;

public interface AdvertInfoService extends BaseService<AdvertInfo, Integer> {

	/**
	 * 获取所有广告
	 * @param sysType
	 * @return
	 * @throws Exception
	 */
	public List<AdvertInfo> getAllAdvertInfoList(int sysType)throws Exception;
	
	/**
	 * 获取比赛设置的广告
	 * @param compID
	 * @return
	 * @throws Exception
	 */
	public AdvertInfo getAdvertInfoByComp(int compID)throws Exception;
	
	/**
	 * 为比赛设置广告
	 * @param compID
	 * @param advertID
	 * @param sysType
	 * @return
	 * @throws Exception
	 */
	public int setCompAdvertRelation(int compID, int advertID, int sysType)throws Exception;
}
