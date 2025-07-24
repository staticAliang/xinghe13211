package com.fengshen.server.process.safelock;

import org.springframework.stereotype.Service;

import com.fengshen.server.game.GameHandler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

/**
 * 请求或取消强制解锁
 * 
 *
 */
@Service
@Slf4j
public class CMD_SAFE_LOCK_RESET implements GameHandler {

	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		log.info("请求或取消强制解锁");
	}

	@Override
	public int cmd() {
		return 0x8042;
	}

}
