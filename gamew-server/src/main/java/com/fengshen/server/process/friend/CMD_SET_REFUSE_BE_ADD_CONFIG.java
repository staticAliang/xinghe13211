package com.fengshen.server.process.friend;

import org.springframework.stereotype.Service;

import com.fengshen.server.data.GameReadTool;
import com.fengshen.server.domain.Chara;
import com.fengshen.server.game.GameHandler;
import com.fengshen.server.game.GameObjectChar;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CMD_SET_REFUSE_BE_ADD_CONFIG implements GameHandler {

	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		
		int level = GameReadTool.readShort(buff);
		log.info("设置拒绝陌生人消息等级设置");
		Chara chara = GameObjectChar.getGameObjectChar().chara;
		chara.setting_refuse_be_add_level = level;
	}

	@Override
	public int cmd() {
		return 0xB086;
	}

}
