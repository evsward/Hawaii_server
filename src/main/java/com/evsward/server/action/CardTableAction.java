package com.evsward.server.action;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;

import com.dance.core.service.BaseService;
import com.dance.core.utils.web.StrutsAction;
import com.evsward.server.facade.CardTableManageFacade;
import com.evsward.server.util.RspCodeValue;
import com.evsward.server.util.HIUtil;
import com.evsward.server.vo.CardTable;

@SuppressWarnings("serial")
@Namespace("/cardTable/cardTable")
@ParentPackage(value = "hi")
public class CardTableAction extends StrutsAction<CardTable, Integer> {

	@Override
	public CardTable getModel() {
		
		return null;
	}

	@Override
	public BaseService<CardTable, Integer> getService() {
		
		return null;
	}

	@Action("addTables")
	public String addTables(){
		if(this.getActionErrors().size() > 0){
			HIUtil.sendResponseJson(HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$11.getRspCode(), RspCodeValue.$11.getMsg())));
			return NONE;
		}
		HIUtil.printRequestParam(this.getClass().getSimpleName(), "addTables");
		HIUtil.sendResponseJson(this.tableManageFacade.addTables(address, tableCount, sysType, empUuid));
		return NONE;
	}
	
	@Action("delTables")
	public String delTables(){
		HIUtil.printRequestParam(this.getClass().getSimpleName(), "delTables");
		HIUtil.sendResponseJson(this.tableManageFacade.delTables(sysType, empUuid));
		return NONE;
	}
	
	@Action("listTables")
	public String listTables(){
		HIUtil.printRequestParam(this.getClass().getSimpleName(), "listTables");
		HIUtil.sendResponseJson(this.tableManageFacade.listTables(sysType));
		return NONE;
	}
	
	public void validateAddTables(){
		if(StringUtils.isEmpty(this.address) || StringUtils.isEmpty(this.empUuid) || this.tableCount <= 0){
			this.addActionError(RspCodeValue.$11.getMsg());
		}
	}
	
	public void validateDelTables(){
		if(StringUtils.isEmpty(this.empUuid)){
			this.addActionError(RspCodeValue.$11.getMsg());
		}
	}
	
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getTableCount() {
		return tableCount;
	}

	public void setTableCount(int tableCount) {
		this.tableCount = tableCount;
	}
	
	public String getEmpUuid() {
		return empUuid;
	}

	public void setEmpUuid(String empUuid) {
		this.empUuid = empUuid;
	}
	
	public int getSysType() {
		return sysType;
	}

	public void setSysType(int sysType) {
		this.sysType = sysType;
	}
	
	private String address;
	private int tableCount;
	private int sysType;
	private String empUuid;
	

	@Resource
	private CardTableManageFacade tableManageFacade;
}
