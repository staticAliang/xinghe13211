package com.fengshen.server.process.system;

import java.util.concurrent.ThreadLocalRandom;

import org.springframework.stereotype.Service;

import com.fengshen.server.data.GameReadTool;
import com.fengshen.server.data.vo.Vo_4275_0;
import com.fengshen.server.data.write.M4275_0;
import com.fengshen.server.game.GameCommonUtil;
import com.fengshen.server.game.GameHandler;
import com.fengshen.server.game.GameObjectChar;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

// 这个类是角色用来连接心跳的
@Service
public class CMD_ECHO implements GameHandler {

	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		GameReadTool.readInt(buff);
		int peer_time = GameReadTool.readInt(buff);
		GameObjectChar gameObjectChar = GameObjectChar.getGameObjectChar();
		if (gameObjectChar != null && gameObjectChar.chara != null) {
			if(GameCommonUtil.addSpeedHandler(gameObjectChar)) {
				return;
			}
		}
		if (gameObjectChar != null) {
			gameObjectChar.heartEcho = System.currentTimeMillis();
			Vo_4275_0 vo_4275_0 = new Vo_4275_0();
			vo_4275_0.a = peer_time + 10000 + ThreadLocalRandom.current().nextInt(500);
			GameObjectChar.send(new M4275_0(), vo_4275_0);
		}
	}

	@Override
	public int cmd() {
		return 4274;
	}
}
