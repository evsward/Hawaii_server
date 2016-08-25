package com.evsward.server.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.dance.core.orm.BaseDao;
import com.dance.core.service.BaseServiceImpl;
import com.evsward.server.dao.CompImportLogDao;
import com.evsward.server.service.CompImportLogService;
import com.evsward.server.vo.CompImportLog;

@Component("compImportService")
public class CompImportLogServiceImpl extends
		BaseServiceImpl<CompImportLog, Integer> implements CompImportLogService {

	@Resource
	private CompImportLogDao compImportLogDao;
	
	@Override
	public BaseDao<CompImportLog, Integer> getDao() {
		return compImportLogDao;
	}

	/**
	 * 查询源比赛导入记录
	 * @param sourCompIDArr
	 * @return
	 * @throws Exception
	 */
	public List<CompImportLog> getSourceCompImportedLogs(int[] sourCompIDArr)throws Exception{
		return this.compImportLogDao.getSourceCompImportedLogs(sourCompIDArr);
	}
	
	/**
	 * 查询目标比赛导入记录
	 * @param destCompID
	 * @return
	 * @throws Exception
	 */
	public CompImportLog getDestCompImportedLog(int destCompID)throws Exception{
		return this.compImportLogDao.getDestCompImportedLog(destCompID);
	}

	@Override
	public boolean existSourceCompImportedLogs(int[] sourCompIDArr)
			throws Exception {
		List<CompImportLog> list = this.compImportLogDao.getSourceCompImportedLogs(sourCompIDArr);
		if(list == null || list.isEmpty()){
			return false;
		}
		return true;
	}

	@Override
	public boolean existDestCompImportedLog(int destCompID) throws Exception {
		CompImportLog log = this.compImportLogDao.getDestCompImportedLog(destCompID);
		if(log == null){
			return false;
		}
		return true;
	}
}
