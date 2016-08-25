package com.evsward.server.dao.impl;

import java.util.List;

import org.springframework.stereotype.Component;

import com.dance.core.orm.mybatis.MyBatisDaoImpl;
import com.evsward.server.vo.AdvertInfo;

@Component("advertDao")
public class AdvertInfoDaoImpl extends MyBatisDaoImpl<AdvertInfo, Integer>
		implements com.evsward.server.dao.AdvertInfoDao {
	
	public List<AdvertInfo> getAllAdvertInfoList(int sysType)throws Exception{
		return this.find("getAllAdvertInfoList", sysType);
	}
	
	/**
	 * 查询比赛配置的广告信息
	 * @param compID
	 * @return
	 * @throws Exception
	 */
	public AdvertInfo getAdvertInfoByCompID(int compID)throws Exception{
		return (AdvertInfo)this.get("getAdvertInfoByCompID", compID);
	}
}
