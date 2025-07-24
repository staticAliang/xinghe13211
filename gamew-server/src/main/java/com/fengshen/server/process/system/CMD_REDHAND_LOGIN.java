package com.fengshen.server.process.system;

import org.springframework.stereotype.Service;

import com.fengshen.server.game.GameHandler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

/**
 * 使用红手登录
 * 
 *
 */
@Service
public class CMD_REDHAND_LOGIN implements GameHandler {

	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {

		
		
	}

	@Override
	public int cmd() {
		return 0xD2C8;
	}

}
