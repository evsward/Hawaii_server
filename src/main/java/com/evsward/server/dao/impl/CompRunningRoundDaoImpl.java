package com.evsward.server.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.dance.core.orm.mybatis.MyBatisDaoImpl;
import com.evsward.server.dao.CompRunningRoundDao;
import com.evsward.server.vo.CompRunningRound;

@Component("runningRoundDao")
public class CompRunningRoundDaoImpl extends MyBatisDaoImpl<CompRunningRound, Integer> implements CompRunningRoundDao {

	/**
	 * 获取比赛的运行盲注信息
	 * @param compID
	 * @return
	 * @throws Exception
	 */
	public CompRunningRound getRunningRoundByCompID(int compID)throws Exception{
		return (CompRunningRound)this.get("getRunningRoundByCompID", compID);
	}
	
	/**
	 * 删除比赛当前运行盲注
	 * @param compID
	 * @return
	 * @throws Exception
	 */
	public int delRunningRoundByCompID(int compID)throws Exception{
		return this.delete("delRunningRoundByCompID", compID);
	}
	
	/**
	 * 插入比赛当前运行盲注
	 * @param runningRound
	 * @return
	 * @throws Exception
	 */
	public int insertRunningRound(CompRunningRound runningRound)throws Exception{
		return (Integer)this.insert("insertRunningRound", runningRound);
	}
	
	/**
	 * 
	 * @param compID
	 * @param reStartTime 单位毫秒
	 * @return
	 * @throws Exception
	 */
	public int updateRunningRoundTime(int compID, long reStartTime)throws Exception{
		Map<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("compID", compID);
		paraMap.put("reStartTime", reStartTime);
		return this.update("updateRunningRoundReStartTime", paraMap);
	}
	
	/**
	 * 比赛充暂停中恢复运行状态，需要更新reStartTime，保证盲注的步进还可以正常进行
	 * @param compID
	 * @return
	 * @throws Exception
	 */
	public int updateRunningRoundReStartTime(int compID, long reStartTime)throws Exception{
		Map<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("compID", compID);
		paraMap.put("reStartTime",reStartTime);
		return this.update("updateRunningRoundReStartTime", paraMap);
	}
}
