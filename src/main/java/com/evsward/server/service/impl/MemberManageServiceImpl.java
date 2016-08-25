package com.evsward.server.service.impl;


import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dance.core.orm.BaseDao;
import com.dance.core.service.BaseServiceImpl;
import com.evsward.server.dao.MemberDao;
import com.evsward.server.dao.NFCCardDao;
import com.evsward.server.facade.impl.MemberManageFacadeImpl;
import com.evsward.server.service.MemberManageService;
import com.evsward.server.vo.MemberInfo;

@Component("memService")
public class MemberManageServiceImpl extends BaseServiceImpl<MemberInfo, Integer> implements MemberManageService {

	private static Logger logger = LoggerFactory.getLogger(MemberManageServiceImpl.class);
	
	@Resource
	private MemberDao memDao;
	@Resource
	private NFCCardDao nfcDao;
	
	public boolean selectMemNFCIDIsExist(long nfcID)throws Exception{
		MemberInfo memInfo = new MemberInfo();
		memInfo.setUuidLong(nfcID);
		memInfo = this.memDao.getExactMemInfo(memInfo);
		if(memInfo == null){
			return false;
		}
		return true;
	}

	@Override
	public BaseDao<MemberInfo, Integer> getDao() {
		return memDao;
	}

	@Transactional(propagation=Propagation.REQUIRED, isolation=Isolation.DEFAULT)
	public void updateMemInfo(MemberInfo memInfo) throws Exception {
		if(!memInfo.getCardNO().equals(memInfo.getOldCardNO())){
			logger.info("换卡注销原卡，oldCardNO="+memInfo.getOldCardNO()+",newCardNO="+memInfo.getCardNO());
			this.nfcDao.delNFCByID(Integer.parseInt(memInfo.getOldCardNO()));
		}
		this.memDao.updateMemInfo(memInfo);
	}

	public boolean memIdentNoIsExist(String memIdentNo) throws Exception {
		MemberInfo memInfo = new MemberInfo();
		memInfo.setMemIdentNO(memIdentNo);
		memInfo = this.memDao.getExactMemInfo(memInfo);
		if(memInfo == null){
			return false;
		}
		return true;
	}

	public List<MemberInfo> vagueFindMembers(String vagueParam) throws Exception {
		return this.memDao.vagueFindMembers(vagueParam);
	}

	public MemberInfo findMemByNFC(long nfcID) throws Exception {
		MemberInfo memInfo = new MemberInfo();
		memInfo.setUuidLong(nfcID);
		return this.memDao.getExactMemInfo(memInfo);
	}
	
	public MemberInfo findMemByIdentno(String identno)throws Exception{
		MemberInfo memInfo = new MemberInfo();
		memInfo.setMemIdentNO(identno);
		return this.memDao.getExactMemInfo(memInfo);
	}
	
	public MemberInfo findMemByID(int memID)throws Exception{
		MemberInfo memInfo = new MemberInfo();
		memInfo.setMemID(memID);
		return this.memDao.getExactMemInfo(memInfo);
	}
}
