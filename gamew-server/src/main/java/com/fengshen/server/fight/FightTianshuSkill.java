package com.fengshen.server.fight;

import java.util.List;

import com.fengshen.server.data.vo.Vo_12028_0;
import com.fengshen.server.data.write.M12028_0;
import com.fengshen.server.domain.JiNeng;
import com.fengshen.server.game.GameUtil;

public abstract class FightTianshuSkill implements FightSkill {
	protected FightObject buffObject;
	protected FightContainer fightContainer;

	public FightTianshuSkill() {
		this.buffObject = null;
	}

	public boolean isActive() {
		// 将反击的触发几率调低一点
		if (this.getStateType() == 8049)
			return GameUtil.getChance(20);
		if (this.getStateType() == 7040)
			return GameUtil.getChance(30);
		return FightManager.RANDOM.nextBoolean();
	}

	@Override
	public List<FightResult> doSkill(final FightContainer fightContainer, final FightRequest fightRequest,
			final JiNeng jiNeng) {
		return null;
	}

	// 这里是发送天书的特效动画
	public void sendEffect(final FightContainer fightContainer) {
		final Vo_12028_0 vo_12028_0 = new Vo_12028_0();
		vo_12028_0.id = this.buffObject.fid;
		vo_12028_0.effect_no = 0;
		vo_12028_0.type = 4;
		vo_12028_0.name = this.getName();
		FightManager.send(fightContainer, new M12028_0(), vo_12028_0);
	}

	public abstract String getName();
}
