package com.evsward.server.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.dance.core.orm.mybatis.MyBatisDaoImpl;
import com.evsward.server.dao.SeatUnAllotDao;
import com.evsward.server.vo.UnAllotedSeat;

@Component("seatUnAllotDao")
public class SeatUnAllotDaoImpl extends MyBatisDaoImpl<UnAllotedSeat, Integer> implements SeatUnAllotDao {

	/**
	 * 获取比赛的一个待分配座位
	 */
	@Override
	public synchronized UnAllotedSeat getUnAllotedSeatByCompID(int compID) throws Exception {
		return (UnAllotedSeat)this.get("getUnAllotedSeatByCompID", compID);
	}
	
	/**
	 * 获取比赛的一个待分配座位,非tableNO桌
	 * @param compID
	 * @param tableNO
	 * @param size 获取座位的个数
	 * @return
	 * @throws Exception
	 */
	public List<UnAllotedSeat> getUnAllotedSeatByCompID(int compID, int tableNO, int size)throws Exception{
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("compID", compID);
		paramMap.put("tableNO", tableNO);
		paramMap.put("size", size);
		return this.find("getUnAllotedSeatByCompIDBurst", paramMap);
	}

	@Override
	public int addUnAllotedSeatOfComp(UnAllotedSeat unAllotedSeat)
			throws Exception {
		int num = 0;
		num += (Integer)this.insert("insert", unAllotedSeat);
		return num;
	}

	@Override
	public UnAllotedSeat getSpecSeatByCompID(int compID, int tableNO, int seatNO) throws Exception {
		UnAllotedSeat unAllotedSeat = new UnAllotedSeat(tableNO, seatNO, compID);
		return (UnAllotedSeat)this.get("selectUnAllotedSpecSeatInfo", unAllotedSeat);
	}

	@Override
	public int delUnAllotedSeatsByCompID(int compID) throws Exception {
		int num = 0;
		num += this.delete("delUnAllotedSeatsByCompID", compID);
		return num;
	}

	@Override
	public List<UnAllotedSeat> getUnAllotedSeatsByCompID(int compID)
			throws Exception {
		return this.find("getUnAllotedSeatsByCompID", compID);
	}

	/**
	 * 删除一个桌子所有的待分配座位
	 * @param tableNO
	 * @return
	 * @throws Exception
	 */
	public int delUnAllotedSeatsByTableNO(int tableNO)throws Exception{
		return this.delete("delUnAllotedSeatsByTableNO", tableNO);
	}
	
	/**
	 * 删除比赛中一个指定的待分配座位
	 * @param compID
	 * @param tableNO
	 * @param seatNO
	 * @return
	 * @throws Exception
	 */
	public int delSpecUnAllotedSeat(int compID, int tableNO, int seatNO)throws Exception{
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("compID", compID);
		paramMap.put("tableNO", tableNO);
		paramMap.put("seatNO", seatNO);
		return this.delete("delSpecUnAllotedSeat", paramMap);
	}
	
//	/**
//	 * 删除比赛中一个指定的待分配座位
//	 * @param id
//	 * @return
//	 * @throws Exception
//	 */
//	public int delUnAllotedSeatByID(int id)throws Exception{
//		return this.delete("delUnAllotedSeatByID", id);
//	}
}
