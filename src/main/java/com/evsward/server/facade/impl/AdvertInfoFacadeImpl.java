package com.evsward.server.facade.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.evsward.server.facade.AdvertInfoFacade;
import com.evsward.server.service.AdvertInfoService;
import com.evsward.server.util.Application;
import com.evsward.server.util.RspCodeValue;
import com.evsward.server.util.HIUtil;
import com.evsward.server.vo.AdvertCompRelation;
import com.evsward.server.vo.AdvertInfo;
import com.evsward.server.vo.tcpmsg.triggmsg.SystemBasePushTriggMsg;

@Component("advertFacade")
public class AdvertInfoFacadeImpl implements AdvertInfoFacade {

	private static Logger logger = LoggerFactory.getLogger(AdvertInfoFacadeImpl.class);
	
	/**
	 * 查询所有广告图片以及比赛和广告的关系
	 * @param compID
	 * @param sysType
	 * @return
	 */
	public String getAdvertInfoByCompWithAll(int compID, int sysType){
		String jsonStr = "";
		List<AdvertInfo> list = null;
		AdvertInfo advertInfo = null;
		AdvertCompRelation relation = null;
		try {
			list = this.advertService.getAllAdvertInfoList(sysType);
			advertInfo = this.advertService.getAdvertInfoByComp(compID);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$438.getRspCode(), RspCodeValue.$438.getMsg()));
		}
		if(list == null || list.size() <= 0){
			return HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$439.getRspCode(), RspCodeValue.$439.getMsg()));
		}
		Map<String, Object> resmap = HIUtil.createJsonResInitMap(RspCodeValue.$1.getRspCode(), RspCodeValue.$1.getMsg());
		resmap.put("adverts", list);
		if(advertInfo != null){
			relation = new AdvertCompRelation(advertInfo.getAdvertID(), compID, advertInfo.getSysType());
			resmap.put("compAdvert", relation);
		}
		jsonStr = HIUtil.toJsonNormalField(resmap);
		return jsonStr;
	}
	
	/**
	 * 为比赛添加广告
	 * @param compID
	 * @param advertID
	 * @return
	 */
	public String addAdvertForComp(int compID, int advertID,int sysType){
		String jsonStr = "";
		try {
			this.advertService.setCompAdvertRelation(compID, advertID, sysType);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$440.getRspCode(), RspCodeValue.$440.getMsg()));
		}
		Application.setTriggingMsgForPushing(new SystemBasePushTriggMsg(Application.OPERTYPE.COMPBINDADVERT, 0, compID));
		Map<String, Object> resmap = HIUtil.createJsonResInitMap(RspCodeValue.$1.getRspCode(), RspCodeValue.$1.getMsg());
		jsonStr = HIUtil.toJsonNormalField(resmap);
		return jsonStr;
	}
	
	@Resource
	private AdvertInfoService advertService;
}
