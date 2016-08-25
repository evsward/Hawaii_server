package com.evsward.server.vo.tcpmsg;

import java.io.Serializable;

@SuppressWarnings("serial")
public class MsgCmdFuncRelation implements Serializable {

	private int id;
	private int funcID;
	private int msgID;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getFuncID() {
		return funcID;
	}
	public void setFuncID(int funcID) {
		this.funcID = funcID;
	}
	public int getMsgID() {
		return msgID;
	}
	public void setMsgID(int msgID) {
		this.msgID = msgID;
	}
}
