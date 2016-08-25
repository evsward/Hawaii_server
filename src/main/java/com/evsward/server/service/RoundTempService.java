package com.evsward.server.service;

import java.util.List;
import java.util.Map;

import com.dance.core.service.BaseService;
import com.evsward.server.vo.Round;
import com.evsward.server.vo.RoundTemplate;

public interface RoundTempService extends BaseService<RoundTemplate, Integer> {

	/**
	 * 获取所有盲注模板列表
	 * @param sysType
	 * @return
	 * @throws Exception
	 */
	public List<RoundTemplate> getAllRoundTempList(int sysType)throws Exception;
	
	/**
	 * 查询所有盲注模板及盲注信息
	 * @param roundMap
	 * @return
	 * @throws Exception
	 */
	public void getAllTempListWithRound(Map<Integer, List<Round>> roundMap)throws Exception;
}
