package com.evsward.server.vo;

import java.io.Serializable;

public class RolePrivRelation implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int id;
	private int roleID;
	private int privID;
	public int getRoleID() {
		return roleID;
	}
	public void setRoleID(int roleID) {
		this.roleID = roleID;
	}
	public int getPrivID() {
		return privID;
	}
	public void setPrivID(int privID) {
		this.privID = privID;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public RolePrivRelation(int id, int roleID, int privID) {
		super();
		this.id = id;
		this.roleID = roleID;
		this.privID = privID;
	}
	public RolePrivRelation() {
		super();
	}
	@Override
	public String toString() {
		return "RolePrivRelation [id=" + id + ", roleID=" + roleID + ", privID=" + privID
				+ "]";
	}
	
}
