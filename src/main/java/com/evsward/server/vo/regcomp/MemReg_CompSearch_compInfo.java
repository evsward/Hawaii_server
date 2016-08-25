package com.evsward.server.vo.regcomp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.evsward.server.vo.CompetitionInfo;
import com.evsward.server.vo.CompetitionMember;


/**
 * 
 */
@SuppressWarnings("serial")
public class MemReg_CompSearch_compInfo implements Serializable{
	
	public interface REGBUTSTATE{
		public static final int REGBUTENABLE = 1;//1可以报名
		public static final int REGBUTDISABLE = 0;//0不可以报名
	}
	private int compID;
	private int regFee;//报名费
	private String dateNoTime;
	private String time;
	private String compName;
	private int compState;
	private String compStateShow = "";
	private int mcState = CompetitionMember.MemCompState.NOREG;
	private String mcStateShow = "";
	private int regCount;
	private List<RunCompSeatInfo> runCompSeatInfoList = new ArrayList<RunCompSeatInfo>();
	private int regBut = REGBUTSTATE.REGBUTDISABLE;
	/**
	 * 获取会员比赛状态中文描述
	 * @return
	 */
	public String getMcStateShow() {
		switch(mcState){
		case CompetitionMember.MemCompState.NOREG:
			mcStateShow = CompetitionMember.MemCompState.NOREGSHOW;
			break;
		case CompetitionMember.MemCompState.REGED:
			mcStateShow = CompetitionMember.MemCompState.REGEDSHOW;
			break;
		case CompetitionMember.MemCompState.ADVAN:
			mcStateShow = CompetitionMember.MemCompState.ADVANSHOW;
			break;
		case CompetitionMember.MemCompState.OUT:
			mcStateShow = CompetitionMember.MemCompState.OUTSHOW;
			break;
		case CompetitionMember.MemCompState.DEL:
			mcStateShow = CompetitionMember.MemCompState.DELSHOW;
			break;
		}
		return mcStateShow;
	}
	public String getCompStateShow() {
		switch(compState){
		case CompetitionInfo.COMPSTATE.STATE_DEL:
			compStateShow = CompetitionInfo.COMPSTATE.STATE_DELSHOW;
			break;
		case CompetitionInfo.COMPSTATE.STATE_END:
			compStateShow = CompetitionInfo.COMPSTATE.STATE_ENDSHOW;
			break;
		case CompetitionInfo.COMPSTATE.STATE_NOREG:
			compStateShow = CompetitionInfo.COMPSTATE.STATE_NOREGSHOW;
			break;
		case CompetitionInfo.COMPSTATE.STATE_REGEND_NOBEGIN:
			compStateShow = CompetitionInfo.COMPSTATE.STATE_REGEND_NOBEGINSHOW;
			break;
		case CompetitionInfo.COMPSTATE.STATE_REGING_BEGINED:
			compStateShow = CompetitionInfo.COMPSTATE.STATE_REGING_BEGINEDSHOW;
			break;
		case CompetitionInfo.COMPSTATE.STATE_REGING_NOBEGIN:
			compStateShow = CompetitionInfo.COMPSTATE.STATE_REGING_NOBEGINSHOW;
			break;
		case CompetitionInfo.COMPSTATE.STATE_RUNNING:
			compStateShow = CompetitionInfo.COMPSTATE.STATE_RUNNINGSHOW;
			break;
		}
		return compStateShow;
	}
	public int getCompID() {
		return compID;
	}
	public void setCompID(int compID) {
		this.compID = compID;
	}
	public int getRegFee() {
		return regFee;
	}
	public void setRegFee(int regFee) {
		this.regFee = regFee;
	}
	public String getDateNoTime() {
		return dateNoTime;
	}
	public void setDateNoTime(String dateNoTime) {
		this.dateNoTime = dateNoTime;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getCompName() {
		return compName;
	}
	public void setCompName(String compName) {
		this.compName = compName;
	}
	public int getCompState() {
		return compState;
	}
	public void setCompState(int compState) {
		this.compState = compState;
	}
	public int getMcState() {
		return mcState;
	}
	public void setMcState(int mcState) {
		this.mcState = mcState;
	}
	public int getRegCount() {
		return regCount;
	}
	public void setRegCount(int regCount) {
		this.regCount = regCount;
	}
	public List<RunCompSeatInfo> getRunCompSeatInfoList() {
		return runCompSeatInfoList;
	}
	public void setRunCompSeatInfoList(List<RunCompSeatInfo> runCompSeatInfoList) {
		this.runCompSeatInfoList = runCompSeatInfoList;
	}
	public int getRegBut() {
		return regBut;
	}
	public void setRegBut(int regBut) {
		this.regBut = regBut;
	}
	public MemReg_CompSearch_compInfo(int compID, int regFee, String time, String compName,
			int compState) {
		super();
		this.compID = compID;
		this.regFee = regFee;
		this.time = time;
		this.compName = compName;
		this.compState = compState;
	}
	public MemReg_CompSearch_compInfo() {
		super();
	}
}
