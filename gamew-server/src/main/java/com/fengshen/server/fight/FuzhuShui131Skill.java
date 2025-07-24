package com.fengshen.server.fight;

import java.util.List;

import com.fengshen.server.data.vo.fight.Vo_ACCEPT_MAGIC_HIT;
import com.fengshen.server.data.vo.fight.Vo_ACCEPT_MAGIC_HIT.Info;
import com.fengshen.server.data.vo.fight.Vo_C_ACCEPT_HIT;
import com.fengshen.server.data.vo.fight.Vo_C_ACTION;
import com.fengshen.server.data.vo.fight.Vo_C_END_ACTION;
import com.fengshen.server.data.write.M64991_0;
import com.fengshen.server.data.write.M65527_0;
import com.fengshen.server.data.write.fight.c.MSG_C_ACCEPT_HIT;
import com.fengshen.server.data.write.fight.c.MSG_C_ACCEPT_MAGIC_HIT;
import com.fengshen.server.data.write.fight.c.MSG_C_ACTION;
import com.fengshen.server.data.write.fight.c.MSG_C_END_ACTION;
import com.fengshen.server.domain.JiNeng;
import com.fengshen.server.domain.ZbAttribute;
import com.fengshen.server.game.GameObjectChar;
import com.fengshen.server.game.GameObjectCharMng;
import com.fengshen.server.game.GameUtil;

/**
 * 全部是水系辅助技能，加防御和抗障碍能力
 * 队伍按速度从快到慢来分配，速度快者能优先得到水的辅助、ps:点击目标必然会有辅助
 * 
 *
 */
// 
public class FuzhuShui131Skill extends FightRoundSkill {
	@Override
	public List<FightResult> doSkill(FightContainer fightContainer, FightRequest fightRequest, JiNeng jiNeng) {
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

		List<FightObject> targetList = FightManager.findTarget(fightContainer, fightRequest, 2, jiNeng.range);
		for (FightObject fightObject : targetList) {
			vo_64989_0.infos.add(new Info(fightObject.fid,1));
		}
		FightManager.send(fightContainer, new MSG_C_ACCEPT_MAGIC_HIT(), vo_64989_0);

		for (FightObject fightObject : targetList) {
			fightObject.addBuffState(fightContainer, this.getStateType());
			FuzhuShui131Skill that = new FuzhuShui131Skill();
			fightObject.addSkill(that);
			that.buffObject = fightObject;
			that.removeRound = fightContainer.round + jiNeng.skillRound - 1;
			that.fightContainer = fightContainer;
			int def = (int) ((jiNeng.skill_level+jiNeng.level_improved)*getOrderNum(fightRequest.para)+(fightObject.fangyu*0.3));
			that.buffObject.fangyu_ext = def;
			
			ZbAttribute zbAttribute = new ZbAttribute();
			zbAttribute.id = fightObject.id;
			zbAttribute.wiz = that.buffObject.fangyu_ext;
			FightManager.send(fightContainer, new M64991_0(), zbAttribute);
			if (that.buffObject.type == 1) {
				GameObjectChar gameObjectChar = GameObjectCharMng.getGameObjectChar(that.buffObject.fid);
				if (null != gameObjectChar) {
					gameObjectChar.sendOne(new M65527_0(), GameUtil.a65527(gameObjectChar.chara));
				}
			}
		}
		FightManager.send(fightContainer, new MSG_C_END_ACTION(), new Vo_C_END_ACTION(fightRequest.id));
		return null;
	}
	
	/**
	 * a根据技能等级阶数来获取防御基数
	 * @param skillNo 技能
	 * @return
	 */
	public int getOrderNum(int skillNo) {
		int defaultNum = 100;
		if(skillNo == 132) {
			defaultNum = 110;
		}else if(skillNo == 133) {
			defaultNum = 120;
		}else if(skillNo == 134) {
			defaultNum = 130;
		}else if(skillNo == 135) {
			defaultNum = 140;
		}
		return defaultNum;
	}
	

	@Override
	protected void doRoundSkill() {
	}

	@Override
	protected void doDisappear() {
		ZbAttribute zbAttribute = new ZbAttribute();
		zbAttribute.id = this.buffObject.id;
		zbAttribute.wiz = 0;
		FightManager.send(this.fightContainer, new M64991_0(), zbAttribute);

		this.buffObject.fangyu_ext = 0;
	}

	@Override
	public int getStateType() {
		return 265984;
	}
}
