package com.fengshen.server.process.friend;

import org.springframework.stereotype.Service;

import com.fengshen.server.game.GameHandler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

@Service
public class CMD_MODIFY_FRIEND_MEMO implements GameHandler {

	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		
	}

	@Override
	public int cmd() {
		return 0xB09E;
	}

}
