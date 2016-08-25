package com.evsward.server.dao;

import java.util.List;
import java.util.Map;

import com.dance.core.orm.BaseDao;
import com.evsward.server.vo.Prize;

public interface PrizeDao extends BaseDao<Prize, Integer> {

	/**
	 * 获取奖励区间组
	 * @param sysType
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Integer>> getPrizeAreaGroup(int sysType)throws Exception;
	
	/**
	 * 获取一个区间范围的奖励
	 * @param allMin
	 * @param allMax
	 * @param sysType
	 * @return
	 * @throws Exception
	 */
	public List<Prize> getPrizeByArea(int allMin, int allMax, int sysType)throws Exception;
	
	/**
	 * 获取所有奖励记录
	 * @param sysType
	 * @return
	 * @throws Exception
	 */
	public List<Prize> getPrizes(int sysType)throws Exception;
	
	/**
	 * 更新奖励模板
	 * @param prizes
	 * @throws Exception
	 */
	public void updatePrizes(List<Prize> prizes)throws Exception;
	
	/**
	 * 插入奖励模板记录
	 * @param prize
	 * @return
	 * @throws Exception
	 */
	public int insertPrize(Prize prize)throws Exception;
	
	/**
	 * 根据报名人数获取对应奖励记录规则
	 * @param regedPlayerCount
	 * @return
	 * @throws Exception
	 */
	public List<Prize> getPrizeListByPlayerCount(long regedPlayerCount, int sysType)throws Exception;
}
