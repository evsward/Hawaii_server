package com.evsward.server.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dance.core.orm.BaseDao;
import com.dance.core.service.BaseServiceImpl;
import com.evsward.server.dao.CompHistoryLogDao;
import com.evsward.server.dao.CompetitionDao;
import com.evsward.server.dao.LockDao;
import com.evsward.server.dao.MemCompDao;
import com.evsward.server.dao.SeatAllotedDao;
import com.evsward.server.dao.SeatUnAllotDao;
import com.evsward.server.service.MemCompService;
import com.evsward.server.util.HIUtil;
import com.evsward.server.vo.AllotedSeat;
import com.evsward.server.vo.CompMemInfo;
import com.evsward.server.vo.CompMovedSeatLog;
import com.evsward.server.vo.CompetitionInfo;
import com.evsward.server.vo.CompetitionMember;
import com.evsward.server.vo.LockTable;
import com.evsward.server.vo.UnAllotedSeat;

@Component("memCompService")
public class MemCompServiceImp extends BaseServiceImpl<CompetitionMember, Integer> implements MemCompService {
	
	public interface RegCompResultState{
		/** 座位信息不存在 */
		public static final int RESULT_3 = -3;
		/** 选手已报该比赛，且未被淘汰 */
		public static final int RESULT_2 = -2;
		/** 分桌失败 */
		public static final int RESULT_1 = -1;
		/** 报名成功 */
		public static final int RESULT1 = 1;
	}
	
	/**
	 * 会员报名比赛结果
	 */
	public class RegCompResult{
		private int result;
		private int tableNO;
		private int seatNO;
		public int getResult() {
			return result;
		}
		public void setResult(int result) {
			this.result = result;
		}
		public int getTableNO() {
			return tableNO;
		}
		public void setTableNO(int tableNO) {
			this.tableNO = tableNO;
		}
		public int getSeatNO() {
			return seatNO;
		}
		public void setSeatNO(int seatNO) {
			this.seatNO = seatNO;
		}
		public RegCompResult(int result, int tableNO, int seatNO) {
			super();
			this.result = result;
			this.tableNO = tableNO;
			this.seatNO = seatNO;
		}
		public RegCompResult() {
			super();
		}
	}

	@Resource
	private MemCompDao memCompDao;
	@Resource
	private CompetitionDao compDao;
	@Resource
	private SeatUnAllotDao seatUnAllotDao;
	@Resource
	private SeatAllotedDao seatAllotedDao;
	@Resource
	private LockDao lockDao;
	@Resource
	private CompHistoryLogDao compHisLogDao;
	
	@Override
	public BaseDao<CompetitionMember, Integer> getDao() {
		return memCompDao;
	}
	
	/**
	 * 查询会员已报名的比赛
	 * @param memID
	 * @param sysType
	 * @return
	 * @throws Exception
	 */
	public List<CompetitionMember> getMemCompetitionInfos(int memID, int sysType)throws Exception{
		return this.memCompDao.getMemCompetionInfos(memID, sysType);
	}
	
	/**
	 * 获取比赛中是否还存活的选手，mcstate=1，2
	 * @param compID
	 * @return
	 * @throws Exception
	 */
	public boolean existMemOfRunningAndAdvanByCompID(int compID)throws Exception{
		List<CompetitionMember>	list = this.memCompDao.getMemOfRunningAndAdvanCompetionInfos(compID);
		if(list != null && list.size() > 0){
			return true;
		}
		return false;
	}
	
	/**
	 * 获取比赛存活选手信息
	 * @param compID
	 * @return
	 * @throws Exception
	 */
	public List<CompMemInfo> getCompMemsSeatInfo(int compID)throws Exception{
		return this.memCompDao.getCompMemsSeatInfo(compID);
	}

