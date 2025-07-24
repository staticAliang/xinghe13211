package com.fengshen.server.data;

import java.util.HashMap;
import java.util.Map;

import com.fengshen.server.data.vo.Vo_16383_0;
import com.fengshen.server.data.vo.Vo_20467_0;
import com.fengshen.server.data.vo.Vo_24505_0;
import com.fengshen.server.data.vo.Vo_4119_0;
import com.fengshen.server.data.vo.Vo_4121_0;
import com.fengshen.server.data.vo.Vo_45128_0;
import com.fengshen.server.data.vo.Vo_TITLE;
import com.fengshen.server.data.vo.Vo_65527_0;
import com.fengshen.server.data.vo.fight.Vo_ADD_FRIEND_OPPONENT;
import com.fengshen.server.data.vo.friend.Vo_FRIEND_ADD_CHAR;
import com.fengshen.server.data.vo.party.Vo_PARTY_DIALOG.Vo_PARTY_DIALOG_Item;
import com.fengshen.server.domain.Chara;
import com.fengshen.server.domain.ZbAttribute;

public class UtilObjMap {
	public static Map<Object, Object> Vo_16383_0(Object obj) {
		HashMap<Object, Object> objectObjectHashMap = new HashMap<Object, Object>();
		Vo_16383_0 obj2 = (Vo_16383_0) obj;
		objectObjectHashMap.put("channel", obj2.channel);
		objectObjectHashMap.put("id", obj2.id);
		objectObjectHashMap.put("name", obj2.name);
		objectObjectHashMap.put("msg", obj2.msg);
		objectObjectHashMap.put("time", obj2.time);
		objectObjectHashMap.put("privilege", obj2.privilege);
		objectObjectHashMap.put("server_name", obj2.server_name);
		objectObjectHashMap.put("show_extra", obj2.show_extra);
		objectObjectHashMap.put("compress", obj2.compress);
		objectObjectHashMap.put("orgLength", obj2.orgLength);
		objectObjectHashMap.put("cardCount", obj2.cardCount);
		objectObjectHashMap.put("voiceTime", obj2.voiceTime);
		objectObjectHashMap.put("token", obj2.token);
		objectObjectHashMap.put("checksum", obj2.checksum);
		objectObjectHashMap.put("gid", obj2.iid_str);
		objectObjectHashMap.put("not_check_bw", obj2.has_break_lv_limit);
		objectObjectHashMap.put("level", obj2.skill);
		objectObjectHashMap.put("icon", obj2.type);
		objectObjectHashMap.put("recv_gid", obj2.suit_level);
		if (obj2.npc_chat != null) {
			objectObjectHashMap.put("npc_chat", obj2.npc_chat);
		}
		if (obj2.chatFloor != null) {
			objectObjectHashMap.put("chat_floor", obj2.chatFloor);
		}
		if (obj2.chatHead != null) {
			objectObjectHashMap.put("chat_head", obj2.chatHead);
		}
		return objectObjectHashMap;
	}

	public static Map<Object, Object> Vo_20467_0(Object obj) {
		HashMap<Object, Object> objectObjectHashMap = new HashMap<Object, Object>();
		Vo_20467_0 obj2 = (Vo_20467_0) obj;
		objectObjectHashMap.put("caption", obj2.caption);
		objectObjectHashMap.put("content", obj2.content);
		objectObjectHashMap.put("peer_name", obj2.peer_name);
		objectObjectHashMap.put("ask_type", obj2.ask_type);
		objectObjectHashMap.put("org_icon", obj2.org_icon);
		objectObjectHashMap.put("iid_str", obj2.iid_str);
		objectObjectHashMap.put("skill", obj2.skill);
		objectObjectHashMap.put("str", obj2.str);
		objectObjectHashMap.put("master", obj2.master);
		objectObjectHashMap.put("metal", obj2.metal);
		objectObjectHashMap.put("req_str", obj2.req_str);
		objectObjectHashMap.put("passive_mode", obj2.passive_mode);
		objectObjectHashMap.put("party_contrib", obj2.party_contrib);
		objectObjectHashMap.put("teamMembersCount", obj2.teamMembersCount);
		objectObjectHashMap.put("comeback_flag", obj2.comeback_flag);
		objectObjectHashMap.put("friend", obj2.tao);
		return objectObjectHashMap;
	}

