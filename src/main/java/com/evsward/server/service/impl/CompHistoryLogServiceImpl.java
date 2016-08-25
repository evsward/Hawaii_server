package com.evsward.server.service.impl;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.dance.core.orm.BaseDao;
import com.dance.core.service.BaseServiceImpl;
import com.evsward.server.dao.CompHistoryLogDao;
import com.evsward.server.service.CompHistoryLogService;
import com.evsward.server.vo.CompMovedSeatLog;

@Component("compHistLogService")
public class CompHistoryLogServiceImpl extends BaseServiceImpl<CompMovedSeatLog, Integer> implements
		CompHistoryLogService {

	@Resource
	private CompHistoryLogDao compHisLogDao;
	
	@Override
	public BaseDao<CompMovedSeatLog, Integer> getDao() {
		return compHisLogDao;
	}

	/**
	 * 查询一个比赛的座位移动记录
	 * @param compID
	 * @return
	 * @throws Exception
	 */
	public Map<Integer, List<CompMovedSeatLog>> getMoveSeatsHistoryLog(int compID)throws Exception{
		//1、先查爆桌记录
		List<CompMovedSeatLog> burstLogList = this.compHisLogDao.getBurstTableHistoryLog(compID);
		//2、再查平衡记录
		List<CompMovedSeatLog> balanceLogList = this.compHisLogDao.getBalanceHistoryLog(compID);
		if(burstLogList == null){
			burstLogList = Collections.emptyList();
		}
		if(balanceLogList == null){
			balanceLogList = Collections.emptyList();
		}
		Map<Integer, List<CompMovedSeatLog>> map = new HashMap<Integer, List<CompMovedSeatLog>>();
		map.put(CompMovedSeatLog.OPTYPE.BURST, burstLogList);
		map.put(CompMovedSeatLog.OPTYPE.BALANCE, balanceLogList);
		return map;
	}
}
