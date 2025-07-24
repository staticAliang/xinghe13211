package com.fengshen.server.data.write.fight.c;

import org.springframework.stereotype.Service;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.data.vo.fight.Vo_C_CHAR_DIED;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

@Service
public class MSG_C_CHAR_DIED extends BaseWrite<Vo_C_CHAR_DIED> {
	@Override
	protected void writeO(ByteBuf writeBuf, Vo_C_CHAR_DIED object) {
		GameWriteTool.writeInt(writeBuf, object.id);
		GameWriteTool.writeInt(writeBuf, object.damage_type);
	}

	@Override
	public int cmd() {
		return 7669;
	}
}
