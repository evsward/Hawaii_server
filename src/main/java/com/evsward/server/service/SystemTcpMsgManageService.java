package com.evsward.server.service;

import java.util.List;
import java.util.Map;

import com.evsward.server.vo.tcpmsg.MsgCmd;
import com.evsward.server.vo.tcpmsg.MsgPushFunction;

public interface SystemTcpMsgManageService{

	public List<MsgCmd> getAllSystemTcpMsg()throws Exception;
	
	public List<MsgPushFunction> getAllSystemTcpMsgPushFunc()throws Exception;
	
	public Map<Integer, List<MsgCmd>> getFuncMsgListMapping()throws Exception;
}
