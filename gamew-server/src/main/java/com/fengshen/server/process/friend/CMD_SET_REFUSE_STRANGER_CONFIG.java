package com.fengshen.server.process.friend;

import org.springframework.stereotype.Service;

import com.fengshen.server.data.GameReadTool;
import com.fengshen.server.domain.Chara;
import com.fengshen.server.game.GameHandler;
import com.fengshen.server.game.GameObjectChar;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

/**
 * 设置拒绝陌生人消息设置
 * 
 *
 */
@Service
@Slf4j
public class CMD_SET_REFUSE_STRANGER_CONFIG implements GameHandler {

	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		int level = GameReadTool.readShort(buff);
		log.info("设置拒绝陌生人消息设置");
		Chara chara = GameObjectChar.getGameObjectChar().chara;
		chara.settingrefuse_stranger_level = level;
	}

	@Override
	public int cmd() {
		return 0xB078;
	}

}
