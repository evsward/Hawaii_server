package com.evsward.server.facade.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.evsward.server.facade.MemRegCompFacade;
import com.evsward.server.service.CompetitionService;
import com.evsward.server.service.MemCompService;
import com.evsward.server.service.MemberManageService;
import com.evsward.server.service.impl.MemCompServiceImp.RegCompResult;
import com.evsward.server.service.impl.MemCompServiceImp.RegCompResultState;
import com.evsward.server.util.Application;
import com.evsward.server.util.DateUtils;
import com.evsward.server.util.RspCodeValue;
import com.evsward.server.util.HIUtil;
import com.evsward.server.vo.CompetitionInfo;
import com.evsward.server.vo.CompetitionMember;
import com.evsward.server.vo.MemberInfo;
import com.evsward.server.vo.UnAllotedSeat;
import com.evsward.server.vo.regcomp.MemReg_CompSearch_Day;
import com.evsward.server.vo.regcomp.MemReg_CompSearch_compInfo;
import com.evsward.server.vo.regcomp.RunCompSeatInfo;
import com.evsward.server.vo.tcpmsg.triggmsg.SystemBasePushTriggMsg;

@Component("memRegCompFacade")
public class MemRegCompFacadeImpl implements MemRegCompFacade {

	private static Logger logger = LoggerFactory.getLogger(MemRegCompFacadeImpl.class);
	
	@Resource
	private MemCompService memCompService;
	@Resource
	private CompetitionService compService;
	@Resource
	private MemberManageService memService;
	
