package com.fengshen.server.process.user;

import org.springframework.stereotype.Service;

import com.fengshen.server.data.GameReadTool;
import com.fengshen.server.game.GameHandler;
import com.fengshen.server.game.GameObjectChar;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

/**
 * 请求认证信息
 * 
 *
 */
@Service
@Slf4j
public class CMD_REQUEST_FUZZY_IDENTITY implements GameHandler {

	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		int forceRequest = GameReadTool.readByte(buff);
		log.info("请求认证信息:{}",forceRequest);
		GameObjectChar gameObjectChar = GameObjectChar.getGameObjectChar();
		if(gameObjectChar == null) {
			return;
		}
		
	}

	@Override
	public int cmd() {
		return 0xD0A8;
	}

}
