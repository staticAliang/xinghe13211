package com.fengshen.server.process.leave;

import org.springframework.stereotype.Service;

import com.fengshen.server.game.GameHandler;
import com.fengshen.server.game.GameLine;
import com.fengshen.server.game.GameObjectChar;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

/**
 * 离开八仙梦境
 * 
 *
 */
@Service
@Slf4j
public class CMD_LEAVE_BAXIAN implements GameHandler {

	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		GameObjectChar gameObjectChar = GameObjectChar.getGameObjectChar();
		//设置位置
		gameObjectChar.chara.x = 48;
		gameObjectChar.chara.y = 53;
		GameLine.getGameMap(gameObjectChar.chara.line, 17000).join(gameObjectChar);
		log.info("八仙点击离开");
	}

	@Override
	public int cmd() {
		return 0x6000;
	}

}
