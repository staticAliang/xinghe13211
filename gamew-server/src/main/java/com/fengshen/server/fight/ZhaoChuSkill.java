package com.fengshen.server.fight;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import com.fengshen.server.data.vo.Vo_12023_0;
import com.fengshen.server.data.vo.Vo_64971_0;
import com.fengshen.server.data.vo.Vo_7653_0;
import com.fengshen.server.data.vo.Vo_GODBOOK_EFFECT;
import com.fengshen.server.data.vo.fight.Vo_ADD_FRIEND_OPPONENT;
import com.fengshen.server.data.vo.fight.Vo_C_ACTION;
import com.fengshen.server.data.vo.fight.Vo_C_END_ACTION;
import com.fengshen.server.data.write.M7653_0;
import com.fengshen.server.data.write.fight.c.MSG_C_ACTION;
import com.fengshen.server.data.write.fight.c.MSG_C_END_ACTION;
import com.fengshen.server.data.write.fight.c.MSG_C_FRIENDS;
import com.fengshen.server.data.write.fight.c.MSG_C_LEAVE_AT_ONCE;
import com.fengshen.server.data.write.fight.c.MSG_C_OPPONENTS;
import com.fengshen.server.data.write.fight.c.MSG_C_REFRESH_PET_LIST;
import com.fengshen.server.data.write.fight.c.MSG_C_SET_FIGHT_PET;
import com.fengshen.server.data.write.pet.MSG_GODBOOK_EFFECT_SUMMON;
import com.fengshen.server.data.write.pet.MSG_UPDATE_PETS;
import com.fengshen.server.domain.Chara;
import com.fengshen.server.domain.JiNeng;
import com.fengshen.server.domain.Petbeibao;
import com.fengshen.server.game.GameObjectChar;
import com.fengshen.server.game.GameObjectCharMng;
import com.fengshen.server.game.GameUtil;
import com.google.common.collect.Lists;

/**
 * 召唤宠物
 * 
 *
 */

public class ZhaoChuSkill implements FightSkill {
	
