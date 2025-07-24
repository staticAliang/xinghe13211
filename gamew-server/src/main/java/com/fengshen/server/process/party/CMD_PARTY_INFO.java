package com.fengshen.server.process.party;

import org.springframework.stereotype.Service;

import com.fengshen.db.domain.Party;
import com.fengshen.server.data.write.party.MSG_PARTY_INFO;
import com.fengshen.server.domain.Chara;
import com.fengshen.server.game.GameData;
import com.fengshen.server.game.GameHandler;
import com.fengshen.server.game.GameObjectChar;
import com.fengshen.server.game.GameUtil;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

/**
 * 查询角色所在帮派信息
 * 
 *
 */
@Service
@Slf4j
public class CMD_PARTY_INFO implements GameHandler {

	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {

		log.info("查询角色所在帮派信息");
		Chara chara = GameObjectChar.getGameObjectChar().chara;
		Party party = GameData.that.partyService.findByPartyName(chara.getPartyName());
		if(party != null) {
			if(party.getState() != 0) {
				GameUtil.sendMeTips("该帮派涉嫌违规，已被封停！");
//				GameUtil.closeDlg("PartyInfoDlg");
//				GameUtil.closeDlg("PartyMemberDlg");
//				return;
			}
			GameObjectChar.send(new MSG_PARTY_INFO(), party);
			return;
		}
		//删除帮派
		chara.setPartyName("");
		GameUtil.sendUpdate(chara);
	}

	@Override
	public int cmd() {
		return 178;
	}

}
