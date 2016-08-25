package com.evsward.server.netty.cache;

import org.jboss.netty.channel.Channel;

/**
 * pad端，大屏幕设备管理，channel缓存
 */
public class DevManageCache extends HIChannelCache {

	public DevManageCache() {
		super();
	}

	public DevManageCache(Channel channel, int commandId, String imei, String clientIp) {
		this.setChannel(channel);
		this.setChannelId(channel.getId());
		this.setCommandId(commandId);
		this.setImei(imei);
		this.setClientIp(clientIp);
	}
}