	public static Map<Object, Object> Vo_24505_0(Object obj) {
		HashMap<Object, Object> objectObjectHashMap = new HashMap<Object, Object>();
		Vo_24505_0 obj2 = (Vo_24505_0) obj;
		objectObjectHashMap.put("update_type", obj2.update_type);
		objectObjectHashMap.put("groupBuf", obj2.groupBuf);
		objectObjectHashMap.put("charBuf", obj2.charBuf);
		objectObjectHashMap.put("user_state", obj2.user_state);
		objectObjectHashMap.put("gid", obj2.gid);
		objectObjectHashMap.put("auto_reply", obj2.auto_reply);
		objectObjectHashMap.put("placed_amount", obj2.placed_amount);
		objectObjectHashMap.put("tao_effect", obj2.tao_effect);
		objectObjectHashMap.put("skill", obj2.skill);
		objectObjectHashMap.put("server_name", obj2.server_name);
		objectObjectHashMap.put("type", obj2.type);
		objectObjectHashMap.put("suit_icon", obj2.suit_icon);
		objectObjectHashMap.put("party_contrib", obj2.party_contrib);
		objectObjectHashMap.put("evolve_level", obj2.evolve_level);
		objectObjectHashMap.put("character_harmony", obj2.character_harmony);
		objectObjectHashMap.put("req_str", obj2.req_str);
		objectObjectHashMap.put("nice", obj2.nice);
		objectObjectHashMap.put("org_icon", obj2.org_icon);
		objectObjectHashMap.put("iid_str", obj2.iid_str);
		objectObjectHashMap.put("balance", obj2.balance);
		objectObjectHashMap.put("arena_rank", obj2.arena_rank);
		return objectObjectHashMap;
	}

	public static Map<Object, Object> Vo_4119_0(Object obj) {
		HashMap<Object, Object> objectObjectHashMap = new HashMap<Object, Object>();
		Vo_4119_0 obj2 = (Vo_4119_0) obj;
		objectObjectHashMap.put("id", obj2.id);
		objectObjectHashMap.put("gid", obj2.gid);
		objectObjectHashMap.put("suit_icon", obj2.suit_icon);
		objectObjectHashMap.put("weapon_icon", obj2.weapon_icon);
		objectObjectHashMap.put("org_icon", obj2.org_icon);
		objectObjectHashMap.put("skill", obj2.skill);
		objectObjectHashMap.put("str", obj2.str);
		objectObjectHashMap.put("master", obj2.master);
		objectObjectHashMap.put("metal", obj2.metal);
		objectObjectHashMap.put("passive_mode", obj2.passive_mode);
		objectObjectHashMap.put("req_str", obj2.req_str);
		objectObjectHashMap.put("party_contrib", obj2.party_contrib);
		objectObjectHashMap.put("upgrade_level", obj2.upgrade_level);
		objectObjectHashMap.put("membercard_name", obj2.membercard_name);
		objectObjectHashMap.put("memberlight_effect_count", obj2.memberlight_effect_count);
		return objectObjectHashMap;
	}

	public static Map<Object, Object> Vo_4121_0(Object obj) {
		HashMap<Object, Object> objectObjectHashMap = new HashMap<Object, Object>();
		Vo_4121_0 obj2 = (Vo_4121_0) obj;
		objectObjectHashMap.put("id", obj2.id);
		objectObjectHashMap.put("gid", obj2.gid);
		objectObjectHashMap.put("suit_icon", obj2.suit_icon);
		objectObjectHashMap.put("weapon_icon", obj2.weapon_icon);
		objectObjectHashMap.put("org_icon", obj2.org_icon);
		objectObjectHashMap.put("skill", obj2.skill);
		objectObjectHashMap.put("str", obj2.str);
		objectObjectHashMap.put("master", obj2.master);
		objectObjectHashMap.put("metal", obj2.metal);
		objectObjectHashMap.put("passive_mode", obj2.passive_mode);
		objectObjectHashMap.put("req_str", obj2.req_str);
		objectObjectHashMap.put("durability", obj2.durability);
		objectObjectHashMap.put("party_contrib", obj2.party_contrib);
		objectObjectHashMap.put("upgrade_level", obj2.upgrade_level);
		objectObjectHashMap.put("memberpos_x", obj2.memberpos_x);
		objectObjectHashMap.put("memberpos_y", obj2.memberpos_y);
		objectObjectHashMap.put("membermap_id", obj2.membermap_id);
		objectObjectHashMap.put("memberteam_status", obj2.memberteam_status);
		objectObjectHashMap.put("membercard_name", obj2.membercard_name);
		objectObjectHashMap.put("membercomeback_flag", obj2.membercomeback_flag);
		objectObjectHashMap.put("memberlight_effect_count", obj2.memberlight_effect_count);
		return objectObjectHashMap;
	}

