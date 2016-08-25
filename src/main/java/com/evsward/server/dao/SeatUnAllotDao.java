package com.evsward.server.dao;

import java.util.List;

import com.dance.core.orm.BaseDao;
import com.evsward.server.vo.UnAllotedSeat;

public interface SeatUnAllotDao extends BaseDao<UnAllotedSeat, Integer> {

	/**
	 * 获取比赛的一个待分配座位
	 * @param compID
	 * @return
	 * @throws Exception
	 */
	public UnAllotedSeat getUnAllotedSeatByCompID(int compID)throws Exception;
	
	/** 获取比赛的一个待分配座位,非tableNO桌
	 * @param compID
	 * @param tableNO
	 * @param size 获取座位的个数
	 * @return
	 * @throws Exception
	 */
	public List<UnAllotedSeat> getUnAllotedSeatByCompID(int compID, int tableNO, int size)throws Exception;
	
	/**
	 * 增加待分配座位
	 * @param compID
	 * @param unAllotedSeat
	 * @return
	 * @throws Exception
	 */
	public int addUnAllotedSeatOfComp(UnAllotedSeat unAllotedSeat)throws Exception;
	
	/**
	 * 获取比赛一个指定的待分配座位
	 * @param compID
	 * @param tableNO
	 * @param seatNO
	 * @return
	 * @throws Exception
	 */
	public UnAllotedSeat getSpecSeatByCompID(int compID, int tableNO, int seatNO)throws Exception;
	
	/**
	 * 删除一个比赛所有的待分配座位
	 * @param compID
	 * @return
	 * @throws Exception
	 */
	public int delUnAllotedSeatsByCompID(int compID)throws Exception;
	
	/**
	 * 删除一个桌子所有的待分配座位
	 * @param tableNO
	 * @return
	 * @throws Exception
	 */
	public int delUnAllotedSeatsByTableNO(int tableNO)throws Exception;
	
	/**
	 * 获取一个比赛所有待分配的座位
	 * @param compID
	 * @return
	 * @throws Exception
	 */
	public List<UnAllotedSeat> getUnAllotedSeatsByCompID(int compID)throws Exception;
	
	/**
	 * 删除比赛中一个指定的待分配座位
	 * @param compID
	 * @param tableNO
	 * @param seatNO
	 * @return
	 * @throws Exception
	 */
	public int delSpecUnAllotedSeat(int compID, int tableNO, int seatNO)throws Exception;
	
//	/**
//	 * 删除比赛中一个指定的待分配座位
//	 * @param tableNO
//	 * @param seatNO
//	 * @param compID
//	 * @return
//	 * @throws Exception
//	 */
//	public int delUnAllotedSeatByID(int tableNO, int seatNO, int compID)throws Exception;
}
