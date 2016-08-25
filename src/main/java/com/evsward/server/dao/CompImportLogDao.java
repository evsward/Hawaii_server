package com.evsward.server.dao;

import java.util.List;

import com.dance.core.orm.BaseDao;
import com.evsward.server.vo.CompImportLog;

public interface CompImportLogDao extends BaseDao<CompImportLog, Integer> {

	/**
	 * 查询源比赛导入记录
	 * @param sourCompIDArr
	 * @return
	 * @throws Exception
	 */
	public List<CompImportLog> getSourceCompImportedLogs(int[] sourCompIDArr)throws Exception;
	
	/**
	 * 查询目标比赛导入记录
	 * @param destCompID
	 * @return
	 * @throws Exception
	 */
	public CompImportLog getDestCompImportedLog(int destCompID)throws Exception;
}
