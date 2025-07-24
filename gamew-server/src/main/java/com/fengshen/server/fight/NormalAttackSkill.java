package com.fengshen.server.fight;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import com.fengshen.server.data.vo.Vo_12028_0;
import com.fengshen.server.data.vo.fight.Vo_ACCEPT_MAGIC_HIT;
import com.fengshen.server.data.vo.fight.Vo_ACCEPT_MAGIC_HIT.Info;
import com.fengshen.server.data.vo.fight.Vo_C_ACCEPT_HIT;
import com.fengshen.server.data.vo.fight.Vo_C_ACTION;
import com.fengshen.server.data.vo.fight.Vo_C_END_ACTION;
import com.fengshen.server.data.write.M12028_0;
import com.fengshen.server.data.write.fight.c.MSG_C_ACCEPT_HIT;
import com.fengshen.server.data.write.fight.c.MSG_C_ACCEPT_MAGIC_HIT;
import com.fengshen.server.data.write.fight.c.MSG_C_ACTION;
import com.fengshen.server.data.write.fight.c.MSG_C_END_ACTION;
import com.fengshen.server.domain.JiNeng;
import com.fengshen.server.game.GameUtil;
import com.fengshen.server.util.RandomUtil;

/**
 * 普通攻击
 * 
 *
 */
