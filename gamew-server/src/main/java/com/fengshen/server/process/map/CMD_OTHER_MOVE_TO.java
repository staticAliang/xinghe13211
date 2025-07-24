package com.fengshen.server.process.map;

import org.springframework.stereotype.Service;

import com.fengshen.server.data.GameReadTool;
import com.fengshen.server.data.vo.Vo_16429_0;
import com.fengshen.server.data.vo.Vo_16431_0;
import com.fengshen.server.data.write.M16429_0;
import com.fengshen.server.data.write.M16431_0;
import com.fengshen.server.domain.Chara;
import com.fengshen.server.game.GameHandler;
import com.fengshen.server.game.GameObjectChar;
import com.fengshen.server.game.GameObjectCharMng;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

/**
 * 移动到某个位置
 * 
 *
 */
@Service
public class CMD_OTHER_MOVE_TO implements GameHandler {
	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		int id = GameReadTool.readInt(buff);
		int map_id = GameReadTool.readInt(buff);
		int x = GameReadTool.readShort(buff);
		int y = GameReadTool.readShort(buff);
		int dir = GameReadTool.readShort(buff);
		GameObjectChar gameObjectChar = GameObjectCharMng.getGameObjectChar(id);
		if(gameObjectChar != null) {
			Chara chara = gameObjectChar.chara;
			chara.x = x;
			chara.y = y;
			chara.dir = dir;
			Vo_16429_0 vo_16429_0 = new Vo_16429_0();
			vo_16429_0.id = id;
			vo_16429_0.x = x;
			vo_16429_0.y = y;
			vo_16429_0.map_id = map_id;
			gameObjectChar.gameMap.send(new M16429_0(), vo_16429_0);
			
			Vo_16431_0 vo_16431_0 = new Vo_16431_0();
			vo_16431_0.id = id;
			vo_16431_0.x = x;
			vo_16431_0.y = y;
			vo_16431_0.dir =  dir;
			gameObjectChar.gameMap.send(new M16431_0(), vo_16431_0);
		}
	}

	@Override
	public int cmd() {
		return 16558;
	}
}
