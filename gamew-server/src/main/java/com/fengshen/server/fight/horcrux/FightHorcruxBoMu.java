package com.fengshen.server.fight.horcrux;

import java.util.List;

import com.fengshen.server.domain.JiNeng;
import com.fengshen.server.fight.FightContainer;
import com.fengshen.server.fight.FightRequest;
import com.fengshen.server.fight.FightResult;
import com.fengshen.server.fight.FightRoundSkill;

/**
 * 战斗技能薄暮
 * 回合开始时有几率获得薄暮效果，提升%d点防御
 * @author aaa
 *
 */
public class FightHorcruxBoMu extends FightRoundSkill {

	@Override
	public List<FightResult> doSkill(FightContainer fightContainer, FightRequest fightRequest, JiNeng jiNeng) {
//		int skillLevel = jiNeng.skill_level;
//		int gailv = skillLevel * 10;
//		int fangyu = (int) (Formula.getStdLife(125) * 0.7 / 1.5 * 2 * jiNeng.level_improved / 5);
		
		return null;
	}

	@Override
	public int getStateType() {
		
		return 1424;
	}

	@Override
	protected void doRoundSkill() {
		
	}

	@Override
	protected void doDisappear() {
		
	}

}