	public static Map<Object, Object> Vo_45128_0(Object obj) {
		HashMap<Object, Object> objectObjectHashMap = new HashMap<Object, Object>();
		Vo_45128_0 obj2 = (Vo_45128_0) obj;
		objectObjectHashMap.put("no", obj2.no);
		objectObjectHashMap.put("type1", obj2.type1);
		objectObjectHashMap.put("phy_power", obj2.phy_power);
		objectObjectHashMap.put("speed", obj2.speed);
		objectObjectHashMap.put("life", obj2.life);
		objectObjectHashMap.put("mag_power", obj2.mag_power);
		objectObjectHashMap.put("wood", obj2.wood);
		objectObjectHashMap.put("water", obj2.water);
		objectObjectHashMap.put("fire", obj2.fire);
		objectObjectHashMap.put("earth", obj2.earth);
		objectObjectHashMap.put("resist_metal", obj2.resist_metal);
		objectObjectHashMap.put("color", obj2.color);
		objectObjectHashMap.put("mana", obj2.mana);
		objectObjectHashMap.put("accurate", obj2.accurate);
		objectObjectHashMap.put("parry", obj2.parry);
		objectObjectHashMap.put("def", obj2.def);
		objectObjectHashMap.put("max_life", obj2.max_life);
		objectObjectHashMap.put("wiz", obj2.wiz);
		objectObjectHashMap.put("str", obj2.str);
		objectObjectHashMap.put("skill", obj2.skill);
		objectObjectHashMap.put("suit_polar", obj2.suit_polar);
		objectObjectHashMap.put("type", obj2.type);
		objectObjectHashMap.put("metal", obj2.metal);
		objectObjectHashMap.put("penetrate", obj2.penetrate);
		objectObjectHashMap.put("max_degree", obj2.max_degree);
		objectObjectHashMap.put("salary", obj2.salary);
		objectObjectHashMap.put("shape", obj2.shape);
		objectObjectHashMap.put("store_exp", obj2.store_exp);
		objectObjectHashMap.put("exp", obj2.exp);
		return objectObjectHashMap;
	}

	public static Map<Object, Object> Vo_61545_0(Object obj) {
		HashMap<Object, Object> objectObjectHashMap = new HashMap<Object, Object>();
		Vo_FRIEND_ADD_CHAR obj2 = (Vo_FRIEND_ADD_CHAR) obj;
		objectObjectHashMap.put("groupBuf", obj2.groupBuf);
		objectObjectHashMap.put("charBuf", obj2.charBuf);
		objectObjectHashMap.put("blocked", obj2.blocked);
		objectObjectHashMap.put("online", obj2.online);
		objectObjectHashMap.put("server_name1", obj2.server_name1);
		objectObjectHashMap.put("insider_level", obj2.insider_level);
		objectObjectHashMap.put("user_state", obj2.user_state);
		objectObjectHashMap.put("auto_reply", obj2.auto_reply);
		objectObjectHashMap.put("gid", obj2.gid);
		objectObjectHashMap.put("placed_amount", obj2.placed_amount);
		objectObjectHashMap.put("tao_effect", obj2.tao_effect);
		objectObjectHashMap.put("skill", obj2.skill);
		objectObjectHashMap.put("type", obj2.type);
		objectObjectHashMap.put("server_name", obj2.server_name);
		objectObjectHashMap.put("suit_icon", obj2.suit_icon);
		objectObjectHashMap.put("party_contrib", obj2.party_contrib);
		objectObjectHashMap.put("character_harmony", obj2.character_harmony);
		objectObjectHashMap.put("evolve_level", obj2.evolve_level);
		objectObjectHashMap.put("nice", obj2.nice);
		objectObjectHashMap.put("req_str", obj2.req_str);
		objectObjectHashMap.put("org_icon", obj2.org_icon);
		objectObjectHashMap.put("iid_str", obj2.iid_str);
		objectObjectHashMap.put("balance", obj2.balance);
		objectObjectHashMap.put("arena_rank", obj2.arena_rank);
		objectObjectHashMap.put("friend_21", obj2.arena_rank);
		return objectObjectHashMap;
	}

