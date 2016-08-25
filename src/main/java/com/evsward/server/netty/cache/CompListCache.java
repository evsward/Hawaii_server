package com.evsward.server.netty.cache;

import org.jboss.netty.channel.Channel;

/**
 * 比赛列表管理，channel缓存
 */
public class CompListCache extends HIChannelCache {

	public CompListCache() {
		super();
	}

	public CompListCache(Channel channel, int commandId, String imei, String clientIp) {
		this.setChannel(channel);
		this.setChannelId(channel.getId());
		this.setClientIp(clientIp);
		this.setCommandId(commandId);
		this.setImei(imei);
	}
}
