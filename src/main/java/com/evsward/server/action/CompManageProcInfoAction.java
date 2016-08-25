package com.evsward.server.action;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;

import com.dance.core.service.BaseService;
import com.dance.core.utils.web.StrutsAction;
import com.evsward.server.facade.CompetitionManageFacade;
import com.evsward.server.util.HIUtil;
import com.evsward.server.vo.CompetitionInfo;

@SuppressWarnings("serial")
@Namespace("/compManage/procManage")
@ParentPackage(value = "hi")
public class CompManageProcInfoAction extends StrutsAction<CompetitionInfo, Integer>{

	@Action("compProcInfo")
	public String compProcInfoTest()throws Exception{
		HIUtil.printRequestParam(this.getClass().getSimpleName(), "compProcInfoTest");
		HIUtil.sendResponseJson(compManageFacade.getCompRunningInfo(compID));
		return NONE;
	}
	
	/**
	 * 手动暂停或开始比赛
	 * @return
	 */
	@Action("pauseCompetition")
	public String manualPauseCompetition(){
		HIUtil.printRequestParam(this.getClass().getSimpleName(), "manualPauseCompetition");
		HIUtil.sendResponseJson(this.compManageFacade.manualPauseCompetition(compID));
		return NONE;
	}
	
	/**
	 * 后退一级盲注
	 * @return
	 */
	@Action("goBack")
	public String goBackOneRank(){
		HIUtil.printRequestParam(this.getClass().getSimpleName(), "goBackOneRank");
		HIUtil.sendResponseJson(this.compManageFacade.goBackRound(compID, curRank));
		return NONE;
	}
	
	/**
	 * 前进一级盲注
	 * @return
	 */
	@Action("goForward")
	public String goForwardOneRank(){
		HIUtil.printRequestParam(this.getClass().getSimpleName(), "goForwardOneRank");
		HIUtil.sendResponseJson(this.compManageFacade.goForwardRound(compID, curRank));
		return NONE;
	}
	
	/**
	 * 调整盲注运行时间
	 * @return
	 */
	@Action("editRunningTime")
	public String editBlindRunningTime(){
		HIUtil.printRequestParam(this.getClass().getSimpleName(), "editBlindRunningTime");
		HIUtil.sendResponseJson(this.compManageFacade.editRoundJumpTime(compID, curRank, jumpTime));
		return NONE;
	}
	
	/**
	 * 修改减扣选手数量
	 * @return
	 */
	@Action("subPlayer")
	public String editSubPlayer(){
		HIUtil.printRequestParam(this.getClass().getSimpleName(), "editSubPlayer");
		HIUtil.sendResponseJson(this.compManageFacade.editSubPlayer(compID, subNum));
		return NONE;
	}
	
	@Override
	public CompetitionInfo getModel() {
		return null;
	}

	@Override
	public BaseService<CompetitionInfo, Integer> getService() {
		return null;
	}
	
	public int getCompID() {
		return compID;
	}

	public void setCompID(int compID) {
		this.compID = compID;
	}
	
	public int getCurRank() {
		return curRank;
	}

	public void setCurRank(int curRank) {
		this.curRank = curRank;
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

	public int getSubNum() {
		return subNum;
	}

	public void setSubNum(int subNum) {
		this.subNum = subNum;
	}
	
	public int getJumpTime() {
		return jumpTime;
	}

	public void setJumpTime(int jumpTime) {
		this.jumpTime = jumpTime;
	}

	@Resource
	private CompetitionManageFacade compManageFacade;
	
	//比赛ID主键
	private int compID;
	//扣减选手数量，累计
	private int subNum;
	private int sysType;
	private String empUuid;
	private int curRank;
	private int jumpTime;//正数，加时间；负数，减时间
	

	
}
