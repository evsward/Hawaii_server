package com.evsward.server.facade.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.evsward.server.facade.CardTableManageFacade;
import com.evsward.server.service.CardTableService;
import com.evsward.server.util.RspCodeValue;
import com.evsward.server.util.HIUtil;
import com.evsward.server.vo.CardTable;

@Component("tableManageFacade")
public class CardTableManageFacadeImpl implements CardTableManageFacade {

	private static Logger logger = LoggerFactory.getLogger(CardTableManageFacadeImpl.class);
	
	@Resource
	private CardTableService tableService;

	@Override
	public String addTables(String address, int tableCount, int sysType, String empUuid) {
		CardTable table = null;
		List<CardTable> tableList = new ArrayList<CardTable>();
		List<CardTable> tableList1 = null;
		try {
			tableList1 = this.tableService.listTables(sysType);
			if(tableList1 != null && tableList1.size() > 0){
				return HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$350.getRspCode(), RspCodeValue.$350.getMsg()));
			}
		} catch (Exception e1) {
			logger.error(e1.getMessage(), e1);
			return HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$350.getRspCode(), RspCodeValue.$350.getMsg()));
		}
		String jsonStr = "";
		long uuidLong = HIUtil.convertHex2Long(empUuid);
		for (int i = 1; i <= tableCount; i++) {
			table = new CardTable(i, 0, "", CardTable.TABLESTATE.TABLEIDLE, uuidLong, address, sysType);
			tableList.add(table);
		}
		try {
			this.tableService.addTables(tableList);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$350.getRspCode(), RspCodeValue.$350.getMsg()));
		}
		jsonStr = HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$1.getRspCode(), RspCodeValue.$1.getMsg()));
		return jsonStr;
	}

	@Override
	public String delTables(int sysType, String empUuid) {
		String jsonStr = "";
		boolean existUsedTables = true;
		try {
			existUsedTables = this.tableService.existUsedTables(sysType);
			if(!existUsedTables){
				this.tableService.deleteTables(sysType);
			}else{
				return HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$357.getRspCode(), RspCodeValue.$357.getMsg()));
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$351.getRspCode(), RspCodeValue.$351.getMsg()));
		}
		jsonStr = HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$1.getRspCode(), RspCodeValue.$1.getMsg()));
		return jsonStr;
	}

	@Override
	public String listTables(int sysType) {
		List<CardTable> tableList = null;
		String jsonStr = "";
		try {
			tableList = this.tableService.listTables(sysType);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$352.getRspCode(), RspCodeValue.$352.getMsg()));
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("cardTables", tableList);
		jsonStr = HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$1.getRspCode(), RspCodeValue.$1.getMsg(), map));
		return jsonStr;
	}
}