public class NormalAttackSkill implements FightSkill {
	@SuppressWarnings("unused")
	@Override
	public List<FightResult> doSkill(FightContainer fightContainer, FightRequest fightRequest, JiNeng jiNeng) {
		List<FightResult> resultList = new ArrayList<FightResult>();

		int id = fightRequest.id;
		FightObject fightObject = FightManager.getFightObject(fightContainer, id);
		FightObject victimFightObject = FightManager.getFightObject(fightContainer, fightRequest.vid);
		if(victimFightObject == null) {
			FightManager.defenseAction(fightContainer, fightRequest);
			return null;
		}
		
		//如果不是混乱的话并且打的是自己队友的话。
		boolean isInvalid = false;
		if(!fightObject.isHunluan()) {
			FightTeam fightTeam = FightManager.getFightTeam(fightContainer, fightObject.fid);
			for(FightObject team:fightTeam.fightObjectList) {
				if(team.fid == victimFightObject.fid) {
					isInvalid = true;
					break;
				}
			}
		}
		
		int tianshuType = fightObject.getRandomTianshuType(fightContainer);
		FightTianshuSkill activeTianshu = fightObject.isActiveTianshu(fightContainer, tianshuType);
		FightResult fightResult = null;
		float jiabei = 1.0f;
		switch (tianshuType) {
		case 7031: // 惊雷
			if (activeTianshu != null) {
				fightResult = tianshuSkill(fightContainer, fightObject, victimFightObject, 14);// 流光异彩
				return resultList;
			}
			break;
		case 7032: // 青木
			if (activeTianshu != null) {
				fightResult = tianshuSkill(fightContainer, fightObject, victimFightObject, 64);// 落叶缤纷
				return resultList;
			}
			break;
		case 7033: // 寒冰
			if (activeTianshu != null) {
				fightResult = tianshuSkill(fightContainer, fightObject, victimFightObject, 113);// 怒波狂涛
				return resultList;
			}
			break;
		case 7034: // 烈焰
			if (activeTianshu != null) {
				fightResult = tianshuSkill(fightContainer, fightObject, victimFightObject, 164);// 焦金砾石
				return resultList;
			}
			break;
		case 7035: // 碎石
			if (activeTianshu != null) {
				fightResult = tianshuSkill(fightContainer, fightObject, victimFightObject, 213);// 天塌地陷
				return resultList;
			}
			break;
		case 7040: // 破天
			if (activeTianshu != null) {
				activeTianshu.sendEffect(fightContainer);
				jiabei = 1.2f;
			}
			break;
		case 7037: // 狂暴随机多打几个对象
			break;
		case 0:
			break;
		default:
			break;
		}
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
		FightFabaoSkill fabaoSkill = fightObject.getFabaoSkill();

		if (fabaoSkill != null) {
			// 卸甲金葫
			if (fabaoSkill.getStateType() == 8398 && fabaoSkill.isActive()) {
				fabao = false;
			}
			// 混元金斗
			if (fabaoSkill.getStateType() == 8016 && fabaoSkill.isActive()) {
				Vo_12028_0 vo_12028_0 = new Vo_12028_0();
				vo_12028_0.id = fightRequest.id;
				vo_12028_0.effect_no = 8016;
				vo_12028_0.type = 1;
				vo_12028_0.name = "混元金斗";
				FightManager.send(fightContainer, new M12028_0(), vo_12028_0);
				jiabei = 2.5f;
			}
		}
		
		
		// 破天
		float jianFangPer = 1.0F;
		if (fightObject.isActiveTianshu(fightContainer, 7040) != null) {
			jianFangPer = 0.5F;
		}

		if (fightObject == null) {
			return null;
		}
		if (victimFightObject == null) {
			return null;
		}
		if (victimFightObject.hasBuffState(3844)) {
			victimFightObject.removeBuffSK(FightManager.getFightContainer(victimFightObject.fid), 3844);
		}
		// 计算fid到vid的伤害
		int hurt = 0;
		// 如果没触发闪躲技能才会生效

		// 计算fid到vid的伤害
		hurt = BattleUtils.battle(fightObject.accurate + fightObject.accurate_ext, 0,
				(int) (victimFightObject.fangyu * jianFangPer + victimFightObject.fangyu_ext));
		// 强物理伤害
		float enhanced_phy = fightObject.getAttribute(FightAttribtueType.ENHANCED_PHY2);
		float add_enhanced_phy = (float) ((fightObject.accurate + fightObject.accurate_ext) * enhanced_phy * 0.01);
		if (add_enhanced_phy < 0) {
			add_enhanced_phy = 0;
		}
		hurt += add_enhanced_phy;

		hurt = (int) (hurt * jiabei);
		
		Vo_C_ACCEPT_HIT vo_19945_0 = new Vo_C_ACCEPT_HIT();
		vo_19945_0.id = fightRequest.vid;
		vo_19945_0.hid = fightRequest.id;
		vo_19945_0.para_ex = 0;
		vo_19945_0.missed = 1;
		vo_19945_0.para = 0;
		vo_19945_0.damage_type = 1;
		float ignoreMagDodge = fightObject.getAttribute(FightAttribtueType.ignore_mag_dodge);
		// 如果触发了闪躲
		if (RandomUtil.checkMagDodge(victimFightObject.getAttribute(FightAttribtueType.mag_dodge)
				+ victimFightObject.magDodgeExt - ignoreMagDodge)) {
			vo_19945_0.missed = 0;
		}
		FightManager.send(fightContainer, new MSG_C_ACCEPT_HIT(), vo_19945_0);
		// 必杀(只有普攻和力破才有必杀)
		if (jiabei == 1.0F && RandomUtil.checkBisha(fightObject.getAttribute(FightAttribtueType.STUNT_RATE) + 0.01f)) {
			GameUtil.showImg(fightContainer, fightRequest.vid, 2002, "必杀");
			jiabei = 1.5F;
		}
		// 如果有罩子的话，
		if (victimFightObject.getBuffState().contains(0x40000000)) {
			List<FightRoundSkill> roundSkill = victimFightObject.getRoundSkill();
			Iterator<FightRoundSkill> roundSkillIterator = roundSkill.iterator();
			while (roundSkillIterator.hasNext()) {
				FightRoundSkill skill = roundSkillIterator.next();
				if (skill instanceof TianJiSqrSkill) {
					if (skill.removeRound <= 0) {
						// 移除效果
						victimFightObject.removeBuffState(fightContainer, 0x40000000);
						victimFightObject.updateState(fightContainer);
					}
					break;
				}
			}
		} else {
			if (victimFightObject.getBuffState().contains(0x09000000)) {
				int skillLevel = 1;
				List<FightRoundSkill> roundSkill = victimFightObject.getRoundSkill();
				Iterator<FightRoundSkill> roundSkillIterator = roundSkill.iterator();
				while (roundSkillIterator.hasNext()) {
					FightRoundSkill next = roundSkillIterator.next();
					if (next instanceof TianJiSqrSkill) {
						skillLevel = next.skillLevel;
						// 移除效果
						victimFightObject.removeBuffState(fightContainer, 0x09000000);
						victimFightObject.updateState(fightContainer);
						break;
					}
				}
				if (skillLevel >= 0 && skillLevel <= 20) {
					// 反击20%伤害
					hurt = (int) (hurt * 0.2);
				} else if (skillLevel >= 21 && skillLevel <= 50) {
					// 反击30%伤害
					hurt = (int) (hurt * 0.3);
				} else if (skillLevel >= 51 && skillLevel <= 100) {
					// 反击40%伤害
					hurt = (int) (hurt * 0.4);
				} else if (skillLevel >= 101 && skillLevel <= 160) {
					// 反击50%伤害
					hurt = (int) (hurt * 0.5);
				} else if (skillLevel >= 161 && skillLevel <= 180) {
					// 反击80%伤害
					hurt = (int) (hurt * 0.8);
				}
				// 获取该技能等级
				int fantanHurt = 0;
				if(isInvalid) {
					hurt = ThreadLocalRandom.current().nextInt(10)+1;
				}
				fantanHurt = fightObject.reduceShengming(hurt, fabao);
				// 反弹伤害到对方
				FightResult fightResult2 = new FightResult();
				fightResult2.id = fightObject.id;
				fightResult2.vid = fightRequest.id;
				fightResult2.effect_no = 0;
				fightResult2.damage_type = 4097;
				fightResult2.point = -fantanHurt;
				FightManager.send_LIFE_DELTA(fightContainer, fightResult2);
			} else {
				if (vo_19945_0.missed == 1) {
					if(isInvalid) {
						hurt = ThreadLocalRandom.current().nextInt(10)+1;
					}
					hurt = victimFightObject.reduceShengming(hurt, fabao);
					fightResult = new FightResult();
					fightResult.id = fightRequest.id;
					fightResult.vid = fightRequest.vid;
					fightResult.point = -hurt;
					fightResult.effect_no = 0;
					fightResult.damage_type = 1;
					FightManager.send_LIFE_DELTA(fightContainer, fightResult);
				}
			}
		}

		// 角色连击
		if (jiabei == 1.0f
				&& RandomUtil.checkLianji(fightObject.getAttribute(FightAttribtueType.DOUBLE_HIT_RATE) + 0.01f)
				&& !fightObject.isDead()) {
			int num = (int) fightObject.getAttribute(FightAttribtueType.DOUBLE_HIT);
			num = (num >= 12) ? 12 : num;
			num = (num == 0) ? 1 : RandomUtil.randomNotZeroInt(num);

			if (victimFightObject.isActiveTianshu(fightContainer, 8050) != null) { // 仙风
				num = 1;
			}
			GameUtil.showImg(fightContainer, fightRequest.id, 0, "连击");
			for (int i = 0; i < num; ++i) {
				// 如果被攻击者被打死了，就退出
				if (victimFightObject.isDead())
					break;
				vo_19945_0 = new Vo_C_ACCEPT_HIT();
				vo_19945_0.id = fightRequest.vid;
				vo_19945_0.hid = fightRequest.id;
				vo_19945_0.para_ex = 0;
				vo_19945_0.missed = 1;
				vo_19945_0.para = 0;
				vo_19945_0.damage_type = 1;
				// 如果触发了闪躲
				if (RandomUtil.checkMagDodge(victimFightObject.getAttribute(FightAttribtueType.mag_dodge)
						+ victimFightObject.magDodgeExt - ignoreMagDodge)) {
					vo_19945_0.missed = 0;
				}
				FightManager.send(fightContainer, new MSG_C_ACCEPT_HIT(), vo_19945_0);
				if (vo_19945_0.missed == 1) {
					// 设置50%的伤害衰减效果
					if (vo_19945_0.missed == 1) {
						hurt = (hurt == 0) ? 1 : (hurt / 2);
						if(isInvalid) {
							hurt = ThreadLocalRandom.current().nextInt(10)+1;
						}
						hurt = victimFightObject.reduceShengming(hurt, fabao);
						fightResult = new FightResult();
						fightResult.id = fightRequest.id;
						fightResult.vid = fightRequest.vid;
						fightResult.point = -hurt;
						fightResult.effect_no = 0;
						fightResult.damage_type = 1;
						FightManager.send_LIFE_DELTA(fightContainer, fightResult);
					}
				}
			}
		}

		// 宠物反击天书，宠物的反击效果为宠物物攻的百分比
		if (victimFightObject.isActiveTianshu(fightContainer, 8049) != null) {
			int fanShang = hurt * 10 / 6;
			int wugong = victimFightObject.accurate;
			fightObject.reduceShengming(fanShang, false);
			fightResult = new FightResult();
			fightResult.id = victimFightObject.fid;
			fightResult.vid = fightObject.fid;
			fightResult.point = -wugong;
			fightResult.effect_no = 0;
			fightResult.damage_type = 1;
			FightManager.send_LIFE_DELTA(fightContainer, fightResult);
		}

		// 角色反震，次数只有一次
//        boolean fanzhen = false;
//        if(RandomUtil.checkFanzhen(victimFightObject.getAttribute(FightAttribtueType.FANZHEN_RATE) + 0.01f)) {
//            int fanzhendu = (int) fightObject.getAttribute(FightAttribtueType.FANZHEN_NUM);
//
//            int fanjiShanghai = (int) (hurt * (1 + fanzhendu * 1.0 / 100));
//            fightObject.reduceShengming(fanjiShanghai, false);
//            fightResult = new FightResult();
//            fightResult.id = victimFightObject.fid;
//            fightResult.vid = fightObject.fid;
//            fightResult.point = -fanjiShanghai;
//            fightResult.effect_no = 0;
//            fightResult.damage_type = 1;
//            resultList.add(fightResult);
//            fanzhen = true;
//        }

		// 角色反击(反震和反击只能触发一次)
//        if(!fanzhen && RandomUtil.checkFanji(victimFightObject.getAttribute(FightAttribtueType.REBACK_HIT_RATE) + 0.01f)) {
//            int num = 1;
//            int fanjiShanghai = hurt;
//            fightObject.reduceShengming(fanjiShanghai, false);
//            fightResult = new FightResult();
//            fightResult.id = victimFightObject.fid;
//            fightResult.vid = fightObject.fid;
//            fightResult.point = -fanjiShanghai;
//            fightResult.effect_no = 0;
//            fightResult.damage_type = 1;
//            resultList.add(fightResult);
//        }

		// 狂暴
		if (fightObject.isActiveTianshu(fightContainer, 7037) != null) {
			int randomNum = new Random().nextInt(3) + 1;
			List<FightObject> targetList = FightManager.findTarget(fightContainer, fightRequest, -1, randomNum);
			if(!targetList.isEmpty()) {
				//天书
				Vo_12028_0 vo_12028_0 = new Vo_12028_0();
				vo_12028_0.id = fightRequest.id;
				vo_12028_0.effect_no = 0;
				vo_12028_0.type = 4;
				vo_12028_0.name = "狂暴";
				FightManager.send(fightContainer, new M12028_0(), vo_12028_0);
				
				Vo_ACCEPT_MAGIC_HIT vo_64989_0 = new Vo_ACCEPT_MAGIC_HIT();
				vo_64989_0.hid = fightRequest.id;
				vo_64989_0.damageType = 1;
				Iterator<FightObject> var14 = targetList.iterator();
				while (var14.hasNext()) {
					FightObject fightObject1 = (FightObject) var14.next();
					if (fightObject1 == null)
						break;
					if (fightObject1.fid == victimFightObject.fid) {
						continue;
					}
					vo_64989_0.infos.add(new Info(fightObject1.fid, 1));
				}

				FightManager.send(fightContainer, new MSG_C_ACCEPT_MAGIC_HIT(), vo_64989_0);
				Iterator<FightObject> var20 = targetList.iterator();

				while (var20.hasNext()) {
					FightObject fightObject1 = (FightObject) var20.next();
					if (fightObject1 == null)
						break;
					if (fightObject1.fid == victimFightObject.fid) {
						continue;
					}
					int showhurt = (int) (hurt * 0.5);
					if(showhurt<=0) {
						showhurt = 1;
					}
					showhurt = fightObject1.reduceShengming(showhurt, fabao);
					fightResult = new FightResult();
					fightResult.id = fightRequest.id;
					fightResult.vid = fightObject1.fid;
					fightResult.point = -showhurt;
					fightResult.effect_no = 0;
					fightResult.damage_type = 1;
					FightManager.send_LIFE_DELTA(fightContainer, fightResult);
				}
			}
		}
		FightManager.send(fightContainer, new MSG_C_END_ACTION(), new Vo_C_END_ACTION(fightRequest.id));
		return null;
	}

