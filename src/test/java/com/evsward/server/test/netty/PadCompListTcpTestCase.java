package com.evsward.server.test.netty;

import org.junit.Test;

import com.evsward.server.protobuf.WptMessage.WptAckMessage;
import com.evsward.server.protobuf.WptMessage.WptReqMessage;

public class PadCompListTcpTestCase extends BaseTcpTestCase {

	@Test
	public void testReq()throws Exception{
		WptReqMessage.Builder hiReqMsg = WptReqMessage.newBuilder();
		hiReqMsg.setJsonReqMsg("{\"IMEI\":\"1234567890\",\"sysType\":1}");
		WptAckMessage hiAckMsg = requestWithAck(hiReqMsg.build(), 502, WptAckMessage.class);
		logger.info(hiAckMsg.getJsonAckMsg());
		
	}
}
