package com.evsward.server.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.dance.core.orm.mybatis.MyBatisDaoImpl;
import com.evsward.server.dao.RoleDao;
import com.evsward.server.vo.Role;

@Component("roleDao")
public class RoleDaoImpl extends MyBatisDaoImpl<Role, Integer> implements RoleDao{

	public List<Role> getRolesByempID(int empID, int sysType) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("empID", empID);
		param.put("sysType", sysType);
		return (List<Role>)this.find("getEnableRolesByempID", param);
	}

}
