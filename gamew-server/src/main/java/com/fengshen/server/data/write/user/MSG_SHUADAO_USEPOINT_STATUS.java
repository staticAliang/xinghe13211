package com.fengshen.server.data.write.user;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

public class MSG_SHUADAO_USEPOINT_STATUS extends BaseWrite<Object>{

	@Override
	protected void writeO(ByteBuf buff, Object object) {
		
		GameWriteTool.writeByte(buff, 1);
		GameWriteTool.writeByte(buff, 1);
		GameWriteTool.writeByte(buff, 1);
		GameWriteTool.writeByte(buff, 1);
	}

	@Override
	public int cmd() {
		return 0xB0A3;
	}

	
}
