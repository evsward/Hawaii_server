package com.evsward.server.action;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;

import com.dance.core.service.BaseService;
import com.dance.core.utils.web.StrutsAction;
import com.evsward.server.facade.CompetitionManageFacade;
import com.evsward.server.util.RspCodeValue;
import com.evsward.server.util.HIUtil;
import com.evsward.server.vo.CompetitionInfo;

@SuppressWarnings("serial")
@Namespace("/competition/competition")
@ParentPackage(value = "hi")
public class CompetitionListAction extends StrutsAction<CompetitionInfo, Integer>{

	/**
	 * 新建比赛
	 * @return
	 */
	@Action("createCompetition")
	public String createCompetition(){
		if(this.getActionErrors().size() > 0){
			HIUtil.sendResponseJson(HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$11.getRspCode(), RspCodeValue.$11.getMsg())));
			return NONE;
		}
		HIUtil.printRequestParam(this.getClass().getSimpleName(), "createCompetition");
		HIUtil.sendResponseJson(this.compManageFacade.createCompetition(compName.trim(), leastPrize, regFee, serviceFee, beginChip, unit, roundTempID, stopRegRank, reEntry, compType, tableType, sysType, aword, assignSeat, beginTime, empUuid));
		return NONE;
	}
	
	/**
	 * 编辑比赛
	 * @return
	 */
	@Action("editCompetition")
	public String editCompetition(){
		if(this.getActionErrors().size() > 0){
			HIUtil.sendResponseJson(HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$11.getRspCode(), RspCodeValue.$11.getMsg())));
			return NONE;
		}
		HIUtil.printRequestParam(this.getClass().getSimpleName(), "editCompetition");
		HIUtil.sendResponseJson(this.compManageFacade.editCompetition(compID, compName, leastPrize, regFee, serviceFee, beginChip, unit, roundTempID, stopRegRank, reEntry, compType, tableType, aword, assignSeat, beginTime, empUuid));
		return NONE;
	}
	
	/**
	 * 获取所有比赛（不包含已逻辑删除）
	 * @return
	 */
	@Action("getAllCompetitionsNoDel")
	public String getAllCompetitions(){
		HIUtil.printRequestParam(this.getClass().getSimpleName(), "getAllCompetitions");
		HIUtil.sendResponseJson(this.compManageFacade.getAllComptitionsNoDel(sysType));
		return NONE;
	}
	
	/**
	 * 结束比赛
	 * @return
	 */
	@Action("endCompetition")
	public String endCompetition(){
		HIUtil.printRequestParam(this.getClass().getSimpleName(), "endCompetition");
		HIUtil.sendResponseJson(this.compManageFacade.endCompetition(compID));
		return NONE;
	}
	
	/**
	 * 删除比赛
	 * @return
	 */
	@Action("delCompetition")
	public String delCompetition(){
		HIUtil.printRequestParam(this.getClass().getSimpleName(), "delCompetition");
		HIUtil.sendResponseJson(this.compManageFacade.delCompetition(compID));
		return NONE;
	}
	
	public void validateDelCompetition(){
		if(this.compID <= 0){
			this.addActionError(RspCodeValue.$11.getMsg());
		}
	}
	
	public void validateEditCompetition(){
		if(this.compID <=0 || StringUtils.isEmpty(this.compName) || this.leastPrize < 0 || this.regFee <= 0 || this.serviceFee <= 0 
				|| this.beginChip <= 0 || (this.unit != CompetitionInfo.MONEYUNIT.USD && this.unit != CompetitionInfo.MONEYUNIT.CNY) 
				|| this.roundTempID <= 0 || (this.aword != CompetitionInfo.AWORDSTATE.WITHAWORD && this.aword != CompetitionInfo.AWORDSTATE.NOAWORD) 
				|| (this.assignSeat != CompetitionInfo.ASSIGNSEATSTATE.USE && this.assignSeat != CompetitionInfo.ASSIGNSEATSTATE.NOUSE) 
				|| this.stopRegRank < 1 || (this.reEntry != CompetitionInfo.ENTRYSTATE.ENABLEENTRY && this.reEntry != CompetitionInfo.ENTRYSTATE.DISABLEENTRY) 
				|| (this.tableType != CompetitionInfo.TABLETYPE.TEN && this.tableType != CompetitionInfo.TABLETYPE.NINE && this.tableType != CompetitionInfo.TABLETYPE.SIX)){
			logger.error("validateEditCompetition [compID=" + compID + ", compName=" + compName
					+ ", leastPrize=" + leastPrize + ", regFee=" + regFee
					+ ", serviceFee=" + serviceFee + ", beginChip=" + beginChip
					+ ", unit=" + unit + ", roundTempID=" + roundTempID
					+ ", stopRegRank=" + stopRegRank + ", reEntry=" + reEntry
					+ ", tableType=" + tableType + ", aword=" + aword
					+ ", assignSeat="+ assignSeat 
					+ ", beginTime=" + beginTime + ", empUuid=" + empUuid + "]");
			this.addActionError(RspCodeValue.$11.getMsg());
		}
	}
	
	public void validateCreateCompetition(){
		if(this.beginTime <= 0 || StringUtils.isEmpty(this.compName) || this.leastPrize < 0 || this.regFee < 0 || this.serviceFee < 0 
				|| this.beginChip <= 0 || (this.unit != CompetitionInfo.MONEYUNIT.USD && this.unit != CompetitionInfo.MONEYUNIT.CNY) 
				|| this.roundTempID <= 0 || (this.aword != CompetitionInfo.AWORDSTATE.WITHAWORD && this.aword != CompetitionInfo.AWORDSTATE.NOAWORD) 
				|| (this.assignSeat != CompetitionInfo.ASSIGNSEATSTATE.USE && this.assignSeat != CompetitionInfo.ASSIGNSEATSTATE.NOUSE) 
				|| this.stopRegRank < 1 || (this.reEntry != CompetitionInfo.ENTRYSTATE.ENABLEENTRY && this.reEntry != CompetitionInfo.ENTRYSTATE.DISABLEENTRY) 
				|| (this.tableType != CompetitionInfo.TABLETYPE.TEN && this.tableType != CompetitionInfo.TABLETYPE.NINE && this.tableType != CompetitionInfo.TABLETYPE.SIX)){
			logger.error("validateCreateCompetition [compID=" + compID + ", compName=" + compName
					+ ", leastPrize=" + leastPrize + ", regFee=" + regFee
					+ ", serviceFee=" + serviceFee + ", beginChip=" + beginChip
					+ ", unit=" + unit + ", roundTempID=" + roundTempID
					+ ", stopRegRank=" + stopRegRank + ", reEntry=" + reEntry
					+ ", tableType=" + tableType + ", aword=" + aword
					+ ", assignSeat="+ assignSeat 
					+ ", beginTime=" + beginTime + ", empUuid=" + empUuid + "]");
			this.addActionError(RspCodeValue.$11.getMsg());
		}
	}
	
	@Override
	public CompetitionInfo getModel() {
		
		return null;
	}

	@Override
	public BaseService<CompetitionInfo, Integer> getService() {
		
		return null;
	}
	
	public String getCompName() {
		return compName;
	}

	public void setCompName(String compName) {
		this.compName = compName;
	}

	public int getLeastPrize() {
		return leastPrize;
	}

	public void setLeastPrize(int leastPrize) {
		this.leastPrize = leastPrize;
	}

	public int getRegFee() {
		return regFee;
	}

	public void setRegFee(int regFee) {
		this.regFee = regFee;
	}

	public int getServiceFee() {
		return serviceFee;
	}

	public void setServiceFee(int serviceFee) {
		this.serviceFee = serviceFee;
	}

	public int getBeginChip() {
		return beginChip;
	}

	public void setBeginChip(int beginChip) {
		this.beginChip = beginChip;
	}

	public int getUnit() {
		return unit;
	}

	public void setUnit(int unit) {
		this.unit = unit;
	}

	public int getRoundTempID() {
		return roundTempID;
	}

	public void setRoundTempID(int roundTempID) {
		this.roundTempID = roundTempID;
	}

	public int getStopRegRank() {
		return stopRegRank;
	}

	public void setStopRegRank(int stopRegRank) {
		this.stopRegRank = stopRegRank;
	}

	public int getReEntry() {
		return reEntry;
	}

	public void setReEntry(int reEntry) {
		this.reEntry = reEntry;
	}

	public int getTableType() {
		return tableType;
	}

	public void setTableType(int tableType) {
		this.tableType = tableType;
	}

	public int getAword() {
		return aword;
	}

	public void setAword(int aword) {
		this.aword = aword;
	}
	
	public int getAssignSeat() {
		return assignSeat;
	}

	public void setAssignSeat(int assignSeat) {
		this.assignSeat = assignSeat;
	}

	public long getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(long beginTime) {
		this.beginTime = beginTime;
	}

	public String getEmpUuid() {
		return empUuid;
	}

	public void setEmpUuid(String empUuid) {
		this.empUuid = empUuid;
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
	
	public int getCompType() {
		return compType;
	}

	public void setCompType(int compType) {
		this.compType = compType;
	}
	
	//比赛ID主键
	private int compID;
	private String compName;
	//保底奖金
	private int leastPrize;
	//参赛费
	private int regFee;
	//服务费
	private int serviceFee;
	//初始筹码
	private int beginChip;
	//货币单位：1、CNY；2、USD
	private int unit;
	//盲注模板
	private int roundTempID;
	//结束报名条件，盲注级别
	private int stopRegRank;
	//是否可重进：1、可重进；0、不可重进
	private int reEntry;
	private int compType;
	//牌桌类型：10、十人桌；9、九人桌；6、六人桌
	private int tableType;
	private int sysType;//系统类型：1、HI；2、智运会
	//是否带入奖励表，1、带入；0、不带入
	private int aword;
	//是否使用分座系统：1、使用；0、不适用
	private int assignSeat;
	//开赛时间
	private long beginTime;
	private String empUuid;
	
	@Resource
	private CompetitionManageFacade compManageFacade;
}
