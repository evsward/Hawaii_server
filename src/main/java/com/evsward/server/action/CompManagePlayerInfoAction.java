package com.evsward.server.action;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dance.core.service.BaseService;
import com.dance.core.utils.web.StrutsAction;
import com.evsward.server.facade.CompetitionManageFacade;
import com.evsward.server.util.HIUtil;
import com.evsward.server.vo.CompetitionInfo;

@SuppressWarnings("serial")
@Namespace("/compManage/playerManage")
@ParentPackage(value = "hi")
public class CompManagePlayerInfoAction extends StrutsAction<CompetitionInfo, Integer>{

	private static Logger logger = LoggerFactory.getLogger(CompManagePlayerInfoAction.class);
	
	/**
	 * 获取比赛里存活的所有选手（包括晋级）
	 * @return
	 */
	@Action("getLivedPlayers")
	public String getLivedPlayersByComp(){
		HIUtil.printRequestParam(this.getClass().getSimpleName(), "getLivedPlayersByComp");
		HIUtil.sendResponseJson(this.compManageFacade.getLivedPlayersInfoByComp(this.compID));
		return NONE;
	}
	
	public int getCompID() {
		return compID;
	}

	public void setCompID(int compID) {
		this.compID = compID;
	}

	public int getSysType() {
		return sysType;
	}

	public void setSysType(int sysType) {
		this.sysType = sysType;
	}

	public String getEmpUuid() {
		return empUuid;
	}

	public void setEmpUuid(String empUuid) {
		this.empUuid = empUuid;
	}

	@Override
	public CompetitionInfo getModel() {
		return null;
	}

	@Override
	public BaseService<CompetitionInfo, Integer> getService() {
		return null;
	}
	
	@Resource
	private CompetitionManageFacade compManageFacade;
	
	private int compID;
	private int sysType;
	private String empUuid;
}
