package com.fengshen.server.process.combat;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.fengshen.server.data.GameReadTool;
import com.fengshen.server.domain.Chara;
import com.fengshen.server.fight.FightContainer;
import com.fengshen.server.fight.FightManager;
import com.fengshen.server.fight.FightObject;
import com.fengshen.server.fight.FightTeam;
import com.fengshen.server.game.GameData;
import com.fengshen.server.game.GameHandler;
import com.fengshen.server.game.GameObjectChar;
import com.fengshen.server.game.GameObjectCharMng;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

/**
 * 结束动画
 */
@Service
@Slf4j
public class CMD_C_END_ANIMATE implements GameHandler {
	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		int answer = GameReadTool.readInt(buff);
		GameObjectChar gameObjectChar = GameObjectChar.getGameObjectChar();
		Chara chara = gameObjectChar.chara;
		log.info("结束动画:{},--------------{}",answer,chara.name);
		FightContainer fightContainer = FightManager.getFightContainer();
		if (fightContainer == null) {
			return;
		}
		//设置战斗超时机制，4秒钟还未进入下一回合则自动
		String key = "fightTime_"+fightContainer.uid;
		String timeout = GameData.that.redisUtils.get(key);
		//如果战斗结束了
		if (FightManager.isOver(fightContainer)) {
			GameData.that.redisUtils.delete(key);
			FightManager.listFight.remove(fightContainer);
			FightManager.sendOver(fightContainer, false);
			return;
		}
		fightContainer.endTime.set(0);
		gameObjectChar.isEndRound.set(true);
		if(!gameObjectChar.isEndRound.get()) {
			//上回合结束时间
			gameObjectChar.setLastRoundEndTime(System.currentTimeMillis());
		}
		if(timeout == null) {
			GameData.that.redisUtils.set(key, StringUtils.join("", "战斗:",gameObjectChar.chara.name,"isRound:",gameObjectChar.isEndRound.get()), 4);
		}
		for(FightTeam teams:fightContainer.teamList) {
			for(FightObject fightObject:teams.fightObjectList) {
				if(fightObject.type == 1 && fightObject.isGuaiWuHide == 0) {
					GameObjectChar teamGame = GameObjectCharMng.getGameObjectChar(fightObject.fid);
					if(teamGame != null) {
						//结束回合标识为false
						if(!teamGame.isEndRound.get() && !teamGame.isBack.get()) {
							return;
						}
					}
				}
			}
		}
		if (fightContainer.state.compareAndSet(3, 1) || fightContainer.state.get() == 4) {
			FightManager.nextRoundOrSendOver(fightContainer,gameObjectChar);
		}
	}

	@Override
	public int cmd() {
		return 0x2204;
	}

}