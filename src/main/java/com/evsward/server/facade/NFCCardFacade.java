package com.evsward.server.facade;

public interface NFCCardFacade {

	/**
	 * 
	 * @param nfcID	nfcID
	 * @return
	 */
	public String getNFCInfo(long nfcID);
	
	/**
	 * 插入nfc会员卡
	 * @param nfcID
	 * @param sysType
	 * @return
	 */
	public String insertNFCInfo(long nfcID, int sysType);
}
