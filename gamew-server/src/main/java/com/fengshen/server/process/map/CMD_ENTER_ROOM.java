package com.fengshen.server.process.map;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fengshen.db.domain.NpcPoint;
import com.fengshen.server.data.GameReadTool;
import com.fengshen.server.domain.Chara;
import com.fengshen.server.game.GameData;
import com.fengshen.server.game.GameHandler;
import com.fengshen.server.game.GameLine;
import com.fengshen.server.game.GameMap;
import com.fengshen.server.game.GameObjectChar;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

/**
 * 进入地图
 * 
 *
 */
@Service
@Slf4j
public class CMD_ENTER_ROOM implements GameHandler {
	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		String room_name = GameReadTool.readString(buff);
		int isTaskWalk = GameReadTool.readByte(buff);
		log.info("进入地图, room_name={},isTaskWalk={}",room_name,isTaskWalk);
		GameObjectChar session = GameObjectChar.getGameObjectChar();
		Chara chara = session.chara;
		GameMap gameMap = GameLine.getGameMap(chara.line, room_name);

		if (!chara.mapName.equals(room_name)) {
			List<NpcPoint> list = (List<NpcPoint>) GameData.that.baseNpcPointService.findByMapname(room_name);
			for (NpcPoint npcPoint : list) {
				if (npcPoint.getDoorname().equals(chara.mapName)) {
					chara.x = npcPoint.getInx();
					chara.y = npcPoint.getIny();
				}
			}
		}
		gameMap.join(session);
	}

	@Override
	public int cmd() {
		return 4144;
	}
}
