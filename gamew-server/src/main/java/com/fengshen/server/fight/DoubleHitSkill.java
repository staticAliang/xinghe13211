package com.fengshen.server.fight;

import java.util.List;

import com.fengshen.server.domain.JiNeng;

public class DoubleHitSkill implements FightSkill {
	@Override
	public List<FightResult> doSkill(final FightContainer fightContainer, final FightRequest fightRequest,
			final JiNeng jiNeng) {
		return null;
	}

	@Override
	public int getStateType() {
		return 0;
	}
}
