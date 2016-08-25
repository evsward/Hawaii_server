package com.evsward.server.service;

import com.dance.core.service.BaseService;
import com.evsward.server.vo.Employee;

public interface EmployeeService extends BaseService<Employee, Integer> {

	public Employee getEmployee(String empUuid)throws Exception;
}
