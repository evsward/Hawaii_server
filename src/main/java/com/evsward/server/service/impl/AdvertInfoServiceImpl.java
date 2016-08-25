package com.evsward.server.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.dance.core.orm.BaseDao;
import com.dance.core.service.BaseServiceImpl;
import com.evsward.server.dao.AdvertInfoDao;
import com.evsward.server.dao.CompAdvertDao;
import com.evsward.server.vo.AdvertCompRelation;
import com.evsward.server.vo.AdvertInfo;

@Component("advertService")
public class AdvertInfoServiceImpl extends BaseServiceImpl<AdvertInfo, Integer> implements
		com.evsward.server.service.AdvertInfoService {

	private static Logger logger = LoggerFactory.getLogger(AdvertInfoServiceImpl.class);

	@Resource
	private AdvertInfoDao advertDao;
	@Resource
	private CompAdvertDao compAdvertDao;
	
	@Override
	public BaseDao<AdvertInfo, Integer> getDao() {
		
		return advertDao;
	}
	
	/**
	 * 获取所有广告
	 * @return
	 * @throws Exception
	 */
	public List<AdvertInfo> getAllAdvertInfoList(int sysType)throws Exception{
		return this.advertDao.getAllAdvertInfoList(sysType);
	}
	
	/**
	 * 获取比赛设置的广告
	 * @param compID
	 * @return
	 * @throws Exception
	 */
	public AdvertInfo getAdvertInfoByComp(int compID)throws Exception{
		return this.advertDao.getAdvertInfoByCompID(compID);
	}
	
	/**
	 * 为比赛设置广告
	 * @param compID
	 * @param advertID
	 * @return
	 * @throws Exception
	 */
	public int setCompAdvertRelation(int compID, int advertID, int sysType)throws Exception{
		AdvertCompRelation relation = new AdvertCompRelation(advertID, compID, sysType);
		this.compAdvertDao.delCompAdvertRelation(compID);
		return this.compAdvertDao.insertCompAdvertRelation(relation);
	}
}
