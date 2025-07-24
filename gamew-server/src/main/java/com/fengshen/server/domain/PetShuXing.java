package com.fengshen.server.domain;

import lombok.Data;
import java.util.Map;

@Data
public class PetShuXing {
	/**
	 * <=0 :没有飞升 has_upgraded
	 */
//    public int has_upgraded;
	/**
	 * 飞升 UPGRAD_TYPE.ordinal()
	 */
	public int upgrade_level = 0;
	public short currentFlyFightIndex = 0; // 当前飞升战斗第几阶段
	// 宠物最大的复活次数
	public int maxReviveTimes;

	// add tzhang 判断宠物神兽丹吃到了哪一个阶段
	public boolean use10w = false;
	public boolean use30w = false;
	public boolean use50w = false;
	public boolean use100w = false;
	public boolean use200w = false;
	public boolean use500w = false;

	public Map<String, Integer> xinFa;
	// add:e
	public int zhuruCaifeng; // 0表示没注入，1表示注入了
	public int no; // 这个版本将23设置为坐骑标志 // 宠物信息的no为0，妖石的no为12，13，14，15
	public int type1;
	public String str; // 宠物名称
	public int max_life;
	public int def; // 气血（宠物属性栏）
	public int max_mana;
	public int dex; // 法力（宠物属性栏）
	public int skill; // 宠物等级
	public int penetrate; // 宠物的类型：1野生，2宝宝，3变异，4神兽，5守护
	public int polar_point; // 属性点未分配点
	public int metal; // 金木水火土哪个相性
	public int stamina;
	public int type; // 宠物的外观编号
	public int durability;
	public int phy_power; // 力量
	public int mag_power; // 灵力
	public int life; // 体质
	public int speed; // 敏捷
	public int accurate; // 物伤（宠物属性栏）
	public int mana; // 法伤（宠物属性栏）
	public int parry; // 速度（宠物属性栏）
	public int wiz; // 防御（宠物属性栏）
	public int shape; // 宠物的亲密度
	public int pot; // 宠物当前的经验值
	//错位字段废弃->resist_point
	public int loyalty;
	//抗性点
	public int resist_point2;
	//抗金
	public int resist_metal;
	//木
	public int resist_wood;
	//水
	public int resist_water;
	//火
	public int resist_fire;
	//土
	public int resist_earth;
	
	//错位字段废弃->resist_earth
	public int exp_to_next_level;
	//抗冰冻
	public int resist_frozen;
	//抗睡眠
	public int resist_sleep;
	//抗遗忘
	public int resist_forgotten;
	//抗混乱
	public int resist_confusion;
	//抗中毒
	public int resist_poison2;
	//错位字段废弃->resist_confusion
	public int longevity;
	public int martial; // 宠物寿命
	public int resist_point; // 总成长(资质)
	public int intimacy; // 宠物武学
	public int last_mon_martial;
	public int mon_tao_rank;
	public int double_hit;
	public int resist_poison; // 宠物当前等级的最大经验值
	public int passive_mode;
	public int pet_mana_shape; // 气血成长（资质）
	public int pet_speed_shape; // 法力成长（资质）
	public int pet_phy_shape; // 速度成长（资质）
	public int pet_mag_shape; // 物攻成长（资质）
	public int rank; // 法功成长（资质）
	public int pet_upgraded;
	public int party_stage_party_name;
	public int left_time_to_delete;
	public int extra_mana;
	public int have_coin_pwd;
	public int mag_rebuild_level;
	public int raw_name;
	public int suit_light_effect;
	public int mag_rebuild_rate;
	public int life_add_temp;
	//强化物攻增量
	public int mag_rebuild_add;
	//强化法攻增量
	public int pet_life_shape_temp;
	public int pet_mana_shape_temp;
	public int pet_speed_shape_temp;
	public int pet_phy_shape_temp;
	public int pet_mag_shape_temp;
	public int evolve_degree;
	public int mana_add_temp;
	public int phy_power_add_temp;
	public int def_add_temp;
	public int mag_power_add_temp;
	public int speed_add_temp;
	public int shuadaojiji_rulvling;
	public int recognize_recognized;
	public int attrib;
	public int enchant_nimbus; // 点化是否完成，等于2表示已经点化完成,0表示没有完成
	public int max_enchant_nimbus; // 当前点化的灵气值
	public int card_type;
	public int eclosion_nimbus; // 羽化是否完成，1表示还在进行中
	public int max_eclosion_nimbus; // 当前羽化的灵气值
	public int status_all_resist_except_add;
	public int status_yanchuan_shenjiao;
	public String insider_level;
	//气血基础成长
	public int mana_effect;
	//法力基础成长
	public int attack_effect;
	//速度基础成长
	public int phy_effect;
	//物攻基础成长
	public int mag_effect;
	//法功基础成长
	public int phy_absorb;
	public int extra_mana_effect;
	public int extra_mag_effect;
	public int extra_phy_effect;
	public int extra_speed_effect;

