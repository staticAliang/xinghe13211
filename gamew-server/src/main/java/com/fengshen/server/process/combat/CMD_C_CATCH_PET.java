package com.fengshen.server.process.combat;

import org.springframework.stereotype.Service;

import com.fengshen.server.data.vo.fight.Vo_SELECT_COMMAND;
import com.fengshen.server.data.write.fight.MSG_SELECT_COMMAND;
import com.fengshen.server.domain.Chara;
import com.fengshen.server.fight.FightContainer;
import com.fengshen.server.fight.FightManager;
import com.fengshen.server.fight.FightObject;
import com.fengshen.server.fight.FightRequest;
import com.fengshen.server.game.GameHandler;
import com.fengshen.server.game.GameObjectChar;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

/**
 * 抓宠物
 * 
 *
 */
@Service
@Slf4j
public class CMD_C_CATCH_PET implements GameHandler {
	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		log.info("抓宠物");
		GameObjectChar session = GameObjectChar.getGameObjectChar();
		Chara chara = session.chara;
		FightRequest fr = new FightRequest();
		fr.id = chara.id;
		fr.action = 9;
		fr.vid = 0;
		FightContainer fightContainer = FightManager.getFightContainer();
		FightObject fightObject = FightManager.getFightObject(fightContainer, chara.id);
		FightObject fightObjectPet = FightManager.getFightObjectPet(fightContainer, fightObject);
		if (fightObjectPet == null) {
			Vo_SELECT_COMMAND vo_53715_0 = new Vo_SELECT_COMMAND();
			vo_53715_0.attacker_id = chara.id;
			vo_53715_0.victim_id = 0;
			vo_53715_0.action = 7;
			vo_53715_0.no = 0;
			GameObjectChar.send(new MSG_SELECT_COMMAND(), vo_53715_0);
		}
		FightManager.addRequest(fightContainer, fr);
	}

	@Override
	public int cmd() {
		return 4616;
	}
}
