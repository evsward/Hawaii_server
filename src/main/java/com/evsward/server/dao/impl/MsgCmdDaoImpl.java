package com.evsward.server.dao.impl;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Component;

import com.dance.core.orm.mybatis.MyBatisDaoImpl;
import com.evsward.server.dao.MsgCmdDao;
import com.evsward.server.vo.tcpmsg.MsgCmd;

@Component("msgCmdDao")
public class MsgCmdDaoImpl extends MyBatisDaoImpl<MsgCmd, Integer> implements
		MsgCmdDao {

	/**
	 * 获取所有消息
	 * @return
	 * @throws Exception
	 */
	public List<MsgCmd> getAllMsgCmdList()throws Exception{
		return this.find("findAll", null);
	}
	
	public List<MsgCmd> getMsgCmdListByFuncID(int funcID)throws Exception{
		List<MsgCmd> list = this.find("selectMsgCmdListByFuncID", funcID);
		if(list == null){
			return Collections.emptyList();
		}
		return list;
	}
}
