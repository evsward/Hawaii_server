package com.evsward.server.dao;

import java.util.List;

import com.dance.core.orm.BaseDao;
import com.evsward.server.vo.RoundTemplate;

public interface RoundTempDao extends BaseDao<RoundTemplate, Integer> {

	/**
	 * 获取所有盲注模板列表
	 * @param sysType
	 * @return
	 * @throws Exception
	 */
	public List<RoundTemplate> getAllRoundTempList(int sysType)throws Exception;
	
	/**
	 * 查询所有盲注模板
	 * @param sysType
	 * @return
	 * @throws Exception
	 */
	public List<RoundTemplate> getAllRoundTempList() throws Exception;
}
