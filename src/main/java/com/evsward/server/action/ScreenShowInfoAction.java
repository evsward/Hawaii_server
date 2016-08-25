package com.evsward.server.action;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;

import com.dance.core.service.BaseService;
import com.dance.core.utils.web.StrutsUtils;
import com.evsward.server.facade.CompetitionManageFacade;
import com.evsward.server.facade.PrizeFacade;
import com.evsward.server.facade.ScreenManageFacade;
import com.evsward.server.util.HIUtil;
import com.dance.core.utils.web.StrutsAction;

@Namespace("/screen/show")
@ParentPackage(value = "hi")
@SuppressWarnings({ "rawtypes", "serial" })
public class ScreenShowInfoAction extends StrutsAction{

	@Resource
	private CompetitionManageFacade compManFacade;
	@Resource
	private PrizeFacade prizeFacade;
	@Resource
	private ScreenManageFacade screenManFacade;
	
	private String devImei;//设备号
	private int compID;
	private int sysType;
	
	public int getSysType() {
		return sysType;
	}

	public void setSysType(int sysType) {
		this.sysType = sysType;
	}

	public String getDevImei() {
		return devImei;
	}

	public void setDevImei(String devImei) {
		this.devImei = devImei;
	}

	public int getCompID() {
		return compID;
	}

	public void setCompID(int compID) {
		this.compID = compID;
	}

	@Override
	public Object getModel() {
		return null;
	}

	@Override
	public BaseService getService() {
		return null;
	}

	/**
	 * 大屏幕显示选手信息接口
	 * @return
	 */
	@Action("getScreenPlayersInfo")
	public String getScreenCompPlayerList(){
		HIUtil.printRequestParam(this.getClass().getSimpleName(), "getScreenCompPlayerList");
		HIUtil.sendResponseJson(compManFacade.getScreenCompPlayersInfo(compID, devImei));
		return NONE;
	}
	
	/**
	 * 大屏幕显示选手信息接口
	 * @return
	 */
	@Action("getScreenPrizes")
	public String getScreenCompPrizeList(){
		HIUtil.printRequestParam(this.getClass().getSimpleName(), "getScreenCompPrizeList");
		HIUtil.sendResponseJson(this.prizeFacade.getPrizeArea(compID));
		return NONE;
	}
	
	/**
	 * 大屏幕设备信息
	 * @return
	 */
	@Action("getScreenDevInfo")
	public String getScreenDevInfo(){
		HIUtil.printRequestParam(this.getClass().getSimpleName(), "getScreenDevInfo");
		HIUtil.sendResponseJson(this.screenManFacade.findScreenDevByImei(devImei));
		return NONE;
	}
	
	/**
	 * 大屏幕设备注册
	 * @return
	 */
	@Action("registScreenDev")
	public String registScreenDev(){
		HIUtil.printRequestParam(this.getClass().getSimpleName(), "registScreenDev");
		HIUtil.sendResponseJson(this.screenManFacade.registScreenDev(devImei, StrutsUtils.getRequest().getRemoteAddr()));
		return NONE;
	}
	
	/**
	 * 大屏幕设备信息(在大屏幕上显示的信息)
	 * @return
	 */
	@Action("getScreenShowInfo")
	public String getScreenShowInfo(){
		HIUtil.printRequestParam(this.getClass().getSimpleName(), "getScreenShowInfo");
		HIUtil.sendResponseJson(this.screenManFacade.getScreenShowInfo(devImei));
		return NONE;
	}
}
