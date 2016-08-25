package com.evsward.server.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.dance.core.orm.mybatis.MyBatisDaoImpl;
import com.evsward.server.dao.PrizeDao;
import com.evsward.server.vo.Prize;

@Component("prizeDao")
public class PrizeDaoImpl extends MyBatisDaoImpl<Prize, Integer> implements PrizeDao {

	/**
	 * 获取奖励区间组
	 * @param sysType
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Integer>> getPrizeAreaGroup(int sysType)throws Exception{
		return this.getSqlSession().selectList("selectPrizeAreaGroup", sysType);
	}
	
	/**
	 * 获取一个区间范围的奖励
	 * @param allMin
	 * @param allMax
	 * @param sysType
	 * @return
	 * @throws Exception
	 */
	public List<Prize> getPrizeByArea(int allMin, int allMax, int sysType)throws Exception{
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("allMin", allMin);
		paramMap.put("allMax", allMax);
		paramMap.put("sysType", sysType);
		return this.find("getPrizeByArea", paramMap);
	}
	
	public List<Prize> getPrizes(int sysType) throws Exception {
		return this.find("findAllPrizes", sysType);
	}

	public void updatePrizes(List<Prize> prizes)throws Exception{
		this.update(prizes);
	}
	
	public int insertPrize(Prize prize)throws Exception{
		return (Integer)this.insert("insertPrize", prize);
	}
	
	/**
	 * 根据报名人数获取对应奖励记录规则
	 * @param regedPlayerCount
	 * @return
	 * @throws Exception
	 */
	public List<Prize> getPrizeListByPlayerCount(long regedPlayerCount, int sysType)throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("playerCount", regedPlayerCount);
		map.put("sysType", sysType);
		return this.find("getPrizeListByPlayerCount", map);
	}
}
