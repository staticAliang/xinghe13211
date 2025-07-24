package com.fengshen.server.fight;

// 战斗回合技能
public abstract class FightRoundSkill implements FightSkill {
	protected FightObject buffObject;
	protected int removeRound; // 持续的回合数
	protected FightContainer fightContainer;
	protected int skillLevel;
	
	public FightRoundSkill() {
		this.buffObject = null;
		this.skillLevel = 0;
	}

	// 如果当前技能的最大回合次数已经等于当前战斗的回合数（即技能已达到最大回合次数）
	// 就将该技能的增益效果移除(包括实际的buff效果和动画特效)
	public boolean disappear(final FightContainer fightContainer) {
		if (this.removeRound <= fightContainer.round) {
			this.doDisappear();
			this.buffObject.removeBuffState(fightContainer, this.getStateType());
			return true;
		}
		return false;
	}

	protected abstract void doRoundSkill();

	protected abstract void doDisappear();
}
