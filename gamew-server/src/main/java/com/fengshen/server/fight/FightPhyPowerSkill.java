package com.fengshen.server.fight;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.fengshen.server.data.vo.fight.Vo_ACCEPT_MAGIC_HIT;
import com.fengshen.server.data.vo.fight.Vo_ACCEPT_MAGIC_HIT.Info;
import com.fengshen.server.data.vo.fight.Vo_ACCEPT_MULTI_HIT;
import com.fengshen.server.data.vo.fight.Vo_C_ACCEPT_HIT;
import com.fengshen.server.data.vo.fight.Vo_C_ACTION;
import com.fengshen.server.data.vo.fight.Vo_C_END_ACTION;
import com.fengshen.server.data.write.fight.c.MSG_C_ACCEPT_HIT;
import com.fengshen.server.data.write.fight.c.MSG_C_ACCEPT_MAGIC_HIT;
import com.fengshen.server.data.write.fight.c.MSG_C_ACCEPT_MULTI_HIT;
import com.fengshen.server.data.write.fight.c.MSG_C_ACTION;
import com.fengshen.server.data.write.fight.c.MSG_C_END_ACTION;
import com.fengshen.server.domain.JiNeng;
import com.fengshen.server.game.GameObjectChar;
import com.fengshen.server.game.GameObjectCharMng;
import com.fengshen.server.game.GameUtil;
import com.fengshen.server.util.RandomUtil;

/**
 * 力破千钧
 * @author aaa
 *
 */
public class FightPhyPowerSkill implements FightSkill {
	
	public double computeExtHurt(FightObject attackObject, FightObject victimObject) {
		//强物理伤害
		float enhanced_phy = attackObject.getAttribute(FightAttribtueType.ENHANCED_PHY2);
		float add_enhanced_phy = (float) ((attackObject.accurate)*enhanced_phy*0.01);
		return add_enhanced_phy<0?0:add_enhanced_phy;
	}
	
	
	
