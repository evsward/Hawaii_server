package com.evsward.server.facade;

import com.evsward.server.vo.Screen;

public interface ScreenManageFacade {

	/**
	 * 大屏幕设备连接tcp请求，查询的大屏幕设备的信息（不包含比赛信息）
	 * @param devImei
	 * @return
	 */
	public String findScreenDevByImei(String devImei);
	
	/**
	 * 根据devImei查询大屏幕设备
	 * @return
	 */
	public String findScreenByImei(String devImei);
	
	/**
	 * 根据devImei查询大屏幕设备
	 * @return
	 */
	public Screen getScreenByImei(String devImei);
	
	/**
	 * 跟新大屏幕设备信息
	 * @param devImei：大屏幕设备号
	 * @param devName：大屏幕设备名称
	 * @param pushType：大屏幕设备推送类型
	 * @param compID
	 * @param language
	 * @param sysType
	 * @return
	 */
	public String updateScreenOnpad(String devImei, String devName, int pushType, int compID, int language, int sysType);
	
	/**
	 * 查询大屏幕设备列表
	 * @param sysType
	 * @return
	 */
	public String findAllScreens(int sysType);
	
	/**
	 * 更新大屏幕为断线状态
	 * @param imei
	 * @return
	 */
	public int updateScreenOffline(String imei);
	
	/**
	 * 大屏幕注册
	 * @param devImei	设备号
	 * @param ipStr	客户端IP地址（172.28.25.66）
	 * @return
	 */
	public String registScreenDev(String devImei, String ipStr);
	
	/**
	 * 大屏幕tcp请求，获取大屏幕显示信息
	 * @param devImei
	 * @return
	 */
	public String getScreenShowInfo(String devImei);
	
	/**
	 * 大屏幕tcp请求，获取大屏幕显示信息
	 * @param devImei
	 * @return
	 */
	public String getScreenShowInfo(Screen screen, int memID);
}
