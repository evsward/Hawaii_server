package com.evsward.server.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.dance.core.orm.mybatis.MyBatisDaoImpl;
import com.evsward.server.dao.CompetitionDao;
import com.evsward.server.vo.CompetitionInfo;

@Component("compDao")
public class CompetitionDaoImpl extends MyBatisDaoImpl<CompetitionInfo, Integer>
		implements CompetitionDao {

	/**
	 * 比赛报名人数+1
	 * @param compID
	 * @return
	 * @throws Exception
	 */
	public int addRegedPlayer(int compID)throws Exception{
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("compID", compID);
		paramMap.put("updateTime", new Date());
		return this.update("addRegedPlayer", paramMap);
	}

	@Override
	public void createCompetition(CompetitionInfo compInfo) throws Exception {
		this.insert(compInfo);
	}

	@Override
	public CompetitionInfo getCompInfoByCompName(String compName) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("compName", compName);
		paramMap.put("compState", CompetitionInfo.COMPSTATE.STATE_DEL);
		return (CompetitionInfo)this.get("getCompInfoByName", paramMap);
	}
	
	@Override
	public CompetitionInfo getCompInfoByCompID(int compID)throws Exception{
		return (CompetitionInfo)this.get("getCompInfoByID", compID);
	}

	/**
	 * 获取所有比赛(包括已删除的比赛)
	 * @param sysType
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<CompetitionInfo> getAllCompetitionList(int sysType)throws Exception{
		return this.find("getAllCompetition", sysType);
	}
	
	/**
	 * 获取所有比赛(不包括已删除的比赛)
	 * @param sysType
	 * @return
	 */
	public List<CompetitionInfo> getAllCompetitionListNoDel(int sysType)throws Exception{
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("sysType", sysType);
		paramMap.put("compState", CompetitionInfo.COMPSTATE.STATE_DEL);
		return this.find("getAllCompetitionNoDel", paramMap);
	}

	@Override
	public void editCompetionInfoByID(CompetitionInfo compInfo)throws Exception{
		this.update("updateCompInfoByID", compInfo);
	}
	
	@Override
	public int delCompetitionByID(int compID)throws Exception{
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("compID", compID);
		paramMap.put("updateTime", new Date());
		paramMap.put("compState", CompetitionInfo.COMPSTATE.STATE_DEL);
		return this.delete("updateCompetitionState", paramMap);
	}
	
	/**
	 * 根据比赛ID暂停或开始比赛
	 * @param compID
	 * @return
	 * @throws Exception
	 */
	public int run2pauseCompetitionByID(int compID)throws Exception{
		Map<String, Object> paramMap = new HashMap<String, Object>();
		Date date = new Date();
		paramMap.put("compID", compID);
		paramMap.put("updateTime", date);
		paramMap.put("pauseTime", date);
		paramMap.put("compPause", CompetitionInfo.PAUSESTATE.MANUALPAUSE);
		return this.update("run2pauseCompByID", paramMap);
	}
	
	/**
	 * 根据比赛ID,由暂停变成开始比赛
	 * @param compID
	 * @return
	 * @throws Exception
	 */
	public int pause2runCompetitionByID(int compID)throws Exception{
		Map<String, Object> paramMap = new HashMap<String, Object>();
		Date date = new Date();
		paramMap.put("compID", compID);
		paramMap.put("updateTime", date);
		paramMap.put("compPause", CompetitionInfo.PAUSESTATE.NOPAUSE);
		return this.update("pause2runCompByID", paramMap);
	}
	
	/**
	 * 根据比赛ID结束比赛
	 * @param compID
	 * @return
	 * @throws Exception
	 */
	public int endCompetitionByID(int compID)throws Exception{
		Map<String, Object> paramMap = new HashMap<String, Object>();
		Date date = new Date();
		paramMap.put("compID", compID);
		paramMap.put("updateTime", date);
		paramMap.put("endTime", date);
		paramMap.put("compState", CompetitionInfo.COMPSTATE.STATE_END);
		return this.update("endCompByID", paramMap);
	}
	
	/**
	 * 查询时间段内未结束的比赛（按比赛开始时间）
	 * @param start
	 * @param end
	 * @param sysType
	 * @return
	 * @throws Exception
	 */
	public List<CompetitionInfo> getNoEndCompsInTimeAreaNoDelNoEnd(Date start, Date end, int sysType)throws Exception{
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("start", start);
		paramMap.put("end", end);
		paramMap.put("compStateArr", new int[]{CompetitionInfo.COMPSTATE.STATE_DEL, CompetitionInfo.COMPSTATE.STATE_END});
		paramMap.put("sysType", sysType);
		return this.find("getNoEndCompsInTimeAreaNoDelNoEnd", paramMap);
	}
	
	/**
	 * 更新比赛状态为开启报名
	 * @param compID
	 * @return
	 * @throws Exception
	 */
	public int openCompRegState(int compID)throws Exception{
		Map<String, Object> paramMap = new HashMap<String, Object>();
		Date d = new Date();
		paramMap.put("compID", compID);
		paramMap.put("openRegTime", d);
		paramMap.put("updateTime", d);
		paramMap.put("compState", CompetitionInfo.COMPSTATE.STATE_REGING_NOBEGIN);
		return this.update("openCompRegState", paramMap);
	}
	
	/**
	 * 编辑扣减选手数量，覆盖减
	 * @param compID
	 * @param subNum
	 * @return
	 */
	public int updateSubPlayer(int compID, int subNum)throws Exception{
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("compID", compID);
		paramMap.put("subPlayerCount", subNum);
		paramMap.put("updateTime", new Date());
		return this.update("updateSubPlayer", paramMap);
	}
	
	/**
	 * 比赛开始时间到，比赛开始，更新比赛状态compState=2或者compState=4
	 * @param compID
	 * @param newCompState
	 * @return
	 * @throws Exception
	 */
	public int startCompetition_Serv(int compID, int newCompState)throws Exception{
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("compID", compID);
		paramMap.put("compState", newCompState);
		paramMap.put("updateTime", new Date());
		return this.update("updateCompetitionState", paramMap);
	}
	
	/**
	 * 更新比赛截止报名状态compState=4
	 * @param compID
	 * @return
	 * @throws Exception
	 */
	public int endCompetitionReg_Serv(int compID)throws Exception{
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("compID", compID);
		paramMap.put("compState", CompetitionInfo.COMPSTATE.STATE_RUNNING);
		paramMap.put("updateTime", new Date());
		return this.update("updateCompetitionState", paramMap);
	}
	
	/**
	 * 
	 * @param sysType
	 * @return
	 * @throws Exception
	 */
	public List<CompetitionInfo> getOrigCompListForImport(int sysType)throws Exception{
		return this.find("getEndOrigAdvanCompList", sysType);
	}
	
	/**
	 * 
	 * @param sysType
	 * @return
	 * @throws Exception
	 */
	public List<CompetitionInfo> getDestListForImport(int sysType)throws Exception{
		return this.find("getDestCompList", sysType);
	}
	
	/**
	 * 晋级导入--更新目标比赛信息
	 * @param compInfo
	 * @return
	 * @throws Exception
	 */
	public int updateDestCompForImport(CompetitionInfo compInfo)throws Exception{
		return this.update("updateDestCompForImport", compInfo);
	}
	
	
	
	
	
	//---------------------------------competition server服务管理线程---------------------------------//
	/**
	 * 查询所有未结束，未删除的比赛，不包含盲注信息
	 * @param sysType
	 * @return
	 * @throws Exception
	 */
	public List<CompetitionInfo> getCompInfoListForInit_Serv(int sysType)throws Exception{
		return this.find("getCompInfoListForInit_Serv", sysType);
	}
}
