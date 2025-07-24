package com.fengshen.server.data;

import java.util.HashMap;
import java.util.Map;

import com.fengshen.server.domain.Chara;
import com.fengshen.server.domain.Duiyuan;
import com.fengshen.server.domain.EquipInformation;
import com.fengshen.server.domain.Goods;
import com.fengshen.server.domain.GoodsBasics;
import com.fengshen.server.domain.GoodsFenSe;
import com.fengshen.server.domain.GoodsGaiZao;
import com.fengshen.server.domain.GoodsGaiZaoGongMing;
import com.fengshen.server.domain.GoodsGaiZaoGongMingChengGong;
import com.fengshen.server.domain.GoodsHuangSe;
import com.fengshen.server.domain.GoodsInfo;
import com.fengshen.server.domain.GoodsLanSe;
import com.fengshen.server.domain.GoodsLvSe;
import com.fengshen.server.domain.GoodsLvSeGongMing;
import com.fengshen.server.domain.JiNeng;
import com.fengshen.server.domain.LieBiao;
import com.fengshen.server.domain.PetShuXing;
import com.fengshen.server.domain.Petbeibao;
import com.fengshen.server.domain.ShouHu;
import com.fengshen.server.domain.ShouHuShuXing;
import com.fengshen.server.domain.ZbAttribute;

public class UtilObjMapshuxing {
	public static Map<Object, Object> Chara(Object obj) {
		HashMap<Object, Object> objectObjectHashMap = new HashMap<Object, Object>();
		Chara obj2 = (Chara) obj;
		objectObjectHashMap.put("cangku", obj2.cangku);
		objectObjectHashMap.put("shizhuang", obj2.shizhuang);
		objectObjectHashMap.put("texiao", obj2.texiao);
		objectObjectHashMap.put("genchong", obj2.genchong);
		objectObjectHashMap.put("backpack", obj2.backpack);
		objectObjectHashMap.put("zbAttribute", obj2.zbAttribute);
		objectObjectHashMap.put("pets", obj2.pets);
		objectObjectHashMap.put("listshouhu", obj2.listshouhu);
		objectObjectHashMap.put("jiNengList", obj2.jiNengList);
		objectObjectHashMap.put("shenmiliwu", obj2.shenmiliwu);
		objectObjectHashMap.put("id", obj2.id);
		objectObjectHashMap.put("x", obj2.x);
		objectObjectHashMap.put("y", obj2.y);
		objectObjectHashMap.put("mapid", obj2.mapid);
		objectObjectHashMap.put("mapName", obj2.mapName);
		objectObjectHashMap.put("level", obj2.level);
		objectObjectHashMap.put("name", obj2.name);
		objectObjectHashMap.put("chenhao", obj2.chenhao);
		objectObjectHashMap.put("polar", obj2.polar);
		objectObjectHashMap.put("exp", obj2.exp);
		objectObjectHashMap.put("sex", obj2.sex);
		objectObjectHashMap.put("line", obj2.line);
		objectObjectHashMap.put("uuid", obj2.uuid);
		objectObjectHashMap.put("waiguan", obj2.waiguan);
		objectObjectHashMap.put("current_task", obj2.current_task);
		objectObjectHashMap.put("phy_power", obj2.phy_power);
		objectObjectHashMap.put("life", obj2.life);
		objectObjectHashMap.put("speed", obj2.speed);
		objectObjectHashMap.put("mag_power", obj2.mag_power);
		objectObjectHashMap.put("accurate", obj2.accurate);
		objectObjectHashMap.put("def", obj2.def);
		objectObjectHashMap.put("dex", obj2.dex);
		objectObjectHashMap.put("wiz", obj2.wiz);
		objectObjectHashMap.put("mana", obj2.mana);
		objectObjectHashMap.put("parry", obj2.parry);
		objectObjectHashMap.put("pot", obj2.exp);
		objectObjectHashMap.put("resist_poison", obj2.expToNextLevel);
		objectObjectHashMap.put("extra_mana", obj2.extra_mana);
		objectObjectHashMap.put("have_coin_pwd", obj2.have_coin_pwd);
		objectObjectHashMap.put("use_skill_d", obj2.use_skill_d);
		objectObjectHashMap.put("wood", obj2.metal);
		objectObjectHashMap.put("water", obj2.wood);
		objectObjectHashMap.put("fire", obj2.water);
		objectObjectHashMap.put("earth", obj2.fire);
		objectObjectHashMap.put("resist_metal", obj2.earth);
		objectObjectHashMap.put("polar_point", obj2.attribPoint);
		objectObjectHashMap.put("stamina", obj2.polarPoint);
		objectObjectHashMap.put("max_life", obj2.max_life);
		objectObjectHashMap.put("max_mana", obj2.max_mana);
		objectObjectHashMap.put("use_money_type", obj2.use_money_type);
		objectObjectHashMap.put("shadow_self", obj2.shadow_self);
		objectObjectHashMap.put("weapon_icon", obj2.weapon_icon);
		objectObjectHashMap.put("silverCoin", obj2.silverCoin);
		objectObjectHashMap.put("extra_life", obj2.goldCoin);
		objectObjectHashMap.put("balance", obj2.cash);
		objectObjectHashMap.put("jishou_coin", obj2.jishou_coin);
		objectObjectHashMap.put("lock_exp", obj2.lock_exp);
		objectObjectHashMap.put("chongwuchanzhanId", obj2.chongwuchanzhanId);
		objectObjectHashMap.put("cash", obj2.pot);
		objectObjectHashMap.put("uptime", obj2.uptime);
		objectObjectHashMap.put("updatetime", obj2.updatetime);
		objectObjectHashMap.put("online_time", obj2.online_time);
		objectObjectHashMap.put("signDays", obj2.signDays);
		objectObjectHashMap.put("isCanSgin", obj2.isCanSgin);
		objectObjectHashMap.put("gender", obj2.sex);
		objectObjectHashMap.put("canzhanshouhunumber", obj2.canzhanshouhunumber);
		objectObjectHashMap.put("zuoqiwaiguan", obj2.zuoqiwaiguan);
		objectObjectHashMap.put("zuoqiId", obj2.zuoqiId);
		objectObjectHashMap.put("yidongsudu", obj2.yidongsudu);
		objectObjectHashMap.put("zuowaiguan", obj2.zuowaiguan);
		objectObjectHashMap.put("special_icon", obj2.special_icon);
		objectObjectHashMap.put("genchong_icon", obj2.genchong_icon);
		objectObjectHashMap.put("vipType", obj2.vipType);
		objectObjectHashMap.put("isGet", obj2.isGet);
		objectObjectHashMap.put("vipTime", obj2.vipTime);
		objectObjectHashMap.put("vipTimeShengYu", obj2.vipTimeShengYu);
		objectObjectHashMap.put("suit_icon", obj2.suit_icon);
		objectObjectHashMap.put("suit_light_effect", obj2.suit_light_effect);
		objectObjectHashMap.put("wuxingBalance", obj2.wuxingBalance);
		objectObjectHashMap.put("enable_double_points", obj2.enable_double_points);
		objectObjectHashMap.put("enable_shenmu_points", obj2.enable_shenmu_points);
		objectObjectHashMap.put("extra_skill", obj2.ziqihongmeng);
		objectObjectHashMap.put("chushi_ex", obj2.chushi_ex);
		objectObjectHashMap.put("fetch_nice", obj2.fetch_nice);
		objectObjectHashMap.put("shuadaochongfeng_san", obj2.shuadaochongfeng_san);
		objectObjectHashMap.put("xinshoulibao", obj2.xinshoulibao);
		objectObjectHashMap.put("npcshuadao", obj2.shudao);
		objectObjectHashMap.put("shuadao", obj2.shuadao);
		objectObjectHashMap.put("chubao", obj2.chubao);
		objectObjectHashMap.put("npcchubao", obj2.npcchubao);
		objectObjectHashMap.put("baibangmang", obj2.baibangmang);
		objectObjectHashMap.put("shimencishu", obj2.shimencishu);
		objectObjectHashMap.put("fabaorenwu", obj2.fabaorenwu);
		objectObjectHashMap.put("xiuxingcishu", obj2.xiuxingcishu);
		objectObjectHashMap.put("xiuxingNpcname", obj2.xiuxingNpcname);
		objectObjectHashMap.put("autofight_select", obj2.autofight_select);
		objectObjectHashMap.put("autofight_skillaction", obj2.autofight_skillaction);
		objectObjectHashMap.put("autofight_skillno", obj2.autofight_skillno);
		objectObjectHashMap.put("friend", obj2.tao);
		objectObjectHashMap.put("owner_name", obj2.taoPoint);
		objectObjectHashMap.put("chenghao", obj2.chenghao);
		return objectObjectHashMap;
	}

