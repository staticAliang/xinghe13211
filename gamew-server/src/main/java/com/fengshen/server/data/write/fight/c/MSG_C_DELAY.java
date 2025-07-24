package com.fengshen.server.data.write.fight.c;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

public class MSG_C_DELAY extends BaseWrite<Integer[]> {

	@Override
	protected void writeO(ByteBuf buff, Integer[] object) {
		
		GameWriteTool.writeInt(buff, object[0]);
		GameWriteTool.writeInt(buff, object[1]);
	}

	@Override
	public int cmd() {
		return 0x2DC9;
	}

}
