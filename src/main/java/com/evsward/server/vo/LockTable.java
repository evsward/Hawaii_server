package com.evsward.server.vo;

import java.io.Serializable;

@SuppressWarnings("serial")
public class LockTable implements Serializable {
	
	public interface LOCKTYPE{
		public final static int PRIZELOCK = 0;
		public final static int SEATLOCK = 1;
	}

	private int lock;
	private int lockType;//主键，锁类型：0、奖励锁；1、座位锁（报名、释放牌桌、爆桌、平衡）；

	public int getLockType() {
		return lockType;
	}

	public void setLockType(int lockType) {
		this.lockType = lockType;
	}

	public int getLock() {
		return lock;
	}

	public void setLock(int lock) {
		this.lock = lock;
	}
}