	public static Map<Object, Object> Vo_61545_1(Object obj) {
		HashMap<Object, Object> objectObjectHashMap = new HashMap<Object, Object>();
		Vo_FRIEND_ADD_CHAR obj2 = (Vo_FRIEND_ADD_CHAR) obj;
		objectObjectHashMap.put("charBuf", obj2.charBuf);
		objectObjectHashMap.put("blocked", obj2.blocked);
		objectObjectHashMap.put("online", obj2.online);
		objectObjectHashMap.put("server_name1", obj2.server_name1);
		objectObjectHashMap.put("insider_level", obj2.insider_level);
		objectObjectHashMap.put("user_state", obj2.user_state);
		objectObjectHashMap.put("auto_reply", obj2.auto_reply);
		objectObjectHashMap.put("gid", obj2.gid);
		objectObjectHashMap.put("placed_amount", obj2.placed_amount);
		objectObjectHashMap.put("tao_effect", obj2.tao_effect);
		objectObjectHashMap.put("skill", obj2.skill);
		objectObjectHashMap.put("type", obj2.type);
		objectObjectHashMap.put("server_name", obj2.server_name);
		objectObjectHashMap.put("suit_icon", obj2.suit_icon);
		objectObjectHashMap.put("party_contrib", obj2.party_contrib);
		objectObjectHashMap.put("character_harmony", obj2.character_harmony);
		objectObjectHashMap.put("evolve_level", obj2.evolve_level);
		objectObjectHashMap.put("nice", obj2.nice);
		objectObjectHashMap.put("req_str", obj2.req_str);
		objectObjectHashMap.put("org_icon", obj2.org_icon);
		objectObjectHashMap.put("iid_str", obj2.iid_str);
		objectObjectHashMap.put("balance", obj2.balance);
		objectObjectHashMap.put("arena_rank", obj2.arena_rank);
		return objectObjectHashMap;
	}

	public static Map<Object, Object> Vo_61671_0(Object obj) {
		HashMap<Object, Object> objectObjectHashMap = new HashMap<Object, Object>();
		Vo_TITLE obj2 = (Vo_TITLE) obj;
		objectObjectHashMap.put("id", obj2.id);
		objectObjectHashMap.put("count", obj2.count);
		objectObjectHashMap.put("list", obj2.list);
		return objectObjectHashMap;
	}

	public static Map<Object, Object> Vo_65017_0(Object obj) {
		HashMap<Object, Object> objectObjectHashMap = new HashMap<Object, Object>();
		Vo_ADD_FRIEND_OPPONENT obj2 = (Vo_ADD_FRIEND_OPPONENT) obj;
		objectObjectHashMap.put("id", obj2.id);
		objectObjectHashMap.put("leader", obj2.leader);
		objectObjectHashMap.put("weapon_icon", obj2.weapon_icon);
		objectObjectHashMap.put("pos", obj2.pos);
		objectObjectHashMap.put("rank", obj2.rank);
		objectObjectHashMap.put("vip_type", obj2.vip_type);
		objectObjectHashMap.put("str", obj2.str);
		objectObjectHashMap.put("type", obj2.type);
		objectObjectHashMap.put("durability", obj2.durability);
		objectObjectHashMap.put("req_level", obj2.req_level);
		objectObjectHashMap.put("upgrade/level2", obj2.upgrade_level);
		objectObjectHashMap.put("upgrade/type2", obj2.upgrade_type);
		objectObjectHashMap.put("dex", obj2.dex);
		objectObjectHashMap.put("max_mana", obj2.max_mana);
		objectObjectHashMap.put("max_life", obj2.max_life);
		objectObjectHashMap.put("def", obj2.def);
		objectObjectHashMap.put("org_icon", obj2.org_icon);
		objectObjectHashMap.put("suit_icon", obj2.suit_icon);
		objectObjectHashMap.put("suit_light_effect", obj2.suit_light_effect);
		objectObjectHashMap.put("special_icon", obj2.special_icon);
		objectObjectHashMap.put("zhenling/type", obj2.zhenlingType);
		objectObjectHashMap.put("zhenling/level", obj2.zhenlingLevel);
		
		return objectObjectHashMap;
	}

