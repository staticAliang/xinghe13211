package com.fengshen.server.fight.tj;

import java.util.List;

import com.fengshen.server.data.vo.Vo_15855_0;
import com.fengshen.server.data.vo.fight.*;
import com.fengshen.server.data.vo.fight.Vo_ACCEPT_MAGIC_HIT.Info;
import com.fengshen.server.data.write.M15855_0;
import com.fengshen.server.data.write.fight.c.*;
import com.fengshen.server.domain.JiNeng;
import com.fengshen.server.fight.FightContainer;
import com.fengshen.server.fight.FightManager;
import com.fengshen.server.fight.FightObject;
import com.fengshen.server.fight.FightRequest;
import com.fengshen.server.fight.FightResult;
import com.fengshen.server.fight.FightRoundSkill;
import com.fengshen.server.game.GameUtil;

/**
 * 舍命一击
 * @author aaa
 *
 */
public class FightSheMingYiJi extends FightRoundSkill {


	@Override
	public List<FightResult> doSkill(FightContainer fightContainer, FightRequest fightRequest, JiNeng jiNeng) {
		// 技能的攻击人数
		int attaNum = jiNeng.range;
		// 攻击发起人
		FightObject attFightObject = FightManager.getFightObject(fightContainer, fightRequest.id);
		// 获取技能要打的对象
		List<FightObject> targetList = FightManager.findTarget(fightContainer, fightRequest, -1, attaNum);

		// 发送战斗回合、fid、vid、使用技能等信息
		Vo_C_ACTION vo_19959_0 = new Vo_C_ACTION();
		vo_19959_0.round = fightContainer.round;
		vo_19959_0.aid = fightRequest.id;
		vo_19959_0.action = fightRequest.action;
		vo_19959_0.vid = fightRequest.vid;
		vo_19959_0.para = fightRequest.para; // 技能编号
		FightManager.send(fightContainer, new MSG_C_ACTION(), vo_19959_0);
		
		//技能说话
		FightManager.autoTalkAction(fightContainer, fightRequest);
		//上前攻击
		Vo_C_ACCEPT_HIT vo_19945_0 = new Vo_C_ACCEPT_HIT();
		vo_19945_0.id = fightRequest.id;
		vo_19945_0.hid = fightRequest.id;
		vo_19945_0.para_ex = 0;
		vo_19945_0.missed = 1;
		vo_19945_0.para = 0;
		vo_19945_0.damage_type = 2;
		FightManager.send(fightContainer, new MSG_C_ACCEPT_HIT(), vo_19945_0);

		// 发送到前端
		Vo_ACCEPT_MAGIC_HIT vo_64989_0 = new Vo_ACCEPT_MAGIC_HIT();
		vo_64989_0.hid = fightRequest.id;
		vo_64989_0.damageType = 2;
		vo_64989_0.infos.add(new Info(targetList.get(0).fid, 1));
		FightManager.send(fightContainer, new MSG_C_ACCEPT_MAGIC_HIT(), vo_64989_0);
		/*头顶掉血动画*/
		Vo_C_LIFE_DELTA vo_15857_0 = new Vo_C_LIFE_DELTA();
		vo_15857_0.id = fightRequest.id;
		vo_15857_0.hitter_id = fightRequest.id;
		vo_15857_0.point = -(attFightObject.shengming-1);
		vo_15857_0.effect_no = 0;
		vo_15857_0.damage_type = 2;
		FightManager.send(fightContainer, new MSG_C_LIFE_DELTA(), vo_15857_0);

		int subMana = 0;
		//寻找被攻击的对象
		boolean isSuccess = false;
		for(FightObject fight:targetList) {
			//计算道行是否足够
			if (GameUtil.zaActiveJudge(attFightObject, fight)) {
				isSuccess = true;
				subMana = fight.mofa;
				//根据血量扣除对方蓝
//				if(jiNeng.skill_level>=1 && jiNeng.skill_level<=49) {
//					subMana = attFightObject.shengming*1;
//				}else if(jiNeng.skill_level>=50 && jiNeng.skill_level<=89) {
//					subMana = (int) (attFightObject.shengming*1.5);
//				}else if(jiNeng.skill_level>=90 && jiNeng.skill_level<=129) {
//					subMana = attFightObject.shengming*2;
//				}else if(jiNeng.skill_level>=130 && jiNeng.skill_level<=159) {
//					subMana = (int) (attFightObject.shengming*2.5);
//				}else if(jiNeng.skill_level>=160 && jiNeng.skill_level<=179){
//					subMana = attFightObject.shengming*3;
//				}else if(jiNeng.skill_level>180){
//					subMana = attFightObject.shengming*4;
//				}
//				if(subMana>fight.mofa) {

//				}
				FightManager.costMofa(fightContainer, fight, subMana);
			}
		}
		if(subMana>0){
			/*头顶掉蓝动画*/
			Vo_15855_0  vo_15855_0 = new Vo_15855_0();
			vo_15855_0.hitter_id = fightRequest.id;
			vo_15855_0.id = fightRequest.vid;
			vo_15855_0.point = -subMana;
			vo_15855_0.effect_no=0;
			FightManager.send(fightContainer,new M15855_0(),vo_15855_0 );
		}
		if(isSuccess) {
			//让当前使用舍命一击的宠物死亡
			attFightObject.reduceShengming(attFightObject.shengming-1, false);
			//读取状态
			FightResult fightResult = new FightResult();
			fightResult.id = attFightObject.id;
			fightResult.vid = fightRequest.id;
			fightResult.effect_no = 0;
			fightResult.damage_type = 4097;
			fightResult.point = 0;
			FightManager.send_LIFE_DELTA(fightContainer, fightResult);
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
		return 0;
	}

}