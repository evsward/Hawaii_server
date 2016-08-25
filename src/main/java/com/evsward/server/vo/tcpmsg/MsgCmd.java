package com.evsward.server.vo.tcpmsg;

import java.io.Serializable;

@SuppressWarnings("serial")
public class MsgCmd implements Serializable {

	private int msgID;
	private String msgName;
	private String msgDesc;
	private String pushClass;
	
	public String getPushClass() {
		return pushClass;
	}
	public void setPushClass(String pushClass) {
		this.pushClass = pushClass;
	}
	public int getMsgID() {
		return msgID;
	}
	public void setMsgID(int msgID) {
		this.msgID = msgID;
	}
	public String getMsgName() {
		return msgName;
	}
	public void setMsgName(String msgName) {
		this.msgName = msgName;
	}
	public String getMsgDesc() {
		return msgDesc;
	}
	public void setMsgDesc(String msgDesc) {
		this.msgDesc = msgDesc;
	}
	public MsgCmd() {
		super();
	}
	public MsgCmd(int msgID, String msgName, String msgDesc, String pushClass) {
		super();
		this.msgID = msgID;
		this.msgName = msgName;
		this.msgDesc = msgDesc;
		this.pushClass = pushClass;
	}
}
