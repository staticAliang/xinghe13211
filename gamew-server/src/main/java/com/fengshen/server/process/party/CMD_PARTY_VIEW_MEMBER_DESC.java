package com.fengshen.server.process.party;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.fengshen.db.domain.Characters;
import com.fengshen.db.domain.Party;
import com.fengshen.server.data.GameReadTool;
import com.fengshen.server.data.vo.party.Vo_PARTY_VIEW_MEMBER_DESC;
import com.fengshen.server.data.write.party.MSG_PARTY_VIEW_MEMBER_DESC;
import com.fengshen.server.domain.Chara;
import com.fengshen.server.game.GameCore;
import com.fengshen.server.game.GameData;
import com.fengshen.server.game.GameHandler;
import com.fengshen.server.game.GameObjectChar;
import com.fengshen.server.game.GameObjectCharMng;
import com.qcloud.cos.utils.StringUtils;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

@Service
public class CMD_PARTY_VIEW_MEMBER_DESC implements GameHandler {

	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		String gid = GameReadTool.readString(buff);
		Chara toChara = null;
		if(GameObjectCharMng.getGameObjectCharByUUid(gid) == null) {
			//数据库查询
			Characters ch = GameData.that.baseCharactersService.findOneByGidSelectProperties(gid, "gid", "data");
			toChara = JSONObject.parseObject(ch.getData(),Chara.class);
			toChara.setUuid(ch.getGid());
		}else {
			toChara = GameObjectCharMng.getGameObjectCharByUUid(gid).chara;
		}
		if(!StringUtils.isNullOrEmpty(toChara.getPartyName())) {
			Party party = GameCore.partyMap.get(toChara.getPartyName());
			if(party != null) {
				Vo_PARTY_VIEW_MEMBER_DESC v = new Vo_PARTY_VIEW_MEMBER_DESC();
				v.desc = "暂无备注";
				v.memberGid = toChara.uuid;
				v.partyId = party.getPartyId();
				GameObjectChar.send(new MSG_PARTY_VIEW_MEMBER_DESC(), v);
			}
		}
	}

	@Override
	public int cmd() {
		return 0x8292;
	}

}
