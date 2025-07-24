package com.fengshen.server.process.party;

import org.springframework.stereotype.Service;

import com.fengshen.db.domain.Party;
import com.fengshen.server.data.GameReadTool;
import com.fengshen.server.domain.Chara;
import com.fengshen.server.game.GameData;
import com.fengshen.server.game.GameHandler;
import com.fengshen.server.game.GameObjectChar;
import com.fengshen.server.game.GameUtil;
import com.qcloud.cos.utils.StringUtils;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import tk.mybatis.mapper.entity.Example;

/**
 * 帮派自动申请设置
 * 
 *
 */
@Service
@Slf4j
public class CMD_PARTY_REJECT_LEVEL implements GameHandler {

	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		int minLevel = GameReadTool.readShort(buff);
		int maxLevel = GameReadTool.readShort(buff);
		int minTao = GameReadTool.readInt(buff);
		int isWork = GameReadTool.readByte(buff);
		int isChange = GameReadTool.readByte(buff);
		GameObjectChar gameObjectChar = GameObjectChar.getGameObjectChar();
		Chara chara = gameObjectChar.chara;
		if(!StringUtils.isNullOrEmpty(chara.getPartyName())) {
			if(!"帮主".equals(chara.getPartyJob())) {
				GameUtil.sendMeTips("只有帮主才可操作");
				return;
			}
			int autoLevel = minLevel*100000+isWork;
			if(isWork == 1) {
				GameUtil.sendMeTips("帮派自动接受开启");
			}else {
				GameUtil.sendMeTips("帮派自动接受关闭");
			}
			//更新帮派信息
			Party party = new Party();
			party.setAutoAcceptLevel(autoLevel);
			party.setMinTao(minTao);
			Example example = new Example(Party.class);
			example.createCriteria().andEqualTo("partyName", chara.getPartyName());
			GameData.that.partyService.updateByExampleSelective(party, example);
		}
		
		log.info("帮派自动申请设置, minLevel={}, maxLevel={},minTao={},isWork={}, isChange={}",minLevel,maxLevel,minTao,isWork,isChange);
	}

	@Override
	public int cmd() {
		return 0x8010;
	}

}
