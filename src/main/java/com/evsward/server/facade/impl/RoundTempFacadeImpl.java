package com.evsward.server.facade.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.evsward.server.facade.RoundTempFacade;
import com.evsward.server.service.RoundTempService;
import com.evsward.server.util.RspCodeValue;
import com.evsward.server.util.HIUtil;
import com.evsward.server.vo.RoundTemplate;

@Component("roundTempFacade")
public class RoundTempFacadeImpl implements RoundTempFacade {

	private static Logger logger = LoggerFactory.getLogger(RoundTempFacadeImpl.class);

	@Resource
	private RoundTempService roundTempService;
	
	@Override
	public String getAllRoundTempList(int sysType) {
		String jsonStr = "";
		List<RoundTemplate> tempList = null;
		try {
			tempList = roundTempService.getAllRoundTempList(sysType);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$450.getRspCode(), RspCodeValue.$450.getMsg()));
		}
		if(tempList == null || tempList.size() <= 0){
			return HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$451.getRspCode(), RspCodeValue.$451.getMsg()));
		}
		Map<String, Object> map = HIUtil.createJsonResInitMap(RspCodeValue.$1.getRspCode(), RspCodeValue.$1.getMsg());
		map.put("roundTemps", tempList);
		jsonStr = HIUtil.toJsonNormalField(map);
		return jsonStr;
	}
	
	
}
