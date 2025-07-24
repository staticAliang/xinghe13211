package com.fengshen.server.process.system;

import org.springframework.stereotype.Service;

import com.fengshen.server.data.GameReadTool;
import com.fengshen.server.game.GameHandler;
import com.fengshen.server.game.GameObjectChar;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CMD_KICK_OFF_CLIENT implements GameHandler {

	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		GameObjectChar gameObjectChar = GameObjectChar.getGameObjectChar();
		String name = "";
		if(gameObjectChar != null) {
			name = gameObjectChar.chara.name;
		}
		String reason = GameReadTool.readString(buff);
		log.error("客户端强制下线---------------------{},角色名为={}",reason,name);
	}

	@Override
	public int cmd() {
		return 0xD1F8;
	}

}
