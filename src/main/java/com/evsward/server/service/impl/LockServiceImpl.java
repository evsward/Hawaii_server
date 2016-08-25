package com.evsward.server.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dance.core.orm.BaseDao;
import com.dance.core.service.BaseServiceImpl;
import com.evsward.server.dao.LockDao;
import com.evsward.server.service.LockService;
import com.evsward.server.vo.LockTable;

@Component("prizeLockService")
public class LockServiceImpl extends BaseServiceImpl<LockTable, Integer> implements
		LockService {

	@Transactional(propagation=Propagation.REQUIRED, isolation=Isolation.DEFAULT)
	public void getLock(int lockType)throws Exception{
		this.lockDao.getLock(lockType);
		Thread.sleep(10000);
	}
	
	@Override
	public BaseDao<LockTable, Integer> getDao() {
		return lockDao;
	}

	@Resource
	private LockDao lockDao;
}