	public static Map<Object, Object> Duiyuan(Object obj) {
		HashMap<Object, Object> objectObjectHashMap = new HashMap<Object, Object>();
		Duiyuan obj2 = (Duiyuan) obj;
		objectObjectHashMap.put("org_icon", obj2.org_icon);
		objectObjectHashMap.put("iid_str", obj2.iid_str);
		objectObjectHashMap.put("skill", obj2.skill);
		objectObjectHashMap.put("str", obj2.str);
		objectObjectHashMap.put("master", obj2.master);
		objectObjectHashMap.put("metal", obj2.metal);
		objectObjectHashMap.put("req_str", obj2.req_str);
		objectObjectHashMap.put("passive_mode", obj2.passive_mode);
		objectObjectHashMap.put("party_contrib", obj2.party_contrib);
		objectObjectHashMap.put("mapteamMembersCount", obj2.mapteamMembersCount);
		objectObjectHashMap.put("mapcomeback_flag", obj2.mapcomeback_flag);
		return objectObjectHashMap;
	}

	public static Map<Object, Object> EquipInformation(Object obj) {
		HashMap<Object, Object> objectObjectHashMap = new HashMap<Object, Object>();
		EquipInformation obj2 = (EquipInformation) obj;
		objectObjectHashMap.put("GroupNo", obj2.GroupNo);
		objectObjectHashMap.put("GroupType", obj2.GroupType);
		objectObjectHashMap.put("attrib", obj2.attrib);
		objectObjectHashMap.put("gift", obj2.gift);
		objectObjectHashMap.put("total_score", obj2.total_score);
		objectObjectHashMap.put("nick", obj2.nick);
		objectObjectHashMap.put("power", obj2.power);
		objectObjectHashMap.put("wrestle_score", obj2.wrestle_score);
		objectObjectHashMap.put("skill", obj2.skill);
		objectObjectHashMap.put("store_exp", obj2.store_exp);
		objectObjectHashMap.put("metal", obj2.metal);
		objectObjectHashMap.put("amount", obj2.amount);
		objectObjectHashMap.put("type", obj2.type);
		objectObjectHashMap.put("rebuild_level", obj2.rebuild_level);
		objectObjectHashMap.put("color", obj2.color);
		objectObjectHashMap.put("str", obj2.str);
		objectObjectHashMap.put("auto_fight", obj2.auto_fight);
		objectObjectHashMap.put("suit_degree", obj2.suit_degree);
		objectObjectHashMap.put("party_stage_party_name", obj2.party_stage_party_name);
		objectObjectHashMap.put("mailing_item_times", obj2.mailing_item_times);
		objectObjectHashMap.put("quality", obj2.quality);
		objectObjectHashMap.put("damage_sel_rate", obj2.damage_sel_rate);
		objectObjectHashMap.put("recognize_recognized", obj2.recognize_recognized);
		objectObjectHashMap.put("suit_enabled", obj2.suit_enabled);
		objectObjectHashMap.put("degree_32", obj2.degree_32);
		objectObjectHashMap.put("master", obj2.master);
		// 完美度
		objectObjectHashMap.put("dunwu_times", obj2.dunwu_times);
		return objectObjectHashMap;
	}

	public static Map<Object, Object> Goods(Object obj) {
		HashMap<Object, Object> objectObjectHashMap = new HashMap<Object, Object>();
		Goods obj2 = (Goods) obj;
		objectObjectHashMap.put("pos", obj2.pos);
		objectObjectHashMap.put("goodsInfo", obj2.goodsInfo);
		objectObjectHashMap.put("goodsBasics", obj2.goodsBasics);
		objectObjectHashMap.put("goodsLanSe", obj2.goodsLanSe);
		objectObjectHashMap.put("goodsFenSe", obj2.goodsFenSe);
		objectObjectHashMap.put("goodsHuangSe", obj2.goodsHuangSe);
		objectObjectHashMap.put("goodsLvSe", obj2.goodsLvSe);
		objectObjectHashMap.put("goodsGaiZao", obj2.goodsGaiZao);
		objectObjectHashMap.put("goodsGaiZaoGongMing", obj2.goodsGaiZaoGongMing);
		objectObjectHashMap.put("goodsGaiZaoGongMingChengGong", obj2.goodsGaiZaoGongMingChengGong);
		objectObjectHashMap.put("goodsLvSeGongMing", obj2.goodsLvSeGongMing);
		return objectObjectHashMap;
	}

	public static Map<Object, Object> GoodsBasics(Object obj) {
		HashMap<Object, Object> objectObjectHashMap = new HashMap<Object, Object>();
		GoodsBasics obj2 = (GoodsBasics) obj;
		objectObjectHashMap.put("groupNo", obj2.groupNo);
		objectObjectHashMap.put("groupType", obj2.groupType);
		objectObjectHashMap.put("mana", obj2.mana);
		objectObjectHashMap.put("accurate", obj2.accurate);
		objectObjectHashMap.put("def", obj2.def);
		objectObjectHashMap.put("dex", obj2.dex);
		objectObjectHashMap.put("wiz", obj2.wiz);
		objectObjectHashMap.put("parry", obj2.parry);
		objectObjectHashMap.put("max_life", obj2.max_life);
		objectObjectHashMap.put("max_mana", obj2.max_mana);
		return objectObjectHashMap;
	}

	public static Map<Object, Object> GoodsGaiZao(Object obj) {
		HashMap<Object, Object> objectObjectHashMap = new HashMap<Object, Object>();
		GoodsGaiZao obj2 = (GoodsGaiZao) obj;
		objectObjectHashMap.put("groupNo", obj2.groupNo);
		objectObjectHashMap.put("groupType", obj2.groupType);
		objectObjectHashMap.put("accurate", obj2.accurate);
		objectObjectHashMap.put("all_polar", obj2.all_polar);
		objectObjectHashMap.put("wiz", obj2.wiz);
		objectObjectHashMap.put("def", obj2.def);
		objectObjectHashMap.put("mana", obj2.mana);
		return objectObjectHashMap;
	}

	public static Map<Object, Object> GoodsGaiZaoGongMing(Object obj) {
		HashMap<Object, Object> objectObjectHashMap = new HashMap<Object, Object>();
		GoodsGaiZaoGongMing obj2 = (GoodsGaiZaoGongMing) obj;
		objectObjectHashMap.put("groupNo", obj2.groupNo);
		objectObjectHashMap.put("groupType", obj2.groupType);
		objectObjectHashMap.put("damage_sel", obj2.damage_sel);
		objectObjectHashMap.put("accurate", obj2.accurate);
		objectObjectHashMap.put("mana", obj2.mana);
		objectObjectHashMap.put("def", obj2.def);
		objectObjectHashMap.put("wiz", obj2.wiz);
		objectObjectHashMap.put("parry", obj2.parry);
		objectObjectHashMap.put("phy_power", obj2.phy_power);
		objectObjectHashMap.put("mag_power", obj2.mag_power);
		objectObjectHashMap.put("speed", obj2.speed);
		objectObjectHashMap.put("life", obj2.life);
		objectObjectHashMap.put("resist_frozen", obj2.resist_frozen);
		objectObjectHashMap.put("resist_sleep", obj2.resist_sleep);
		objectObjectHashMap.put("resist_forgotten", obj2.resist_forgotten);
		objectObjectHashMap.put("resist_confusion", obj2.resist_confusion);
		objectObjectHashMap.put("longevity", obj2.longevity);
		objectObjectHashMap.put("resist_wood", obj2.resist_wood);
		objectObjectHashMap.put("resist_water", obj2.resist_water);
		objectObjectHashMap.put("resist_fire", obj2.resist_fire);
		objectObjectHashMap.put("resist_earth", obj2.resist_earth);
		objectObjectHashMap.put("exp_to_next_level", obj2.exp_to_next_level);
		objectObjectHashMap.put("mstunt_rate", obj2.mstunt_rate);
		objectObjectHashMap.put("stunt_rate", obj2.stunt_rate);
		objectObjectHashMap.put("double_hit_rate", obj2.double_hit_rate);
		objectObjectHashMap.put("super_excluse_wood", obj2.super_excluse_wood);
		objectObjectHashMap.put("super_excluse_water", obj2.super_excluse_water);
		objectObjectHashMap.put("super_excluse_fire", obj2.super_excluse_fire);
		objectObjectHashMap.put("super_excluse_earth", obj2.super_excluse_earth);
		objectObjectHashMap.put("B_skill_low_cost", obj2.B_skill_low_cost);
		objectObjectHashMap.put("life_recover", obj2.life_recover);
		objectObjectHashMap.put("family", obj2.family);
		objectObjectHashMap.put("portrait", obj2.portrait);
		objectObjectHashMap.put("tao_ex", obj2.tao_ex);
		objectObjectHashMap.put("release_confusion", obj2.release_confusion);
		objectObjectHashMap.put("release_sleep", obj2.release_sleep);
		objectObjectHashMap.put("release_frozen", obj2.release_frozen);
		objectObjectHashMap.put("release_poison", obj2.release_poison);
		objectObjectHashMap.put("C_skill_low_cost", obj2.C_skill_low_cost);
		objectObjectHashMap.put("D_skill_low_cost", obj2.D_skill_low_cost);
		objectObjectHashMap.put("super_poison", obj2.super_poison);
		// 法术必杀
		objectObjectHashMap.put("mstunt_rate_225", obj2.mstunt_rate_225);

		return objectObjectHashMap;
	}

