package com.fengshen.server.process.system;

import org.springframework.stereotype.Service;

import com.fengshen.server.data.GameReadTool;
import com.fengshen.server.game.GameHandler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CMD_REPORT_NETWORK_DELAY implements GameHandler {

	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		String memo = GameReadTool.readString2(buff);
		log.error("网络延迟上报：{}",memo);
	}

	@Override
	public int cmd() {
		return 0xB322;
	}

}
