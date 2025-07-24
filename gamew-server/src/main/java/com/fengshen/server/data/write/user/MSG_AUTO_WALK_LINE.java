package com.fengshen.server.data.write.user;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.game.GameCore;
import com.fengshen.server.game.GameObjectChar;
import com.fengshen.server.netty.BaseWrite;
import com.fengshen.server.util.GameConfig;

import io.netty.buffer.ByteBuf;

public class MSG_AUTO_WALK_LINE extends BaseWrite<Object[]> {

	@Override
	protected void writeO(ByteBuf buff, Object[] object) {
		
		GameWriteTool.writeByte(buff, 0);
		GameWriteTool.writeString(buff, "#Zrandom_walk#Z");
		GameWriteTool.writeString(buff, GameConfig.lineName+GameCore.getGameLine(GameObjectChar.getGameObjectChar().chara.line).lineNum + "线");
	}

	@Override
	public int cmd() {
		return 0xD22D;
	}

}
