package com.fengshen.server.data.vo.tongtianta;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Vo_TONGTIANTA_BONUS_DLG {

	// 奖励类型
	private String bonusType;

	private int dlgType;

	/**
	 * 
	 * 具体值算法----例如奖励是1200 、
	 * int year = 1200/360; 
	 * int day = 1200-(year*360);
	 */
	private int bonusValue;

	// 道行计算点.
	private int bonusTaoPoint;

	public Vo_TONGTIANTA_BONUS_DLG() {
		this.bonusTaoPoint = 0;
	}
}
