package com.evsward.server.dao;

import com.dance.core.orm.BaseDao;
import com.evsward.server.vo.InitedNFC;

public interface NFCCardDao extends BaseDao<InitedNFC, Long> {

	/**
	 * 根据序列号删除nfc卡
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public int delNFCByID(int id)throws Exception;
	
	/**
	 * 插入nfc会员卡
	 * @param nfcInfo
	 * @throws Exception
	 */
	public void insertNFCInfo(InitedNFC nfcInfo)throws Exception;
}
