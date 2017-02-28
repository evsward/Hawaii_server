package com.evsward.server.service.impl;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dance.core.orm.BaseDao;
import com.dance.core.service.BaseServiceImpl;
import com.evsward.server.dao.CardTableDao;
import com.evsward.server.dao.CompHistoryLogDao;
import com.evsward.server.dao.CompImportLogDao;
import com.evsward.server.dao.CompRunningPrizeDao;
import com.evsward.server.dao.CompRunningRoundDao;
import com.evsward.server.dao.CompetitionDao;
import com.evsward.server.dao.LockDao;
import com.evsward.server.dao.MemCompDao;
import com.evsward.server.dao.MemberDao;
import com.evsward.server.dao.RoundDao;
import com.evsward.server.dao.SeatAllotedDao;
import com.evsward.server.dao.SeatUnAllotDao;
import com.evsward.server.service.CompetitionService;
import com.evsward.server.util.DateUtils;
import com.evsward.server.util.RspCodeValue;
import com.evsward.server.util.HIUtil;
import com.evsward.server.vo.AllotedSeat;
import com.evsward.server.vo.CardTable;
import com.evsward.server.vo.CompImportLog;
import com.evsward.server.vo.CompMemInfo;
import com.evsward.server.vo.CompMovedSeatLog;
import com.evsward.server.vo.CompRunningPrize;
import com.evsward.server.vo.CompRunningRound;
import com.evsward.server.vo.CompetitionInfo;
import com.evsward.server.vo.CompetitionMember;
import com.evsward.server.vo.LockTable;
import com.evsward.server.vo.MemberInfo;
import com.evsward.server.vo.Prize;
import com.evsward.server.vo.Round;
import com.evsward.server.vo.UnAllotedSeat;
import com.evsward.server.vo.compmanage.BurstTableNewSeatInfo;
import com.evsward.server.vo.compmanage.CompBurstTableRes;
import com.evsward.server.vo.compmanage.CompManPlayerInfo;
import com.evsward.server.vo.compmanage.CompManPrizeInfo;
import com.evsward.server.vo.compmanage.EditSubPlayerVO;
import com.evsward.server.vo.compmanage.PrizePrintInfo;
import com.evsward.server.vo.screen.ScreenCompInfo;
import com.evsward.server.vo.screen.ScreenCompPlayerInfo;

