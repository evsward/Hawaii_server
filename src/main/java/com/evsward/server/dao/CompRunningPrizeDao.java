package com.evsward.server.dao;

import java.util.List;

import com.dance.core.orm.BaseDao;
import com.evsward.server.vo.CompRunningPrize;

/**
 * 比赛运行参赛会员奖励信息记录表
 * @version 0.8.0
 */
public interface CompRunningPrizeDao extends BaseDao<CompRunningPrize, Integer> {

	/**
	 * 插入一条比赛运行参赛会员奖励信息记录
	 * @param runningPrizeList
	 * @throws Exception
	 */
	public void insertRunningPrize(CompRunningPrize runningPrize)throws Exception;
	
//	/**
//	 * 查询比赛当前存在的会员奖励记录
//	 * @param compID
//	 * @return
//	 * @throws Exception
//	 */
//	public List<CompRunningPrize> getExistCompRunningPrizeList(int compID)throws Exception;
	
	/**
	 * 查询比赛当前存在的会员奖励记录,带有会员信息
	 * @param compID
	 * @return
	 * @throws Exception
	 */
	public List<CompRunningPrize> getExistCompRunningPrizeListWithMemInfo(int compID)throws Exception;
	
	/**
	 * 根据主键，查询一条记录
	 * @param ranking
	 * @param compID
	 * @return
	 * @throws Exception
	 */
	public CompRunningPrize getCompRunningPrize(int ranking, int compID)throws Exception;
	
//	/**
//	 * 查询比赛当前存在的会员奖励记录,最小名次记录
//	 * @param compID
//	 * @return
//	 * @throws Exception
//	 */
//	public Integer getExistMinCompRanking(int compID)throws Exception;
	
	/**
	 * 根据主键ID编辑奖池中选手的奖金
	 * @param ranking
	 * @param compID
	 * @param amountInt
	 * @return
	 * @throws Exception
	 */
	public int updateCompRunningPrizeAmount(int ranking, int compID, int amountInt)throws Exception;
}
