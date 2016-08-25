package com.evsward.server.dao;

import java.util.Date;
import java.util.List;

import com.dance.core.orm.BaseDao;
import com.evsward.server.vo.CompetitionInfo;

public interface CompetitionDao extends BaseDao<CompetitionInfo, Integer>{

	/**
	 * 比赛报名人数+1
	 * @param compID
	 * @return
	 * @throws Exception
	 */
	public int addRegedPlayer(int compID)throws Exception;
	
	/**
	 * 建立新比赛
	 * @param compInfo
	 * @throws Exception
	 */
	public void createCompetition(CompetitionInfo compInfo)throws Exception;
	
	/**
	 * 通过比赛名称查询比赛信息
	 * @param compName
	 * @throws Exception
	 */
	public CompetitionInfo getCompInfoByCompName(String compName)throws Exception;
	
	/**
	 * 根据比赛ID查询比赛信息
	 * @param compID
	 * @return
	 * @throws Exception
	 */
	public CompetitionInfo getCompInfoByCompID(int compID)throws Exception;
	
	/**
	 * 获取所有比赛(包括已删除的比赛)
	 * @param sysType
	 * @return
	 * @throws Exception
	 */
	public List<CompetitionInfo> getAllCompetitionList(int sysType)throws Exception;
	
	/**
	 * 获取所有比赛(不包括已删除的比赛)
	 * @param sysType
	 * @return
	 */
	public List<CompetitionInfo> getAllCompetitionListNoDel(int sysType)throws Exception;
	
	/**
	 * 根据比赛ID编辑比赛信息
	 * @param compInfo
	 * @throws Exception
	 */
	public void editCompetionInfoByID(CompetitionInfo compInfo)throws Exception;
	
	/**
	 * 根据比赛ID逻辑删除比赛
	 * @param compID
	 * @return
	 * @throws Exception
	 */
	public int delCompetitionByID(int compID)throws Exception;
	
	/**
	 * 根据比赛ID暂停比赛
	 * @param compID
	 * @return
	 * @throws Exception
	 */
	public int run2pauseCompetitionByID(int compID)throws Exception;
	
	/**
	 * 根据比赛ID,由暂停变成开始比赛
	 * @param compID
	 * @return
	 * @throws Exception
	 */
	public int pause2runCompetitionByID(int compID)throws Exception;
	
	/**
	 * 根据比赛ID结束比赛
	 * @param compID
	 * @return
	 * @throws Exception
	 */
	public int endCompetitionByID(int compID)throws Exception;
	
	/**
	 * 查询时间段内未结束的比赛
	 * @param start
	 * @param end
	 * @param sysType
	 * @return
	 * @throws Exception
	 */
	public List<CompetitionInfo> getNoEndCompsInTimeAreaNoDelNoEnd(Date start, Date end, int sysType)throws Exception;
	
	/**
	 * 更新比赛状态为开启报名
	 * @param compID
	 * @return
	 * @throws Exception
	 */
	public int openCompRegState(int compID)throws Exception;
	
	/**
	 * 编辑扣减选手数量，覆盖减
	 * @param compID
	 * @param subNum
	 * @return
	 */
	public int updateSubPlayer(int compID, int subNum)throws Exception;
	
	/**
	 * 比赛开始时间到，比赛开始，更新比赛状态compState=2或者compState=4
	 * @param compID
	 * @param newCompState
	 * @return
	 * @throws Exception
	 */
	public int startCompetition_Serv(int compID, int newCompState)throws Exception;
	
	/**
	 * 更新比赛截止报名状态compState=4
	 * @param compID
	 * @return
	 * @throws Exception
	 */
	public int endCompetitionReg_Serv(int compID)throws Exception;
	
	/**
	 * 
	 * @param sysType
	 * @return
	 * @throws Exception
	 */
	public List<CompetitionInfo> getOrigCompListForImport(int sysType)throws Exception;
	
	/**
	 * 
	 * @param sysType
	 * @return
	 * @throws Exception
	 */
	public List<CompetitionInfo> getDestListForImport(int sysType)throws Exception;
	
	/**
	 * 晋级导入--更新目标比赛信息
	 * @param compInfo
	 * @return
	 * @throws Exception
	 */
	public int updateDestCompForImport(CompetitionInfo compInfo)throws Exception;
	
	
	
	
	//---------------------------------competition server服务管理线程---------------------------------//
	/**
	 * 查询所有未结束，未删除的比赛，不包含盲注信息
	 * @param sysType
	 * @return
	 * @throws Exception
	 */
	public List<CompetitionInfo> getCompInfoListForInit_Serv(int sysType)throws Exception;

}
