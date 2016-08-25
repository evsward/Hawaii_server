package com.evsward.server.service.impl;


import javax.annotation.Resource;
import org.springframework.stereotype.Component;
import com.dance.core.orm.BaseDao;
import com.dance.core.service.BaseServiceImpl;
import com.evsward.server.dao.RoundDao;
import com.evsward.server.service.RoundService;
import com.evsward.server.vo.Round;

@Component("roundService")
public class RoundServiceImpl extends BaseServiceImpl<Round, Integer> implements
		RoundService {

	@Resource
	private RoundDao roundDao;
	
	@Override
	public BaseDao<Round, Integer> getDao() {
		
		return roundDao;
	}

	/**
	 * 根据级别查询盲注
	 * @param roundTempID
	 * @param rank
	 * @param roundType
	 * @return
	 * @throws Exception
	 */
	public Round getRoundByRank(int roundTempID, int rank, int roundType)throws Exception{
		return this.roundDao.getRoundByRank(roundTempID, rank, roundType);
	}
}
