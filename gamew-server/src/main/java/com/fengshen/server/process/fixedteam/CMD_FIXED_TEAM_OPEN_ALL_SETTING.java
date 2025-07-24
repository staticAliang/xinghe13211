package com.fengshen.server.process.fixedteam;

import org.springframework.stereotype.Service;

import com.fengshen.server.game.GameHandler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CMD_FIXED_TEAM_OPEN_ALL_SETTING implements GameHandler {

	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		log.info("固定队打开全部设置");
	}

	@Override
	public int cmd() {
		return 0xD20A;
	}

}
