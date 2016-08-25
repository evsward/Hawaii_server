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
@Namespace("/compManage/chipInfoManage")
@ParentPackage(value = "hi")
public class CompManageChipInfoAction extends StrutsAction<CompetitionInfo, Integer>{

	/**
	 * 获取比赛存活选手的筹码信息
	 * @return
	 */
	@Action("getLivedPlayersChipInfo")
	public String getLivedPlayersChipInfoByComp(){
		HIUtil.printRequestParam(this.getClass().getSimpleName(), "getLivedPlayersChipInfoByComp");
		HIUtil.sendResponseJson(this.compManFacade.getLivedPlayersChipByComp(compID));
		return NONE;
	}
	
	/**
	 * 查询比赛中指定存活选手的筹码信息
	 * @return
	 */
	@Action("getPlayerChipInfo")
	public String getSpecPlayerChipInfo(){
		HIUtil.printRequestParam(this.getClass().getSimpleName(), "getSpecPlayerChipInfo");
		HIUtil.sendResponseJson(this.compManFacade.getSpecPlayerChipByComp(compID, nfcID, cardNO));
		return NONE;
	}
	
	/**
	 * 更新比赛中指定存活选手的筹码信息
	 * @return
	 */
	@Action("updatePlayerChipInfo")
	public String updatePlayerChipInfo(){
		HIUtil.printRequestParam(this.getClass().getSimpleName(), "updatePlayerChipInfo");
		HIUtil.sendResponseJson(this.compManFacade.updatePlayerChip(mcID, compID, memID, chip));
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

	public String getCardNO() {
		return cardNO;
	}

	public void setCardNO(String cardNO) {
		this.cardNO = cardNO;
	}

	public long getNfcID() {
		return nfcID;
	}

	public void setNfcID(long nfcID) {
		this.nfcID = nfcID;
	}

	public int getMemChip() {
		return memChip;
	}

	public void setMemChip(int memChip) {
		this.memChip = memChip;
	}
	
	public int getChip() {
		return chip;
	}

	public void setChip(int chip) {
		this.chip = chip;
	}

	public int getMemID() {
		return memID;
	}

	public void setMemID(int memID) {
		this.memID = memID;
	}

	public int getMcID() {
		return mcID;
	}

	public void setMcID(int mcID) {
		this.mcID = mcID;
	}
	
	private int compID;
	private int sysType;
	private String empUuid;
	private String cardNO;
	private long nfcID;
	private int memChip;
	private int mcID;
	private int memID;
	private int chip;
	

	@Resource
	private CompetitionManageFacade compManFacade;
}
