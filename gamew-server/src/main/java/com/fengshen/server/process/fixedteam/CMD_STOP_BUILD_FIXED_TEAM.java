package com.fengshen.server.process.fixedteam;

import org.springframework.stereotype.Service;

import com.fengshen.server.data.write.fixedteam.MSG_CANCEL_BUILD_FIXED_TEAM;
import com.fengshen.server.domain.Chara;
import com.fengshen.server.game.GameCommonUtil;
import com.fengshen.server.game.GameHandler;
import com.fengshen.server.game.GameObjectChar;
import com.fengshen.server.game.GameObjectCharMng;
import com.fengshen.server.game.GameUtil;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CMD_STOP_BUILD_FIXED_TEAM implements GameHandler {

	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		log.info("取消缔结固定队伍");
		GameObjectChar gameObjectChar = GameObjectChar.getGameObjectChar();
		Chara chara = gameObjectChar.chara;
		if(GameCommonUtil.isNotGameTeam(gameObjectChar.gameTeam)) {
			for(Chara team:gameObjectChar.gameTeam.duiwu) {
				if(team.id != chara.id) {
					GameObjectChar teamGameObjectChar = GameObjectCharMng.getGameObjectChar(team.id);
					teamGameObjectChar.sendOne(new MSG_CANCEL_BUILD_FIXED_TEAM(),null);
					GameCommonUtil.sendTips("#Y"+chara.name+"#n取消缔结固定队伍", teamGameObjectChar);
				}
			}
			GameUtil.sendMeTips("已取消缔结固定队伍");
		}
	}

	@Override
	public int cmd() {
		return 0xD1FA;
	}

}
