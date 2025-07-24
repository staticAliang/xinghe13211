package com.fengshen.server.process.party;

import org.springframework.stereotype.Service;

import com.fengshen.server.data.write.party.MSG_PARTY_CHANNEL_DENY_LIST;
import com.fengshen.server.game.GameHandler;
import com.fengshen.server.game.GameObjectChar;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

/**
 * 帮派禁言列表
 * 
 *
 */
@Service
public class CMD_GET_PARTY_CHANNEL_DENY_LIST implements GameHandler{

	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		GameObjectChar.send(new MSG_PARTY_CHANNEL_DENY_LIST(), null);
	}

	@Override
	public int cmd() {
		return 0x2E3C;
	}

}
