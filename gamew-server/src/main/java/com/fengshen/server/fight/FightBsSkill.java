package com.fengshen.server.fight;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.fengshen.server.data.vo.fight.Vo_ACCEPT_MAGIC_HIT;
import com.fengshen.server.data.vo.fight.Vo_C_ACCEPT_HIT;
import com.fengshen.server.data.vo.fight.Vo_C_ACTION;
import com.fengshen.server.data.vo.fight.Vo_C_END_ACTION;
import com.fengshen.server.data.vo.fight.Vo_ACCEPT_MAGIC_HIT.Info;
import com.fengshen.server.data.write.fight.c.MSG_C_ACCEPT_HIT;
import com.fengshen.server.data.write.fight.c.MSG_C_ACCEPT_MAGIC_HIT;
import com.fengshen.server.data.write.fight.c.MSG_C_ACTION;
import com.fengshen.server.data.write.fight.c.MSG_C_END_ACTION;
import com.fengshen.server.domain.JiNeng;
import com.fengshen.server.game.GameObjectChar;
import com.fengshen.server.game.GameObjectCharMng;
import com.fengshen.server.game.GameUtil;
import com.fengshen.server.util.RandomUtil;

public class FightBsSkill implements FightSkill {

	@Override
	public List<FightResult> doSkill(FightContainer fightContainer, FightRequest fightRequest, JiNeng jiNeng) {

		List<FightResult> resultList = new ArrayList<FightResult>();
		// 技能的攻击人数
		int attaNum = jiNeng.range;
		// 攻击发起人
		FightObject attFightObject = FightManager.getFightObject(fightContainer, fightRequest.id);
		if (attFightObject.type == 2) {
			if (attFightObject.skillRange > 0) {
				attaNum = attFightObject.skillRange;
			}
		}
		// 获取技能要打的对象
		List<FightObject> targetList = FightManager.findTarget(fightContainer, fightRequest, -1, attaNum);
		// 发送战斗回合、fid、vid、使用技能等信息
		Vo_C_ACTION vo_19959_0 = new Vo_C_ACTION();
		vo_19959_0.round = fightContainer.round;
		vo_19959_0.aid = fightRequest.id;
		vo_19959_0.action = 3;
		vo_19959_0.vid = fightRequest.vid;
		vo_19959_0.para = fightRequest.para; // 技能编号
		FightManager.send(fightContainer, new MSG_C_ACTION(), vo_19959_0);
		//技能说话
		FightManager.autoTalkAction(fightContainer, fightRequest);
		int attTimes = 1;
		boolean fabao = true;
		float jiabei = 1.0f;
		FightFabaoSkill fabaoSkill = attFightObject.getFabaoSkill();
		if (fabaoSkill != null) {
			if (fabaoSkill.getStateType() == 8013 && fabaoSkill.isActive()) {
				fabaoSkill.sendEffect(fightContainer);
				attTimes = 2;
				GameUtil.showImg(fightContainer, fightRequest.id, 0, "番天印");
			}
			if (fabaoSkill.getStateType() == 8398 && fabaoSkill.isActive()) {
				fabaoSkill.sendEffect(fightContainer);
				fabao = false;
			}
		}
		int tianshuType = attFightObject.getRandomTianshuType(fightContainer);
		FightTianshuSkill tianshu = null;
		boolean isBiSha = false;
		switch (tianshuType) {
		case 7041:
			//修罗术
			tianshu = attFightObject.isActiveTianshu(fightContainer, 7041);
			if (tianshu != null) {
				attTimes = 2;
				attFightObject.fightRequest = fightRequest;
				tianshu.sendEffect(fightContainer);
			}
			break;
		case 7036:
			//降魔斩
			tianshu = attFightObject.isActiveTianshu(fightContainer, 7036);
			if (tianshu != null) {
				jiabei = 1.5f;
				tianshu.sendEffect(fightContainer);
			}
			break;
		case 7039:
			//怒击
			tianshu = attFightObject.isActiveTianshu(fightContainer, 7039);
			if (tianshu != null) {
				jiabei = 1.5f;
				tianshu.sendEffect(fightContainer);
				isBiSha = true;
			}
			break;
		}
		float ignoreMagDodge = attFightObject.getAttribute(FightAttribtueType.ignore_mag_dodge);
		//上前攻击
		Vo_C_ACCEPT_HIT vo_19945_0 = new Vo_C_ACCEPT_HIT();
		vo_19945_0.id = fightRequest.vid;
		vo_19945_0.hid = fightRequest.id;
		vo_19945_0.para_ex = 0;
		vo_19945_0.missed = 1;
		vo_19945_0.para = 0;
		vo_19945_0.damage_type = 2;
		FightManager.send(fightContainer, new MSG_C_ACCEPT_HIT(), vo_19945_0);

		//保存躲避人的信息
		Map<Integer,Integer> magDodgeInfo = new HashMap<>();
		// 发送到前端
		Vo_ACCEPT_MAGIC_HIT vo_64989_0 = new Vo_ACCEPT_MAGIC_HIT();
		vo_64989_0.hid = fightRequest.id;
		vo_64989_0.damageType = 2;
		for (FightObject fightObject : targetList) {
			if (fightObject == null)
				continue;
			int missed = 1;
			if(RandomUtil.checkMagDodge(fightObject.getAttribute(FightAttribtueType.mag_dodge)+fightObject.magDodgeExt-ignoreMagDodge)) {
				missed = 0;
			}
			magDodgeInfo.put(fightObject.fid, missed);
			vo_64989_0.infos.add(new Info(fightObject.fid, missed));
		}
		FightManager.send(fightContainer, new MSG_C_ACCEPT_MAGIC_HIT(), vo_64989_0);
		// 法术必杀
		float bisha = attFightObject.getAttribute(FightAttribtueType.MSTUNT_RATE);
		//如果出现了怒击或者修罗术效果,则没有必杀效果
		if (jiabei == 1.0F && RandomUtil.checkBisha(bisha)) {
			jiabei = 1.5F;
			isBiSha = true;
		}
		if (new Random().nextBoolean()) {
			if (attFightObject.type == 1) {
				GameObjectChar gameObjectChar = GameObjectCharMng.getGameObjectChar(fightRequest.id);
				if (gameObjectChar != null) {
					// 加倍伤害
					if (gameObjectChar.chara.upgrade_type == 3) {
						GameUtil.showImg(fightContainer, fightRequest.id, 8043, "后发制人");
						jiabei += 0.3F;
					} else if (gameObjectChar.chara.upgrade_type == 4) {
						GameUtil.showImg(fightContainer, fightRequest.id, 8045, "釜底抽薪");
						jiabei += 0.3F;
					}
				}
			}
		}
		
		
		for (FightObject target : targetList) {
			
			int addHurt = BattleUtils.skillAttack(attFightObject.fashang + attFightObject.fashang_ext,
					jiNeng.skill_level, "BS", jiNeng.skill_no);
			int hurt = 0;
			if (target == null) {
				resultList.add(null);
				continue;
			}
			if (target.hasBuffState(3844)) {
				target.removeBuffSK(FightManager.getFightContainer(target.fid), 3844);
			}
			//人物开始攻击了之后，才能播放必杀动画
			if(isBiSha) {
				GameUtil.showImg(fightContainer, target.fid, 2002, "必杀");
			}
			double factor = 0;
			// 总伤害包括基础伤害和技能伤害，还有对方防御等
			hurt = BattleUtils.battle(attFightObject.fashang + attFightObject.fashang_ext, addHurt,
					target.fangyu + target.fangyu_ext);
			hurt = (int) (hurt * (1 - factor));
			// 获取法宝技能
			fabaoSkill = target.getFabaoSkill();
			// 如果有法宝，则此次施法加入法宝伤害
			if (!fabao && fabaoSkill != null && fabaoSkill.getStateType() == 8014 && fabaoSkill.isActive()) {
				fabaoSkill.sendEffect(fightContainer);
				List<FightObject> exclude = new ArrayList<FightObject>();
				exclude.add(attFightObject);
				exclude.add(target);
				FightObject randomObject = FightManager.getRandomObject(fightContainer, exclude);
				int showhurt = 0;
				//如果使用如意圈,伤害不计算
				if(target.getBuffState().contains(0x80000000)) {
					//天生技能
					List<FightRoundSkill> roundSkill = target.getRoundSkill();
					//回合数减一
					for(FightRoundSkill skill:roundSkill) {
						if(skill instanceof TianJiSqrSkill) {
							skill.removeRound--;
							if(skill.removeRound<=0) {
								//移除效果
								target.removeBuffState(fightContainer, 0x80000000);
								target.updateState(fightContainer);
							}
							break;
						}
					}
				}else {
					if(magDodgeInfo.get(target.getFid()) != null && magDodgeInfo.get(target.getFid()) == 1) {
						//魔道点加成
						hurt += getUpgradeMagicScore(attFightObject, hurt);
						//仙道点减伤
						hurt -= getUpgradeImmortalScore(target, hurt);
						//最终加倍
						hurt *= jiabei;
						showhurt = randomObject.reduceShengming(hurt, false);
					}
				}
				if(magDodgeInfo.get(target.getFid()) != null && magDodgeInfo.get(target.getFid()) == 1) {
					FightResult fightResult = new FightResult();
					fightResult.id = fightRequest.id;
					fightResult.vid = randomObject.fid;
					fightResult.point = -showhurt;
					fightResult.effect_no = 0;
					fightResult.damage_type = 2;
					FightManager.send_LIFE_DELTA(fightContainer, fightResult);
				}
			}
			// 没有法宝
			else {
				int showhurt = 0;
				//如果使用如意圈,伤害不计算
				if(target.getBuffState().contains(0x80000000)) {
					List<FightRoundSkill> roundSkill = target.getRoundSkill();
					//回合数减一
					for(FightRoundSkill skill:roundSkill) {
						if(skill instanceof TianJiSqrSkill) {
							skill.removeRound--;
							if(skill.removeRound<=0) {
								//移除效果
								target.removeBuffState(fightContainer, 0x80000000);
								target.updateState(fightContainer);
							}
							break;
						}
					}
				}else {
					if(magDodgeInfo.get(target.getFid()) != null && magDodgeInfo.get(target.getFid()) == 1) {
						//魔道点加成
						hurt += getUpgradeMagicScore(attFightObject, hurt);
						//仙道点减伤
						hurt -= getUpgradeImmortalScore(target, hurt);
						//最终加倍
						hurt *= jiabei;
						showhurt = target.reduceShengming(hurt, fabao);
					}
				}
				if(magDodgeInfo.get(target.getFid()) != null && magDodgeInfo.get(target.getFid()) == 1) {
					FightResult fightResult2 = new FightResult();
					fightResult2.id = fightRequest.id;
					fightResult2.vid = target.fid;
					fightResult2.point = -showhurt;
					fightResult2.effect_no = 0;
					fightResult2.damage_type = 2;
					FightManager.send_LIFE_DELTA(fightContainer, fightResult2);
				}
			}
		}
		if (attTimes == 2) {
			FightManager.send(fightContainer, new MSG_C_END_ACTION(), new Vo_C_END_ACTION(fightRequest.id));
			if(isBiSha) {
				//第二次连击要扣除连击伤害
				jiabei -= 1.5F;
			}
			//如果有连击话重新初始化
			magDodgeInfo.clear();
			if (resultList != null) {
				for (FightResult fightResult3 : resultList) {
					if (fightResult3 == null)
						continue;
					FightManager.send_LIFE_DELTA(fightContainer, fightResult3);
				}
			}
			Iterator<FightObject> iterator = targetList.iterator();
			int remove = 0;
			while (iterator.hasNext()) {
				FightObject next = iterator.next();
				if(next != null) {
					if (next.isDead()) {
						iterator.remove();
						++remove;
					}
				}
			}
			List<FightObject> fightObjectList = FightManager.getFightTeamDM(fightContainer,
					attFightObject.fid).fightObjectList;
			for (FightObject fightObject2 : fightObjectList) {
				if (remove == 0) {
					break;
				}
				if (fightObject2.isDead() || targetList.contains(fightObject2)) {
					continue;
				}
				targetList.add(fightObject2);
				--remove;
			}
			FightObject fightObject = FightManager.getFightObject(fightContainer, fightRequest.vid);
			if (fightObject != null && fightObject.isDead() && targetList != null
					&& targetList.size() > 0) {
				fightRequest.vid = targetList.get(0).fid;
			}
			vo_19959_0 = new Vo_C_ACTION();
			vo_19959_0.round = fightContainer.round;
			vo_19959_0.aid = fightRequest.id;
			vo_19959_0.action = 3;
			vo_19959_0.vid = fightRequest.vid;
			vo_19959_0.para = fightRequest.para;
			FightManager.send(fightContainer, new MSG_C_ACTION(), vo_19959_0);
			vo_19945_0 = new Vo_C_ACCEPT_HIT();
			vo_19945_0.id = fightRequest.vid;
			vo_19945_0.hid = fightRequest.id;
			vo_19945_0.para_ex = 0;
			vo_19945_0.missed = 1;
			vo_19945_0.para = 0;
			vo_19945_0.damage_type = 2;
			FightManager.send(fightContainer, new MSG_C_ACCEPT_HIT(), vo_19945_0);
			vo_64989_0 = new Vo_ACCEPT_MAGIC_HIT();
			vo_64989_0.hid = fightRequest.id;
			vo_64989_0.damageType = 2;
			for (FightObject fightObject2 : targetList) {
				int missed = 1;
				if(RandomUtil.checkMagDodge(fightObject2.getAttribute(FightAttribtueType.mag_dodge)+fightObject2.magDodgeExt-ignoreMagDodge)) {
					missed = 0;
				}
				if(fightObject != null) {
					magDodgeInfo.put(fightObject.fid, missed);
				}
				vo_64989_0.infos.add(new Info(fightObject2.fid,missed));
			}
			FightManager.send(fightContainer, new MSG_C_ACCEPT_MAGIC_HIT(), vo_64989_0);
			
			int addHurt = BattleUtils.skillAttack(attFightObject.fashang + attFightObject.fashang_ext,
					jiNeng.skill_level, "BS", jiNeng.skill_no);
			for (FightObject target : targetList) {
				int hurt = 0;
				int showhurt = 0;
				//如果使用如意圈
				if(fightObject != null && fightObject.getBuffState().contains(0x80000000)) {
					//天生技能
					List<FightRoundSkill> roundSkill = target.getRoundSkill();
					//被攻击了效果要消失
					for(FightRoundSkill skill:roundSkill) {
						if(skill instanceof TianJiSqrSkill) {
							skill.removeRound--;
							if(skill.removeRound<=0) {
								//移除效果
								fightObject.removeBuffState(fightContainer, 0x80000000);
								fightObject.updateState(fightContainer);
							}
							break;
						}
					}
				}else {
					hurt = BattleUtils.battle(attFightObject.fashang + attFightObject.fashang_ext, addHurt,
							target.fangyu + target.fangyu_ext);
					if(fightObject != null && magDodgeInfo.get(fightObject.getFid()) != null 
							&& magDodgeInfo.get(fightObject.getFid()) == 1) {
						//魔道点加成
						hurt += getUpgradeMagicScore(attFightObject, hurt);
						//仙道点减伤
						hurt -= getUpgradeImmortalScore(target, hurt);
						//伤害
						showhurt = target.reduceShengming(hurt, fabao);
					}
				}
				if(fightObject != null && magDodgeInfo.get(fightObject.getFid()) != null && magDodgeInfo.get(fightObject.getFid()) == 1) {
					FightResult fightResult4 = new FightResult();
					fightResult4.id = fightRequest.id;
					fightResult4.vid = target.fid;
					fightResult4.point = -showhurt;
					fightResult4.effect_no = 0;
					fightResult4.damage_type = 2;
					FightManager.send_LIFE_DELTA(fightContainer, fightResult4);
				}
			}
		}
		attFightObject.fightRequest = null;
		FightManager.send(fightContainer, new MSG_C_END_ACTION(), new Vo_C_END_ACTION(fightRequest.id));
		return resultList;
	}

	@Override
	public int getStateType() {
		return 0;
	}

}
