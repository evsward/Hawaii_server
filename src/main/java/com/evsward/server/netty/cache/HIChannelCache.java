package com.evsward.server.netty.cache;

import org.jboss.netty.channel.Channel;

/**
 * 服务端缓存的和客户端的socket连接
 */
public abstract class HIChannelCache {

	private Channel channel;//连接通道
	private int channelId;//通道标示
	private int commandId;//消息号
	private String imei;//客户端设备号
	private String clientIp;
	
	public String getClientIp() {
		return clientIp;
	}
	public void setClientIp(String clientIp) {
		this.clientIp = clientIp;
	}
	public int getChannelId() {
		return channelId;
	}
	public void setChannelId(int channelId) {
		this.channelId = channelId;
	}
	public Channel getChannel() {
		return channel;
	}
	public void setChannel(Channel channel) {
		this.channel = channel;
	}
	public int getCommandId() {
		return commandId;
	}
	public void setCommandId(int commandId) {
		this.commandId = commandId;
	}
	public String getImei() {
		return imei;
	}
	public void setImei(String imei) {
		this.imei = imei;
	}
	public HIChannelCache() {
		super();
	}
	@Override
	public String toString() {
		return "HIChannelCache [clientIp=" + clientIp + ", channelId="
				+ channelId + ", commandId=" + commandId + ", imei=" + imei
				+ "]";
	}
	
//	public abstract int getCompID();
}
