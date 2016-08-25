package com.evsward.server.action;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dance.core.service.BaseService;
import com.dance.core.utils.web.StrutsAction;
import com.evsward.server.facade.CompetitionManageFacade;
import com.evsward.server.util.RspCodeValue;
import com.evsward.server.util.HIUtil;
import com.evsward.server.vo.CompetitionInfo;

@SuppressWarnings("serial")
@Namespace("/compManage/seatManage")
@ParentPackage(value = "hi")
public class CompManageSeatInfoAction extends StrutsAction<CompetitionInfo, Integer>{
	
	private static Logger logger = LoggerFactory.getLogger(CompManageSeatInfoAction.class);
	
	@Action("compManSeatInfoList")
	public String compManSeatInfoTest()throws Exception{
		HIUtil.printRequestParam(this.getClass().getSimpleName(), "compManSeatInfoTest");
		HIUtil.sendResponseJson(this.compManageFacade.getCompMemsSeatInfo(compID));
		return NONE;
	}
	
	/**
	 * 比赛开启牌桌（开启报名）
	 * @return
	 */
	@Action("openCompTable")
	public String openCompTables(){
		if(this.hasActionErrors()){
			HIUtil.sendResponseJson(HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$11.getRspCode(), RspCodeValue.$11.getMsg())));
			return NONE;
		}
		HIUtil.printRequestParam(this.getClass().getSimpleName(), "openCompTables");
		String[] tableNOArr = this.openTableNO.split(",");
		int[] tableNOArrInt = new int[tableNOArr.length];
		for (int i = 0; i < tableNOArr.length; i++) {
			tableNOArrInt[i] = Integer.parseInt(tableNOArr[i].trim());
		}
		HIUtil.sendResponseJson(this.compManageFacade.openCompTables(compID, tableNOArrInt, empUuid));
		return NONE;
	}
	
	/**
	 * 释放比赛牌桌
	 * @return
	 */
	@Action("releaseCompTable")
	public String releaseCompTable(){
		HIUtil.printRequestParam(this.getClass().getSimpleName(), "releaseCompTable");
		HIUtil.sendResponseJson(this.compManageFacade.releaseCompTable(compID, tableNO));
		return NONE;
	}
	
	/**
	 * 平衡比赛牌桌
	 * @return
	 */
	@Action("balanceCompTable")
	public String balanceCompMember(){
		if(this.hasActionErrors()){
			HIUtil.sendResponseJson(HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$11.getRspCode(), RspCodeValue.$11.getMsg())));
			return NONE;
		}
		HIUtil.printRequestParam(this.getClass().getSimpleName(), "balanceCompMember");
		HIUtil.sendResponseJson(this.compManageFacade.balanceCompMember(cmID, compID, memID, tableNO, seatNO, empUuid));
		return NONE;
	}
	
	/**
	 * 淘汰比赛选手
	 * @return
	 */
	@Action("outMember")
	public String outMemberOfComp(){
		HIUtil.printRequestParam(this.getClass().getSimpleName(), "outMemberOfComp");
		HIUtil.sendResponseJson(this.compManageFacade.outMemFromComp(cmID, memID, compID));
		return NONE;
	}
	
	/**
	 * 比赛爆桌
	 * @return
	 */
	@Action("burstCompTable")
	public String burstCompTable(){
		HIUtil.printRequestParam(this.getClass().getSimpleName(), "burstCompTable");
		HIUtil.sendResponseJson(this.compManageFacade.burstTableFromComp(compID, tableNO, empUuid));
		return NONE;
	}
	
	public void validateOpenCompTables(){
		if(StringUtils.isEmpty(this.openTableNO) || this.compID <= 0){
			this.addActionError(RspCodeValue.$11.getMsg());
		}
	}
	
	public void validateBalanceCompTable(){
		if(this.compID <= 0 || this.tableNO <= 0 || this.seatNO <= 0){
			this.addActionError(RspCodeValue.$101.getMsg());
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
	
	public int getCompID() {
		return compID;
	}

	public void setCompID(int compID) {
		this.compID = compID;
	}
	
	public int getTableNO() {
		return tableNO;
	}

	public void setTableNO(int tableNO) {
		this.tableNO = tableNO;
	}

	public int getSeatNO() {
		return seatNO;
	}

	public void setSeatNO(int seatNO) {
		this.seatNO = seatNO;
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
	
	public String getOpenTableNO() {
		return openTableNO;
	}

	public void setOpenTableNO(String openTableNO) {
		this.openTableNO = openTableNO;
	}
	
	public int getMemID() {
		return memID;
	}

	public void setMemID(int memID) {
		this.memID = memID;
	}

	public int getCmID() {
		return cmID;
	}

	public void setCmID(int cmID) {
		this.cmID = cmID;
	}
	
	@Resource
	private CompetitionManageFacade compManageFacade;
	
	//比赛ID主键
	private int compID;
	private int memID;
	private int cmID;
	//桌号
	private int tableNO;
	//座位号
	private int seatNO;
	private int sysType;
	//员工号
	private String empUuid;
	
	private String openTableNO;//要开启的牌桌号，逗号分隔

	
	
}
