package com.fengshen.server.process.jiehun;

import org.springframework.stereotype.Service;

import com.fengshen.server.game.GameHandler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

/**
 * 请求开始婚礼
 * 
 *
 */
@Service
@Slf4j
public class CMD_START_WEDDING implements GameHandler {

	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		
		log.info("开始婚礼");
	}

	@Override
	public int cmd() {
		return 0xB0D5;
	}

}
