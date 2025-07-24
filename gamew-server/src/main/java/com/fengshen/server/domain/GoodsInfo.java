package com.fengshen.server.domain;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class GoodsInfo {
	// 首饰的强化等级
	public Integer groupNo;
	public Integer groupType;
	public Integer value;
	public Integer total_score;
	public Integer type;
	public Integer rebuild_level;
	public Integer parry;
	public String str;
	public String auto_fight;
	public String quality; // 商品颜色
	public Integer damage_sel_rate;
	public Integer recognize_recognized;
	public Integer owner_id; // 商品数量的意思
	// 完美度
	public Integer dunwu_times;
	public Integer attrib; // 商品或者装备的等级
	public Integer gift;
	public Integer nick;
	public Integer power;
	public Integer wrestlescore;
	public Integer skill; // 等级(法宝等级)
	public Integer store_exp; // 改造进度
	public Integer metal;
	// 1是武器，2是头盔，3是衣服，4是项链，5是玉佩，6是手镯，10是鞋子，
	public Integer amount; // 物品的类型，装备、首饰是非零，物品是0
	public Integer color; // 是改造等级
	public Integer suit_degree;
	public Integer party_stage_party_name;
	// 进化星级
	public Integer mailing_item_times;
	// 绿相性
	public Integer suit_enabled;
	public Integer degree_32; // 1是未鉴定，0是已鉴定的
	public Integer master;
	public Integer transform_cool_ti;
	public Integer silver_coin;
	public Integer diandqk_frozen_round;
	public Integer shuadao_ziqihongmeng; // 法宝的属性，1-5表示5个属性
	public Integer durability;
	public Integer add_pet_exp;
	public String alias;
	public Integer food_num;
	public Integer merge_rate;
	public Integer fasion_type;
	public Integer pet_upgraded;
	public Integer couple;
	public Integer shape; // 物品的亲密度（法宝亲密度会在这里增加）
	public Integer pot; // 法宝的道法
	public Integer resist_poison;
	public String phy_rebuild_level;
	public Integer max_durability;
	public Integer skill_level;
	public Integer upgrade_degree;
	// 首饰转换次数
	public Integer transform_num;
	// 首饰强化进度
	public Integer strengthen_degree;
	// 首饰强化等级
	public Integer strengthen_level;
	//耐久度
	public Integer durability2;
	public Integer max_durability2;
	//是否启灵
	public Integer open_nimbus;
	public GoodsInfo() {
		this.groupNo = 0;
		this.groupType = 1;
		this.str = "";
		this.auto_fight = "";
		this.quality = "";
		this.alias = "";
		this.phy_rebuild_level = "";

		this.value = 0;
		this.total_score = 0;
		this.type = 0;
		this.rebuild_level = 0;
		this.parry = 0;
		this.damage_sel_rate = 0;
		this.recognize_recognized = 0;
		this.owner_id = 0;
		this.dunwu_times = 0;
		this.attrib = 0;
		this.gift = 0;
		this.nick = 0;
		this.power = 0;
		this.wrestlescore = 0;
		this.skill = 0;
		this.store_exp = 0;
		this.metal = 0;
		this.amount = 0;
		this.color = 0;
		this.suit_degree = 0;
		this.party_stage_party_name = 0;
		this.mailing_item_times = 0;
		this.suit_enabled = 0;
		this.degree_32 = 0;
		this.master = 0;
		this.transform_cool_ti = 0;
		this.silver_coin = 0;
		this.diandqk_frozen_round = 0;
		this.shuadao_ziqihongmeng = 0;
		this.durability = 0;
		this.add_pet_exp = 0;
		this.food_num = 0;
		this.merge_rate = 0;
		this.fasion_type = 0;
		this.pet_upgraded = 0;
		this.couple = 0;
		this.shape = 0;
		this.pot = 0;
		this.resist_poison = 0;
		this.max_durability = 0;
		this.skill_level = 0;
		this.upgrade_degree = 0;
		this.transform_num = 0;
		this.strengthen_degree = 0;
		this.strengthen_level = 0;
		this.durability2 = 0;
		this.max_durability2 = 0;
		this.open_nimbus = 0;
	}

}