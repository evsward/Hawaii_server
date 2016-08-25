package com.evsward.server.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.dance.core.orm.mybatis.MyBatisDaoImpl;
import com.evsward.server.dao.CompImportLogDao;
import com.evsward.server.vo.CompImportLog;

@Component("compImportLogDao")
public class CompImportLogDaoImpl extends
		MyBatisDaoImpl<CompImportLog, Integer> implements CompImportLogDao {

	/**
	 * 查询源比赛导入记录
	 * @param sourCompIDArr
	 * @return
	 * @throws Exception
	 */
	public List<CompImportLog> getSourceCompImportedLogs(int[] sourCompIDArr)throws Exception{
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("sourceCompIDArr", sourCompIDArr);
		return this.find("getSourceCompImportedLog", paramMap);
	}
	
	/**
	 * 查询目标比赛导入记录
	 * @param destCompID
	 * @return
	 * @throws Exception
	 */
	public CompImportLog getDestCompImportedLog(int destCompID)throws Exception{
		return (CompImportLog)this.get("getDestCompImportedLog", destCompID);
	}
}
