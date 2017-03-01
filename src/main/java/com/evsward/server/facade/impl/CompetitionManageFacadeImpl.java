package com.evsward.server.facade.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.evsward.server.facade.CompetitionManageFacade;
import com.evsward.server.service.CardTableService;
import com.evsward.server.service.CompCurRoundMangeService;
import com.evsward.server.service.CompHistoryLogService;
import com.evsward.server.service.CompImportLogService;
import com.evsward.server.service.CompetitionService;
import com.evsward.server.service.MemCompService;
import com.evsward.server.service.MemberManageService;
import com.evsward.server.service.RoundService;
import com.evsward.server.util.Application;
import com.evsward.server.util.DateUtils;
import com.evsward.server.util.RspCodeValue;
import com.evsward.server.util.HIUtil;
import com.evsward.server.vo.CardTable;
import com.evsward.server.vo.CompMemInfo;
import com.evsward.server.vo.CompMovedSeatLog;
import com.evsward.server.vo.CompRunningRound;
import com.evsward.server.vo.CompetitionInfo;
import com.evsward.server.vo.CompetitionMember;
import com.evsward.server.vo.MemberInfo;
import com.evsward.server.vo.Round;
import com.evsward.server.vo.complist.CompListInfo;
import com.evsward.server.vo.complist.CompetitionInfo_Day;
import com.evsward.server.vo.compmanage.ComManRunningInfo;
import com.evsward.server.vo.compmanage.CompBurstTableRes;
import com.evsward.server.vo.compmanage.CompManPlayerInfo;
import com.evsward.server.vo.compmanage.CompManPrizeInfo;
import com.evsward.server.vo.compmanage.CompManSeatInfo;
import com.evsward.server.vo.compmanage.CompManTableInfo;
import com.evsward.server.vo.compmanage.EditSubPlayerVO;
import com.evsward.server.vo.compmanage.PrizePrintInfo;
import com.evsward.server.vo.compmanage.SeatMovedLog;
import com.evsward.server.vo.screen.ScreenCompPlayerInfo;
import com.evsward.server.vo.tcpmsg.triggmsg.SystemBasePushTriggMsg;

@Component("compManageFacade")
public class CompetitionManageFacadeImpl implements CompetitionManageFacade {

	private static Logger logger = LoggerFactory.getLogger(CompetitionManageFacadeImpl.class);
	
	@Resource
	private MemberManageService memManService;
	@Resource
	private CompetitionService compService;
	@Resource
	private MemCompService memCompService;
	@Resource
	private CardTableService tableService;
	@Resource
	private CompCurRoundMangeService compCurRoundManService;
	@Resource
	private RoundService roundService;
	@Resource
	private CompHistoryLogService compHistLogService;
	@Resource
	private CompImportLogService compImportLogService;
	
