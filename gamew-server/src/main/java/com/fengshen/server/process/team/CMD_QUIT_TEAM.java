package com.fengshen.server.process.team;

import org.springframework.stereotype.Service;

import com.fengshen.server.game.GameHandler;
import com.fengshen.server.game.GameObjectChar;
import com.fengshen.server.game.GameTeamUtil;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

/**
 * 离开队伍
 * @author aaa
 *
 */
@Service
public class CMD_QUIT_TEAM implements GameHandler {

	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		GameObjectChar gameObjectChar = GameObjectChar.getGameObjectChar();
		if(gameObjectChar == null) {
			return;
		}
		//离开队伍
		GameTeamUtil.quitTeam(gameObjectChar);
	}

	@Override
	public int cmd() {
		return 26;
	}
}