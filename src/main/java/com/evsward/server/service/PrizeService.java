package com.evsward.server.service;

import java.util.List;
import java.util.Map;

import com.dance.core.service.BaseService;
import com.evsward.server.vo.Prize;

public interface PrizeService extends BaseService<Prize, Integer> {

	/**
	 * 
	 * @param prizeMap
	 * @throws Exception
	 */
	public void getPrizeListMap(Map<String, List<Prize>> prizeMap)throws Exception;
}
