package com.evsward.server.facade;

public interface CardTableManageFacade {

	/**
	 * 新建牌桌
	 * @param address
	 * @param tableCount
	 * @param empUuid
	 * @param sysType
	 * @return
	 */
	public String addTables(String address, int tableCount, int sysType, String empUuid);
	
	/**
	 * 删除牌桌
	 * @param empUuid
	 * @param sysType
	 * @return
	 */
	public String delTables(int sysType, String empUuid);
	
	/**
	 * 查看牌桌列表
	 * @param sysType
	 * @return
	 */
	public String listTables(int sysType);
}
