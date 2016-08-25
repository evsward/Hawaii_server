package com.evsward.server.dao;

import com.dance.core.orm.BaseDao;
import com.evsward.server.vo.AdvertCompRelation;

public interface CompAdvertDao extends BaseDao<AdvertCompRelation, Integer> {

	/**
	 * 添加比赛广告关系
	 * @param relation
	 * @return
	 * @throws Exception
	 */
	public int insertCompAdvertRelation(AdvertCompRelation relation)throws Exception;
	
	/**
	 * 删除比赛广告关联关系
	 * @param compID
	 * @return
	 * @throws Exception
	 */
	public int delCompAdvertRelation(int compID)throws Exception;
}