	// 幻化的次数和阶段
	public int morph_life_times; // 气血
	public int morph_mana_times; // 法力
	public int morph_speed_times; // 速度
	public int morph_phy_times; // 物攻
	public int morph_mag_times; // 法功

	public int morph_life_stat;
	public int morph_mana_stat;
	public int morph_speed_stat;
	public int morph_phy_stat;
	public int morph_mag_stat;

	public int free_unlock_exp_times;
	public int mount_attribmove_speed;
	public int capacity_level; // 这里是阶数对应的移动速度1
	/**
	 * .判断开始 (介数 ＝ 2) 返回 (9) .判断 (介数 ＝ 3) 返回 (16) .判断 (介数 ＝ 4) 返回 (20) .判断 (介数 ＝ 5)
	 * 返回 (25) .判断 (介数 ＝ 6) 返回 (32) .判断 (介数 ＝ 7) ' 10=3 14=4 19=5 20=6 23=7 28=8
	 * 31=9 35=10 39=11 40=12 42=13 ‘提高移动速度包 返回 (35) .判断 (介数 ＝ 8) 返回 (40) .判断 (介数 ＝
	 * 9) 返回 (49) .判断 (介数 ＝ 10) 返回 (53) .判断 (介数 ＝ 11) 返回 (57) .判断 (介数 ＝ 12) 返回 (64)
	 * .判断 (介数 ＝ 13) 返回 (66)
	 */
	public int hide_mount; // 坐骑的阶数
	public int equip_perfect_percent;
	public int deadline;
	public int merge_rate;
	public int dunwu_rate;
	public int pet_anger;
	public int status_huanbing_zhiji;
	public int gm_attribsmax_life;
	public int gm_attribsmax_mana;
	public int gm_attribsphy_power;
	public int gm_attribsmag_power;
	public int gm_attribsdef;
	public int gm_attribsspeed;
	public int limit_use_time; // 0表示没飞升成功，1表示飞升成功
	public int mag_power_without_intimacy;
	public int def_without_intimacy;
	public int origin_intimacy;
	public int douchong_rank;
	public int fasion_id;
	public int fasion_visible;
	public String auto_fight;
	//宠物原始名字
	public String suit_polar;
	public int all_polar;
	public int upgrade_magic;
	public int upgrade_total;
	public int silver_coin;
	//妖石加成--伤害
	public int shanghai;
	//防御
	public int fangyu;
	//气血
	public int qixue;
	//速度
	public int sudu;
	//法力
	public int fali;
	//亲密伤害加成
	public int qinmiAccurate;
	public int qinmiMana;
	public int qinmiWiz;
	public int qinmiParry;
	//宠物变色
	public int dye_icon;
	//攻击范围
	public int skillRange;
	public int zhenlingType;
	//附灵等级
	public int zhenlingLevel;
	

	public PetShuXing() {
		this.no = 0;
		this.type1 = 1;
		this.str = "";
		this.status_yanchuan_shenjiao = 0;
		this.insider_level = "";
		this.auto_fight = "";
		this.suit_polar = "";
		this.all_polar = 0;
		this.upgrade_magic = 0;
		this.upgrade_total = 0;
	}
}