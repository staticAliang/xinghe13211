package com.fengshen.server.data.write;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

public class TestWrite extends BaseWrite<Object> {
	
	public int cmd;

	public TestWrite(int cmd) {
		this.cmd = cmd;
	}
	
	@Override
	protected void writeO(final ByteBuf buff, Object object) {

		GameWriteTool.writeByte(buff, 1);
		
		
	}

	@Override
	public int cmd() {
		return cmd;
	}
}
