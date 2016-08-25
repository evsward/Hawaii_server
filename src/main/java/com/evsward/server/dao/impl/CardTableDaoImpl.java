package com.evsward.server.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.dance.core.orm.mybatis.MyBatisDaoImpl;
import com.evsward.server.dao.CardTableDao;
import com.evsward.server.vo.CardTable;

@Component("tableDao")
public class CardTableDaoImpl extends MyBatisDaoImpl<CardTable, Integer>
		implements CardTableDao {

	@Override
	public void delAllTables(int sysType) throws Exception {
		this.delete("delAll", sysType);
		
	}

	@Override
	public List<CardTable> findAllTables(int sysType) throws Exception {
		return this.find("findAll", sysType);
	}
	
	/**
	 * 查询被占用的所有牌桌
	 * @param sysType
	 * @return
	 * @throws Exception
	 */
	public List<CardTable> findUsedTables(int sysType)throws Exception{
		return this.find("findAllUsedTables", sysType);
	}

	@Override
	public int releaseCompTables(int compID) throws Exception {
		int num = this.update("releaseCompTables", compID);
		return num;
	}

	@Override
	public int releaseCompTable(int compID, int tableNO) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("compID", compID);
		paramMap.put("tableNO", tableNO);
		int num = this.update("releaseCompTable", paramMap);
		return num;
	}

	@Override
	public List<CardTable> getTablesByCompID(int compID) throws Exception {
		List<CardTable> tables = this.find("getTablesByCompID", compID);
		return tables;
	}
	
	/**
	 * 查询剩余空闲的牌桌。
	 * @param sysType
	 * @return
	 * @throws Exception
	 */
	public List<CardTable> getAllIdleTables(int sysType)throws Exception{
		return this.find("getAllIdleTables", sysType);
	}
	
	/**
	 * 查询比赛占用和空闲的牌桌。
	 * @param compID
	 * @param sysType
	 * @return
	 * @throws Exception
	 */
	public List<CardTable> getCompUsedAndIdleTables(int compID, int sysType)throws Exception{
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("compID", compID);
		param.put("sysType", sysType);
		return this.find("getCompUsedAndIdleTables", param);
	}
	
	/**
	 * 获取指定牌桌
	 * @param tableNOArr
	 * @return
	 * @throws Exception
	 */
	public List<CardTable> getTablesBySpecTableNO(int[] tableNOArr)throws Exception{
		return this.find("getTablesBySpecTableNO", tableNOArr);
	}
	
	/**
	 * 开启比赛牌桌
	 * @param compID
	 * @param tableNOArr
	 * @param compName
	 * @return
	 * @throws Exception
	 */
	public int openTablesByCompID(int compID, int[] tableNOArr, String compName)throws Exception{
		if(tableNOArr == null || tableNOArr.length <= 0){
			return 0;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("compID", compID);
		map.put("compName", compName);
		map.put("list", tableNOArr);
		return this.update("openTablesByCompID", map);
	}
	
	/**
	 * 根据桌号查询牌桌
	 * @param tableNO
	 * @return
	 * @throws Exception
	 */
	public CardTable getTableByTableNO(int tableNO)throws Exception{
		return (CardTable)this.get("getTableByTableNO", tableNO);
	}
}
