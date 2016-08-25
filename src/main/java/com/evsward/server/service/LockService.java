package com.evsward.server.service;

import com.dance.core.service.BaseService;
import com.evsward.server.vo.LockTable;

public interface LockService extends BaseService<LockTable, Integer> {

	public void getLock(int lockType)throws Exception;
}
