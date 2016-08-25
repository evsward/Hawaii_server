package com.evsward.server.dao.impl;

import java.util.List;

import org.springframework.stereotype.Component;

import com.dance.core.orm.mybatis.MyBatisDaoImpl;
import com.evsward.server.dao.PrivilegeDao;
import com.evsward.server.vo.Privilege;

@Component("privDao")
public class PrivilegeDaoImpl extends MyBatisDaoImpl<Privilege, Integer>
		implements PrivilegeDao {

	public List<Privilege> getPrivilegesByroleID(int roleID) throws Exception {
		return (List<Privilege>)this.find("getEnablePrivsByroleID", roleID);
	}
	
}
