package com.fengshen.server.game;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

public interface GameHandler {
	void process(final ChannelHandlerContext ctx, final ByteBuf buff);
	int cmd();
}
