package com.evsward.server.service;

import java.util.List;

import com.dance.core.service.BaseService;
import com.evsward.server.vo.CompImportLog;

public interface CompImportLogService extends
		BaseService<CompImportLog, Integer> {

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
	
	/**
	 * 查询源比赛是否存在导入记录
	 * @param sourCompIDArr
	 * @return	false：不存在；true：存在
	 * @throws Exception
	 */
	public boolean existSourceCompImportedLogs(int[] sourCompIDArr)throws Exception;
	
	/**
	 * 查询目标比赛是否存在导入记录
	 * @param destCompID
	 * @return	false：不存在；true：存在
	 * @throws Exception
	 */
	public boolean existDestCompImportedLog(int destCompID)throws Exception;
}
