package com.evsward.server.vo.compmanage;

import java.io.Serializable;

/**
 * 比赛管理--座位--牌桌的座位信息
 */
@SuppressWarnings("serial")
public class CompManSeatInfo implements Serializable {

	private int id;//MEMCOMPINFO表的主键
	private int seatNO;
	private int memID;
	private String cardNO;
	private String memImage;
	
	public CompManSeatInfo() {
		super();
	}
	public CompManSeatInfo(int id, int seatNO, int memID, String cardNO,
			String memImage) {
		super();
		this.id = id;
		this.seatNO = seatNO;
		this.memID = memID;
		this.cardNO = cardNO;
		this.memImage = memImage;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getSeatNO() {
		return seatNO;
	}
	public void setSeatNO(int seatNO) {
		this.seatNO = seatNO;
	}
	public int getMemID() {
		return memID;
	}
	public void setMemID(int memID) {
		this.memID = memID;
	}
	public String getCardNO() {
		return cardNO;
	}
	public void setCardNO(String cardNO) {
		this.cardNO = cardNO;
	}
	public String getMemImage() {
		return memImage;
	}
	public void setMemImage(String memImage) {
		this.memImage = memImage;
	}
}
