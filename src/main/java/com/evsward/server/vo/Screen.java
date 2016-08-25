package com.evsward.server.vo;

import java.io.Serializable;
import java.util.Date;

public class Screen implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public interface DEVPUSHTYPE{
		public static final int PUSHTYPE_NO = 0;
		public static final int PUSHTYPE_LIST = 1;
		public static final int PUSHTYPE_TIME = 2;
		public static final int PUSHTYPE_MEM = 3;
		public static final int PUSHTYPE_CHECK = 4;
		public static final String PUSHTYPE_NO_SHOW = "不推送";
		public static final String PUSHTYPE_LIST_SHOW = "赛事列表";
		public static final String PUSHTYPE_TIME_SHOW = "现场比赛信息";
		public static final String PUSHTYPE_MEM_SHOW = "选手座位信息";
		public static final String PUSHTYPE_CHECK_SHOW = "入场安检";
	}
	
	public interface DEVSTATE{
		public static final int DEVSTATE_ONLINE = 1;
		public static final int DEVSTATE_OFFLINE = 0;
		public static final String DEVSTATE_ONLINE_SHOW = "正常";
		public static final String DEVSTATE_OFFLINE_SHOW = "断线";
	}
	
	public interface LANGUAGE{
		public static final int LANGUAGE_EN = 1;
		public static final int LANGUAGE_CN = 0;
		public static final String LANGUAGE_EN_SHOW = "英文";
		public static final String LANGUAGE_CN_SHOW = "中文";
	}
	
	private String devImei;
	private int compID;
	//IP long型：2887522626
	private long ip;
	//IP字符串格式：172.28.25.66
	private String ipStr;
	private String devName;
	private int devState;
	private String devStateShow;
	private int pushType;
	private String pushTypeShow;
	private String compName;
	//语言类型：1、英文；0、中文
	private int language;
	private String languageShow;
	private int sysType;
	private Date createTime;
	private Date updateTime;
	
	public String getLanguageShow() {
		if(this.language == LANGUAGE.LANGUAGE_EN){
			languageShow = LANGUAGE.LANGUAGE_EN_SHOW;
		}else{
			languageShow = LANGUAGE.LANGUAGE_CN_SHOW;
		}
		return languageShow;
	}
	public String getDevStateShow() {
		if(this.devState == DEVSTATE.DEVSTATE_ONLINE){
			this.devStateShow = DEVSTATE.DEVSTATE_ONLINE_SHOW;
		}else{
			this.devStateShow = DEVSTATE.DEVSTATE_OFFLINE_SHOW;
		}
		return devStateShow;
	}
	public String getPushTypeShow() {
		if(this.pushType == DEVPUSHTYPE.PUSHTYPE_NO){
			pushTypeShow = DEVPUSHTYPE.PUSHTYPE_NO_SHOW;
		}else if(this.pushType == DEVPUSHTYPE.PUSHTYPE_LIST){
			pushTypeShow = DEVPUSHTYPE.PUSHTYPE_LIST_SHOW;
		}else if(this.pushType == DEVPUSHTYPE.PUSHTYPE_TIME){
			pushTypeShow = DEVPUSHTYPE.PUSHTYPE_TIME_SHOW;
		}else if(this.pushType == DEVPUSHTYPE.PUSHTYPE_MEM){
			pushTypeShow = DEVPUSHTYPE.PUSHTYPE_MEM_SHOW;
		}else if(this.pushType == DEVPUSHTYPE.PUSHTYPE_CHECK){
			pushTypeShow = DEVPUSHTYPE.PUSHTYPE_CHECK_SHOW;
		}else{
			pushTypeShow = DEVPUSHTYPE.PUSHTYPE_NO_SHOW;
		}
		return pushTypeShow;
	}
	public int getLanguage() {
		return language;
	}
	public void setLanguage(int language) {
		this.language = language;
	}
	public String getIpStr() {
		if(ip <= 0){
			ipStr = "";
		}else{
			String ipHex = Long.toHexString(ip).toLowerCase();
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < ipHex.length(); i=i+2) {
				int t = Integer.parseInt(ipHex.toUpperCase().substring(i, i+2),16);
				sb.append(t).append(".");
			}
			ipStr = sb.toString().substring(0, sb.toString().length() - 1);
		}
		return ipStr;
	}
	public void setIpStr(String ipStr) {
		this.ipStr = ipStr;
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
	public long getIp() {
		return ip;
	}
	public void setIp(long ip) {
		this.ip = ip;
	}
	public String getDevName() {
		return devName;
	}
	public void setDevName(String devName) {
		this.devName = devName;
	}
	public int getDevState() {
		return devState;
	}
	public void setDevState(int devState) {
		this.devState = devState;
	}
	public int getPushType() {
		return pushType;
	}
	public void setPushType(int pushType) {
		this.pushType = pushType;
	}
	public String getCompName() {
		return compName;
	}
	public void setCompName(String compName) {
		this.compName = compName;
	}
	public int getSysType() {
		return sysType;
	}
	public void setSysType(int sysType) {
		this.sysType = sysType;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public Screen() {
		super();
	}
	public Screen(String devImei) {
		super();
		this.devImei = devImei;
	}
	@Override
	public String toString() {
		return "Screen [devImei=" + devImei + ", compID=" + compID + ", ip=" + ip + ", ipStr=" + ipStr
				+ ", devName=" + devName + ", devState=" + devState
				+ ", pushType=" + pushType + ", compName=" + compName
				+ ", language=" + language + ", createTime=" + createTime
				+ ", updateTime=" + updateTime + "]";
	}
}
