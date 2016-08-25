package com.evsward.server.action;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dance.core.service.BaseService;
import com.dance.core.utils.web.StrutsAction;
import com.evsward.server.facade.ScreenManageFacade;
import com.evsward.server.util.RspCodeValue;
import com.evsward.server.util.HIUtil;
import com.evsward.server.vo.Screen;

@SuppressWarnings("serial")
@Namespace("/screen/screen")
@ParentPackage(value = "hi")
public class ScreensManageAction extends StrutsAction<Screen, Integer> {

	private static Logger logger = LoggerFactory.getLogger(ScreensManageAction.class);

//	/**
//	 * 大屏幕设备注册接口
//	 * @return
//	 */
//	@Action("screenRegist")
//	public String screenRegist(){
//		
//		return NONE;
//	}
	
	/**
	 * pad查看所有大屏幕设备信息
	 * @return
	 */
	@Action("findScreens")
	public String findScreens(){
		HIUtil.printRequestParam(this.getClass().getSimpleName(), "findScreens");
		if(this.getActionErrors().size() > 0){
			HIUtil.sendResponseJson(HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$11.getRspCode(), RspCodeValue.$11.getMsg())));
			return NONE;
		}
		HIUtil.sendResponseJson(screenFacade.findAllScreens(sysType));
		return NONE;
	}
	
	/**
	 * pad端编辑更新单个大屏幕设备
	 * @return
	 */
	@Action("updateScreen")
	public String updateScreen(){
		HIUtil.printRequestParam(this.getClass().getSimpleName(), "updateScreen");
		if(this.getActionErrors().size() > 0){
			HIUtil.sendResponseJson(HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$11.getRspCode(), RspCodeValue.$11.getMsg())));
			return NONE;
		}
		HIUtil.sendResponseJson(screenFacade.updateScreenOnpad(devImei, devName, pushType, compID, language, sysType));
		return NONE;
	}
	
	/**
	 * pad查看单个大屏幕设备信息
	 * @return
	 */
	@Action("findScreen")
	public String findScreen(){
		HIUtil.printRequestParam(this.getClass().getSimpleName(), "findScreen");
		if(this.getActionErrors().size() > 0){
			HIUtil.sendResponseJson(HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$11.getRspCode(), RspCodeValue.$11.getMsg())));
			return NONE;
		}
		HIUtil.sendResponseJson(screenFacade.findScreenByImei(this.devImei));
		return NONE;
	}
	
	public void validateFindScreen(){
		if(StringUtils.isEmpty(this.devImei)){
			this.addActionError(RspCodeValue.$11.getMsg());
		}
	}
	
	public void validateUpdateScreen(){
		if(StringUtils.isEmpty(this.devImei) || StringUtils.isEmpty(this.devName)){
			this.addActionError(RspCodeValue.$11.getMsg());
			return;
		}
		if(this.language != 1 && this.language != 0){
			logger.debug("language="+this.language);
			this.addActionError(RspCodeValue.$11.getMsg());
			return;
		}
		if(Screen.DEVPUSHTYPE.PUSHTYPE_NO == this.pushType){
			this.compID = 0;
		}else if(Screen.DEVPUSHTYPE.PUSHTYPE_LIST == this.pushType){
			this.compID = 0;
		}else if(Screen.DEVPUSHTYPE.PUSHTYPE_CHECK == this.pushType){
			this.compID = 0;
		}else if(Screen.DEVPUSHTYPE.PUSHTYPE_TIME == this.pushType){
			if(this.compID <=0){
				logger.debug("推送计时信息，比赛ID为0"+",pushType="+pushType+",compID="+compID);
				this.addActionError(RspCodeValue.$11.getMsg());
			}
		}else if(Screen.DEVPUSHTYPE.PUSHTYPE_MEM == this.pushType){
			if(this.compID <=0){
				logger.debug("推送比赛会员信息，比赛ID为0"+",pushType="+pushType+",compID="+compID);
				this.addActionError(RspCodeValue.$11.getMsg());
			}
		}else{
			this.addActionError(RspCodeValue.$11.getMsg());
		}
	}
	
	@Override
	public BaseService<Screen, Integer> getService() {
		return null;
	}
	
	public String getDevName() {
		return devName;
	}

	public void setDevName(String devName) {
		this.devName = devName;
	}

	public String getDevImei() {
		return devImei;
	}

	public void setDevImei(String devImei) {
		this.devImei = devImei;
	}

	public int getPushType() {
		return pushType;
	}

	public void setPushType(int pushType) {
		this.pushType = pushType;
	}

	public int getCompID() {
		return compID;
	}

	public void setCompID(int compID) {
		this.compID = compID;
	}

	public int getLanguage() {
		return language;
	}

	public void setLanguage(int language) {
		this.language = language;
	}

	public Screen getModel() {
		return null;
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
	private ScreenManageFacade screenFacade;
	
	private String devName;
	private String devImei;
	//推送类型：0、不推送；1、推送比赛里表；2、推送计时信息；3、推送会员信息
	private int pushType = 0;
	private int compID;
	private int language;
	private int sysType;//系统类型：1、WPT；2、智运会
	private String empUuid;
}