	/**
	 * 指定座位信息，报名比赛
	 * @param memID
	 * @param compID
	 * @param specSeat
	 * @param compInfo
	 * @return
	 * @throws Exception
	 */
	@Transactional(propagation=Propagation.REQUIRED, isolation=Isolation.DEFAULT)
	public RegCompResult insertMemRegCompetitionWithSpecSeat(int memID, int compID, CompetitionInfo compInfo, UnAllotedSeat specSeat)throws Exception{
		this.lockDao.getLock(LockTable.LOCKTYPE.SEATLOCK);
		RegCompResult result = null;
		int res = 0;
		//查询会员与该比赛的关系信息
		List<CompetitionMember> cmList = this.memCompDao.findMemCompetitionInfos(memID, compID, compInfo.getSysType());
		if(cmList != null && cmList.size() > 0){
			throw new RuntimeException("会员已经报名该比赛，且未淘汰memID="+memID+",compID="+compID);
		}
		//获取一个指定位置
		res = this.seatUnAllotDao.delSpecUnAllotedSeat(compID, specSeat.getTableNO(), specSeat.getSeatNO());
		if(res <= 0){//指定座位不存在
			return new RegCompResult(RegCompResultState.RESULT_3, 0, 0);
		}
		this.seatAllotedDao.insertAllotedSeat(new AllotedSeat(specSeat.getTableNO(), specSeat.getSeatNO(), compID, memID, new Date()));
		UnAllotedSeat unAllotedSeat = new UnAllotedSeat(specSeat.getTableNO(), specSeat.getSeatNO(), compID);
		CompetitionMember cm = new CompetitionMember(memID, compID, CompetitionMember.MemCompState.REGED, unAllotedSeat.getTableNO(), unAllotedSeat.getSeatNO(), compInfo.getBeginChip(), compInfo.getSysType(), new Date());
		//memcompinfo表添加一条记录
		this.memCompDao.saveMemCompRelation(cm);
		this.compDao.addRegedPlayer(compID);
		result = new RegCompResult(RegCompResultState.RESULT1, unAllotedSeat.getTableNO(), unAllotedSeat.getSeatNO());
		return result;
	}

	
	/**
	 * 随机分座报名比赛，如果比赛不使用分桌系统，则只报名不分桌。
	 * @param memID
	 * @param compID
	 * @param compInfo
	 * @return
	 * @throws Exception
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED, isolation=Isolation.DEFAULT)
	public RegCompResult insertMemRegCompetitionRandomAllotSeat(int memID, int compID, CompetitionInfo compInfo)throws Exception{
		this.lockDao.getLock(LockTable.LOCKTYPE.SEATLOCK);
		RegCompResult result = null;
		//查询会员与该比赛的关系信息
		List<CompetitionMember> cmList = this.memCompDao.findMemCompetitionInfos(memID, compID, compInfo.getSysType());
		if(cmList != null && cmList.size() > 0){
			result = new RegCompResult(RegCompResultState.RESULT_2, 0, 0);
			return result;
		}
		//随机获取一个位置信息
		UnAllotedSeat unAllotedSeat = this.seatUnAllotDao.getUnAllotedSeatByCompID(compID);
		if(unAllotedSeat != null){
			int res = this.seatUnAllotDao.delSpecUnAllotedSeat(unAllotedSeat.getCompID(),unAllotedSeat.getTableNO(), unAllotedSeat.getSeatNO());
			if(res <= 0){
				throw new RuntimeException("随机分座位，报名失败");
			}
			this.seatAllotedDao.insertAllotedSeat(new AllotedSeat(unAllotedSeat.getTableNO(), unAllotedSeat.getSeatNO(), compID, memID, new Date()));
		}else{
			result = new RegCompResult(RegCompResultState.RESULT_1, 0, 0);
			return result;
		}
		CompetitionMember cm = new CompetitionMember(memID, compID, CompetitionMember.MemCompState.REGED, unAllotedSeat.getTableNO(), unAllotedSeat.getSeatNO(), compInfo.getBeginChip(), compInfo.getSysType(), new Date());
		//memcompinfo表添加一条记录
		this.memCompDao.saveMemCompRelation(cm);
		this.compDao.addRegedPlayer(compID);
		result = new RegCompResult(RegCompResultState.RESULT1, unAllotedSeat.getTableNO(), unAllotedSeat.getSeatNO());
		return result;
	}
	
	/**
	 * 不使用分桌系统报名比赛。
	 * @param memID
	 * @param compID
	 * @param compInfo
	 * @return
	 * @throws Exception
	 */
	@Transactional(propagation=Propagation.REQUIRED, isolation=Isolation.DEFAULT)
	public RegCompResult insertMemRegCompetitionWithoutAssignSeat(int memID, int compID, CompetitionInfo compInfo)throws Exception{
		RegCompResult result = null;
		//查询会员与该比赛的关系信息
		List<CompetitionMember> cmList = this.memCompDao.findMemCompetitionInfos(memID, compID, compInfo.getSysType());
		if(cmList != null && cmList.size() > 0){
			result = new RegCompResult(RegCompResultState.RESULT_2, 0, 0);
			return result;
		}
		CompetitionMember cm = new CompetitionMember(memID, compID, CompetitionMember.MemCompState.REGED, 0, 0, compInfo.getBeginChip(), compInfo.getSysType(), new Date());
		//memcompinfo表添加一条记录
		this.memCompDao.saveMemCompRelation(cm);
		result = new RegCompResult(RegCompResultState.RESULT1, 0, 0);
		return result;
	}
	
