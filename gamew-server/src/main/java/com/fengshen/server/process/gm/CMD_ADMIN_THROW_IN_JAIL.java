package com.fengshen.server.process.gm;

import org.springframework.stereotype.Service;

import com.fengshen.server.data.GameReadTool;
import com.fengshen.server.game.GameHandler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CMD_ADMIN_THROW_IN_JAIL implements GameHandler {

	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {

		String name = GameReadTool.readString(buff);
		String gid = GameReadTool.readString(buff);
		int ri = GameReadTool.readInt(buff);
		String reason = GameReadTool.readString(buff);
		
		log.info("gm禁闭角色={},gid={},ri,={},reason={}",name,gid,ri,reason);
	}

	@Override
	public int cmd() {
		return 0x1AF6;
	}

}
