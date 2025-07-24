package com.fengshen.server.data.write.fight.c;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.data.vo.fight.Vo_C_LIFE_DELTA;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

public class MSG_C_LIFE_DELTA extends BaseWrite<Vo_C_LIFE_DELTA> {
	@Override
	protected void writeO(ByteBuf writeBuf, Vo_C_LIFE_DELTA object) {
		GameWriteTool.writeInt(writeBuf, object.id);
		GameWriteTool.writeInt(writeBuf, object.hitter_id);
		GameWriteTool.writeInt(writeBuf, object.point);
		GameWriteTool.writeInt(writeBuf, object.effect_no);
		GameWriteTool.writeInt(writeBuf, object.damage_type);
	}

	@Override
	public int cmd() {
		return 15857;
	}
}
