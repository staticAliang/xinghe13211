package com.fengshen.server.data.write.system;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.data.vo.system.Vo_RELOCATE;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

public class MSG_RELOCATE extends BaseWrite<Vo_RELOCATE> {

	@Override
	protected void writeO(ByteBuf buff, Vo_RELOCATE object) {
		
		GameWriteTool.writeInt(buff, object.getId());
		GameWriteTool.writeShort(buff, object.getX());
		GameWriteTool.writeShort(buff, object.getY());
		GameWriteTool.writeByte(buff, object.getDir());
	}

	@Override
	public int cmd() {
		return 0xF0DB;
	}

}
