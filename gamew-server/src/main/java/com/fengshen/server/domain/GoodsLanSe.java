package com.fengshen.server.domain;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class GoodsLanSe {
	public Integer groupNo;
	public Integer groupType;
	// 力量
	public Integer phy_power;
	// 灵力
	public Integer mag_power;
	// 敏捷
	public Integer speed;
	// 体质
	public Integer life;
	// 天伤
	public Integer skill_low_cost;
	public Integer mstunt_rate;
	// 所有属性
	public Integer all_polar;
	// 所有相性
	public Integer all_resist_polar;
	// 金
	public Integer wood;
	// 木
	public Integer water;
	// 水
	public Integer fire;
	// 火相
	public Integer earth;
	// 土相性
	public Integer resist_metal;
	// 物理必杀率
	public Integer damage_sel;
	// 物理连击率
	public Integer stunt_rate;
	public Integer double_hit_rate;
	public Integer release_forgotten;
	public Integer ignore_all_resist_except;
	public Integer stunt;
	public Integer def;
	public Integer dex;
	public Integer wiz;
	public Integer family;
	public Integer life_recover;
	public Integer all_skill;
	public Integer portrait;
	// 抗冰冻
	public Integer resist_frozen;
	// 抗晕睡
	public Integer resist_sleep;
	// 抗遗忘
	public Integer resist_forgotten;
	// 抗混乱
	public Integer resist_confusion;
	public Integer longevity;
	public Integer resist_wood;
	public Integer resist_water;
	public Integer resist_fire;
	public Integer resist_earth;
	public Integer exp_to_next_level;
	public Integer all_resist_except;
	public Integer accurate;
	public Integer mana;
	// 速度
	public Integer parry;
	public Integer ignore_resist_wood;
	public Integer ignore_resist_water;
	public Integer ignore_resist_fire;
	public Integer ignore_resist_earth;
	public Integer ignore_resist_forgotten;
	public Integer ignore_resist_frozen;
	public Integer ignore_resist_sleep;
	public Integer ignore_resist_confusion;
	public Integer super_excluse_metal;
	public Integer ignore_resist_poison;

	public GoodsLanSe() {
		this.groupNo = 2;
		this.groupType = 2;
		this.phy_power=0;
		this.mag_power=0;
		this.speed=0;
		this.life=0;
		this.skill_low_cost=0;
		this.mstunt_rate=0;
		this.all_polar=0;
		this.all_resist_polar=0;
		this.wood=0;
		this.water=0;
		this.fire=0;
		this.earth=0;
		this.resist_metal=0;
		this.damage_sel=0;
		this.stunt_rate=0;
		this.double_hit_rate=0;
		this.release_forgotten=0;
		this.ignore_all_resist_except=0;
		this.stunt=0;
		this.def=0;
		this.dex=0;
		this.wiz=0;
		this.family=0;
		this.life_recover=0;
		this.all_skill=0;
		this.portrait=0;
		this.resist_frozen=0;
		this.resist_sleep=0;
		this.resist_forgotten=0;
		this.resist_confusion=0;
		this.longevity=0;
		this.resist_wood=0;
		this.resist_water=0;
		this.resist_fire=0;
		this.resist_earth=0;
		this.exp_to_next_level=0;
		this.all_resist_except=0;
		this.accurate=0;
		this.mana=0;
		this.parry=0;
		this.ignore_resist_wood=0;
		this.ignore_resist_water=0;
		this.ignore_resist_fire=0;
		this.ignore_resist_earth=0;
		this.ignore_resist_forgotten=0;
		this.ignore_resist_frozen=0;
		this.ignore_resist_sleep=0;
		this.ignore_resist_confusion=0;
		this.super_excluse_metal=0;
		this.ignore_resist_poison=0;
	}
}
