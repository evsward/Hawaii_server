package com.evsward.server.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.dance.core.orm.BaseDao;
import com.dance.core.service.BaseServiceImpl;
import com.evsward.server.dao.NFCCardDao;
import com.evsward.server.service.NFCCardService;
import com.evsward.server.vo.InitedNFC;

@Component("nfcService")
public class NFCCardServiceImpl extends BaseServiceImpl<InitedNFC, Long>
		implements NFCCardService {

	@Resource
	private NFCCardDao nfcDao;
	
	/**
	 * 查询nfc卡对象信息
	 */
	public InitedNFC getInitedNFCbynfcID(long nfcID) throws Exception {
		return this.nfcDao.get(nfcID);
	}

	@Override
	public BaseDao<InitedNFC, Long> getDao() {
		return nfcDao;
	}
	
	public void insertNFCInfo(InitedNFC nfcInfo)throws Exception{
		this.nfcDao.insertNFCInfo(nfcInfo);
	}
}
