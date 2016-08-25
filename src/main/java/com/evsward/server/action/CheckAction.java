package com.evsward.server.action;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

import com.dance.core.service.BaseService;
import com.dance.core.utils.web.StrutsAction;
import com.dance.core.utils.web.StrutsUtils;
import com.evsward.server.service.CompetitionService;
import com.evsward.server.thread.CompServerManage;
import com.evsward.server.thread.PushMsg2TerminalService;
import com.evsward.server.util.Application;
import com.evsward.server.util.RspCodeValue;
import com.evsward.server.util.HIFileUtils;
import com.evsward.server.util.HIUtil;
import com.evsward.server.vo.CompetitionInfo;

@Namespace("/system/ping")
@ParentPackage(value = "hi")
@SuppressWarnings({ "serial", "rawtypes" })
public class CheckAction extends StrutsAction{
	
	@Resource
	private CompetitionService compService;
	

	public Object getModel() {
		
		return null;
	}

	@Override
	public BaseService getService() {
		
		return null;
	}

	@Action("check")
	public String check(){
		HIUtil.sendResponseJson(HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$1.getRspCode(), RspCodeValue.$1.getMsg())));
		return NONE;
	}
	
	/**
	 * 查看推送线程集合
	 * @return
	 */
	@Action(value="viewCache", results={
			@Result(location="/WEB-INF/jsp/pushThreadMonitor.jsp", name="pushThreadMonitor", type="dispatcher")
	})
	public String viewHiChannelCache(){
		Map<Integer, PushMsg2TerminalService> pushMsg2TermServiceMap = Application.pushMsg2TermServiceMap;
		StrutsUtils.getRequest().setAttribute("pushMsg2TermServiceMap", pushMsg2TermServiceMap);
		return "pushThreadMonitor";
	}
	
	/**
	 * 查看后台比赛的维护线程集合
	 * @return
	 */
	@Action(value="viewCompThread", results={
			@Result(location="/WEB-INF/jsp/compThread.jsp", name="compThread", type="dispatcher")
	})
	public String viewCompThread_Serv(){
		Map<String, CompServerManage> map = Application.compServManageMap;
		StrutsUtils.getRequest().setAttribute("compServManageMap", map);
		return "compThread";
	}
	
	/**
	 * 要导出的比赛列表
	 * @return
	 */
	@Action(value="exportCompList", results={
		@Result(location="/WEB-INF/jsp/export.jsp", name="compExportList", type="dispatcher")
	})
	public String exportCompList(){
		List<CompetitionInfo> list = null;
		try {
			list = this.compService.getAllCompetitionList(sysType);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		StrutsUtils.getRequest().setAttribute("compList", list);
		return "compExportList";
	}
	
	/**
	 * 导出数据
	 * @return
	 */
	@Action(value="export")
	public String exportData(){
		HIUtil.printRequestParam(this.getClass().getSimpleName(), "exportData");
		HIFileUtils.exportExcel(this.compID);
		HIUtil.sendResponseJson(HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$1.getRspCode(), RspCodeValue.$1.getMsg())));
		return NONE;
	}
	
	private int compID;
	
	public int getCompID() {
		return compID;
	}

	public void setCompID(int compID) {
		this.compID = compID;
	}

	private int sysType = Application.SYSTYPE.USECOMPMANAGE;

	public int getSysType() {
		return sysType;
	}

	public void setSysType(int sysType) {
		this.sysType = sysType;
	}
}
