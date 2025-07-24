package com.fengshen.server.process.user;

import org.springframework.stereotype.Service;

import com.fengshen.server.data.write.user.MSG_DAILY_STATS;
import com.fengshen.server.domain.Chara;
import com.fengshen.server.game.GameHandler;
import com.fengshen.server.game.GameObjectChar;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

/**
 * 今日数据统计
 * 
 *
 */
@Service
public class CMD_REQUEST_DAILY_STATS implements GameHandler {

	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		Chara chara = GameObjectChar.getGameObjectChar().chara;
		GameObjectChar.send(new MSG_DAILY_STATS(), chara);
	}

	@Override
	public int cmd() {
		return 0xD06E;
	}

}
