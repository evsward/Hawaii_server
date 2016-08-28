package com.evsward.server.action;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;

import com.dance.core.service.BaseService;
import com.dance.core.utils.web.StrutsAction;
import com.evsward.server.facade.MemberManageFacade;
import com.evsward.server.util.HIUtil;
import com.evsward.server.vo.CompetitionMember;

/**
 * 会员比赛信息
 */
@Namespace("/compMember/compMember")
@ParentPackage(value = "hi")
@SuppressWarnings("serial")
public class CompetitionMemberAction extends StrutsAction<CompetitionMember, Integer>{

	/**
	 * 会员查询，已报名的比赛信息查询
	 * @return
	 */
	@Action("memCompSearch")
	public String memCompSearch(){
		HIUtil.printRequestParam(this.getClass().getSimpleName(), "memCompSearch");
		HIUtil.sendResponseJson(this.memManageFacade.findMemCompInfoByMemID(memID, sysType));
		return NONE;
	}
	
	@Override
	public CompetitionMember getModel() {
		return null;
	}

	@Override
	public BaseService<CompetitionMember, Integer> getService() {
		return null;
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
	
	public int getSysType() {
		return sysType;
	}

	public void setSysType(int sysType) {
		this.sysType = sysType;
	}
	
	@Resource
	private MemberManageFacade memManageFacade;
	//会员ID
	private int memID;
	private int sysType;//系统类型：1、HI；2、智运会
	private String empUuid;

	
}
