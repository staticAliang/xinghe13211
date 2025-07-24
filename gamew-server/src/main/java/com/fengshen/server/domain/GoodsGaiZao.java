package com.fengshen.server.domain;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class GoodsGaiZao {
	public Integer groupNo;
	public Integer groupType;
	// 改造伤害
	public Integer accurate;
	// 改造属性
	public Integer all_polar;
	public Integer wiz;
	public Integer def;
	public Integer mana;

	public GoodsGaiZao() {
		this.groupNo = 10;
		this.groupType = 2;
		this.accurate = 0;
		this.all_polar = 0;
		this.wiz = 0;
		this.def = 0;
		this.mana = 0;
	}
}
