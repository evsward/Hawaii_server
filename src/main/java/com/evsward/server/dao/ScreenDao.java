package com.evsward.server.dao;

import java.util.List;

import com.dance.core.orm.BaseDao;
import com.evsward.server.vo.Screen;

public interface ScreenDao extends BaseDao<Screen, String> {

	/**
	 * 查询所有大屏幕设备
	 * @return
	 * @throws Exception
	 */
	public List<Screen> findAllScreens(int sysType)throws Exception;
	
	/**
	 * 查询单个大屏幕设备
	 * @param devImei
	 * @return
	 * @throws Exception
	 */
	public Screen findScreen(String devImei)throws Exception;
	
	/**
	 * 更新大屏幕设备信息
	 * @param screen
	 * @throws Exception
	 */
	public void updateScreenByImei(Screen screen)throws Exception;
	
	/**
	 * 设备连接上后更新IP和DEVSTATE
	 * @param screen
	 * @return
	 * @throws EXception
	 */
	public int updateScreenAuto(Screen screen)throws Exception;
	
	/**
	 * 插入大屏幕设备信息
	 * @param screen
	 * @return
	 * @throws Exception
	 */
	public int saveScreen(Screen screen)throws Exception;
	
	/**
	 * 设置所有大屏幕设备状态为断线
	 * @throws Exception
	 */
	public void initScreenNoOnline()throws Exception;
	
	/**
	 * 更新设备状态为断线
	 * @param imei
	 * @return
	 * @throws Exception
	 */
	public int updateScreenOfflineByImei(String imei)throws Exception;
}
