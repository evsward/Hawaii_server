package com.evsward.server.test.netty;


import org.junit.Test;

import com.evsward.server.protobuf.WptMessage.WptAckMessage;
import com.evsward.server.protobuf.WptMessage.WptReqMessage;

public class DevTcpTestCase extends BaseTcpTestCase{

	@Test
	public void testReq()throws Exception{
		WptReqMessage.Builder hiReqMsg = WptReqMessage.newBuilder();
		hiReqMsg.setJsonReqMsg("{\"IMEI\":\"1234567890\"}");
		WptAckMessage hiAckMsg = requestWithAck(hiReqMsg.build(), 501, WptAckMessage.class);
		logger.info(hiAckMsg.getJsonAckMsg());
		
	}
}
