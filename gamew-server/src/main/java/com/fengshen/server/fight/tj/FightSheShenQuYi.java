package com.fengshen.server.fight.tj;

import com.fengshen.server.data.vo.Vo_15855_0;
import com.fengshen.server.data.vo.fight.*;
import com.fengshen.server.data.vo.fight.Vo_ACCEPT_MAGIC_HIT.Info;
import com.fengshen.server.data.write.M15855_0;
import com.fengshen.server.data.write.fight.c.*;
import com.fengshen.server.data.write.leitai.MSG_COMPETE_TOURNAMENT_INFO;
import com.fengshen.server.domain.JiNeng;
import com.fengshen.server.fight.*;
import com.fengshen.server.game.GameCommonUtil;
import com.fengshen.server.game.GameUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 舍命一击
 * @author aaa
 *
 */
public class FightSheShenQuYi extends FightRoundSkill {
	public FightSheShenQuYi() {
	}

	public List<FightResult> doSkill(FightContainer fightContainer, FightRequest fightRequest, JiNeng jiNeng) {
		int attaNum = jiNeng.range;
		FightObject attFightObject = FightManager.getFightObject(fightContainer, fightRequest.id);
		FightObject fightObjectBei = FightManager.getFightObject(fightContainer, fightRequest.vid);
		List<FightObject> targetList = FightManager.findTarget(fightContainer, fightRequest, 2, attaNum);
		Vo_C_ACTION vo_19959_0 = new Vo_C_ACTION();
		vo_19959_0.round = fightContainer.round;
		vo_19959_0.aid = fightRequest.id;
		vo_19959_0.action = fightRequest.action;
		vo_19959_0.vid = fightRequest.vid;
		vo_19959_0.para = fightRequest.para;
		FightManager.send(fightContainer, new MSG_C_ACTION(), vo_19959_0);
		GameCommonUtil.sendTips("技能说话", fightObjectBei.id);
		FightManager.autoTalkAction(fightContainer, fightRequest);
		Vo_C_ACCEPT_HIT vo_19945_0 = new Vo_C_ACCEPT_HIT();
		vo_19945_0.id = fightRequest.id;
		vo_19945_0.hid = fightRequest.id;
		vo_19945_0.para_ex = 0;
		vo_19945_0.missed = 1;
		vo_19945_0.para = 0;
		vo_19945_0.damage_type = 2;
		FightManager.send(fightContainer, new MSG_C_ACCEPT_HIT(), vo_19945_0);
		Vo_ACCEPT_MAGIC_HIT vo_64989_0 = new Vo_ACCEPT_MAGIC_HIT();
		vo_64989_0.hid = fightRequest.id;
		vo_64989_0.damageType = 2;
		vo_64989_0.infos.add(new Info(((FightObject)targetList.get(0)).fid, 1));
		FightManager.send(fightContainer, new MSG_C_ACCEPT_MAGIC_HIT(), vo_64989_0);
		Vo_C_LIFE_DELTA vo_15857_0 = new Vo_C_LIFE_DELTA();
		vo_15857_0.id = fightRequest.id;
		vo_15857_0.hitter_id = fightRequest.id;
		vo_15857_0.point = -attFightObject.shengming;
		vo_15857_0.effect_no = 0;
		vo_15857_0.damage_type = 2;
		FightManager.send(fightContainer, new MSG_C_LIFE_DELTA(), vo_15857_0);
		attFightObject.reduceShengming(attFightObject.shengming, false);
		FightResult fightResult = new FightResult();
		fightResult.id = attFightObject.id;
		fightResult.vid = fightRequest.id;
		fightResult.effect_no = 0;
		fightResult.damage_type = 4097;
		fightResult.point = 0;
		FightManager.send_LIFE_DELTA(fightContainer, fightResult);
		int recoverHp = (int)((double)(attFightObject.max_shengming * this.skillLevel) * 0.01D);
		if (recoverHp <= 0) {
			recoverHp = 1000000;
		}

		int shengming = fightObjectBei.addShengming(recoverHp);
		if (fightObjectBei.state.get() == 2) {
			fightObjectBei.state.set(1);
			fightObjectBei.revive(fightContainer);
		} else {
			fightResult.id = fightRequest.id;
			fightResult.vid = fightRequest.vid;
			fightResult.point = shengming;
			FightManager.send_LIFE_DELTA(fightContainer, fightResult);
		}

		FightManager.send(fightContainer, new MSG_C_END_ACTION(), new Vo_C_END_ACTION(fightRequest.id));
		return null;
	}

	protected void doRoundSkill() {
	}

	protected void doDisappear() {
	}

	public int getStateType() {
		return 0;
	}

	public static void main(String[] args) {
		List list = new ArrayList();
		list.add(1);
		list.add(2);
		list.add(3);
		list.add(4);
		list.add(5);
		for(int i=0;i<list.size();i++){

			System.out.println(list.get(i));
			System.out.println(list.get(++i));

		}
	}
}