@Component("compService")
public class CompetitionServiceImpl extends
		BaseServiceImpl<CompetitionInfo, Integer> implements CompetitionService {

	/**
	 * 新建比赛信息
	 * @param compInfo
	 * @throws Exception
	 */
	@Override
	public void createCompetition(CompetitionInfo compInfo)throws Exception{
		this.compDao.createCompetition(compInfo);
	}
	
	/**
	 * 编辑比赛信息
	 * @param compInfo
	 * @throws Exception
	 */
	@Override
	public void editCompetionInfo(CompetitionInfo compInfo)throws Exception{
		this.compDao.editCompetionInfoByID(compInfo);
	}

	/**
	 * 判断比赛名称是否已经存在
	 * @param compName
	 * @return
	 * @throws Exception
	 */
	@Override
	public boolean compNameExist(String compName) throws Exception {
		CompetitionInfo compInfo = (CompetitionInfo)this.compDao.getCompInfoByCompName(compName);
		if(compInfo != null){
			return true;
		}
		return false;
	}
	
	/**
	 * 根据比赛ID查询比赛信息
	 * @param compID
	 * @return
	 * @throws Exception
	 */
	@Override
	public CompetitionInfo getCompInfoByCompID(int compID)throws Exception{
		CompetitionInfo compInfo = (CompetitionInfo)this.compDao.getCompInfoByCompID(compID);
		return compInfo;
	}
	
	/**
	 * 查询所有比赛(不包括已删除比赛)，带有当前运行盲注信息
	 * @param sysType
	 * @return
	 * @throws Exception
	 */
	public List<CompetitionInfo> getAllCompWithRunningRound(int sysType)throws Exception{
		List<CompetitionInfo> list = this.compDao.getAllCompetitionListNoDel(sysType);
		if(list != null && list.size() > 0){
			for (int i = 0; i < list.size(); i++) {
				CompetitionInfo info = list.get(i);
				info.setCompRunningRound(this.runningRoundDao.getRunningRoundByCompID(info.getCompID()));
			}
		}
		return list;
	}
	
	/**
	 * 查询所有比赛(包括已删除比赛)
	 * @param sysType
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<CompetitionInfo> getAllCompetitionList(int sysType)throws Exception{
		return this.compDao.getAllCompetitionList(sysType);
	}
	
	/**
	 * 查询当天未结束比赛
	 * @param sysType
	 * @return
	 * @throws Exception
	 */
	public List<CompetitionInfo> getOndaynoEndComps(int sysType)throws Exception{
		Date onday = new Date();
		String dayStr = DateUtils.formatDate(onday, DateUtils.PATTERN2);
		Date start = DateUtils.getFormatDate(dayStr + " 00:00:00", DateUtils.PATTERN1);
		Date end = DateUtils.getFormatDate(dayStr + " 23:59:59", DateUtils.PATTERN1);
		return this.compDao.getNoEndCompsInTimeAreaNoDelNoEnd(start, end, sysType);
	}
	
	/**
	 * 根据比赛ID查询比赛信息
	 * @param compID
	 * @return
	 * @throws Exception
	 */
	public CompetitionInfo getCompetitionInfoByCompID(int compID)throws Exception{
		return this.compDao.getCompInfoByCompID(compID);
	}
	
	@Override
	public int getCompAllRegedPlayerCount(int compID) throws Exception {
		CompetitionInfo compInfo = (CompetitionInfo)this.compDao.getCompInfoByCompID(compID);
		if(compInfo == null){
			return 0;
		}
		return compInfo.getTotalPlayer();
	}
	
	/**
	 * 查询比赛存活选手数量
	 * @param compID
	 * @return
	 * @throws Exception
	 */
	public int getCompAllLivedPlayerCount(int compID)throws Exception{
		return (int)this.memCompDao.findCompLivedTotalPlayerCount(compID);
	}
	
	/**
	 * 比赛管理--玩家，查询存活的玩家信息（包括已晋级的）
	 * @param compID
	 * @return
	 * @throws Exception
	 */
	public CompManPlayerInfo getLivedPlayersByComp(int compID)throws Exception{
		CompetitionInfo compInfo = this.compDao.getCompInfoByCompID(compID);
		List<CompMemInfo> list = this.memCompDao.getCompPlayerInfo(compID, 1);
		int totalRegedPlayer = compInfo.getTotalPlayer();
		int totalRegedPlayerEdit = totalRegedPlayer - compInfo.getSubPlayerCount();
		int totalChip = totalRegedPlayer * compInfo.getBeginChip();
		int totalChipEdit = totalRegedPlayerEdit * compInfo.getBeginChip();
		CompManPlayerInfo playersInfo = new CompManPlayerInfo(totalRegedPlayer, totalRegedPlayerEdit, totalChip, totalChipEdit, list);
		return playersInfo;
	}
	
	/**
	 * 比赛管理--筹码，查询存活的玩家筹码信息（包括已晋级的）
	 * @param compID
	 * @return
	 * @throws Exception
	 */
	public List<CompMemInfo> getLivedPlayerChipByComp(int compID)throws Exception{
		return this.memCompDao.getCompPlayerChip(compID);
	}
	
	/**
	 * 比赛管理--筹码，通过nfc卡查询指定存活玩家筹码信息
	 * @param compID
	 * @param nfcID
	 * @return
	 * @throws Exception
	 */
	public List<CompMemInfo> getSpecPlayerChipOfCompByNfcID(int compID, long nfcID)throws Exception{
		return this.memCompDao.findSpecPlayerChipOfCompByNfcID(compID, nfcID);
	}
	
	/**
	 * 比赛管理--筹码，通过cardNO查询指定存活玩家筹码信息
	 * @param compID
	 * @param nfcID
	 * @return
	 * @throws Exception
	 */
	public  List<CompMemInfo> getSpecPlayerChipOfCompByCardNO(int compID, String cardNO)throws Exception{
		return this.memCompDao.findSpecPlayerChipOfCompByCardNO(compID, cardNO);
	}
	
	
	/**
	 * 查询所有未结束，未删除的比赛，后初始化比赛后台管理线程服务。
	 * @return
	 * @throws Exception
	 */
	public List<CompetitionInfo> getCompInfoListForInit_Serv()throws Exception{
		return this.compDao.getAllCompetitionList(1);
	}
	

	/**
	 * 获取奖励小条的待打印信息
	 * @param ranking
	 * @param compID
	 * @return
	 * @throws Exception
	 */
	public PrizePrintInfo getPrizePrintInfo(int ranking, int compID)throws Exception{
		CompRunningPrize runningPrize = this.runningPrizeDao.getCompRunningPrize(ranking, compID);
		PrizePrintInfo prizePrintInfo = null;
		if(runningPrize != null){
			CompetitionInfo compInfo = this.compDao.getCompInfoByCompID(runningPrize.getCompID());
			MemberInfo memInfo = new MemberInfo();
			memInfo.setMemID(runningPrize.getMemID());
			memInfo = this.memDao.getExactMemInfo(memInfo);
			prizePrintInfo = new PrizePrintInfo();
			prizePrintInfo.setCompStartTime(DateUtils.formatDate(compInfo.getStartTime(), DateUtils.PATTERN1));
			prizePrintInfo.setCompID(compInfo.getCompID());
			prizePrintInfo.setCompName(compInfo.getCompName());
			prizePrintInfo.setUnit(compInfo.getAmountUnit());
			prizePrintInfo.setMemID(memInfo.getMemID());
			prizePrintInfo.setMemName(memInfo.getMemName());
			prizePrintInfo.setMemSex(memInfo.getMemSex());
			prizePrintInfo.setMemIdentNO(memInfo.getMemIdentNO());
			prizePrintInfo.setCardNO(memInfo.getCardNO());
			prizePrintInfo.setAmountInt(runningPrize.getAmountInt());
			prizePrintInfo.setRanking(runningPrize.getRanking());
			prizePrintInfo.setTableNO(runningPrize.getTableNO());
			prizePrintInfo.setSeatNO(runningPrize.getSeatNO());
		}
		return prizePrintInfo;
	}
	

	/**
	 * 奖池，查询奖励信息
	 * @param compID
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getCompMemPrizeInfoList(int compID)throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		List<CompManPrizeInfo> result = new ArrayList<CompManPrizeInfo>();
		CompManPrizeInfo compManPrizeInfo = null;
		CompRunningPrize compRunningPrize = null;
		CompetitionInfo compInfo = this.compDao.getCompInfoByCompID(compID);
		if(compInfo == null){
			map = HIUtil.createJsonResInitMap(RspCodeValue.$405.getRspCode(), RspCodeValue.$405.getMsg());
			return map;
		}else if(compInfo.getAword() == CompetitionInfo.AWORDSTATE.NOAWORD){
			map = HIUtil.createJsonResInitMap(RspCodeValue.$478.getRspCode(), RspCodeValue.$478.getMsg());
			return map;
		}
		long totalRegedPlayer = compInfo.getTotalPlayer();
		long totalRegedPlayerEdit = totalRegedPlayer - compInfo.getSubPlayerCount();
		if(totalRegedPlayerEdit < 0){
			totalRegedPlayerEdit = 0;
		}
		//计算总奖金
		long totalPrize = HIUtil.calcCompTotalPrizeMoney(compInfo.getLeastPrize(), compInfo.getRegFee(), compInfo.getTotalPlayer());
		//减去扣除人数后，计算的总奖金
		long totalPrizeEdit = HIUtil.calcCompTotalPrizeMoney(compInfo.getLeastPrize(), compInfo.getRegFee(), compInfo.getTotalPlayer(), compInfo.getSubPlayerCount());
		long prizeTotalPlayer = 0;//奖励总人数
		List<Prize> list = HIUtil.getPrizeListByPlayerCount(totalRegedPlayerEdit);
		if(list != null && list.size() > 0){
			prizeTotalPlayer = list.size();
		}
		List<CompRunningPrize> compRunningPrizePlayerList = this.runningPrizeDao.getExistCompRunningPrizeListWithMemInfo(compID);
		if(compRunningPrizePlayerList != null){
			for (int i = 0; i < compRunningPrizePlayerList.size(); i++) {
				compRunningPrize = compRunningPrizePlayerList.get(i);
				compManPrizeInfo = new CompManPrizeInfo();
				compManPrizeInfo.setMemID(compRunningPrize.getMemID());
				compManPrizeInfo.setMemName(compRunningPrize.getMemName());
				compManPrizeInfo.setMemIdentNO(compRunningPrize.getMemIdentNO());
				compManPrizeInfo.setCompID(compID);
				compManPrizeInfo.setTableNO(compRunningPrize.getTableNO());
				compManPrizeInfo.setSeatNO(compRunningPrize.getSeatNO());
				compManPrizeInfo.setRanking(compRunningPrize.getRanking());
				compManPrizeInfo.setPercent(HIUtil.formatMoney(compRunningPrize.getPercent()*100, "#.##"));
				compManPrizeInfo.setAmountInt(compRunningPrize.getAmountInt());
				compManPrizeInfo.setMemSex(compRunningPrize.getMemSex());
				result.add(compManPrizeInfo);
			}
		}
		map.put("totalRegedPlayer", totalRegedPlayer);
		map.put("totalRegedPlayerEdit", totalRegedPlayerEdit);
		map.put("totalPrize", totalPrize);
		map.put("totalPrizeEdit", totalPrizeEdit);
		map.put("prizeTotalPlayer", prizeTotalPlayer);
		map.put("compManPrizeInfoList", result);
		map.put("rspCode", RspCodeValue.$1.getRspCode());
		map.put("msg", RspCodeValue.$1.getMsg());
		return map;
	}

	/**
	 * 查询比赛运行奖励表中的一条记录
	 * @param runningPrizeID
	 * @param compID
	 * @param memID
	 * @return
	 * @throws Exception
	 */
	public CompManPrizeInfo getCompMemPrizeInfo(int ranking, int compID, int memID)throws Exception{
		CompManPrizeInfo compManPrizeInfo = new CompManPrizeInfo();
		CompRunningPrize compRunningPrize = this.runningPrizeDao.getCompRunningPrize(ranking, compID);
		if(compRunningPrize == null){
			logger.error("编辑奖池选手奖励信息，compRunningPrize null");
			return null;
		}else if(compID != compRunningPrize.getCompID() || memID != compRunningPrize.getMemID()){
			logger.error("编辑奖池选手奖励信息，memID或compID不匹配");
			return null;
		}
		MemberInfo memInfo = new MemberInfo();
		memInfo.setMemID(memID);
		memInfo = this.memDao.getExactMemInfo(memInfo);
		compManPrizeInfo.setMemID(compRunningPrize.getMemID());
		compManPrizeInfo.setMemName(compRunningPrize.getMemName());
		compManPrizeInfo.setMemIdentNO(compRunningPrize.getMemIdentNO());
		compManPrizeInfo.setCompID(compID);
		compManPrizeInfo.setTableNO(compRunningPrize.getTableNO());
		compManPrizeInfo.setSeatNO(compRunningPrize.getSeatNO());
		compManPrizeInfo.setRanking(compRunningPrize.getRanking());
		compManPrizeInfo.setPercent(HIUtil.formatMoney(compRunningPrize.getPercent(), "#.####"));
		compManPrizeInfo.setAmountInt(compRunningPrize.getAmountInt());
		return compManPrizeInfo;
	}
	/**
	 * 
	 * @param sysType
	 * @return
	 * @throws Exception
	 */
	public List<CompetitionInfo> getOrigCompListForImport(int sysType)throws Exception{
		return this.compDao.getOrigCompListForImport(sysType);
	}
	
	/**
	 * 
	 * @param sysType
	 * @return
	 * @throws Exception
	 */
	public List<CompetitionInfo> getDestListForImport(int sysType)throws Exception{
		return this.compDao.getDestListForImport(sysType);
	}
	
	/**
	 * 根据比赛ID列表，查询比赛集合
	 * @param compID
	 * @return
	 * @throws Exception
	 */
	public List<CompetitionInfo> getCompListByIDs(int...compID)throws Exception{
		List<CompetitionInfo> list = new ArrayList<CompetitionInfo>();
		for (int i = 0; i < compID.length; i++) {
			list.add(this.compDao.getCompInfoByCompID(compID[i]));
		}
		return list;
	}
	
	/**
	 * 根据比赛ID逻辑删除比赛
	 * @param compID
	 * @return 1、删除成功；
	 * @throws Exception
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED, isolation=Isolation.DEFAULT)
	public int updateDelCompetitionByID(int compID) throws Exception {
		if(compID <= 0){
			return 0;
		}
		//1、逻辑删除会员参赛关系记录（MCSTATE设为-2）
		this.memCompDao.logicDelMemCompByCompID(compID);
		//2、释放牌桌
		this.tableDao.releaseCompTables(compID);
		//3、逻辑删除比赛
		this.compDao.delCompetitionByID(compID);
		//4、删除所有待分座位
		this.seatUnAllotDao.delUnAllotedSeatsByCompID(compID);
		//5、删除所有已分座位
		this.seatAllotedDao.deleteAllByCompID(compID);
		return 1;
	}
	
	/**
	 * 暂停或开始比赛
	 * @param compID
	 * @return
	 * @throws Exception
	 */
	@Transactional(propagation=Propagation.REQUIRED, isolation=Isolation.DEFAULT)
	public int updatePauseCompetitionByID(int compID)throws Exception{
		CompetitionInfo compInfo = this.compDao.getCompInfoByCompID(compID);
		if(compInfo.getCompPause() == CompetitionInfo.PAUSESTATE.NOPAUSE){//比赛未暂停，就暂停比赛
			return this.compDao.run2pauseCompetitionByID(compID);
		}else{//比赛暂停中，就开启比赛，但是需要更新当前盲注运行记录中的reStartTime
			CompRunningRound runningRound = this.runningRoundDao.getRunningRoundByCompID(compID);
			if(runningRound != null){
				//这个逻辑比较绕，就是比赛从暂停恢复的时候，
				//算出来比赛暂停的时间点与盲注动态启动时间的时间间隔（就是盲注已经调减的时间值），然后用当前时间再减去这个时间段，就是要更新的最新值
				long time = System.currentTimeMillis();
				long reStartTime = time - (compInfo.getPauseTime().getTime() - runningRound.getReStartTime());
				this.runningRoundDao.updateRunningRoundReStartTime(compID, reStartTime);
			}
			return this.compDao.pause2runCompetitionByID(compID);
		}
		
	}
	
	/**
	 * 结束比赛,很复杂的逻辑
	 * 结束这个比赛，添加奖励的时候，从代码上看是有数据同步问题，但是从业务情景出发，结束这个比赛的时候就不会再淘汰选手了。所以，对于comprunningprize表里的这个compid的奖励记录，是不存在并发修改的。
	 * 有剩余选手：
	 * 无剩余选手：
	 * @param compID
	 * @return
	 * @throws Exception
	 */
	@Transactional(propagation=Propagation.REQUIRED, isolation=Isolation.DEFAULT)
	public int updateEndCompetitionByID(int compID)throws Exception{
		//比赛中存活的选手
		List<CompetitionMember> cmList = null;
		//比赛报名的选手数量
		int compRegedTotalPlayerCount = 0;
		//查询比赛信息
		CompetitionInfo compInfo = this.compDao.getCompInfoByCompID(compID);
		if(compInfo == null){
			return -1;//比赛信息不存在
		}
		if(compInfo.getCompState() != CompetitionInfo.COMPSTATE.STATE_RUNNING){
			logger.info("比赛compID="+compID+",状态compState="+compInfo.getCompState()+",不可结束");
			return 0;//比赛状态不是“停止报名-比赛进行中”
		}
		//查询比赛中存活的选手
		cmList = this.memCompDao.getMemOfRunningAndAdvanCompetionInfos(compID);
		//查询比赛报名的选手数量,强制转为int，选手量肯定不会超过int的上限
		compRegedTotalPlayerCount = compInfo.getTotalPlayer();
		if(compRegedTotalPlayerCount > 0){//剔除扣减人数
			compRegedTotalPlayerCount -= compInfo.getSubPlayerCount();
		}
		if(cmList != null && cmList.size() > 0){//要结束的比赛中还有存活选手
			if(CompetitionInfo.COMPTYPE.SINGLE != compInfo.getCompType()){//晋级赛
				//更新MEMCOMPINFO表选手状态为晋级
				this.memCompDao.updateMemsAdvanByCompID(compID);
			}else{//不是晋级赛，比赛中有选手，需要先淘汰所有选手，再结束
				return -2;
			}
		}
		//删除比赛暂用的牌桌
		this.tableDao.releaseCompTables(compID);
		//删除待分配座位表里面的座位信息
		this.seatUnAllotDao.delUnAllotedSeatsByCompID(compID);
		//删除已分座位表中的座位信息
		this.seatAllotedDao.deleteAllByCompID(compID);
		//结束比赛，设置compstate=5
		this.compDao.endCompetitionByID(compID);
		return 1;
	}
	
	/**
	 * 比赛开启牌桌，根据比赛是否使用分桌系统自行判断是否开启牌桌
	 * @param compID
	 * @param tableNOArr
	 * @throws Exception
	 */
	@Transactional(propagation=Propagation.REQUIRED, isolation=Isolation.DEFAULT)
	public void updateOpenRegistration(int compID,int[] tableNOArr)throws Exception{
		List<CardTable> cardTableList = null;
		CardTable table = null;
		//查询比赛信息
		CompetitionInfo compInfo = this.compDao.getCompInfoByCompID(compID);
		if(compInfo == null){
			throw new RuntimeException("比赛不存在compID="+compID);
		}
		if(compInfo.getAssignSeat() == CompetitionInfo.ASSIGNSEATSTATE.USE){//使用分桌系统
			//更新牌桌状态
			int openTableCount = this.tableDao.openTablesByCompID(compID, tableNOArr, compInfo.getCompName());
			if(openTableCount != tableNOArr.length){//说明其中有被占用的桌子
				throw new RuntimeException("牌桌被占用");
			}
			cardTableList = new ArrayList<CardTable>();
			for (int i = 0; i < tableNOArr.length; i++) {
				table = new CardTable(tableNOArr[i], compID, compInfo.getCompName(), compInfo.getSysType());
				cardTableList.add(table);
			}
			List<UnAllotedSeat> insertList = HIUtil.creatRandomSeat(compID, compInfo.getTableType(), compInfo.getSysType(), cardTableList);
			this.seatUnAllotDao.insert(insertList);
		}
		if(compInfo.getCompState() == CompetitionInfo.COMPSTATE.STATE_NOREG){
			//更新比赛状态
			this.compDao.openCompRegState(compID);
		}
	}
	
	/**
	 * 释放比赛单张牌桌
	 * @param compID
	 * @param tableNO
	 * @return	0：桌号不存在
	 * @throws Exception
	 */
	@Transactional(propagation=Propagation.REQUIRED, isolation=Isolation.DEFAULT)
	public int updateReleaseCompTable(int compID, int tableNO)throws Exception{
		this.lockDao.getLock(LockTable.LOCKTYPE.SEATLOCK);
		//查询牌桌是否被该比赛占用
		CardTable table = this.tableDao.getTableByTableNO(tableNO);
		if(table == null){//桌子不存在
			return 0;
		}
		if(table.getTableState() == CardTable.TABLESTATE.TABLEDISABLED){//桌子状态为不可用，不能释放
			return -1;
		}
		if(table.getCompID() != compID){//桌子不属于该比赛，不能释放
			return -2;
		}
		//查询牌桌是否已经有人入座
		List<CompetitionMember> cmList = this.memCompDao.getLivedMemByTableNO(tableNO);
		if(cmList != null && cmList.size() > 0){//桌子上有人坐着，不能释放
			return -3;
		}
		//删除待分座位，更新牌桌状态
		this.seatUnAllotDao.delUnAllotedSeatsByTableNO(tableNO);
		this.tableDao.releaseCompTable(compID, tableNO);
		return 1;
	}

	/**
	 * 淘汰比赛中的参赛选手
	 * @param cmID
	 * @param memID
	 * @param compID
	 * @return
	 * @throws Exception
	 */
	@Transactional(propagation=Propagation.REQUIRED, isolation=Isolation.DEFAULT)
	public int updateOutMemFromComp(int cmID, int memID, int compID)throws Exception{
		this.lockDao.getLock(LockTable.LOCKTYPE.PRIZELOCK);//抢锁
		int compRegedTotalPlayerCount = 0;
		//查询比赛信息
		CompetitionInfo compInfo = this.compDao.getCompInfoByCompID(compID);
		//查询选手参赛信息
		CompetitionMember cm = this.memCompDao.getCompMemByID(cmID);
		if(compInfo == null || cm == null || cm.getMemID() != memID || cm.getCompID() != compID){
			return 0;
		}
		if(compInfo.getCompState() == CompetitionInfo.COMPSTATE.STATE_NOREG || 
				compInfo.getCompState() == CompetitionInfo.COMPSTATE.STATE_DEL || 
				compInfo.getCompState() == CompetitionInfo.COMPSTATE.STATE_END){
			return -1;
		}
		if(cm.getMcState() != CompetitionMember.MemCompState.REGED){
			return -2;
		}
		//更新参赛状态为淘汰
		this.memCompDao.outCompMemberByID(cmID);
		//判断是否带奖励
		if(compInfo.getAword() == CompetitionInfo.AWORDSTATE.WITHAWORD && 
				(compInfo.getCompState() == CompetitionInfo.COMPSTATE.STATE_REGEND_NOBEGIN || 
				compInfo.getCompState() == CompetitionInfo.COMPSTATE.STATE_RUNNING)){
			compRegedTotalPlayerCount = compInfo.getTotalPlayer();
			if(compRegedTotalPlayerCount > 0){//剔除扣减人数
				compRegedTotalPlayerCount -= compInfo.getSubPlayerCount();
			}
			//比赛总奖金
			int compTotalPrize = HIUtil.calcCompTotalPrizeMoney(compInfo.getLeastPrize(), compInfo.getRegFee(), compInfo.getTotalPlayer(), compInfo.getSubPlayerCount());
			/**
			 * 产生奖励记录
			 */
			//1、根据报名人数，获取奖励区间
			List<Prize> prizeList = HIUtil.getPrizeListByPlayerCount(compRegedTotalPlayerCount);
			//查询存活的选手
			int livedPlayerCount = (int)this.memCompDao.findCompLivedTotalPlayerCount(compID);
			if(prizeList.size() > 0 && livedPlayerCount < prizeList.get(0).getRankNum()){//进入钱圈人数范围
				Prize prize = null;
				CompRunningPrize runningPrize = null;
				double percent = 0d;
				double amount = 0d;
				int amountInt = 0;
				for (int i = 0; i < prizeList.size(); i++) {
					prize  = prizeList.get(i);
					if(prize.getRankNO() == livedPlayerCount + 1){//找到对应的奖励名次
						percent = prize.getPercent();
						amount = compTotalPrize * prize.getPercent();
						amount = HIUtil.round(amount, 2);
						amountInt = (int)amount;
						runningPrize = new CompRunningPrize();
						runningPrize.setMemID(cm.getMemID());
						runningPrize.setCompID(cm.getCompID());
						runningPrize.setRanking(prize.getRankNO());
						runningPrize.setAmount(amount);
						runningPrize.setAmountInt(amountInt);
						runningPrize.setPercent(percent);
						runningPrize.setSysType(compInfo.getSysType());
						runningPrize.setTableNO(cm.getTableNO());
						runningPrize.setSeatNO(cm.getSeatNO());
						runningPrize.setCreateTime(new Date());
						this.runningPrizeDao.insertRunningPrize(runningPrize);
					}
				}
			}
		}
		this.seatUnAllotDao.addUnAllotedSeatOfComp(
				new UnAllotedSeat(cm.getTableNO(), cm.getSeatNO(), cm.getCompID(), 
						new Date().getTime(), HIUtil.getSeatLevel(cm.getSeatNO(), compInfo.getTableType()), compInfo.getTableType(), compInfo.getSysType()));
		this.seatAllotedDao.deleteByTableSeatNO(cm.getTableNO(), cm.getSeatNO());
		return 1;
	}
	
	/**
	 * 编辑扣减选手数量，累计减
	 * @param compID
	 * @param subNum
	 * @return
	 */
	@Transactional(propagation=Propagation.REQUIRED, isolation=Isolation.DEFAULT)
	public EditSubPlayerVO updateSubPlayer(int compID, int subNum)throws Exception{
		EditSubPlayerVO vo = null;
		this.compDao.updateSubPlayer(compID, subNum);
		CompetitionInfo compInfo = this.compDao.getCompInfoByCompID(compID);
		int compRegedTotalPlayerCount = compInfo.getTotalPlayer();
		int totalRegedPlayerCountEdit = compRegedTotalPlayerCount - compInfo.getSubPlayerCount();
		if(totalRegedPlayerCountEdit <= 0){
			throw new RuntimeException("修改后的总人数不能小于1");
		}
		vo = new EditSubPlayerVO(compID, compRegedTotalPlayerCount, totalRegedPlayerCountEdit);
		return vo;
	}
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED, isolation=Isolation.DEFAULT)
	public int updateGoForwardRound(int compID) throws Exception {
		CompetitionInfo compInfo = this.compDao.getCompInfoByCompID(compID);
		if(compInfo.getCompPause() == CompetitionInfo.PAUSESTATE.MANUALPAUSE){
			return -1;//比赛暂停中，不能调整
		}
		if(compInfo.getStartTime().getTime() > System.currentTimeMillis()){
			return -2;//比赛为开始
		}
		if(compInfo.getCompState() == CompetitionInfo.COMPSTATE.STATE_DEL || compInfo.getCompState() == CompetitionInfo.COMPSTATE.STATE_END){
			return -3;//比赛已经结束、或删除
		}
		int roundTempID = compInfo.getRoundTempID();
		CompRunningRound runningRound = this.runningRoundDao.getRunningRoundByCompID(compID);
		if(runningRound == null){
			if(compInfo.getCompState() == CompetitionInfo.COMPSTATE.STATE_RUNNING){
				return -4;//比赛盲注已经跳完，还未结束
			}
			throw new RuntimeException("比赛没有当前运行盲注信息compID="+compID);
		}
		Round nextRound = this.roundDao.getRoundByRank(roundTempID, runningRound.getCurRank() + 1);//
		if(nextRound == null){
			throw new RuntimeException("已是最后一个盲注级别，无法再进");
		}
		this.runningRoundDao.delRunningRoundByCompID(compID);
		runningRound = new CompRunningRound();
		runningRound.setCompID(compID);
		runningRound.setCurBeforeChip(nextRound.getBeforeChip());
		runningRound.setCurBigBlind(nextRound.getBigBlind());
		runningRound.setCurRank(nextRound.getRoundrank());
		runningRound.setCurSmallBlind(nextRound.getSmallBlind());
		Date date = new Date();
		runningRound.setCurStartTime(date.getTime());
		runningRound.setReStartTime(date.getTime());
		runningRound.setCurType(nextRound.getRoundType());
		runningRound.setRoundTempID(nextRound.getRoundTempID());
		runningRound.setStepLen(nextRound.getStepLen());
		runningRound.setSysType(compInfo.getSysType());
		if(runningRound.getCurRank() == compInfo.getStopRegRank() && compInfo.getCompState() == CompetitionInfo.COMPSTATE.STATE_REGING_BEGINED){
			this.compDao.endCompetitionReg_Serv(compInfo.getCompID());
		}
		return this.runningRoundDao.insertRunningRound(runningRound);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED, isolation=Isolation.DEFAULT)
	public int updateGoBackRound(int compID) throws Exception {
		CompetitionInfo compInfo = this.compDao.getCompInfoByCompID(compID);
		if(compInfo.getCompPause() == CompetitionInfo.PAUSESTATE.MANUALPAUSE){
			return -1;//比赛暂停中，不能调整
		}
		if(compInfo.getStartTime().getTime() > System.currentTimeMillis()){
			return -2;//比赛为开始
		}
		if(compInfo.getCompState() == CompetitionInfo.COMPSTATE.STATE_DEL || compInfo.getCompState() == CompetitionInfo.COMPSTATE.STATE_END){
			return -3;//比赛已经结束、或删除
		}
		int roundTempID = compInfo.getRoundTempID();
		CompRunningRound runningRound = this.runningRoundDao.getRunningRoundByCompID(compID);
		if(runningRound == null){
			if(compInfo.getCompState() == CompetitionInfo.COMPSTATE.STATE_RUNNING){
				return -4;//比赛盲注已经跳完，还未结束
			}
			throw new RuntimeException("比赛没有当前运行盲注信息compID="+compID);
		}
		Round beforeRound = this.roundDao.getRoundByRank(roundTempID, runningRound.getCurRank() - 1);//
		if(beforeRound == null){
			throw new RuntimeException("已是最后一个盲注级别，无法再进");
		}
		this.runningRoundDao.delRunningRoundByCompID(compID);
		runningRound = new CompRunningRound();
		runningRound.setCompID(compID);
		runningRound.setCurBeforeChip(beforeRound.getBeforeChip());
		runningRound.setCurBigBlind(beforeRound.getBigBlind());
		runningRound.setCurRank(beforeRound.getRoundrank());
		runningRound.setCurSmallBlind(beforeRound.getSmallBlind());
		Date date = new Date();
		runningRound.setCurStartTime(date.getTime());
		runningRound.setReStartTime(date.getTime());
		runningRound.setCurType(beforeRound.getRoundType());
		runningRound.setRoundTempID(beforeRound.getRoundTempID());
		runningRound.setStepLen(beforeRound.getStepLen());
		runningRound.setSysType(compInfo.getSysType());
		return this.runningRoundDao.insertRunningRound(runningRound);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED, isolation=Isolation.DEFAULT)
	public int updateRoundJumpTime(int compID, int curRank, int jumpTime)throws Exception{
		CompetitionInfo compInfo = this.compDao.getCompInfoByCompID(compID);
		if(compInfo.getCompPause() == CompetitionInfo.PAUSESTATE.MANUALPAUSE){
			return -1;//比赛暂停中，不能调整
		}
		int roundTempID = compInfo.getRoundTempID();
		CompRunningRound runningRound = this.runningRoundDao.getRunningRoundByCompID(compID);
		if(runningRound == null){
			return -2;//比赛未开始，计时盲注不存在
		}else if(runningRound.getCurRank() != curRank){
			logger.error("客户端盲注级别不同步，客户端rank="+curRank+",服务端，rank="+runningRound.getCurRank()+",compID="+compID);
			return -3;
		}
		Round round = this.roundDao.getRoundByRank(roundTempID, curRank, runningRound.getCurType());
		if(round.getStepLen() < Math.abs(jumpTime)){
			throw new RuntimeException("跳跃时间超过盲注步进最大值");
		}
		long reStartTime = runningRound.getReStartTime() - (jumpTime+1) * 1000;//这个地方放置误差，统一多加一秒
		return this.runningRoundDao.updateRunningRoundTime(compID, reStartTime);
	}
	
	/**
	 * 编辑奖池中选手的奖金
	 * @param runningPrizeID
	 * @param amountInt
	 * @return
	 * @throws Exception
	 */
	public int updateCompManPrizeInfoAmount(int ranking, int compID, int amountInt)throws Exception{
		return this.runningPrizeDao.updateCompRunningPrizeAmount(ranking, compID, amountInt);
	}
	
	/**
	 * 比赛晋级导入
	 * @param origCompList		源比赛集合
	 * @param orignalTablesList	源比赛牌桌集合（这个参数是固定分座使用的）
	 * @param uniqueCMMap		排重后的导入选手
	 * @param outCMList			淘汰的选手
	 * @param destComp			目标赛
	 * @param usedTables		目标赛开启的牌桌
	 * @param condition			分座条件：1、固定座位；0、随机座位
	 * @param empUuid
	 * @return
	 * @throws Exception
	 */
	@Transactional(propagation=Propagation.REQUIRED, isolation=Isolation.DEFAULT)
	public int updateImportComps(List<CompetitionInfo> origCompList, List<Integer> orignalTablesList, Map<Integer, CompetitionMember> uniqueCMMap, List<CompetitionMember> outCMList, CompetitionInfo destComp, List<CardTable> usedTables, int condition, String empUuid)throws Exception{
		CompetitionMember outCM = null;
		CompetitionMember newOutCM = null;
		CompetitionInfo compInfo = null;
		CompRunningPrize runningPrize = null;
		Prize prizeTemp = null;
		//1、桌号映射，保证原来的一桌人还在一桌,添加比赛关系
		this.allotSeatForImport(orignalTablesList, uniqueCMMap, usedTables, destComp, condition);
		//2、更新比赛，状态，总人数，总筹码，减除人数
		int importTotalPlayer = 0;
		int importTotalSubPlayer = 0;
		Date importTime = new Date();
		empUuid = (empUuid == null ? "" : empUuid);
		for (int i = 0; i < origCompList.size(); i++) {
			compInfo = origCompList.get(i);
			importTotalPlayer += compInfo.getTotalPlayer();
			importTotalSubPlayer += compInfo.getSubPlayerCount();
			//添加日志记录
			this.compImportLogDao.insert(new CompImportLog(compInfo.getCompID(), destComp.getCompID(), importTime, empUuid));
		}
		//目标赛带有奖励表，把淘汰的人员加入奖励名次
		if(destComp.getAword() == CompetitionInfo.AWORDSTATE.WITHAWORD && outCMList != null && outCMList.size() > 0){
			//判断所有晋级人员数量与奖励表区间最大奖励数量的关系。1、前者大于后者，说明有淘汰的人员没有奖励；2、前者小于等于后者，需要按照晋级人员数量，从最后一名开始出名次；
			List<Prize> prizeAreaList = HIUtil.getPrizeListByPlayerCount(importTotalPlayer - importTotalSubPlayer);
			if(prizeAreaList.isEmpty()){
				throw new RuntimeException("导入失败，目标比赛奖励表区间异常");
			}
			
			double percent = 0.0d;
			int compTotalPrize = HIUtil.calcCompTotalPrizeMoney(destComp.getLeastPrize(), destComp.getRegFee(), importTotalPlayer, importTotalSubPlayer);//(importTotalPlayer - importTotalSubPlayer) * destComp.getRegFee();
			double amount = 0.0d;
			int amountInt = 0;
			int lastRanking = 0;//最后一名的名次
			if(prizeAreaList.get(0).getRankNum() > uniqueCMMap.size()){//奖励人数>排重后的存活人数,那淘汰的重复人员里就有一部分人会产生名次；奖励人数<=排重后的存活人数,那淘汰的重复人员就没有名次，直接淘汰就OK
				int prizeSize = 0;
				if(prizeAreaList.get(0).getRankNum() < uniqueCMMap.size() + outCMList.size()){//部分淘汰人员有奖励，按筹码多少排序
					lastRanking = prizeAreaList.get(0).getRankNum();//最后一名就是奖励人数的最大值
					prizeSize = uniqueCMMap.size() + outCMList.size() - lastRanking - 1;
				}else{//晋级人数小于等于钱圈奖励人数，淘汰的重复人员全部都有奖励，最后一名就是晋级总人数（算上淘汰的重复人数）的最大值。
					lastRanking = uniqueCMMap.size() + outCMList.size();
					prizeSize = outCMList.size();
				}
				for (int i = 0; i < prizeSize; i++) {//现在有个问题解决不了，例如outCMList：{6000，7000，7000}，如果只缺一个名词，那就只能取最后一个7000，但是实际上是，应该两个7000都是同名次，评分奖金才对。
					outCM = outCMList.get(outCMList.size() - i - 1);
					prizeTemp = prizeAreaList.get(lastRanking - 1);
					percent = prizeTemp.getPercent();
					amount = compTotalPrize * prizeTemp.getPercent();
					amount = HIUtil.round(amount, 2);
					amountInt = (int)amount;
					runningPrize = new CompRunningPrize();
					runningPrize.setMemID(outCM.getMemID());
					runningPrize.setCompID(destComp.getCompID());
					runningPrize.setRanking(lastRanking);
					runningPrize.setAmount(amount);
					runningPrize.setAmountInt(amountInt);
					runningPrize.setPercent(percent);
					runningPrize.setSysType(compInfo.getSysType());
					runningPrize.setTableNO(outCM.getTableNO());
					runningPrize.setSeatNO(outCM.getSeatNO());
					runningPrize.setCreateTime(new Date());
					lastRanking --;
					this.runningPrizeDao.insertRunningPrize(runningPrize);
				}
			}
		}
		Date regTime = new Date();
		for (int i = 0; i < outCMList.size(); i++) {
			outCM = outCMList.get(i);
			newOutCM = new CompetitionMember(outCM.getMemID(), destComp.getCompID(), CompetitionMember.MemCompState.OUT, -1, -1, 0, destComp.getSysType(), regTime);
			this.memCompDao.saveMemCompRelation(newOutCM);
		}
		destComp.setBeginChip(origCompList.get(0).getBeginChip());
		destComp.setCompState(CompetitionInfo.COMPSTATE.STATE_REGEND_NOBEGIN);
		destComp.setTotalPlayer(importTotalPlayer);
		destComp.setSubPlayerCount(importTotalSubPlayer);
		destComp.setUpdateTime(new Date());
		this.compDao.updateDestCompForImport(destComp);
		return 1;
	}
	
	/**
	 * 比赛爆桌
	 * @param compID
	 * @param tableNO
	 * @return
	 * @throws Exception
	 */
	@Transactional(propagation=Propagation.REQUIRED, isolation=Isolation.DEFAULT)
	public CompBurstTableRes updateburstTableFromComp(int compID, int tableNO, String empUuid)throws Exception{
		this.lockDao.getLock(LockTable.LOCKTYPE.SEATLOCK);//取得锁
		CompBurstTableRes compBurstTableRes = new CompBurstTableRes();
		CompetitionInfo compInfo = this.compDao.getCompInfoByCompID(compID);
		if(compInfo.getCompState() == CompetitionInfo.COMPSTATE.STATE_DEL || 
				compInfo.getCompState() == CompetitionInfo.COMPSTATE.STATE_END || 
				compInfo.getCompState() == CompetitionInfo.COMPSTATE.STATE_NOREG){
			compBurstTableRes.setResCode(CompBurstTableRes.RESCODE.RESCODE_1);
			compBurstTableRes.setCompID(compID);
			return compBurstTableRes;//比赛状态不正确
		}
		List<CompetitionMember> livedMemCompList = this.memCompDao.getLivedMemByTableNO(tableNO);
		if(livedMemCompList == null || livedMemCompList.size() <= 0){//该桌子上没有存活选手
			compBurstTableRes.setResCode(CompBurstTableRes.RESCODE.RESCODE_2);
			compBurstTableRes.setCompID(compID);
			return compBurstTableRes;
		}
		//根据存活选手数量随机找新座位
		List<UnAllotedSeat> newSeatList = this.seatUnAllotDao.getUnAllotedSeatByCompID(compID, tableNO, livedMemCompList.size());
		if(newSeatList == null || newSeatList.size() < livedMemCompList.size()){//新分座位数量不够选手个数
			compBurstTableRes.setResCode(CompBurstTableRes.RESCODE.RESCODE_3);
			compBurstTableRes.setCompID(compID);
			return compBurstTableRes;
		}
		CompetitionMember cm = null;
		UnAllotedSeat newSeat = null;
		AllotedSeat allotedSeat = null;
		Date date = new Date();
		MemberInfo memInfo = null;
		List<BurstTableNewSeatInfo> burstNewsInfoList = new ArrayList<BurstTableNewSeatInfo>();
		BurstTableNewSeatInfo burstTableNewSeatInfo = null;
		for (int i = 0; i < newSeatList.size(); i++) {
			cm = livedMemCompList.get(i);
			newSeat = newSeatList.get(i);
			this.seatUnAllotDao.delSpecUnAllotedSeat(compID, newSeat.getTableNO(), newSeat.getSeatNO());
			allotedSeat = new AllotedSeat(newSeat.getTableNO(), newSeat.getSeatNO(), compID, cm.getMemID(), date);
			//插入已分新座位
			this.seatAllotedDao.insertAllotedSeat(allotedSeat);
			//更新选手座位信息
			this.memCompDao.updateMemSeatInfo(cm.getId(), newSeat.getTableNO(), newSeat.getSeatNO());
			//插入到爆桌记录表
			this.compHisLogDao.insert(new CompMovedSeatLog(compID, cm.getMemID(), cm.getTableNO(), cm.getSeatNO(), empUuid, newSeat.getTableNO(), newSeat.getSeatNO(), CompMovedSeatLog.OPTYPE.BURST, date.getTime(), compInfo.getSysType()));
			memInfo = new MemberInfo();
			memInfo.setMemID(cm.getMemID());
			memInfo = this.memDao.getExactMemInfo(memInfo);
			burstTableNewSeatInfo = new BurstTableNewSeatInfo(memInfo.getMemID(), memInfo.getCardNO(), memInfo.getMemName(), newSeat.getTableNO(), newSeat.getSeatNO(), memInfo.getMemSex());
			burstNewsInfoList.add(burstTableNewSeatInfo);
		}
		//删除该比赛该牌桌所有待分座位和已分座位
		this.seatUnAllotDao.delUnAllotedSeatsByTableNO(tableNO);
		this.seatAllotedDao.deleteByTableNO(compID, tableNO);
		//释放牌桌
		this.tableDao.releaseCompTable(compID, tableNO);
		compBurstTableRes.setCompID(compID);
		compBurstTableRes.setResCode(1);
		compBurstTableRes.setNewSeatInfoList(burstNewsInfoList);
		return compBurstTableRes;
	}
	
	
	
	
	
	
	
	//---------------------------------screen 显示信息查询---------------------------------//
	/**
	 * 大屏幕显示选手信息
	 * @param compID
	 * @return
	 * @throws Exception
	 */
	public List<ScreenCompPlayerInfo> getScreenCompPlayerInfoList(int compID)throws Exception{
		List<ScreenCompPlayerInfo> list = new ArrayList<ScreenCompPlayerInfo>();
		List<CompMemInfo> compMemInfoList = this.memCompDao.getCompPlayerInfo(compID, 0);
		CompMemInfo compMemInfo = null;
		ScreenCompPlayerInfo screenCompPlayerInfo = null;
		if(compMemInfoList != null && !compMemInfoList.isEmpty()){
			for (int i = 0; i < compMemInfoList.size(); i++) {
				compMemInfo = compMemInfoList.get(i);
				screenCompPlayerInfo = new ScreenCompPlayerInfo(i+1, compMemInfo.getCardNO(), 
						compMemInfo.getMemName(), compMemInfo.getTableNO(), compMemInfo.getSeatNO(), compMemInfo.getChip());
				list.add(screenCompPlayerInfo);
			}
		}
		return list;
	}
	
	
	
	
	
	
	
	//---------------------------------screen显示信息查询---------------------------------//
	/**
	 * 判断选手是否报名的当天的比赛
	 * @param memID
	 * @param start
	 * @param end
	 * @return
	 * @throws Exception
	 */
	public boolean memIsRegedCompsOfToday(int memID, Date start, Date end)throws Exception{
		List<Map<String, Object>> regedCompNoEndList = this.memCompDao.getMemRegCompsOfToday(memID, start, end);
		if(regedCompNoEndList == null || regedCompNoEndList.isEmpty()){
			return false;
		}
		return true;
	}
	
	/**
	 * 查询大屏幕比赛列表信息
	 * @param start
	 * @param end
	 * @param sysType
	 * @return
	 * @throws Exception 
	 */
	public List<ScreenCompInfo> getScreenCompList(Date start, Date end, int sysType)throws Exception{
		List<ScreenCompInfo> res = new ArrayList<ScreenCompInfo>();
		List<CompetitionInfo> list = this.compDao.getNoEndCompsInTimeAreaNoDelNoEnd(start, end, sysType);
		ScreenCompInfo screenCompInfo = null;
		int curRank = 0;
		int curBeforeChip = 0;
		if(list != null && list.size() > 0){
			for (int i = 0; i < list.size(); i++) {
				CompetitionInfo info = list.get(i);
				info.setCompRunningRound(this.runningRoundDao.getRunningRoundByCompID(info.getCompID()));
				if(info.getCompRunningRound() != null){
					curRank = info.getCompRunningRound().getCurRank();
					curBeforeChip =  info.getCompRunningRound().getCurBeforeChip();
				}
				screenCompInfo = new ScreenCompInfo(info.getCompID(), info.getCompName(), info.getCompState(), info.getBeginChip(),curBeforeChip,
						DateUtils.formatDate(info.getStartTime(), DateUtils.PATTERN2), DateUtils.formatDate(info.getStartTime(), 
								DateUtils.PATTERN7), info.getTotalPlayer() - info.getSubPlayerCount(), curRank);
				res.add(screenCompInfo);
			}
		}
		return res;
	}
	
	
	
	
	
	
	
	//---------------------------------competition server服务管理线程---------------------------------//
	@Override
	@Transactional(propagation=Propagation.REQUIRED, isolation=Isolation.DEFAULT)
	public int updateStartCompetition_Serv(CompetitionInfo compInfo, int newCompState) throws Exception {
		//更新比赛的compState=2
		this.compDao.startCompetition_Serv(compInfo.getCompID(), newCompState);
		//添加当前运行盲注信息
		Round round = this.roundDao.getRoundByRank(compInfo.getRoundTempID(), 0);
		CompRunningRound runningRound = new CompRunningRound();
		runningRound.setCompID(compInfo.getCompID());
		runningRound.setCurBeforeChip(round.getBeforeChip());
		runningRound.setCurBigBlind(round.getBigBlind());
		runningRound.setCurRank(round.getRoundrank());
		runningRound.setCurSmallBlind(round.getSmallBlind());
		Date date = new Date();
		runningRound.setCurStartTime(date.getTime());
		runningRound.setReStartTime(date.getTime());
		runningRound.setCurType(round.getRoundType());
		runningRound.setRoundTempID(round.getRoundTempID());
		runningRound.setStepLen(round.getStepLen());
		runningRound.setSysType(compInfo.getSysType());
		int res =  this.runningRoundDao.insertRunningRound(runningRound);
		return res;
	}
	
	/**
	 * 后台比赛维护线程--维护比赛盲注进度
	 * @param compInfo
	 * @return
	 * @throws Exception
	 */
	@Transactional(propagation=Propagation.REQUIRED, isolation=Isolation.DEFAULT)
	public int updateCompRoundRun_Serv(CompetitionInfo compInfo)throws Exception{
		Round round = null;
		CompRunningRound runningRound = this.runningRoundDao.getRunningRoundByCompID(compInfo.getCompID());
		if(runningRound == null){
			return 0;
		}
		long start = runningRound.getReStartTime();
		long now = new Date().getTime();
		long deviation = now - (start + runningRound.getStepLen() * 1000);
		if(deviation >= -500){//偏差值小于等于1秒，就可以进行下一个盲注了。
			round = this.roundDao.getNextRound_Serv(runningRound.getRoundTempID(), runningRound.getCurRank(), runningRound.getCurType());
			if(round != null && round.getRoundType() == Round.ROUNDTYPE.TIME){
				if(runningRound.getCurRank() + 1 == compInfo.getStopRegRank() && (compInfo.getCompState() == CompetitionInfo.COMPSTATE.STATE_REGING_BEGINED || compInfo.getCompState() == CompetitionInfo.COMPSTATE.STATE_REGEND_NOBEGIN)){//下个级别就该截止报名了
					this.compDao.endCompetitionReg_Serv(compInfo.getCompID());
				}
			}
			logger.info("--------------盲注跳跃时间到--------------compID = "+compInfo.getCompID()+", compName = "+compInfo.getCompName());
			if(round != null){
				this.runningRoundDao.delRunningRoundByCompID(compInfo.getCompID());
				runningRound = new CompRunningRound();
				runningRound.setCompID(compInfo.getCompID());
				runningRound.setCurBeforeChip(round.getBeforeChip());
				runningRound.setCurBigBlind(round.getBigBlind());
				runningRound.setCurRank(round.getRoundrank());
				runningRound.setCurSmallBlind(round.getSmallBlind());
				Date date = new Date();
				runningRound.setCurStartTime(date.getTime());
				runningRound.setReStartTime(date.getTime());
				runningRound.setCurType(round.getRoundType());
				runningRound.setRoundTempID(round.getRoundTempID());
				runningRound.setStepLen(round.getStepLen());
				runningRound.setSysType(compInfo.getSysType());
				int res = this.runningRoundDao.insertRunningRound(runningRound);
				return res;
			}else{
				logger.info("--------------盲注已经跳尽--------------compID = "+compInfo.getCompID()+", compName = "+compInfo.getCompName());
				return -1;
			}
		}
		return 0;
	}
	
	/**
	 * 比赛导入分座位
	 * @param orignalTablesList	源比赛的桌号
	 * @param uniqueCMMap		排重后的选手<memID, CompetitionMember>
	 * @param usedTables		目标比赛占用的桌子集合
	 * @param destComp			目标比赛
	 * @param condition			分桌条件：1、固定座位；0、随机座位
	 * @throws Exception
	 */
	private void allotSeatForImport(List<Integer> orignalTablesList, Map<Integer, CompetitionMember> uniqueCMMap, List<CardTable> usedTables, CompetitionInfo destComp, int condition)throws Exception{
		Map<Integer, Integer> old_new_mapping = new HashMap<Integer, Integer>();//新老桌号映射
		
		List<UnAllotedSeat> anAllotedSeatList = null;
		UnAllotedSeat unAllotedSeat = null;
		Set<Entry<Integer, CompetitionMember>> set = uniqueCMMap.entrySet();
		Iterator<Entry<Integer, CompetitionMember>> it = set.iterator();
		Entry<Integer, CompetitionMember> entry = null;
		CompetitionMember cm = null;
		CompetitionMember newcm = null;//新待插入的比赛会员关系
		Date regTime = new Date();
		if(condition != 1){//随机分座，获取目标赛的所有待分座位
			anAllotedSeatList = this.seatUnAllotDao.getUnAllotedSeatsByCompID(destComp.getCompID());
			while(it.hasNext()){
				entry = it.next();
				cm = entry.getValue();
				if(anAllotedSeatList.size() > 0){//获取一个待分座位
					unAllotedSeat = anAllotedSeatList.remove(0);
				}else{
					throw new RuntimeException("分配的牌桌数量不够");
				}
				newcm = new CompetitionMember(cm.getMemID(), destComp.getCompID(), CompetitionMember.MemCompState.REGED, unAllotedSeat.getTableNO(), unAllotedSeat.getSeatNO(), cm.getChip(), destComp.getSysType(), regTime);
				//删除该分配过的座位
				int res = this.seatUnAllotDao.delSpecUnAllotedSeat(destComp.getCompID(), unAllotedSeat.getTableNO(), unAllotedSeat.getSeatNO());
				if(res <= 0){
					throw new RuntimeException("晋级导入--随机座位，座位被占用");
				}
				//插入已分座位表中
				this.seatAllotedDao.insertAllotedSeat(new AllotedSeat(unAllotedSeat.getTableNO(), unAllotedSeat.getSeatNO(), destComp.getCompID(), cm.getMemID(), regTime));
				//生成报名数据
				this.memCompDao.saveMemCompRelation(newcm);
			}
		}else{
			//设置新老桌号映射关系
			for (int i = 0; i < orignalTablesList.size(); i++) {
				old_new_mapping.put(orignalTablesList.get(i), usedTables.get(i).getTableNO());
			}
			Integer newTableNO = 0;//老桌号
			while(it.hasNext()){
				entry = it.next();
				cm = entry.getValue();
				newTableNO = old_new_mapping.get(cm.getTableNO());
				newcm = new CompetitionMember(cm.getMemID(), destComp.getCompID(), CompetitionMember.MemCompState.REGED, newTableNO, cm.getSeatNO(), cm.getChip(), destComp.getSysType(), regTime);
				int res = this.seatUnAllotDao.delSpecUnAllotedSeat(destComp.getCompID(), newTableNO, cm.getSeatNO());
				if(res <= 0){
					throw new RuntimeException("晋级导入--固定座位，牌桌座位被占用");
				}
				this.seatAllotedDao.insertAllotedSeat(new AllotedSeat(newTableNO, cm.getSeatNO(), destComp.getCompID(), cm.getMemID(), regTime));
				this.memCompDao.saveMemCompRelation(newcm);
			}
		}
	}
	
	@Override
	public BaseDao<CompetitionInfo, Integer> getDao() {
		return compDao;
	}
	
	private static Logger logger = LoggerFactory.getLogger(CompetitionServiceImpl.class);
	
	@Resource
	private CompetitionDao compDao;
	@Resource
	private CardTableDao tableDao;
	@Resource
	private MemCompDao memCompDao;
	@Resource
	private CompRunningPrizeDao runningPrizeDao;
	@Resource
	private SeatUnAllotDao seatUnAllotDao;
	@Resource
	private SeatAllotedDao seatAllotedDao;
	@Resource
	private CompRunningRoundDao runningRoundDao;
	@Resource
	private RoundDao roundDao;
	@Resource
	private MemberDao memDao;
	@Resource
	private LockDao lockDao;
	@Resource
	private CompHistoryLogDao compHisLogDao;
	@Resource
	private CompImportLogDao compImportLogDao;
}
