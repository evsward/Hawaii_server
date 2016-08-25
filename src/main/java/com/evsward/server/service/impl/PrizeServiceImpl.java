package com.evsward.server.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.dance.core.orm.BaseDao;
import com.dance.core.service.BaseServiceImpl;
import com.evsward.server.dao.PrizeDao;
import com.evsward.server.service.PrizeService;
import com.evsward.server.vo.Prize;

@Component("prizeService")
public class PrizeServiceImpl extends BaseServiceImpl<Prize, Integer> implements
		PrizeService {

	public void getPrizeListMap(Map<String, List<Prize>> prizeMap)throws Exception{
		List<Map<String, Integer>> prizeAreaList = this.prizeDao.getPrizeAreaGroup(1);
		Map<String, Integer> map = null;
		List<Prize> list = null;
		for (int i = 0; i < prizeAreaList.size(); i++) {
			map = prizeAreaList.get(i);
			Integer allMin = map.get("ALLMIN");
			Integer allMax = map.get("ALLMAX");
			list = this.prizeDao.getPrizeByArea(allMin, allMax, 1);
			prizeMap.put(list.get(0).getAllMin() + "-" + list.get(0).getAllMax(), list);
		}
	}

	@Override
	public BaseDao<Prize, Integer> getDao() {
		return prizeDao;
	}
	
	@Resource
	private PrizeDao prizeDao;
}
