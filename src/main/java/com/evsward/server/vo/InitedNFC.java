package com.evsward.server.vo;

import java.io.Serializable;

public class InitedNFC implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public interface NFCStateConst{
		public static final int NFCSTATEVALID = 1;
		public static final int NFCSTATEINVALID = 0;
	}
	private int id;
	private long uuidLong;
	private String cardno;//用户看到的卡号
	private int nfcState;
	private int sysType;
	
	public int getSysType() {
		return sysType;
	}
	public void setSysType(int sysType) {
		this.sysType = sysType;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public long getUuidLong() {
		return uuidLong;
	}
	public void setUuidLong(long uuidLong) {
		this.uuidLong = uuidLong;
	}
	public String getCardno() {
		return cardno;
	}
	public void setCardno(String cardno) {
		this.cardno = cardno;
	}
	public int getNfcState() {
		return nfcState;
	}
	public void setNfcState(int nfcState) {
		this.nfcState = nfcState;
	}
	public InitedNFC() {
		super();
	}
	@Override
	public String toString() {
		return "InitedNFC [id=" + id + ", uuidLong=" + uuidLong + ", nfcState="
				+ nfcState + "]";
	}
}