	/**
	 * 平衡牌桌选手
	 * @param cmID
	 * @param compID
	 * @param memID
	 * @param tableNO
	 * @param seatNO
	 * @return
	 * @throws Exception
	 */
	@Transactional(propagation=Propagation.REQUIRED, isolation=Isolation.DEFAULT)
	public int updateBalanceCompMember(int cmID, int compID, int memID, int tableNO, int seatNO, String empUuid)throws Exception{
		this.lockDao.getLock(LockTable.LOCKTYPE.SEATLOCK);
		CompetitionInfo compInfo = this.compDao.getCompInfoByCompID(compID);
		CompetitionMember cm = this.memCompDao.getCompMemByID(cmID);
		if(cm == null || cm.getCompID() != compID || cm.getMemID() != memID || cm.getMcState() != CompetitionMember.MemCompState.REGED){//选手不存在
			return 0;
		}else if(cm.getTableNO() == tableNO){//平衡的牌桌和原桌号相同
			return -1;
		}
		int res = this.seatUnAllotDao.delSpecUnAllotedSeat(compID, tableNO, seatNO);
		if(res <= 0){//指定的座位不存在
			throw new RuntimeException("平衡选手失败,指定座位不存在，compID="+compID+",cmID="+cmID+",newtableNO="+tableNO+",newseatNO="+seatNO);
		}
		this.seatUnAllotDao.addUnAllotedSeatOfComp(new UnAllotedSeat(cm.getTableNO(), cm.getSeatNO(), compID, new Date().getTime(), HIUtil.getSeatLevel(cm.getSeatNO(), compInfo.getTableType()), compInfo.getTableType(), compInfo.getSysType()));
		this.seatAllotedDao.deleteByTableSeatNO(cm.getTableNO(), cm.getSeatNO());
		this.seatAllotedDao.insertAllotedSeat(new AllotedSeat(tableNO, seatNO, compID, memID, new Date()));
		Date date = new Date();
		this.compHisLogDao.insert(new CompMovedSeatLog(compID, cm.getMemID(), cm.getTableNO(), cm.getSeatNO(), empUuid, tableNO, seatNO, CompMovedSeatLog.OPTYPE.BALANCE, date.getTime(), compInfo.getSysType()));
		res = this.memCompDao.updateMemSeatInfo(cmID, tableNO, seatNO);
		if(res <= 0){//平衡选手位置失败
			throw new RuntimeException("平衡选手,更新选手比赛关系座位信息失败，compID="+compID+",cmID="+cmID+",newtableNO="+tableNO+",newseatNO="+seatNO);
		}
		return 1;
	}
	
	/**
	 * 更新选手的筹码数量
	 * @param mcID
	 * @param compID
	 * @param memID
	 * @param chip
	 * @return
	 * @throws Exception
	 */
	public int updatePlayerChip(int mcID, int compID, int memID, int chip)throws Exception{
		CompetitionMember cm = this.memCompDao.getCompMemByID(mcID);
		if(cm == null){
			throw new RuntimeException("更新选手筹码，信息不存在，mcID="+mcID);
		}
		if(cm.getMemID() != memID || cm.getCompID() != compID){
			String str = "更新选手筹码，会员信息不匹配，mcID=%s, memID=%s, compID=%s, cm.memID=%s, cm.compID=%s";
			throw new RuntimeException(String.format(str, mcID, memID, compID, cm.getMemID(), cm.getCompID()));
		}
		return this.memCompDao.updatePlayerChipByID(mcID, chip);
	}
	
	/**
	 * 查询比赛中晋级的选手
	 * @param compID
	 * @return
	 * @throws Exception
	 */
	public List<CompetitionMember> getAllAdvanMemByCompID(int compID)throws Exception{
		return this.memCompDao.getAdvanCompMemByCompID(compID);
	}
}