	@Override
	public List<FightResult> doSkill(FightContainer fightContainer, FightRequest fightRequest, JiNeng jiNeng) {
		int attaNum = jiNeng.range;
		//随机查找攻击目标
		List<FightObject> targetList = FightManager.findTarget(fightContainer, fightRequest, 0, attaNum);
		if(targetList.isEmpty()) {
			FightManager.defenseAction(fightContainer, fightRequest);
			return null;
		}
		FightObject attFightObject = FightManager.getFightObject(fightContainer, fightRequest.id);
		
		FightObject vttFightObject = FightManager.getFightObject(fightContainer, fightRequest, fightRequest.vid);
		if(vttFightObject == null) {
			FightManager.defenseAction(fightContainer, fightRequest);
			return null;
		}
		
		Vo_C_ACTION vo_19959_0 = new Vo_C_ACTION();
		vo_19959_0.round = fightContainer.round;
		vo_19959_0.aid = fightRequest.id;
		vo_19959_0.action = 2;
		vo_19959_0.vid = fightRequest.vid;
		vo_19959_0.para = fightRequest.para;
		FightManager.send(fightContainer, new MSG_C_ACTION(), vo_19959_0);
		//技能说话
		FightManager.autoTalkAction(fightContainer, fightRequest);
		float ignoreMagDodge = attFightObject.getAttribute(FightAttribtueType.ignore_mag_dodge);
		
		boolean fabao = true;
		FightFabaoSkill fabaoSkill = attFightObject.getFabaoSkill();
		float jiabei = 1.0f;
		if (fabaoSkill != null) {
			if (fabaoSkill.getStateType() == 8398 && fabaoSkill.isActive()) {
				fabao = false;
				fabaoSkill.sendEffect(fightContainer);
			}
			if (fabaoSkill.getStateType() == 8016 && fabaoSkill.isActive()) {
				fabaoSkill.sendEffect(fightContainer);
				GameUtil.showImg(fightContainer, fightRequest.id, 0, "混元金斗");
				jiabei = 2.5f;
			}
		}
		boolean isBiSha = false;
		// 必杀(只有普攻和力破才有必杀)
		if (jiabei == 1.0F
				&& RandomUtil.checkBisha(attFightObject.getAttribute(FightAttribtueType.STUNT_RATE) + 0.01f)) {
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
		//连接次数
		int doubleHitCount = 0;
		// 设置力破的连击次数
		if (jiabei == 1.0f
				&& RandomUtil.checkLianji(attFightObject.getAttribute(FightAttribtueType.DOUBLE_HIT_RATE) + 0.01f)) {
			int num = (int) attFightObject.getAttribute(FightAttribtueType.DOUBLE_HIT);
			num = (num >= 12) ? 12 : num;
			num = (num == 0) ? 1 : RandomUtil.randomNotZeroInt(num);
			doubleHitCount = num;
		}
		int hurt = 0;
		if (doubleHitCount > 0 && !attFightObject.isDead() && !isBiSha) {
			for (int i = 0; i < doubleHitCount+1; ++i) {
				//保存躲避人的信息
				Map<Integer,Integer> mapInfo = new HashMap<>();
				boolean isEndDoubleHit = false;
				//出手次数
				Vo_C_ACCEPT_HIT vo_19945_0 = new Vo_C_ACCEPT_HIT();
				vo_19945_0.id = fightRequest.vid;
				vo_19945_0.hid = fightRequest.id;
				vo_19945_0.para_ex = 0;
				vo_19945_0.missed = 1;
				vo_19945_0.para = 0;
				vo_19945_0.damage_type = 1;
				FightManager.send(fightContainer, new MSG_C_ACCEPT_HIT(), vo_19945_0);
				//击中目标
				Vo_ACCEPT_MULTI_HIT hit = new Vo_ACCEPT_MULTI_HIT();
				hit.setHitterId(fightRequest.id);
				hit.setDamageType(1);
				for (FightObject fightObject2 : targetList) {
					if (fightObject2 == null) {
						continue;
					}
					int missed = 1;
					if(RandomUtil.checkMagDodge(fightObject2.getAttribute(FightAttribtueType.mag_dodge)+fightObject2.magDodgeExt-ignoreMagDodge)) {
						missed = 0;
					}
					mapInfo.put(fightObject2.fid, missed);
					hit.getInfos().add(new Info(fightObject2.fid, missed));
				}
				FightManager.send(fightContainer, new MSG_C_ACCEPT_MULTI_HIT(), hit);
				int index = 1;
				Iterator<FightObject> iterator = targetList.iterator();
				
				while(iterator.hasNext()) {
					FightObject fightObject2 = iterator.next();
					if (fightObject2 == null) {
						continue;
					}
					if (fightObject2.hasBuffState(3844)) {
						fightObject2.removeBuffSK(FightManager.getFightContainer(fightObject2.fid), 3844);
					}
					int showhurt = 0;
					//如果有罩子的话，
					if (fightObject2.getBuffState().contains(0x40000000)) {
						List<FightRoundSkill> roundSkill = fightObject2.getRoundSkill();
						Iterator<FightRoundSkill> roundSkillIterator = roundSkill.iterator();
						while(roundSkillIterator.hasNext()) {
							FightRoundSkill skill = roundSkillIterator.next();
							if (skill instanceof TianJiSqrSkill) {
								skill.removeRound--;
								if(skill.removeRound<=0) {
									//移除效果
									fightObject2.removeBuffState(fightContainer, 0x40000000);
									fightObject2.updateState(fightContainer);
								}
								break;
							}
						}
						// 回合数减一
						if(mapInfo.get(fightObject2.getFid()) != null && mapInfo.get(fightObject2.getFid()) == 1) {
							FightResult fightResult = new FightResult();
							fightResult.id = fightRequest.id;
							fightResult.vid = fightObject2.fid;
							fightResult.point = -0;
							fightResult.effect_no = 0;
							fightResult.damage_type = 4097;
							FightManager.send_LIFE_DELTA(fightContainer, fightResult);
						}
					}else {
						if (hurt == 0) {
							int addHurt = BattleUtils.skillAttack(attFightObject.accurate + attFightObject.accurate_ext,
									jiNeng.skill_level, "WS", jiNeng.skill_no - 501);
							hurt = BattleUtils.battle(attFightObject.accurate + attFightObject.accurate_ext, addHurt,
									fightObject2.fangyu + fightObject2.fangyu_ext);
							hurt = (int) (hurt * jiabei);
						} else {
							hurt = (int) (hurt * 0.9);
						}
						//如果使用了乾坤罩
						if (fightObject2.getBuffState().contains(0x09000000)) {
							int skillLevel = 1;
							List<FightRoundSkill> roundSkill = fightObject2.getRoundSkill();
							Iterator<FightRoundSkill> roundSkillIterator = roundSkill.iterator();
							while(roundSkillIterator.hasNext()) {
								FightRoundSkill next = roundSkillIterator.next();
								if (next instanceof TianJiSqrSkill) {
									skillLevel = next.skillLevel;
									//移除效果
									fightObject2.removeBuffState(fightContainer, 0x09000000);
									fightObject2.updateState(fightContainer);
									break;
								}
							}
							if(skillLevel>=0 && skillLevel<=20) {
								//反击20%伤害
								hurt = (int) (hurt*0.2);
							}else if(skillLevel>=21 && skillLevel<=50) {
								//反击30%伤害
								hurt = (int) (hurt*0.3);
							}else if(skillLevel>=51 && skillLevel<=100) {
								//反击40%伤害
								hurt = (int) (hurt*0.4);
							}else if(skillLevel>=101 && skillLevel<=160) {
								//反击50%伤害
								hurt = (int) (hurt*0.5);
							}else if(skillLevel>=161 && skillLevel<=180) {
								//反击80%伤害
								hurt = (int) (hurt*0.8);
							}
							//获取该技能等级
							hurt+=computeExtHurt(attFightObject, vttFightObject);
							//魔道点加成
							hurt += getUpgradeMagicScore(attFightObject, hurt);
							//仙道点减伤
							hurt -= getUpgradeImmortalScore(fightObject2, hurt);
							int fantanHurt = attFightObject.reduceShengming(hurt, fabao);
							//反弹伤害到对方
							FightResult fightResult = new FightResult();
							fightResult.id = attFightObject.id;
							fightResult.vid = fightRequest.id;
							fightResult.effect_no = 0;
							fightResult.damage_type = 4097;
							fightResult.point = -fantanHurt;
							// 回合数减一
							FightManager.send_LIFE_DELTA(fightContainer, fightResult);
						}else {
							if(mapInfo.get(fightObject2.getFid()) != null && mapInfo.get(fightObject2.getFid()) == 1) {
								// 计算伤害
								hurt+=computeExtHurt(attFightObject, vttFightObject);
								//魔道点加成
								hurt += getUpgradeMagicScore(attFightObject, hurt);
								//仙道点减伤
								hurt -= getUpgradeImmortalScore(fightObject2, hurt);
								if(index>1) {
									hurt = hurt/2;
								}
								showhurt = fightObject2.reduceShengming(hurt, fabao);
								FightResult fightResult = new FightResult();
								fightResult.id = fightRequest.id;
								fightResult.vid = fightObject2.fid;
								fightResult.point = -showhurt;
								fightResult.effect_no = 0;
								fightResult.damage_type = 4097;
								FightManager.send_LIFE_DELTA(fightContainer, fightResult);
							}
						}
					}
					if(fightRequest.vid == fightObject2.fid && fightObject2.isDead()) {
						isEndDoubleHit = true;
					}else if(fightObject2.isDead()) {
						isEndDoubleHit = true;
					}else {
						GameUtil.showImg(fightContainer, fightRequest.id, 0, "连击");
					}
					index ++;
				}
				//如果被攻击的人死了，或者是自己被反击死了
				if(isEndDoubleHit || attFightObject.isDead()) {
					break;
				}
			}
		}else {//普通攻击
			//出手次数
			Vo_C_ACCEPT_HIT vo_19945_0 = new Vo_C_ACCEPT_HIT();
			vo_19945_0.id = fightRequest.vid;
			vo_19945_0.hid = fightRequest.id;
			vo_19945_0.para_ex = 0;
			vo_19945_0.missed = 1;
			vo_19945_0.para = 0;
			vo_19945_0.damage_type = 1;
			vo_19945_0.missed = 1;
			//命中动画
			Vo_ACCEPT_MAGIC_HIT vo_64989_0 = new Vo_ACCEPT_MAGIC_HIT();
			vo_64989_0.hid = fightRequest.id;
			vo_64989_0.damageType = 1;
			//保存躲避人的信息
			Map<Integer,Integer> mapInfo = new HashMap<>();
			for (FightObject fightObject : targetList) {
				int missed = 1;
				if(fightObject.type == 1) {
					if(RandomUtil.checkMagDodge(fightObject.getAttribute(FightAttribtueType.mag_dodge)+fightObject.magDodgeExt-ignoreMagDodge)) {
						missed = 0;
					}
					if(vo_19945_0.id == fightObject.fid) {
						vo_19945_0.missed = missed;
					}
				}
				mapInfo.put(fightObject.fid, missed);
				vo_64989_0.infos.add(new Info(fightObject.fid, missed));
			}
			FightManager.send(fightContainer, new MSG_C_ACCEPT_HIT(), vo_19945_0);
			//出完手之后在播放特效
			if(isBiSha) {
				GameUtil.showImg(fightContainer, fightRequest.vid, 2002, "必杀");
			}
			FightManager.send(fightContainer, new MSG_C_ACCEPT_MAGIC_HIT(), vo_64989_0);
			
			for (int i = 0; i < targetList.size(); i++) {
				FightObject fightObject2 = targetList.get(i);
				if (fightObject2 == null) {
					continue;
				}
				int showhurt = 0;
				//如果有罩子的话，
				if (fightObject2.getBuffState().contains(0x40000000)) {
					List<FightRoundSkill> roundSkill = fightObject2.getRoundSkill();
					Iterator<FightRoundSkill> roundSkillIterator = roundSkill.iterator();
					while(roundSkillIterator.hasNext()) {
						FightRoundSkill skill = roundSkillIterator.next();
						if (skill instanceof TianJiSqrSkill) {
							skill.removeRound--;
							if(skill.removeRound<=0) {
								//移除效果
								fightObject2.removeBuffState(fightContainer, 0x40000000);
								fightObject2.updateState(fightContainer);
							}
							break;
						}
					}
					// 回合数减一
				}else {
					int addHurt = BattleUtils.skillAttack(attFightObject.accurate + attFightObject.accurate_ext,
							jiNeng.skill_level, "WS", jiNeng.skill_no - 501);
					hurt = BattleUtils.battle(attFightObject.accurate + attFightObject.accurate_ext, addHurt,
							fightObject2.fangyu + fightObject2.fangyu_ext);
					hurt = (int) (hurt * jiabei);
					//如果使用了乾坤罩
					if (fightObject2.getBuffState().contains(0x09000000)) {
						int skillLevel = 1;
						List<FightRoundSkill> roundSkill = fightObject2.getRoundSkill();
						Iterator<FightRoundSkill> roundSkillIterator = roundSkill.iterator();
						while(roundSkillIterator.hasNext()) {
							FightRoundSkill next = roundSkillIterator.next();
							if (next instanceof TianJiSqrSkill) {
								skillLevel = next.skillLevel;
								//移除效果
								fightObject2.removeBuffState(fightContainer, 0x09000000);
								fightObject2.updateState(fightContainer);
								break;
							}
						}
						if(skillLevel>=0 && skillLevel<=20) {
							//反击20%伤害
							hurt = (int) (hurt*0.2);
						}else if(skillLevel>=21 && skillLevel<=50) {
							//反击30%伤害
							hurt = (int) (hurt*0.3);
						}else if(skillLevel>=51 && skillLevel<=100) {
							//反击40%伤害
							hurt = (int) (hurt*0.4);
						}else if(skillLevel>=101 && skillLevel<=160) {
							//反击50%伤害
							hurt = (int) (hurt*0.5);
						}else if(skillLevel>=161 && skillLevel<=180) {
							//反击80%伤害
							hurt = (int) (hurt*0.8);
						}
						//获取该技能等级
						hurt+=computeExtHurt(attFightObject, vttFightObject);
						//魔道点加成
						hurt += getUpgradeMagicScore(attFightObject, hurt);
						//仙道点减伤
						hurt -= getUpgradeImmortalScore(fightObject2, hurt);
						int fantanHurt = attFightObject.reduceShengming(hurt, fabao);
						//反弹伤害到对方
						FightResult fightResult = new FightResult();
						fightResult.id = attFightObject.id;
						fightResult.vid = fightRequest.id;
						fightResult.effect_no = 0;
						fightResult.damage_type = 4097;
						fightResult.point = -fantanHurt;
						FightManager.send_LIFE_DELTA(fightContainer, fightResult);
						// 回合数减一
					}else {
						if(mapInfo.get(fightObject2.getFid()) != null && mapInfo.get(fightObject2.getFid()) == 1) {
							// 计算伤害
							hurt+=computeExtHurt(attFightObject, vttFightObject);
							//魔道点加成
							hurt += getUpgradeMagicScore(attFightObject, hurt);
							//仙道点减伤
							hurt -= getUpgradeImmortalScore(fightObject2, hurt);
							if(i>0) {
								hurt = hurt/2;
							}
							showhurt = fightObject2.reduceShengming(hurt, fabao);
							//其他目标都是主伤害的2倍
							FightResult fightResult = new FightResult();
							fightResult.id = fightRequest.id;
							fightResult.vid = fightObject2.fid;
							fightResult.point = -showhurt;
							fightResult.effect_no = 0;
							fightResult.damage_type = 4097;
							FightManager.send_LIFE_DELTA(fightContainer, fightResult);
						}
					}
				}
			}
		}
		FightManager.send(fightContainer, new MSG_C_END_ACTION(), new Vo_C_END_ACTION(fightRequest.id));
		return null;
	}

	@Override
	public int getStateType() {
		return 0;
	}
}
