package com.evsward.server.action;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;

import com.dance.core.service.BaseService;
import com.dance.core.utils.web.StrutsAction;
import com.evsward.server.facade.RoundTempFacade;
import com.evsward.server.util.HIUtil;
import com.evsward.server.vo.RoundTemplate;

@SuppressWarnings("serial")
@Namespace("/roundTemp/roundTemp")
@ParentPackage(value = "hi")
public class RoundTempAction extends StrutsAction<RoundTemplate, Integer> {

	@Override
	public RoundTemplate getModel() {
		return null;
	}

	@Override
	public BaseService<RoundTemplate, Integer> getService() {
		return null;
	}
	
	@Action("getRoundTempList")
	public String getAllRoundTemp(){
		HIUtil.printRequestParam(this.getClass().getSimpleName(), "getAllRoundTemp");
		HIUtil.sendResponseJson(this.roundTempFacade.getAllRoundTempList(sysType));
		return NONE;
	}
	
	public String getEmpUuid() {
		return empUuid;
	}

	public void setEmpUuid(String empUuid) {
		this.empUuid = empUuid;
	}

	@Resource
	private RoundTempFacade roundTempFacade;
	private int sysType;
	public int getSysType() {
		return sysType;
	}

	public void setSysType(int sysType) {
		this.sysType = sysType;
	}

	private String empUuid;
	
}
