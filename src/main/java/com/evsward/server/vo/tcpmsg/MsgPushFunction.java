package com.evsward.server.vo.tcpmsg;

import java.io.Serializable;

@SuppressWarnings("serial")
public class MsgPushFunction implements Serializable {

	private int funcID;
	private String funcName;
	private String funcDesc;
	public int getFuncID() {
		return funcID;
	}
	public void setFuncID(int funcID) {
		this.funcID = funcID;
	}
	public String getFuncName() {
		return funcName;
	}
	public void setFuncName(String funcName) {
		this.funcName = funcName;
	}
	public String getFuncDesc() {
		return funcDesc;
	}
	public void setFuncDesc(String funcDesc) {
		this.funcDesc = funcDesc;
	}
	public MsgPushFunction() {
		super();
	}
	public MsgPushFunction(int funcID, String funcName, String funcDesc) {
		super();
		this.funcID = funcID;
		this.funcName = funcName;
		this.funcDesc = funcDesc;
	}
}
