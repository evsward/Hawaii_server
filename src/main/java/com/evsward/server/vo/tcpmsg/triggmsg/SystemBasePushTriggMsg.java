package com.evsward.server.vo.tcpmsg.triggmsg;

public class SystemBasePushTriggMsg {

	private int funcID;
	private int memID;
	private int compID;
	public int getFuncID() {
		return funcID;
	}
	public void setFuncID(int funcID) {
		this.funcID = funcID;
	}
	public int getMemID() {
		return memID;
	}
	public void setMemID(int memID) {
		this.memID = memID;
	}
	public int getCompID() {
		return compID;
	}
	public void setCompID(int compID) {
		this.compID = compID;
	}
	public SystemBasePushTriggMsg(int funcID, int memID, int compID) {
		super();
		this.funcID = funcID;
		this.memID = memID;
		this.compID = compID;
	}
}
