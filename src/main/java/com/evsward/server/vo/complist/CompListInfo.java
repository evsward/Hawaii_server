package com.evsward.server.vo.complist;

import java.io.Serializable;

import com.evsward.server.vo.CompetitionInfo;

@SuppressWarnings("serial")
public class CompListInfo implements Serializable {

	private String compName;
	private String time;
	private int compState;
	private String compStateShow;
	private int regFee;
	private int serviceFee;
	private int beginChip;
	private int curRank;
	private int curType;
	private int reEntry;
	private int compID;
	private int amountUnit;
	
	public int getAmountUnit() {
		return amountUnit;
	}
	public void setAmountUnit(int amountUnit) {
		this.amountUnit = amountUnit;
	}
	public int getCurType() {
		return curType;
	}
	public void setCurType(int curType) {
		this.curType = curType;
	}
	public int getCompID() {
		return compID;
	}
	public void setCompID(int compID) {
		this.compID = compID;
	}
	public int getReEntry() {
		return reEntry;
	}
	public void setReEntry(int reEntry) {
		this.reEntry = reEntry;
	}
	public String getCompStateShow() {
		switch(compState){
			case CompetitionInfo.COMPSTATE.STATE_DEL:
				this.compStateShow = CompetitionInfo.COMPSTATE.STATE_DELSHOW;
				break;
			case CompetitionInfo.COMPSTATE.STATE_NOREG:
				this.compStateShow = CompetitionInfo.COMPSTATE.STATE_NOREGSHOW;
				break;
			case CompetitionInfo.COMPSTATE.STATE_REGING_NOBEGIN:
				this.compStateShow = CompetitionInfo.COMPSTATE.STATE_REGING_NOBEGINSHOW;
				break;
			case CompetitionInfo.COMPSTATE.STATE_REGING_BEGINED:
				this.compStateShow = CompetitionInfo.COMPSTATE.STATE_REGING_BEGINEDSHOW;
				break;
			case CompetitionInfo.COMPSTATE.STATE_REGEND_NOBEGIN:
				this.compStateShow = CompetitionInfo.COMPSTATE.STATE_REGEND_NOBEGINSHOW;
				break;
			case CompetitionInfo.COMPSTATE.STATE_RUNNING:
				this.compStateShow = CompetitionInfo.COMPSTATE.STATE_RUNNINGSHOW;
				break;
			case CompetitionInfo.COMPSTATE.STATE_END:
				this.compStateShow = CompetitionInfo.COMPSTATE.STATE_ENDSHOW;
				break;
		}
		return compStateShow;
	}
	public void setCompStateShow(String compStateShow) {
		this.compStateShow = compStateShow;
	}
	public String getCompName() {
		return compName;
	}
	public void setCompName(String compName) {
		this.compName = compName;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public int getCompState() {
		return compState;
	}
	public void setCompState(int compState) {
		this.compState = compState;
	}
	public int getRegFee() {
		return regFee;
	}
	public void setRegFee(int regFee) {
		this.regFee = regFee;
	}
	public int getServiceFee() {
		return serviceFee;
	}
	public void setServiceFee(int serviceFee) {
		this.serviceFee = serviceFee;
	}
	public int getBeginChip() {
		return beginChip;
	}
	public void setBeginChip(int beginChip) {
		this.beginChip = beginChip;
	}
	public int getCurRank() {
		return curRank;
	}
	public void setCurRank(int curRank) {
		this.curRank = curRank;
	}
	public CompListInfo() {
		super();
	}
	public CompListInfo(int compID, String compName, String time, int compState,
			int regFee, int serviceFee, int beginChip, int curRank, int curType, int reEntry, int amountUnit) {
		super();
		this.compID = compID;
		this.compName = compName;
		this.time = time;
		this.compState = compState;
		this.regFee = regFee;
		this.serviceFee = serviceFee;
		this.beginChip = beginChip;
		this.curRank = curRank;
		this.curType = curType;
		this.reEntry = reEntry;
		this.amountUnit = amountUnit;
	}
}
