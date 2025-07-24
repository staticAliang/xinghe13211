package com.fengshen.server.data.write.fight.c;

import org.springframework.stereotype.Service;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.data.vo.fight.Vo_C_END_COMBAT;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

@Service
public class MSG_C_END_COMBAT extends BaseWrite<Vo_C_END_COMBAT> {
	@Override
	protected void writeO(ByteBuf writeBuf, Vo_C_END_COMBAT object) {
		GameWriteTool.writeShort(writeBuf, object.a);
	}

	@Override
	public int cmd() {
		return 3581;
	}
}