	public static Map<Object, Object> GoodsGaiZaoGongMingChengGong(Object obj) {
		HashMap<Object, Object> objectObjectHashMap = new HashMap<Object, Object>();
		GoodsGaiZaoGongMingChengGong obj2 = (GoodsGaiZaoGongMingChengGong) obj;
		objectObjectHashMap.put("groupNo", obj2.groupNo);
		objectObjectHashMap.put("groupType", obj2.groupType);
		objectObjectHashMap.put("color", obj2.color);
		objectObjectHashMap.put("damage_sel", obj2.damage_sel);
		objectObjectHashMap.put("accurate", obj2.accurate);
		objectObjectHashMap.put("mana", obj2.mana);
		objectObjectHashMap.put("def", obj2.def);
		objectObjectHashMap.put("wiz", obj2.wiz);
		objectObjectHashMap.put("parry", obj2.parry);
		objectObjectHashMap.put("phy_power", obj2.phy_power);
		objectObjectHashMap.put("mag_power", obj2.mag_power);
		objectObjectHashMap.put("speed", obj2.speed);
		objectObjectHashMap.put("life", obj2.life);
		objectObjectHashMap.put("resist_frozen", obj2.resist_frozen);
		objectObjectHashMap.put("resist_sleep", obj2.resist_sleep);
		objectObjectHashMap.put("resist_forgotten", obj2.resist_forgotten);
		objectObjectHashMap.put("resist_confusion", obj2.resist_confusion);
		objectObjectHashMap.put("longevity", obj2.longevity);
		objectObjectHashMap.put("resist_wood", obj2.resist_wood);
		objectObjectHashMap.put("resist_water", obj2.resist_water);
		objectObjectHashMap.put("resist_fire", obj2.resist_fire);
		objectObjectHashMap.put("resist_earth", obj2.resist_earth);
		objectObjectHashMap.put("exp_to_next_level", obj2.exp_to_next_level);
		objectObjectHashMap.put("mstunt_rate", obj2.mstunt_rate);
		objectObjectHashMap.put("stunt_rate", obj2.stunt_rate);
		objectObjectHashMap.put("double_hit_rate", obj2.double_hit_rate);
		objectObjectHashMap.put("super_excluse_wood", obj2.super_excluse_wood);
		objectObjectHashMap.put("super_excluse_water", obj2.super_excluse_water);
		objectObjectHashMap.put("super_excluse_fire", obj2.super_excluse_fire);
		objectObjectHashMap.put("super_excluse_earth", obj2.super_excluse_earth);
		objectObjectHashMap.put("B_skill_low_cost", obj2.B_skill_low_cost);
		objectObjectHashMap.put("life_recover", obj2.life_recover);
		objectObjectHashMap.put("family", obj2.family);
		objectObjectHashMap.put("portrait", obj2.portrait);
		objectObjectHashMap.put("tao_ex", obj2.tao_ex);
		objectObjectHashMap.put("release_confusion", obj2.release_confusion);
		objectObjectHashMap.put("release_sleep", obj2.release_sleep);
		objectObjectHashMap.put("release_frozen", obj2.release_frozen);
		objectObjectHashMap.put("release_poison", obj2.release_poison);
		objectObjectHashMap.put("C_skill_low_cost", obj2.C_skill_low_cost);
		objectObjectHashMap.put("D_skill_low_cost", obj2.D_skill_low_cost);
		objectObjectHashMap.put("super_poison", obj2.super_poison);
		// 法术必杀
		objectObjectHashMap.put("mstunt_rate_225", obj2.mstunt_rate_225);
		return objectObjectHashMap;
	}

	public static Map<Object, Object> GoodsFenSe(Object obj) {
		HashMap<Object, Object> objectObjectHashMap = new HashMap<Object, Object>();
		GoodsFenSe obj2 = (GoodsFenSe) obj;
		objectObjectHashMap.put("groupNo", obj2.groupNo);
		objectObjectHashMap.put("groupType", obj2.groupType);
		objectObjectHashMap.put("phy_power", obj2.phy_power);
		objectObjectHashMap.put("mag_power", obj2.mag_power);
		objectObjectHashMap.put("speed", obj2.speed);
		objectObjectHashMap.put("life", obj2.life);
		objectObjectHashMap.put("mstunt_rate", obj2.mstunt_rate);
		objectObjectHashMap.put("skill_low_cost", obj2.skill_low_cost);
		objectObjectHashMap.put("parry", obj2.parry);
		objectObjectHashMap.put("all_polar", obj2.all_polar);
		objectObjectHashMap.put("all_resist_polar", obj2.all_resist_polar);
		objectObjectHashMap.put("wood", obj2.wood);
		objectObjectHashMap.put("water", obj2.water);
		objectObjectHashMap.put("fire", obj2.fire);
		objectObjectHashMap.put("earth", obj2.earth);
		objectObjectHashMap.put("resist_metal", obj2.resist_metal);
		objectObjectHashMap.put("damage_sel", obj2.damage_sel);
		objectObjectHashMap.put("stunt_rate", obj2.stunt_rate);
		objectObjectHashMap.put("double_hit_rate", obj2.double_hit_rate);
		objectObjectHashMap.put("release_forgotten", obj2.release_forgotten);
		objectObjectHashMap.put("ignore_all_resist_except", obj2.ignore_all_resist_except);
		objectObjectHashMap.put("stunt", obj2.stunt);
		objectObjectHashMap.put("def", obj2.def);
		objectObjectHashMap.put("dex", obj2.dex);
		objectObjectHashMap.put("wiz", obj2.wiz);
		objectObjectHashMap.put("family", obj2.family);
		objectObjectHashMap.put("life_recover", obj2.life_recover);
		objectObjectHashMap.put("all_skill", obj2.all_skill);
		objectObjectHashMap.put("portrait", obj2.portrait);
		objectObjectHashMap.put("resist_frozen", obj2.resist_frozen);
		objectObjectHashMap.put("resist_sleep", obj2.resist_sleep);
		objectObjectHashMap.put("resist_forgotten", obj2.resist_forgotten);
		objectObjectHashMap.put("resist_confusion", obj2.resist_confusion);
		objectObjectHashMap.put("longevity", obj2.longevity);
		objectObjectHashMap.put("resist_wood", obj2.resist_wood);
		objectObjectHashMap.put("resist_water", obj2.resist_water);
		objectObjectHashMap.put("resist_fire", obj2.resist_fire);
		objectObjectHashMap.put("resist_earth", obj2.resist_earth);
		objectObjectHashMap.put("exp_to_next_level", obj2.exp_to_next_level);
		objectObjectHashMap.put("all_resist_except", obj2.all_resist_except);
		return objectObjectHashMap;
	}

