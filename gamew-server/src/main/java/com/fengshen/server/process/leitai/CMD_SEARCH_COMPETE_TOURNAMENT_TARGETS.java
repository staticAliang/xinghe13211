package com.fengshen.server.process.leitai;

import org.springframework.stereotype.Service;

import com.fengshen.server.game.GameHandler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CMD_SEARCH_COMPETE_TOURNAMENT_TARGETS implements GameHandler {

	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		log.info("刷新擂台挑战");
	}

	@Override
	public int cmd() {
		return 0x5010;
	}

}