	public static Map<Object, Object> Vo_65019_0(Object obj) {
		HashMap<Object, Object> objectObjectHashMap = new HashMap<Object, Object>();
		Vo_ADD_FRIEND_OPPONENT obj2 = (Vo_ADD_FRIEND_OPPONENT) obj;
		objectObjectHashMap.put("id", obj2.id);
		objectObjectHashMap.put("leader", obj2.leader);
		objectObjectHashMap.put("weapon_icon", obj2.weapon_icon);
		objectObjectHashMap.put("pos", obj2.pos);
		objectObjectHashMap.put("rank", obj2.rank);
		objectObjectHashMap.put("vip_type", obj2.vip_type);
		objectObjectHashMap.put("str", obj2.str);
		objectObjectHashMap.put("type", obj2.type);
		objectObjectHashMap.put("durability", obj2.durability);
		objectObjectHashMap.put("req_level", obj2.req_level);
		objectObjectHashMap.put("upgrade/level2", obj2.upgrade_level);
		objectObjectHashMap.put("upgrade/type2", obj2.upgrade_type);
		objectObjectHashMap.put("dex", obj2.dex);
		objectObjectHashMap.put("max_mana", obj2.max_mana);
		objectObjectHashMap.put("max_life", obj2.max_life);
		objectObjectHashMap.put("def", obj2.def);
		objectObjectHashMap.put("org_icon", obj2.org_icon);
		objectObjectHashMap.put("suit_icon", obj2.suit_icon);
		objectObjectHashMap.put("suit_light_effect", obj2.suit_light_effect);
		objectObjectHashMap.put("special_icon", obj2.special_icon);
		objectObjectHashMap.put("zhenling/type", obj2.zhenlingType);
		objectObjectHashMap.put("zhenling/level", obj2.zhenlingLevel);
		return objectObjectHashMap;
	}

