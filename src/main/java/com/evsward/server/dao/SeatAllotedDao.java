package com.evsward.server.dao;

import com.dance.core.orm.BaseDao;
import com.evsward.server.vo.AllotedSeat;

public interface SeatAllotedDao extends BaseDao<AllotedSeat, Integer> {

	/**
	 * 添加一条已分座位记录
	 * @param allotedSeat
	 * @return
	 * @throws Exception
	 */
	public int insertAllotedSeat(AllotedSeat allotedSeat)throws Exception;
	
	/**
	 * 删除比赛所有已分配的座位信息
	 * @param compID
	 * @return
	 * @throws Exception
	 */
	public int deleteAllByCompID(int compID)throws Exception;
	
	/**
	 * 根据双主键，删除已分出座位记录
	 * @param tableNO
	 * @param seatNO
	 * @return
	 * @throws Exception
	 */
	public int deleteByTableSeatNO(int tableNO, int seatNO)throws Exception;
	
	/**
	 * 删除比赛中某张牌桌的所有已分座位
	 * @param compID
	 * @param tableNO
	 * @return
	 * @throws Exception
	 */
	public int deleteByTableNO(int compID, int tableNO)throws Exception;
}
