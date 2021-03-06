package com.evsward.server.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Employee implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer empID;
	private String empUUID;
	private Long nfcIdLong;
	private String empName;
	private Integer empState;
	private String empMobile;
	private String empImage;
	private Integer sysType;
	private String empIdentNo;
	private Integer empIdentType;
	private String password;
	private Date createTime;
	private Date updateTime;
	private Date delTime;
	
	private List<Role> roles;
	
	public List<Role> getRoles() {
		return roles;
	}
	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
	public Integer getEmpID() {
		return empID;
	}
	public void setEmpID(Integer empID) {
		this.empID = empID;
	}
	public String getEmpUUID() {
		return empUUID;
	}
	public void setEmpUUID(String empUUID) {
		this.empUUID = empUUID;
	}
	public Long getNfcIdLong() {
		return nfcIdLong;
	}
	public void setNfcIdLong(Long nfcIdLong) {
		this.nfcIdLong = nfcIdLong;
	}
	public String getEmpName() {
		return empName;
	}
	public void setEmpName(String empName) {
		this.empName = empName;
	}
	public Integer getEmpState() {
		return empState;
	}
	public void setEmpState(Integer empState) {
		this.empState = empState;
	}
	public String getEmpMobile() {
		return empMobile;
	}
	public void setEmpMobile(String empMobile) {
		this.empMobile = empMobile;
	}
	public String getEmpImage() {
		return empImage;
	}
	public void setEmpImage(String empImage) {
		this.empImage = empImage;
	}
	public Integer getSysType() {
		return sysType;
	}
	public void setSysType(Integer sysType) {
		this.sysType = sysType;
	}
	public String getEmpIdentNo() {
		return empIdentNo;
	}
	public void setEmpIdentNo(String empIdentNo) {
		this.empIdentNo = empIdentNo;
	}
	public Integer getEmpIdentType() {
		return empIdentType;
	}
	public void setEmpIdentType(Integer empIdentType) {
		this.empIdentType = empIdentType;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public Date getDelTime() {
		return delTime;
	}
	public void setDelTime(Date delTime) {
		this.delTime = delTime;
	}
	public Employee(Integer empID, String empUUID, Long nfcIdLong, String empName,
			Integer empState, String empMobile, String empImage, Integer sysType,
			String empIdentNo, Integer empIdentType, String password,
			Date createTime, Date updateTime, Date delTime) {
		super();
		this.empID = empID;
		this.empUUID = empUUID;
		this.nfcIdLong = nfcIdLong;
		this.empName = empName;
		this.empState = empState;
		this.empMobile = empMobile;
		this.empImage = empImage;
		this.sysType = sysType;
		this.empIdentNo = empIdentNo;
		this.empIdentType = empIdentType;
		this.password = password;
		this.createTime = createTime;
		this.updateTime = updateTime;
		this.delTime = delTime;
	}
	public Employee(Integer empID) {
		super();
		this.empID = empID;
	}
	public Employee() {
		super();
	}
	@Override
	public String toString() {
		return "Employee [empID=" + empID + ", empUUID=" + empUUID
				+ ", nfcIdLong=" + nfcIdLong + ", empName=" + empName
				+ ", empState=" + empState + ", empMobile=" + empMobile
				+ ", empImage=" + empImage + ", sysType=" + sysType
				+ ", empIdentNo=" + empIdentNo + ", empIdentType="
				+ empIdentType + ", password=" + password + ", createTime="
				+ createTime + ", updateTime=" + updateTime + ", delTime="
				+ delTime + "]";
	}
	
}
