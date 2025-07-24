package com.fengshen.server.process.system;

import org.springframework.stereotype.Service;

import com.fengshen.server.data.GameReadTool;
import com.fengshen.server.game.GameHandler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CMD_SWITCH_SERVER implements GameHandler {
	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		String lineName = GameReadTool.readString(buff);
		log.info("切换线路, lineName={}", lineName);
	}

	@Override
	public int cmd() {
		return 4308;
	}
}
