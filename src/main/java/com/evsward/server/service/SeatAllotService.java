package com.evsward.server.service;

import java.util.List;

import com.dance.core.service.BaseService;
import com.evsward.server.vo.UnAllotedSeat;

public interface SeatAllotService extends BaseService<UnAllotedSeat, Integer> {
	
	/**
	 * 获取比赛的一个待分配座位,同步方法
	 * @param compID
	 * @return
	 * @throws Exception
	 */
	public UnAllotedSeat getUnAllotedSeatOfComp(int compID)throws Exception;
	
	/**
	 * 获取比赛一个指定的待分配座位,同步方法
	 * @param compID
	 * @param tableNO
	 * @param seatNO
	 * @return
	 * @throws Exception
	 */
	public UnAllotedSeat getSpecSeat(int compID, int tableNO, int seatNO)throws Exception;
	
	/**
	 * 删除一个牌桌的所有待分配座位
	 * @param tableNO
	 * @return
	 * @throws Exception
	 */
	public int deleteUnAllotedSeatsByTableNO(int tableNO)throws Exception;
	
	/**
	 * 查询比赛所有待分配的座位
	 * @param compID
	 * @return
	 * @throws Exception
	 */
	public List<UnAllotedSeat> getAllUnAllotedSeatOfComp(int compID)throws Exception;
}
