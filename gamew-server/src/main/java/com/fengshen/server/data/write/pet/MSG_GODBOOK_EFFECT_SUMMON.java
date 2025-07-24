package com.fengshen.server.data.write.pet;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.data.vo.Vo_GODBOOK_EFFECT;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

public class MSG_GODBOOK_EFFECT_SUMMON extends BaseWrite<Vo_GODBOOK_EFFECT> {
	@Override
	protected void writeO(ByteBuf writeBuf, Vo_GODBOOK_EFFECT object) {
		GameWriteTool.writeInt(writeBuf, object.id);
		GameWriteTool.writeShort(writeBuf, object.effect_no);
	}

	@Override
	public int cmd() {
		return 0x2EFB;
	}
}
