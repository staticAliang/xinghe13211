package com.fengshen.server.data.write.npc;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

public class MSG_MENU_SELECT extends BaseWrite<String> {

	@Override
	protected void writeO(ByteBuf buff, String object) {
		
		GameWriteTool.writeString(buff, object);
	}

	@Override
	public int cmd() {
		return 0xB008;
	}

}
