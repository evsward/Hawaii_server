package com.evsward.server.dao.impl;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Component;

import com.dance.core.orm.mybatis.MyBatisDaoImpl;
import com.evsward.server.dao.MsgPushFuncDao;
import com.evsward.server.vo.tcpmsg.MsgPushFunction;

@Component("msgPushFuncDao")
public class MsgPushFuncDaoImpl extends
		MyBatisDaoImpl<MsgPushFunction, Integer> implements MsgPushFuncDao {

	/**
	 * 获取所有出发消息推送的功能
	 * @return
	 * @throws Exception
	 */
	public List<MsgPushFunction> getAllMsgPushFunctionList()throws Exception{
		List<MsgPushFunction> list = this.find("findAll", null);
		if(list == null){
			return Collections.emptyList();
		}
		return list;
	}
}
