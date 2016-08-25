package com.evsward.server.dao.impl;

import java.util.List;

import org.springframework.stereotype.Component;

import com.dance.core.orm.mybatis.MyBatisDaoImpl;
import com.evsward.server.dao.RoundTempDao;
import com.evsward.server.vo.RoundTemplate;

@Component("roundTempDao")
public class RoundTempDaoImpl extends MyBatisDaoImpl<RoundTemplate, Integer> implements RoundTempDao {

	@Override
	public List<RoundTemplate> getAllRoundTempList(int sysType) throws Exception {
		return this.find("getAllRoundTempBySysType", sysType);
	}

	/**
	 * 查询所有盲注模板
	 * @return
	 * @throws Exception
	 */
	public List<RoundTemplate> getAllRoundTempList() throws Exception{
		return this.find("getAllRoundTemp", null);
	}
}
