package com.evsward.server.dao;

import java.util.List;

import com.dance.core.orm.BaseDao;
import com.evsward.server.vo.MemberInfo;

public interface MemberDao extends BaseDao<MemberInfo, Integer> {

	/**
	 * 初始化会员信息
	 * @param memInfo
	 * @return
	 * @throws Exception
	 */
	public void initMember(MemberInfo memInfo)throws Exception;
	
	/**
	 * 精确查找会员，查询会员基本信息，memID，uuidLong，cardNO，memIdentNO，四者中至少一个条件
	 * @param memInfo
	 * @return
	 * @throws Exception
	 */
	public MemberInfo getExactMemInfo(MemberInfo memInfo)throws Exception;
	
	/**
	 * 新建会员第二步，完善新建会员信息
	 * @param memInfo
	 * @return
	 * @throws Exception
	 */
	public int updateMemInfo(MemberInfo memInfo)throws Exception;
	
	/**
	 * 模糊查询用户信息，匹配条件：cardno ， memname ， memidentno ， memmobile其中任意一个
	 * @param vagueParam
	 * @return
	 * @throws Exception
	 */
	public List<MemberInfo> vagueFindMembers(String vagueParam)throws Exception;
}
