package com.evsward.server.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.dance.core.orm.mybatis.MyBatisDaoImpl;
import com.evsward.server.dao.SeatAllotedDao;
import com.evsward.server.vo.AllotedSeat;

@Component("seatAllotedDao")
public class SeatAllotedDaoImpl extends MyBatisDaoImpl<AllotedSeat, Integer> implements
		SeatAllotedDao {

	/**
	 * 添加一条已分座位记录
	 * @param allotedSeat
	 * @return
	 * @throws Exception
	 */
	public int insertAllotedSeat(AllotedSeat allotedSeat)throws Exception{
		return (Integer)this.insert("insert", allotedSeat);
	}
	
	/**
	 * 删除比赛所有已分配的座位信息
	 * @param compID
	 * @return
	 * @throws Exception
	 */
	public int deleteAllByCompID(int compID)throws Exception{
		return this.delete("delAllByCompID", compID);
	}
	
	/**
	 * 根据双主键，删除已分出座位记录
	 * @param tableNO
	 * @param seatNO
	 * @return
	 * @throws Exception
	 */
	public int deleteByTableSeatNO(int tableNO, int seatNO)throws Exception{
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("tableNO", tableNO);
		paramMap.put("seatNO", seatNO);
		return this.delete("deleteByTableSeatNO", paramMap);
	}
	
	/**
	 * 删除比赛中某张牌桌的所有已分座位
	 * @param compID
	 * @param tableNO
	 * @return
	 * @throws Exception
	 */
	public int deleteByTableNO(int compID, int tableNO)throws Exception{
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("compID", compID);
		paramMap.put("tableNO", tableNO);
		return this.delete("deleteByTableNO", paramMap);
	}
}
