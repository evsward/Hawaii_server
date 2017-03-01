package com.evsward.server.facade;

public interface CompetitionManageFacade {

	/**
	 * 新建比赛信息
	 * @param compName
	 * @param leastPrize
	 * @param regFee
	 * @param serviceFee
	 * @param beginChip
	 * @param unit
	 * @param roundTempID
	 * @param stopRegRank
	 * @param reEntry
	 * @param tableType
	 * @param aword
	 * @param assignSeat
	 * @param beginTime
	 * @param empUuid
	 * @return
	 */
	public String createCompetition(String compName, int leastPrize, int regFee, int serviceFee, 
			int beginChip, int unit, int roundTempID, int stopRegRank, int reEntry, int compType, int tableType, 
			int sysType, int aword, int assignSeat, long beginTime, String empUuid);
	
	/**
	 * 编辑比赛信息,比赛开始报名后无法编辑
	 * @param compID
	 * @param compName
	 * @param leastPrize
	 * @param regFee
	 * @param serviceFee
	 * @param beginChip
	 * @param unit
	 * @param roundTempID
	 * @param stopRegRank
	 * @param reEntry
	 * @param tableType
	 * @param aword
	 * @param assignSeat
	 * @param beginTime
	 * @param empUuid
	 * @return
	 */
	public String editCompetition(int compID, String compName, int leastPrize, int regFee, int serviceFee, 
			int beginChip, int unit, int roundTempID, int stopRegRank, int reEntry, int compType, int tableType, 
			int aword, int assignSeat, long beginTime, String empUuid);
	
	/**
	 * 删除比赛，删除比赛应该注意并发因素，删除操作属于极少发生的概率事件。现在没有进行最高级别的锁表操作。因为要锁很多表，并且效果也不理想。
	 * 正确的删除比赛操作，应该是，确定要删除比赛，需要先停止报名
	 * @param compID
	 * @return
	 */
	public String delCompetition(int compID);
	
	/**
	 * 手动暂停比赛
	 * @param compID
	 * @return
	 */
	public String manualPauseCompetition(int compID);
	
	/**
	 * 结束比赛(1、校验比赛状态；2、设置所有未淘汰的选手为晋级状态；3、校验比赛是否带有奖励表；4、如果带奖励表，放进用户到奖励信息表里；5、清除所有参赛选手的座位；)
	 * @param compID
	 * @return
	 */
	public String endCompetition(int compID);
	
	/**
	 * 查询所有比赛（包括已删除的比赛）
	 * @param sysType
	 * @return
	 */
	public String getAllCompetitions(int sysType);
	
	/**
	 * 查询所有比赛，带有盲注信息（不包括已删除的比赛）
	 * @param sysType
	 * @return
	 */
	public String getAllComptitionsNoDel(int sysType);
	
	/**
	 * 比赛管理-座位，查询比赛的存活选手的座位信息
	 * @param compID
	 * @return
	 */
	public String getCompMemsSeatInfo(int compID);
	
	/**
	 * 比赛管理--玩家，获取存活的所有玩家，包括已晋级
	 * @param compID
	 * @return
	 */
	public String getLivedPlayersInfoByComp(int compID);
	
	/**
	 * 比赛管理--筹码，获取存活的所有玩家筹码信息，包括已晋级
	 * @param compID
	 * @return
	 */
	public String getLivedPlayersChipByComp(int compID);
	
	/**
	 * 比赛管理--筹码，获取指定存活的玩家筹码信息
	 * @param compID
	 * @param nfcID
	 * @param cardNO
	 * @return
	 */
	public String getSpecPlayerChipByComp(int compID, long nfcID, String cardNO);
	
	/**
	 * 查询比赛的进程信息
	 * @param compID
	 * @return
	 */
	public String getCompRunningInfo(int compID);
	
	/**
	 * 退一级盲注
	 * @param compID
	 * @param curRank
	 * @return
	 */
	public String goBackRound(int compID, int curRank);
	
	/**
	 * 进一级盲注
	 * @param compID
	 * @param curRank
	 * @return
	 */
	public String goForwardRound(int compID, int curRank);
	
	/**
	 * 比赛管理--奖池，查询奖励信息
	 * @param compID
	 * @return
	 */
	public String getCompMemPrizeInfoList(int compID);
	
