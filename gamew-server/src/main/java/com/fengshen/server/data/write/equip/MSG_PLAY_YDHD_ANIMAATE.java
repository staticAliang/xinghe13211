package com.fengshen.server.data.write.equip;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.data.vo.equip.Vo_PLAY_LYFH_ANIMAATE;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

public class MSG_PLAY_YDHD_ANIMAATE extends BaseWrite<Vo_PLAY_LYFH_ANIMAATE> {

	@Override
	protected void writeO(ByteBuf buff, Vo_PLAY_LYFH_ANIMAATE object) {
		
		GameWriteTool.writeInt(buff, object.getPos());
		GameWriteTool.writeByte(buff, object.getIndex());
	}

	@Override
	public int cmd() {
		return 0xD2A9;
	}

}
