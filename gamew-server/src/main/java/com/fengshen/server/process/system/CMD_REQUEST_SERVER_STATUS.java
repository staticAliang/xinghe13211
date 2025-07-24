package com.fengshen.server.process.system;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fengshen.server.data.write.MSG_REQUEST_SERVER_STATUS;
import com.fengshen.server.game.GameCore;
import com.fengshen.server.game.GameHandler;
import com.fengshen.server.game.GameLine;
import com.fengshen.server.game.GameObjectChar;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

// 发送所有线路信息到前端
@Service
@Slf4j
public class CMD_REQUEST_SERVER_STATUS implements GameHandler {
	@Override
	public void process(final ChannelHandlerContext ctx, final ByteBuf buff) {
		List<GameLine> gameLineAll = GameCore.that.getGameLineAll();
		GameObjectChar.send(new MSG_REQUEST_SERVER_STATUS(), gameLineAll);
		log.info("发送所有线路信息");
	}

	@Override
	public int cmd() {
		return 222;
	}
}
