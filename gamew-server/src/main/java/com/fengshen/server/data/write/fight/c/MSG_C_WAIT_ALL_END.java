package com.fengshen.server.data.write.fight.c;

import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

public class MSG_C_WAIT_ALL_END extends BaseWrite<Object> {

	@Override
	protected void writeO(ByteBuf buff, Object object) {
		
	}

	@Override
	public int cmd() {
		return 0x0DC5;
	}

}
