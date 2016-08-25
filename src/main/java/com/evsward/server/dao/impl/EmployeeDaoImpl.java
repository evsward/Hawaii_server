package com.evsward.server.dao.impl;

import org.springframework.stereotype.Component;

import com.dance.core.orm.mybatis.MyBatisDaoImpl;
import com.evsward.server.dao.EmployeeDao;
import com.evsward.server.vo.Employee;

@Component("employeeDao")
public class EmployeeDaoImpl extends MyBatisDaoImpl<Employee, Integer> implements EmployeeDao{

	public Employee getEmployeePrivsByUuid(String uuid)throws Exception{
		Employee emp = (Employee)this.get("getEnableEmployeeByempUUID", uuid);
		return emp;
	}
}
