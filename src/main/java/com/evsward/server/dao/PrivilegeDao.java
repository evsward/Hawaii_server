package com.evsward.server.dao;

import java.util.List;

import com.dance.core.orm.BaseDao;
import com.evsward.server.vo.Privilege;

public interface PrivilegeDao extends BaseDao<Privilege, Integer>{

	/**
	 * 获取角色关联的权限信息
	 * @param roleID
	 * @return
	 * @throws Exception
	 */
	public List<Privilege> getPrivilegesByroleID(int roleID)throws Exception;
}
