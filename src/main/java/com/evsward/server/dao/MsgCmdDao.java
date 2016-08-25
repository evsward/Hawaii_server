package com.evsward.server.dao;

import java.util.List;

import com.dance.core.orm.BaseDao;
import com.evsward.server.vo.tcpmsg.MsgCmd;

public interface MsgCmdDao extends BaseDao<MsgCmd, Integer> {

	/**
	 * 获取所有消息
	 * @return
	 * @throws Exception
	 */
	public List<MsgCmd> getAllMsgCmdList()throws Exception;
	
	/**
	 * 根据功能查询关联的推送消息
	 * @param funcID
	 * @return
	 * @throws Exception
	 */
	public List<MsgCmd> getMsgCmdListByFuncID(int funcID)throws Exception;
}
