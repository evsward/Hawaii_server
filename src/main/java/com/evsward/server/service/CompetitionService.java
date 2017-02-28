package com.evsward.server.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.dance.core.service.BaseService;
import com.evsward.server.vo.CardTable;
import com.evsward.server.vo.CompMemInfo;
import com.evsward.server.vo.CompetitionInfo;
import com.evsward.server.vo.CompetitionMember;
import com.evsward.server.vo.compmanage.CompBurstTableRes;
import com.evsward.server.vo.compmanage.CompManPlayerInfo;
import com.evsward.server.vo.compmanage.CompManPrizeInfo;
import com.evsward.server.vo.compmanage.EditSubPlayerVO;
import com.evsward.server.vo.compmanage.PrizePrintInfo;
import com.evsward.server.vo.screen.ScreenCompInfo;
import com.evsward.server.vo.screen.ScreenCompPlayerInfo;

public interface CompetitionService extends
		BaseService<CompetitionInfo, Integer> {

	/**
	 * 新建比赛信息
	 * @param compInfo
	 * @throws Exception
	 */
	public void createCompetition(CompetitionInfo compInfo)throws Exception;
	
	/**
	 * 编辑比赛信息
	 * @param compInfo
	 * @throws Exception
	 */
	public void editCompetionInfo(CompetitionInfo compInfo)throws Exception;
	
	/**
	 * 判断比赛名称是否已经存在
	 * @param compName
	 * @return
	 * @throws Exception
	 */
	public boolean compNameExist(String compName)throws Exception;
	
	/**
	 * 根据比赛ID查询比赛信息
	 * @param compID
	 * @return
	 * @throws Exception
	 */
	public CompetitionInfo getCompInfoByCompID(int compID)throws Exception;
	
	/**
	 * 查询所有比赛(不包括已删除比赛)，带有当前运行盲注信息
	 * @param sysType
	 * @return
	 * @throws Exception
	 */
	public List<CompetitionInfo> getAllCompWithRunningRound(int sysType)throws Exception;
	
	/**
	 * 查询所有比赛(包括已删除比赛)
	 * @param sysType
	 * @return
	 * @throws Exception
	 */
	public List<CompetitionInfo> getAllCompetitionList(int sysType)throws Exception;
	
	/**
	 * 查询当天未结束比赛
	 * @param sysType
	 * @return
	 * @throws Exception
	 */
	public List<CompetitionInfo> getOndaynoEndComps(int sysType)throws Exception;
	
	/**
	 * 根据比赛ID查询比赛信息
	 * @param compID
	 * @return
	 * @throws Exception
	 */
	public CompetitionInfo getCompetitionInfoByCompID(int compID)throws Exception;
	
	/**
	 * 查询比赛已报名选手数量
	 * @param compID
	 * @return
	 * @throws Exception
	 */
	public int getCompAllRegedPlayerCount(int compID)throws Exception;
	
	/**
	 * 查询比赛存活选手数量
	 * @param compID
	 * @return
	 * @throws Exception
	 */
	public int getCompAllLivedPlayerCount(int compID)throws Exception;
	
	/**
	 * 比赛管理--玩家，查询存活的玩家信息（包括已晋级的）
	 * @param compID
	 * @return
	 * @throws Exception
	 */
	public CompManPlayerInfo getLivedPlayersByComp(int compID)throws Exception;
	
	/**
	 * 比赛管理--筹码，查询存活的玩家筹码信息（包括已晋级的）
	 * @param compID
	 * @return
	 * @throws Exception
	 */
	public List<CompMemInfo> getLivedPlayerChipByComp(int compID)throws Exception;
	
	/**
	 * 比赛管理--筹码，通过nfc卡查询指定存活玩家筹码信息
	 * @param compID
	 * @param nfcID
	 * @return
	 * @throws Exception
	 */
	public  List<CompMemInfo> getSpecPlayerChipOfCompByNfcID(int compID, long nfcID)throws Exception;
	
	/**
	 * 比赛管理--筹码，通过cardNO查询指定存活玩家筹码信息
	 * @param compID
	 * @param nfcID
	 * @return
	 * @throws Exception
	 */
	public List<CompMemInfo> getSpecPlayerChipOfCompByCardNO(int compID, String cardNO)throws Exception;
	
	/**
	 * 奖池，查询奖励信息，带有（总奖励，总报名人数，最大奖励人数）统计信息
	 * @param compID
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getCompMemPrizeInfoList(int compID)throws Exception;
	
	/**
	 * 查询比赛运行奖励表中的一条记录
	 * @param ranking
	 * @param compID
	 * @param memID
	 * @return
	 * @throws Exception
	 */
	public CompManPrizeInfo getCompMemPrizeInfo(int ranking, int compID, int memID)throws Exception;
	
	/**
	 * 获取奖励小条的待打印信息
	 * @param ranking
	 * @param compID
	 * @return
	 * @throws Exception
	 */
	public PrizePrintInfo getPrizePrintInfo(int ranking, int compID)throws Exception;
	
	/**
	 * 
	 * @param sysType
	 * @return
	 * @throws Exception
	 */
	public List<CompetitionInfo> getOrigCompListForImport(int sysType)throws Exception;
	
	/**
	 * 
	 * @param sysType
	 * @return
	 * @throws Exception
	 */
	public List<CompetitionInfo> getDestListForImport(int sysType)throws Exception;
	
	/**
	 * 根据比赛ID列表，查询比赛集合
	 * @param compID
	 * @return
	 * @throws Exception
	 */
	public List<CompetitionInfo> getCompListByIDs(int...compID)throws Exception;
	
	/**
	 * 比赛晋级导入
	 * @param origCompList		源比赛集合
	 * @param uniqueTableList	源比赛牌桌集合（这个参数是固定分座使用的）
	 * @param uniqueCMMap		排重后的导入选手
	 * @param outCMList			淘汰的选手
	 * @param destComp			目标赛
	 * @param usedTables		目标赛开启的牌桌
	 * @param condition			分座条件：1、固定座位；0、随机座位
	 * @param empUuid
	 * @return
	 * @throws Exception
	 */
	public int updateImportComps(List<CompetitionInfo> origCompList, List<Integer> uniqueTableList, Map<Integer, CompetitionMember> uniqueCMMap, List<CompetitionMember> outCMList, CompetitionInfo destComp, List<CardTable> usedTables, int condition, String empUuid)throws Exception;

	/**
	 * 根据比赛ID逻辑删除比赛
	 * @param compID
	 * @return 1、删除成功；
	 * @throws Exception
	 */
	public int updateDelCompetitionByID(int compID)throws Exception;
	
	/**
	 * 暂停或开始比赛
	 * @param compID
	 * @return
	 * @throws Exception
	 */
	public int updatePauseCompetitionByID(int compID)throws Exception;
	
	/**
	 * 结束比赛
	 * @param compID
	 * @return
	 * @throws Exception
	 */
	public int updateEndCompetitionByID(int compID)throws Exception;
	
	/**
	 * 比赛开启牌桌，根据比赛是否使用分桌系统自行判断是否开启牌桌
	 * @param compID
	 * @param tableNOArr
	 * @throws Exception
	 */
	public void updateOpenRegistration(int compID, int[] tableNOArr)throws Exception;
	
	/**
	 * 释放比赛单张牌桌
	 * @param compID
	 * @param tableNO
	 * @return
	 * @throws Exception
	 */
	public int updateReleaseCompTable(int compID, int tableNO)throws Exception;
	
	/**
	 * 淘汰比赛中的参赛选手
	 * @param cmID
	 * @param memID
	 * @param compID
	 * @return
	 * @throws Exception
	 */
	public int updateOutMemFromComp(int cmID, int memID, int compID)throws Exception;
	
	/**
	 * 编辑扣减选手数量，累计减
	 * @param compID
	 * @param subNum
	 * @return
	 */
	public EditSubPlayerVO updateSubPlayer(int compID, int subNum)throws Exception;
	
	/**
	 * 退一级盲注
	 * @param compID
	 * @return
	 * @throws Exception
	 */
	public int updateGoBackRound(int compID)throws Exception;
	
	/**
	 * 进一级盲注
	 * @param compID
	 * @return
	 * @throws Exception
	 */
	public int updateGoForwardRound(int compID)throws Exception;
	
	/** 
	 * 修改当前盲注计时时间
	 * @param compID
	 * @param curRank
	 * @param jumpTime	单位是秒，跳跃的时间间隔。最大一定不大于盲注的步进
	 * @return
	 */
	public int updateRoundJumpTime(int compID, int curRank, int jumpTime)throws Exception;

	/**
	 * 编辑奖池中选手的奖金
	 * @param ranking
	 * @param compID
	 * @param amountInt
	 * @return
	 * @throws Exception
	 */
	public int updateCompManPrizeInfoAmount(int ranking, int compID, int amountInt)throws Exception;
	
	/**
	 * 比赛爆桌
	 * @param compID
	 * @param tableNO
	 * @param empUuid
	 * @return
	 * @throws Exception
	 */
	public CompBurstTableRes updateburstTableFromComp(int compID, int tableNO, String empUuid)throws Exception;
	
	
	
	
	
	
	
	//---------------------------------screen显示信息查询---------------------------------//
	/**
	 * 判断选手是否报名的当天的比赛
	 * @param memID
	 * @param start
	 * @param end
	 * @return
	 * @throws Exception
	 */
	public boolean memIsRegedCompsOfToday(int memID, Date start, Date end)throws Exception;
	
	
	
	
	
	
	
	
	
	//---------------------------------screen 显示信息查询---------------------------------//
	/**
	 * 大屏幕显示选手信息
	 * @param compID
	 * @return
	 * @throws Exception
	 */
	public List<ScreenCompPlayerInfo> getScreenCompPlayerInfoList(int compID)throws Exception;
	
	/**
	 * 查询大屏幕比赛列表信息
	 * @param start
	 * @param end
	 * @param sysType
	 * @return
	 * @throws Exception
	 */
	public List<ScreenCompInfo> getScreenCompList(Date start, Date end, int sysType)throws Exception;
	
	
	
	
	
	
	
	
	
	//---------------------------------competition server服务管理线程---------------------------------//
	/**
	 * 后台比赛维护线程--比赛时间到，更新比赛状态为compState=2或者compState=4
	 * @param compInfo
	 * @param newCompState，要更新的比赛新状态
	 * @return
	 * @throws Exception
	 */
	public int updateStartCompetition_Serv(CompetitionInfo compInfo, int newCompState)throws Exception;
	
	/**
	 * 后台比赛维护线程--维护比赛盲注进度
	 * @param compInfo
	 * @p
	 * @return
	 * @throws Exception
	 */
	public int updateCompRoundRun_Serv(CompetitionInfo compInfo)throws Exception;
	
	/**
	 * 查询所有未结束，未删除的比赛，后初始化比赛后台管理线程服务。
	 * @return
	 * @throws Exception
	 */
	public List<CompetitionInfo> getCompInfoListForInit_Serv()throws Exception;
}
