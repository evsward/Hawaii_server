package com.evsward.server.dao;

import java.util.List;

import com.dance.core.orm.BaseDao;
import com.evsward.server.vo.AdvertInfo;

public interface AdvertInfoDao extends BaseDao<AdvertInfo, Integer> {

	/**
	 * 查询所有广告信息
	 * @param sysType
	 * @return
	 * @throws Exception
	 */
	public List<AdvertInfo> getAllAdvertInfoList(int sysType)throws Exception;
	
	/**
	 * 查询比赛配置的广告信息
	 * @param compID
	 * @return
	 * @throws Exception
	 */
	public AdvertInfo getAdvertInfoByCompID(int compID)throws Exception;
}
