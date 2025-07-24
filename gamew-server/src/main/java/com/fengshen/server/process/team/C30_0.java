package com.fengshen.server.process.team;

import org.springframework.stereotype.Service;

import com.fengshen.server.data.GameReadTool;
import com.fengshen.server.domain.Chara;
import com.fengshen.server.game.GameCommonUtil;
import com.fengshen.server.game.GameHandler;
import com.fengshen.server.game.GameObjectChar;
import com.fengshen.server.game.GameObjectCharMng;
import com.fengshen.server.game.GameTeamUtil;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

/**
 * 将队员提升为队长
 * 
 *
 */

@Service
@Slf4j
public class C30_0 implements GameHandler {
	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		String new_leader_id = GameReadTool.readString(buff);
		int type = GameReadTool.readByte(buff);
		GameObjectChar gameObjectChar = GameObjectChar.getGameObjectChar();
		Chara chara = gameObjectChar.chara;
		if(chara.taskMap.get("萝卜桃子大收集") != null) {
			GameCommonUtil.sendTips("你领取了萝卜桃子大收集任务，不允许快捷切换地图");
			return;
		}else {
			GameObjectChar toGame = GameObjectCharMng.getGameObjectChar(Integer.valueOf(new_leader_id));
			if(toGame != null && toGame.chara.taskMap.get("萝卜桃子大收集") != null) {
				GameCommonUtil.sendTips("#Y"+toGame.chara.name+"#n领取了萝卜桃子大收集任务，无法提升为队长");
				return;
			}
		}
		GameTeamUtil.changeTeamLeader(gameObjectChar, Integer.valueOf(new_leader_id));
		log.info("将队员提升为队长、id={},type={}",new_leader_id,type);
	}

	@Override
	public int cmd() {
		return 30;
	}
}