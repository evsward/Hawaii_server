package com.evsward.server.dao;

import com.dance.core.orm.BaseDao;
import com.evsward.server.vo.LockTable;

public interface LockDao extends BaseDao<LockTable, Integer> {

	public LockTable getLock(int lockType)throws Exception;
}
