package com.evsward.server.action;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;

import com.dance.core.service.BaseService;
import com.dance.core.utils.web.StrutsAction;
import com.evsward.server.facade.CompetitionManageFacade;
import com.evsward.server.facade.MemberManageFacade;
import com.evsward.server.util.HIUtil;

/**
 * 入场安检
 */
@Namespace("/check/welcome")
@ParentPackage(value = "hi")
@SuppressWarnings({ "serial", "rawtypes" })
public class CheckWelcomeAction extends StrutsAction{

	@Override
	public Object getModel() {
		return null;
	}

	@Override
	public BaseService getService() {
		return null;
	}

	@Action("checkWelcome")
	public String findMemRegedComp(){
		HIUtil.printRequestParam(this.getClass().getSimpleName(), "findMemRegedComp");
		this.compManageFacade.welcome(memID, "");//入场欢迎
		HIUtil.sendResponseJson(memManageFacade.findMemCompInfoByMemID(memID, sysType));
		return NONE;
	}
	
	@Resource
	private MemberManageFacade memManageFacade;
	@Resource
	private CompetitionManageFacade compManageFacade;
	
	private int memID;
	private int sysType;
	private String empUuid;

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

	public int getMemID() {
		return memID;
	}

	public void setMemID(int memID) {
		this.memID = memID;
	}
}