	public static Map<Object, Object> GoodsHuangSe(Object obj) {
		HashMap<Object, Object> objectObjectHashMap = new HashMap<Object, Object>();
		GoodsHuangSe obj2 = (GoodsHuangSe) obj;
		objectObjectHashMap.put("groupNo", obj2.groupNo);
		objectObjectHashMap.put("groupType", obj2.groupType);
		objectObjectHashMap.put("phy_power", obj2.phy_power);
		objectObjectHashMap.put("mag_power", obj2.mag_power);
		objectObjectHashMap.put("speed", obj2.speed);
		objectObjectHashMap.put("life", obj2.life);
		objectObjectHashMap.put("mstunt_rate", obj2.mstunt_rate);
		objectObjectHashMap.put("skill_low_cost", obj2.skill_low_cost);
		objectObjectHashMap.put("all_polar", obj2.all_polar);
		objectObjectHashMap.put("all_resist_polar", obj2.all_resist_polar);
		objectObjectHashMap.put("wood", obj2.wood);
		objectObjectHashMap.put("water", obj2.water);
		objectObjectHashMap.put("fire", obj2.fire);
		objectObjectHashMap.put("earth", obj2.earth);
		objectObjectHashMap.put("resist_metal", obj2.resist_metal);
		objectObjectHashMap.put("damage_sel", obj2.damage_sel);
		objectObjectHashMap.put("stunt_rate", obj2.stunt_rate);
		objectObjectHashMap.put("double_hit_rate", obj2.double_hit_rate);
		objectObjectHashMap.put("release_forgotten", obj2.release_forgotten);
		objectObjectHashMap.put("ignore_all_resist_except", obj2.ignore_all_resist_except);
		objectObjectHashMap.put("stunt", obj2.stunt);
		objectObjectHashMap.put("def", obj2.def);
		objectObjectHashMap.put("dex", obj2.dex);
		objectObjectHashMap.put("wiz", obj2.wiz);
		objectObjectHashMap.put("family", obj2.family);
		objectObjectHashMap.put("life_recover", obj2.life_recover);
		objectObjectHashMap.put("all_skill", obj2.all_skill);
		objectObjectHashMap.put("portrait", obj2.portrait);
		objectObjectHashMap.put("resist_frozen", obj2.resist_frozen);
		objectObjectHashMap.put("resist_sleep", obj2.resist_sleep);
		objectObjectHashMap.put("resist_forgotten", obj2.resist_forgotten);
		objectObjectHashMap.put("resist_confusion", obj2.resist_confusion);
		objectObjectHashMap.put("longevity", obj2.longevity);
		objectObjectHashMap.put("resist_wood", obj2.resist_wood);
		objectObjectHashMap.put("resist_water", obj2.resist_water);
		objectObjectHashMap.put("resist_fire", obj2.resist_fire);
		objectObjectHashMap.put("resist_earth", obj2.resist_earth);
		objectObjectHashMap.put("exp_to_next_level", obj2.exp_to_next_level);
		objectObjectHashMap.put("all_resist_except", obj2.all_resist_except);
		objectObjectHashMap.put("parry", obj2.parry);

		return objectObjectHashMap;
	}

	public static Map<Object, Object> GoodsInfo(Object obj) {
		HashMap<Object, Object> objectObjectHashMap = new HashMap<Object, Object>();
		GoodsInfo obj2 = (GoodsInfo) obj;
		objectObjectHashMap.put("groupNo", obj2.groupNo);
		objectObjectHashMap.put("groupType", obj2.groupType);
		objectObjectHashMap.put("value", obj2.value);
		objectObjectHashMap.put("total_score", obj2.total_score);
		objectObjectHashMap.put("type", obj2.type);
		objectObjectHashMap.put("rebuild_level", obj2.rebuild_level);
		objectObjectHashMap.put("str", obj2.str);
		objectObjectHashMap.put("auto_fight", obj2.auto_fight);
		objectObjectHashMap.put("quality", obj2.quality);
		objectObjectHashMap.put("damage_sel_rate", obj2.damage_sel_rate);
		objectObjectHashMap.put("recognize_recognized", obj2.recognize_recognized);
		objectObjectHashMap.put("owner_id", obj2.owner_id);
		objectObjectHashMap.put("attrib", obj2.attrib);
		objectObjectHashMap.put("gift", obj2.gift);
		objectObjectHashMap.put("nick", obj2.nick);
		objectObjectHashMap.put("power", obj2.power);
		objectObjectHashMap.put("wrestlescore", obj2.wrestlescore);
		objectObjectHashMap.put("skill", obj2.skill);
		objectObjectHashMap.put("store_exp", obj2.store_exp);
		objectObjectHashMap.put("metal", obj2.metal);
		objectObjectHashMap.put("amount", obj2.amount);
		objectObjectHashMap.put("color", obj2.color);
		objectObjectHashMap.put("suit_degree", obj2.suit_degree);
		objectObjectHashMap.put("party_stage_party_name", obj2.party_stage_party_name);
		objectObjectHashMap.put("mailing_item_times", obj2.mailing_item_times);
		objectObjectHashMap.put("suit_enabled", obj2.suit_enabled);
		objectObjectHashMap.put("degree_32", obj2.degree_32);
		objectObjectHashMap.put("master", obj2.master);
		objectObjectHashMap.put("silver_coin", obj2.silver_coin);
		objectObjectHashMap.put("diandqk_frozen_round", obj2.diandqk_frozen_round);
		objectObjectHashMap.put("shuadao_ziqihongmeng", obj2.shuadao_ziqihongmeng);
		objectObjectHashMap.put("durability", obj2.durability);
		objectObjectHashMap.put("add_pet_exp", obj2.add_pet_exp);
		objectObjectHashMap.put("alias", obj2.alias);
		objectObjectHashMap.put("food_num", obj2.food_num);
		objectObjectHashMap.put("merge_rate", obj2.merge_rate);
		objectObjectHashMap.put("fasion_type", obj2.fasion_type);
		objectObjectHashMap.put("pet_upgraded", obj2.pet_upgraded);
		objectObjectHashMap.put("couple", obj2.couple);
		objectObjectHashMap.put("shape", obj2.shape);
		objectObjectHashMap.put("pot", obj2.pot);
		objectObjectHashMap.put("resist_poison", obj2.resist_poison);
		objectObjectHashMap.put("phy_rebuild_level", obj2.phy_rebuild_level);
//		objectObjectHashMap.put("max_durability", 5);
//		objectObjectHashMap.put("polar", 10);
		// 完美度
		objectObjectHashMap.put("dunwu_times", obj2.dunwu_times);
		// 魂器
		objectObjectHashMap.put("skill_level2", obj2.skill_level);
		objectObjectHashMap.put("upgrade_degree2", obj2.upgrade_degree);
		// 首饰转换次数和冷却
		objectObjectHashMap.put("transform_num2", obj2.transform_num);
		// 首饰转换冷却时间
		objectObjectHashMap.put("transform_cool_ti2", obj2.transform_cool_ti);
		// 首饰强化等级
		objectObjectHashMap.put("strengthen_level2", obj2.strengthen_level);
		// 首饰强化进度
		objectObjectHashMap.put("strengthen_degree2", obj2.strengthen_degree);
		//耐久度
		objectObjectHashMap.put("max_durability2", obj2.max_durability2);
		objectObjectHashMap.put("durability2", obj2.durability2);
		//是否启灵 7.0
		objectObjectHashMap.put("open_nimbus", obj2.open_nimbus);
		return objectObjectHashMap;
	}

