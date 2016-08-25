package com.evsward.server.action;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;

import com.dance.core.service.BaseService;
import com.dance.core.utils.web.StrutsAction;
import com.evsward.server.facade.EmployeeFacade;
import com.evsward.server.util.RspCodeValue;
import com.evsward.server.util.HISysProp;
import com.evsward.server.util.HIUtil;
import com.evsward.server.vo.Employee;

/**
 * nfc刷卡登录
 */
@SuppressWarnings("serial")
@Namespace("/system/login")
@ParentPackage(value = "hi")
public class UuidLoginAction extends StrutsAction<Employee,Integer>{

	@Resource
	private EmployeeFacade empFacade;
	@Resource
	private HISysProp hiSysProp;
	
	private String empUuid;//雇员的nfc卡tagid
	
	private String version;
	
	private int sysType;
	
	public int getSysType() {
		return sysType;
	}

	public void setSysType(int sysType) {
		this.sysType = sysType;
	}
	
	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getEmpUuid() {
		return empUuid;
	}

	public void setEmpUuid(String empUuid) {
		this.empUuid = empUuid;
	}

	public Employee getModel() {
		
		return null;
	}

	@Override
	public BaseService<Employee, Integer> getService() {
		
		return null;
	}

	/**
	 * employee的nfc登录入口
	 * @return
	 * 		返回employee的信息，包含角色，权限。如果没有employee信息，那就是非法的用户。
	 */
	@Action(value="uuidLogin")
	public String uuidLogin(){
		HIUtil.printRequestParam(this.getClass().getSimpleName(), "uuidLogin");
		String appVersion = hiSysProp.getPropParams().get("app.version");
		if(appVersion != null){
			if(!appVersion.equals(this.version)){
				String msg = RspCodeValue.$10.getMsg()+"[client version="+this.version+", server version="+appVersion+"]";
				HIUtil.sendResponseJson(HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$10.getRspCode(), msg)));
				return NONE;
			}
		}
		HIUtil.sendResponseJson(this.empFacade.uuidEmpLoginResponse(empUuid.toUpperCase()));
		return NONE;
	}
}
