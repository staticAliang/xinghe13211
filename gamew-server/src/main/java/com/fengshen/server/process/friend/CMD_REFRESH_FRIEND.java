package com.fengshen.server.process.friend;

import org.springframework.stereotype.Service;

import com.fengshen.server.data.GameReadTool;
import com.fengshen.server.game.GameHandler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

/**
 * 刷新好友
 * 
 *
 */
@Service
@Slf4j
public class CMD_REFRESH_FRIEND implements GameHandler {

	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		
		String group = GameReadTool.readString(buff);
		String name = GameReadTool.readString(buff);
		String gid = GameReadTool.readString(buff);
		log.info("刷新好友, group={},name={},gid={}",group,name,gid);
	}

	@Override
	public int cmd() {
		return 0x206A;
	}

}
