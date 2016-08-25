package com.evsward.server.vo;

import java.io.Serializable;
import java.util.Date;

public class MemberInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public interface MemberState{
		public static final int MEMSTATE_VALID = 1;
		public static final int MEMSTATE_DEL = -1;
	}
	
	public interface MemberSex{
		public static final String MAN = "1";
		public static final String WOMAN = "0";
	}

	private int memID;
	private long uuidLong;
	private String cardNO;
	private String memName;
	private int memState;
	private String memImage;
	private String memMobile;
	private int memSex;
	private String memIdentNO;
	private int chip;
	private int balance;
	private int sysType;
	private Date createTime;
	private Date updateTime;
	private Date delTime;
	//换卡时，原nfc卡、卡号
	private String oldCardNO;
	private long oldUuidLong;
	
	public String getOldCardNO() {
		return oldCardNO;
	}
	public void setOldCardNO(String oldCardNO) {
		this.oldCardNO = oldCardNO;
	}
	public long getOldUuidLong() {
		return oldUuidLong;
	}
	public void setOldUuidLong(long oldUuidLong) {
		this.oldUuidLong = oldUuidLong;
	}
	public int getMemID() {
		return memID;
	}
	public void setMemID(int memID) {
		this.memID = memID;
	}
	public long getUuidLong() {
		return uuidLong;
	}
	public void setUuidLong(long uuidLong) {
		this.uuidLong = uuidLong;
	}
	public String getCardNO() {
		return cardNO;
	}
	public void setCardNO(String cardNO) {
		this.cardNO = cardNO;
	}
	public String getMemName() {
		return memName;
	}
	public void setMemName(String memName) {
		this.memName = memName;
	}
	public int getMemState() {
		return memState;
	}
	public void setMemState(int memState) {
		this.memState = memState;
	}
	public String getMemImage() {
		return memImage;
	}
	public void setMemImage(String memImage) {
		this.memImage = memImage;
	}
	public String getMemMobile() {
		return memMobile;
	}
	public void setMemMobile(String memMobile) {
		this.memMobile = memMobile;
	}
	public int getMemSex() {
		return memSex;
	}
	public void setMemSex(int memSex) {
		this.memSex = memSex;
	}
	public String getMemIdentNO() {
		return memIdentNO;
	}
	public void setMemIdentNO(String memIdentNO) {
		this.memIdentNO = memIdentNO;
	}
	public int getChip() {
		return chip;
	}
	public void setChip(int chip) {
		this.chip = chip;
	}
	public int getBalance() {
		return balance;
	}
	public void setBalance(int balance) {
		this.balance = balance;
	}
	public int getSysType() {
		return sysType;
	}
	public void setSysType(int sysType) {
		this.sysType = sysType;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public Date getDelTime() {
		return delTime;
	}
	public void setDelTime(Date delTime) {
		this.delTime = delTime;
	}
	public MemberInfo() {
		super();
	}
	/**
	 * initMem必须的参数
	 * @param uuidLong
	 * @param memState	0
	 * @param chip	0
	 * @param balance	0
	 * @param sysType	1
	 * @param createTime
	 */
	public MemberInfo(long uuidLong, int memState, int chip, int balance,
			int sysType, Date createTime) {
		super();
		this.uuidLong = uuidLong;
		this.memState = memState;
		this.chip = chip;
		this.balance = balance;
		this.sysType = sysType;
		this.createTime = createTime;
	}
	@Override
	public String toString() {
		return "MemberInfo [memID=" + memID + ", uuidLong=" + uuidLong
				+ ", cardNO=" + cardNO + ", memName=" + memName + ", memState="
				+ memState + ", memImage=" + memImage + ", memMobile="
				+ memMobile + ", memSex=" + memSex + ", memIdentNO="
				+ memIdentNO + ", chip=" + chip + ", balance=" + balance
				+ ", sysType=" + sysType + ", createTime=" + createTime
				+ ", updateTime=" + updateTime + ", delTime=" + delTime + "]";
	}
	
}
