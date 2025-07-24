package com.fengshen.server.process.system;

import org.springframework.stereotype.Service;

import com.fengshen.db.domain.Party;
import com.fengshen.server.data.GameReadTool;
import com.fengshen.server.data.vo.party.VO_PARTY_ICON;
import com.fengshen.server.data.write.system.MSG_SEND_ICON;
import com.fengshen.server.domain.Chara;
import com.fengshen.server.game.GameData;
import com.fengshen.server.game.GameHandler;
import com.fengshen.server.game.GameObjectChar;
import com.fengshen.server.game.GameObjectCharMng;
import com.qcloud.cos.utils.StringUtils;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

/**
 * 请求图标
 * 
 * 
 *
 */
@Service
@Slf4j
public class CMD_REQUEST_ICON implements GameHandler {

	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		String md5Value = GameReadTool.readString(buff);
		Chara chara = GameObjectChar.getGameObjectChar().chara;
		//帮派图标
		for (GameObjectChar g : GameObjectChar.getGameObjectChar().gameMap.getSessionList()) {
			if(!StringUtils.isNullOrEmpty(g.chara.getPartyName())) {
				Party party = GameData.that.partyService.findByPartyName(g.chara.getPartyName());
				if(party != null) {
					VO_PARTY_ICON icon = new VO_PARTY_ICON();
					icon.setMd5Value(party.getIconMd5());
					icon.setBuffData(party.getReviewIconMd5());
					GameObjectCharMng.sendAllmap(new MSG_SEND_ICON(), icon, chara.mapid);
				}
			}
		}
		log.info("请求图标,md5Value={},名字={}", md5Value, chara.name);
	}

	@Override
	public int cmd() {
		return 0x5037;
	}

}
