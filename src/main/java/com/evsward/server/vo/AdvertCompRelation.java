package com.evsward.server.vo;

import java.io.Serializable;

public class AdvertCompRelation implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int id;
	private int advertID;
	private int compID;
	private int sysType;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getSysType() {
		return sysType;
	}
	public void setSysType(int sysType) {
		this.sysType = sysType;
	}
	public int getAdvertID() {
		return advertID;
	}
	public void setAdvertID(int advertID) {
		this.advertID = advertID;
	}
	public int getCompID() {
		return compID;
	}
	public void setCompID(int compID) {
		this.compID = compID;
	}
	
	public AdvertCompRelation(int advertID, int compID) {
		super();
		this.advertID = advertID;
		this.compID = compID;
	}
	public AdvertCompRelation(int advertID, int compID, int sysType) {
		super();
		this.advertID = advertID;
		this.compID = compID;
		this.sysType = sysType;
	}
	public AdvertCompRelation() {
		super();
	}
	@Override
	public String toString() {
		return "AdvertCompRelation [id=" + id + ", advertID=" + advertID
				+ ", compID=" + compID + ", sysType=" + sysType + "]";
	}
	
}
