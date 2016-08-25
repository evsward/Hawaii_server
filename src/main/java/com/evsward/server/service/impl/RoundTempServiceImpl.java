package com.evsward.server.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.dance.core.orm.BaseDao;
import com.dance.core.service.BaseServiceImpl;
import com.evsward.server.dao.RoundDao;
import com.evsward.server.dao.RoundTempDao;
import com.evsward.server.service.RoundTempService;
import com.evsward.server.vo.Round;
import com.evsward.server.vo.RoundTemplate;

@Component("roundTempService")
public class RoundTempServiceImpl extends BaseServiceImpl<RoundTemplate, Integer>  implements RoundTempService {

	private static Logger logger = LoggerFactory.getLogger(RoundTempServiceImpl.class);

	@Resource
	private RoundTempDao roundTempDao;
	@Resource
	private RoundDao roundDao;
	
	@Override
	public BaseDao<RoundTemplate, Integer> getDao() {
		return roundTempDao;
	}

	@Override
	public List<RoundTemplate> getAllRoundTempList(int sysType) throws Exception {
		return this.roundTempDao.getAllRoundTempList(sysType);
	}

	/**
	 * 查询所有盲注模板及盲注信息
	 * @param roundMap
	 * @return
	 * @throws Exception
	 */
	public void getAllTempListWithRound(Map<Integer, List<Round>> roundMap)throws Exception{
		List<RoundTemplate> tempList = this.roundTempDao.getAllRoundTempList();
		List<Round> roundList = null;
		RoundTemplate temp = null;
		for (int i = 0; i < tempList.size(); i++) {
			temp = tempList.get(i);
			roundList = this.roundDao.getRoundListByTempID(temp.getRoundTempID());
			roundMap.put(temp.getRoundTempID(), roundList);
		}
	}
}