	public static Map<Object, Object> Vo_65527_0( Object obj) {

		HashMap<Object, Object> objectObjectHashMap = new HashMap<Object, Object>();
		Vo_65527_0 obj2 = (Vo_65527_0) obj;
		objectObjectHashMap.put("str", obj2.str);
		objectObjectHashMap.put("phy_power", obj2.phy_power);
		objectObjectHashMap.put("accurate", obj2.accurate);
		objectObjectHashMap.put("life", obj2.life);
		//life(当前生命值)
		objectObjectHashMap.put("max_life", obj2.max_life);
		//max-life(最大生命值)
		objectObjectHashMap.put("def", obj2.def);
		objectObjectHashMap.put("wiz", obj2.wiz);
		objectObjectHashMap.put("mag_power", obj2.mag_power);
		objectObjectHashMap.put("mana", obj2.mana);
		objectObjectHashMap.put("max_mana", obj2.max_mana);
		objectObjectHashMap.put("dex", obj2.dex);
		objectObjectHashMap.put("speed", obj2.speed);
		objectObjectHashMap.put("parry", obj2.parry);
		objectObjectHashMap.put("attrib_point", obj2.attrib_point);
		objectObjectHashMap.put("metal", obj2.metal);
		objectObjectHashMap.put("wood", obj2.wood);
		objectObjectHashMap.put("water", obj2.water);
		objectObjectHashMap.put("fire", obj2.fire);
		objectObjectHashMap.put("earth", obj2.earth);
		objectObjectHashMap.put("resist_metal", obj2.resist_metal);
		objectObjectHashMap.put("resist_wood", obj2.resist_wood);
		objectObjectHashMap.put("resist_water", obj2.resist_water);
		objectObjectHashMap.put("resist_fire", obj2.resist_fire);
		objectObjectHashMap.put("resist_earth", obj2.resist_earth);
		objectObjectHashMap.put("exp_to_next_level", obj2.exp_to_next_level);
		objectObjectHashMap.put("polar_point", obj2.polar_point);
		objectObjectHashMap.put("stamina", obj2.stamina);
		objectObjectHashMap.put("max_stamina", obj2.max_stamina);
		objectObjectHashMap.put("tao", obj2.tao);
		objectObjectHashMap.put("friend", obj2.friend);
		objectObjectHashMap.put("owner_name", obj2.owner_name);
		//月道行
//		objectObjectHashMap.put("mon_tao_ex", obj2.mon_tao_ex);
//		objectObjectHashMap.put("last_mon_tao", obj2.last_mon_tao);
		
		objectObjectHashMap.put("last_mon_tao_ex", obj2.last_mon_tao_ex);
		objectObjectHashMap.put("mon_martial", obj2.mon_martial);
		objectObjectHashMap.put("degree", obj2.degree);
		objectObjectHashMap.put("exp", obj2.exp);
		objectObjectHashMap.put("pot", obj2.pot);
		objectObjectHashMap.put("cash", obj2.cash);
		objectObjectHashMap.put("balance", obj2.balance);
		objectObjectHashMap.put("gender", obj2.gender);
		objectObjectHashMap.put("max_balance", obj2.max_balance);
		objectObjectHashMap.put("ignore_resist_metal", obj2.ignore_resist_metal);
		objectObjectHashMap.put("master", obj2.master);
		objectObjectHashMap.put("level", obj2.level);
		objectObjectHashMap.put("skill", obj2.skill);
		objectObjectHashMap.put("status_daofa_wubian", obj2.status_daofa_wubian);
		objectObjectHashMap.put("nick", obj2.nick);
		objectObjectHashMap.put("family_title", obj2.partyJob);
		objectObjectHashMap.put("title", obj2.title);
		objectObjectHashMap.put("reputation", obj2.reputation);
		objectObjectHashMap.put("couple", obj2.couple);
		objectObjectHashMap.put("icon", obj2.icon);
		objectObjectHashMap.put("type", obj2.type);
		objectObjectHashMap.put("resist_poison", obj2.resist_poison);
		objectObjectHashMap.put("item_unique", obj2.item_unique);
		objectObjectHashMap.put("passive_mode", obj2.passive_mode);
		objectObjectHashMap.put("req_str", obj2.req_str);
		objectObjectHashMap.put("locked", obj2.locked);
		objectObjectHashMap.put("extra_desc", obj2.extra_desc);
		objectObjectHashMap.put("silver_coin", obj2.silverCoin);
		objectObjectHashMap.put("gold_coin", obj2.silverCoin);
		objectObjectHashMap.put("extra_life", obj2.extra_life);
		objectObjectHashMap.put("extra_mana", obj2.extra_mana);
		objectObjectHashMap.put("have_coin_pwd", obj2.have_coin_pwd);
		objectObjectHashMap.put("max_req_level", obj2.max_req_level);
		objectObjectHashMap.put("use_skill_d", obj2.use_skill_d);
		objectObjectHashMap.put("double_points", obj2.double_points);
		objectObjectHashMap.put("enable_double_points", obj2.enable_double_points);
		objectObjectHashMap.put("can_buy_dp_times", obj2.can_buy_dp_times);
		objectObjectHashMap.put("enable_shenmu_points", obj2.enable_shenmu_points);
		objectObjectHashMap.put("gift_key", obj2.gift_key);
		objectObjectHashMap.put("online", obj2.online);
		objectObjectHashMap.put("voucher", obj2.voucher);
		objectObjectHashMap.put("party_name", obj2.party_name);
		objectObjectHashMap.put("use_money_type", obj2.use_money_type);
		//金钱使用
		objectObjectHashMap.put("lock_exp", obj2.use_money_type);
		objectObjectHashMap.put("equip_identify", obj2.equip_identify);
		objectObjectHashMap.put("recharge", obj2.recharge);
		objectObjectHashMap.put("shadow_self", obj2.shadow_self);
		objectObjectHashMap.put("extra_life_effect", obj2.extra_life_effect);
		objectObjectHashMap.put("desc", obj2.desc);
		objectObjectHashMap.put("enchant", obj2.enchant);
		objectObjectHashMap.put("higest_feixdx", obj2.higest_feixdx);
		//如意刷道令点数
		objectObjectHashMap.put("fetch_nice", obj2.fetch_nice);
		//刷道宠风散点数
		objectObjectHashMap.put("shuadaochongfeng_san", obj2.shuadaochongfeng_san);
		//紫气鸿蒙点数
		objectObjectHashMap.put("extra_skill", obj2.extra_skill);
		//如意刷道令点数
		objectObjectHashMap.put("chushi_ex", obj2.chushi_ex);
		objectObjectHashMap.put("settingrefuse_stranger_level", obj2.settingrefuse_stranger_level);
		objectObjectHashMap.put("settingauto_reply_msg", obj2.settingauto_reply_msg);
		objectObjectHashMap.put("setting_refuse_be_add_level", obj2.setting_refuse_be_add_level);
		objectObjectHashMap.put("mount_attrib_end_time", obj2.mount_attrib_end_time);
		objectObjectHashMap.put("bully_kill_num", obj2.bully_kill_num);
		objectObjectHashMap.put("police_kill_num", obj2.police_kill_num);
		objectObjectHashMap.put("gm_attribsmax_life", obj2.gm_attribsmax_life);
		objectObjectHashMap.put("gm_attribsmax_mana", obj2.gm_attribsmax_mana);
		objectObjectHashMap.put("gm_attribsphy_power", obj2.gm_attribsphy_power);
		objectObjectHashMap.put("gm_attribsmag_power", obj2.gm_attribsmag_power);
		objectObjectHashMap.put("gm_attribsdef", obj2.gm_attribsdef);
		objectObjectHashMap.put("gm_attribsspeed", obj2.gm_attribsspeed);
		objectObjectHashMap.put("shuadao_ruyi_point", obj2.shuadao_ruyi_point);
//		objectObjectHashMap.put("artifact_upgraded_enabled", obj2.artifact_upgraded_enabled);
		objectObjectHashMap.put("house_house_class", obj2.house_house_class);
		objectObjectHashMap.put("plant_level", obj2.plant_level);
		objectObjectHashMap.put("phy_power_without_intimacy", obj2.phy_power_without_intimacy);
		objectObjectHashMap.put("plant_exp", obj2.plant_exp);
		objectObjectHashMap.put("marriage_couple_gid", obj2.marriage_couple_gid);
		objectObjectHashMap.put("strengthen_jewelry_num", obj2.strengthen_jewelry_num);
		objectObjectHashMap.put("soul_state", obj2.soul_state);
		objectObjectHashMap.put("dan_data_today_exp", obj2.dan_data_today_exp);
		objectObjectHashMap.put("transform_num", obj2.transform_num);
		objectObjectHashMap.put("fasion_effect_disable", obj2.fasion_effect_disable);
		objectObjectHashMap.put("strengthen_level", obj2.strengthen_level);
		objectObjectHashMap.put("status_diliebo_flag", obj2.status_diliebo_flag);
		objectObjectHashMap.put("exp_ware_data_lock_time", obj2.exp_ware_data_lock_time);
		objectObjectHashMap.put("exp_ware_data_exp_ware", obj2.exp_ware_data_exp_ware);
		objectObjectHashMap.put("exp_ware_data_fetch_times", obj2.exp_ware_data_fetch_times);
		objectObjectHashMap.put("exp_ware_data_today_fetch_times", obj2.exp_ware_data_today_fetch_times);
		objectObjectHashMap.put("free_rename", obj2.free_rename);
		//锁定经验
		objectObjectHashMap.put("shuadaojiji_rulvling", obj2.lock_exp);
		//元婴修复
		objectObjectHashMap.put("upgrade/state2", obj2.upgrade_state);//340
		objectObjectHashMap.put("upgrade/type2", obj2.upgrade_type);//341
		objectObjectHashMap.put("upgrade/level2", obj2.upgrade_level);//342
		objectObjectHashMap.put("upgrade/exp2", obj2.upgrade_exp);//343
		objectObjectHashMap.put("upgrade/exp_to_next_level2", obj2.upgrade_exp_to_next_level);//344
		objectObjectHashMap.put("upgrade/max_polar_extra2", obj2.upgrade_max_polar_extra);//345
		objectObjectHashMap.put("upgrade_magic2", obj2.upgrade_magic);
		objectObjectHashMap.put("upgrade/total2", obj2.upgrade_total);
		objectObjectHashMap.put("upgrade_immortal2", obj2.upgradeImmortal);
		//魂魄
		objectObjectHashMap.put("shenhun_data/state", obj2.shenHunDataSate);
		objectObjectHashMap.put("shenhun_data/layer", obj2.shenHunDataLayer);
		objectObjectHashMap.put("shenhun_data/exp", obj2.shenHunDataExp);
		objectObjectHashMap.put("shenhun_data/exp_to_next_level", obj2.shenHunDataExpToNextLevel);
		//内丹
		objectObjectHashMap.put("dan_data/state2", obj2.dan_data_state);
		objectObjectHashMap.put("dan_data/stage2", obj2.dan_data_stage);
		objectObjectHashMap.put("dan_data/exp2", obj2.dan_data_exp);
		objectObjectHashMap.put("dan_data/exp_to_next_level2", obj2.dan_data_exp_to_next_level);
		objectObjectHashMap.put("dan_data/attrib_point2", obj2.dan_data_attrib_point);
		objectObjectHashMap.put("dan_data/polar_point2", obj2.dan_data_polar_point);
		
		objectObjectHashMap.put("jewelry_essence2", obj2.jewelry_essence);
		//装备切换
		objectObjectHashMap.put("max_req_level", obj2.equipPage);
		//帮派
		objectObjectHashMap.put("nick", obj2.party_contrib);
		objectObjectHashMap.put("party_name2", obj2.party_name);
		objectObjectHashMap.put("title2", obj2.chengwei);
		//结婚
		objectObjectHashMap.put("fasion_custom_disable", obj2.marriage_book_id);
		objectObjectHashMap.put("marriage_start_time", obj2.marriage_start_time);
		//伴侣id
		objectObjectHashMap.put("marriagemarry_id", obj2.marriagemarry_id);
		//附灵
		objectObjectHashMap.put("zhenling/type", obj2.zhenlingType);
		objectObjectHashMap.put("zhenling/level", obj2.zhenlingLevel);
		//擂台
		objectObjectHashMap.put("ct_data/top_rank", obj2.ct_data_top_rank);
		objectObjectHashMap.put("ct_data/score", obj2.ct_data_score);
		objectObjectHashMap.put("status_loyalty", 2);
		return objectObjectHashMap;
	}

