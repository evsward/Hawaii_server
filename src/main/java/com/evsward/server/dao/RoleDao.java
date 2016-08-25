package com.evsward.server.dao;

import java.util.List;

import com.dance.core.orm.BaseDao;
import com.evsward.server.vo.Role;

public interface RoleDao extends BaseDao<Role, Integer>{

	/**
	 * 获取账号关联的角色信息
	 * @param empID
	 * @param sysType
	 * @return
	 * @throws Exception
	 */
	public List<Role> getRolesByempID(int empID, int sysType)throws Exception;
}
