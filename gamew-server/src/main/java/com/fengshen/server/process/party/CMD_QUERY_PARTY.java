package com.fengshen.server.process.party;

import org.springframework.stereotype.Service;

import com.fengshen.db.domain.Party;
import com.fengshen.server.data.GameReadTool;
import com.fengshen.server.data.write.party.MSG_PARTY_BRIEF_INFO;
import com.fengshen.server.game.GameData;
import com.fengshen.server.game.GameHandler;
import com.fengshen.server.game.GameObjectChar;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

/**
 * 查询某个帮派信息
 * 
 *
 */
@Service
@Slf4j
public class CMD_QUERY_PARTY implements GameHandler {
	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buf) {
		String name = GameReadTool.readString(buf);
		String id = GameReadTool.readString(buf);
		String type = GameReadTool.readString(buf);
		log.info("查询某个帮派信息----name={},id={},type={}", name, id, type);

		Party party = GameData.that.partyService.findByPartyId(id);
		if (party == null) {
			return;
		}
		GameObjectChar.send(new MSG_PARTY_BRIEF_INFO(), party);
	}

	@Override
	public int cmd() {
		return 0xA012;
	}
}
