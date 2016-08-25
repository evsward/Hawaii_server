package com.evsward.server.service;

import java.util.List;

import com.dance.core.service.BaseService;
import com.evsward.server.vo.CardTable;

public interface CardTableService extends BaseService<CardTable, Integer> {

	/**
	 * 添加牌桌
	 * @param tableList
	 * @throws Exception
	 */
	public void addTables(List<CardTable> tableList)throws Exception;
	
	/**
	 * 删除所有牌桌
	 * @param sysType
	 * @throws Exception
	 */
	public void deleteTables(int sysType)throws Exception;

	/**
	 * 查看所有牌桌
	 * @param sysType
	 * @return
	 * @throws Exception
	 */
	public List<CardTable> listTables(int sysType)throws Exception;
	
	/**
	 * 比赛还存在没有释放的牌桌
	 * @param compID
	 * @return
	 * @throws Exception
	 */
	public boolean compExistTables(int compID)throws Exception;
	
	/**
	 * 查看是否存在被占用的牌桌
	 * @param sysType
	 * @return
	 * @throws Exception
	 */
	public boolean existUsedTables(int sysType)throws Exception;
	
	/**
	 * 获取比赛占用和空闲牌桌
	 * @param compID
	 * @param sysType
	 * @return
	 * @throws Exception
	 */
	public List<CardTable> getCompUsedAndIdleTables(int compID, int sysType)throws Exception;
	
	/**
	 * 桌号是否已经在被占用,如果有多个桌号，则其中一个被占用就返回true,都未被占用则返回false
	 * @param tableNOArr
	 * @return
	 * @throws Exception
	 */
	public boolean tablesIsUsing(int[] tableNOArr)throws Exception;
	
	/**
	 * 获取比赛占用的牌桌
	 * @param compID
	 * @return
	 * @throws Exception
	 */
	public List<CardTable> getUsedTablesByCompID(int compID)throws Exception;
	
//	/**
//	 * 释放比赛占用的所有牌桌
//	 * @param compID
//	 * @return
//	 * @throws Exception
//	 */
//	public boolean releaseCompTables(int compID)throws Exception;
//	
//	/**
//	 * 释放比赛的单个牌桌
//	 * @param compID
//	 * @param tableNO
//	 * @return
//	 * @throws Exception
//	 */
//	public boolean releaseCompTable(int compID, int tableNO)throws Exception;
}