	public static Map<Object, Object> userCardNew(Object obj, Chara chara) {
		Vo_65527_0 obj2 = (Vo_65527_0) obj;
		HashMap<Object, Object> objectObjectHashMap = new HashMap<Object, Object>();
		ZbAttribute zb = chara.getZbAttribute();
		objectObjectHashMap.put("name", obj2.str);
		objectObjectHashMap.put("gid", chara.uuid);
		objectObjectHashMap.put("phy_power", obj2.accurate + zb.accurate);
		objectObjectHashMap.put("max_life", obj2.def + zb.def);
		objectObjectHashMap.put("def", obj2.wiz + zb.wiz);
		objectObjectHashMap.put("mag_power", obj2.mana + zb.mana);
		objectObjectHashMap.put("max_mana", obj2.dex + zb.dex);
		objectObjectHashMap.put("speed", obj2.parry + zb.parry);
		objectObjectHashMap.put("polar", obj2.metal);
		objectObjectHashMap.put("tao", obj2.friend);
		objectObjectHashMap.put("tao_ex", obj2.owner_name);
		objectObjectHashMap.put("level", obj2.skill);
		objectObjectHashMap.put("icon", obj2.type);
		objectObjectHashMap.put("ct_data/score", obj2.ct_data_top_rank);
		objectObjectHashMap.put("upgrade/level", obj2.upgrade_level);
		objectObjectHashMap.put("upgrade/type", obj2.upgrade_type);
		objectObjectHashMap.put("weapon_icon", chara.weapon_icon);
		objectObjectHashMap.put("suit_icon", chara.suit_icon);
		// 内丹等级
		objectObjectHashMap.put("dan_data/state", chara.danDataState);
		objectObjectHashMap.put("dan_data/stage", chara.danDataStage);

		return objectObjectHashMap;
	}

