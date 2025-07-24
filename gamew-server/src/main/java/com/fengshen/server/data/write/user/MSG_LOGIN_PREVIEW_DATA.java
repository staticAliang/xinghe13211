package com.fengshen.server.data.write.user;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.game.GameObjectChar;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

public class MSG_LOGIN_PREVIEW_DATA extends BaseWrite<Object> {

	@Override
	protected void writeO(ByteBuf buff, Object object) {
		GameWriteTool.writeString(buff, GameObjectChar.getGameObjectChar().chara.uuid);
	}

	@Override
	public int cmd() {
		return 0x5E33;
	}

}
