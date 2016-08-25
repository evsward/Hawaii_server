package com.evsward.server.facade.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.evsward.server.facade.PrizeFacade;
import com.evsward.server.service.CompetitionService;
import com.evsward.server.service.PrizeService;
import com.evsward.server.util.RspCodeValue;
import com.evsward.server.util.SystemCache;
import com.evsward.server.util.HIUtil;
import com.evsward.server.vo.CompetitionInfo;
import com.evsward.server.vo.Prize;
import com.evsward.server.vo.screen.ScreenPrizeInfo;

@Component("prizeFacade")
public class PrizeFacadeImpl implements PrizeFacade {

	private static Logger logger = LoggerFactory.getLogger(PrizeFacadeImpl.class);
	@Resource
	private PrizeService prizeService;
	@Resource
	private CompetitionService compService;
	
	@Override
	public String getPrizeArea(int compID) {
		CompetitionInfo compInfo = null;
		ScreenPrizeInfo screenPrizeInfo = null;
		List<ScreenPrizeInfo> list = new ArrayList<ScreenPrizeInfo>();
		Prize prize = null;
		String jsonStr = "";
		try {
			compInfo = this.compService.getCompetitionInfoByCompID(compID);
			if(compInfo == null){
				jsonStr = HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$405.getRspCode(), RspCodeValue.$405.getMsg()));
				return jsonStr;
			}
			if(compInfo.getAword() != CompetitionInfo.AWORDSTATE.WITHAWORD){
				jsonStr = HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$478.getRspCode(), RspCodeValue.$478.getMsg()));
				return jsonStr;
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		//比赛状态不正确
		if(compInfo.getCompState() == CompetitionInfo.COMPSTATE.STATE_DEL 
				|| compInfo.getCompState() == CompetitionInfo.COMPSTATE.STATE_END 
				|| compInfo.getCompState() == CompetitionInfo.COMPSTATE.STATE_NOREG 
				|| compInfo.getCompState() == CompetitionInfo.COMPSTATE.STATE_REGING_BEGINED 
				|| compInfo.getCompState() == CompetitionInfo.COMPSTATE.STATE_REGING_NOBEGIN){
			jsonStr = HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$434.getRspCode(), RspCodeValue.$434.getMsg()));
			return jsonStr;
		}
		List<Prize> prizeList = HIUtil.getPrizeListByPlayerCount(compInfo.getTotalPlayer() - compInfo.getSubPlayerCount());
		if(prizeList == null || prizeList.isEmpty()){
			jsonStr = HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$488.getRspCode(), RspCodeValue.$488.getMsg()));
			return jsonStr;
		}
		int totalPrizeMoney = HIUtil.calcCompTotalPrizeMoney(compInfo.getLeastPrize(), compInfo.getRegFee(), compInfo.getTotalPlayer(), compInfo.getSubPlayerCount());
		int awordMoneyInt = 0;
		for (int i = 0; i < prizeList.size(); i++) {
			prize = prizeList.get(i);
			awordMoneyInt = (int)HIUtil.round(totalPrizeMoney * prize.getPercent(), 1);
			screenPrizeInfo = new ScreenPrizeInfo(prize.getRankNO(), awordMoneyInt);
			list.add(screenPrizeInfo);
		}
		Map<String, Object> map = HIUtil.createJsonResInitMap(RspCodeValue.$1.getRspCode(), RspCodeValue.$1.getMsg());
		map.put("compID", compID);
		map.put("screenCompPrizeArea", list);
		jsonStr = HIUtil.toJsonNormalField(map);
		return jsonStr;
	}

	/**
	 * 缓存奖励表信息
	 * @param prizeMap
	 * @throws Exception
	 */
	public void cachePrizeListMap()throws Exception{
		this.prizeService.getPrizeListMap(SystemCache.prizeMap);
	}
}
