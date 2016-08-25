package com.evsward.server.facade;

public interface MemRegCompFacade {

	/**
	 * 会员报名查询会员已报的比赛和所有比赛
	 * @param memID
	 * @param sysType
	 * @return
	 */
	public String searchRegedCompAndAllComps(int memID, int sysType);
	
	/**
	 * 普通会员报名比赛
	 * @param memID
	 * @param compID
	 * @return
	 */
	public String memRegComp(int memID, int compID);
	
	/**
	 * VIP会员报名比赛
	 * @param memID
	 * @param compID
	 * @param tableNO
	 * @param seatNO
	 * @return
	 */
	public String vipMemRegComp(int memID, int compID, int tableNO, int seatNO);
}
