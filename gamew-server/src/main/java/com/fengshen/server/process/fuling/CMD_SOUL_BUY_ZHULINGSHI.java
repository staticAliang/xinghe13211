package com.fengshen.server.process.fuling;

import org.springframework.stereotype.Service;

import com.fengshen.server.game.GameHandler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

/**
 *a 购买铸灵石
 * @author aaa
 *
 */
@Service
@Slf4j
public class CMD_SOUL_BUY_ZHULINGSHI implements GameHandler {

	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		
		log.info("购买铸灵石");
	}

	@Override
	public int cmd() {
		return 0xD36E;
	}

}
