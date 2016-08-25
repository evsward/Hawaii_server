package com.evsward.server.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.dance.core.orm.mybatis.MyBatisDaoImpl;
import com.evsward.server.dao.ScreenDao;
import com.evsward.server.vo.Screen;

@Component("screenManDao")
public class ScreenDaoImpl extends MyBatisDaoImpl<Screen, String> implements ScreenDao {

	public List<Screen> findAllScreens(int sysType) throws Exception {
		List<Screen> list = this.find("findAll", sysType);
		return list;
	}

	public Screen findScreen(String devImei) throws Exception {
		Screen screen = (Screen)this.get("findScreenByImei", devImei);
		return screen;
	}

	public void updateScreenByImei(Screen screen)throws Exception{
		this.update("updateScreenOnPad", screen);
	}
	
	public int updateScreenAuto(Screen screen)throws Exception{
		return this.update("updateScreenAuto", screen);
	}
	
	public int saveScreen(Screen screen)throws Exception{
		return (Integer)this.insert("insertScreen", screen);
	}
	
	public void initScreenNoOnline()throws Exception{
		this.update("updateNoOnline", null);
	}

	@Override
	public int updateScreenOfflineByImei(String imei) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("imei", imei);
		paramMap.put("devState", Screen.DEVSTATE.DEVSTATE_OFFLINE);
		return this.update("updateScreenOffline", paramMap);
	}
}
