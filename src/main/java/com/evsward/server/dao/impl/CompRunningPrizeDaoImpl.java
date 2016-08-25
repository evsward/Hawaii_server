package com.evsward.server.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.dance.core.orm.mybatis.MyBatisDaoImpl;
import com.evsward.server.dao.CompRunningPrizeDao;
import com.evsward.server.vo.CompRunningPrize;

@Component("runningPrizeDao")
public class CompRunningPrizeDaoImpl extends
		MyBatisDaoImpl<CompRunningPrize, Integer> implements
		CompRunningPrizeDao {

	@Override
	public void insertRunningPrize(CompRunningPrize runningPrize)
			throws Exception {
		this.insert("insertRunningPrize",runningPrize);
	}

//	/**
//	 * 查询比赛当前存在的会员奖励记录
//	 * @param compID
//	 * @return
//	 * @throws Exception
//	 */
//	public List<CompRunningPrize> getExistCompRunningPrizeList(int compID)throws Exception{
//		return this.find("getExistCompRunningPrizeList", compID);
//	}
	
	/**
	 * 查询比赛当前存在的会员奖励记录,带有会员信息
	 * @param compID
	 * @return
	 * @throws Exception
	 */
	public List<CompRunningPrize> getExistCompRunningPrizeListWithMemInfo(int compID)throws Exception{
		return this.find("getExistCompRunningPrizeListWithMemInfo", compID);
	}
	
	/**
	 * 根据ID主键，查询一条记录
	 * @param ranking
	 * @param compID
	 * @return
	 * @throws Exception
	 */
	public CompRunningPrize getCompRunningPrize(int ranking, int compID)throws Exception{
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("ranking", ranking);
		paramMap.put("compID", compID);
		return (CompRunningPrize)this.get("getCompRunningPrize", paramMap);
	}
	
//	/**
//	 * 查询比赛当前存在的会员奖励记录,最小名次记录
//	 * @param compID
//	 * @return
//	 * @throws Exception
//	 */
//	public Integer getExistMinCompRanking(int compID)throws Exception{
//		return (Integer)this.get("getExistMinRankNO", compID);
//	}
	
	/**
	 * 根据主键ID编辑奖池中选手的奖金
	 * @param runningPrizeID
	 * @param amountInt
	 * @return
	 * @throws Exception
	 */
	public int updateCompRunningPrizeAmount(int ranking, int compID, int amountInt)throws Exception{
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("ranking", ranking);
		paramMap.put("compID", compID);
		paramMap.put("amountInt", amountInt);
		return this.update("updateCompRunningPrizeAmount", paramMap);
	}
}