	public String createCompetition(String compName, int leastPrize, int regFee, int serviceFee, 
			int beginChip, int unit, int roundTempID, int stopRegRank, int reEntry, int compType, int tableType, 
			int sysType, int aword, int assignSeat, long beginTime, String empUuid){
		String jsonStr = "";
		//判断比赛名称是否已经存在
		boolean exist = true;
		compName = compName.toUpperCase();
		try {
			exist = this.compService.compNameExist(compName);
		} catch (Exception e1) {
			logger.error(e1.getMessage(), e1);
			return HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$403.getRspCode(), RspCodeValue.$403.getMsg()));
		}
		if(exist){
			return HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$402.getRspCode(), RspCodeValue.$402.getMsg()));
		}
		CompetitionInfo compInfo = new CompetitionInfo(roundTempID, compName, CompetitionInfo.COMPSTATE.STATE_NOREG, 
				CompetitionInfo.PAUSESTATE.NOPAUSE, compType, regFee, serviceFee, beginChip, unit, aword, stopRegRank, 
				leastPrize, 0, 0, 0, CompetitionInfo.SYNCSTATE.NOSYNC, tableType, sysType, assignSeat, reEntry, new Date(beginTime), 
				new Date(), null, null, null);
		try {
			this.compService.createCompetition(compInfo);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$401.getRspCode(), RspCodeValue.$401.getMsg()));
		}
		Application.startCompServEntrance(compInfo.getCompID());
		Application.setTriggingMsgForPushing(new SystemBasePushTriggMsg(Application.OPERTYPE.CREATECOMP, 0, compInfo.getCompID()));
		jsonStr = HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$1.getRspCode(), RspCodeValue.$1.getMsg()));
		return jsonStr;
	}

	@Override
	public String editCompetition(int compID, String compName, int leastPrize,
			int regFee, int serviceFee, int beginChip, int unit,
			int roundTempID, int stopRegRank, int reEntry, int compType, int tableType,
			int aword, int assignSeat, long beginTime, String empUuid) {
		String jsonStr = "";
		//判断比赛名称是否已经存在
		boolean exist = true;
		CompetitionInfo compInfo = null;
		try {
			compInfo = this.compService.getCompInfoByCompID(compID);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$404.getRspCode(), RspCodeValue.$404.getMsg()));
		}
		if(compInfo == null){
			return HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$405.getRspCode(), RspCodeValue.$405.getMsg()));
		}
		if(compInfo.getCompState() > 0){
			return HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$406.getRspCode(), RspCodeValue.$406.getMsg()));
		}
		if(!compInfo.getCompName().equals(compName)){
			try {
				exist = this.compService.compNameExist(compName);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				return HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$403.getRspCode(), RspCodeValue.$403.getMsg()));
			}
			if(exist){
				return HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$402.getRspCode(), RspCodeValue.$402.getMsg()));
			}
		}
		compInfo.setCompName(compName);
		compInfo.setRoundTempID(roundTempID);
		compInfo.setRegFee(regFee);
		compInfo.setServiceFee(serviceFee);
		compInfo.setBeginChip(beginChip);
		compInfo.setAmountUnit(unit);
		compInfo.setAword(aword);
		compInfo.setStopRegRank(stopRegRank);
		compInfo.setLeastPrize(leastPrize);
		compInfo.setTableType(tableType);
		compInfo.setReEntry(reEntry);
		compInfo.setCompType(compType);
		compInfo.setStartTime(new Date(beginTime));
		compInfo.setUpdateTime(new Date());
		try {
			this.compService.editCompetionInfo(compInfo);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$407.getRspCode(), RspCodeValue.$407.getMsg()));
		}
		jsonStr = HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$1.getRspCode(), RspCodeValue.$1.getMsg()));
		return jsonStr;
	}
	
	public String delCompetition(int compID){
		String jsonStr = "";
		CompetitionInfo compInfo = null;
		try {
			compInfo = this.compService.getCompInfoByCompID(compID);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$404.getRspCode(), RspCodeValue.$404.getMsg()));
		}
		if(compInfo == null){
			return HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$405.getRspCode(), RspCodeValue.$405.getMsg()));
		}
		//判断比赛状态:3,4,5都不能删除
		if(CompetitionInfo.COMPSTATE.STATE_REGEND_NOBEGIN == compInfo.getCompState() || 
				CompetitionInfo.COMPSTATE.STATE_RUNNING == compInfo.getCompState() || 
				CompetitionInfo.COMPSTATE.STATE_END == compInfo.getCompState()){
			logger.error("比赛状态不允许删除：[compID="+compID+",compState="+compInfo.getCompState());
			return HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$408.getRspCode(), RspCodeValue.$408.getMsg()));
		}
		//比赛状态为：1，2需要判断比赛是否有人，如果有人不允许删除，提示先淘汰选手，然后再删除比赛
		if(CompetitionInfo.COMPSTATE.STATE_REGING_NOBEGIN == compInfo.getCompState() || 
				CompetitionInfo.COMPSTATE.STATE_REGING_BEGINED == compInfo.getCompState()){
			//查看是否有选手
			boolean exist = true;
			try {
				exist = this.memCompService.existMemOfRunningAndAdvanByCompID(compID);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				return HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$409.getRspCode(), RspCodeValue.$409.getMsg()));
			}
			if(exist){
				return HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$410.getRspCode(), RspCodeValue.$410.getMsg()));
			}
		}
		try {
			this.compService.updateDelCompetitionByID(compID);
		} catch (Exception e) {
			logger.error(e.getMessage()+",compID="+compID, e);
			return HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$411.getRspCode(), RspCodeValue.$411.getMsg()));
		}
		Application.setTriggingMsgForPushing(new SystemBasePushTriggMsg(Application.OPERTYPE.DELCOMP, 0, compID));
		jsonStr = HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$1.getRspCode(), RspCodeValue.$1.getMsg()));
		return jsonStr;
	}

	@Override
	public String getAllCompetitions(int sysType) {
		List<CompetitionInfo> allCompList = null;
		String jsonStr = "";
		try {
			allCompList = this.compService.getAllCompetitionList(sysType);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$414.getRspCode(), RspCodeValue.$414.getMsg()));
		}
		if(allCompList == null || allCompList.size() <= 0){
			return HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$415.getRspCode(), RspCodeValue.$415.getMsg()));
		}
		Map<String, Object>  map = HIUtil.createJsonResInitMap(RspCodeValue.$1.getRspCode(), RspCodeValue.$1.getMsg());
		map.put("competitions", allCompList);
		jsonStr = HIUtil.toJsonNormalField(map);
		return jsonStr;
	}
	
	/**
	 * 查询所有比赛,带有盲注信息（不包括已删除的比赛）
	 * @param sysType
	 * @return
	 */
	public String getAllComptitionsNoDel(int sysType){
		CompetitionInfo_Day  compInfo_Day = null;
		CompListInfo compListInfo = null;
		Map<String, CompetitionInfo_Day> weekDayMap = null;
		List<CompetitionInfo_Day> dayList = new ArrayList<CompetitionInfo_Day>();
		String weekDayKey = null;
		CompRunningRound runningRound = null;
		int curRank = 0;
		int curType = 0;
		int curBeforeChip = 0;
		List<CompetitionInfo> allCompetitionList = null;
		String jsonStr = "";
		try {
			allCompetitionList = this.compService.getAllCompWithRunningRound(sysType);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$414.getRspCode(), RspCodeValue.$414.getMsg()));
		}
		if(allCompetitionList == null || allCompetitionList.size() <= 0){
			return HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$415.getRspCode(), RspCodeValue.$415.getMsg()));
		}
		//先分隔出yyyy-MM-dd E格式的对象,然后把compListInfo对象放到父对象的list集合中
		weekDayMap = new LinkedHashMap<String, CompetitionInfo_Day>();
		for (int i = 0; i < allCompetitionList.size(); i++) {
			curRank = 0;
			curType = 0;
			CompetitionInfo compInfo = allCompetitionList.get(i);
			weekDayKey = DateUtils.formatDate(compInfo.getStartTime(), DateUtils.PATTERN8);
			if(!weekDayMap.containsKey(weekDayKey)){
				compInfo_Day = new CompetitionInfo_Day();
				compInfo_Day.setDay_week(weekDayKey);
				weekDayMap.put(weekDayKey, compInfo_Day);
			}
			runningRound = compInfo.getCompRunningRound();
			if(runningRound != null){
				curRank = runningRound.getCurRank();
				curType = runningRound.getCurType();
				curBeforeChip = runningRound.getCurBeforeChip();
			}
			compListInfo = new CompListInfo(compInfo.getCompID(), compInfo.getCompName(), 
					DateUtils.formatDate(compInfo.getStartTime(), DateUtils.PATTERN7), compInfo.getCompState(), 
					compInfo.getRegFee(), compInfo.getServiceFee(), compInfo.getBeginChip(), curBeforeChip,
					curRank, curType, compInfo.getReEntry(), compInfo.getAmountUnit());
			compInfo_Day.getCompListInfo().add(compListInfo);
		}
		Set<Entry<String, CompetitionInfo_Day>> set = weekDayMap.entrySet();
		Iterator<Entry<String, CompetitionInfo_Day>> it = set.iterator();
		Entry<String, CompetitionInfo_Day> entry = null;
		while(it.hasNext()){
			entry = it.next();
			dayList.add(entry.getValue());
		}
		Map<String, Object>  map = HIUtil.createJsonResInitMap(RspCodeValue.$1.getRspCode(), RspCodeValue.$1.getMsg());
		map.put("padCompList", dayList);
		jsonStr = HIUtil.toJsonNormalField(map);
		return jsonStr;
	}

	@Override
	public String manualPauseCompetition(int compID) {
		String jsonStr = "";
		CompetitionInfo compInfo = null;
		try {
			this.compService.updatePauseCompetitionByID(compID);
			compInfo = this.compService.getCompetitionInfoByCompID(compID);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$416.getRspCode(), RspCodeValue.$416.getMsg()));
		}
		Application.setTriggingMsgForPushing(new SystemBasePushTriggMsg(Application.OPERTYPE.PAUSECOMP, 0, compID));
		Map<String, Object> resMap = HIUtil.createJsonResInitMap(RspCodeValue.$1.getRspCode(), RspCodeValue.$1.getMsg());
		resMap.put("compPause", compInfo.getCompPause());
		jsonStr = HIUtil.toJsonNormalField(resMap);
		return jsonStr;
	}

	@Override
	public String endCompetition(int compID) {
		String jsonStr = "";
		int endRes = 0;
		try {
			endRes = this.compService.updateEndCompetitionByID(compID);
			if(endRes == -1){//比赛信息不存在
				jsonStr = HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$405.getRspCode(), RspCodeValue.$405.getMsg()));
			}else if(endRes == 0){//比赛状态不是“停止报名-比赛进行中”
				jsonStr = HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$418.getRspCode(), RspCodeValue.$418.getMsg()));
			}else if(endRes == -2){
				jsonStr = HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$431.getRspCode(), RspCodeValue.$431.getMsg()));
			}else if(endRes == -3){//晋级人数大于奖励的最大人数
				jsonStr = HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$469.getRspCode(), RspCodeValue.$469.getMsg()));
			}else{
				jsonStr = HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$1.getRspCode(), RspCodeValue.$1.getMsg()));
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$417.getRspCode(), RspCodeValue.$417.getMsg()));
		}
		Application.setTriggingMsgForPushing(new SystemBasePushTriggMsg(Application.OPERTYPE.ENDCOMP, 0, compID));
		return jsonStr;
	}
	
	/**
	 * 查询比赛的进程信息
	 * @param compID
	 * @return
	 */
	public String getCompRunningInfo(int compID){
		String jsonStr = "";
		
		CompetitionInfo compInfo = null;
		ComManRunningInfo compManRunningInfo = null;
		CompRunningRound compCurRound = null;
		Round round = null;
		int totalRegedPlayerCount = 0;
		int totalLivedPlayerCount = 0;
		int totalRegedPlayerCountEdit = 0;
		int averChip = 0;
		int restTime = 0;//剩余倒计时长
		try {
			compInfo = this.compService.getCompInfoByCompID(compID);
			compCurRound = this.compCurRoundManService.getCurRoundOfComp(compID);
			totalRegedPlayerCount = this.compService.getCompAllRegedPlayerCount(compID);
			totalLivedPlayerCount = this.compService.getCompAllLivedPlayerCount(compID);
			if(compCurRound != null){
				round = this.roundService.getRoundByRank(compInfo.getRoundTempID(), compCurRound.getCurRank(), compCurRound.getCurType());
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$424.getRspCode(), RspCodeValue.$424.getMsg()));
		}
		if(compInfo == null){
			return HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$405.getRspCode(), RspCodeValue.$405.getMsg()));
		}
		if(compInfo.getCompState() == CompetitionInfo.COMPSTATE.STATE_END || 
				compInfo.getCompState() == CompetitionInfo.COMPSTATE.STATE_DEL || 
				compInfo.getCompState() == CompetitionInfo.COMPSTATE.STATE_NOREG){
			return HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$437.getRspCode(), RspCodeValue.$437.getMsg()));
		}
		if(compCurRound == null){//比赛没有运行盲注信息
			return HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$425.getRspCode(), RspCodeValue.$425.getMsg()));
		}
		if(totalRegedPlayerCount <= 0){//报名人数为0
			averChip = 0;
			totalRegedPlayerCountEdit = 0;
		}else{
			totalRegedPlayerCountEdit = totalRegedPlayerCount - compInfo.getSubPlayerCount();
			if(totalLivedPlayerCount <= 0){//存活选手为0
				averChip = 0;
			}else{
				averChip = (int)(totalRegedPlayerCountEdit * compInfo.getBeginChip() / totalLivedPlayerCount);
			}
		}
		//倒计时,秒
		long start = (long)(compCurRound.getReStartTime() / 1000);
		int stepLen = compCurRound.getStepLen();
		long cur = (long)(new Date().getTime() / 1000);
		restTime = (int)(stepLen - (cur - start));
		if(restTime <= 0){//比赛当前运行盲注已经计时过了。
			restTime = 0;
		}
		compManRunningInfo = new ComManRunningInfo(compID, compCurRound.getCurRank(), compCurRound.getCurType(), restTime, compCurRound.getCurSmallBlind(), compCurRound.getCurBigBlind(), compCurRound.getCurBeforeChip(), totalRegedPlayerCount, totalRegedPlayerCountEdit, averChip, round.getStepLen(), compInfo.getCompPause());
		Map<String, Object> resmap = HIUtil.createJsonResInitMap(RspCodeValue.$1.getRspCode(), RspCodeValue.$1.getMsg());
		resmap.put("compProgessInfo", compManRunningInfo);
		jsonStr = HIUtil.toJsonNormalField(resmap);
		return jsonStr;
	}
	
	/**
	 * 比赛管理-座位，查询比赛的存活选手的座位信息
	 * @param compID
	 * @return
	 */
	public String getCompMemsSeatInfo(int compID){
		String jsonStr = "";
		
		List<CompMemInfo> compLivedMemInfo = null;
		CompMemInfo compMemInfo = null;
		List<CardTable> tableList = null;
		CardTable table = null;
		CompManTableInfo compManTableInfo = null;
		CompManSeatInfo compManSeatInfo = null;
		Entry<Integer, CompManTableInfo> entry = null;
		CompetitionInfo compInfo = null;
		int usingTableIdleSeatCount = 0;//该比赛所有使用的桌子的空闲座位总和
		int tableUsingSeatCount = 0;//某个桌子已占用的座位总数
		
		Map<Integer, CompManTableInfo> tableSortedMap = new LinkedHashMap<Integer, CompManTableInfo>();
		List<CompManTableInfo> compManTableInfoList = new ArrayList<CompManTableInfo>();
		
		try {
			compInfo = this.compService.getCompetitionInfoByCompID(compID);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			jsonStr = HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$404.getRspCode(), RspCodeValue.$404.getMsg()));
			return jsonStr;
		}
		if(compInfo == null){//比赛不存在
			jsonStr = HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$405.getRspCode(), RspCodeValue.$405.getMsg()));
			return jsonStr;
		}else if(compInfo.getAssignSeat() == CompetitionInfo.ASSIGNSEATSTATE.NOUSE){//没有使用座位信息，比赛管理-座位，不可使用
			jsonStr = HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$421.getRspCode(), RspCodeValue.$421.getMsg()));
			return jsonStr;
		}else if(compInfo.getCompState() == CompetitionInfo.COMPSTATE.STATE_DEL || 
				compInfo.getCompState() == CompetitionInfo.COMPSTATE.STATE_END){//删除、结束的比赛无法查看
			jsonStr = HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$422.getRspCode(), RspCodeValue.$422.getMsg()));
			return jsonStr;
		}
		int tableType = compInfo.getTableType();
		try {
			compLivedMemInfo = this.memCompService.getCompMemsSeatInfo(compID);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			jsonStr = HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$404.getRspCode(), RspCodeValue.$404.getMsg()));
			return jsonStr;
		}
		try {
			tableList = this.tableService.getCompUsedAndIdleTables(compID, compInfo.getSysType());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			jsonStr = HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$404.getRspCode(), RspCodeValue.$404.getMsg()));
			return jsonStr;
		}
		//tableList，说明比赛没有占用牌桌
		if(tableList == null){
			jsonStr = HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$420.getRspCode(), RspCodeValue.$420.getMsg()));
			return jsonStr;
		}
		//tableList转成Map<tableNO, CompManTableInfo>
		for (int i = 0; i < tableList.size(); i++) {
			table = tableList.get(i);
			compManTableInfo = new CompManTableInfo(table.getTableNO(), table.getTableState(), tableType);
			tableSortedMap.put(tableList.get(i).getTableNO(), compManTableInfo);
		}
		//该算法，只包含有人的位置的信息
		for (int i = 0; i < compLivedMemInfo.size(); i++) {//桌号，座位号升序排列的集合
			compMemInfo = compLivedMemInfo.get(i);
			compManTableInfo = tableSortedMap.get(compMemInfo.getTableNO());
			if(compManTableInfo != null){//比赛有这张桌子
				compManSeatInfo = new CompManSeatInfo(compMemInfo.getId(), compMemInfo.getSeatNO(), 
						compMemInfo.getMemID(), compMemInfo.getCardNO(), compMemInfo.getMemImage());
				compManTableInfo.getMemSeatInfoList().add(compManSeatInfo);
			}
		}
		Set<Entry<Integer, CompManTableInfo>> set = tableSortedMap.entrySet();
		Iterator<Entry<Integer, CompManTableInfo>> it = set.iterator();
		while(it.hasNext()){
			entry = it.next();
			compManTableInfoList.add(entry.getValue());
			if(entry.getValue().getTableState() == CardTable.TABLESTATE.TABLEUSING){//该比赛占用的牌桌
				usingTableIdleSeatCount += (tableType - entry.getValue().getMemSeatInfoList().size());
			}
		}
		//设置桌子上的状态按钮
		for (int i = 0; i < compManTableInfoList.size(); i++) {
			compManTableInfo = compManTableInfoList.get(i);
			//空闲状态，则锁定。
			if(compManTableInfo.getTableState() == CardTable.TABLESTATE.TABLEIDLE){
				compManTableInfo.setButton(CompManTableInfo.TABLEBUTOPER.LOCK);
				continue;
			}
			//占用状态且牌桌上无人，则释放
			if(compManTableInfo.getMemSeatInfoList().size() <= 0 && compManTableInfo.getTableState() == CardTable.TABLESTATE.TABLEUSING){
				compManTableInfo.setButton(CompManTableInfo.TABLEBUTOPER.RELEASE);
				continue;
			}
			//某张桌子上有人，且占用的位置数量小于等于剩余被占用的桌子空闲位置个数，则爆桌按钮
			tableUsingSeatCount = compManTableInfo.getMemSeatInfoList().size();
			if(tableUsingSeatCount > 0 && tableUsingSeatCount <= (usingTableIdleSeatCount + tableUsingSeatCount - tableType)){
				compManTableInfo.setButton(CompManTableInfo.TABLEBUTOPER.BURSTTABLE);
				continue;
			}
		}
		Map<String, Object> map = HIUtil.createJsonResInitMap(RspCodeValue.$1.getRspCode(), RspCodeValue.$1.getMsg());
		map.put("compID", compID);
		map.put("startTime", compInfo.getStartTime());
		map.put("compTableInfos", compManTableInfoList);
		jsonStr = HIUtil.toJsonNormalField(map);
		return jsonStr;
	}
	
	/**
	 * 比赛管理--玩家，获取存活的所有玩家，包括已晋级
	 * @param compID
	 * @return
	 */
	public String getLivedPlayersInfoByComp(int compID){
		String jsonStr = "";
		CompManPlayerInfo playersInfo = null;
		try {
			playersInfo = this.compService.getLivedPlayersByComp(compID);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			jsonStr = HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$441.getRspCode(), RspCodeValue.$441.getMsg()));
			return jsonStr;
		}
		Map<String, Object> resmap = HIUtil.createJsonResInitMap(RspCodeValue.$1.getRspCode(), RspCodeValue.$1.getMsg());
		if(playersInfo != null){
			resmap.put("playersTotalInfo", playersInfo);
		}
		jsonStr = HIUtil.toJsonNormalField(resmap);
		return jsonStr;
	}
	
	/**
	 * 比赛管理--筹码，获取存活的所有玩家筹码信息，包括已晋级
	 * @param compID
	 * @return
	 */
	public String getLivedPlayersChipByComp(int compID){
		String jsonStr = "";
		List<CompMemInfo> list = null;
		try {
			list = this.compService.getLivedPlayerChipByComp(compID);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			jsonStr = HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$442.getRspCode(), RspCodeValue.$442.getMsg()));
			return jsonStr;
		}
		if(list == null || list.size() <= 0){
			jsonStr = HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$443.getRspCode(), RspCodeValue.$443.getMsg()));
			return jsonStr;
		}
		Map<String, Object> resmap = HIUtil.createJsonResInitMap(RspCodeValue.$1.getRspCode(), RspCodeValue.$1.getMsg());
		resmap.put("playersChip", list);
		jsonStr = HIUtil.toJsonNormalField(resmap);
		return jsonStr;
	}
	
	/**
	 * 比赛管理--筹码，获取指定存活的玩家筹码信息
	 * @param compID
	 * @param nfcID
	 * @param cardNO
	 * @return
	 */
	public String getSpecPlayerChipByComp(int compID, long nfcID, String cardNO){
		String jsonStr = "";
		List<CompMemInfo> list = null;
		try {
			if(StringUtils.isEmpty(cardNO)){
				list = this.compService.getSpecPlayerChipOfCompByNfcID(compID, nfcID);
			}else{
				list = this.compService.getSpecPlayerChipOfCompByCardNO(compID, cardNO);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			jsonStr = HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$442.getRspCode(), RspCodeValue.$442.getMsg()));
			return jsonStr;
		}
		if(list == null || list.size() <= 0){
			jsonStr = HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$444.getRspCode(), RspCodeValue.$444.getMsg()));
			return jsonStr;
		}else if(list.size() > 1){//有重复数据
			jsonStr = HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$445.getRspCode(), RspCodeValue.$445.getMsg()));
			return jsonStr;
		}
		Map<String, Object> resmap = HIUtil.createJsonResInitMap(RspCodeValue.$1.getRspCode(), RspCodeValue.$1.getMsg());
		resmap.put("playerChip", list.get(0));
		jsonStr = HIUtil.toJsonNormalField(resmap);
		return jsonStr;
	}
	
	/**
	 * 比赛开启牌桌，如果compstate=0，同时更新compstate=1
	 * @param compID
	 * @param tableNOArr
	 * @param empUuid
	 * @return
	 * @throws Exception
	 */
	public String openCompTables(int compID, int[] tableNOArr, String empUuid){
		String jsonStr = "";
		try {
			this.compService.updateOpenRegistration(compID, tableNOArr);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			jsonStr = HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$426.getRspCode(), RspCodeValue.$426.getMsg()));
			return jsonStr;
		}
		Application.setTriggingMsgForPushing(new SystemBasePushTriggMsg(Application.OPERTYPE.OPENTREG, 0, compID));
		jsonStr = HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$1.getRspCode(), RspCodeValue.$1.getMsg()));
		return jsonStr;
	}
	
	/**
	 * 平衡牌桌选手
	 * @param cmID
	 * @param compID
	 * @param memID
	 * @param tableNO
	 * @param seatNO
	 * @param empUuid
	 * @return
	 */
	public String balanceCompMember(int cmID, int compID, int memID, int tableNO, int seatNO, String empUuid){
		String jsonStr = "";
		int res = 0;
		MemberInfo memInfo = null;
		try {
			memInfo = this.memManService.findMemByID(memID);
			res = this.memCompService.updateBalanceCompMember(cmID, compID, memID, tableNO, seatNO, empUuid);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			jsonStr = HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$428.getRspCode(), RspCodeValue.$428.getMsg()));
			return jsonStr;
		}
		switch(res){
			case 0:
				jsonStr = HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$428.getRspCode(), RspCodeValue.$428.getMsg()));
				return jsonStr;
			case -1:
				jsonStr = HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$429.getRspCode(), RspCodeValue.$429.getMsg()));
				return jsonStr;
			case -2:
				jsonStr = HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$430.getRspCode(), RspCodeValue.$430.getMsg()));
				return jsonStr;
		}
		Application.setTriggingMsgForPushing(new SystemBasePushTriggMsg(Application.OPERTYPE.BALANCE, 0, compID));
		Map<String, Object> map = HIUtil.createJsonResInitMap(RspCodeValue.$1.getRspCode(), RspCodeValue.$1.getMsg());
		Map<String, Integer> seatInfo = new HashMap<String, Integer>();
		seatInfo.put("tableNO", tableNO);
		seatInfo.put("seatNO", seatNO);
		seatInfo.put("memSex", memInfo.getMemSex());
		map.put("newSeatInfo", seatInfo);
		jsonStr = HIUtil.toJsonNormalField(map);
		return jsonStr;
	}
	
	/**
	 * 释放比赛一张牌桌
	 * @param compID
	 * @param tableNO
	 * @return
	 */
	public String releaseCompTable(int compID, int tableNO){
		String jsonStr = "";
		int res = 0;
		try {
			res = this.compService.updateReleaseCompTable(compID, tableNO);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			jsonStr = HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$427.getRspCode(), RspCodeValue.$427.getMsg()));
			return jsonStr;
		}
		if(res == 0){
			jsonStr = HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$353.getRspCode(), RspCodeValue.$353.getMsg()));
			return jsonStr;
		}else if(res == -1){
			jsonStr = HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$354.getRspCode(), RspCodeValue.$354.getMsg()));
			return jsonStr;
		}else if(res == -2){
			jsonStr = HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$355.getRspCode(), RspCodeValue.$355.getMsg()));
			return jsonStr;
		}else if(res == -3){
			jsonStr = HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$356.getRspCode(), RspCodeValue.$356.getMsg()));
			return jsonStr;
		}
		Application.setTriggingMsgForPushing(new SystemBasePushTriggMsg(Application.OPERTYPE.RELEASETABLE, 0, compID));
		jsonStr = HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$1.getRspCode(), RspCodeValue.$1.getMsg()));
		return jsonStr;
	}
	
	/**
	 * 比赛淘汰选手，淘汰后，把座位信息放回到待分座位表。
	 * @param cmID
	 * @param memID
	 * @param compID
	 * @return
	 */
	public String outMemFromComp(int tableNO, int seatNO, int memID, int compID){
		String jsonStr = "";
		int res = 0;
		try {
			res = this.compService.updateOutMemFromComp(tableNO, seatNO, memID, compID);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			jsonStr = HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$432.getRspCode(), RspCodeValue.$432.getMsg()));
			return jsonStr;
		}
		switch(res){
			case 0:
				jsonStr = HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$433.getRspCode(), RspCodeValue.$433.getMsg()));
				return jsonStr;
			case -1:
				jsonStr = HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$434.getRspCode(), RspCodeValue.$434.getMsg()));
				return jsonStr;
			case -2:
				jsonStr = HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$435.getRspCode(), RspCodeValue.$435.getMsg()));
				return jsonStr;
		}
		Application.setTriggingMsgForPushing(new SystemBasePushTriggMsg(Application.OPERTYPE.OUTMEM, 0, compID));
		jsonStr = HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$1.getRspCode(), RspCodeValue.$1.getMsg()));
		return jsonStr;
	}
	
	/**
	 * 编辑扣减选手数量，累计减
	 * @param compID
	 * @param subNum
	 * @return
	 */
	public String editSubPlayer(int compID, int subNum){
		String jsonStr = "";
		EditSubPlayerVO vo = null;
		try {
			vo = this.compService.updateSubPlayer(compID, subNum);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			jsonStr = HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$436.getRspCode(), RspCodeValue.$436.getMsg()));
			return jsonStr;
		}
		Application.setTriggingMsgForPushing(new SystemBasePushTriggMsg(Application.OPERTYPE.EDITREGEDPLAYER, 0, compID));
		Map<String, Object> resmap = HIUtil.createJsonResInitMap(RspCodeValue.$1.getRspCode(), RspCodeValue.$1.getMsg());
		resmap.put("subRes", vo);
		jsonStr = HIUtil.toJsonNormalField(resmap);
		return jsonStr;
	}
	
	/**
	 * 更新选手的筹码数量
	 * @param mcID
	 * @param compID
	 * @param memID
	 * @param chip
	 * @return
	 */
	public String updatePlayerChip(int mcID, int compID, int memID, int chip){
		String jsonStr = "";
		try {
			this.memCompService.updatePlayerChip(mcID, compID, memID, chip);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			jsonStr = HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$446.getRspCode(), RspCodeValue.$446.getMsg()));
			return jsonStr;
		}
		jsonStr = HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$1.getRspCode(), RspCodeValue.$1.getMsg()));
		return jsonStr;
	}
	
	/**
	 * 修改当前盲注计时时间
	 * @param compID
	 * @param curRank
	 * @param jumpTime
	 * @return
	 */
	public String editRoundJumpTime(int compID, int curRank, int jumpTime){
		String jsonStr = "";
		int res = 0;
		try {
			res = this.compService.updateRoundJumpTime(compID, curRank, jumpTime);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			jsonStr = HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$447.getRspCode(), RspCodeValue.$447.getMsg()));
			return jsonStr;
		}
		if(res == -1){
			jsonStr = HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$470.getRspCode(), RspCodeValue.$470.getMsg()));
			return jsonStr;
		}else if(res == -2){
			jsonStr = HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$472.getRspCode(), RspCodeValue.$472.getMsg()));
			return jsonStr;
		}else if(res == -3){
			jsonStr = HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$471.getRspCode(), RspCodeValue.$471.getMsg()));
			return jsonStr;
		}else if(res == 0){
			jsonStr = HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$447.getRspCode(), RspCodeValue.$447.getMsg()));
			return jsonStr;
		}
		Application.setTriggingMsgForPushing(new SystemBasePushTriggMsg(Application.OPERTYPE.JUMPBLINDTIME, 0, compID));
		jsonStr = HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$1.getRspCode(), RspCodeValue.$1.getMsg()));
		return jsonStr;
	}
	
	/**
	 * 退一级盲注
	 * @param compID
	 * @param curRank
	 * @return
	 */
	public String goBackRound(int compID, int curRank){
		String jsonStr = "";
		int res = 0;
		try {
			res = this.compService.updateGoBackRound(compID);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			jsonStr = HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$448.getRspCode(), RspCodeValue.$448.getMsg()));
			return jsonStr;
		}
		if(res == -1){
			jsonStr = HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$470.getRspCode(), RspCodeValue.$470.getMsg()));
			return jsonStr;
		}
		Application.setTriggingMsgForPushing(new SystemBasePushTriggMsg(Application.OPERTYPE.GOBACKBLIND, 0, compID));
		jsonStr = HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$1.getRspCode(), RspCodeValue.$1.getMsg()));
		return jsonStr;
	}
	
	/**
	 * 进一级盲注
	 * @param compID
	 * @param curRank
	 * @return
	 */
	public String goForwardRound(int compID, int curRank){
		String jsonStr = "";
		int res = 0;
		try {
			res = this.compService.updateGoForwardRound(compID);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			jsonStr = HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$449.getRspCode(), RspCodeValue.$449.getMsg()));
			return jsonStr;
		}
		if(res == -1){
			jsonStr = HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$470.getRspCode(), RspCodeValue.$470.getMsg()));
			return jsonStr;
		}
		Application.setTriggingMsgForPushing(new SystemBasePushTriggMsg(Application.OPERTYPE.GOFORWARDBLIND, 0, compID));
		jsonStr = HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$1.getRspCode(), RspCodeValue.$1.getMsg()));
		return jsonStr;
	}
	
	/**
	 * 比赛管理--奖池，查询奖励信息
	 * @param compID
	 * @return
	 */
	public String getCompMemPrizeInfoList(int compID){
		String jsonStr = "";
		Map<String, Object> resMap = null;
		try {
			resMap = this.compService.getCompMemPrizeInfoList(compID);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			jsonStr = HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$452.getRspCode(), RspCodeValue.$452.getMsg()));
			return jsonStr;
		}
		if(resMap == null){
			jsonStr = HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$452.getRspCode(), RspCodeValue.$452.getMsg()));
			return jsonStr;
		}
