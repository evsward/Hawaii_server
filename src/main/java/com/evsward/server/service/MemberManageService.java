package com.evsward.server.service;

import java.util.List;

import com.dance.core.service.BaseService;
import com.evsward.server.vo.MemberInfo;

public interface MemberManageService extends BaseService<MemberInfo, Integer> {

	/**
	 * 此NFCID是否存在
	 * @param nfcID
	 * @return
	 * @throws Exception
	 */
	public boolean selectMemNFCIDIsExist(long nfcID)throws Exception;
	
	/**
	 * 刷nfc卡查询会员
	 * @param nfcID
	 * @return
	 * @throws Exception
	 */
	public MemberInfo findMemByNFC(long nfcID)throws Exception;
	
	/**
	 * 根据memID查询会员
	 * @param memID
	 * @return
	 * @throws Exception
	 */
	public MemberInfo findMemByID(int memID)throws Exception;
	
	/**
	 * 修改会员
	 * @param memInfo
	 * @throws Exception
	 */
	public void updateMemInfo(MemberInfo memInfo)throws Exception;
	
	/**
	 * 判断memIdentNo是否已经存在，
	 * @param memIdentNo
	 * @return
	 * @throws Exception
	 */
	public boolean memIdentNoIsExist(String memIdentNo)throws Exception;
	
	/**
	 * 根据身份证件号查询用户信息
	 * @param identno
	 * @return
	 * @throws Exception
	 */
	public MemberInfo findMemByIdentno(String identno)throws Exception;
	
	/**
	 * 模糊查询用户信息，匹配条件：cardno ， memname ， memidentno ， memmobile其中任意一个
	 * @param vagueParam
	 * @return
	 * @throws Exception
	 */
	public List<MemberInfo> vagueFindMembers(String vagueParam)throws Exception;
}
