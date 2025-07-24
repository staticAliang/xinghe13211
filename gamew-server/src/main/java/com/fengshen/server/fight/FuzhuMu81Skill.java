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
import lombok.extern.slf4j.Slf4j;

/**
 * 全部是木系辅助技能，加气血值和复活效果
 * 队伍里按血最少到高来分配，血高者，没机会享受到木的辅助、ps:点击目标必然会得到辅助
 * 
 */
@Slf4j
public class FuzhuMu81Skill extends FightRoundSkill {
	private boolean use;
	
	public FuzhuMu81Skill() {
		this.use = false;
	}

	@Override
	public List<FightResult> doSkill(FightContainer fightContainer, FightRequest fightRequest, JiNeng jiNeng) {
		List<FightObject> targetList = FightManager.findTarget(fightContainer, fightRequest, 528128, jiNeng.range);
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
		
		
		for (FightObject fightObject : targetList) {
			vo_64989_0.infos.add(new Info(fightObject.fid,1));
		}
		FightManager.send(fightContainer, new MSG_C_ACCEPT_MAGIC_HIT(), vo_64989_0);
		
		for (FightObject fightObject : targetList) {
			fightObject.addBuffState(fightContainer, this.getStateType());
			FuzhuMu81Skill that = new FuzhuMu81Skill();
			fightObject.addSkill(that);
			that.buffObject = fightObject;
			that.removeRound = fightContainer.round + jiNeng.skillRound;
			that.fightContainer = fightContainer;
			//已经死亡了
			if (fightObject.isDead()) {
				that.doRoundSkill(true);
			} else {
				int skillIndex = BattleUtils.getSkillIndex(jiNeng.skill_no)+1;
				int xueliang = (jiNeng.skill_level+jiNeng.level_improved)*47*2*skillIndex;
				fightObject.addShengming(xueliang);
				fightObject.update(fightContainer);

				FightResult fightResult = new FightResult();
				fightResult.id = fightRequest.id;
				fightResult.vid = fightObject.fid;
				fightResult.point = xueliang;
				fightResult.effect_no = 10005;
				fightResult.damage_type = 0;
				fightObject.isHideLifeEffect = false;
				FightManager.send_LIFE_DELTA(fightContainer, fightResult);
			}
		}
		FightManager.send(fightContainer, new MSG_C_END_ACTION(), new Vo_C_END_ACTION(fightRequest.id));
		return null;
	}

	@Override
	protected void doRoundSkill() {
		doRoundSkill(this.use);
	}
	
	public void doRoundSkill(boolean use) {
		//如果是使用技能复活的话.
		FightObject fightObject = this.buffObject;
		if(fightObject != null) {
			if(use) {
				fightObject.state.set(1);
				int blood = fightObject.max_shengming / 5;
				fightObject.shengming = blood;
				Vo_C_ACTION vo_19959_0 = new Vo_C_ACTION();
				vo_19959_0.round = fightContainer.round;
				vo_19959_0.aid = fightObject.fid;
				vo_19959_0.action = 41;
				vo_19959_0.vid = 0;
				vo_19959_0.para = 0;
				FightManager.send(fightContainer, new MSG_C_ACTION(), vo_19959_0);
				fightObject.revive(this.fightContainer);
				FightManager.send(fightContainer, new MSG_C_END_ACTION(), new Vo_C_END_ACTION(fightObject.fid));
				return;
			}
			//如果有心的情况下,并且已经死亡了
			if (!use && (fightObject.state.get() == 2 ||fightObject.state.get() == 3)) {
				//让木系辅助技能消失
				if(fightObject.isDead()) {
					fightObject.removeBuffSK(fightContainer, this.getStateType());
				}
				fightObject.state.set(1);
				int blood = fightObject.max_shengming / 5;
				fightObject.shengming = blood;
				Vo_C_ACTION vo_19959_0 = new Vo_C_ACTION();
				vo_19959_0.round = fightContainer.round;
				vo_19959_0.aid = fightObject.fid;
				vo_19959_0.action = 41;
				vo_19959_0.vid = 0;
				vo_19959_0.para = 0;
				FightManager.send(fightContainer, new MSG_C_ACTION(), vo_19959_0);
				fightObject.revive(this.fightContainer);
				FightManager.send(fightContainer, new MSG_C_END_ACTION(), new Vo_C_END_ACTION(fightObject.fid));
				return;
			}

			// 这里设置每回合初对活着的角色回血
			int xueliang = 5000;
			int diff = fightObject.max_shengming - fightObject.shengming; // 已损失的生命值
			xueliang = (int) (diff * 0.1); // 回复已损失生命值的10%
			
			xueliang = fightObject.addShengming(xueliang);
			fightObject.update(fightContainer);

			FightResult fightResult = new FightResult();
			fightResult.id = fightObject.fid;
			fightResult.vid = fightObject.fid;
			fightResult.point = xueliang;
			fightResult.effect_no = 10005;
			fightResult.damage_type = 0;
			FightManager.send_LIFE_DELTA(this.fightContainer, fightResult);
		}
	}

	@Override
	protected void doDisappear() {
	}

	@Override
	public int getStateType() {
		return 528128;
	}
}