	public static Map<Object, Object> GoodsLanSe(Object obj) {
		HashMap<Object, Object> objectObjectHashMap = new HashMap<Object, Object>();
		GoodsLanSe obj2 = (GoodsLanSe) obj;
		objectObjectHashMap.put("groupNo", obj2.groupNo);
		objectObjectHashMap.put("groupType", obj2.groupType);
		objectObjectHashMap.put("phy_power", obj2.phy_power);
		objectObjectHashMap.put("mag_power", obj2.mag_power);
		objectObjectHashMap.put("speed", obj2.speed);
		objectObjectHashMap.put("life", obj2.life);
		objectObjectHashMap.put("skill_low_cost", obj2.skill_low_cost);
		objectObjectHashMap.put("mstunt_rate", obj2.mstunt_rate);
		objectObjectHashMap.put("all_polar", obj2.all_polar);
		objectObjectHashMap.put("all_resist_polar", obj2.all_resist_polar);
		objectObjectHashMap.put("wood", obj2.wood);
		objectObjectHashMap.put("water", obj2.water);
		objectObjectHashMap.put("fire", obj2.fire);
		objectObjectHashMap.put("earth", obj2.earth);
		objectObjectHashMap.put("resist_metal", obj2.resist_metal);
		objectObjectHashMap.put("damage_sel", obj2.damage_sel);
		objectObjectHashMap.put("stunt_rate", obj2.stunt_rate);
		objectObjectHashMap.put("double_hit_rate", obj2.double_hit_rate);
		objectObjectHashMap.put("release_forgotten", obj2.release_forgotten);
		objectObjectHashMap.put("ignore_all_resist_except", obj2.ignore_all_resist_except);
		objectObjectHashMap.put("stunt", obj2.stunt);
		objectObjectHashMap.put("def", obj2.def);
		objectObjectHashMap.put("dex", obj2.dex);
		objectObjectHashMap.put("wiz", obj2.wiz);
		objectObjectHashMap.put("family", obj2.family);
		objectObjectHashMap.put("life_recover", obj2.life_recover);
		objectObjectHashMap.put("all_skill", obj2.all_skill);
		objectObjectHashMap.put("portrait", obj2.portrait);
		objectObjectHashMap.put("resist_frozen", obj2.resist_frozen);
		objectObjectHashMap.put("resist_sleep", obj2.resist_sleep);
		objectObjectHashMap.put("resist_forgotten", obj2.resist_forgotten);
		objectObjectHashMap.put("resist_confusion", obj2.resist_confusion);
		objectObjectHashMap.put("longevity", obj2.longevity);
		objectObjectHashMap.put("resist_wood", obj2.resist_wood);
		objectObjectHashMap.put("resist_water", obj2.resist_water);
		objectObjectHashMap.put("resist_fire", obj2.resist_fire);
		objectObjectHashMap.put("resist_earth", obj2.resist_earth);
		objectObjectHashMap.put("exp_to_next_level", obj2.exp_to_next_level);
		objectObjectHashMap.put("all_resist_except", obj2.all_resist_except);
		objectObjectHashMap.put("accurate", obj2.accurate);
		objectObjectHashMap.put("mana", obj2.mana);
		objectObjectHashMap.put("parry", obj2.parry);
		objectObjectHashMap.put("ignore_resist_wood", obj2.ignore_resist_wood);
		objectObjectHashMap.put("ignore_resist_water", obj2.ignore_resist_water);
		objectObjectHashMap.put("ignore_resist_fire", obj2.ignore_resist_fire);
		objectObjectHashMap.put("ignore_resist_earth", obj2.ignore_resist_earth);
		objectObjectHashMap.put("ignore_resist_forgotten", obj2.ignore_resist_forgotten);
		objectObjectHashMap.put("ignore_resist_frozen", obj2.ignore_resist_frozen);
		objectObjectHashMap.put("ignore_resist_sleep", obj2.ignore_resist_sleep);
		objectObjectHashMap.put("ignore_resist_confusion", obj2.ignore_resist_confusion);
		objectObjectHashMap.put("super_excluse_metal", obj2.super_excluse_metal);
		objectObjectHashMap.put("ignore_resist_poison", obj2.ignore_resist_poison);
		return objectObjectHashMap;
	}

	public static Map<Object, Object> GoodsLvSe(Object obj) {
		HashMap<Object, Object> objectObjectHashMap = new HashMap<Object, Object>();
		GoodsLvSe obj2 = (GoodsLvSe) obj;
		objectObjectHashMap.put("groupNo", obj2.groupNo);
		objectObjectHashMap.put("groupType", obj2.groupType);
		objectObjectHashMap.put("accurate", obj2.accurate);
		objectObjectHashMap.put("resist_frozen", obj2.resist_frozen);
		objectObjectHashMap.put("resist_sleep", obj2.resist_sleep);
		objectObjectHashMap.put("resist_forgotten", obj2.resist_forgotten);
		objectObjectHashMap.put("resist_confusion", obj2.resist_confusion);
		objectObjectHashMap.put("longevity", obj2.longevity);
		objectObjectHashMap.put("super_excluse_wood", obj2.super_excluse_wood);
		objectObjectHashMap.put("super_excluse_water", obj2.super_excluse_water);
		objectObjectHashMap.put("super_excluse_fire", obj2.super_excluse_fire);
		objectObjectHashMap.put("super_excluse_earth", obj2.super_excluse_earth);
		objectObjectHashMap.put("B_skill_low_cost", obj2.B_skill_low_cost);
		objectObjectHashMap.put("enhanced_wood", obj2.enhanced_wood);
		objectObjectHashMap.put("enhanced_water", obj2.enhanced_water);
		objectObjectHashMap.put("enhanced_fire", obj2.enhanced_fire);
		objectObjectHashMap.put("enhanced_earth", obj2.enhanced_earth);
		objectObjectHashMap.put("mag_dodge", obj2.mag_dodge);
		objectObjectHashMap.put("ignore_mag_dodge", obj2.ignore_mag_dodge);
		objectObjectHashMap.put("jinguang_zhaxian_counter_att_rate", obj2.jinguang_zhaxian_counter_att_rate);
		objectObjectHashMap.put("C_skill_low_cost", obj2.C_skill_low_cost);
		objectObjectHashMap.put("D_skill_low_cost", obj2.D_skill_low_cost);
		objectObjectHashMap.put("super_poison", obj2.super_poison);
		objectObjectHashMap.put("ignore_resist_wood", obj2.ignore_resist_wood);
		objectObjectHashMap.put("ignore_resist_water", obj2.ignore_resist_water);
		objectObjectHashMap.put("ignore_resist_fire", obj2.ignore_resist_fire);
		objectObjectHashMap.put("ignore_resist_earth", obj2.ignore_resist_earth);
		objectObjectHashMap.put("ignore_resist_forgotten", obj2.ignore_resist_forgotten);
		objectObjectHashMap.put("release_forgotten", obj2.release_forgotten);
		objectObjectHashMap.put("ignore_all_resist_except", obj2.ignore_all_resist_except);
		objectObjectHashMap.put("super_confusion", obj2.super_confusion);
		objectObjectHashMap.put("super_sleep", obj2.super_sleep);
		objectObjectHashMap.put("enhanced_metal", obj2.enhanced_metal);
		objectObjectHashMap.put("super_forgotten", obj2.super_forgotten);
		objectObjectHashMap.put("super_frozen", obj2.super_frozen);
		objectObjectHashMap.put("ignore_resist_frozen", obj2.ignore_resist_frozen);
		objectObjectHashMap.put("ignore_resist_sleep", obj2.ignore_resist_sleep);
		objectObjectHashMap.put("ignore_resist_confusion", obj2.ignore_resist_confusion);
		objectObjectHashMap.put("super_excluse_metal", obj2.super_excluse_metal);
		objectObjectHashMap.put("ignore_resist_poison", obj2.ignore_resist_poison);
		objectObjectHashMap.put("tao_ex", obj2.tao_ex);
		objectObjectHashMap.put("release_confusion", obj2.release_confusion);
		objectObjectHashMap.put("release_sleep", obj2.release_sleep);
		objectObjectHashMap.put("release_frozen", obj2.release_frozen);
		objectObjectHashMap.put("release_poison", obj2.release_poison);
		// 忽视躲避攻击
		objectObjectHashMap.put("ignore_mag_dodge2", obj2.ignore_mag_dodge2);
		return objectObjectHashMap;
	}

	public static Map<Object, Object> GoodsLvSeGongMing(Object obj) {
		HashMap<Object, Object> objectObjectHashMap = new HashMap<Object, Object>();
		GoodsLvSeGongMing obj2 = (GoodsLvSeGongMing) obj;
		objectObjectHashMap.put("groupNo", obj2.groupNo);
		objectObjectHashMap.put("groupType", obj2.groupType);
		objectObjectHashMap.put("mana", obj2.mana);
		objectObjectHashMap.put("def", obj2.def);
		objectObjectHashMap.put("wiz", obj2.wiz);
		objectObjectHashMap.put("parry", obj2.parry);
		objectObjectHashMap.put("accurate", obj2.accurate);
		return objectObjectHashMap;
	}

