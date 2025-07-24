package com.fengshen.server.data.write.fight.c;

import org.springframework.stereotype.Service;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.data.vo.Vo_64971_0;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

@Service
public class MSG_C_SET_FIGHT_PET extends BaseWrite<Vo_64971_0> {
	@Override
	protected void writeO(final ByteBuf writeBuf, Vo_64971_0 object2) {
		GameWriteTool.writeInt(writeBuf, object2.id);
		GameWriteTool.writeShort(writeBuf, object2.haveCalled);
	}

	@Override
	public int cmd() {
		return 0xFDB1;
	}
}