	public static Map<Object, Object> MSG_FRIEND_UPDATE_LISTS(Object obj, Chara chara) {
		Vo_65527_0 obj2 = (Vo_65527_0) obj;
		HashMap<Object, Object> objectObjectHashMap = new HashMap<Object, Object>();
		objectObjectHashMap.put("name", obj2.str);
		objectObjectHashMap.put("gid", chara.uuid);
		objectObjectHashMap.put("level", chara.level);
		objectObjectHashMap.put("party_name", "");
		objectObjectHashMap.put("polar", obj2.metal);
		objectObjectHashMap.put("icon", obj2.type);
		objectObjectHashMap.put("group_name", "我的好友");
		objectObjectHashMap.put("group_id", "1");

		return objectObjectHashMap;
	}

	public static Map<Object, Object> friend(Object obj) {
		HashMap<Object, Object> objectObjectHashMap = new HashMap<Object, Object>();
		Vo_FRIEND_ADD_CHAR obj2 = (Vo_FRIEND_ADD_CHAR) obj;
		objectObjectHashMap.put("gid", obj2.gid);
		objectObjectHashMap.put("level", obj2.skill);
		objectObjectHashMap.put("party/name", obj2.party_contrib);
		objectObjectHashMap.put("icon", obj2.type);
		objectObjectHashMap.put("gid", obj2.iid_str);
		objectObjectHashMap.put("friend", obj2.arena_rank);
		return objectObjectHashMap;
	}

	public static Map<Object, Object> partyJoinList(Vo_PARTY_DIALOG_Item obj) {
		HashMap<Object, Object> objectObjectHashMap = new HashMap<Object, Object>();
		objectObjectHashMap.put("gid", obj.getGid());
		objectObjectHashMap.put("level", obj.getLevel());
		objectObjectHashMap.put("name", obj.getName());
		objectObjectHashMap.put("polar", obj.getPolar());
		objectObjectHashMap.put("tao", obj.getTao());
		objectObjectHashMap.put("gender", obj.getGender());
		return objectObjectHashMap;
	}

	public static Map<Object, Object> neidan(Object obj) {
		HashMap<Object, Object> objectObjectHashMap = new HashMap<Object, Object>();
		objectObjectHashMap.put("shenhun_data/state", 0);
		return objectObjectHashMap;
	}
}
