package com.fengshen.server.data.write.other;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.data.vo.other.Vo_MAP_DECORATION_APPEAR;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

public class MSG_MAP_DECORATION_APPEAR extends BaseWrite<Vo_MAP_DECORATION_APPEAR> {

	@Override
	protected void writeO(ByteBuf buff, Vo_MAP_DECORATION_APPEAR object) {
		
		GameWriteTool.writeInt(buff, object.getId());
		GameWriteTool.writeInt(buff, object.getIcon());
		GameWriteTool.writeShort(buff, object.getX());
		GameWriteTool.writeShort(buff, object.getY());
		GameWriteTool.writeShort(buff, object.getDir());
		GameWriteTool.writeShort(buff, object.getOx());
		GameWriteTool.writeShort(buff, object.getOy());
	}

	@Override
	public int cmd() {
		return 0xB271;
	}

}
