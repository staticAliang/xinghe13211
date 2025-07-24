package com.fengshen.server.fight;

import java.util.List;

import com.fengshen.server.data.vo.fight.Vo_ACCEPT_MAGIC_HIT;
import com.fengshen.server.data.vo.fight.Vo_ACCEPT_MAGIC_HIT.Info;
import com.fengshen.server.data.vo.fight.Vo_C_ACCEPT_HIT;
import com.fengshen.server.data.vo.fight.Vo_C_ACTION;
import com.fengshen.server.data.vo.fight.Vo_C_END_ACTION;
import com.fengshen.server.data.write.M64991_0;
import com.fengshen.server.data.write.fight.c.MSG_C_ACCEPT_HIT;
import com.fengshen.server.data.write.fight.c.MSG_C_ACCEPT_MAGIC_HIT;
import com.fengshen.server.data.write.fight.c.MSG_C_ACTION;
import com.fengshen.server.data.write.fight.c.MSG_C_END_ACTION;
import com.fengshen.server.domain.JiNeng;
import com.fengshen.server.domain.ZbAttribute;

public class FuzhuTu231Skill extends FightRoundSkill {
	
	
	
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
		Vo_C_ACCEPT_HIT vo_19945_0 = new Vo_C_ACCEPT_HIT();
		vo_19945_0.id = fightRequest.vid;
		vo_19945_0.hid = fightRequest.id;
		vo_19945_0.para_ex = 0;
		vo_19945_0.missed = 1;
		vo_19945_0.para = 0;
		vo_19945_0.damage_type = 2;
		FightManager.send(fightContainer, new MSG_C_ACCEPT_HIT(), vo_19945_0);

		Vo_ACCEPT_MAGIC_HIT vo_64989_0 = new Vo_ACCEPT_MAGIC_HIT();
		vo_64989_0.hid = fightRequest.id;
		vo_64989_0.damageType = 2;

		List<FightObject> targetList = FightManager.findTarget(fightContainer, fightRequest, 2, jiNeng.range);
		for (FightObject fightObject : targetList) {
			vo_64989_0.infos.add(new Info(fightObject.fid,1));
		}
		FightManager.send(fightContainer, new MSG_C_ACCEPT_MAGIC_HIT(), vo_64989_0);

		for (FightObject fightObject : targetList) {
			ZbAttribute zbAttribute = new ZbAttribute();
			zbAttribute.id = fightObject.id;
			//按等级给定相应的数值
			int magDodge = (int) ((jiNeng.range*0.1)*(jiNeng.skill_level+jiNeng.level_improved));
			if(magDodge > 50) {
				//最高只能是
				magDodge = 50;
			}
			zbAttribute.jinguang_zhaxian_counter_att_rate = magDodge;
			
			FightManager.send(fightContainer, new M64991_0(), zbAttribute);

			fightObject.addBuffState(fightContainer, this.getStateType());
			FuzhuTu231Skill that = new FuzhuTu231Skill();
			fightObject.addSkill(that);
			that.buffObject = fightObject;
			that.removeRound = fightContainer.round + jiNeng.skillRound - 1;
			that.buffObject.magDodgeExt = magDodge;
		}
		FightManager.send(fightContainer, new MSG_C_END_ACTION(), new Vo_C_END_ACTION(fightRequest.id));
		return null;
	}

	@Override
	protected void doRoundSkill() {
	}

	@Override
	protected void doDisappear() {
		
		this.buffObject.magDodgeExt = 0;
	}

	@Override
	public int getStateType() {
		return 134912;
	}
}