	/**
	 * 查询比赛运行奖励表中的一条记录
	 * @param ranking
	 * @param compID
	 * @param memID
	 * @return
	 */
	public String getCompMemPrizeInfo(int ranking, int compID, int memID);
	
	/**
	 * 查询待打印的奖励小条信息
	 * @param ranking
	 * @param memID
	 * @param compID
	 * @return
	 */
	public String getPlayerAwordInfoForPrint(int ranking, int memID, int compID);
	
	/**
	 * 比赛进阶管理--比赛导入，比赛列表查询
	 * @param sysType
	 * @return
	 */
	public String getCompListForImportComp(int sysType);
	
	/**
	 * 比赛开启牌桌，如果compstate=0，同时更新compstate=1
	 * @param compID
	 * @param tableNOArr
	 * @param empUuid
	 * @return
	 * @throws Exception
	 */
	public String openCompTables(int compID, int[] tableNOArr, String empUuid);
	
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
	public String balanceCompMember(int cmID, int compID, int memID, int tableNO, int seatNO, String empUuid);
	
	/**
	 * 释放比赛一张牌桌
	 * @param compID
	 * @param tableNO
	 * @return
	 */
	public String releaseCompTable(int compID, int tableNO);
	
	/**
	 * 比赛淘汰选手，淘汰后，把座位信息放回到待分座位表。
	 * @param cmID
	 * @param memID
	 * @param compID
	 * @return
	 */
	public String outMemFromComp(int tableNO, int seatNO, int memID, int compID);
	
	/**
	 * 编辑扣减选手数量，累计减
	 * @param compID
	 * @param subNum
	 * @return
	 */
	public String editSubPlayer(int compID, int subNum);
	
	/**
	 * 比赛爆桌
	 * @param compID
	 * @param tableNO
	 * @param empUuid
	 * @return
	 */
	public String burstTableFromComp(int compID, int tableNO, String empUuid);
	
	/**
	 * 更新选手的筹码数量
	 * @param mcID
	 * @param compID
	 * @param memID
	 * @param chip
	 * @return
	 */
	public String updatePlayerChip(int mcID, int compID, int memID, int chip);
	
	/**
	 * 修改当前盲注计时时间
	 * @param compID
	 * @param curRank
	 * @param jumpTime
	 * @return
	 */
	public String editRoundJumpTime(int compID, int curRank, int jumpTime);
	
	/**
	 * 编辑奖池中选手的奖金
	 * @param runningPrizeID
	 * @param amountInt
	 * @param memID
	 * @param compID
	 * @return
	 */
	public String editCompMemPrizeInfo(int runningPrizeID, int memID, int compID, int amountInt);
	
	/**
	 * 导入比赛
	 * @param origCompID
	 * @param destCompID
	 * @param condition
	 * @param empUuid
	 * @return
	 */
	public String importComps(int[] origCompID, int destCompID, int condition, String empUuid);
	
	/**
	 * 查询比赛位移记录
	 * @param compID
	 * @return
	 */
	public String getCompSeatMovedLogs(int compID);
	
	
	
	
	
//	//---------------------------------screen显示信息查询---------------------------------//
//	/**
//	 * 查询大屏幕比赛计时信息
//	 * @param compID
//	 * @param devImei
//	 * @return
//	 */
//	public String getScreenTimerInfo(int compID, String devImei);
	
	/**
	 * 大屏幕显示比赛晋级的玩家新座位等信息
	 * @param compID
	 * @param devImei
	 * @return
	 */
	public String getScreenCompPlayersInfo(int compID, String devImei);
//	
//	/**
//	 * 大屏幕显示当天要开赛，状态：未结束、未删除的比赛列表信息
//	 * @param devImei
//	 * @return
//	 */
//	public String getScreenCompInfoList(String devImei);
	
	/**
	 * 大屏幕入场欢迎信息
	 * @param memID：0、客户端第一次连接，测试值
	 * @param devImei
	 * @return
	 */
	public String welcome(int memID, String devImei);
	
	
	
	
	
	
	//---------------------------------competition server服务管理线程---------------------//
	/**
	 * 
	 * @return
	 */
	public boolean initCompServerManageThread_Serv();
}
