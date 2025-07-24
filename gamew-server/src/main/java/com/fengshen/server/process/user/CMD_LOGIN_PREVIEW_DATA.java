package com.fengshen.server.process.user;

import org.springframework.stereotype.Service;

import com.fengshen.server.data.write.user.MSG_LOGIN_PREVIEW_DATA;
import com.fengshen.server.game.GameHandler;
import com.fengshen.server.game.GameObjectChar;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CMD_LOGIN_PREVIEW_DATA implements GameHandler {

	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {

		log.info("登陆刷新预览");
		GameObjectChar.send(new MSG_LOGIN_PREVIEW_DATA(), null);
	}

	@Override
	public int cmd() {
		return 0x5E32;
	}

}
