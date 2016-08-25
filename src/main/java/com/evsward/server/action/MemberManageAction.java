package com.evsward.server.action;

import java.io.File;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;

import com.dance.core.service.BaseService;
import com.dance.core.utils.web.StrutsAction;
import com.evsward.server.facade.MemberManageFacade;
import com.evsward.server.facade.NFCCardFacade;
import com.evsward.server.util.RspCodeValue;
import com.evsward.server.util.HIUtil;
import com.evsward.server.vo.MemberInfo;

@SuppressWarnings("serial")
@Namespace("/member/member")
@ParentPackage(value = "hi")
public class MemberManageAction extends StrutsAction<MemberInfo, Integer> {


	/**
	 * 判断nfc卡是否可用
	 * @return
	 */
	@Action("getcardno")
	public String checkNFCEnable(){
		if(this.getActionErrors().size() > 0){
			HIUtil.sendResponseJson(HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$11.getRspCode(), RspCodeValue.$11.getMsg())));
			return NONE;
		}
		HIUtil.printRequestParam(this.getClass().getSimpleName(), "checkNFCEnable");
		HIUtil.sendResponseJson(nfcFacade.getNFCInfo(nfcID));
		return NONE;
	}
	
	/**
	 * 模糊查找用户
	 * @return
	 */
	@Action("vagueFindMems")
	public String vagueFindMemInfos(){
		//校验参数
		if(this.getActionErrors().size() > 0){
			HIUtil.sendResponseJson(HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$11.getRspCode(), RspCodeValue.$11.getMsg())));
			return NONE;
		}
		HIUtil.printRequestParam(this.getClass().getSimpleName(), "vagueFindMemInfos");
		HIUtil.sendResponseJson(memManFacade.vagueFindMembers(vagueParam.trim()));
		return NONE;
	}
	
	/**
	 * 刷nfc卡查询会员
	 * @return
	 */
	@Action("searchMemNFC")
	public String findMemInfosByNFC(){
		if(this.getActionErrors().size() > 0){
			HIUtil.sendResponseJson(HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$11.getRspCode(), RspCodeValue.$11.getMsg())));
			return NONE;
		}
		HIUtil.printRequestParam(this.getClass().getSimpleName(), "findMemInfosByNFC");
		HIUtil.sendResponseJson(memManFacade.findMemByNFC(nfcID));
		return NONE;
	}
	
	/**
	 * 新建会员
	 * @return
	 */
	@Action(value="addMember")
	public String addMember(){
		if(this.getActionErrors().size() > 0){
			HIUtil.sendResponseJson(HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$11.getRspCode(), RspCodeValue.$11.getMsg())));
			return NONE;
		}
		HIUtil.printRequestParam(this.getClass().getSimpleName(), "addMember");
		//完善member信息
		HIUtil.sendResponseJson(memManFacade.addMemInfo(nfcID, cardNO, memName.trim(), mobile.trim(), Integer.parseInt(memSex), identno.trim(), sysType, image));
		return NONE;
	}
	
	/**
	 * 新建会员
	 * @return
	 */
	@Action(value="addMemberTest")
	public String addMemberTest(){
		HIUtil.printRequestParam(this.getClass().getSimpleName(), "addMemberTest");
		//完善member信息
		HIUtil.sendResponseJson(memManFacade.addMemInfoTest(nfcID, cardNO, memName.trim(), mobile.trim(), Integer.parseInt(memSex), identno.trim(), sysType, image));
		return NONE;
	}
	
	/**
	 * 更新会员（包括换卡）
	 * @return
	 */
	@Action(value="updateMember")
	public String updateMember(){
		if(this.getActionErrors().size() > 0){
			HIUtil.sendResponseJson(HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$11.getRspCode(), RspCodeValue.$11.getMsg())));
			return NONE;
		}
		HIUtil.printRequestParam(this.getClass().getSimpleName(), "updateMember");
		HIUtil.sendResponseJson(this.memManFacade.updateMember(memID, nfcID, cardNO, memName.trim(), Integer.parseInt(memSex), identno.trim(), mobile.trim(), image, empUuid));
		return NONE;
	}
	
	/**
	 * 根据memID查询会员信息
	 * @return
	 */
	@Action(value="searchMemByMemID")
	public String findMemInfoByMemID(){
		HIUtil.printRequestParam(this.getClass().getSimpleName(), "findMemInfoByMemID");
		HIUtil.sendResponseJson(this.memManFacade.findMemByMemID(this.memID, this.sysType));
		return NONE;
	}
	
	/**
	 * 刷nfc卡查询会员信息参数校验
	 */
	public void validateFindMemInfosByNFC(){
		if(nfcID <= 0){
			this.addActionError(RspCodeValue.$11.getMsg());
		}
	}
	
	/**
	 * 模糊查询会员信息参数校验
	 */
	public void validateVagueFindMemInfos(){
		if(StringUtils.isEmpty(vagueParam)){
			this.addActionError(RspCodeValue.$11.getMsg());
		}
	}
	/**
	 * 编辑会员参数校验
	 */
	public void validateUpdateMember(){
		if(this.memID <= 0 || StringUtils.isEmpty(this.memName) || (!MemberInfo.MemberSex.MAN.equals(this.memSex) && !MemberInfo.MemberSex.WOMAN.equals(this.memSex)) 
				|| StringUtils.isEmpty(this.identno) || StringUtils.isEmpty(this.mobile)
				|| StringUtils.isEmpty(this.cardNO)){
			this.addActionError(RspCodeValue.$11.getMsg());
			logger.error("updateMember"+","+this.memName+","+this.memSex+","+this.identno+","+this.mobile+","+this.cardNO+","+image);
		}
	}
	
	/**
	 * 新建会员参数校验
	 */
	public void validateAddMember(){
		if(StringUtils.isEmpty(this.memName) || (!MemberInfo.MemberSex.MAN.equals(this.memSex) && !MemberInfo.MemberSex.WOMAN.equals(this.memSex))
				|| StringUtils.isEmpty(this.identno) || StringUtils.isEmpty(this.mobile)
				|| StringUtils.isEmpty(this.cardNO) || image == null){
			this.addActionError(RspCodeValue.$11.getMsg());
			logger.error("addMember"+","+this.memName+","+this.memSex+","+this.identno+","+this.mobile+","+this.cardNO+","+image);
		}
	}
	
	/**
	 * nfc卡有效性参数校验
	 */
	public void validateCheckNFCInitMem(){
		if(nfcID <= 0){
			this.addActionError(RspCodeValue.$11.getMsg());
		}
	}
	
	@Override
	public BaseService<MemberInfo, Integer> getService() {
		return null;
	}
	
	public MemberInfo getModel() {
		return null;
	}
	
	public String getEmpUuid() {
		return empUuid;
	}

	public void setEmpUuid(String empUuid) {
		this.empUuid = empUuid;
	}
	
	public String getMemName() {
		return memName;
	}

	public void setMemName(String memName) {
		this.memName = memName;
	}

	public String getMemSex() {
		return memSex;
	}

	public void setMemSex(String memSex) {
		this.memSex = memSex;
	}

	public String getIdentno() {
		return identno;
	}

	public void setIdentno(String identno) {
		this.identno = identno;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public int getMemID() {
		return memID;
	}

	public void setMemID(int memID) {
		this.memID = memID;
	}

	public long getNfcID() {
		return nfcID;
	}

	public void setNfcID(long nfcID) {
		this.nfcID = nfcID;
	}

	public File getImage() {
		return image;
	}

	public void setImage(File image) {
		this.image = image;
	}

	public String getCardNO() {
		return cardNO;
	}

	public void setCardNO(String cardNO) {
		this.cardNO = cardNO;
	}
	
	public String getVagueParam() {
		return vagueParam;
	}

	public void setVagueParam(String vagueParam) {
		this.vagueParam = vagueParam;
	}
	
	public int getCompID() {
		return compID;
	}

	public void setCompID(int compID) {
		this.compID = compID;
	}
	
	public String getImageContentType() {
		return imageContentType;
	}

	public void setImageContentType(String imageContentType) {
		this.imageContentType = imageContentType;
	}

	public String getImageFileName() {
		return imageFileName;
	}

	public void setImageFileName(String imageFileName) {
		this.imageFileName = imageFileName;
	}
	
	public int getSysType() {
		return sysType;
	}

	public void setSysType(int sysType) {
		this.sysType = sysType;
	}
	
	//员工nfctagid
	private String empUuid;
	//会员姓名
	private String memName;
	//会员性别
	private String memSex;
	//会员身份证号
	private String identno;
	//会员手机号
	private String mobile;
	//会员卡号
	private String cardNO;
	//会员ID
	private int memID;
	//nfctagid十进制
	private long nfcID;
	//上传的会员头像照片
	private File image;
	private String imageContentType;
	private String imageFileName;
	//模糊查找参数
	private String vagueParam;
	//比赛ID
	private int compID;
	private int sysType;

	@Resource
	private NFCCardFacade nfcFacade;
	@Resource
	private MemberManageFacade memManFacade;
}
