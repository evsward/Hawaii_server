package com.evsward.server.action;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;

import com.dance.core.service.BaseService;
import com.dance.core.utils.web.StrutsAction;
import com.evsward.server.facade.MemRegCompFacade;
import com.evsward.server.util.HIUtil;
import com.evsward.server.vo.regcomp.MemReg_CompSearch_Day;

/**
 * 会员报名比赛
 */
@Namespace("/memRegComp/memRegComp")
@ParentPackage(value = "hi")
@SuppressWarnings("serial")
public class MemRegAction extends StrutsAction<MemReg_CompSearch_Day, Integer> {

	@Override
	public MemReg_CompSearch_Day getModel() {
		return null;
	}

	@Override
	public BaseService<MemReg_CompSearch_Day, Integer> getService() {
		return null;
	}
	
	/**
	 * 会员报名比赛，信息查询
	 * @return
	 */
	@Action("memRegCompSearch")
	public String memRegCompSearch(){
		HIUtil.printRequestParam(this.getClass().getSimpleName(), "memRegCompSearch");
		HIUtil.sendResponseJson(memRegCompFacade.searchRegedCompAndAllComps(memID, sysType));
		return NONE;
	}
	
	/**
	 * 普通会员报名比赛
	 * @return
	 */
	@Action("memRegComp")
	public String memRegComp(){
		HIUtil.printRequestParam(this.getClass().getSimpleName(), "memRegComp");
		HIUtil.sendResponseJson(memRegCompFacade.memRegComp(memID, compID));
		return NONE;
	}
	
	/**
	 * VIP会员报名比赛
	 * @return
	 */
	@Action("vipMemRegComp")
	public String vipMemRegComp(){
		HIUtil.printRequestParam(this.getClass().getSimpleName(), "vipMemRegComp");
		HIUtil.sendResponseJson(memRegCompFacade.vipMemRegComp(memID, compID, tableNO, seatNO));
		return NONE;
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
	public int getTableNO() {
		return tableNO;
	}
	
	public int getMemID() {
		return memID;
	}

	public void setMemID(int memID) {
		this.memID = memID;
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

	@Resource
	private MemRegCompFacade memRegCompFacade;
	
	private int memID;
	private int compID;
	private int tableNO;
	private int seatNO;
	private int sysType;//系统类型：1、WPT；2、智运会
	private String empUuid;
	
}
