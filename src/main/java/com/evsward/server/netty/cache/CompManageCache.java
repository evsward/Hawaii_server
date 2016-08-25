package com.evsward.server.netty.cache;

import org.jboss.netty.channel.Channel;

/**
 * 比赛管理，channel缓存
 */
public class CompManageCache extends HIChannelCache {

	private int compID;//比赛ID

	public int getCompID() {
		return compID;
	}

	public void setCompID(int compID) {
		this.compID = compID;
	}
	public CompManageCache(int compID, Channel channel, int commandId, String imei, String clientIp) {
		super();
		this.setCompID(compID);
		this.setChannel(channel);
		this.setChannelId(channel.getId());
		this.setCommandId(commandId);
		this.setImei(imei);
		this.setClientIp(clientIp);
	}

	public CompManageCache() {
		super();
	}
}
