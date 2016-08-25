package com.evsward.server.dao;

import java.util.List;

import com.dance.core.orm.BaseDao;
import com.evsward.server.vo.tcpmsg.MsgPushFunction;

public interface MsgPushFuncDao extends BaseDao<MsgPushFunction, Integer> {

	/**
	 * 获取所有出发消息推送的功能
	 * @return
	 * @throws Exception
	 */
	public List<MsgPushFunction> getAllMsgPushFunctionList()throws Exception;
}
