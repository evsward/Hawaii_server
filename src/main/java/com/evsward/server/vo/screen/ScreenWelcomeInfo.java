package com.evsward.server.vo.screen;

import java.io.Serializable;

/**
 * 大屏幕入场安检欢迎信息
 */
@SuppressWarnings("serial")
public class ScreenWelcomeInfo implements Serializable {

	//会员ID
	private int memID = 0;
	//会员昵称
	private String memNickName = "";
	//会员姓名
	private String memName = "";
	
	public int getMemID() {
		return memID;
	}
	public void setMemID(int memID) {
		this.memID = memID;
	}
	public String getMemNickName() {
		return memNickName;
	}
	public void setMemNickName(String memNickName) {
		this.memNickName = memNickName;
	}
	public String getMemName() {
		return memName;
	}
	public void setMemName(String memName) {
		this.memName = memName;
	}
	public ScreenWelcomeInfo(int memID, String memName) {
		super();
		this.memID = memID;
		this.memName = memName;
	}
	public ScreenWelcomeInfo() {
		super();
	}
}
