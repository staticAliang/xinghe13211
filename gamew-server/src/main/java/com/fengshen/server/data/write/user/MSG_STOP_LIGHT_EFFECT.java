package com.fengshen.server.data.write.user;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.data.vo.user.Vo_PLAY_LIGHT_EFFECT;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

public class MSG_STOP_LIGHT_EFFECT extends BaseWrite<Vo_PLAY_LIGHT_EFFECT> {

	@Override
	protected void writeO(ByteBuf buff, Vo_PLAY_LIGHT_EFFECT object) {
		GameWriteTool.writeInt(buff, object.getCharId());
		GameWriteTool.writeInt(buff, object.getEffectIcon());
	}

	@Override
	public int cmd() {
		return 0xA009;
	}

}
