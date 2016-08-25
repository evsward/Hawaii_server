package com.evsward.server.dao;

import com.dance.core.orm.BaseDao;
import com.evsward.server.vo.Employee;

public interface EmployeeDao extends BaseDao<Employee, Integer>{

	public Employee getEmployeePrivsByUuid(String uuid)throws Exception;
}
