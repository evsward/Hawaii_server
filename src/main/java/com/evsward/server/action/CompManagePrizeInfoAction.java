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
@Namespace("/compManage/prizeInfoManage")
@ParentPackage(value = "hi")
public class CompManagePrizeInfoAction extends StrutsAction<CompetitionInfo, Integer>{

	/**
	 * 获取比赛运行奖池信息
	 * @return
	 */
	@Action("getAllRunningChipInfo")
	public String getPrizesInfoByComp(){
		HIUtil.printRequestParam(this.getClass().getSimpleName(), "getPrizesInfoByComp");
		HIUtil.sendResponseJson(this.compManageFacade.getCompMemPrizeInfoList(compID));
		return NONE;
	}
	
	/**
	 * 获取比赛指定名次的该选手的奖励信息，依据主键
	 * @return
	 */
	@Action("getRunningChipInfo")
	public String getPrizeInfo(){
		HIUtil.printRequestParam(this.getClass().getSimpleName(), "getPrizeInfo");
		HIUtil.sendResponseJson(this.compManageFacade.getCompMemPrizeInfo(ranking, compID, memID));
		return NONE;
	}
	
	/**
	 * 编辑比赛中某个已被淘汰且进入钱圈玩家的奖励金额
	 * @return
	 */
	@Action("updateRunningChipInfo")
	public String updatePlayerPrizeInfo(){
		HIUtil.printRequestParam(this.getClass().getSimpleName(), "updatePlayerPrizeInfo");
		HIUtil.sendResponseJson(this.compManageFacade.editCompMemPrizeInfo(ranking, memID, compID, awordMoney));
		return NONE;
	}
	
	/**
	 * 获取比赛中指定玩家的奖励信息，为打印奖励小条准备
	 * @return
	 */
	@Action("getAwordInfoForPrint")
	public String getPlayerAwordInfoForPrint(){
		HIUtil.printRequestParam(this.getClass().getSimpleName(), "getPlayerAwordInfoForPrint");
		HIUtil.sendResponseJson(this.compManageFacade.getPlayerAwordInfoForPrint(ranking, memID, compID));
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

	public int getRanking() {
		return ranking;
	}

	public void setRanking(int ranking) {
		this.ranking = ranking;
	}

	public int getMemID() {
		return memID;
	}

	public void setMemID(int memID) {
		this.memID = memID;
	}

	public int getAwordMoney() {
		return awordMoney;
	}

	public void setAwordMoney(int awordMoney) {
		this.awordMoney = awordMoney;
	}

	private int compID;
	private int sysType;
	private String empUuid;
	private int ranking;//选手的奖励名次
	private int memID;
	private int awordMoney;
	@Resource
	private CompetitionManageFacade compManageFacade;
}
