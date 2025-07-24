package com.fengshen.server.process.leave;

import org.springframework.stereotype.Service;

import com.fengshen.server.domain.Chara;
import com.fengshen.server.game.GameHandler;
import com.fengshen.server.game.GameLine;
import com.fengshen.server.game.GameObjectChar;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

/**
 * 离开副本
 * @author weilian
 *
 */
@Service
@Slf4j
public class CMD_LEAVE_DUNGEON implements GameHandler {

	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		
		GameObjectChar gameObjectChar = GameObjectChar.getGameObjectChar();
		Chara chara = gameObjectChar.chara;
		chara.x = 141;
		chara.y = 17;
		GameLine.getGameMap(chara.line, "天墉城").join(gameObjectChar);;
		log.info("副本点击离开");
	}

	@Override
	public int cmd() {
		return 0x6002;
	}

}
