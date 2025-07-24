package com.fengshen.server.fight;

import java.util.List;

import com.fengshen.server.data.vo.fight.Vo_ACCEPT_MAGIC_HIT;
import com.fengshen.server.data.vo.fight.Vo_ACCEPT_MAGIC_HIT.Info;
import com.fengshen.server.data.vo.fight.Vo_C_ACCEPT_HIT;
import com.fengshen.server.data.vo.fight.Vo_C_ACTION;
import com.fengshen.server.data.vo.fight.Vo_C_END_ACTION;
import com.fengshen.server.data.write.fight.c.MSG_C_ACCEPT_HIT;
import com.fengshen.server.data.write.fight.c.MSG_C_ACCEPT_MAGIC_HIT;
import com.fengshen.server.data.write.fight.c.MSG_C_ACTION;
import com.fengshen.server.data.write.fight.c.MSG_C_END_ACTION;
import com.fengshen.server.domain.JiNeng;

/**
 * 如意圈、神龙罩、乾坤罩
 * 
 * 
 *
 */
public class TianJiSqrSkill extends FightRoundSkill {

	private int stateType;

	@Override
	public List<FightResult> doSkill(FightContainer fightContainer, FightRequest fightRequest, JiNeng jiNeng) {
		Vo_C_ACTION vo_19959_0 = new Vo_C_ACTION();
		vo_19959_0.round = fightContainer.round;
		vo_19959_0.aid = fightRequest.id;
		vo_19959_0.action = fightRequest.action;
		vo_19959_0.vid = fightRequest.vid;
		vo_19959_0.para = fightRequest.para;
		// MSG_C_ACTION
		FightManager.send(fightContainer, new MSG_C_ACTION(), vo_19959_0);
		//技能说话
		FightManager.autoTalkAction(fightContainer, fightRequest);
		Vo_C_ACCEPT_HIT vo_19945_0 = new Vo_C_ACCEPT_HIT();
		vo_19945_0.id = fightRequest.vid;
		vo_19945_0.hid = fightRequest.id;
		vo_19945_0.para_ex = 0;
		vo_19945_0.missed = 1;
		vo_19945_0.para = 0;
		vo_19945_0.damage_type = 2;
		// MSG_C_ACCEPT_HIT
		FightManager.send(fightContainer, new MSG_C_ACCEPT_HIT(), vo_19945_0);

		Vo_ACCEPT_MAGIC_HIT vo_64989_0 = new Vo_ACCEPT_MAGIC_HIT();
		vo_64989_0.hid = fightRequest.id;
		vo_64989_0.damageType = 2;

		List<FightObject> targetList = FightManager.findTarget(fightContainer, fightRequest, 2, jiNeng.range);
		for (FightObject fightObject : targetList) {
			vo_64989_0.infos.add(new Info(fightObject.fid,1));
		}
		FightManager.send(fightContainer, new MSG_C_ACCEPT_MAGIC_HIT(), vo_64989_0);


		for (FightObject fightObject2 : targetList) {
			fightObject2.getRoundSkill().clear();
			int stateType = 0x80000000;
			if (fightRequest.para == 259) {
				// 乾坤罩
				stateType = 0x09000000;
			} else if (fightRequest.para == 260) {
				// 神龙罩
				stateType = 0x40000000;
			} else if (fightRequest.para == 264) {
				stateType = 620;
			}
			this.setStateType(stateType);
			this.skillLevel = jiNeng.skill_level;
			fightObject2.addBuffState(fightContainer, stateType);
			TianJiSqrSkill that = new TianJiSqrSkill();
			that.setStateType(stateType);
			that.skillLevel = jiNeng.skill_level;
			fightObject2.addSkill(that);
			that.buffObject = fightObject2;
			that.removeRound = fightContainer.round + jiNeng.skillRound - 1;
			that.fightContainer = fightContainer;
		}
		FightManager.send(fightContainer, new MSG_C_END_ACTION(), new Vo_C_END_ACTION(fightRequest.id));
		return null;
	}

	@Override
	protected void doRoundSkill() {

	}

	@Override
	protected void doDisappear() {

	}

	@Override
	public int getStateType() {
		return this.stateType;
	}

	public void setStateType(int stateType) {
		this.stateType = stateType;
	}
}
