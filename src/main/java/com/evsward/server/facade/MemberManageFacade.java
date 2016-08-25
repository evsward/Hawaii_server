package com.evsward.server.facade;

import java.io.File;


public interface MemberManageFacade {

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
	public String addMemInfoTest(long nfcID, String cardNO, String memName, String mobile, int memSex, String identno, int sysType, File image);
	
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
	public String addMemInfo(long nfcID, String cardNO, String memName, String mobile, int memSex, String identno, int sysType, File image);
	
	/**
	 * 模糊查询用户信息，匹配条件：cardno ， memname ， memidentno ， memmobile其中任意一个
	 * @param vagueParam
	 * @return
	 * @throws Exception
	 */
	public String vagueFindMembers(String vagueParam);
	
	/**
	 * 刷nfc卡查询会员
	 * @param nfcID
	 * @return
	 */
	public String findMemByNFC(long nfcID);
	
	/**
	 * 根绝memID查询会员
	 * @param nfcID
	 * @param sysType
	 * @return
	 */
	public String findMemByMemID(int memID, int sysType);
	
	/**
	 * 编辑会员(包括补卡)
	 * @param memID
	 * @param nfcID
	 * @param cardNO
	 * @param memName
	 * @param memSex
	 * @param identno
	 * @param mobile
	 * @param image
	 * @param empUuid
	 * @return
	 */
	public String updateMember(int memID, long nfcID, String cardNO, String memName, int memSex, String identno, String mobile, File image,String empUuid);

	/**
	 * 根据会员ID查询会员参赛信息
	 * @param memID
	 * @param sysType
	 * @return
	 */
	public String findMemCompInfoByMemID(int memID, int sysType);
}
