package com.fengshen.server.data.write.leitai;

import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

public class MSG_COMPETE_TOURNAMENT_INFO extends BaseWrite<Object> {

	@Override
	protected void writeO(ByteBuf buff, Object object) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int cmd() {
		return 0;
	}

}
