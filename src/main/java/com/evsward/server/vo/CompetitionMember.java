package com.evsward.server.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * 会员比赛信息
 */
@SuppressWarnings("serial")
public class CompetitionMember implements Serializable{

	public interface MemCompState{
		/** 未报名 */
		public static final int NOREG = -1001;
		public static final String NOREGSHOW = "未报名";
		/** 无效 */
		public static final int INVALID = -1000;
		/** 已删除 */
		public static final int DEL = -1;
		public static final String DELSHOW = "已删除";
		/** 淘汰 */
		public static final int OUT = 0;
		public static final String OUTSHOW = "已淘汰";
		/** 已报名 */
		public static final int REGED = 1;
		public static final String REGEDSHOW = "已报名";
		/** 晋级 */
		public static final int ADVAN = 2;
		public static final String ADVANSHOW = "已晋级";
	}
	
	private int id;
	private int memID;
	private int compID;
	private String compName;
	//会员参赛状态
	private int mcState;
	//购买手数（报名次数）
	private int regCount = 1;
	//比赛状态
	private int compState;
	private int tableNO;
	private int seatNO;
	private int chip;
	private int sysType;
	private Date compStartTime;
	private Date regTime;
	private Date outTime;
	
	public int getTableNO() {
		return tableNO;
	}
	public void setTableNO(int tableNO) {
		this.tableNO = tableNO;
	}
	public int getSeatNO() {
		return seatNO;
	}
	public void setSeatNO(int seatNO) {
		this.seatNO = seatNO;
	}
	public int getChip() {
		return chip;
	}
	public void setChip(int chip) {
		this.chip = chip;
	}
	public int getSysType() {
		return sysType;
	}
	public void setSysType(int sysType) {
		this.sysType = sysType;
	}
	public Date getOutTime() {
		return outTime;
	}
	public void setOutTime(Date outTime) {
		this.outTime = outTime;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getCompID() {
		return compID;
	}
	public void setCompID(int compID) {
		this.compID = compID;
	}
	public int getMemID() {
		return memID;
	}
	public void setMemID(int memID) {
		this.memID = memID;
	}
	public String getCompName() {
		return compName;
	}
	public void setCompName(String compName) {
		this.compName = compName;
	}
	public int getMcState() {
		return mcState;
	}
	public void setMcState(int mcState) {
		this.mcState = mcState;
	}
	public Date getCompStartTime() {
		return compStartTime;
	}
	public void setCompStartTime(Date compStartTime) {
		this.compStartTime = compStartTime;
	}
	public int getRegCount() {
		return regCount;
	}
	public void setRegCount(int regCount) {
		this.regCount = regCount;
	}
	public int getCompState() {
		return compState;
	}
	public void setCompState(int compState) {
		this.compState = compState;
	}
	public Date getRegTime() {
		return regTime;
	}
	public void setRegTime(Date regTime) {
		this.regTime = regTime;
	}
	public CompetitionMember() {
		super();
	}
	public CompetitionMember(int memID, int compID, int mcState, int tableNO, int seatNO,
			int chip, int sysType, Date regTime) {
		super();
		this.memID = memID;
		this.compID = compID;
		this.mcState = mcState;
		this.tableNO = tableNO;
		this.seatNO = seatNO;
		this.chip = chip;
		this.sysType = sysType;
		this.regTime = regTime;
	}
}
