package com.evsward.server.action;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;

import com.dance.core.service.BaseService;
import com.dance.core.utils.web.StrutsAction;
import com.evsward.server.facade.CompetitionManageFacade;
import com.evsward.server.util.HIUtil;
import com.evsward.server.vo.CompMovedSeatLog;

/**
 * 
 */
@Namespace("/Logs/compLog")
@ParentPackage(value = "hi")
@SuppressWarnings("serial")
public class CompHistoryLogAction extends StrutsAction<CompMovedSeatLog, Integer> {

	/**
	 * 获取比赛位移日志记录
	 * @return
	 */
	@Action("getMoveSeatLogs")
	public String getMoveSeatLogList(){
		HIUtil.printRequestParam(this.getClass().getSimpleName(), "getMoveSeatLogList");
		HIUtil.sendResponseJson(this.compManFacade.getCompSeatMovedLogs(compID));
		return NONE;
	}
	
	
	@Override
	public CompMovedSeatLog getModel() {
		return null;
	}

	@Override
	public BaseService<CompMovedSeatLog, Integer> getService() {
		return null;
	}

	@Resource
	private CompetitionManageFacade compManFacade;
	
	private int compID;
	private String empUuid;
	private int sysType;
	
	public int getCompID() {
		return compID;
	}


	public void setCompID(int compID) {
		this.compID = compID;
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
}