	public static Map<Object, Object> JiNeng(Object obj) {
		HashMap<Object, Object> objectObjectHashMap = new HashMap<Object, Object>();
		JiNeng obj2 = (JiNeng) obj;
		objectObjectHashMap.put("id", obj2.id);
		objectObjectHashMap.put("skill_no", obj2.skill_no);
		objectObjectHashMap.put("skill_attrib", obj2.skill_attrib);
		objectObjectHashMap.put("skill_attrib1", obj2.skill_attrib1);
		objectObjectHashMap.put("skill_level", obj2.skill_level);
		objectObjectHashMap.put("level_improved", obj2.level_improved);
		objectObjectHashMap.put("skill_mana_cost", obj2.skill_mana_cost);
		objectObjectHashMap.put("skill_nimbus", obj2.skill_nimbus);
		objectObjectHashMap.put("skill_disabled", obj2.skill_disabled);
		objectObjectHashMap.put("range", obj2.range);
		objectObjectHashMap.put("max_range", obj2.max_range);
		objectObjectHashMap.put("count1", obj2.count1);
		objectObjectHashMap.put("s1", obj2.s1);
		objectObjectHashMap.put("s2", obj2.s2);
		objectObjectHashMap.put("isTempSkill", obj2.isTempSkill);
		objectObjectHashMap.put("skillRound", obj2.skillRound);
		return objectObjectHashMap;
	}

	public static Map<Object, Object> LieBiao(Object obj) {
		HashMap<Object, Object> objectObjectHashMap = new HashMap<Object, Object>();
		LieBiao obj2 = (LieBiao) obj;
		objectObjectHashMap.put("ask_type", obj2.ask_type);
		objectObjectHashMap.put("peer_name", obj2.peer_name);
		objectObjectHashMap.put("duiyuanList", obj2.duiyuanList);
		return objectObjectHashMap;
	}

	public static Map<Object, Object> Petbeibao(Object obj) {
		HashMap<Object, Object> objectObjectHashMap = new HashMap<Object, Object>();
		Petbeibao obj2 = (Petbeibao) obj;
		objectObjectHashMap.put("no", obj2.no);
		objectObjectHashMap.put("id", obj2.id);
		objectObjectHashMap.put("petShuXing", obj2.petShuXing);
		objectObjectHashMap.put("tianshu", obj2.tianshu);
		objectObjectHashMap.put("autofight_select", obj2.autofight_select);
		objectObjectHashMap.put("autofight_skillaction", obj2.autofight_skillaction);
		objectObjectHashMap.put("autofight_skillno", obj2.autofight_skillno);
		return objectObjectHashMap;
	}

	public static Map<Object, Object> PetShuXing(Object obj, String... owner_name) {
		HashMap<Object, Object> objectObjectHashMap = new HashMap<Object, Object>();
		PetShuXing obj2 = (PetShuXing) obj;
		objectObjectHashMap.put("no", obj2.no);
		objectObjectHashMap.put("type1", obj2.type1);
		objectObjectHashMap.put("str", obj2.str);
		objectObjectHashMap.put("max_life", obj2.max_life);
		objectObjectHashMap.put("def", obj2.def);
		objectObjectHashMap.put("max_mana", obj2.max_mana);
		objectObjectHashMap.put("dex", obj2.dex);
		objectObjectHashMap.put("skill", obj2.skill);
		objectObjectHashMap.put("penetrate", obj2.penetrate);
		objectObjectHashMap.put("polar_point", obj2.polar_point);
		objectObjectHashMap.put("metal", obj2.metal);
		objectObjectHashMap.put("stamina", obj2.stamina);
		objectObjectHashMap.put("type", obj2.type);
		objectObjectHashMap.put("durability", obj2.durability);
		objectObjectHashMap.put("phy_power", obj2.phy_power);
		objectObjectHashMap.put("mag_power", obj2.mag_power);
		objectObjectHashMap.put("life", obj2.life);
		objectObjectHashMap.put("speed", obj2.speed);
		objectObjectHashMap.put("accurate", obj2.accurate);
		objectObjectHashMap.put("mana", obj2.mana);
		objectObjectHashMap.put("parry", obj2.parry);
		objectObjectHashMap.put("wiz", obj2.wiz);
		objectObjectHashMap.put("shape", obj2.shape);
		objectObjectHashMap.put("pot", obj2.pot);
		objectObjectHashMap.put("martial", obj2.martial);
		objectObjectHashMap.put("resist_point", obj2.resist_point);
		objectObjectHashMap.put("intimacy", obj2.intimacy);
		objectObjectHashMap.put("last_mon_martial", obj2.last_mon_martial);
		objectObjectHashMap.put("mon_tao_rank", obj2.mon_tao_rank);
		objectObjectHashMap.put("double_hit", obj2.double_hit);
		objectObjectHashMap.put("resist_poison", obj2.resist_poison);
		objectObjectHashMap.put("passive_mode", obj2.passive_mode);
		objectObjectHashMap.put("pet_mana_shape", obj2.pet_mana_shape);
		objectObjectHashMap.put("pet_speed_shape", obj2.pet_speed_shape);
		objectObjectHashMap.put("pet_phy_shape", obj2.pet_phy_shape);
		objectObjectHashMap.put("pet_mag_shape", obj2.pet_mag_shape);
		objectObjectHashMap.put("rank", obj2.rank);
		objectObjectHashMap.put("pet_upgraded", obj2.pet_upgraded);
		objectObjectHashMap.put("party_stage_party_name", obj2.party_stage_party_name);
		objectObjectHashMap.put("left_time_to_delete", obj2.left_time_to_delete);
		objectObjectHashMap.put("extra_mana", obj2.extra_mana);
		objectObjectHashMap.put("have_coin_pwd", obj2.have_coin_pwd);
		objectObjectHashMap.put("mag_rebuild_level", obj2.mag_rebuild_level);
		objectObjectHashMap.put("raw_name", obj2.raw_name);
		objectObjectHashMap.put("suit_light_effect", obj2.suit_light_effect);
		objectObjectHashMap.put("mag_rebuild_rate", obj2.mag_rebuild_rate);
		objectObjectHashMap.put("life_add_temp", obj2.life_add_temp);
		objectObjectHashMap.put("mag_rebuild_add", obj2.mag_rebuild_add);
		objectObjectHashMap.put("pet_life_shape_temp", obj2.pet_life_shape_temp);
		objectObjectHashMap.put("pet_mana_shape_temp", obj2.pet_mana_shape_temp);
		objectObjectHashMap.put("pet_speed_shape_temp", obj2.pet_speed_shape_temp);
		objectObjectHashMap.put("pet_phy_shape_temp", obj2.pet_phy_shape_temp);
		objectObjectHashMap.put("pet_mag_shape_temp", obj2.pet_mag_shape_temp);
		objectObjectHashMap.put("evolve_degree", obj2.evolve_degree);
		objectObjectHashMap.put("mana_add_temp", obj2.mana_add_temp);
		objectObjectHashMap.put("phy_power_add_temp", obj2.phy_power_add_temp);
		objectObjectHashMap.put("def_add_temp", obj2.def_add_temp);
		objectObjectHashMap.put("mag_power_add_temp", obj2.mag_power_add_temp);
		objectObjectHashMap.put("speed_add_temp", obj2.speed_add_temp);
		objectObjectHashMap.put("shuadaojiji_rulvling", obj2.shuadaojiji_rulvling);
		objectObjectHashMap.put("recognize_recognized", obj2.recognize_recognized);
		objectObjectHashMap.put("attrib", obj2.attrib);
		objectObjectHashMap.put("enchant_nimbus", obj2.enchant_nimbus);
		objectObjectHashMap.put("max_enchant_nimbus", obj2.max_enchant_nimbus);
		objectObjectHashMap.put("card_type", obj2.card_type);
		objectObjectHashMap.put("eclosion_nimbus", obj2.eclosion_nimbus);
		objectObjectHashMap.put("max_eclosion_nimbus", obj2.max_eclosion_nimbus);
		objectObjectHashMap.put("status_all_resist_except_add", obj2.status_all_resist_except_add);
		objectObjectHashMap.put("status_yanchuan_shenjiao", obj2.status_yanchuan_shenjiao);
		objectObjectHashMap.put("insider_level", obj2.insider_level);
		objectObjectHashMap.put("mana_effect", obj2.mana_effect);
		objectObjectHashMap.put("attack_effect", obj2.attack_effect);
		objectObjectHashMap.put("phy_effect", obj2.phy_effect);
		objectObjectHashMap.put("mag_effect", obj2.mag_effect);
		objectObjectHashMap.put("phy_absorb", obj2.phy_absorb);
		objectObjectHashMap.put("extra_mana_effect", obj2.extra_mana_effect);
		objectObjectHashMap.put("extra_mag_effect", obj2.extra_mag_effect);
		objectObjectHashMap.put("extra_phy_effect", obj2.extra_phy_effect);
		// 幻化此位置偏移导致错位 开始，
		// 气血
		objectObjectHashMap.put("morph_mana_times", obj2.morph_life_times);// morph_life_times
		objectObjectHashMap.put("morph_mana_stat", obj2.morph_life_stat);// morph_life_stat
		// 法力
		objectObjectHashMap.put("morph_speed_times", obj2.morph_mana_times);// morph_mana_times
		objectObjectHashMap.put("morph_speed_stat", obj2.morph_mana_stat);// morph_mana_stat
		// 速度
		objectObjectHashMap.put("morph_phy_times", obj2.morph_speed_times);// morph_speed_times
		objectObjectHashMap.put("morph_phy_stat", obj2.morph_speed_stat);// morph_speed_stat
		// 物攻
		objectObjectHashMap.put("morph_mag_times", obj2.morph_phy_times);// morph_phy_times
		objectObjectHashMap.put("morph_mag_stat", obj2.morph_phy_stat);// morph_phy_stat
		// 法攻
		objectObjectHashMap.put("morph_life_stat", obj2.morph_mag_times);// morph_mag_times
		objectObjectHashMap.put("free_unlock_exp_times", obj2.morph_mag_stat);// morph_mag_stat
		// 幻化此位置偏移导致错位 结束
//		objectObjectHashMap.put("free_unlock_exp_times", obj2.free_unlock_exp_times);
		objectObjectHashMap.put("mount_attribmove_speed", obj2.mount_attribmove_speed);
		objectObjectHashMap.put("capacity_level", obj2.capacity_level);
		objectObjectHashMap.put("hide_mount", obj2.hide_mount);
		objectObjectHashMap.put("equip_perfect_percent", obj2.equip_perfect_percent);
		objectObjectHashMap.put("deadline", obj2.deadline);
		objectObjectHashMap.put("merge_rate", obj2.merge_rate);
		objectObjectHashMap.put("dunwu_rate", obj2.dunwu_rate);
		objectObjectHashMap.put("pet_anger", obj2.pet_anger);
		objectObjectHashMap.put("status_huanbing_zhiji", obj2.status_huanbing_zhiji);
		objectObjectHashMap.put("gm_attribsmax_life", obj2.gm_attribsmax_life);
		objectObjectHashMap.put("gm_attribsmax_mana", obj2.gm_attribsmax_mana);
		objectObjectHashMap.put("gm_attribsphy_power", obj2.gm_attribsphy_power);
		objectObjectHashMap.put("gm_attribsmag_power", obj2.gm_attribsmag_power);
		objectObjectHashMap.put("gm_attribsdef", obj2.gm_attribsdef);
		objectObjectHashMap.put("gm_attribsspeed", obj2.gm_attribsspeed);
		objectObjectHashMap.put("limit_use_time", obj2.limit_use_time);
		objectObjectHashMap.put("mag_power_without_intimacy", obj2.mag_power_without_intimacy);
		objectObjectHashMap.put("def_without_intimacy", obj2.def_without_intimacy);
		objectObjectHashMap.put("origin_intimacy", obj2.origin_intimacy);
		// 修复亲密问题
		objectObjectHashMap.put("douchong_rank", obj2.shape);
		objectObjectHashMap.put("auto_fight", obj2.auto_fight);
		objectObjectHashMap.put("suit_polar", obj2.suit_polar);
		objectObjectHashMap.put("all_polar", obj2.all_polar);
		objectObjectHashMap.put("upgrade_magic", obj2.upgrade_magic);
		objectObjectHashMap.put("upgrade_total", obj2.upgrade_total);
		objectObjectHashMap.put("silver_coin", obj2.silver_coin);
		if (owner_name != null && owner_name.length > 0) {
			objectObjectHashMap.put("owner_name_176", owner_name[0]);
		}
		// 彩凤
		objectObjectHashMap.put("soul_state2", obj2.zhuruCaifeng);
		// 宠物时装
		// 953
		objectObjectHashMap.put("fasion_id2", obj2.fasion_id);
		objectObjectHashMap.put("fasion_visible2", obj2.fasion_visible);
		objectObjectHashMap.put("dye_icon2", obj2.dye_icon);
		// 附灵
		objectObjectHashMap.put("zhenling/type", obj2.zhenlingType);
		objectObjectHashMap.put("zhenling/level", obj2.zhenlingType);
		// 修复宠物抗性，这里key是错位的,value是正确对位的
		objectObjectHashMap.put("resist_wood", obj2.resist_metal); // 抗金
		objectObjectHashMap.put("resist_water", obj2.resist_wood); // 木
		objectObjectHashMap.put("resist_fire", obj2.resist_water);// 水
		objectObjectHashMap.put("resist_earth", obj2.resist_fire);// 火
		objectObjectHashMap.put("exp_to_next_level", obj2.resist_earth);// 土
		// 修复宠物抗障碍
		objectObjectHashMap.put("resist_confusion", obj2.resist_forgotten);// 遗忘
		objectObjectHashMap.put("resist_frozen", obj2.resist_poison2); // 中毒
		objectObjectHashMap.put("resist_sleep", obj2.resist_frozen);// 冰冻
		objectObjectHashMap.put("resist_forgotten", obj2.resist_sleep);// 昏睡
		objectObjectHashMap.put("longevity", obj2.resist_confusion);// 混乱
		// 抗性点
		objectObjectHashMap.put("loyalty", obj2.resist_point2);// 抗性点
		return objectObjectHashMap;
	}