	@Override
	public List<FightResult> doSkill(FightContainer fightContainer, FightRequest fightRequest, JiNeng jiNeng) {
		GameObjectChar gameObjectChar = GameObjectCharMng.getGameObjectChar(fightRequest.id);
		if(gameObjectChar == null) {
			FightManager.defenseAction(fightContainer, fightRequest);
			return null;
		}
		Vo_C_ACTION vo_19959_0 = new Vo_C_ACTION();
		vo_19959_0.round = fightContainer.round;
		vo_19959_0.aid = fightRequest.id; // 角色id
		vo_19959_0.action = fightRequest.action;
		vo_19959_0.vid = fightRequest.vid; // 召唤的宠物id
		vo_19959_0.para = fightRequest.para;
		FightManager.send(fightContainer, new MSG_C_ACTION(), vo_19959_0);
		FightObject charObject = FightManager.getFightObject(fightContainer, fightRequest.id);
		FightObject fightObjectPet = FightManager.getFightObjectPet(fightContainer, charObject);
		if (fightObjectPet != null) {
			//让原来的宠物立马离开
			FightManager.send(fightContainer, new MSG_C_LEAVE_AT_ONCE(), fightObjectPet.id);
			
			Vo_7653_0 vo_7653_0 = new Vo_7653_0();
			vo_7653_0.id = fightObjectPet.fid;
			FightManager.send(fightContainer, new M7653_0(), vo_7653_0);
			Vo_64971_0 vo_64971_0 = new Vo_64971_0();
			vo_64971_0.id = fightObjectPet.id;
			vo_64971_0.haveCalled = 0;
			FightManager.send(fightContainer, new MSG_C_SET_FIGHT_PET(), vo_64971_0);
			
			
			FightManager.remove(fightContainer, fightObjectPet);
			vo_64971_0 = new Vo_64971_0();
			vo_64971_0.count = 1;
			vo_64971_0.id = fightObjectPet.id;
			vo_64971_0.haveCalled = 0;
			gameObjectChar.sendOne(new MSG_C_REFRESH_PET_LIST(), vo_64971_0);
		}

		
		Chara chara = GameObjectCharMng.getGameObjectChar(fightRequest.id).chara;
		//获取我方队伍
		FightTeam friendsFightTeam = FightManager.getFightTeam(fightContainer, fightRequest.id);
		//获取敌方队伍
		FightTeam opponentsFightTeam = FightManager.getFightTeamDM(fightContainer, fightRequest.id);
		//获取角色宠物列表
		List<Petbeibao> pets = GameObjectCharMng.getGameObjectChar(fightRequest.id).chara.pets;
		FightObject fightObject = null; // 这个是要召唤的宠物
		Petbeibao pet = null;
		for (int j = 0; j < pets.size(); ++j) {
			Petbeibao petbeibao = pets.get(j);
			// 这里不是仅仅的判断宝宝ID等于参战ID，还需要判断角色的等级和宠物的等级差是否在15级之内。
			if (petbeibao.id == fightRequest.vid && (petbeibao.petShuXing.get(0).attrib - chara.level) <= 15) {
				if(fightRequest.vid != chara.chongwuluezhenId) {
					chara.chongwuchanzhanId = fightRequest.vid;
				}
				fightObject = new FightObject(petbeibao, gameObjectChar.chara);
				fightObject.pos = charObject.pos + 5;
				fightObject.isLueZhen = true;
				fightObject.fid = petbeibao.id;
				fightObject.id = petbeibao.id;
				fightObject.cid = chara.id;
				if (petbeibao.tianshu.size() != 0) {
					Vo_12023_0 vo_12023_0 = petbeibao.tianshu
							.get(FightManager.RANDOM.nextInt(petbeibao.tianshu.size()));
					fightObject.godbook = FightTianshuMap.TIANSHU_EFFECT.get(vo_12023_0.god_book_skill_name);
				}
				friendsFightTeam.add(fightObject);
				pet = petbeibao;
				break;
			}
		}

		if (fightObject == null) {
			return null;
		}
		
		//战斗vo对象
		Vo_ADD_FRIEND_OPPONENT vo_65017_0 = GameUtil.vo_65017_0(fightObject);
		List<Vo_ADD_FRIEND_OPPONENT> list65017 = new ArrayList<Vo_ADD_FRIEND_OPPONENT>();
		list65017.add(vo_65017_0);
		// 添加友方
		FightManager.sendTeam(fightContainer, friendsFightTeam.fightObjectList, new MSG_C_FRIENDS(), list65017);
		// 添加敌方
		FightManager.sendTeam(fightContainer, opponentsFightTeam.fightObjectList, new MSG_C_OPPONENTS(), list65017);
		
		if(fightContainer.lookCharas != null && !fightContainer.lookCharas.isEmpty()) {
			Iterator<Entry<Integer, GameObjectChar>> iterator = fightContainer.lookCharas.entrySet().iterator();
			while (iterator.hasNext()) {
				GameObjectChar value = iterator.next().getValue();
				if (value != null) {
					if(value.chara != null && value.chara.isFight) {
						// 这人在战斗有可能是异常导致没有移除，这里手动清理
						iterator.remove();
						continue;
					}
					boolean isFriend = false;
					for(FightObject figObject:friendsFightTeam.fightObjectList) {
						if(figObject.fid == value.lookCharId) {
							isFriend = true;
							break;
						}
					}
					if(isFriend) {
						value.sendOne(new MSG_C_FRIENDS(), list65017);
					}else {
						value.sendOne(new MSG_C_OPPONENTS(), list65017);
					}
				}
			}
		}
		
		Vo_64971_0 vo_64971_2 = new Vo_64971_0();
		vo_64971_2.count = 1;
		vo_64971_2.id = fightObject.id;
		vo_64971_2.haveCalled = 1;
		gameObjectChar.sendOne(new MSG_C_REFRESH_PET_LIST(), vo_64971_2);
		
		FightManager.getRandomGodbookEffect(friendsFightTeam.fightObjectList, fightContainer);
		int addFightTianShuType = fightObject.getRandomTianshuType(fightContainer);
		Vo_GODBOOK_EFFECT vo_12025_0 = new Vo_GODBOOK_EFFECT();
		vo_12025_0.id = fightObject.fid;
		vo_12025_0.effect_no = addFightTianShuType;
		FightManager.send(fightContainer, new MSG_GODBOOK_EFFECT_SUMMON(), vo_12025_0);		
		//参战宠物
		gameObjectChar.sendOne(new MSG_C_SET_FIGHT_PET(), new Vo_64971_0(fightObject.id, 1));
		gameObjectChar.sendOne(new MSG_UPDATE_PETS(), Lists.newArrayList(pet));
		//技能说话
		FightRequest newRequest = new FightRequest();
		newRequest.id = fightObject.id;
		newRequest.skill_talk = fightRequest.skill_talk;
		newRequest.action = fightRequest.action;
		//召唤比较特殊
		FightManager.setAutoTalkMsg(fightObject, newRequest);
		FightManager.autoTalkAction(fightContainer, newRequest);
		
		FightManager.send(fightContainer, new MSG_C_END_ACTION(), new Vo_C_END_ACTION(fightRequest.id));
		return null;
	}

	@Override
	public int getStateType() {
		return 0;
	}
}
