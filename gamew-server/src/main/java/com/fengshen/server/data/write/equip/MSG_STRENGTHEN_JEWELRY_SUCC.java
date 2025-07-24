package com.fengshen.server.data.write.equip;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

public class MSG_STRENGTHEN_JEWELRY_SUCC extends BaseWrite<Integer[]> {

	@Override
	protected void writeO(ByteBuf buff, Integer[] object) {
		GameWriteTool.writeInt(buff, object[0]);
		GameWriteTool.writeByte(buff, object[1]);
	}

	@Override
	public int cmd() {
		return 0xB225;
	}

}
