package com.fengshen.server.fight;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.fengshen.server.data.vo.Vo_8711_0;
import com.fengshen.server.data.vo.fight.Vo_C_ACTION;
import com.fengshen.server.data.vo.fight.Vo_C_END_ACTION;
import com.fengshen.server.data.write.M8711_0;
import com.fengshen.server.data.write.fight.c.MSG_C_ACTION;
import com.fengshen.server.data.write.fight.c.MSG_C_END_ACTION;
import com.fengshen.server.domain.JiNeng;
import com.fengshen.server.game.GameCommonUtil;
import com.fengshen.server.game.GameObjectCharMng;

// 战斗逃跑的逻辑
public class FleeSkill implements FightSkill {
	@Override
	public List<FightResult> doSkill(FightContainer fightContainer, FightRequest fightRequest, JiNeng jiNeng) {

		List<FightResult> resultList = new ArrayList<FightResult>();
		int id = fightRequest.id;
		FightObject fightObject = FightManager.getFightObject(id);
		FightObject fightObjectPet = FightManager.getFightObjectPet(fightContainer, fightObject);
		fightObject.run = true;

		//被强P的人,允许逃跑.但是成功率只有10
		if(!fightObject.isDead()&&
				GameObjectCharMng.getGameObjectChar(fightObject.id).action.equals("passiveForcePk")) {
			int random = new Random().nextInt(100)+1;
			if(random>10) {
				fightObject.run = false;
				GameCommonUtil.sendTips("强制PK逃跑失败。",fightObject.id);
			}
		}
		
		Vo_C_ACTION vo_19959_0 = new Vo_C_ACTION();
		vo_19959_0.round = fightContainer.round;
		vo_19959_0.aid = fightRequest.id;
		vo_19959_0.action = fightRequest.action;
		vo_19959_0.vid = fightRequest.vid;
		vo_19959_0.para = fightRequest.para;
		FightManager.send(fightContainer, new MSG_C_ACTION(), vo_19959_0);

		M8711_0 msg = new M8711_0();
		Vo_8711_0 vo_8711_0 = new Vo_8711_0();
		vo_8711_0.id = id;
		vo_8711_0.success = 0;
		vo_8711_0.die = 0;
		FightManager.send(fightContainer, msg, vo_8711_0);
		if(fightObject.run) {
			if (fightObjectPet != null) {
				msg = new M8711_0();
				vo_8711_0 = new Vo_8711_0();
				vo_8711_0.id = fightObjectPet.id;
				vo_8711_0.success = 0;
				vo_8711_0.die = 0;
				FightManager.send(fightContainer, msg, vo_8711_0);
			}
		}
		FightManager.send(fightContainer, new MSG_C_END_ACTION(), new Vo_C_END_ACTION(fightRequest.id));
		return resultList;
	}

	@Override
	public int getStateType() {
		return 0;
	}
}
