package com.evsward.server.action;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;

import com.dance.core.service.BaseService;
import com.dance.core.utils.web.StrutsAction;
import com.evsward.server.facade.EmployeeFacade;
import com.evsward.server.facade.NFCCardFacade;
import com.evsward.server.util.RspCodeValue;
import com.evsward.server.util.HIUtil;
import com.evsward.server.vo.InitedNFC;

@SuppressWarnings("serial")
@Namespace("/system/nfcInfo")
@ParentPackage(value = "hi")
public class NfcInfoAction extends StrutsAction<InitedNFC, Integer> {

	@Resource
	private NFCCardFacade nfcFacade;
	@Resource
	private EmployeeFacade empFacade;
	
	@Override
	public InitedNFC getModel() {
		return null;
	}

	@Override
	public BaseService<InitedNFC, Integer> getService() {
		return null;
	}

	@Action("addnfc")
	public String addNfcInfo(){
		HIUtil.printRequestParam(this.getClass().getSimpleName(), "addNfcInfo");
		HIUtil.sendResponseJson(nfcFacade.insertNFCInfo(nfcID, sysType));
		return NONE;
	}
	
	@Action("addEmployee")
	public String addEmployee(){
		HIUtil.printRequestParam(this.getClass().getSimpleName(), "addEmployee");
		if(StringUtils.isEmpty(empUuid)){
			HIUtil.sendResponseJson(HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$11.getRspCode(), RspCodeValue.$11.getMsg())));
			return NONE;
		}
		HIUtil.sendResponseJson(empFacade.addEmployee(empUuid.toUpperCase()));
		return NONE;
	}
	
	public long getNfcID() {
		return nfcID;
	}

	public void setNfcID(long nfcID) {
		this.nfcID = nfcID;
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
	
	//会员nfc卡十进制卡号
	private long nfcID;
	private int sysType = 1;
	private String empUuid;

}
