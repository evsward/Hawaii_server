package com.evsward.server.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.dance.core.orm.BaseDao;
import com.evsward.server.vo.CompMemInfo;
import com.evsward.server.vo.CompetitionMember;

public interface MemCompDao extends BaseDao<CompetitionMember, Integer> {

	
	/**
	 * 获取比赛所有晋级选手
	 * @param compID
	 * @return
	 * @throws Exception
	 */
	public List<CompetitionMember> getAdvanCompMemByCompID(int compID)throws Exception;
	
	/**
	 * 查询会员参赛信息,mcstate=-1,0,1,2
	 * @param memID
	 * @param sysType;
	 * @return
	 * @throws Exception
	 */
	public List<CompetitionMember> getMemCompetionInfos(int memID, int sysType)throws Exception;
	
	/**
	 * 获取比赛中还存货的选手，mcstate=1，2
	 * @param compID
	 * @return
	 * @throws Exception
	 */
	public List<CompetitionMember> getMemOfRunningAndAdvanCompetionInfos(int compID)throws Exception;
	
	/**
	 * 获取比赛存活选手座位信息
	 * @param compID
	 * @return
	 * @throws Exception
	 */
	public List<CompMemInfo> getCompMemsSeatInfo(int compID)throws Exception;
	
	/**
	 * 获取比赛存活选手信息（包括已晋级）
	 * @param compID
	 * @param chipOrder	筹码的排序，1、按chip的升序；0、按chip的降序
	 * @return
	 * @throws Exception
	 */
	public List<CompMemInfo> getCompPlayerInfo(int compID, int chipOrder)throws Exception;
	
	/**
	 * 获取比赛存活选手筹码信息（包括已晋级）
	 * @param compID
	 * @return
	 * @throws Exception
	 */
	public List<CompMemInfo> getCompPlayerChip(int compID)throws Exception;
	
	/**
	 * 查询该牌桌上是否有存活的选手
	 * @param tableNO
	 * @return
	 * @throws Exception
	 */
	public List<CompetitionMember> getLivedMemByTableNO(int tableNO)throws Exception;
	
	/**
	 * 根据主键查询CompetitionMember对象
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public CompetitionMember getCompMemByID(int id)throws Exception;
	
	/**
	 * 根据桌号座位号查询CompetitionMember对象
	 * @param tableNO
	 * @param seatNO
	 * @return
	 * @throws Exception
	 */
	public CompetitionMember getCompMemBySeat(int tableNO, int seatNO)throws Exception;
//	/**
//	 * 查询比赛中已报名的选手数量
//	 * @param compID
//	 * @return
//	 * @throws Exception
//	 */
//	public long findCompRegTotalPlayerCount(int compID)throws Exception;
	
	/**
	 * 查询比赛中存活的选手数量，mcstate=1
	 * @param compID
	 * @return
	 * @throws Exception
	 */
	public long findCompLivedTotalPlayerCount(int compID)throws Exception;
	
	/**
	 * 查询会员在该比赛中是否有参赛中和晋级的状态数据
	 * @param memID
	 * @param compID
	 * @param sysType
	 * @return
	 * @throws Exception
	 */
	public List<CompetitionMember> findMemCompetitionInfos(int memID, int compID, int sysType)throws Exception;
	
	/**
	 * 比赛管理--筹码，通过nfc卡查询指定存活玩家筹码信息
	 * @param compID
	 * @param nfcID
	 * @return
	 */
	public List<CompMemInfo> findSpecPlayerChipOfCompByNfcID(int compID, long nfcID)throws Exception;
	
	/**
	 * 赛管理--筹码，通过cardNO查询指定存活玩家筹码信息
	 * @param compID
	 * @param nfcID
	 * @return
	 */
	public List<CompMemInfo> findSpecPlayerChipOfCompByCardNO(int compID, String cardNO)throws Exception;
	
	/**
	 * 添加会员比比赛的关系
	 * @param cm
	 * @return
	 * @throws Exception
	 */
	public int saveMemCompRelation(CompetitionMember cm)throws Exception;
	
	/**
	 * 更新比赛选手状态为晋级
	 * @param compID
	 * @return
	 * @throws Exception
	 */
	public int updateMemsAdvanByCompID(int compID)throws Exception;
	
	/**
	 * 逻辑删除某个比赛的报名记录MCSTATE设为-1
	 * @param compID
	 * @throws Exception
	 */
	public void logicDelMemCompByCompID(int compID)throws Exception;
	
	/**
	 * 更新参赛选手的座位信息
	 * @param cmID
	 * @param tableNO
	 * @param seatNO
	 * @return
	 * @throws Exception
	 */
	public int updateMemSeatInfo(int cmID, int tableNO, int seatNO)throws Exception;
	
	/**
	 * 根据主键ID淘汰选手
	 * @param cmID
	 * @return
	 * @throws Exception
	 */
	public int outCompMemberByID(int cmID)throws Exception;
	
	/**
	 * 更新选手筹码
	 * @param mcID
	 * @return
	 * @throws Exception
	 */
	public int updatePlayerChipByID(int mcID, int chip)throws Exception;
	
	/**
	 * 查询选手是否报名了日期范围的比赛（未结束的状态）
	 * @param memID
	 * @param start
	 * @param end
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> getMemRegCompsOfToday(int memID, Date start, Date end)throws Exception;
	
	/**
	 * 导出比赛选手信息
	 * @param compID
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> getExportData(int compID)throws Exception;
	
//	/**
//	 * 查询比赛，牌桌上的存活选手，mcState=1（不包含晋级的）
//	 * @param compID
//	 * @param tableNO
//	 * @return
//	 * @throws Exception
//	 */
//	public List<CompetitionMember> getLivedMemByCompTableNO(int compID, int tableNO)throws Exception;
}
