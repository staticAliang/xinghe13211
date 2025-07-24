package com.fengshen.server.process.system;

import org.springframework.stereotype.Service;

import com.fengshen.server.data.GameReadTool;
import com.fengshen.server.game.GameHandler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

/**
 * 客户端通知服务器出现错误
 * 
 *
 */
@Service
@Slf4j
public class CMD_CLIENT_ERR_OCCUR implements GameHandler {

	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		int errType = GameReadTool.readShort(buff);
		GameReadTool.readString2(buff);
	}

	@Override
	public int cmd() {
		return 0xD15A;
	}

}
