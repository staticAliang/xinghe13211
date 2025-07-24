package com.fengshen.server.data.game;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;

public class NoviceGiftBagUtils {
	public static List<String[]> giftBag(final int sex, final int metal) {
		final List<String[]> list = new LinkedList<>();
		list.add(giftBags(10, sex, metal));
        list.add(giftBags(20, sex, metal));
        list.add(giftBags(30, sex, metal));
        list.add(giftBags(40, sex, metal));
        list.add(giftBags(50, sex, metal));
        list.add(giftBags(60, sex, metal));
        list.add(giftBags(70, sex, metal));
        list.add(giftBags(80, sex, metal));
		return list;
	}

	public static String[] giftBags(final int attrib, final int sex, final int metal) {
        if (attrib == 10) {
            final String[] arms = { "铁枪", "虎爪", "青锋剑", "精铁扇", "流星锤" };
            final String[] nv = { "飞凤钗", "绛紫裙" };
            final String[] bag = { "虎爪#装备", "皮帽#装备", "布鞋#装备", "虎皮衣#装备", "驯兽诀#物品" };
            bag[0] = arms[metal - 1] + "#装备";
            if (sex == 2) {
                bag[1] = nv[0] + "#装备";
                bag[3] = nv[1] + "#装备";
            }
            return bag;
        }
        if (attrib == 20) {
            final String[] arms = { "点钢枪", "赤炼爪", "沉香剑", "逍遥扇", "八棱锤" };
            final String[] nv = { "碧玉钗", "虹羽衣" };
            final String[] bag = { "赤炼爪#装备", "青铜盔#装备", "马靴#装备", "青铜铠#装备", "纹龙佩#首饰", "青珑挂珠#首饰", "金刚手镯#首饰", "金刚手镯#首饰", "血玲珑#物品", "法玲珑#物品", "代金券#100000" };
            bag[0] = arms[metal - 1] + "#装备";
            if (sex == 2) {
                bag[1] = nv[0] + "#装备";
                bag[3] = nv[1] + "#装备";
            }
            return bag;
        }
        if (attrib == 30) {
            final String[] arms = { "乌金枪", "残青爪", "飞虹剑", "玉骨扇", "亮银锤" };
            final String[] nv = { "蝴蝶钗", "凤暖袍" };
            final String[] bag = { "残青爪#装备", "冲天盔#装备", "牛皮靴#装备", "皂罗袍#装备", "天神护佑#物品", "血池#物品", "灵池#物品", "火眼金睛#物品", "代金券#150000" };
            bag[0] = arms[metal - 1] + "#装备";
            if (sex == 2) {
                bag[1] = nv[0] + "#装备";
                bag[3] = nv[1] + "#装备";
            }
            return bag;
        }
        if (attrib == 40) {
            final String[] arms = { "火焰枪", "阴风爪", "乾元剑", "阴阳扇", "乌金锤" };
            final String[] nv = { "金钗", "锦月袄" };
            final String[] bag = { "阴风爪#装备", "虎头盔#装备", "长筒靴#装备", "金锁甲#装备", "天神护佑#物品", "代金券#200000" };
            bag[0] = arms[metal - 1] + "#装备";
            if (sex == 2) {
                bag[1] = nv[0] + "#装备";
                bag[3] = nv[1] + "#装备";
            }
            return bag;
        }
        if (attrib == 50) {
            final String[] arms = { "双头枪", "寒冰刺", "斩妖剑", "凤羽扇", "混元锤" };
            final String[] nv = { "珍珠钗", "凝霜衣" };
            final String[] bag = { "寒冰刺#装备", "神龙盔#装备", "追云履#装备", "莽龙袍#装备", "天神护佑#物品", "代金券#250000" };
            bag[0] = arms[metal - 1] + "#装备";
            if (sex == 2) {
                bag[1] = nv[0] + "#装备";
                bag[3] = nv[1] + "#装备";
            }
            return bag;
        }
        if (attrib == 60) {
            final String[] arms = { "寒风枪", "骷髅爪", "昆吾剑", "百花扇", "霹雳锤" };
            final String[] nv = { "凤尾钗", "水合袍" };
            final String[] bag = { "骷髅爪#装备", "白玉冠#装备", "亮银靴#装备", "金丝甲#装备", "天神护佑#物品", "代金券#300000" };
            bag[0] = arms[metal - 1] + "#装备";
            if (sex == 2) {
                bag[1] = nv[0] + "#装备";
                bag[3] = nv[1] + "#装备";
            }
            return bag;
        }
        if (attrib == 70) {
            final String[] nv2 = { "鱼尾冠", "狐皮袄" };
            final String[] bag2 = { "乾坤冠#装备", "疾风履#装备", "八卦衣#装备", "天神护佑#物品", "代金券#400000" };
            if (sex == 2) {
                bag2[0] = nv2[0] + "#装备";
                bag2[2] = nv2[1] + "#装备";
            }
            return bag2;
        }
        if (attrib == 80) {
            final String[] bag3 = { "超级绿水晶#物品", "超级女娲石#物品", "血池#物品", "灵池#物品", "代金券#600000" };
            return bag3;
        }
        if (attrib == 90) {
            final String[] bag3 = { "九转金刚刃#装备", "天星奇光#首饰", "血池#物品", "太极熊#坐骑", "代金券#600000" };
            return bag3;
        }
        if (attrib == 100) {
            final String[] bag3 = { "超级绿水晶#物品", "超级女娲石#物品", "血池#物品", "灵池#物品", "代金券#600000" };
            return bag3;
        }
        if (attrib == 110) {
            final String[] bag3 = { "超级绿水晶#物品", "超级女娲石#物品", "血池#物品", "灵池#物品", "代金券#600000" };
            return bag3;
        }
        if (attrib == 120) {
            final String[] bag3 = { "超级绿水晶#物品", "超级女娲石#物品", "血池#物品", "灵池#物品", "代金券#600000" };
            return bag3;
        }
        if (attrib == 130) {
            final String[] bag3 = { "超级绿水晶#物品", "超级女娲石#物品", "血池#物品", "灵池#物品", "代金券#600000" };
            return bag3;
        }
        return null;
    }