	public static Map<Object, Object> ShouHu(Object obj) {
		HashMap<Object, Object> objectObjectHashMap = new HashMap<Object, Object>();
		ShouHu obj2 = (ShouHu) obj;
		objectObjectHashMap.put("id", obj2.id);
		objectObjectHashMap.put("listShouHuShuXing", obj2.listShouHuShuXing);
		return objectObjectHashMap;
	}

	public static Map<Object, Object> ShouHuShuXing(Object obj) {
		HashMap<Object, Object> objectObjectHashMap = new HashMap<Object, Object>();
		ShouHuShuXing obj2 = (ShouHuShuXing) obj;
		objectObjectHashMap.put("no", obj2.no);
		objectObjectHashMap.put("type1", obj2.type1);
		objectObjectHashMap.put("str", obj2.str);
		objectObjectHashMap.put("wood", obj2.wood);
		objectObjectHashMap.put("water", obj2.water);
		objectObjectHashMap.put("fire", obj2.fire);
		objectObjectHashMap.put("earth", obj2.earth);
		objectObjectHashMap.put("resist_metal", obj2.resist_metal);
		objectObjectHashMap.put("skill", obj2.skill);
		objectObjectHashMap.put("type", obj2.type);
		objectObjectHashMap.put("shape", obj2.shape);
		objectObjectHashMap.put("nil", obj2.nil);
		objectObjectHashMap.put("penetrate", obj2.penetrate);
		objectObjectHashMap.put("metal", obj2.metal);
		objectObjectHashMap.put("max_degree", obj2.max_degree);
		objectObjectHashMap.put("color", obj2.color);
		objectObjectHashMap.put("exp", obj2.exp);
		objectObjectHashMap.put("store_exp", obj2.store_exp);
		objectObjectHashMap.put("salary", obj2.salary);
		objectObjectHashMap.put("mag_power", obj2.mag_power);
		objectObjectHashMap.put("mana", obj2.mana);
		objectObjectHashMap.put("phy_power", obj2.phy_power);
		objectObjectHashMap.put("accurate", obj2.accurate);
		objectObjectHashMap.put("suit_polar", obj2.suit_polar);
		objectObjectHashMap.put("life", obj2.life);
		objectObjectHashMap.put("parry", obj2.parry);
		objectObjectHashMap.put("def", obj2.def);
		objectObjectHashMap.put("wiz", obj2.wiz);
		objectObjectHashMap.put("max_life", obj2.max_life);
		objectObjectHashMap.put("speed", obj2.speed);
		return objectObjectHashMap;
	}

