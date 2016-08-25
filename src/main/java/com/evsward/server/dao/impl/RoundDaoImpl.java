package com.evsward.server.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.dance.core.orm.mybatis.MyBatisDaoImpl;
import com.evsward.server.dao.RoundDao;
import com.evsward.server.vo.Round;

@Component("roundDao")
public class RoundDaoImpl extends MyBatisDaoImpl<Round, Integer> implements
		RoundDao {

	/**
	 * 根据级别、盲注模板ID查询盲注(非休息类型)
	 * @param rank
	 * @return
	 * @throws Exception
	 */
	public Round getRoundByRank(int roundTempID, int rank)throws Exception{
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("roundTempID", roundTempID);
		paramMap.put("roundRank", rank);
		paramMap.put("roundType", Round.ROUNDTYPE.TIME);
		return (Round)this.get("getRoundByRankAndTempID", paramMap);
	}
	
	/**
	 * 根据级别、盲注模板ID、盲注类型查询盲注
	 * @param roundTempID
	 * @param rank
	 * @param roundType
	 * @return
	 * @throws Exception
	 */
	public Round getRoundByRank(int roundTempID, int rank, int roundType)throws Exception{
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("roundTempID", roundTempID);
		paramMap.put("roundRank", rank);
		paramMap.put("roundType", roundType);
		return (Round)this.get("getRoundByRankAndTempID", paramMap);
	}
	
	/**
	 * 查询模板下所有盲注信息
	 * @param roundTempID
	 * @return
	 * @throws Exception
	 */
	public List<Round> getRoundListByTempID(int roundTempID)throws Exception{
		return this.find("getRoundListByTempID", roundTempID);
	}
	
	
	public Round getNextRound_Serv(int roundTempID, int curRank, int curType)throws Exception{
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("roundTempID", roundTempID);
		paramMap.put("curRank", curRank);
		paramMap.put("nextRank", curRank+1);
		List<Round> list = this.find("getNextRound_Serv", paramMap);
		if(curType == Round.ROUNDTYPE.TIME){//当前为计时盲注
			for (int i = 0; i < list.size(); i++) {
				if(list.get(i).getRoundrank() == curRank && list.get(i).getRoundType() == Round.ROUNDTYPE.REST){//当前盲注之后有休息时间，那就跳到休息
					return list.get(i);
				}
			}
			for (int i = 0; i < list.size(); i++) {
				if(list.get(i).getRoundrank() == curRank + 1 && list.get(i).getRoundType() == Round.ROUNDTYPE.TIME){//当前盲注之后没有休息时间，那就跳到下一个盲注
					return list.get(i);
				}
			}
		}else{//当前为休息盲注
			for (int i = 0; i < list.size(); i++) {
				if(list.get(i).getRoundrank() == curRank + 1 && list.get(i).getRoundType() == Round.ROUNDTYPE.TIME){//当前盲注为休息时间，那就跳到下一个盲注
					return list.get(i);
				}
			}
		}
		return null;
	}
}
