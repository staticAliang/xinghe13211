package com.fengshen.server.process.leitai;

import org.springframework.stereotype.Service;

import com.fengshen.server.data.GameReadTool;
import com.fengshen.server.game.GameHandler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CMD_COMPETE_TOURNAMENT_TOP_USER_INFO implements GameHandler {

	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		String name = GameReadTool.readString(buff);
		log.info("擂台请求10强,{}",name);
	}

	@Override
	public int cmd() {
		return 0x5013;
	}

}
