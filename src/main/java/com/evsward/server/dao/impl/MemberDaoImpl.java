package com.evsward.server.dao.impl;


import java.util.List;

import org.springframework.stereotype.Component;

import com.dance.core.orm.mybatis.MyBatisDaoImpl;
import com.evsward.server.dao.MemberDao;
import com.evsward.server.vo.MemberInfo;

@Component("memDao")
public class MemberDaoImpl extends MyBatisDaoImpl<MemberInfo, Integer> implements MemberDao {


	public void initMember(MemberInfo memInfo) throws Exception {
		this.insert("insertInitMem", memInfo);
	}

	public MemberInfo getExactMemInfo(MemberInfo memInfo) throws Exception {
		memInfo = (MemberInfo)this.get("getExactMemInfo", memInfo);
		return memInfo;
	}

	/**
	 * 更新会员基本信息
	 * @param memInfo
	 * @return
	 * @throws Exception
	 */
	public int updateMemInfo(MemberInfo memInfo)throws Exception{
		return this.update("updateMemInfo", memInfo);
	}
	
	public List<MemberInfo> vagueFindMembers(String vagueParam)throws Exception{
		List<MemberInfo> members = this.find("selectVagueMemInfo", vagueParam);
		return members;
	}
}
