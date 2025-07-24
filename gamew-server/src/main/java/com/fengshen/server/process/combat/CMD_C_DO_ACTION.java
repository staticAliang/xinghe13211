package com.fengshen.server.process.combat;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.fengshen.server.data.GameReadTool;
import com.fengshen.server.data.vo.fight.Vo_C_SANDGLASS;
import com.fengshen.server.data.vo.fight.Vo_SELECT_COMMAND;
import com.fengshen.server.data.write.fight.MSG_SELECT_COMMAND;
import com.fengshen.server.data.write.fight.c.MSG_C_COMMAND_ACCEPTED;
import com.fengshen.server.data.write.fight.c.MSG_C_SANDGLASS;
import com.fengshen.server.domain.Chara;
import com.fengshen.server.domain.Goods;
import com.fengshen.server.domain.JiNeng;
import com.fengshen.server.fight.FightContainer;
import com.fengshen.server.fight.FightManager;
import com.fengshen.server.fight.FightObject;
import com.fengshen.server.fight.FightRequest;
import com.fengshen.server.game.GameCommonUtil;
import com.fengshen.server.game.GameHandler;
import com.fengshen.server.game.GameObjectChar;
import com.fengshen.server.game.GameUtil;
import com.fengshen.server.process.CommonCmd;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

// 召回/召唤的代码都在这里
//CMD_C_DO_ACTION
/**
 * DEFENSE = 1, -- 防御 
 * PHYSICAL_ATTACK = 2, -- 物理攻击 
 * CAST_MAGIC =3, -- 施展魔法
 * APPLY_ITEM = 4, -- 使用道具 
 * USE_ARTIFACT = 5, -- 使用宝物 
 * USE_STUNT = 6,-- 施展绝技 
 * FLEE = 7, -- 逃跑 
 * SELECT_PET = 8, -- 选择宠物出战 
 * CATCH_PET = 9, -- 捕捉宠物
 * GUARD = 10, -- 保护 
 * JOINT_ATTACK = 11, -- 合击 
 * DOUBLE_HIT = 12, -- 连击 
 * LEECH_MANA = 13, -- 吸魔 
 * CALLBACK_PET = 14, -- 召回宠物 
 * ACTION_USE_ARTIFACT_EXTRA_SKILL = 16, -- 使用法宝特殊技能 
 * DIE = 40, -- 死亡 
 * REVIVE = 41, -- 重生 
 * HEAL = 42, -- 治疗 
 * CHECK_STATUS = 43, -- 检查状态
 * COUNTER_ATTACK = 44, -- 反击
 * SELECT_MENU = 45, -- 选择菜单
 * DISAPPEAR = 46, -- 直接消失
 *  DEADLY_KISS = 47, -- 死亡缠绵
 *  DOUBLE_MAGIC_HIT = 48, -- 法术攻击双击
 * CANCEL = 52, -- 取消输入
 * SPECIAL = 98, -- 特殊动作
 * NULL = 99, -- 不做动作
 */
@Service
@Slf4j
public class CMD_C_DO_ACTION implements GameHandler {
	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		int id = GameReadTool.readInt(buff); // 战斗id
		int victim_id = GameReadTool.readInt(buff); // 攻击目标
		int action = GameReadTool.readInt(buff); // 释放的技能类型
		int para = GameReadTool.readInt(buff); // 施法的技能ID
		String para2 = GameReadTool.readString(buff);
		String para3 = GameReadTool.readString(buff);
		String para4 = GameReadTool.readString(buff);
		GameReadTool.readString(buff);
		GameObjectChar gameObjectChar = GameObjectChar.getGameObjectChar();
		Chara chara = gameObjectChar.chara;
		FightContainer fightContainer = FightManager.getFightContainer();
		if(fightContainer == null) {
			return;
		}
		boolean checkSkill = false;
		//如果自己都为空的话那就直接防御
		FightObject fightObject = FightManager.getFightObject(fightContainer, id);
		if (fightObject == null) {
			if(action == 52) {
				return;
			}
			log.error("对象为空返回...");
			return;
		}
		if (action == 52) {
			if(fightObject.type == 1) {
				//人物返回
				log.info("人物点击返回");
			}else if(fightObject.type == 2) {
				//宠物返回
				FightObject cfight = FightManager.getFightObject(fightObject.cid);
				if(cfight != null) {
					cfight.fightRequest = null;
				}
				log.info("宠物点击返回");
			}
			if(fightContainer.state.get() == 1) {
				//返回操作
				log.error("取消输入...");
				Map<String,Object> obj = new LinkedHashMap<>();
				obj.put("int:id", chara.id);
				obj.put("short:result", 1);
				GameObjectChar.send(new CommonCmd(0x2DD3), obj);
				obj.put("short:result", 0);
				GameObjectChar.send(new CommonCmd(0x2DD3), obj);
			}
			return;
		}
		if(fightObject.fightRequest != null) {
			return;
		}
		// 法术技能
		if (action == 3) {
			List<JiNeng> jiNengList = fightObject.skillsList;
			for (JiNeng jiNeng : jiNengList) {
				if (jiNeng.skill_no == para) {
					checkSkill = true;
					break;
				}
			}
			if (!checkSkill) {
				return;
			}
		}

