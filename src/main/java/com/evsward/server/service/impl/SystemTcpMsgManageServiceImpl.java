package com.evsward.server.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.evsward.server.dao.MsgCmdDao;
import com.evsward.server.dao.MsgPushFuncDao;
import com.evsward.server.service.SystemTcpMsgManageService;
import com.evsward.server.vo.tcpmsg.MsgCmd;
import com.evsward.server.vo.tcpmsg.MsgPushFunction;

@Component("sysTcpMsgManService")
public class SystemTcpMsgManageServiceImpl implements SystemTcpMsgManageService {

	@Resource
	private MsgCmdDao msgCmdDao;
	@Resource
	private MsgPushFuncDao msgPushFuncDao;
	
	@Override
	public List<MsgCmd> getAllSystemTcpMsg() throws Exception {
		return this.msgCmdDao.getAllMsgCmdList();
	}

	@Override
	public List<MsgPushFunction> getAllSystemTcpMsgPushFunc() throws Exception {
		return this.msgPushFuncDao.getAllMsgPushFunctionList();
	}

	@Override
	public Map<Integer, List<MsgCmd>> getFuncMsgListMapping() throws Exception {
		Map<Integer, List<MsgCmd>> relationMap = new HashMap<Integer, List<MsgCmd>>();
		List<MsgPushFunction> funcList = this.msgPushFuncDao.getAllMsgPushFunctionList();
		MsgPushFunction func = null;
		for (int i = 0; i < funcList.size(); i++) {
			func = funcList.get(i);
			relationMap.put(func.getFuncID(), this.msgCmdDao.getMsgCmdListByFuncID(func.getFuncID()));
		}
		return relationMap;
	}
}
