package com.evsward.server.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dance.core.utils.encode.JsonBinder;

@SuppressWarnings("serial")
public class JsonResBase implements Serializable {

	public static void main(String[] args) {
		String jsonStr;
		JsonResBase jsonRes = new JsonResBase();
		Employee emp = new Employee(1, "1234567890", 1234567890l, "刘斌", 1, "15901512344", "", 1, "410602198312062016", 1, "", new Date(), null, null);
		Role role = new Role(1, "开发-刘斌", 1, "开发-刘斌DESC", "开发-刘斌SHOW", 1, new Date(), null, null);
		Privilege priv1 = new Privilege(1, "新建会员", "", 1, 0, "新建会员SHOW", "新建会员DESC", "1234567890", 0, 1, new Date(), null, null);
		Privilege priv2 = new Privilege(2, "会员查询", "", 1, 0, "会员查询SHOW", "会员查询DESC", "1234567890", 0, 1, new Date(), null, null);
		Privilege priv3 = new Privilege(3, "会员报名", "", 1, 0, "会员报名SHOW", "会员报名DESC", "1234567890", 0, 1, new Date(), null, null);
		Privilege priv4 = new Privilege(4, "比赛列表", "", 1, 0, "比赛列表SHOW", "比赛列表DESC", "1234567890", 0, 1, new Date(), null, null);
		List<Role> roles = new ArrayList<Role>();
		List<Privilege> privileges = new ArrayList<Privilege>();
		privileges.add(priv1);
		privileges.add(priv2);
		privileges.add(priv3);
		privileges.add(priv4);
		role.setPrivileges(privileges);
		roles.add(role);
		emp.setRoles(roles);
		jsonRes.setObj(emp);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("msg", "ok");
		map.put("rspCode", 200);
		map.put("employee", emp);
		
		JsonBinder jsonBinder = JsonBinder.buildNonNullBinder();
		jsonStr = jsonBinder.toJson(map);
		System.out.println(jsonStr);
	}
	
	private String msg = "ok";
	private int rspCode = 200;
	private Object obj;
	
	public Object getObj() {
		return obj;
	}
	public void setObj(Object obj) {
		this.obj = obj;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public int getRspCode() {
		return rspCode;
	}
	public void setRspCode(int rspCode) {
		this.rspCode = rspCode;
	}
}
