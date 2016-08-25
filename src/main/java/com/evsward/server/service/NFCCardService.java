package com.evsward.server.service;

import com.dance.core.service.BaseService;
import com.evsward.server.vo.InitedNFC;

public interface NFCCardService extends BaseService<InitedNFC, Long> {

	/**
	 * 查询nfc卡信息
	 * @param nfcID
	 * @return
	 * @throws Exception
	 */
	public InitedNFC getInitedNFCbynfcID(long nfcID)throws Exception;
	
	/**
	 * 插入nfc会员卡
	 * @param nfcInfo
	 * @throws Exception
	 */
	public void insertNFCInfo(InitedNFC nfcInfo)throws Exception;
}
