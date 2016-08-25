package com.evsward.server.dao;

import java.util.List;

import com.dance.core.orm.BaseDao;
import com.evsward.server.vo.CardTable;

public interface CardTableDao extends BaseDao<CardTable, Integer>{

	/**
	 * 删除所有牌桌
	 * @param sysType
	 * @throws Exception
	 */
	public void delAllTables(int sysType)throws Exception;
	
	/**
	 * 查询所有牌桌
	 * @param sysType
	 * @return
	 * @throws Exception
	 */
	public List<CardTable> findAllTables(int sysType)throws Exception;
	
	/**
	 * 查询被占用的所有牌桌
	 * @param sysType
	 * @return
	 * @throws Exception
	 */
	public List<CardTable> findUsedTables(int sysType)throws Exception;
	
	/**
	 * 释放比赛占用的所有牌桌
	 * @param compID
	 * @return
	 * @throws Exception
	 */
	public int releaseCompTables(int compID)throws Exception;
	
	/**
	 * 释放比赛的单个牌桌
	 * @param compID
	 * @param tableNO
	 * @return
	 * @throws Exception
	 */
	public int releaseCompTable(int compID, int tableNO)throws Exception;
	
	/**
	 * 获取比赛开启的牌桌信息
	 * @param compID
	 * @return
	 * @throws Exception
	 */
	public List<CardTable> getTablesByCompID(int compID)throws Exception;
	
	/**
	 * 查询剩余空闲的牌桌。
	 * @param sysType
	 * @return
	 * @throws Exception
	 */
	public List<CardTable> getAllIdleTables(int sysType)throws Exception;
	
	/**
	 * 查询比赛占用和空闲的牌桌。
	 * @param compID
	 * @param sysType
	 * @return
	 * @throws Exception
	 */
	public List<CardTable> getCompUsedAndIdleTables(int compID, int sysType)throws Exception;
	
	/**
	 * 获取指定牌桌
	 * @param tableNOArr
	 * @return
	 * @throws Exception
	 */
	public List<CardTable> getTablesBySpecTableNO(int[] tableNOArr)throws Exception;
	
	/**
	 * 开启比赛牌桌
	 * @param compID
	 * @param tableNOArr
	 * @param compName
	 * @return
	 * @throws Exception
	 */
	public int openTablesByCompID(int compID, int[] tableNOArr, String compName)throws Exception;
	
	/**
	 * 根据桌号查询牌桌
	 * @param tableNO
	 * @return
	 * @throws Exception
	 */
	public CardTable getTableByTableNO(int tableNO)throws Exception;
}