	// 这里是5个普通的天书技能，默认倍数为2
	private FightResult tianshuSkill(FightContainer fightContainer, FightObject attFightObject,
			FightObject victimFightObject, int skillNo) {
		Vo_C_ACTION vo_19959_0 = new Vo_C_ACTION();
		vo_19959_0.round = fightContainer.round;
		vo_19959_0.aid = attFightObject.id;
		vo_19959_0.vid = victimFightObject.fid;
		vo_19959_0.action = 2;
		vo_19959_0.para = skillNo;
		FightManager.send(fightContainer, new MSG_C_ACTION(), vo_19959_0);
		
		Vo_C_ACCEPT_HIT vo_19945_0 = new Vo_C_ACCEPT_HIT();
		vo_19945_0.hid = attFightObject.id;
		vo_19945_0.id = victimFightObject.fid;
		vo_19945_0.para_ex = 0;
		vo_19945_0.missed = 1;
		vo_19945_0.para = 0;
		vo_19945_0.damage_type = 1;
		FightManager.send(fightContainer, new MSG_C_ACCEPT_HIT(), vo_19945_0);

		Vo_ACCEPT_MAGIC_HIT vo_64989_0 = new Vo_ACCEPT_MAGIC_HIT();
		vo_64989_0.hid = attFightObject.id;
		vo_64989_0.damageType = 2;
		vo_64989_0.infos.add(new Info(victimFightObject.fid, 1));

		FightManager.send(fightContainer, new MSG_C_ACCEPT_MAGIC_HIT(), vo_64989_0);

		int skillAttack = BattleUtils.skillAttack(attFightObject.accurate + attFightObject.accurate_ext, 1, "WS",
				skillNo);
		int thurt = (int) (2.0 * BattleUtils.battle(attFightObject.accurate + attFightObject.accurate_ext, skillAttack,
				victimFightObject.fangyu + victimFightObject.fangyu_ext));
		victimFightObject.reduceShengming(thurt, false);

		FightResult fightResult = new FightResult();
		fightResult.id = attFightObject.id;
		fightResult.vid = victimFightObject.fid;
		fightResult.point = -thurt;
		fightResult.effect_no = 0;
		fightResult.damage_type = 2;
		FightManager.send_LIFE_DELTA(fightContainer, fightResult);
		
		FightManager.send(fightContainer, new MSG_C_END_ACTION(), new Vo_C_END_ACTION(attFightObject.fid));
		return fightResult;
	}

	@Override
	public int getStateType() {
		return 0;
	}
}
