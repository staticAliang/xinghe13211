package com.fengshen.server.process.party;

import org.springframework.stereotype.Service;

import com.fengshen.db.domain.Party;
import com.fengshen.server.data.GameReadTool;
import com.fengshen.server.domain.Chara;
import com.fengshen.server.game.GameCommonUtil;
import com.fengshen.server.game.GameData;
import com.fengshen.server.game.GameHandler;
import com.fengshen.server.game.GameObjectChar;
import com.qcloud.cos.utils.StringUtils;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import tk.mybatis.mapper.entity.Example;

/**
 * 修改帮派公告
 * 
 *
 */
@Service
public class CMD_PARTY_MODIFY_ANNOUNCE implements GameHandler {

	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		String annouce = GameReadTool.readString2(buff);
		Chara chara = GameObjectChar.getGameObjectChar().chara;
		if(!StringUtils.isNullOrEmpty(chara.getPartyName())) {
			if("帮主".equals(chara.getPartyJob())) {
				//必须是帮主才可以修改
				Example example = new Example(Party.class);
				example.createCriteria().andEqualTo("partyName", chara.getPartyName());
				Party party = new Party();
				party.setPartyAnnounce(annouce);
				GameData.that.partyService.updateByExampleSelective(party, example);
				GameCommonUtil.dialogOk("修改帮派宗旨成功。");
			}
		}
	}

	@Override
	public int cmd() {
		return 0x10B6;
	}

}
