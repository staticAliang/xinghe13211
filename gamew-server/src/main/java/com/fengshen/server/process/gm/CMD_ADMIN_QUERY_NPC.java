package com.fengshen.server.process.gm;

import org.springframework.stereotype.Service;

import com.fengshen.server.data.vo.Vo_4275_0;
import com.fengshen.server.data.write.M53363_0;
import com.fengshen.server.game.GameHandler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

/**
 * 查询npc
 * @author weilian
 *
 */
@Service
@Slf4j
public class CMD_ADMIN_QUERY_NPC implements GameHandler {
	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		Object vo_4275_0 = new Vo_4275_0();
		@SuppressWarnings("unchecked")
		ByteBuf write = new M53363_0().write(vo_4275_0);
		ctx.writeAndFlush((Object) write);
		log.info("查询NPC");
	}

	@Override
	public int cmd() {
		return 53362;
	}
}
