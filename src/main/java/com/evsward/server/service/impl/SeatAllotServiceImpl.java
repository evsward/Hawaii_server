package com.evsward.server.service.impl;

import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dance.core.orm.BaseDao;
import com.dance.core.service.BaseServiceImpl;
import com.evsward.server.dao.SeatUnAllotDao;
import com.evsward.server.service.SeatAllotService;
import com.evsward.server.vo.UnAllotedSeat;

/**
 * 这个类是同步类,不能自己new出来对象，必须通过spring调用
 */
@Component("seatAllotService")
public class SeatAllotServiceImpl extends BaseServiceImpl<UnAllotedSeat, Integer> implements SeatAllotService{

	public SeatAllotServiceImpl(){
		
	}
	private static Logger logger = LoggerFactory.getLogger(SeatAllotServiceImpl.class);
	
	
	@Resource
	private SeatUnAllotDao seatUnAllotDao;
	@Override
	public BaseDao<UnAllotedSeat, Integer> getDao() {
		return seatUnAllotDao;
	}

	@Transactional(propagation=Propagation.REQUIRED, isolation=Isolation.DEFAULT)
	public UnAllotedSeat getUnAllotedSeatOfComp(int compID)throws Exception{
		UnAllotedSeat seat = this.seatUnAllotDao.getUnAllotedSeatByCompID(compID);
		if(seat != null){
			this.seatUnAllotDao.delete(seat);
		}
		return seat;
	}
	@Transactional(propagation=Propagation.REQUIRED, isolation=Isolation.DEFAULT)
	public UnAllotedSeat getSpecSeat(int compID, int tableNO, int seatNO)throws Exception{
		return this.seatUnAllotDao.getSpecSeatByCompID(compID, tableNO, seatNO);
	}
	
	/**
	 * 删除一个牌桌的所有待分配座位
	 * @param tableNO
	 * @return
	 * @throws Exception
	 */
	public int deleteUnAllotedSeatsByTableNO(int tableNO)throws Exception{
		return this.seatUnAllotDao.delUnAllotedSeatsByTableNO(tableNO);
	}
	
	/**
	 * 查询比赛所有待分配的座位
	 * @param compID
	 * @return
	 * @throws Exception
	 */
	public List<UnAllotedSeat> getAllUnAllotedSeatOfComp(int compID)throws Exception{
		List<UnAllotedSeat> list = this.seatUnAllotDao.getUnAllotedSeatsByCompID(compID);
		if(list == null){
			return Collections.EMPTY_LIST;
		}
		return list;
	}
}
