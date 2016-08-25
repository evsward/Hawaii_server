package com.evsward.server.dao.impl;

import org.springframework.stereotype.Component;

import com.dance.core.orm.mybatis.MyBatisDaoImpl;
import com.evsward.server.dao.NFCCardDao;
import com.evsward.server.vo.InitedNFC;

@Component("nfcDao")
public class NFCCardDaoImpl extends MyBatisDaoImpl<InitedNFC, Long> implements NFCCardDao {

	public int delNFCByID(int id) throws Exception {
		this.update("delNfcByID", id);
		return 0;
	}

	public void insertNFCInfo(InitedNFC nfcInfo)throws Exception{
		this.insert("insert", nfcInfo);
	}
}
