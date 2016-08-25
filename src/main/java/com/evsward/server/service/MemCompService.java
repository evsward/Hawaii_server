package com.evsward.server.service;

import java.util.List;


import com.dance.core.service.BaseService;
import com.evsward.server.service.impl.MemCompServiceImp.RegCompResult;
import com.evsward.server.vo.CompMemInfo;
import com.evsward.server.vo.CompetitionInfo;
import com.evsward.server.vo.CompetitionMember;
import com.evsward.server.vo.UnAllotedSeat;

public interface MemCompService extends BaseService<CompetitionMember, Integer> {
	
	/**
	 * 查询会员已报名的比赛
	 * @param memID
	 * @param sysType
	 * @return
	 * @throws Exception
	 */
	public List<CompetitionMember> getMemCompetitionInfos(int memID, int sysType)throws Exception;
	
	/**
	 * 获取比赛中是否还存活的选手，mcstate=1，2
	 * @param compID
	 * @return
	 * @throws Exception
	 */
	public boolean existMemOfRunningAndAdvanByCompID(int compID)throws Exception;
	
	/**
	 * 获取比赛存活选手信息,mcstate=1
	 * @param compID
	 * @return
	 * @throws Exception
	 */
	public List<CompMemInfo> getCompMemsSeatInfo(int compID)throws Exception;
	
	/**
	 * 指定座位信息，报名比赛
	 * @param memID
	 * @param compID
	 * @param compInfo
	 * @param specSeat
	 * @return
	 * @throws Exception
	 */
	public RegCompResult insertMemRegCompetitionWithSpecSeat(int memID, int compID, CompetitionInfo compInfo, UnAllotedSeat specSeat)throws Exception;
	
	/**
	 * 随机分座报名比赛
	 * @param memID
	 * @param compID
	 * @param compInfo
	 * @return
	 * @throws Exception
	 */
	public RegCompResult insertMemRegCompetitionRandomAllotSeat(int memID, int compID, CompetitionInfo compInfo)throws Exception;
	
	/**
	 * 不使用分桌系统报名比赛。
	 * @param memID
	 * @param compID
	 * @param compInfo
	 * @return
	 * @throws Exception
	 */
	public RegCompResult insertMemRegCompetitionWithoutAssignSeat(int memID, int compID, CompetitionInfo compInfo)throws Exception;

	/**
	 * 平衡牌桌选手
	 * @param cmID
	 * @param compID
	 * @param memID
	 * @param tableNO
	 * @param seatNO
	 * @param empUuid
	 * @return
	 * @throws Exception
	 */
	public int updateBalanceCompMember(int cmID, int compID, int memID, int tableNO, int seatNO, String empUuid)throws Exception;
	
	
	/**
	 * 更新选手的筹码数量
	 * @param mcID
	 * @param compID
	 * @param memID
	 * @param chip
	 * @return
	 * @throws Exception
	 */
	public int updatePlayerChip(int mcID, int compID, int memID, int chip)throws Exception;
	
	/**
	 * 查询比赛中晋级的选手
	 * @param compID
	 * @return
	 * @throws Exception
	 */
	public List<CompetitionMember> getAllAdvanMemByCompID(int compID)throws Exception;
}