	public static Map<Object, Object> guardArrtib(ShouHuShuXing obj2) {
		HashMap<Object, Object> objectObjectHashMap = new HashMap<Object, Object>();
//		objectObjectHashMap.put("no", obj2.no);
//		objectObjectHashMap.put("type1", obj2.type1);
		objectObjectHashMap.put("str", obj2.str);
		objectObjectHashMap.put("wood", obj2.wood);
		objectObjectHashMap.put("water", obj2.water);
		objectObjectHashMap.put("fire", obj2.fire);
		objectObjectHashMap.put("earth", obj2.earth);
		objectObjectHashMap.put("resist_metal", obj2.resist_metal);
		objectObjectHashMap.put("skill", obj2.skill);
		objectObjectHashMap.put("type", obj2.type);
		objectObjectHashMap.put("shape", obj2.shape);
		objectObjectHashMap.put("nil", obj2.nil);
		objectObjectHashMap.put("penetrate", obj2.penetrate);
		objectObjectHashMap.put("metal", obj2.metal);
		objectObjectHashMap.put("max_degree", obj2.max_degree);
		objectObjectHashMap.put("color", obj2.color);
		objectObjectHashMap.put("exp", obj2.exp);
		objectObjectHashMap.put("store_exp", obj2.store_exp);
		objectObjectHashMap.put("salary", obj2.salary);
		objectObjectHashMap.put("mag_power", obj2.mag_power);
		objectObjectHashMap.put("mana", obj2.mana);
		objectObjectHashMap.put("phy_power", obj2.phy_power);
		objectObjectHashMap.put("accurate", obj2.accurate);
		objectObjectHashMap.put("suit_polar", obj2.suit_polar);
		objectObjectHashMap.put("life", obj2.life);
		objectObjectHashMap.put("parry", obj2.parry);
		objectObjectHashMap.put("def", obj2.def);
		objectObjectHashMap.put("wiz", obj2.wiz);
		objectObjectHashMap.put("max_life", obj2.max_life);
		objectObjectHashMap.put("speed", obj2.speed);
		return objectObjectHashMap;
	}

	public static Map<Object, Object> ShuXingUtil(Object obj) {
		HashMap<Object, Object> objectObjectHashMap = new HashMap<Object, Object>();
//         ShuXingUtil obj2 = (ShuXingUtil)obj;
		return objectObjectHashMap;
	}

	public static Map<Object, Object> ZbAttribute(Object obj) {
		HashMap<Object, Object> objectObjectHashMap = new HashMap<Object, Object>();
		ZbAttribute obj2 = (ZbAttribute) obj;
		objectObjectHashMap.put("id", obj2.id);
		objectObjectHashMap.put("phy_power", obj2.phy_power);
		objectObjectHashMap.put("mag_power", obj2.mag_power);
		objectObjectHashMap.put("speed", obj2.speed);
		objectObjectHashMap.put("life", obj2.life);
		objectObjectHashMap.put("skill_low_cost", obj2.skill_low_cost);
		objectObjectHashMap.put("mstunt_rate", obj2.mstunt_rate);
		objectObjectHashMap.put("wood", obj2.wood);
		objectObjectHashMap.put("water", obj2.water);
		objectObjectHashMap.put("fire", obj2.fire);
		objectObjectHashMap.put("earth", obj2.earth);
		objectObjectHashMap.put("resist_metal", obj2.resist_metal);
		objectObjectHashMap.put("damage_sel", obj2.damage_sel);
		objectObjectHashMap.put("stunt_rate", obj2.stunt_rate);
		objectObjectHashMap.put("double_hit_rate", obj2.double_hit_rate);
		objectObjectHashMap.put("release_forgotten", obj2.release_forgotten);
		objectObjectHashMap.put("ignore_all_resist_except", obj2.ignore_all_resist_except);
		objectObjectHashMap.put("stunt", obj2.stunt);
		objectObjectHashMap.put("def", obj2.def);
		objectObjectHashMap.put("dex", obj2.dex);
		objectObjectHashMap.put("wiz", obj2.wiz);
		objectObjectHashMap.put("family", obj2.family);
		objectObjectHashMap.put("life_recover", obj2.life_recover);
		objectObjectHashMap.put("all_skill", obj2.all_skill);
		objectObjectHashMap.put("portrait", obj2.portrait);
		objectObjectHashMap.put("resist_frozen", obj2.resist_frozen);
		objectObjectHashMap.put("resist_sleep", obj2.resist_sleep);
		objectObjectHashMap.put("resist_forgotten", obj2.resist_forgotten);
		objectObjectHashMap.put("resist_confusion", obj2.resist_confusion);
		objectObjectHashMap.put("longevity", obj2.longevity);
		objectObjectHashMap.put("resist_wood", obj2.resist_wood);
		objectObjectHashMap.put("resist_water", obj2.resist_water);
		objectObjectHashMap.put("resist_fire", obj2.resist_fire);
		objectObjectHashMap.put("resist_earth", obj2.resist_earth);
		objectObjectHashMap.put("exp_to_next_level", obj2.exp_to_next_level);
		objectObjectHashMap.put("all_resist_except", obj2.all_resist_except);
		objectObjectHashMap.put("accurate", obj2.accurate);
		objectObjectHashMap.put("mana", obj2.mana);
		objectObjectHashMap.put("parry", obj2.parry);
		objectObjectHashMap.put("super_excluse_wood", obj2.super_excluse_wood);
		objectObjectHashMap.put("super_excluse_water", obj2.super_excluse_water);
		objectObjectHashMap.put("super_excluse_fire", obj2.super_excluse_fire);
		objectObjectHashMap.put("super_excluse_earth", obj2.super_excluse_earth);
		objectObjectHashMap.put("B_skill_low_cost", obj2.B_skill_low_cost);
		objectObjectHashMap.put("enhanced_wood", obj2.enhanced_wood);
		objectObjectHashMap.put("enhanced_water", obj2.enhanced_water);
		objectObjectHashMap.put("enhanced_fire", obj2.enhanced_fire);
		objectObjectHashMap.put("enhanced_earth", obj2.enhanced_earth);
		objectObjectHashMap.put("mag_dodge", obj2.mag_dodge);
		objectObjectHashMap.put("ignore_mag_dodge", obj2.ignore_mag_dodge);
		objectObjectHashMap.put("jinguang_zhaxian_counter_att_rate", obj2.jinguang_zhaxian_counter_att_rate);
		objectObjectHashMap.put("C_skill_low_cost", obj2.C_skill_low_cost);
		objectObjectHashMap.put("D_skill_low_cost", obj2.D_skill_low_cost);
		objectObjectHashMap.put("super_poison", obj2.super_poison);
		objectObjectHashMap.put("ignore_resist_wood", obj2.ignore_resist_wood);
		objectObjectHashMap.put("ignore_resist_water", obj2.ignore_resist_water);
		objectObjectHashMap.put("ignore_resist_fire", obj2.ignore_resist_fire);
		objectObjectHashMap.put("ignore_resist_earth", obj2.ignore_resist_earth);
		objectObjectHashMap.put("ignore_resist_forgotten", obj2.ignore_resist_forgotten);
		objectObjectHashMap.put("super_confusion", obj2.super_confusion);
		objectObjectHashMap.put("super_sleep", obj2.super_sleep);
		objectObjectHashMap.put("enhanced_metal", obj2.enhanced_metal);
		objectObjectHashMap.put("super_forgotten", obj2.super_forgotten);
		objectObjectHashMap.put("super_frozen", obj2.super_frozen);
		objectObjectHashMap.put("ignore_resist_frozen", obj2.ignore_resist_frozen);
		objectObjectHashMap.put("ignore_resist_sleep", obj2.ignore_resist_sleep);
		objectObjectHashMap.put("ignore_resist_confusion", obj2.ignore_resist_confusion);
		objectObjectHashMap.put("super_excluse_metal", obj2.super_excluse_metal);
		objectObjectHashMap.put("ignore_resist_poison", obj2.ignore_resist_poison);
		objectObjectHashMap.put("tao_ex", obj2.tao_ex);
		objectObjectHashMap.put("release_confusion", obj2.release_confusion);
		objectObjectHashMap.put("release_sleep", obj2.release_sleep);
		objectObjectHashMap.put("release_frozen", obj2.release_frozen);
		objectObjectHashMap.put("release_poison", obj2.release_poison);
		return objectObjectHashMap;
	}
}
