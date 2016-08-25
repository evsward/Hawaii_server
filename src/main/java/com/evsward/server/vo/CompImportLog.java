package com.evsward.server.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * 进阶比赛导入记录
 */
@SuppressWarnings("serial")
public class CompImportLog implements Serializable {

	private int sourceCompID;
	private int destCompID;
	private Date importTime;
	private String empUuid;
	
	public int getSourceCompID() {
		return sourceCompID;
	}
	public void setSourceCompID(int sourceCompID) {
		this.sourceCompID = sourceCompID;
	}
	public int getDestCompID() {
		return destCompID;
	}
	public void setDestCompID(int destCompID) {
		this.destCompID = destCompID;
	}
	public Date getImportTime() {
		return importTime;
	}
	public void setImportTime(Date importTime) {
		this.importTime = importTime;
	}
	public String getEmpUuid() {
		return empUuid;
	}
	public void setEmpUuid(String empUuid) {
		this.empUuid = empUuid;
	}
	public CompImportLog() {
		super();
	}
	public CompImportLog(int sourceCompID, int destCompID, Date importTime,
			String empUuid) {
		super();
		this.sourceCompID = sourceCompID;
		this.destCompID = destCompID;
		this.importTime = importTime;
		this.empUuid = empUuid;
	}
}
