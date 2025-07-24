package com.fengshen.server.data.write.hunpo;

import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

public class MSG_GET_NEIDAN_BREAK_TASK_SUCC extends BaseWrite<Object> {

	@Override
	protected void writeO(ByteBuf buff, Object object) {
		
	}

	@Override
	public int cmd() {
		return 0xB182;
	}

}
