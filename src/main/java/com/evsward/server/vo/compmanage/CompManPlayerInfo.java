package com.evsward.server.vo.compmanage;

import java.io.Serializable;
import java.util.List;

import com.evsward.server.vo.CompMemInfo;

/**
 * 比赛管理--玩家
 */
@SuppressWarnings("serial")
public class CompManPlayerInfo implements Serializable {

	private int totalRegedPlayer;
	private int totalRegedPlayerEdit;
	private int totalChip;
	private int totalChipEdit;
	private List<CompMemInfo> playerInfos = null;
	
	public int getTotalRegedPlayerEdit() {
		return totalRegedPlayerEdit;
	}

	public void setTotalRegedPlayerEdit(int totalRegedPlayerEdit) {
		this.totalRegedPlayerEdit = totalRegedPlayerEdit;
	}

	public int getTotalChipEdit() {
		return totalChipEdit;
	}

	public void setTotalChipEdit(int totalChipEdit) {
		this.totalChipEdit = totalChipEdit;
	}

	public int getTotalRegedPlayer() {
		return totalRegedPlayer;
	}

	public void setTotalRegedPlayer(int totalRegedPlayer) {
		this.totalRegedPlayer = totalRegedPlayer;
	}

	public int getTotalChip() {
		return totalChip;
	}

	public void setTotalChip(int totalChip) {
		this.totalChip = totalChip;
	}

	public List<CompMemInfo> getPlayerInfos() {
		return playerInfos;
	}

	public void setPlayerInfos(List<CompMemInfo> playerInfos) {
		this.playerInfos = playerInfos;
	}

	public CompManPlayerInfo(int totalRegedPlayer, int totalRegedPlayerEdit,
			int totalChip, int totalChipEdit, List<CompMemInfo> playerInfos) {
		super();
		this.totalRegedPlayer = totalRegedPlayer;
		this.totalRegedPlayerEdit = totalRegedPlayerEdit;
		this.totalChip = totalChip;
		this.totalChipEdit = totalChipEdit;
		this.playerInfos = playerInfos;
	}

	public CompManPlayerInfo() {
		super();
	}
	
}
