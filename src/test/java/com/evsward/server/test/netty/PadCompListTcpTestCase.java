package com.evsward.server.test.netty;

import org.junit.Test;

import com.evsward.server.protobuf.HIMessage.HIAckMessage;
import com.evsward.server.protobuf.HIMessage.HIReqMessage;

public class PadCompListTcpTestCase extends BaseTcpTestCase {

	@Test
	public void testReq()throws Exception{
		HIReqMessage.Builder hiReqMsg = HIReqMessage.newBuilder();
		hiReqMsg.setJsonReqMsg("{\"IMEI\":\"1234567890\",\"sysType\":1}");
		HIAckMessage hiAckMsg = requestWithAck(hiReqMsg.build(), 502, HIAckMessage.class);
		logger.info(hiAckMsg.getJsonAckMsg());
		
	}
}
