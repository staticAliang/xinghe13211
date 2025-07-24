package com.fengshen.server.data.write.leitai;

import java.util.List;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.domain.Chara;
import com.fengshen.server.game.GameCommonUtil;
import com.fengshen.server.game.GameObjectChar;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

public class MSG_COMPETE_TOURNAMENT_TARGETS extends BaseWrite<List<GameObjectChar>>{


	@Override
	protected void writeO(ByteBuf buff, List<GameObjectChar> object) {
		GameWriteTool.writeShort(buff, object.size());
		for(GameObjectChar gameObjectChar:object) {
			Chara chara = gameObjectChar.chara;
			GameWriteTool.writeString(buff, chara.uuid);
			GameWriteTool.writeString(buff, chara.name);
			GameWriteTool.writeInt(buff, chara.waiguan);
			GameWriteTool.writeByte(buff, chara.level);
			if(GameCommonUtil.isNotGameTeam(gameObjectChar.gameTeam)) {
				GameWriteTool.writeByte(buff, 1);
				GameWriteTool.writeByte(buff, gameObjectChar.gameTeam.duiwu.size());
			}else {
				GameWriteTool.writeByte(buff, 0);
				GameWriteTool.writeByte(buff, 0);
			}
			GameWriteTool.writeInt(buff, chara.ctDataScore);
		}
	}

	@Override
	public int cmd() {
		return 0x5011;
	}

	
}
