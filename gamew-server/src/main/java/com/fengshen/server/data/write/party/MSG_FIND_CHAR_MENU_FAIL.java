package com.fengshen.server.data.write.party;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

public class MSG_FIND_CHAR_MENU_FAIL extends BaseWrite<Object[]>{

	@Override
	protected void writeO(ByteBuf buff, Object[] object) {
		
		GameWriteTool.writeString(buff, (String) object[0]);
		GameWriteTool.writeString(buff, (String) object[1]);
	}

	@Override
	public int cmd() {
		return 0xB009;
	}

}
