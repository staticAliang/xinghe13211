package com.fengshen.server.fight;

import java.util.ArrayList;
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

/**
 * 木系障碍技能
 * 
 *
 */
public class ZhangaiMu71Skill extends FightRoundSkill {
	
	private int xueliang;

	@Override
	public List<FightResult> doSkill(FightContainer fightContainer, FightRequest fightRequest, JiNeng jiNeng) {
		List<FightResult> resultList = new ArrayList<FightResult>();
		FightObject attFightObject = FightManager.getFightObject(fightContainer, fightRequest.id);

		Vo_C_ACTION vo_19959_0 = new Vo_C_ACTION();
		vo_19959_0.round = fightContainer.round;
		vo_19959_0.aid = fightRequest.id;
		vo_19959_0.action = fightRequest.action;
		vo_19959_0.vid = fightRequest.vid;
		vo_19959_0.para = fightRequest.para;
		FightManager.send(fightContainer, new MSG_C_ACTION(), vo_19959_0);
		//技能说话
		FightManager.autoTalkAction(fightContainer, fightRequest);
		boolean fabao = true;
		FightFabaoSkill fabaoSkill = attFightObject.getFabaoSkill();
		if (fabaoSkill != null && fabaoSkill.getStateType() == 8398 && fabaoSkill.isActive()) {
			fabao = false;
		}
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
		List<FightObject> targetList = FightManager.findTarget(fightContainer, fightRequest, 3842, jiNeng.range);
		for (FightObject fightObject : targetList) {
			vo_64989_0.infos.add(new Info(fightObject.fid,1));
		}
		FightManager.send(fightContainer, new MSG_C_ACCEPT_MAGIC_HIT(), vo_64989_0);
		for (FightObject fightObject : targetList) {
			ZhangaiMu71Skill that = new ZhangaiMu71Skill();
			if (GameUtil.zaActiveJudge(attFightObject, fightObject)) {
				fightObject.addBuffState(fightContainer, this.getStateType());
				fightObject.addSkill(that);
				that.buffObject = fightObject;
				//回合数根据对方道行判断
				int differTao = (attFightObject.friend/360)-(fightObject.friend/360);
				int removeRound = GameUtil.getRemoveRound(differTao, jiNeng.skillRound);
				that.removeRound = fightContainer.round + removeRound;
				that.fightContainer = fightContainer;
				int hurt = jiNeng.level_improved+jiNeng.skill_level*200;
				int showhurt = fightObject.reduceShengming(hurt, fabao);
				that.xueliang = hurt;
				FightResult fightResult = new FightResult();
				fightResult.id = fightRequest.id;
				fightResult.vid = fightObject.fid;
				fightResult.point = -showhurt;
				fightResult.effect_no = 0;
				fightResult.damage_type = 4;
				FightManager.send_LIFE_DELTA(fightContainer, fightResult);
			}
		}
		FightManager.send(fightContainer, new MSG_C_END_ACTION(), new Vo_C_END_ACTION(fightRequest.id));
		return resultList;
	}

	@Override
	protected void doRoundSkill() {
		FightObject buffObject = this.buffObject;
		if(buffObject != null) {
			this.xueliang /= 2;
			this.xueliang = buffObject.reduceShengming(this.xueliang, false);
			if (buffObject.type == 1 || buffObject.type == 2) {
				buffObject.update(this.fightContainer);
			}
			FightResult fightResult = new FightResult();
			fightResult.id = buffObject.fid;
			fightResult.vid = buffObject.fid;
			fightResult.point = -this.xueliang;
			fightResult.effect_no = 0;
			fightResult.damage_type = 4;
			FightManager.send_LIFE_DELTA(this.fightContainer, fightResult);
		}
		
	}

	@Override
	protected void doDisappear() {
	}

	@Override
	public int getStateType() {
		return 3842;
	}
}
