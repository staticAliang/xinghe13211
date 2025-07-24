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
import com.fengshen.server.game.GameUtil;

// 金系的障碍技能
public class ZhangAiJin21Skill extends FightRoundSkill {
	@Override
	public List<FightResult> doSkill(FightContainer fightContainer, FightRequest fightRequest, JiNeng jiNeng) {
		FightObject attcObject = FightManager.getFightObject(fightContainer, fightRequest.id);

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
		List<FightObject> targetList = FightManager.findTarget(fightContainer, fightRequest, 3848, jiNeng.range);

		for (FightObject fightObject : targetList) {
			vo_64989_0.infos.add(new Info(fightObject.fid,1));
		}
		FightManager.send(fightContainer, new MSG_C_ACCEPT_MAGIC_HIT(), vo_64989_0);


		for (FightObject fightObject2 : targetList) {
			if (GameUtil.zaActiveJudge(attcObject, fightObject2)) {
				// 在这里对战斗对象添加障碍效果
				fightObject2.addBuffState(fightContainer, this.getStateType());
				ZhangAiJin21Skill that = new ZhangAiJin21Skill();
				fightObject2.addSkill(that);
				that.buffObject = fightObject2;
				//回合数根据对方道行判断
				int differTao = (attcObject.friend/360)-(fightObject2.friend/360);
				int removeRound = GameUtil.getRemoveRound(differTao, jiNeng.skillRound);
				that.removeRound = fightContainer.round + removeRound;
			}
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
		return 3848;
	}
}
