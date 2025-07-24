package com.fengshen.server.process.user;

import org.springframework.stereotype.Service;

import com.fengshen.server.game.GameHandler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

/**
 * 查看我的师徒关系
 * 
 *
 */
@Service
@Slf4j
public class CMD_MY_APPRENTICE_INFO implements GameHandler {
	@Override
	public void process(final ChannelHandlerContext ctx, final ByteBuf buff) {
		log.info("查看我的师徒关系");
	}

	@Override
	public int cmd() {
		return 53336;
	}
}
