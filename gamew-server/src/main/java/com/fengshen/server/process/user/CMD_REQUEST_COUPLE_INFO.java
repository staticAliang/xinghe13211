package com.fengshen.server.process.user;

import org.springframework.stereotype.Service;

import com.fengshen.server.game.GameHandler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

/**
 * 请求夫妻信息
 * 
 *
 */
@Service
@Slf4j
public class CMD_REQUEST_COUPLE_INFO implements GameHandler {
	@Override
	public void process(final ChannelHandlerContext ctx, final ByteBuf buff) {
		log.info("请求夫妻信息");
	}

	@Override
	public int cmd() {
		return 45172;
	}
}