//		Map<String, Object> map = HIUtil.createJsonResInitMap(RspCodeValue.$1.getRspCode(), RspCodeValue.$1.getMsg());
//		map.putAll(resMap);
//		jsonStr = HIUtil.toJsonNormalField(map);
		jsonStr = HIUtil.toJsonNormalField(resMap);
		return jsonStr;
	}
	
	/**
	 * 查询比赛运行奖励表中的一条记录
	 * @param ranking
	 * @param compID
	 * @param memID
	 * @return
	 */
	public String getCompMemPrizeInfo(int ranking, int compID, int memID){
		String jsonStr = "";
		CompManPrizeInfo compManPrizeInfo = null;
		try {
			compManPrizeInfo = this.compService.getCompMemPrizeInfo(ranking, compID, memID);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			jsonStr = HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$453.getRspCode(), RspCodeValue.$453.getMsg()));
			return jsonStr;
		}
		if(compManPrizeInfo == null){
			jsonStr = HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$454.getRspCode(), RspCodeValue.$454.getMsg()));
			return jsonStr;
		}
		Map<String, Object> map = HIUtil.createJsonResInitMap(RspCodeValue.$1.getRspCode(), RspCodeValue.$1.getMsg());
		map.put("compManPrizeInfo", compManPrizeInfo);
		jsonStr = HIUtil.toJsonNormalField(map);
		return jsonStr;
	}
	
	/**
	 * 编辑奖池中选手的奖金
	 * @param ranking
	 * @param memID
	 * @param compID
	 * @param amountInt
	 * @return
	 */
	public String editCompMemPrizeInfo(int ranking, int memID, int compID, int amountInt){
		int res = 0;
		String jsonStr = "";
		try {
			res = this.compService.updateCompManPrizeInfoAmount(ranking, compID, amountInt);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			jsonStr = HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$455.getRspCode(), RspCodeValue.$455.getMsg()));
			return jsonStr;
		}
		if(res <= 0){
			logger.error("未找到更新记录，ranking=" + ranking + ",compID=" + compID);
			jsonStr = HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$455.getRspCode(), RspCodeValue.$455.getMsg()));
			return jsonStr;
		}
		jsonStr = HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$1.getRspCode(), RspCodeValue.$1.getMsg()));
		return jsonStr;
	}
	
	/**
	 * 查询待打印的奖励小条信息
	 * @param ranking
	 * @param memID
	 * @param compID
	 * @return
	 */
	public String getPlayerAwordInfoForPrint(int ranking, int memID, int compID){
		String jsonStr = "";
		PrizePrintInfo prizePrintInfo = null;
		try {
			prizePrintInfo = this.compService.getPrizePrintInfo(ranking, compID);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			jsonStr = HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$456.getRspCode(), RspCodeValue.$456.getMsg()));
			return jsonStr;
		}
		if(prizePrintInfo == null){
			jsonStr = HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$456.getRspCode(), RspCodeValue.$456.getMsg()));
			return jsonStr;
		}
		Map<String, Object> map = HIUtil.createJsonResInitMap(RspCodeValue.$1.getRspCode(), RspCodeValue.$1.getMsg());
		map.put("prizePrintInfo", prizePrintInfo);
		jsonStr = HIUtil.toJsonNormalField(map);
		return jsonStr;
	}
	
	/**
	 * 比赛进阶管理--比赛导入，比赛列表查询
	 * @param sysType
	 * @return
	 */
	public String getCompListForImportComp(int sysType){
		String jsonStr = "";
		List<CompetitionInfo> origList = null;
		List<CompetitionInfo> destList = null;
		try {
			origList = this.compService.getOrigCompListForImport(sysType);
			destList = this.compService.getDestListForImport(sysType);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			jsonStr = HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$457.getRspCode(), RspCodeValue.$457.getMsg()));
			return jsonStr;
		}
		Map<String, Object> map = HIUtil.createJsonResInitMap(RspCodeValue.$1.getRspCode(), RspCodeValue.$1.getMsg());
		if(origList == null){
			map.put("origList", Collections.EMPTY_LIST);
		}else{
			map.put("origList", origList);
		}
		if(destList == null){
			map.put("destList", Collections.EMPTY_LIST);
		}else{
			map.put("destList", destList);
		}
		jsonStr = HIUtil.toJsonNormalField(map);
		return jsonStr;
	}
	
	/**
	 * 晋级导入比赛，固定分座：源比赛只能有一个；随机分桌：无限制
	 * @param origCompID
	 * @param destCompID
	 * @param condition:0、随机分座；1、固定分座
	 * @param empUuid
	 * @return
	 */
	public String importComps(int[] origCompID, int destCompID, int condition, String empUuid){
		String jsonStr = "";
		CompetitionInfo temp = null;
		CompetitionMember tempCM1 = null;
		CompetitionMember tempCM2 = null;
		CompetitionInfo destComp = null;
		//导入的目标比赛占用的桌子集合
		List<CardTable> usedTables = null;
		//待导入的原比赛集合
		List<CompetitionInfo> origCompList = null;
		//所有晋级选手汇总后的集合
		List<CompetitionMember> advanCMList = new ArrayList<CompetitionMember>();
		//待导入的排重的晋级选手信息
		Map<Integer, CompetitionMember> uniqueCMMap = new HashMap<Integer, CompetitionMember>();
		//待导入源比赛占用的桌子号，这里有个实际场景的情况说名：实际场景中，固定座位只会用在源比赛只有一场待导入比赛上，所以，肯定不会出现桌号重复的问题。
		Set<Integer> uniqueTableSet = new HashSet<Integer>();
		List<Integer> orignalTablesList = new ArrayList<Integer>();
		//出现重复选手后淘汰掉的选手集合,最后需要做按筹码量由低到高的排序
		List<CompetitionMember> outCMList = new ArrayList<CompetitionMember>();
		
		if(condition == 1 && origCompID.length > 1){
			jsonStr = HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$468.getRspCode(), RspCodeValue.$468.getMsg()));
			return jsonStr;
		}
		try {
			//检查比赛是否已经导入过，源比赛，目标比赛都检查
			boolean existSourceCompsLog = this.compImportLogService.existSourceCompImportedLogs(origCompID);
			if(existSourceCompsLog){
				jsonStr = HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$485.getRspCode(), RspCodeValue.$485.getMsg()));
				return jsonStr;
			}
			boolean existDestCompLog = this.compImportLogService.existDestCompImportedLog(destCompID);
			if(existDestCompLog){
				jsonStr = HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$486.getRspCode(), RspCodeValue.$486.getMsg()));
				return jsonStr;
			}
			//1、检查目标比赛的状态
			destComp = this.compService.getCompInfoByCompID(destCompID);
			if(destComp == null || destComp.getCompState() != CompetitionInfo.COMPSTATE.STATE_REGING_NOBEGIN){
				jsonStr = HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$458.getRspCode(), RspCodeValue.$458.getMsg()));
				return jsonStr;
			}
			//2、检查待导入比赛的状态
			origCompList = this.compService.getCompListByIDs(origCompID);
			if(origCompList.size() != origCompID.length){//待导入的比赛数量不符合
				jsonStr = HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$457.getRspCode(), RspCodeValue.$457.getMsg()));
				return jsonStr;
			}
			for (int i = 0; i < origCompList.size(); i++) {
				temp = origCompList.get(i);
				if(temp.getCompState() != CompetitionInfo.COMPSTATE.STATE_END){//状态不是已结束
					jsonStr = HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$459.getRspCode(), RspCodeValue.$459.getMsg()));
					return jsonStr;
				}
				if(temp.getCompType() != CompetitionInfo.COMPTYPE.PROMOTION){//不是晋级赛
					jsonStr = HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$460.getRspCode(), RspCodeValue.$460.getMsg()));
					return jsonStr;
				}
			}
			//3、把所有源比赛的晋级选手合到一个集合
			for (int i = 0; i < origCompID.length; i++) {
				List<CompetitionMember> advanCMTempList = this.memCompService.getAllAdvanMemByCompID(origCompID[i]);
				advanCMList.addAll(advanCMTempList);//还未排重的所有晋级选手集合
			}
			if(advanCMList.size() <= 0){
				jsonStr = HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$484.getRspCode(), RspCodeValue.$484.getMsg()));
				return jsonStr;
			}
			//4、查询待导入比赛的晋级状态的选手数量（排重后），查询目标比赛的待分配座位数量，二者比较
			for (int i = 0; i < advanCMList.size(); i++) {//排重选手，有重复的选手，就选最高筹码量的，剩下的都以淘汰状态进入目标表赛，并且如果目标比赛，带有奖励表，就要产生名次。
				tempCM1 = advanCMList.get(i);
				uniqueTableSet.add(tempCM1.getTableNO());//待导入源比赛占用的桌子号（固定分桌才会使用）
				if(uniqueCMMap.containsKey(tempCM1.getMemID())){//排重后的晋级选手的Map<memID, CompetitionMember>
					//如果带导入源比赛中重复的晋级选手（memID是一样的），那就找出他最高的一次剩余筹码量的参赛记录，淘汰其它的。
					tempCM2 = uniqueCMMap.get(tempCM1.getMemID());
					if(tempCM2.getChip() < tempCM1.getChip()){
						outCMList.add(tempCM2);
						uniqueCMMap.put(tempCM1.getMemID(), tempCM1);
					}else{
						outCMList.add(tempCM1);
					}
				}else{
					uniqueCMMap.put(tempCM1.getMemID(), tempCM1);
				}
			}
			//6、校验目标赛座位数量是否足够
			usedTables = this.tableService.getUsedTablesByCompID(destCompID);
			if(usedTables == null){
				jsonStr = HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$461.getRspCode(), RspCodeValue.$461.getMsg()));
				return jsonStr;
			}
			if(condition == 0){//随机分桌，人数<=待分座位数
				if(uniqueCMMap.size() > (usedTables.size() * destComp.getTableType())){//待分座位不够
					jsonStr = HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$461.getRspCode(), RspCodeValue.$461.getMsg()));
					return jsonStr;
				}
			}else{//condition==1,固定座位，桌子数量相等,每个比赛里面剩余几个桌子，就需要在目标比赛中开启桌子个数的总和。
				if(uniqueTableSet.size() > usedTables.size()){//目标比赛分的桌子数量不够。
					jsonStr = HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$461.getRspCode(), RspCodeValue.$461.getMsg()));
					return jsonStr;
				}
			}
			//对于淘汰的选手，按筹码量由低到高排列
			Collections.sort(outCMList, new Comparator<CompetitionMember>(){
				@Override
				public int compare(CompetitionMember o1, CompetitionMember o2) {
					if(o1.getChip() < o2.getChip()){
						return -1;
					}else if(o1.getChip() == o2.getChip()){
						return 0;
					}else{
						return 1;
					}
				}
			});
			Iterator<Integer> it = uniqueTableSet.iterator();
			while(it.hasNext()){
				orignalTablesList.add(it.next());
			}
			//4、导入选手
			this.compService.updateImportComps(origCompList, orignalTablesList, uniqueCMMap, outCMList, destComp, usedTables, condition, empUuid);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			jsonStr = HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$462.getRspCode(), RspCodeValue.$462.getMsg()));
			return jsonStr;
		}
		Map<String, Object> map = HIUtil.createJsonResInitMap(RspCodeValue.$1.getRspCode(), RspCodeValue.$1.getMsg());
		jsonStr = HIUtil.toJsonNormalField(map);
		return jsonStr;
	}
	
	/**
	 * 比赛爆桌
	 * @param compID
	 * @param tableNO
	 * @param empUuid
	 * @return
	 */
	public String burstTableFromComp(int compID, int tableNO, String empUuid){
		String jsonStr = "";
		CompBurstTableRes res = null;
		try {
			res = this.compService.updateburstTableFromComp(compID, tableNO, empUuid);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			jsonStr = HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$463.getRspCode(), RspCodeValue.$463.getMsg()));
			return jsonStr;
		}
		switch(res.getResCode()){
			case CompBurstTableRes.RESCODE.RESCODE_1:
				jsonStr = HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$464.getRspCode(), RspCodeValue.$464.getMsg()));
				return jsonStr;
			case CompBurstTableRes.RESCODE.RESCODE_2:
				jsonStr = HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$465.getRspCode(), RspCodeValue.$465.getMsg()));
				return jsonStr;
			case CompBurstTableRes.RESCODE.RESCODE_3:
				jsonStr = HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$466.getRspCode(), RspCodeValue.$466.getMsg()));
				return jsonStr;
		}
		Application.setTriggingMsgForPushing(new SystemBasePushTriggMsg(Application.OPERTYPE.BURST, 0, compID));
		Map<String, Object> map = HIUtil.createJsonResInitMap(RspCodeValue.$1.getRspCode(), RspCodeValue.$1.getMsg());
		map.put("burstTableRes", res);
		jsonStr = HIUtil.toJsonNormalField(map);
		return jsonStr;
	}
	
	/**
	 * 查询比赛位移记录
	 * @param compID
	 * @return
	 */
	public String getCompSeatMovedLogs(int compID){
		String jsonStr = "";
		Map<Integer, List<CompMovedSeatLog>> map = null;
		
		List<CompMovedSeatLog> tempLogList = new ArrayList<CompMovedSeatLog>();
		Map<Long, SeatMovedLog> seatMovegLogMap = new LinkedHashMap<Long, SeatMovedLog>();
		Map<Long, List<CompMovedSeatLog>> createTimeMap = new LinkedHashMap<Long, List<CompMovedSeatLog>>();
		SeatMovedLog seatMovedLog = null;
		List<CompMovedSeatLog> burstList = null;
		List<CompMovedSeatLog> balanceList = null;
		CompMovedSeatLog log = null;
		List<SeatMovedLog> result = new ArrayList<SeatMovedLog>();
		try {
			map = this.compHistLogService.getMoveSeatsHistoryLog(compID);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			jsonStr = HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$467.getRspCode(), RspCodeValue.$467.getMsg()));
			return jsonStr;
		}
		burstList = map.get(CompMovedSeatLog.OPTYPE.BURST);
		balanceList = map.get(CompMovedSeatLog.OPTYPE.BALANCE);
		
		for (int i = 0; i < burstList.size(); i++) {
			log = burstList.get(i);
			if(createTimeMap.containsKey(log.getCreateTime())){
				createTimeMap.get(log.getCreateTime()).add(log);
			}else{
				tempLogList = new ArrayList<CompMovedSeatLog>();
				tempLogList.add(log);
				createTimeMap.put(log.getCreateTime(), tempLogList);
			}
		}
		Set<Entry<Long, List<CompMovedSeatLog>>> set = createTimeMap.entrySet();
		Iterator<Entry<Long, List<CompMovedSeatLog>>> it = set.iterator();
		Entry<Long, List<CompMovedSeatLog>> entry = null;
		long key = 0;
		while(it.hasNext()){
			entry = it.next();
			key = entry.getKey();
			tempLogList = entry.getValue();
			if(seatMovegLogMap.containsKey(key)){
				continue;
			}else{
				seatMovedLog = new SeatMovedLog();
				seatMovedLog.setOpType(tempLogList.get(0).getOpType());
				seatMovedLog.setTableNO(tempLogList.get(0).getOldTableNO());
				seatMovedLog.getLogList().addAll(tempLogList);
				seatMovegLogMap.put(key, seatMovedLog);
			}
		}
		Set<Entry<Long, SeatMovedLog>> set1 = seatMovegLogMap.entrySet();
		Iterator<Entry<Long, SeatMovedLog>> it1 = set1.iterator();
		Entry<Long, SeatMovedLog> entry1 = null;
		while(it1.hasNext()){
			entry1 = it1.next();
			result.add(entry1.getValue());
		}
		Map<String, Object> resmap = HIUtil.createJsonResInitMap(RspCodeValue.$1.getRspCode(), RspCodeValue.$1.getMsg());
		resmap.put("burstLogList", result);
		resmap.put("balanceLogList", balanceList);
		jsonStr = HIUtil.toJsonNormalField(resmap);
		return jsonStr;
	}
	
	
	
	
	
	//---------------------------------screen显示信息查询---------------------------------//
	
	/**
	 * 大屏幕显示比赛晋级的玩家新座位等信息
	 * @param compID
	 * @return
	 */
	public String getScreenCompPlayersInfo(int compID, String devImei){
		String jsonStr = "";
		List<ScreenCompPlayerInfo> list = new ArrayList<ScreenCompPlayerInfo>();
		try {
			list = this.compService.getScreenCompPlayerInfoList(compID);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			jsonStr = HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$479.getRspCode(), RspCodeValue.$479.getMsg()));
			return jsonStr;
		}
		Map<String, Object> resmap = HIUtil.createJsonResInitMap(RspCodeValue.$1.getRspCode(), RspCodeValue.$1.getMsg());
		resmap.put("playerInfoList", list);
		jsonStr = HIUtil.toJsonNormalField(resmap);
		return jsonStr;
	}
	
	/**
	 * 大屏幕入场欢迎信息
	 * @param memID：0、客户端第一次连接，测试值
	 * @param devImei
	 * @return
	 */
	public String welcome(int memID, String devImei){
		String jsonStr = "";
		boolean welcome = false;
		Date start = DateUtils.getFormatDate(DateUtils.formatDate(new Date(), DateUtils.PATTERN2) + " 00:00:00", DateUtils.PATTERN1);
		Date end = DateUtils.getFormatDate(DateUtils.formatDate(new Date(), DateUtils.PATTERN2) + " 23:59:59", DateUtils.PATTERN1);
		try {
			welcome = this.compService.memIsRegedCompsOfToday(memID, start, end);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			jsonStr = HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$481.getRspCode(), RspCodeValue.$481.getMsg()));
			return jsonStr;
		}
		if(welcome){
			Application.setTriggingMsgForPushing(new SystemBasePushTriggMsg(Application.OPERTYPE.ENTRANCECHECK, memID, 0));
		}
		return jsonStr;
	}
	
	
	
	
	//---------------------------------competition server服务管理线程---------------------//
	public boolean initCompServerManageThread_Serv(){
		try {
			List<CompetitionInfo> list = this.compService.getCompInfoListForInit_Serv();
			CompetitionInfo compInfo = null;
			if(list != null){
				for (int i = 0; i < list.size(); i++) {
					compInfo = list.get(i);
					logger.info(compInfo.toString());
					Application.startCompServEntrance(compInfo);
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return false;
		}
		return true;
	}
}
