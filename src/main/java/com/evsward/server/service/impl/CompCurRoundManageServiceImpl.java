package com.evsward.server.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.dance.core.orm.BaseDao;
import com.dance.core.service.BaseServiceImpl;
import com.evsward.server.dao.CompRunningRoundDao;
import com.evsward.server.service.CompCurRoundMangeService;
import com.evsward.server.vo.CompRunningRound;

@Component("compCurRoundManService")
public class CompCurRoundManageServiceImpl extends
		BaseServiceImpl<CompRunningRound, Integer> implements
		CompCurRoundMangeService {

	@Resource
	private CompRunningRoundDao compCurRoundDao;
	
	@Override
	public BaseDao<CompRunningRound, Integer> getDao() {
		return compCurRoundDao;
	}

	/**
	 * 获取比赛当前运行盲注信息
	 * @param compID
	 * @return
	 * @throws Exception
	 */
	public CompRunningRound getCurRoundOfComp(int compID)throws Exception{
		return this.compCurRoundDao.getRunningRoundByCompID(compID);
	}
}
