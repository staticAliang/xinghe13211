package com.fengshen.server.fight;

import java.util.List;

import com.fengshen.server.data.vo.fight.Vo_C_ACTION;
import com.fengshen.server.data.vo.fight.Vo_C_END_ACTION;
import com.fengshen.server.data.write.fight.c.MSG_C_ACTION;
import com.fengshen.server.data.write.fight.c.MSG_C_END_ACTION;
import com.fengshen.server.domain.JiNeng;

// 防御技能
public class DefenseSkill extends FightRoundSkill {
	@Override
	public List<FightResult> doSkill(FightContainer fightContainer, FightRequest fightRequest, JiNeng jiNeng) {
		Vo_C_ACTION vo_19959_0 = new Vo_C_ACTION();
		vo_19959_0.round = fightContainer.round;
		vo_19959_0.aid = fightRequest.id;
		vo_19959_0.action = fightRequest.action;
		vo_19959_0.vid = fightRequest.vid;
		vo_19959_0.para = fightRequest.para;
		FightManager.send(fightContainer, new MSG_C_ACTION(), vo_19959_0);

		//技能说话
		FightManager.autoTalkAction(fightContainer, fightRequest);
		FightObject fightObject = FightManager.getFightObject(fightContainer, fightRequest.id);
		fightObject.addBuffState(fightContainer, this.getStateType());
		fightObject.addSkill(this);
		fightObject.fangyu_ext = fightObject.fangyu * 2;
		this.buffObject = fightObject;
		
		FightManager.send(fightContainer, new MSG_C_END_ACTION(), new Vo_C_END_ACTION(fightRequest.id));
		return null;
	}

	@Override
	protected void doRoundSkill() {
	}

	@Override
	protected void doDisappear() {
		this.buffObject.fangyu_ext = 0;
	}

	@Override
	public int getStateType() {
		return 36608;
	}
}
