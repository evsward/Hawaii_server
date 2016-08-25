package com.evsward.server.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dance.core.orm.BaseDao;
import com.dance.core.service.BaseServiceImpl;
import com.evsward.server.dao.CardTableDao;
import com.evsward.server.service.CardTableService;
import com.evsward.server.vo.CardTable;

@Component("tableService")
public class CardTableServiceImpl extends BaseServiceImpl<CardTable, Integer> implements CardTableService{

	@Resource
	private CardTableDao tableDao;
	
	@Override
	public BaseDao<CardTable, Integer> getDao() {
		return tableDao;
	}

	@Override
	public void addTables(List<CardTable> tableList) throws Exception {
		this.tableDao.insert(tableList);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED, isolation=Isolation.DEFAULT)
	public void deleteTables(int sysType) throws Exception {
		this.tableDao.delAllTables(sysType);
		
	}
	
	/**
	 * 查看是否存在被占用的牌桌
	 * @param sysType
	 * @return
	 * @throws Exception
	 */
	public boolean existUsedTables(int sysType)throws Exception{
		List<CardTable> usedList = this.tableDao.findUsedTables(sysType);
		if(usedList != null && usedList.size() > 0){
			return true;
		}
		return false;
	}

	@Override
	public List<CardTable> listTables(int sysType) throws Exception {
		return this.tableDao.findAllTables(sysType);
	}

	@Override
	public boolean compExistTables(int compID) throws Exception {
		List<CardTable> tables = this.tableDao.getTablesByCompID(compID);
		if(tables == null || tables.size() <= 0){
			return false;
		}
		return true;
	}
	
	/**
	 * 获取比赛占用和空闲牌桌
	 * @param compID
	 * @param sysType
	 * @return
	 * @throws Exception
	 */
	public List<CardTable> getCompUsedAndIdleTables(int compID, int sysType)throws Exception{
		return this.tableDao.getCompUsedAndIdleTables(compID, sysType);
	}
	
	/**
	 * 桌号是否已经在被占用
	 * @param tableNOArr
	 * @return
	 * @throws Exception
	 */
	public boolean tablesIsUsing(int[] tableNOArr)throws Exception{
		if(tableNOArr == null || tableNOArr.length <= 0){
			return false;
		}
		List<CardTable> list = this.tableDao.getTablesBySpecTableNO(tableNOArr);
		if(list == null || list.size() <= 0){
			return false;
		}
		int size = list.size();
		CardTable table = null;
		for (int i = 0; i < size; i++) {
			table = list.get(i);
			if(table.getTableState() != CardTable.TABLESTATE.TABLEIDLE){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 获取比赛占用的牌桌
	 * @param compID
	 * @return
	 * @throws Exception
	 */
	public List<CardTable> getUsedTablesByCompID(int compID)throws Exception{
		return this.tableDao.getTablesByCompID(compID);
	}

//	@Override
//	public boolean releaseCompTables(int compID) throws Exception {
//		this.
//		return false;
//	}
//
//	@Override
//	public boolean releaseCompTable(int compID, int tableNO) throws Exception {
//		// TODO Auto-generated method stub
//		return false;
//	}

	
}
