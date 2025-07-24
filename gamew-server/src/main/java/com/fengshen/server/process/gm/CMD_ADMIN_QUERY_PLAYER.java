package com.fengshen.server.process.gm;

import org.springframework.stereotype.Service;

import com.fengshen.server.game.GameHandler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CMD_ADMIN_QUERY_PLAYER implements GameHandler {

	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		
		log.info("gm查看菜单");
	}

	@Override
	public int cmd() {
		return 0x1A06;
	}

}