	public static List<Hashtable<String, Integer>> equipmentGiftBags(final int eqType, final int eq_attrib) {
		final List<Hashtable<String, Integer>> appraisalList = new ArrayList<Hashtable<String, Integer>>();
		final int[] skill_low_cost = { 60, 140, 240, 360, 500, 660, 660 };
		final int[] phy_power = { 2, 3, 4, 6, 7, 9, 10 };
		if (eqType == 1) {
			final Hashtable<String, Integer> key_vlaue_tab = new Hashtable<String, Integer>();
			key_vlaue_tab.put("groupNo", 2);
			key_vlaue_tab.put("skill_low_cost", skill_low_cost[eq_attrib / 10 - 1]);
			key_vlaue_tab.put("phy_power", phy_power[eq_attrib / 10 - 1]);
			key_vlaue_tab.put("mag_power", phy_power[eq_attrib / 10 - 1]);
			appraisalList.add(key_vlaue_tab);
		} else {
			final Hashtable<String, Integer> key_vlaue_tab = new Hashtable<String, Integer>();
			key_vlaue_tab.put("groupNo", 2);
			key_vlaue_tab.put("phy_power", phy_power[eq_attrib / 10 - 1]);
			key_vlaue_tab.put("mag_power", phy_power[eq_attrib / 10 - 1]);
			key_vlaue_tab.put("life", phy_power[eq_attrib / 10 - 1]);
			appraisalList.add(key_vlaue_tab);
		}
		String keyName1 = "wiz";
		String keyName2 = "wiz";
		if (eqType == 1) {
			keyName1 = "accurate";
			keyName2 = "mana";
		}
		if (eq_attrib == 10) {
			final Hashtable<String, Integer> key_vlaue_tab2 = new Hashtable<String, Integer>();
			key_vlaue_tab2.put("groupNo", 10);
			key_vlaue_tab2.put(keyName1, (eqType == 1) ? 141 : 48);
			key_vlaue_tab2.put(keyName2, (eqType == 1) ? 141 : 48);
			key_vlaue_tab2.put("changeNum", 3);
			appraisalList.add(key_vlaue_tab2);
		}
		if (eq_attrib == 20) {
			final Hashtable<String, Integer> key_vlaue_tab2 = new Hashtable<String, Integer>();
			key_vlaue_tab2.put("groupNo", 10);
			key_vlaue_tab2.put(keyName1, (eqType == 1) ? 267 : 93);
			key_vlaue_tab2.put(keyName2, (eqType == 1) ? 267 : 93);
			key_vlaue_tab2.put("changeNum", 3);
			appraisalList.add(key_vlaue_tab2);
		}
		if (eq_attrib == 30) {
			final Hashtable<String, Integer> key_vlaue_tab2 = new Hashtable<String, Integer>();
			key_vlaue_tab2.put("groupNo", 10);
			key_vlaue_tab2.put(keyName1, (eqType == 1) ? 417 : 147);
			key_vlaue_tab2.put(keyName2, (eqType == 1) ? 417 : 147);
			key_vlaue_tab2.put("changeNum", 3);
			appraisalList.add(key_vlaue_tab2);
		}
		if (eq_attrib == 40) {
			final Hashtable<String, Integer> key_vlaue_tab2 = new Hashtable<String, Integer>();
			key_vlaue_tab2.put("groupNo", 10);
			key_vlaue_tab2.put(keyName1, (eqType == 1) ? 396 : 140);
			key_vlaue_tab2.put(keyName2, (eqType == 1) ? 396 : 140);
			key_vlaue_tab2.put("changeNum", 2);
			appraisalList.add(key_vlaue_tab2);
		}
		if (eq_attrib == 50 && eqType != 1) {
			final Hashtable<String, Integer> key_vlaue_tab2 = new Hashtable<String, Integer>();
			key_vlaue_tab2.put("groupNo", 10);
			key_vlaue_tab2.put(keyName1, 282);
			key_vlaue_tab2.put(keyName2, 282);
			key_vlaue_tab2.put("changeNum", 3);
			appraisalList.add(key_vlaue_tab2);
		}
		if (eq_attrib == 60) {
			final Hashtable<String, Integer> key_vlaue_tab2 = new Hashtable<String, Integer>();
			key_vlaue_tab2.put("groupNo", 10);
			key_vlaue_tab2.put(keyName1, (eqType == 1) ? 1023 : 363);
			key_vlaue_tab2.put(keyName2, (eqType == 1) ? 1023 : 363);
			key_vlaue_tab2.put("changeNum", 3);
			appraisalList.add(key_vlaue_tab2);
		}
		if (eq_attrib == 70 && eqType != 1) {
			final Hashtable<String, Integer> key_vlaue_tab2 = new Hashtable<String, Integer>();
			key_vlaue_tab2.put("groupNo", 10);
			key_vlaue_tab2.put(keyName1, 453);
			key_vlaue_tab2.put(keyName2, 453);
			key_vlaue_tab2.put("changeNum", 3);
			appraisalList.add(key_vlaue_tab2);
		}
		if (eq_attrib == 70 && eqType != 1) {
			final Hashtable<String, Integer> key_vlaue_tab2 = new Hashtable<String, Integer>();
			key_vlaue_tab2.put("groupNo", 3);
			key_vlaue_tab2.put("phy_power", 10);
			appraisalList.add(key_vlaue_tab2);
		}
		if (eq_attrib == 70 && eqType != 1) {
			final Hashtable<String, Integer> key_vlaue_tab2 = new Hashtable<String, Integer>();
			key_vlaue_tab2.put("groupNo", 4);
			key_vlaue_tab2.put("mag_power", 10);
			appraisalList.add(key_vlaue_tab2);
		}
		return appraisalList;
	}
}