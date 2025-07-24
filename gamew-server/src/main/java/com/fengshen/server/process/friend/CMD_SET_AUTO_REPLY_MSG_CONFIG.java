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
public class CMD_SET_AUTO_REPLY_MSG_CONFIG implements GameHandler {

	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		String content = GameReadTool.readString(buff);
		Chara chara = GameObjectChar.getGameObjectChar().chara;
		chara.settingauto_reply_msg = content;
		log.info("设置自动回复");
	}

	@Override
	public int cmd() {
		return 0xB084;
	}

}
