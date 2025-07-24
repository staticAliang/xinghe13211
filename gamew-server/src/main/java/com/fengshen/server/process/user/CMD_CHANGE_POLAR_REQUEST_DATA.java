package com.fengshen.server.process.user;

import org.springframework.stereotype.Service;

import com.fengshen.server.game.GameHandler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

/**
 * a请求门派转换数据
 * @author aaa
 *
 */
@Service
@Slf4j
public class CMD_CHANGE_POLAR_REQUEST_DATA implements GameHandler {

	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {

		log.info("请求门派转换数据");
	}

	@Override
	public int cmd() {
		return 0x5290;
	}

}
