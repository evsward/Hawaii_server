package com.evsward.server.service;

import java.util.List;

import com.dance.core.service.BaseService;
import com.evsward.server.vo.Screen;

/**
 * 
 */
public interface ScreensManageService extends BaseService<Screen, String> {

	/**
	 * 获取所有大屏幕设备信息
	 * @return
	 * @throws Exception
	 */
	public List<Screen> findAllScreens(int sysType)throws Exception;
	/**
	 * 根绝imei查询大屏幕设备
	 * @param devImei
	 * @param onpad
	 * @return
	 * @throws Exception
	 */
	public Screen findScreenByImei(String devImei)throws Exception;
	
	/**
	 * pad更新设备信息
	 * @param screen
	 * @throws Exception
	 */
	public void updateScreenOnpad(Screen screen)throws Exception;
	
	/**
	 * 设备连接注册后，保存或更新信息
	 * @param screen
	 * @return
	 * @throws Exception
	 */
	public int saveOrUpdateScreen(Screen screen)throws Exception;
	
	/**
	 * 初始化所有大屏幕设备状态为断线
	 * @throws Exception
	 */
	public void initAllScreenStateNoOnline()throws Exception;
	
	/**
	 * 更新设备状态为断线
	 * @param imei
	 * @return
	 * @throws Exception
	 */
	public int updateScreenOffline(String imei)throws Exception;
}
