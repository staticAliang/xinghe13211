package com.fengshen.server.fight;

import java.util.ArrayList;
import java.util.List;

import com.fengshen.server.data.vo.Vo_64971_0;
import com.fengshen.server.data.vo.Vo_7653_0;
import com.fengshen.server.data.vo.fight.Vo_C_ACTION;
import com.fengshen.server.data.vo.fight.Vo_C_END_ACTION;
import com.fengshen.server.data.write.M7653_0;
import com.fengshen.server.data.write.fight.c.MSG_C_ACTION;
import com.fengshen.server.data.write.fight.c.MSG_C_END_ACTION;
import com.fengshen.server.data.write.fight.c.MSG_C_REFRESH_PET_LIST;
import com.fengshen.server.data.write.fight.c.MSG_C_SET_FIGHT_PET;
import com.fengshen.server.domain.JiNeng;
import com.fengshen.server.game.GameObjectCharMng;

// 召回技能
public class ZhaohuiSkill implements FightSkill {
	@Override
	public List<FightResult> doSkill(FightContainer fightContainer, FightRequest fightRequest, JiNeng jiNeng) {
		FightObject fightObject = FightManager.getFightObject(fightContainer, fightRequest.vid); // 获取要召回的宠物
		if (fightObject == null || fightObject.type != 2) {
			FightManager.defenseAction(fightContainer, fightRequest);
			return null;
		}
		Vo_C_ACTION vo_19959_0 = new Vo_C_ACTION();
		vo_19959_0.round = fightContainer.round;
		vo_19959_0.aid = fightRequest.id;
		vo_19959_0.action = fightRequest.action;
		vo_19959_0.vid = fightRequest.vid;
		vo_19959_0.para = fightRequest.para;
		FightManager.send(fightContainer, new MSG_C_ACTION(), vo_19959_0);
		Vo_7653_0 vo_7653_0 = new Vo_7653_0();
		vo_7653_0.id = fightObject.fid;
		FightManager.send(fightContainer, new M7653_0(), vo_7653_0);
		Vo_64971_0 vo_64971_0 = new Vo_64971_0();
		vo_64971_0.id = fightObject.id;
		vo_64971_0.haveCalled = 0;
		FightManager.send(fightContainer, new MSG_C_SET_FIGHT_PET(), vo_64971_0);
		FightManager.remove(fightContainer, fightObject);
		vo_64971_0 = new Vo_64971_0();
		vo_64971_0.count = 1;
		vo_64971_0.id = fightObject.id;
		vo_64971_0.haveCalled = 0;
		GameObjectCharMng.getGameObjectChar(fightObject.cid).sendOne(new MSG_C_REFRESH_PET_LIST(), vo_64971_0);
		List<FightResult> resultList = new ArrayList<FightResult>();
		FightManager.send(fightContainer, new MSG_C_END_ACTION(), new Vo_C_END_ACTION(fightRequest.id));
		return resultList;
	}

	@Override
	public int getStateType() {
		return 0;
	}
}
