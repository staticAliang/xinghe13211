package com.fengshen.server.data.write;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.data.vo.Vo_41009_0;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

public class M41009_0 extends BaseWrite<Vo_41009_0> {
	@Override
	protected void writeO(ByteBuf writeBuf, Vo_41009_0 object) {
		GameWriteTool.writeInt(writeBuf, object.server_time);
		GameWriteTool.writeByte(writeBuf, object.time_zone);
		GameWriteTool.writeString(writeBuf, object.ip);
	}

	@Override
	public int cmd() {
		return 41009;
	}
}
