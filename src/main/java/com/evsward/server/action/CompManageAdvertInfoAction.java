package com.evsward.server.action;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;

import com.dance.core.service.BaseService;
import com.dance.core.utils.web.StrutsAction;
import com.evsward.server.facade.AdvertInfoFacade;
import com.evsward.server.util.HIUtil;
import com.evsward.server.vo.CompetitionInfo;

@SuppressWarnings("serial")
@Namespace("/compManage/advertInfoManage")
@ParentPackage(value = "hi")
public class CompManageAdvertInfoAction extends StrutsAction<CompetitionInfo, Integer>{

	/**
	 * 获取比赛的广告信息，和所有广告信息。
	 * @return
	 */
	@Action("getAllAdvertInfo")
	public String getAdvertInfoByCompWithAll(){
		HIUtil.printRequestParam(this.getClass().getSimpleName(), "getAdvertInfoByCompWithAll");
		HIUtil.sendResponseJson(this.advertFacade.getAdvertInfoByCompWithAll(compID, sysType));
		return NONE;
	}
	
	/**
	 * 设置比赛关联的广告图片
	 * @return
	 */
	@Action("updateCompAdvertInfo")
	public String setRelation(){
		HIUtil.printRequestParam(this.getClass().getSimpleName(), "setRelation");
		HIUtil.sendResponseJson(this.advertFacade.addAdvertForComp(compID, advertID, sysType));
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
	
	public int getAdvertID() {
		return advertID;
	}

	public void setAdvertID(int advertID) {
		this.advertID = advertID;
	}
	
	private int compID;
	private String empUuid;
	private int advertID;
	private int sysType;
	
	@Resource
	private AdvertInfoFacade advertFacade;
}
