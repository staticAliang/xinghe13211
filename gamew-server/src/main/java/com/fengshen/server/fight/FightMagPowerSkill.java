package com.fengshen.server.fight;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.json.JSONObject;

import com.fengshen.server.data.game.PetAndHelpSkillUtils;
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
import com.fengshen.server.game.GameObjectChar;
import com.fengshen.server.game.GameObjectCharMng;
import com.fengshen.server.game.GameUtil;
import com.fengshen.server.util.RandomUtil;

// 全部是法术攻击
public class FightMagPowerSkill implements FightSkill {
	
	// 计算抗性和忽视抗性的伤害因子
	public double computeDelta(FightObject attackObject, FightObject victimObject) {
		double factor = 0;
		double kangXing = 0;
		double hushiKangxing = 0;
		// 抗性的值存储的是大于1的数，不是小数
		if (attackObject.polar == 1) {
			kangXing = victimObject.getAttribute(FightAttribtueType.RESIST_METAL);
			hushiKangxing = attackObject.getAttribute(FightAttribtueType.IGNORE_RESIST_METAL);
		} else if (attackObject.polar == 2) {
			kangXing = victimObject.getAttribute(FightAttribtueType.RESIST_WOOD);
			hushiKangxing = attackObject.getAttribute(FightAttribtueType.IGNORE_RESIST_WOOD);
		} else if (attackObject.polar == 3) {
			kangXing = victimObject.getAttribute(FightAttribtueType.RESIST_WATER);
			hushiKangxing = attackObject.getAttribute(FightAttribtueType.IGNORE_RESIST_WATER);
		} else if (attackObject.polar == 4) {
			kangXing = victimObject.getAttribute(FightAttribtueType.RESIST_FIRE);
			hushiKangxing = attackObject.getAttribute(FightAttribtueType.IGNORE_RESIST_FIRE);
		} else if (attackObject.polar == 5) {
			kangXing = victimObject.getAttribute(FightAttribtueType.RESIST_EARTH);
			hushiKangxing = attackObject.getAttribute(FightAttribtueType.IGNORE_RESIST_EARTH);
		}
		hushiKangxing+= attackObject.getAttribute(FightAttribtueType.IGNORE_ALL_RESIST_POLAR);
		
		kangXing = (kangXing - 40) > 0.0 ? 40 : kangXing;
		hushiKangxing = (hushiKangxing - 40) > 0.0 ? 40 : hushiKangxing;
		kangXing /= 100;
		hushiKangxing /= 100;
		if (Math.abs(kangXing - 0.0f) <= 0 && Math.abs(hushiKangxing - 0.0f) <= 0) {
			factor = 0;
		} else if (Math.abs(kangXing - 0.0f) > 0 && Math.abs(hushiKangxing - 0.0f) <= 0) {
			factor = kangXing;
		} else if (Math.abs(kangXing - 0.0f) <= 0 && Math.abs(hushiKangxing - 0.0f) > 0) {
			factor = -hushiKangxing;
		} else if (Math.abs(kangXing - 0.0f) > 0 && Math.abs(hushiKangxing - 0.0f) > 0) {
			factor = -(hushiKangxing - kangXing);
		}
		return factor;
	}
	
	/**
	 * 计算强法伤害、强力
	 * @param attackObject 攻击者
	 * @param victimObject 被攻击者
	 * @param skillNo 技能编号
	 * @return
	 */
	public double computeExtHurt(FightObject attackObject, FightObject victimObject, int skillNo) {
		int hurt = 0;
		JSONObject skill = PetAndHelpSkillUtils.jsonArray(skillNo);
		//基础伤害
		int accurat = attackObject.accurate;
		if(skill != null) {
			String skillType = skill.getString("skillType");
			int metal = skill.getInt("metal");
			//强法只对法伤的生效
			if("FS".equals(skillType)) {
				//如果攻击的人和当前释放的法术相同,则加伤害
				if(attackObject.polar == 1 && metal == 1) {
					//技能必须和职业相同才生效
					hurt+=accurat*(attackObject.getAttribute(FightAttribtueType.enhanced_metal)*0.01);
				}else if(attackObject.polar == 2 && metal == 2) {
					//技能必须和职业相同才生效
					hurt+=accurat*(attackObject.getAttribute(FightAttribtueType.enhanced_wood)*0.01);
				}else if(attackObject.polar == 3 && metal == 3) {
					//技能必须和职业相同才生效
					hurt+=accurat*(attackObject.getAttribute(FightAttribtueType.enhanced_water)*0.01);
				}else if(attackObject.polar == 4 && metal == 4) {
					//技能必须和职业相同才生效
					hurt+=accurat*(attackObject.getAttribute(FightAttribtueType.enhanced_fire)*0.01);
				}else if(attackObject.polar == 5 && metal == 5) {
					//技能必须和职业相同才生效
					hurt+=accurat*(attackObject.getAttribute(FightAttribtueType.enhanced_earth)*0.01);
				}
			}
		}
		//强力克制
		if(victimObject.polar == 1) {
			hurt+=accurat*(attackObject.getAttribute(FightAttribtueType.super_excluse_metal)*0.01);
		}else if(victimObject.polar == 2) {
			hurt+=accurat*(attackObject.getAttribute(FightAttribtueType.super_excluse_wood)*0.01);
		}else if(victimObject.polar == 3) {
			hurt+=accurat*(attackObject.getAttribute(FightAttribtueType.super_excluse_water)*0.01);
		}else if(victimObject.polar == 4) {
			hurt+=accurat*(attackObject.getAttribute(FightAttribtueType.super_excluse_fire)*0.01);
		}else if(victimObject.polar == 5) {
			hurt+=accurat*(attackObject.getAttribute(FightAttribtueType.super_excluse_earth)*0.01);
		}
		return hurt<0?0:hurt;
	}
	

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
		if(targetList.isEmpty()) {
			FightManager.defenseAction(fightContainer, fightRequest);
			return null;
		}
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
		float jiabei = 1.0f;
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
					jiNeng.skill_level, "FS", jiNeng.skill_no);
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
			double factor = computeDelta(attFightObject, target);
//			addHurt = (int) (addHurt * jiabei);
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
						hurt += computeExtHurt(attFightObject,target, jiNeng.skill_no);
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
						hurt += computeExtHurt(attFightObject,target, jiNeng.skill_no);
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
					jiNeng.skill_level, "FS", jiNeng.skill_no);
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
						hurt += computeExtHurt(attFightObject,fightObject, jiNeng.skill_no);
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
