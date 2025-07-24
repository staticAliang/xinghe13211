package com.fengshen.server.fight;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import com.fengshen.server.data.vo.Vo_12028_0;
import com.fengshen.server.data.write.M12028_0;
import com.fengshen.server.domain.JiNeng;

public abstract class FightFabaoSkill implements FightSkill {
	protected FightObject buffObject;
	protected FightContainer fightContainer;
	public int level; // 法宝等级
	public int qinmi; // 法宝亲密
	// 添加法宝道法
	public int daofa; // 法宝道法
	@SuppressWarnings("unused")
	private int times;

	public FightFabaoSkill() {
		this.buffObject = null;
		this.times = 0;
	}

	public void active() {
		this.times = 1;
	}

	public abstract int getTimes();

	public void resetTimes() {
		this.times = 0;
	}
	
	

	// 新增法宝的激活算法
	// 触发几率判断1=法宝等级/100+0.1（亲密度>1万）
	// 触发几率判断2=法宝等级/100+0.15（亲密度>10万）
	// 触发几率判断3=法宝等级/100+0.2（10万<亲密度<50万）
	// 触发几率判断4=法宝等级/100+0.25（亲密度>50万）
	/**
	 * @return
	 */
	public boolean isActive() {
		boolean active = false;
		double randomNum = ThreadLocalRandom.current().nextDouble(100) + 1;
		if (randomNum < getFaBaoActiveNum()) {
			active = ThreadLocalRandom.current().nextBoolean();
		}
		return active;
	}

	@Override
	public List<FightResult> doSkill(final FightContainer fightContainer, final FightRequest fightRequest,
			final JiNeng jiNeng) {
		return null;
	}

	public void sendEffect(final FightContainer fightContainer) {
		final Vo_12028_0 vo_12028_0 = new Vo_12028_0();
		vo_12028_0.id = this.buffObject.fid;
		vo_12028_0.effect_no = this.getStateType();
		vo_12028_0.type = 0;
		FightManager.send(fightContainer, new M12028_0(), vo_12028_0);
	}

	/**
	 * 获取法宝除法几率
	 * 
	 * @return
	 */
	public double getFaBaoActiveNum() {
		double num = 0.9;
		switch (level) {
		case 2:
			num = 1;
			break;
		case 3:
			num = 2;
			break;
		case 4:
			num = 3;
			break;
		case 5:
			num = 5;
			break;
		case 6:
			num = 7;
			break;
		case 7:
			num = 10;
			break;
		case 8:
			num = 13;
			break;
		case 9:
			num = 16;
			break;
		case 10:
			num = 20;
			break;
		case 11:
			num = 23;
			break;
		case 12:
			num = 25;
			break;
		case 13:
			num = 27;
			break;
		case 14:
			num = 29;
			break;
		case 15:
			num = 32;
			break;
		case 16:
			num = 35;
			break;
		case 17:
			num = 38;
			break;
		case 18:
			num = 40;
			break;
		case 19:
			num = 45;
			break;
		case 20:
			num = 50;
			break;
		case 21:
			num = 60;
			break;
		case 22:
			num = 65;
			break;
		case 23:
			num = 80;
			break;
		case 24:
			num = 90;
			break;
		}
		return num;
	}
}