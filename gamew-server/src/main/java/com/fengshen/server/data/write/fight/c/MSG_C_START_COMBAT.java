package com.fengshen.server.data.write.fight.c;

import org.springframework.stereotype.Service;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.data.vo.Vo_3583_0;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

@Service
public class MSG_C_START_COMBAT extends BaseWrite<Vo_3583_0> {
	@Override
	protected void writeO(final ByteBuf writeBuf, Vo_3583_0 object) {
		GameWriteTool.writeShort(writeBuf, object.flag);
		GameWriteTool.writeByte(writeBuf, object.mode);
		GameWriteTool.writeByte(writeBuf, object.pet_capcity_type);
	}

	@Override
	public int cmd() {
		return 3583;
	}
}
