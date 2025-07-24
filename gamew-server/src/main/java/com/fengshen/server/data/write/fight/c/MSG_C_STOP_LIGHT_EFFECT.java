package com.fengshen.server.data.write.fight.c;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.data.vo.fight.Vo_COMBAT_LIGHT_EFFECT;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

public class MSG_C_STOP_LIGHT_EFFECT extends BaseWrite<Vo_COMBAT_LIGHT_EFFECT>{

	@Override
	protected void writeO(ByteBuf buff, Vo_COMBAT_LIGHT_EFFECT object) {
		
		GameWriteTool.writeInt(buff, object.getCharId());
		GameWriteTool.writeShort(buff, object.getEffectIcon());
	}

	@Override
	public int cmd() {
		return 0xD1A5;
	}

}
