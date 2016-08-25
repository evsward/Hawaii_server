package com.evsward.server.facade.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.evsward.server.facade.NFCCardFacade;
import com.evsward.server.service.MemberManageService;
import com.evsward.server.service.NFCCardService;
import com.evsward.server.util.RspCodeValue;
import com.evsward.server.util.HIUtil;
import com.evsward.server.vo.InitedNFC;
import com.evsward.server.vo.InitedNFC.NFCStateConst;

@Component("nfcFacade")
public class NFCCardFacadeImpl implements NFCCardFacade{

	private static Logger logger = LoggerFactory.getLogger(NFCCardFacadeImpl.class);
	
	@Resource
	private NFCCardService nfcService;
	@Resource
	private MemberManageService memService;
	
	public String insertNFCInfo(long nfcID, int sysType){
		String jsonStr = "";
		InitedNFC nfcInfo = null;
		if(String.valueOf(nfcID).length() > 11){//选手卡的ID长度都是11位以内的数字
			jsonStr = HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$105.getRspCode(), RspCodeValue.$105.getMsg()));
			return jsonStr;
		}
		try {
			nfcInfo = this.nfcService.getInitedNFCbynfcID(nfcID);
			if(nfcInfo == null){
				nfcInfo = new InitedNFC();
				nfcInfo.setUuidLong(nfcID);
				nfcInfo.setNfcState(NFCStateConst.NFCSTATEVALID);
				nfcInfo.setSysType(sysType);
				this.nfcService.insertNFCInfo(nfcInfo);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			jsonStr = HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$104.getRspCode(), RspCodeValue.$104.getMsg()));
			return jsonStr;
		}
		nfcInfo.setCardno(HIUtil.getMemCardNO(nfcInfo.getId()));
		Map<String, Object> resmap = HIUtil.createJsonResInitMap(RspCodeValue.$1.getRspCode(), RspCodeValue.$1.getMsg());
		resmap.put("nfcInfo", nfcInfo);
		jsonStr = HIUtil.toJsonNormalField(resmap);
		return jsonStr;
	}
	
	public String getNFCInfo(long nfcID){
		InitedNFC nfcInfo = null;
		try {
			nfcInfo = nfcService.getInitedNFCbynfcID(nfcID);
		} catch (Exception e) {
			logger.error(" get InitedNFC error nfcID="+nfcID, e);
			//获取nfc卡信息失败
			return HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$201.getRspCode(), RspCodeValue.$201.getMsg()));
		}
		if(nfcInfo == null){//不存在此卡
			return HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$202.getRspCode(), RspCodeValue.$202.getMsg()));
		}
		if(InitedNFC.NFCStateConst.NFCSTATEVALID != nfcInfo.getNfcState()){//此卡状态无效
			return HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$203.getRspCode(), RspCodeValue.$203.getMsg()));
		}
		//查询NFC卡是否已经使用
		boolean isExist = true;
		try {
			isExist = memService.selectMemNFCIDIsExist(nfcID);
		} catch (Exception e) {
			logger.error(" search nfc is used error nfcID="+nfcID, e);
			return HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$204.getRspCode(), RspCodeValue.$204.getMsg()));
		}
		if(isExist){
			return HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$205.getRspCode(), RspCodeValue.$205.getMsg()));
		}
		//返回给pad端此卡的卡号
		String cardNO = HIUtil.getMemCardNO(nfcInfo.getId());
		nfcInfo.setCardno(cardNO);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("nfcInfo", nfcInfo);
		return HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$1.getRspCode(), RspCodeValue.$1.getMsg(), map));
	}

}
