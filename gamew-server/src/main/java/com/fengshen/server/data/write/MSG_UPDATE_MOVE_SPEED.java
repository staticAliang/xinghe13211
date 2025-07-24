package com.fengshen.server.data.write;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.data.vo.Vo_UPDATE_MOVE_SPEED;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

public class MSG_UPDATE_MOVE_SPEED extends BaseWrite<Vo_UPDATE_MOVE_SPEED> {
	@Override
	protected void writeO(ByteBuf writeBuf, Vo_UPDATE_MOVE_SPEED object) {
		GameWriteTool.writeInt(writeBuf, object.id);
		GameWriteTool.writeByte(writeBuf, object.moveSpeedPercent);
	}

	@Override
	public int cmd() {
		return 45177;
	}
}