		FightRequest fr = new FightRequest();
		fr.id = id;
		fr.action = action;
		fr.vid = victim_id;
		fr.para = para;
		fr.para1 = para2;
		fr.para2 = para3;
		fr.para3 = para4;
		try {
			//设置自动喊话
			FightManager.setAutoTalkMsg(fightObject, fr);
		} catch (Exception e) {
			log.error("{}",e);
		}
		// 如果是角色
		if (fightObject.type == 1) {
			// 如果参战的宠物不为空，或者宠物已经死亡
			Vo_SELECT_COMMAND vo_53715_0 = new Vo_SELECT_COMMAND();
			vo_53715_0.attacker_id = id;
			vo_53715_0.victim_id = victim_id;
			vo_53715_0.action = action;
			if (para != 2) {
				vo_53715_0.no = para;
			}
			// 使用了道具
			if (action == 4) {
				Goods beibaowupin = GameUtil.beibaowupin(chara, para);
				if (beibaowupin != null) {
					vo_53715_0.no = beibaowupin.goodsInfo.type;
					fr.item_type = beibaowupin.goodsInfo.type;
				}
			}
			GameObjectChar.send(new MSG_C_COMMAND_ACCEPTED(), new Object[] {id,2});
			//如果他的宠物还在的话
			FightObject fightObjectPet = FightManager.getFightObjectPet(fightContainer, fightObject);
			//如果为空或者是已经死亡
			if(fightObjectPet == null || fightObjectPet.isDead()) {
				FightManager.send(fightContainer,new MSG_C_SANDGLASS(), new Vo_C_SANDGLASS(chara.id, 0));
			}
		} else {
			FightObject fightObjectChar = FightManager.getFightObject(fightContainer, chara.id);
			if (fightObjectChar == null) {
				FightManager.doOver(fightContainer);
				FightManager.nextRoundOrSendOver(fightContainer,gameObjectChar);
				log.info("战斗对象为空={},名字={}", fightObject, chara.name);
				return;
			}
			if(fightObjectChar.fightRequest != null) {
				Vo_SELECT_COMMAND vo_53715_0 = new Vo_SELECT_COMMAND();
				vo_53715_0.attacker_id = fightObjectChar.fightRequest.id;
				vo_53715_0.victim_id = fightObjectChar.fightRequest.vid;
				vo_53715_0.action = fightObjectChar.fightRequest.action;
				if (vo_53715_0.action != 2) {
					vo_53715_0.no = fightObjectChar.fightRequest.para;
				}
				
				// 使用了道具
				if (fightObjectChar.fightRequest.action == 4) {
					Goods beibaowupin = GameUtil.beibaowupin(chara, fightObjectChar.fightRequest.para);
					if (beibaowupin != null) {
						vo_53715_0.no = beibaowupin.goodsInfo.type;
						fightObjectChar.fightRequest.item_type = beibaowupin.goodsInfo.type;
					}
				}
				GameObjectChar.send(new MSG_SELECT_COMMAND(), vo_53715_0);
			}

			Vo_SELECT_COMMAND vo_53715_0 = new Vo_SELECT_COMMAND();
			vo_53715_0.attacker_id = id;
			vo_53715_0.victim_id = victim_id;
			vo_53715_0.action = action;
			if (para != 2) {
				vo_53715_0.no = para;
			}

			if (action == 4) {
				Goods beibaowupin = GameUtil.beibaowupin(chara, para);
				if (beibaowupin != null) {
					vo_53715_0.no = beibaowupin.goodsInfo.type;
					fr.item_type = beibaowupin.goodsInfo.type;
				}
			}
			GameObjectChar.send(new MSG_SELECT_COMMAND(), vo_53715_0);
			FightManager.send(fightContainer,new MSG_C_SANDGLASS(), new Vo_C_SANDGLASS(chara.id, 0));
		}
		// 每次攻击的id
		gameObjectChar.victimId = victim_id;
		FightManager.changeAutoFightSkill(fightContainer, fightObject, action, para);
		GameCommonUtil.fightCmdInfo(gameObjectChar);
		FightManager.addRequest(fightContainer, fr);
		
	}

	@Override
	public int cmd() {
		return 12802;
	}
}
