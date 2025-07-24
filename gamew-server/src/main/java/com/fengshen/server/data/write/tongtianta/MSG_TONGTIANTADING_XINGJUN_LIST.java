package com.fengshen.server.data.write.tongtianta;

import java.util.List;

import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

public class MSG_TONGTIANTADING_XINGJUN_LIST extends BaseWrite<List<Object[]>> {

	@Override
	protected void writeO(ByteBuf buff, List<Object[]> object) {
		
	}

	@Override
	public int cmd() {
		return 0xD225;
	}
}
