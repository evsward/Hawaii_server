package com.evsward.server.dao.impl;

import org.springframework.stereotype.Component;

import com.dance.core.orm.mybatis.MyBatisDaoImpl;
import com.evsward.server.dao.CompAdvertDao;
import com.evsward.server.vo.AdvertCompRelation;

@Component("compAdvertDao")
public class CompAdvertDaoImpl extends MyBatisDaoImpl<AdvertCompRelation, Integer> implements
		CompAdvertDao {

	/**
	 * 添加比赛广告关系
	 * @param relation
	 * @return
	 * @throws Exception
	 */
	public int insertCompAdvertRelation(AdvertCompRelation relation)throws Exception{
		return (Integer)this.insert("insert", relation);
	}
	
	/**
	 * 删除比赛广告关联关系
	 * @param compID
	 * @return
	 * @throws Exception
	 */
	public int delCompAdvertRelation(int compID)throws Exception{
		return this.delete("delByCompID", compID);
	}
}
