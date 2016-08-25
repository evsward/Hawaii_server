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
@Namespace("/compAdvanManage/importComp")
@ParentPackage(value = "hi")
public class CompImportAction extends StrutsAction<CompetitionInfo, Integer>{

	@Override
	public CompetitionInfo getModel() {
		return null;
	}

	@Override
	public BaseService<CompetitionInfo, Integer> getService() {
		return null;
	}

	/**
	 * 比赛进阶管理--待导入的进阶类型的比赛。
	 * @return
	 */
	@Action("getOriginalAndDestCompList")
	public String getOriginalAndDestCompList(){
		HIUtil.printRequestParam(this.getClass().getSimpleName(), "getOriginalAndDestCompList");
		HIUtil.sendResponseJson(this.compManageFacade.getCompListForImportComp(sysType));
		return NONE;
	}
	
	/**
	 * 比赛进阶管理--导入比赛。
	 * @return
	 */
	@Action("importComps")
	public String importComps(){
		HIUtil.printRequestParam(this.getClass().getSimpleName(), "importComps");
		String[] origCompIDStr = this.orignal.split(",");
		int[] origCompID = new int[origCompIDStr.length];
		for (int i = 0; i < origCompID.length; i++) {
			origCompID[i] = Integer.parseInt(origCompIDStr[i]);
		}
		HIUtil.sendResponseJson(this.compManageFacade.importComps(origCompID, destCompID, condition, empUuid));
		return NONE;
	}
	
	@Resource
	private CompetitionManageFacade compManageFacade;
	
	private int sysType;
	private String empUuid;
	
	private String orignal;
	private int destCompID;
	private int condition;//1、固定座位；0、随机座位
	
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

	public String getOrignal() {
		return orignal;
	}

	public void setOrignal(String orignal) {
		this.orignal = orignal;
	}

	public int getDestCompID() {
		return destCompID;
	}

	public void setDestCompID(int destCompID) {
		this.destCompID = destCompID;
	}

	public int getCondition() {
		return condition;
	}

	public void setCondition(int condition) {
		this.condition = condition;
	}
	
}
