package com.fengshen.server.data.write.fight.c;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.data.vo.fight.Vo_C_MESSAGE;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

public class MSG_C_MESSAGE extends BaseWrite<Vo_C_MESSAGE> {

	@Override
	protected void writeO(ByteBuf buff, Vo_C_MESSAGE object) {
		GameWriteTool.writeShort(buff, object.getChannel());
		GameWriteTool.writeString(buff, object.getName());
		GameWriteTool.writeString(buff, object.getMsg());
	}

	@Override
	public int cmd() {
		return 0x3DD9;
	}

}
