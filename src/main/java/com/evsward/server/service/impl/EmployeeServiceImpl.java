package com.evsward.server.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.dance.core.orm.BaseDao;
import com.dance.core.service.BaseServiceImpl;
import com.evsward.server.dao.EmployeeDao;
import com.evsward.server.dao.PrivilegeDao;
import com.evsward.server.dao.RoleDao;
import com.evsward.server.service.EmployeeService;
import com.evsward.server.vo.Employee;
import com.evsward.server.vo.Privilege;
import com.evsward.server.vo.Role;

@Component("employeeService")
public class EmployeeServiceImpl extends BaseServiceImpl<Employee, Integer> implements EmployeeService{

	@Resource
	private EmployeeDao empDao;
	@Resource
	private RoleDao roleDao;
	@Resource
	private PrivilegeDao privDao;
	
	@Override
	public BaseDao<Employee, Integer> getDao() {
		return empDao;
	}

	public Employee getEmployee(String empUuid) throws Exception {
		Employee emp = this.empDao.getEmployeePrivsByUuid(empUuid);
		if(emp != null){
			List<Role> roles = this.roleDao.getRolesByempID(emp.getEmpID(), emp.getSysType());
			if(roles != null && roles.size() > 0){
				int rolesSize = roles.size();
				for (int j = 0; j < rolesSize; j++) {
					Role role = roles.get(j);
					List<Privilege> privileges = this.privDao.getPrivilegesByroleID(role.getRoleID());
					role.setPrivileges(privileges);
				}
				emp.setRoles(roles);
			}
		}
		return emp;
	}

}
