package com.evsward.server.facade.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.evsward.server.facade.EmployeeFacade;
import com.evsward.server.service.EmployeeService;
import com.evsward.server.util.RspCodeValue;
import com.evsward.server.util.HIUtil;
import com.evsward.server.vo.Employee;

@Component("empFacade")
public class EmployeeFacadeImpl implements EmployeeFacade {

	private static Logger logger = LoggerFactory.getLogger(EmployeeFacadeImpl.class);
	
	@Resource
	private EmployeeService empService;
	
	public String uuidEmpLoginResponse(String empUuid){
		Employee emp = null;
		String jsonStr = "";
		try {
			emp = this.empService.getEmployee(empUuid);
		} catch (Exception e) {
			logger.error("employee uuid login select employee error", e);
			return HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$101.getRspCode(), RspCodeValue.$101.getMsg()));
		}
		
		if(emp == null){
			return HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$102.getRspCode(), RspCodeValue.$102.getMsg()));
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("employee", emp);
		jsonStr = HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$1.getRspCode(), RspCodeValue.$1.getMsg(), map));
		return jsonStr;
	}
	
	public String addEmployee(String empUuid){
		String jsonStr = "";
		if(empUuid.length() < 12){
			jsonStr = HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$106.getRspCode(), RspCodeValue.$106.getMsg()));
			return jsonStr;
		}
		Employee emp = new Employee();
		emp.setEmpUUID(empUuid);
		emp.setNfcIdLong(HIUtil.convertHex2Long(empUuid));
		emp.setEmpName("初始化姓名");
		emp.setCreateTime(new Date());
		try{
			this.empService.insert(emp);
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			jsonStr = HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$103.getRspCode(), RspCodeValue.$103.getMsg()));
			return jsonStr;
		}
		jsonStr = HIUtil.toJsonNormalField(HIUtil.createJsonResInitMap(RspCodeValue.$1.getRspCode(), RspCodeValue.$1.getMsg()));
		return jsonStr;
	}
}
