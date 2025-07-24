package com.fengshen.server.process.wdrb;

import org.springframework.stereotype.Service;

import com.fengshen.server.game.GameHandler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CMD_WD_RB_SHOW_REDBAG implements GameHandler {

	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		log.info("查看世界红包");
	}

	@Override
	public int cmd() {
		return 0x82C4;
	}

}
