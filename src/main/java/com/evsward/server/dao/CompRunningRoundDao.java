package com.evsward.server.dao;

import com.dance.core.orm.BaseDao;
import com.evsward.server.vo.CompRunningRound;

public interface CompRunningRoundDao extends BaseDao<CompRunningRound, Integer> {

	/**
	 * 获取比赛的运行盲注信息
	 * @param compID
	 * @return
	 * @throws Exception
	 */
	public CompRunningRound getRunningRoundByCompID(int compID)throws Exception;
	
	/**
	 * 删除比赛当前运行盲注
	 * @param compID
	 * @return
	 * @throws Exception
	 */
	public int delRunningRoundByCompID(int compID)throws Exception;
	
	/**
	 * 插入比赛当前运行盲注
	 * @param runningRound
	 * @return
	 * @throws Exception
	 */
	public int insertRunningRound(CompRunningRound runningRound)throws Exception;

	/**
	 * 盲注倒计时时间调整
	 * @param compID
	 * @param reStartTime 单位毫秒
	 * @return
	 * @throws Exception
	 */
	public int updateRunningRoundTime(int compID, long reStartTime)throws Exception;
	
	/**
	 * 比赛充暂停中恢复运行状态，需要更新reStartTime，保证盲注的步进还可以正常进行
	 * @param compID
	 * @param reStartTime
	 * @return
	 * @throws Exception
	 */
	public int updateRunningRoundReStartTime(int compID, long reStartTime)throws Exception;
}
