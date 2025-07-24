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

/**
 * 辅助金技能
 * 点击目标必然会有效果，其他目标从队伍中按高到低的物伤来分配刀，物伤高者能优先得到辅助。
 * 
 *
 */
public class FuzhuJin31Skill extends FightRoundSkill {
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

		List<FightObject> targetList = FightManager.findTarget(fightContainer, fightRequest, 20224, jiNeng.range);
		for (FightObject fightObject : targetList) {
			vo_64989_0.infos.add(new Info(fightObject.fid, 1));
		}
		FightManager.send(fightContainer, new MSG_C_ACCEPT_MAGIC_HIT(), vo_64989_0);


		for (FightObject fightObject2 : targetList) {
			ZbAttribute zbAttribute = new ZbAttribute();
			zbAttribute.id = fightObject2.id;
			zbAttribute.accurate = 1000 * (jiNeng.skill_no - 30);
			zbAttribute.mana = 1000 * (jiNeng.skill_no - 30);
			FightManager.send(fightContainer, new M64991_0(), zbAttribute);

			fightObject2.addBuffState(fightContainer, this.getStateType());
			FuzhuJin31Skill that = new FuzhuJin31Skill();
			fightObject2.addSkill(that);
			that.buffObject = fightObject2;
			that.removeRound = fightContainer.round + jiNeng.skillRound - 1;
			that.fightContainer = fightContainer;
			int gongjili = (int) BattleUtils.extAdd(jiNeng.skill_level, jiNeng.skill_no);
			that.buffObject.accurate_ext = that.buffObject.accurate * gongjili / 100;
			that.buffObject.fashang_ext = that.buffObject.fashang * gongjili / 100;
		}
		FightManager.send(fightContainer, new MSG_C_END_ACTION(), new Vo_C_END_ACTION(fightRequest.id));
		return null;
	}

	@Override
	protected void doRoundSkill() {
	}

	@Override
	protected void doDisappear() {

		ZbAttribute zbAttribute = new ZbAttribute();
		zbAttribute.id = this.buffObject.id;
		zbAttribute.accurate = 0;
		zbAttribute.mana = 0;
		FightManager.send(this.fightContainer, new M64991_0(), zbAttribute);

	}

	@Override
	public int getStateType() {
		return 20224;
	}
}
