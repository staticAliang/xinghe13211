package com.fengshen.server.domain;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class GoodsLvSeGongMing {
	public Integer groupNo;
	public Integer groupType;
	public Integer mana;
	public Integer def;
	public Integer wiz;
	public Integer parry;
	public Integer accurate;

	public GoodsLvSeGongMing() {
		this.groupNo = 8;
		this.groupType = 2;
		this.groupNo = 0;
		this.groupType = 0;
		this.mana = 0;
		this.def = 0;
		this.wiz = 0;
		this.parry = 0;
		this.accurate = 0;
	}
}
