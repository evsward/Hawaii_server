package com.evsward.server.dao;

import java.util.List;

import com.dance.core.orm.BaseDao;
import com.evsward.server.vo.Round;

public interface RoundDao extends BaseDao<Round, Integer> {

	/**
	 * 根据级别、盲注模板ID查询盲注(非休息类型)
	 * @param roundTempID
	 * @param rank
	 * @return
	 * @throws Exception
	 */
	public Round getRoundByRank(int roundTempID, int rank)throws Exception;
	
	/**
	 * 根据级别、盲注模板ID、盲注类型查询盲注
	 * @param roundTempID
	 * @param rank
	 * @param roundType
	 * @return
	 * @throws Exception
	 */
	public Round getRoundByRank(int roundTempID, int rank, int roundType)throws Exception;
	
	/**
	 * 查询模板下所有盲注信息
	 * @param roundTempID
	 * @return
	 * @throws Exception
	 */
	public List<Round> getRoundListByTempID(int roundTempID)throws Exception;
	
	/**
	 * 服务端比赛维护线程，正常跳忙，查询下一个盲注信息，如果下一个是休息，则休息
	 * @param roundTempID
	 * @param curRank
	 * @return
	 * @throws Exception
	 */
	public Round getNextRound_Serv(int roundTempID, int curRank, int curType)throws Exception;
}
