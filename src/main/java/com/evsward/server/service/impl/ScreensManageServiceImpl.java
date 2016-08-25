package com.evsward.server.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.dance.core.orm.BaseDao;
import com.dance.core.service.BaseServiceImpl;
import com.evsward.server.dao.ScreenDao;
import com.evsward.server.service.ScreensManageService;
import com.evsward.server.vo.Screen;

@Component("screensManService")
public class ScreensManageServiceImpl extends BaseServiceImpl<Screen, String>
		implements ScreensManageService {

	@Resource
	private ScreenDao screenDao;
	
	public List<Screen> findAllScreens(int sysType) throws Exception {
		return screenDao.findAllScreens(sysType);
	}
	
	public Screen findScreenByImei(String devImei)throws Exception{
		return screenDao.findScreen(devImei);
	}

	@Override
	public BaseDao<Screen, String> getDao() {
		return screenDao;
	}

	public void updateScreenOnpad(Screen screen)throws Exception{
		this.screenDao.updateScreenByImei(screen);
	}
	
	/**
	 * 设备连接注册后，保存或更新信息
	 * @param screen
	 * @return
	 * @throws Exception
	 */
	public int saveOrUpdateScreen(Screen screen)throws Exception{
		Screen temp = this.screenDao.findScreen(screen.getDevImei());
		int res = 0;
		if(temp == null){
			res = this.screenDao.saveScreen(screen);
		}else{
			res = this.screenDao.updateScreenAuto(screen);
		}
		return res;
	}
	
	public void initAllScreenStateNoOnline()throws Exception{
		screenDao.initScreenNoOnline();
	}
	
	/**
	 * 更新设备状态为断线
	 * @param imei
	 * @return
	 * @throws Exception
	 */
	public int updateScreenOffline(String imei)throws Exception{
		return screenDao.updateScreenOfflineByImei(imei);
	}
}
