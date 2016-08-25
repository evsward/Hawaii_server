package com.evsward.server.vo;

import java.io.Serializable;

public class RoleEmpRelation implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int id;
	private int roleID;
	private int empID;
	public int getRoleID() {
		return roleID;
	}
	public void setRoleID(int roleID) {
		this.roleID = roleID;
	}
	public int getEmpID() {
		return empID;
	}
	public void setEmpID(int empID) {
		this.empID = empID;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public RoleEmpRelation(int id, int roleID, int empID) {
		super();
		this.id = id;
		this.roleID = roleID;
		this.empID = empID;
	}
	public RoleEmpRelation() {
		super();
	}
	@Override
	public String toString() {
		return "RoleEmpRelation [id=" + id + ", roleID=" + roleID + ", empID=" + empID + "]";
	}
	
}
