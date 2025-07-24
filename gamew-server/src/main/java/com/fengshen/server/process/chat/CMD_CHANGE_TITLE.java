package com.fengshen.server.process.chat;

import org.springframework.stereotype.Service;

import com.fengshen.server.data.GameReadTool;
import com.fengshen.server.game.GameCommonUtil;
import com.fengshen.server.game.GameHandler;
import com.fengshen.server.game.GameObjectChar;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

/**
 * 更换称谓
 * @author weilian
 *
 */
@Service
@Slf4j
public class CMD_CHANGE_TITLE implements GameHandler {

	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		String select = GameReadTool.readString(buff);
		log.info("更换称谓");
		GameCommonUtil.changeTitle(GameObjectChar.getGameObjectChar(), select);
	}

	@Override
	public int cmd() {
		return 4288;
	}
}