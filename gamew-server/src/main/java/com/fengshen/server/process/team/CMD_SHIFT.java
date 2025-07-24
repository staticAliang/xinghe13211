package com.fengshen.server.process.team;

import org.springframework.stereotype.Service;

import com.fengshen.server.data.GameReadTool;
import com.fengshen.server.data.vo.Vo_TITLE;
import com.fengshen.server.data.write.MSG_TITLE;
import com.fengshen.server.domain.Chara;
import com.fengshen.server.game.GameHandler;
import com.fengshen.server.game.GameLine;
import com.fengshen.server.game.GameObjectChar;
import com.fengshen.server.game.GameObjectCharMng;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

@Service
public class CMD_SHIFT implements GameHandler {
	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		int id = GameReadTool.readInt(buff);
		int x = GameReadTool.readShort(buff);
		int y = GameReadTool.readShort(buff);
		int dir = GameReadTool.readShort(buff);
		Chara chara = GameObjectChar.getGameObjectChar().chara;
		GameObjectChar formGameObjectChar = GameObjectCharMng.getGameObjectChar(id);
		if (formGameObjectChar != null) {
			Chara formChara = formGameObjectChar.chara;
			formChara.x = x;
			formChara.y = y;
			formChara.dir = dir;
			formChara.mapid = chara.mapid;
			formChara.mapName = chara.mapName;
			if (GameObjectChar.getGameObjectChar().gameMap != null) {
				GameObjectChar.getGameObjectChar().gameMap.joinduiyuan(formGameObjectChar,
						chara);
			} else {
				GameLine.getGameMap(chara.line, chara.mapName)
						.joinduiyuan(formGameObjectChar, chara);
			}

			Vo_TITLE vo_61671_0 = new Vo_TITLE();
			vo_61671_0.id = formChara.id;
			vo_61671_0.count = 2;
			vo_61671_0.list.add(2);
			vo_61671_0.list.add(5);
			GameObjectChar.getGameObjectChar().gameMap.send(new MSG_TITLE(), vo_61671_0);
			vo_61671_0 = new Vo_TITLE();
			vo_61671_0.id = chara.id;
			vo_61671_0.count = 2;
			vo_61671_0.list.add(2);
			vo_61671_0.list.add(3);
			GameObjectChar.getGameObjectChar().gameMap.send(new MSG_TITLE(), vo_61671_0);
		}
	}

	@Override
	public int cmd() {
		return 4248;
	}
}
