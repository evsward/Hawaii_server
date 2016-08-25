package com.evsward.server.dao.impl;

import org.springframework.stereotype.Component;

import com.dance.core.orm.mybatis.MyBatisDaoImpl;
import com.evsward.server.dao.LockDao;
import com.evsward.server.vo.LockTable;

@Component("lockDao")
public class LockDaoImpl extends MyBatisDaoImpl<LockTable, Integer> implements LockDao {

	public LockTable getLock(int lockType)throws Exception{
		return (LockTable)this.get("getLock", lockType);
	}
}
