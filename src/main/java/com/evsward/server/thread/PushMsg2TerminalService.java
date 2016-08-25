package com.evsward.server.thread;

import java.nio.ByteOrder;
import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingDeque;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channels;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.evsward.server.netty.cache.HIChannelCache;
import com.evsward.server.protobuf.WptMessage.WptAckMessage;
import com.evsward.server.util.Application;
import com.evsward.server.vo.tcpmsg.triggmsg.SystemBasePushTriggMsg;

/**
 * 网客户端分发消息的监控线程对象
 */

public abstract class PushMsg2TerminalService implements Runnable{
	
	private static Logger logger = LoggerFactory.getLogger(PushMsg2TerminalService.class);
	//线程名称
	protected String thrdDesc;
	//消息队列
	protected BlockingQueue<SystemBasePushTriggMsg> msgQueue = new LinkedBlockingDeque<SystemBasePushTriggMsg>();
	//<channelId, WptChannelCache>,接收该消息的客户端连接Channel
	protected ConcurrentHashMap<Integer, HIChannelCache> onlineChannelMap = new ConcurrentHashMap<Integer, HIChannelCache>();

	/**
	 * 推送线程
	 */
	@Override
	public void run(){
		logger.info(Application.TPS + "后台消息推送线程启动,"+ this.getThrdDesc() + Application.TPS);
		SystemBasePushTriggMsg pushTriggMsg = null;
		HIChannelCache hiChCache = null;
		while(true){
			try {
				pushTriggMsg = msgQueue.take();
			} catch (InterruptedException e) {
				logger.error(e.getMessage(), e);
			}
			if(pushTriggMsg == null){
				continue;
			}
			//开始处理消息
			Collection<HIChannelCache> channeCollection = onlineChannelMap.values();
			Iterator<HIChannelCache> it = channeCollection.iterator();
			while(it.hasNext()){
				hiChCache = it.next();
				if(hiChCache != null && hiChCache.getChannel().isConnected() && hiChCache.getChannel().isWritable()){
					logger.info(Application.TPS + this.thrdDesc + "正在推送" + Application.TPS);
					this.pushMsg2Terminal(pushTriggMsg, hiChCache);
				}else{
					this.onlineChannelMap.remove(hiChCache.getChannelId());
				}
			}
		}
	}
	
	public abstract void pushMsg2Terminal(SystemBasePushTriggMsg pushTriggMsg, HIChannelCache hiChCache);
	
	/**
	 * 创建响应格式消息
	 * @param hi
	 * @param ack
	 * @return
	 */
	protected ChannelBuffer encode(HIChannelCache hi, WptAckMessage.Builder ack){
		byte[] body = ack.build().toByteArray();
		ChannelBuffer result = ChannelBuffers.buffer(ByteOrder.LITTLE_ENDIAN, 12 + body.length);
		int ackCommandId = hi.getCommandId() | 0x08000000; 
		int ackBodyLength = body.length; 
		result.writeInt(ackCommandId);
		result.writeInt(ackBodyLength);
		result.writeInt(1);
		result.writeBytes(body);
		return result;
	}
	
	protected void writeMsg2Client(HIChannelCache hiChCache, ChannelBuffer cb){
		Channels.write(hiChCache.getChannel(), cb);
	}
	
	/**
	 * 获取当前缓存的消息通道集合
	 * @return
	 */
	public ConcurrentHashMap<Integer, HIChannelCache> getOnlineChannelMap(){
		return this.onlineChannelMap;
	}
	
	/**
	 * 缓存客户端和服务端的连接通道
	 * @param hiChannel
	 */
	public void cacheChannel(HIChannelCache hiChannel){
		onlineChannelMap.put(hiChannel.getChannel().getId(), hiChannel);
	}
	
	/**
	 * 从消息通道缓存中已出一个消息通道
	 * @param channelId
	 */
	public HIChannelCache removeChannel(Integer channelId){
		return onlineChannelMap.remove(channelId);
	}

	/**
	 * 放到队列里触发消息
	 * @param systemPushMsg
	 */
	public void putMsg(SystemBasePushTriggMsg pushTriggMsg){
		msgQueue.offer(pushTriggMsg);
	}
	
	public String getThrdDesc() {
		return thrdDesc;
	}

	public void setThrdDesc(String thrdDesc) {
		this.thrdDesc = thrdDesc;
	}
	
//	public void closeChannel(WptChannelCache hiCache){
//	try{
//		if(hiCache != null){
//			Channels.close(hiCache.getChannel());
//		}
//	}catch(Exception e){
//		logger.error(e.getMessage(), e);
//	}
//}
}
