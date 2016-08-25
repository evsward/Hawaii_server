package com.evsward.server.facade.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.evsward.server.facade.MemberManageFacade;
import com.evsward.server.service.MemCompService;
import com.evsward.server.service.MemberManageService;
import com.evsward.server.service.NFCCardService;
import com.evsward.server.util.Application;
import com.evsward.server.util.DateUtils;
import com.evsward.server.util.RspCodeValue;
import com.evsward.server.util.HIFileUtils;
import com.evsward.server.util.HIUtil;
import com.evsward.server.vo.CompetitionMember;
import com.evsward.server.vo.InitedNFC;
import com.evsward.server.vo.MemberInfo;
import com.evsward.server.vo.regcomp.MemReg_CompSearch_compInfo;
import com.evsward.server.vo.regcomp.RunCompSeatInfo;

@Component("memManFacade")
public class MemberManageFacadeImpl implements MemberManageFacade {

	private static Logger logger = LoggerFactory.getLogger(MemberManageFacadeImpl.class);
	
	@Resource
	private MemberManageService memService;
	@Resource
	private MemCompService memCompService;
	@Resource
	private NFCCardService nfcService;
	
	/**
	 * 新建会员
	 * @param nfcID 会员卡
	 * @param cardNO
	 * @param memName
	 * @param memSex
	 * @param mobile
	 * @param identno
	 * @param image
	 * @return
	 */
	public String addMemInfoTest(long nfcID, String cardNO, String memName, String mobile, int memSex, String identno, int sysType, File image){
		String jsonStr = "";
		String imageName = cardNO + ".jpg";
		boolean isExist = true;
		MemberInfo memInfo = new MemberInfo();
		memInfo.setUuidLong(nfcID);
		memInfo.setCardNO(cardNO);
		memInfo.setMemName(memName);
		memInfo.setMemImage(imageName);
		memInfo.setMemSex(memSex);
		memInfo.setMemMobile(mobile);
		memInfo.setMemState(MemberInfo.MemberState.MEMSTATE_VALID);
		memInfo.setMemIdentNO(identno);
		memInfo.setSysType(sysType);
		memInfo.setCreateTime(new Date());
		//完善信息
		try {
			isExist = this.memService.memIdentNoIsExist(identno);
			if(isExist){
				jsonStr = HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$229.getRspCode(), RspCodeValue.$229.getMsg()));
				return jsonStr;
			}
			this.memService.insert(memInfo);
		} catch (Exception e) {
			logger.error("perfectMember error nfcID="+nfcID, e);
			jsonStr = HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$230.getRspCode(), RspCodeValue.$230.getMsg()));
			return jsonStr;
		}
		jsonStr = HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$1.getRspCode(), RspCodeValue.$1.getMsg()));
		return jsonStr;
	}
	
	/**
	 * 新建会员
	 * @param nfcID 会员卡
	 * @param cardNO
	 * @param memName
	 * @param memSex
	 * @param mobile
	 * @param identno
	 * @param image
	 * @return
	 */
	public String addMemInfo(long nfcID, String cardNO, String memName, String mobile, int memSex, String identno, int sysType, File image){
		String jsonStr = "";
		String imageName = cardNO + ".jpg";
		//保存用户头像图片
		boolean saveSuc = HIFileUtils.saveFile(Application.PATH_ROOT + Application.PATH_IMAGE, imageName, image);
		if(!saveSuc){
			jsonStr = HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$232.getRspCode(), RspCodeValue.$232.getMsg()));
			return jsonStr;
		}
		boolean isExist = true;
		MemberInfo memInfo = new MemberInfo();
		memInfo.setUuidLong(nfcID);
		memInfo.setCardNO(cardNO);
		memInfo.setMemName(memName);
		memInfo.setMemImage(imageName);
		memInfo.setMemSex(memSex);
		memInfo.setMemMobile(mobile);
		memInfo.setMemState(MemberInfo.MemberState.MEMSTATE_VALID);
		memInfo.setMemIdentNO(identno);
		memInfo.setSysType(sysType);
		memInfo.setCreateTime(new Date());
		//完善信息
		try {
			isExist = this.memService.memIdentNoIsExist(identno);
			if(isExist){
				jsonStr = HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$229.getRspCode(), RspCodeValue.$229.getMsg()));
				return jsonStr;
			}
			this.memService.insert(memInfo);
		} catch (Exception e) {
			logger.error("perfectMember error nfcID="+nfcID, e);
			jsonStr = HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$230.getRspCode(), RspCodeValue.$230.getMsg()));
			return jsonStr;
		}
		jsonStr = HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$1.getRspCode(), RspCodeValue.$1.getMsg()));
		return jsonStr;
	}
	
	/**
	 * 模糊查询用户信息，匹配条件：cardno ， memname ， memidentno ， memmobile其中任意一个
	 * @param vagueParam
	 * @return
	 * @throws Exception
	 */
	public String vagueFindMembers(String vagueParam){
		List<MemberInfo> members = null;
		String jsonStr = "";
		try {
			members = this.memService.vagueFindMembers(vagueParam);
		} catch (Exception e) {
			logger.error("", e);
			jsonStr = HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$221.getRspCode(), RspCodeValue.$221.getMsg()));
			return jsonStr;
		}
		//没有找到会员的信息
		if(members == null || members.size() <= 0){
			jsonStr = HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$226.getRspCode(), RspCodeValue.$226.getMsg()));
			return jsonStr;
		}
		Map<String, Object> resmap = HIUtil.createJsonResInitMap(RspCodeValue.$1.getRspCode(), RspCodeValue.$1.getMsg());
		resmap.put("memberInfo", members);
		jsonStr = HIUtil.toJsonNormalField(resmap);
		return jsonStr;
	}

	/**
	 * 刷nfc卡查询会员
	 * @param nfcID
	 * @return
	 */
	public String findMemByNFC(long nfcID){
		String jsonStr = "";
		MemberInfo memInfo = null;
		InitedNFC nfcInfo = null;
		try {
			nfcInfo = this.nfcService.getInitedNFCbynfcID(nfcID);
		} catch (Exception e1) {
			logger.info(e1.getMessage(), e1);
			//获取nfc卡信息失败
			return HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$201.getRspCode(), RspCodeValue.$201.getMsg()));
		}
		if(nfcInfo == null){//不存在此卡
			return HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$202.getRspCode(), RspCodeValue.$202.getMsg()));
		}
		if(InitedNFC.NFCStateConst.NFCSTATEVALID != nfcInfo.getNfcState()){//此卡状态无效
			return HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$203.getRspCode(), RspCodeValue.$203.getMsg()));
		}
		try {
			memInfo = this.memService.findMemByNFC(nfcID);
		} catch (Exception e) {
			logger.error("", e);
			jsonStr = HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$223.getRspCode(), RspCodeValue.$223.getMsg()));
			return jsonStr;
		}
		//没有找到会员的全部信息
		if(memInfo == null){
			jsonStr = HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$231.getRspCode(), RspCodeValue.$231.getMsg()));
			return jsonStr;
		}
		Map<String, Object> resmap = HIUtil.createJsonResInitMap(RspCodeValue.$1.getRspCode(), RspCodeValue.$1.getMsg());
		resmap.put("memberInfo", memInfo);
		jsonStr = HIUtil.toJsonNormalField(resmap);
		return jsonStr;
		
	}
	
	/**
	 * 根绝memID查询会员
	 * @param nfcID
	 * @param sysType
	 * @return
	 */
	public String findMemByMemID(int memID, int sysType){
		String jsonStr = "";
		MemberInfo memInfo = null;
		try {
			memInfo = this.memService.findMemByID(memID);
		} catch (Exception e) {
			logger.error("", e);
			jsonStr = HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$223.getRspCode(), RspCodeValue.$223.getMsg()));
			return jsonStr;
		}
		//没有找到会员的全部信息
		if(memInfo == null){
			jsonStr = HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$231.getRspCode(), RspCodeValue.$231.getMsg()));
			return jsonStr;
		}
		Map<String, Object> resmap = HIUtil.createJsonResInitMap(RspCodeValue.$1.getRspCode(), RspCodeValue.$1.getMsg());
		resmap.put("memberInfo", memInfo);
		jsonStr = HIUtil.toJsonNormalField(resmap);
		return jsonStr;
	}

	public String updateMember(int memID, long nfcID, String cardNO,
			String memName, int memSex, String identno, String mobile,
			File image, String empUuid) {
		MemberInfo mem = null;
		String jsonStr = "";
		String imageName = cardNO + ".jpg";
		try {
			mem = this.memService.findMemByID(memID);
		} catch (Exception e) {
			logger.error("主键查询会员信息失败memID=" + memID, e);
			jsonStr = HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$225.getRspCode(), RspCodeValue.$225.getMsg()));
			return jsonStr;
		}
		//没有找到会员的全部信息
		if(mem == null){
			jsonStr = HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$226.getRspCode(), RspCodeValue.$226.getMsg()));
			return jsonStr;
		}
		try {
			if(!identno.equals(mem.getMemIdentNO())){//编辑了身份证件号
				MemberInfo temp = this.memService.findMemByIdentno(identno);
				if(temp != null && temp.getMemID() != mem.getMemID()){//已经存在这个身份证件号码
					jsonStr = HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$229.getRspCode(), RspCodeValue.$229.getMsg()));
					return jsonStr;
				}
			}
		} catch (Exception e) {
			logger.error("", e);
			jsonStr = HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$225.getRspCode(), RspCodeValue.$225.getMsg()));
			return jsonStr;
		}
		if(image != null){//换照片
			//保存用户新的头像图片
			boolean saveSuc = HIFileUtils.saveFile(Application.PATH_ROOT + Application.PATH_IMAGE, imageName, image);
			if(!saveSuc){
				jsonStr = HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$232.getRspCode(), RspCodeValue.$232.getMsg()));
				return jsonStr;
			}
		}
		mem.setOldCardNO(mem.getCardNO());
		mem.setOldUuidLong(mem.getUuidLong());
		mem.setCardNO(cardNO);
		mem.setUuidLong(nfcID);
		
		mem.setMemName(memName);
		mem.setMemImage(imageName);
		mem.setMemSex(memSex);
		mem.setMemIdentNO(identno);
		mem.setMemMobile(mobile);
		try {
			this.memService.updateMemInfo(mem);
		} catch (Exception e) {
			logger.error("", e);
			if(mem.getOldCardNO() == mem.getCardNO()){
				jsonStr = HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$227.getRspCode(), RspCodeValue.$227.getMsg()));
			}else{
				jsonStr = HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$228.getRspCode(), RspCodeValue.$228.getMsg()));
			}
			return jsonStr;
		}
		jsonStr = HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$1.getRspCode(), RspCodeValue.$1.getMsg()));
		return jsonStr;
	}

	@Override
	public String findMemCompInfoByMemID(int memID, int sysType) {
		String jsonStr = "";
		List<CompetitionMember> regedCompList = null;
		MemberInfo memInfo = null;
		MemReg_CompSearch_compInfo memReg_CompSearch_Info = null;//
		RunCompSeatInfo seatInfo = null;
		List<MemReg_CompSearch_compInfo> list = new ArrayList<MemReg_CompSearch_compInfo>();
		//查询会员信息
		try {
			memInfo = this.memService.findMemByID(memID);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$225.getRspCode(), RspCodeValue.$225.getMsg()));
		}
		if(memInfo == null){
			return HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$226.getRspCode(), RspCodeValue.$226.getMsg()));
		}
		//会员参赛状态：-9、无效；-1、删除；0、已淘汰；1、已分座；2、晋级
		try {
			regedCompList = this.memCompService.getMemCompetitionInfos(memID, sysType);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$222.getRspCode(), RspCodeValue.$222.getMsg()));
		}
		if(regedCompList == null || regedCompList.size() <= 0){
			return HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$233.getRspCode(), RspCodeValue.$233.getMsg()));
		}
		//比赛ID排重
		Set<Integer> compIDSet = new LinkedHashSet<Integer>();
		for (int i = 0; i < regedCompList.size(); i++) {
			compIDSet.add(regedCompList.get(i).getCompID());
		}
		//按照排重后的循环和regedCompList做匹配
		Iterator<Integer> compIDIt = compIDSet.iterator();
		int regedCompListSize = regedCompList.size();
		while(compIDIt.hasNext()){
			Integer compID = compIDIt.next();
			memReg_CompSearch_Info = new MemReg_CompSearch_compInfo();
			memReg_CompSearch_Info.setCompID(compID);
			for (int i = 0; i < regedCompListSize; i++) {
				CompetitionMember cm = regedCompList.get(i);
				if(cm.getCompID() == compID){
					memReg_CompSearch_Info.setCompName(cm.getCompName());
					memReg_CompSearch_Info.setCompState(cm.getCompState());
					memReg_CompSearch_Info.setDateNoTime(DateUtils.formatDate(cm.getCompStartTime(), DateUtils.PATTERN2));
					memReg_CompSearch_Info.setTime(DateUtils.formatDate(cm.getCompStartTime(), DateUtils.PATTERN7));
					//如果设置了晋级或者参赛中，就不再改变状态了。
					if(memReg_CompSearch_Info.getMcState() != CompetitionMember.MemCompState.REGED && memReg_CompSearch_Info.getMcState() != CompetitionMember.MemCompState.ADVAN){
						memReg_CompSearch_Info.setMcState(cm.getMcState());
					}
					memReg_CompSearch_Info.setRegCount(memReg_CompSearch_Info.getRegCount() + 1);
					if(cm.getMcState() == CompetitionMember.MemCompState.REGED || cm.getMcState() == CompetitionMember.MemCompState.ADVAN){
						seatInfo = new RunCompSeatInfo(cm.getTableNO(), cm.getSeatNO());
						memReg_CompSearch_Info.getRunCompSeatInfoList().add(seatInfo);
					}
				}
			}
			list.add(memReg_CompSearch_Info);
		}
		Map<String, Object> map = HIUtil.createJsonResInitMap(RspCodeValue.$1.getRspCode(), RspCodeValue.$1.getMsg());
		map.put("memberInfo", memInfo);
		map.put("compInfos", list);
		jsonStr = HIUtil.toJsonNormalField(map);
		return jsonStr;
	}
}
