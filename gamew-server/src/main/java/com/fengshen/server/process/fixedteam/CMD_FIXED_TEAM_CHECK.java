package com.fengshen.server.process.fixedteam;

import org.springframework.stereotype.Service;

import com.fengshen.server.data.write.fixedteam.MSG_FIXED_TEAM_CHECK;
import com.fengshen.server.game.GameHandler;
import com.fengshen.server.game.GameObjectChar;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CMD_FIXED_TEAM_CHECK implements GameHandler {

	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		log.info("检查是否有固定队");
		GameObjectChar gameObjectChar = GameObjectChar.getGameObjectChar();
		int flag = 0;
		if(!gameObjectChar.chara.fixedTeamName.isEmpty()) {
			flag = 1;
		}
		GameObjectChar.send(new MSG_FIXED_TEAM_CHECK(), flag);
	}

	@Override
	public int cmd() {
		return 0x50FB;
	}

}