	@Override
	public String searchRegedCompAndAllComps(int memID, int sysType) {
		String jsonStr = "";
		List<CompetitionMember> regedCompList = null;//报过名的比赛
		List<CompetitionInfo> compList = null;//
		Map<String, MemReg_CompSearch_Day> weekDayMap = null;//
		List<MemReg_CompSearch_Day> dayList = new ArrayList<MemReg_CompSearch_Day>();
		List<MemReg_CompSearch_compInfo> infoList = null;
		MemReg_CompSearch_Day memReg_CompSearch_Day = null;//
		MemReg_CompSearch_compInfo memReg_CompSearch_Info = null;//
		MemberInfo memInfo = null;
		String weekDayKey = null;
		CompetitionInfo compInfo = null;
		//查询会员信息
		try {
			memInfo = this.memService.findMemByID(memID);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$225.getRspCode(), RspCodeValue.$225.getMsg()));
		}
		if(memInfo == null){
			return HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$226.getRspCode(), RspCodeValue.$226.getMsg()));
		}
		//查询所有比赛，包括已删除的；查询会员报名过的比赛，包括已删除的
		try {
			compList = this.compService.getAllCompetitionList(sysType);
			if(compList != null && compList.size() > 0){
				regedCompList = this.memCompService.getMemCompetitionInfos(memID, sysType);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$224.getRspCode(), RspCodeValue.$224.getMsg()));
		}
		if(compList != null && compList.size() > 0){
			weekDayMap = new LinkedHashMap<String, MemReg_CompSearch_Day>();
			for (int i = 0; i < compList.size(); i++) {
				//把所有比赛按照“2015-05-11 星期一”格式的key，放到Map里
				compInfo = compList.get(i);
				weekDayKey = DateUtils.formatDate(compInfo.getStartTime(), DateUtils.PATTERN8);
				if(!weekDayMap.containsKey(weekDayKey)){//没有这个日期key
					memReg_CompSearch_Day = new MemReg_CompSearch_Day(weekDayKey);
					weekDayMap.put(weekDayKey, memReg_CompSearch_Day);
				}
//				else{//已经存在日期key
//					memReg_CompSearch_Day = weekDayMap.get(weekDayKey);
//				}
			}
			infoList = this.setMemReg_CompSearch_InfoList(regedCompList);//排重过后的，报过名的比赛
			Date startTime = null;
			boolean regedflag = false;
			for (int j = 0; j < compList.size(); j++) {
				compInfo = compList.get(j);
				regedflag = false;
				for (int i = 0; i < infoList.size(); i++) {
					memReg_CompSearch_Info = infoList.get(i);
					if(compInfo.getCompID() != memReg_CompSearch_Info.getCompID()){//选手没有报过改场比赛，组装一个MemReg_CompSearch_Info对象，放到MemReg_CompSearch_Day的list里
						continue;
					}
					regedflag = true;
					weekDayKey = DateUtils.formatDate(compInfo.getStartTime(), DateUtils.PATTERN8);
					memReg_CompSearch_Day = weekDayMap.get(weekDayKey);
					memReg_CompSearch_Day.getList().add(memReg_CompSearch_Info);
				}
				if(!regedflag){
					memReg_CompSearch_Info = new MemReg_CompSearch_compInfo();
					memReg_CompSearch_Info.setCompID(compInfo.getCompID());
					memReg_CompSearch_Info.setCompName(compInfo.getCompName());
					memReg_CompSearch_Info.setCompState(compInfo.getCompState());
					memReg_CompSearch_Info.setDateNoTime(DateUtils.formatDate(compInfo.getStartTime(), DateUtils.PATTERN2));
					memReg_CompSearch_Info.setTime(DateUtils.formatDate(compInfo.getStartTime(), DateUtils.PATTERN7));
					startTime = DateUtils.getFormatDate(memReg_CompSearch_Info.getDateNoTime() + " " + memReg_CompSearch_Info.getTime(), DateUtils.PATTERN1);
					weekDayKey = DateUtils.formatDate(startTime, DateUtils.PATTERN8);
					memReg_CompSearch_Day = weekDayMap.get(weekDayKey);
					memReg_CompSearch_Day.getList().add(memReg_CompSearch_Info);
				}
			}
		}
		if(weekDayMap == null || weekDayMap.size() <= 0){//还没有建立任何比赛
			return HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$415.getRspCode(), RspCodeValue.$415.getMsg()));
		}
		this.setMemRegButtonState(compList, weekDayMap);
		Set<Entry<String, MemReg_CompSearch_Day>> set = weekDayMap.entrySet();
		Iterator<Entry<String, MemReg_CompSearch_Day>> it = set.iterator();
		Entry<String, MemReg_CompSearch_Day> entry = null;
		while(it.hasNext()){
			entry = it.next();
			memReg_CompSearch_Day = entry.getValue();
			dayList.add(memReg_CompSearch_Day);
		}
		Map<String, Object> map = HIUtil.createJsonResInitMap(RspCodeValue.$1.getRspCode(), RspCodeValue.$1.getMsg());
		map.put("memberInfo", memInfo);
		map.put("compInfos", dayList);
		jsonStr = HIUtil.toJsonNormalField(map);
		return jsonStr;
	}
	
	protected List<MemReg_CompSearch_compInfo> setMemReg_CompSearch_InfoList(List<CompetitionMember> regedCompList){
		MemReg_CompSearch_compInfo memReg_CompSearch_Info = null;//
		RunCompSeatInfo seatInfo = null;
		List<MemReg_CompSearch_compInfo> list = new ArrayList<MemReg_CompSearch_compInfo>();
		//比赛ID排重
		Set<Integer> compIDSet = new LinkedHashSet<Integer>();
		for (int i = 0; i < regedCompList.size(); i++) {
			compIDSet.add(regedCompList.get(i).getCompID());
		}
		//按照排重后的循环和regedCompList做匹配
		Iterator<Integer> compIDIt = compIDSet.iterator();
		int regedCompListSize = regedCompList.size();
		while(compIDIt.hasNext()){
			Integer compID = compIDIt.next();
			memReg_CompSearch_Info = new MemReg_CompSearch_compInfo();
			memReg_CompSearch_Info.setCompID(compID);
			for (int i = 0; i < regedCompListSize; i++) {
				CompetitionMember cm = regedCompList.get(i);
				if(cm.getCompID() == compID){
					memReg_CompSearch_Info.setCompName(cm.getCompName());
					memReg_CompSearch_Info.setCompState(cm.getCompState());
					memReg_CompSearch_Info.setDateNoTime(DateUtils.formatDate(cm.getCompStartTime(), DateUtils.PATTERN2));
					memReg_CompSearch_Info.setTime(DateUtils.formatDate(cm.getCompStartTime(), DateUtils.PATTERN7));
					//如果设置了晋级或者参赛中，就不再改变状态了。
					if(memReg_CompSearch_Info.getMcState() != CompetitionMember.MemCompState.REGED && memReg_CompSearch_Info.getMcState() != CompetitionMember.MemCompState.ADVAN){
						memReg_CompSearch_Info.setMcState(cm.getMcState());
					}
					memReg_CompSearch_Info.setRegCount(memReg_CompSearch_Info.getRegCount() + 1);
					if(cm.getMcState() == CompetitionMember.MemCompState.REGED || cm.getMcState() == CompetitionMember.MemCompState.ADVAN){
						seatInfo = new RunCompSeatInfo(cm.getTableNO(), cm.getSeatNO());
						memReg_CompSearch_Info.getRunCompSeatInfoList().add(seatInfo);
					}
				}
			}
			list.add(memReg_CompSearch_Info);
		}
		return list;
	}

	/**
	 * 重设会员能否报名比赛的按钮状态
	 * @param memCompList
	 * @param weekDayMap
	 */
	protected void setMemRegButtonState(List<CompetitionInfo> compList, Map<String, MemReg_CompSearch_Day> weekDayMap){
		List<MemReg_CompSearch_compInfo> infoList = null;
		MemReg_CompSearch_Day memReg_CompSearch_Day = null;//
		MemReg_CompSearch_compInfo memReg_CompSearch_Info = null;//
		CompetitionInfo temp = null;
		Map<Integer, CompetitionInfo> compMap = new HashMap<Integer, CompetitionInfo>();
		for (int i = 0; i < compList.size(); i++) {
			compMap.put(compList.get(i).getCompID(), compList.get(i));
		}
		Set<Entry<String, MemReg_CompSearch_Day>> set = weekDayMap.entrySet();
		Iterator<Entry<String, MemReg_CompSearch_Day>> it = set.iterator();
		Entry<String, MemReg_CompSearch_Day> entry = null;
		while(it.hasNext()){
			entry = it.next();
			memReg_CompSearch_Day = entry.getValue();
			infoList = memReg_CompSearch_Day.getList();
			if(!infoList.isEmpty()){
				for (int i = 0; i < infoList.size(); i++) {
					memReg_CompSearch_Info = infoList.get(i);
					if(memReg_CompSearch_Info.getMcState() == CompetitionMember.MemCompState.OUT){
						if(memReg_CompSearch_Info.getCompState() == CompetitionInfo.COMPSTATE.STATE_REGING_BEGINED || 
								memReg_CompSearch_Info.getCompState() == CompetitionInfo.COMPSTATE.STATE_REGING_NOBEGIN){
							temp = compMap.get(memReg_CompSearch_Info.getCompID());
							if(temp.getReEntry() == CompetitionInfo.ENTRYSTATE.ENABLEENTRY){//可重进
								memReg_CompSearch_Info.setRegBut(MemReg_CompSearch_compInfo.REGBUTSTATE.REGBUTENABLE);
							}
						}
					}else if(memReg_CompSearch_Info.getMcState() == CompetitionMember.MemCompState.NOREG){
						if(memReg_CompSearch_Info.getCompState() == CompetitionInfo.COMPSTATE.STATE_REGING_BEGINED || 
								memReg_CompSearch_Info.getCompState() == CompetitionInfo.COMPSTATE.STATE_REGING_NOBEGIN){
							memReg_CompSearch_Info.setRegBut(MemReg_CompSearch_compInfo.REGBUTSTATE.REGBUTENABLE);
						}
					}
				}
			}
		}
	}

	@Override
	public String memRegComp(int memID, int compID) {
		return this.memRegComp(memID, compID, null);
	}

	@Override
	public String vipMemRegComp(int memID, int compID, int tableNO, int seatNO) {
		UnAllotedSeat specSeat = new UnAllotedSeat(tableNO, seatNO);
		return this.memRegComp(memID, compID, specSeat);
	}
	
	/**
	 * 报名比赛（1、检查比赛是否允许报名；2、判断比赛是否使用分桌系统--分桌；3、注册报名）
	 * @param memID
	 * @param compID
	 * @param specSeat
	 * @param useSeat true：使用分桌
	 * @return
	 */
	private String memRegComp(int memID, int compID, UnAllotedSeat specSeat){
		String jsonStr = "";
		CompetitionInfo compInfo = null;
		RegCompResult result = null;
		//查询比赛信息
		try {
			compInfo = this.compService.getCompetitionInfoByCompID(compID);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$404.getRspCode(), RspCodeValue.$404.getMsg()));
		}
		if(compInfo.getCompState() == CompetitionInfo.COMPSTATE.STATE_DEL){//已删除
			return HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$236.getRspCode(), RspCodeValue.$236.getMsg()));
		}else if(compInfo.getCompState() == CompetitionInfo.COMPSTATE.STATE_NOREG){//未开放
			return HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$235.getRspCode(), RspCodeValue.$235.getMsg()));
		}else if(compInfo.getCompState() == CompetitionInfo.COMPSTATE.STATE_REGEND_NOBEGIN){//停止报名，比赛未开始
			return HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$238.getRspCode(), RspCodeValue.$238.getMsg()));
		}else if(compInfo.getCompState() == CompetitionInfo.COMPSTATE.STATE_RUNNING){//停止报名，比赛进行中
			return HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$238.getRspCode(), RspCodeValue.$238.getMsg()));
		}else if(compInfo.getCompState() == CompetitionInfo.COMPSTATE.STATE_END){//比赛已结束
			return HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$237.getRspCode(), RspCodeValue.$237.getMsg()));
		}
		//
		try {
			if(compInfo.getAssignSeat() == CompetitionInfo.ASSIGNSEATSTATE.USE){//使用分桌系统
				if(specSeat != null){//指定座位
					result = this.memCompService.insertMemRegCompetitionWithSpecSeat(memID, compID, compInfo, specSeat);
				}else{
					result = this.memCompService.insertMemRegCompetitionRandomAllotSeat(memID, compID, compInfo);
				}
			}else{//不使用分桌系统
				result = this.memCompService.insertMemRegCompetitionWithoutAssignSeat(memID, compID, compInfo);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$241.getRspCode(), RspCodeValue.$241.getMsg()));
		}
		if(result == null){
			return HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$241.getRspCode(), RspCodeValue.$241.getMsg()));
		}else if(result.getResult() == RegCompResultState.RESULT_2){
			return HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$240.getRspCode(), RspCodeValue.$240.getMsg()));
		}else if(result.getResult() == RegCompResultState.RESULT_1){
			return HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$239.getRspCode(), RspCodeValue.$239.getMsg()));
		}else if(result.getResult() == RegCompResultState.RESULT_3){
			return HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$242.getRspCode(), RspCodeValue.$242.getMsg()));
		}
		Application.setTriggingMsgForPushing(new SystemBasePushTriggMsg(Application.OPERTYPE.REG, 0, compID));
		Map<String, Object> regCompInfo = new HashMap<String, Object>();
		regCompInfo.put("memID", memID);
		regCompInfo.put("compID", compID);
		regCompInfo.put("compName", compInfo.getCompName());
		regCompInfo.put("startTime", DateUtils.formatDate(compInfo.getStartTime(), DateUtils.PATTERN1));
		regCompInfo.put("tableNO", result.getTableNO());
		regCompInfo.put("seatNO", result.getSeatNO());
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("regCompInfo", regCompInfo);
		jsonStr = HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$1.getRspCode(), RspCodeValue.$1.getMsg(), map));
		return jsonStr;
	}
}
