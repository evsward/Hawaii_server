package com.evsward.server.test.netty;

import com.netty.server.test.support.tcp.AbstractTcpTestCase;

public class BaseTcpTestCase extends AbstractTcpTestCase {

	@Override
	public String getServerIp() {
		return "127.0.0.1";
	}

	@Override
	public int getServerPort() {
		return 22399;
	}

}
