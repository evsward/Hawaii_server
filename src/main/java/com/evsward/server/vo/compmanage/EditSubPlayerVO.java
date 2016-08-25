package com.evsward.server.vo.compmanage;

import java.io.Serializable;

/**
 * 编辑扣减选手数量返回VO
 */
@SuppressWarnings("serial")
public class EditSubPlayerVO implements Serializable {

	private int compID;
	private int totalRegedPlayer;
	private int totalRegedPlayerEdit;
	
	public EditSubPlayerVO() {
		super();
	}
	public EditSubPlayerVO(int compID,
			int totalRegedPlayer, int totalRegedPlayerEdit) {
		super();
		this.compID = compID;
		this.totalRegedPlayer = totalRegedPlayer;
		this.totalRegedPlayerEdit = totalRegedPlayerEdit;
	}
	public int getCompID() {
		return compID;
	}
	public void setCompID(int compID) {
		this.compID = compID;
	}
	public int getTotalRegedPlayer() {
		return totalRegedPlayer;
	}
	public void setTotalRegedPlayer(int totalRegedPlayer) {
		this.totalRegedPlayer = totalRegedPlayer;
	}
	public int getTotalRegedPlayerEdit() {
		return totalRegedPlayerEdit;
	}
	public void setTotalRegedPlayerEdit(int totalRegedPlayerEdit) {
		this.totalRegedPlayerEdit = totalRegedPlayerEdit;
	}
}
