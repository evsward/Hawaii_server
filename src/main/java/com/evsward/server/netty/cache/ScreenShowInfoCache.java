package com.evsward.server.netty.cache;

import org.jboss.netty.channel.Channel;

public class ScreenShowInfoCache extends HIChannelCache{

	public ScreenShowInfoCache() {
		super();
	}

	public ScreenShowInfoCache(Channel channel, int commandId, String imei, String clientIp) {
		this.setChannel(channel);
		this.setChannelId(channel.getId());
		this.setCommandId(commandId);
		this.setImei(imei);
		this.setClientIp(clientIp);
	}
}
