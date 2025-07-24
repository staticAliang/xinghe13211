package com.fengshen.server.fight;

// 这里是天书技能
public class XiuluoshuSkill extends FightTianshuSkill {
	private String name;
	private int stateType;

	public XiuluoshuSkill(final String tsName) {
		this.name = tsName;
		this.stateType = FightTianshuMap.TIANSHU_EFFECT.get(tsName);
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public int getStateType() {
		return this.stateType;
	}
}
