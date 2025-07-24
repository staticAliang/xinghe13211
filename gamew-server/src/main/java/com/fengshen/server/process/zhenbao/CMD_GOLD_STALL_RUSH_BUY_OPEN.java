package com.fengshen.server.process.zhenbao;

import org.springframework.stereotype.Service;

import com.fengshen.server.data.GameReadTool;
import com.fengshen.server.game.GameHandler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CMD_GOLD_STALL_RUSH_BUY_OPEN implements GameHandler {

	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		
		String readString = GameReadTool.readString(buff);
		log.info("珍宝抢购。{}",readString);
	}

	@Override
	public int cmd() {
		return 0x811A;
	}

}
