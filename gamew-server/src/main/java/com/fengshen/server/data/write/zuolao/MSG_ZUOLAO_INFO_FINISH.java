package com.fengshen.server.data.write.zuolao;

import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

public class MSG_ZUOLAO_INFO_FINISH extends BaseWrite<Object>{

	@Override
	protected void writeO(ByteBuf buff, Object object) {
		
	}

	@Override
	public int cmd() {
		return 0xB0B1;
	}

}
