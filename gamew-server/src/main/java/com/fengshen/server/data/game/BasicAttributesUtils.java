package com.fengshen.server.data.game;

import com.fengshen.server.domain.*;
import com.fengshen.server.util.GameConfig;

import java.util.Map;

public class BasicAttributesUtils {
	// 这里是通过角色的一些基本信息，计算成长，包括它的气血、法力、物伤、法伤、速度、防御
	public static int[] calculationAttributes(int attrib, int constitution, int mag_power, int phy_power, int speed,
			int wood, int water, int fire, int earth, int resist_metal) {
		int[] attributes = { 100, 80, 40, 40, 48, 20 };
		int accurate_phy_power = phy_power * 5 + 40;
		int accurate_resist_metal = (int) (phy_power * 0.45 * resist_metal);
		int accurate_all = accurate_phy_power + accurate_resist_metal;
		int mana_mag_power = mag_power * 5 + 40;
		int mana_wood = (int) (mag_power * 0.16 * wood);
		int mana_all = mana_mag_power + mana_wood;
		int wiz_constitution = constitution * 5 + 20;
		int wiz_fire = (int) (constitution * 0.25 * fire);
		int wiz_all = wiz_constitution + wiz_fire;
		int parry_speed = speed * 2 + 48;
		int parry_earth = (int) (speed * 0.023 * earth);
		int parry_all = parry_speed + parry_earth;
		int dex_constitution_per = (int) ((attrib - 2) * 0.3 + 4.0);
		int dex_constitution = dex_constitution_per * mag_power;
		int[] dex_attribs = { 80, 239, 452, 699, 983, 1303, 1658, 2049, 2476, 2939, 3337, 3861, 4421, 5018 };
		int dex_attrib = dex_attribs[0];
		if (attrib >= 130) {
			dex_attrib = dex_attribs[13] + 60 * (attrib - 130);
		} else if (attrib > 1) {
			int index = attrib / 10;
			dex_attrib = dex_attribs[index] + (dex_attribs[index + 1] - dex_attribs[index]) / 10 * (attrib % 10);
		}
		int dex_water = (int) (mag_power * 0.657 * water);
		int dex_all = dex_constitution + dex_attrib + dex_water;
		int def_constitution_per = (int) ((attrib - 2) * 0.3 + 5.0);
		int def_constitution = def_constitution_per * constitution;
		int[] def_attribs = { 100, 359, 727, 1177, 1712, 2281, 2971, 3746, 4604, 5546, 6571, 7569, 8751, 10016 };
		int def_attrib = def_attribs[0];
		if (attrib >= 130) {
			def_attrib = def_attribs[13] + 127 * (attrib - 130);
		} else if (attrib > 1) {
			int index2 = attrib / 10;
			def_attrib = def_attribs[index2] + (def_attribs[index2 + 1] - def_attribs[index2]) / 10 * (attrib % 10);
		}
		int def_water = (int) (constitution * 1.4 * water);
		int def_all = def_constitution + def_attrib + def_water;
		attributes[0] = def_all;
		attributes[1] = dex_all;
		attributes[2] = accurate_all * 2;
		attributes[3] = mana_all * 2;
		attributes[4] = parry_all;
		attributes[5] = wiz_all * 6 / 5;
		return attributes;
	}


	public static void petshuxing1(final PetShuXing petShuXing) {
        if (petShuXing.pot < 0) {
            petShuXing.pot = 0;
        }
        final boolean fagong = petShuXing.rank > petShuXing.pet_mag_shape;
        final int[] attributes = PetAttributesUtils.petAttributes(fagong, petShuXing.skill, petShuXing.life, petShuXing.mag_power, petShuXing.phy_power, petShuXing.speed, petShuXing.pet_mana_shape, petShuXing.pet_speed_shape, petShuXing.pet_phy_shape, petShuXing.pet_mag_shape, petShuXing.rank);
        if (petShuXing.max_life >= petShuXing.def) {
            petShuXing.max_life = petShuXing.def;
        }
        if (petShuXing.max_mana >= petShuXing.dex) {
            petShuXing.max_mana = petShuXing.dex;
        }
        petShuXing.def = attributes[0];
        petShuXing.dex = attributes[1];
        petShuXing.accurate = attributes[2];
        petShuXing.mana = attributes[3];
        petShuXing.parry = attributes[4];
        petShuXing.wiz = attributes[5];

        Map<String, Integer> xinFa = petShuXing.getXinFa();
        if (xinFa != null) {
            double petXinfaRate = GameConfig.config.getBaseConfig().getPetXinfaRate();
            petXinfaRate = petXinfaRate / 100.0;
            for (Map.Entry<String, Integer> entry : xinFa.entrySet()) {
                String key = entry.getKey();
                Integer value = entry.getValue();
                if (value == null || value == 0) {
                    continue;
                }
                if ("def".equals(key)) {
                    petShuXing.def += petShuXing.def * petXinfaRate * value;
                } else if ("dex".equals(key)) {
                    petShuXing.dex += petShuXing.dex * petXinfaRate * value;
                } else if ("accurate".equals(key)) {
                    petShuXing.accurate += petShuXing.accurate * petXinfaRate * value;
                } else if ("mana".equals(key)) {
                    petShuXing.mana += petShuXing.mana * petXinfaRate * value;
                } else if ("parry".equals(key)) {
                    petShuXing.parry += petShuXing.parry * petXinfaRate * value;
                } else if ("wiz".equals(key)) {
                    petShuXing.wiz += petShuXing.wiz * petXinfaRate * value;
                }
            }
        }
        petShuXing.accurate += petShuXing.qinmiAccurate;
        petShuXing.mana += petShuXing.qinmiMana;
        petShuXing.parry += petShuXing.qinmiParry;
        petShuXing.wiz += petShuXing.qinmiWiz;
    }

	// 通过宠物的基本信息计算宠物的6大成长属性
	public static void petshuxing(PetShuXing petShuXing, Petbeibao pet) {
		boolean fagong = petShuXing.rank > petShuXing.pet_mag_shape;
		int[] attributes = PetAttributesUtils.petAttributes(fagong, petShuXing.skill, petShuXing.life,
				petShuXing.mag_power, petShuXing.phy_power, petShuXing.speed, petShuXing.pet_mana_shape,
				petShuXing.pet_speed_shape, petShuXing.pet_phy_shape, petShuXing.pet_mag_shape, petShuXing.rank);
		if (petShuXing.max_life >= petShuXing.def) {
			petShuXing.max_life = petShuXing.def;
		}
		if (petShuXing.max_mana >= petShuXing.dex) {
			petShuXing.max_mana = petShuXing.dex;
		}
		// 增加个判断逻辑，如果通过属性算出来的值比当前宠物的属性大，就替换，否则不替换。
		// 这样可以解决加点失败的问题
		petShuXing.def = attributes[0]; // 气血
		petShuXing.dex = attributes[1]; // 法力
		petShuXing.accurate = attributes[2]; // 物伤
		petShuXing.mana = attributes[3]; // 法伤
		petShuXing.parry = attributes[4]; // 速度
		petShuXing.wiz = attributes[5]; // 防御
		Map<String, Integer> xinFa = petShuXing.getXinFa();
		if (xinFa != null) {
			double petXinfaRate = GameConfig.config.getBaseConfig().getPetXinfaRate();
			petXinfaRate = petXinfaRate / 100.0;
			for (Map.Entry<String, Integer> entry : xinFa.entrySet()) {
				String key = entry.getKey();
				Integer value = entry.getValue();
				if (value == null || value == 0) {
					continue;
				}
				if ("def".equals(key)) {
					petShuXing.def += petShuXing.def * petXinfaRate * value;
				} else if ("dex".equals(key)) {
					petShuXing.dex += petShuXing.dex * petXinfaRate * value;
				} else if ("accurate".equals(key)) {
					petShuXing.accurate += petShuXing.accurate * petXinfaRate * value;
				} else if ("mana".equals(key)) {
					petShuXing.mana += petShuXing.mana * petXinfaRate * value;
				} else if ("parry".equals(key)) {
					petShuXing.parry += petShuXing.parry * petXinfaRate * value;
				} else if ("wiz".equals(key)) {
					petShuXing.wiz += petShuXing.wiz * petXinfaRate * value;
				}
			}
		}
		// 亲密
		petShuXing.accurate += petShuXing.qinmiAccurate; // 物伤
		petShuXing.mana += petShuXing.qinmiMana; // 法伤
		petShuXing.parry += petShuXing.qinmiParry; // 速度
		petShuXing.wiz += petShuXing.qinmiWiz; // 防御

		// 这里是计算妖石伤害
		for (PetShuXing yaoshi : pet.petShuXing) {
			// 在宠物的基础信息里面操作
			if (yaoshi.no >= 12 && yaoshi.no <= 15) {
				petShuXing.wiz += yaoshi.wiz;
				petShuXing.parry += yaoshi.parry;
				petShuXing.def += yaoshi.def;
				petShuXing.dex += yaoshi.dex;
				petShuXing.mana += yaoshi.mana;
				petShuXing.accurate += yaoshi.accurate;
			}
			if(yaoshi.no >= 21920 && yaoshi.no <= 21933){
				petShuXing.wiz += yaoshi.wiz;
				petShuXing.parry += yaoshi.parry;
				petShuXing.def += yaoshi.def;
				petShuXing.dex += yaoshi.dex;
				petShuXing.mana += yaoshi.mana;
				petShuXing.accurate += yaoshi.accurate;
			}
		}
		petShuXing.max_life = petShuXing.def;
		petShuXing.max_mana = petShuXing.dex;
	}

	// 这里是通过等级计算角色的6大成长属性
	public static void shuxing(Chara chara) {
		int[] attributes = calculationAttributes(chara.level, chara.life + chara.zbAttribute.life,
				chara.mag_power + chara.zbAttribute.mag_power, chara.phy_power + chara.zbAttribute.phy_power,
				chara.speed + chara.zbAttribute.speed, chara.metal + chara.zbAttribute.wood,
				chara.wood + chara.zbAttribute.water, chara.water + chara.zbAttribute.fire,
				chara.fire + chara.zbAttribute.earth, chara.earth + chara.zbAttribute.resist_metal);
		chara.def = attributes[0];
		chara.dex = attributes[1];
		if (chara.max_life > chara.def) {
			chara.max_life = chara.def + chara.zbAttribute.def;
		}
		if (chara.max_mana > chara.dex) {
			chara.max_mana = chara.dex + chara.zbAttribute.dex;
		}
		chara.accurate = attributes[2];
		chara.mana = attributes[3];
		chara.parry = attributes[4];
		chara.wiz = attributes[5];
	}

	/**
	 * 计算元婴等级
	 * 
	 * @param chara
	 */
	public static void shuxingToYuanying(Chara chara) {
		int[] attributes = calculationAttributes(chara.upgrade_level, chara.life + chara.zbAttribute.life,
				chara.mag_power + chara.zbAttribute.mag_power, chara.phy_power + chara.zbAttribute.phy_power,
				chara.speed + chara.zbAttribute.speed, chara.metal + chara.zbAttribute.wood,
				chara.wood + chara.zbAttribute.water, chara.water + chara.zbAttribute.fire,
				chara.fire + chara.zbAttribute.earth, chara.earth + chara.zbAttribute.resist_metal);
		chara.def = attributes[0];
		chara.dex = attributes[1];
		if (chara.max_life > chara.def) {
			chara.max_life = chara.def + chara.zbAttribute.def;
		}
		if (chara.max_mana > chara.dex) {
			chara.max_mana = chara.dex + chara.zbAttribute.dex;
		}
		chara.accurate = attributes[2];
		chara.mana = attributes[3];
		chara.parry = attributes[4];
		chara.wiz = attributes[5];
	}

	public static int[] changeCalculationAttributes(int attrib, int constitution, int mag_power, int phy_power,
			int speed) {
		int accurate_all = phy_power * 5;
		int mana_all = mag_power * 5;
		int wiz_all = constitution * 5;
		int parry_all = speed * 2;
		int dex_constitution_per = (int) ((attrib - 2) * 0.3 + 4.0);
		int dex_all = dex_constitution_per * mag_power;
		int def_constitution_per = (int) ((attrib - 2) * 0.3 + 5.0);
		int def_all = def_constitution_per * constitution;
		int[] attributes = { 0, 0, 0, 0, 0, 0 };
		attributes[0] = def_all;
		attributes[1] = dex_all;
		attributes[2] = accurate_all;
		attributes[3] = mana_all;
		attributes[4] = parry_all;
		attributes[5] = wiz_all;
		return attributes;
	}

	public static int[] changeRelAttributes(int attrib, int constitution, int mag_power, int phy_power, int speed,
			int wood, int water, int fire, int earth, int resist_metal) {
		int accurate_all = (int) (phy_power * 0.45 * resist_metal);
		int mana_all = (int) (mag_power * 0.16 * wood);
		int wiz_all = (int) (constitution * 0.25 * fire);
		int parry_all = (int) (speed * 0.023 * earth);
		int dex_all = (int) (mag_power * 0.657 * water);
		int def_all = (int) (constitution * 1.4 * water);
		int[] attributes = { 0, 0, 0, 0, 0, 0 };
		attributes[0] = def_all;
		attributes[1] = dex_all;
		attributes[2] = accurate_all;
		attributes[3] = mana_all;
		attributes[4] = parry_all;
		attributes[5] = wiz_all;
		return attributes;
	}

	public static int[] calculationHelpAttributes(int attrib, int constitution, int mag_power, int phy_power, int speed,
			int wood, int water, int fire, int earth, int resist_metal, int polar) {
		int[] attributes = { 100, 80, 40, 40, 48, 20 };
		int accurate_phy_power = phy_power * 5 + 40;
		int accurate_resist_metal = (int) (phy_power * 0.45 * resist_metal);
		int accurate_all = accurate_phy_power + accurate_resist_metal;
		int mana_mag_power = mag_power * 5 + 40;
		int mana_wood = (int) (mag_power * 0.16 * wood);
		int mana_all = mana_mag_power + mana_wood;
		int wiz_constitution = constitution * 5 + 20;
		int wiz_fire = (int) (constitution * 0.25 * fire);
		int wiz_all = wiz_constitution + wiz_fire;
		int parry_speed = speed * 2 + 48;
		int parry_earth = (int) (speed * 0.023 * earth);
		int parry_all = parry_speed + parry_earth;
		int dex_constitution_per = (int) ((attrib - 2) * 0.3 + 4.0);
		int dex_constitution = dex_constitution_per * mag_power;
		int[] dex_attribs = { 80, 239, 452, 699, 983, 1303, 1658, 2049, 2476, 2939, 3337, 3861, 4421, 5018 };
		int dex_attrib = dex_attribs[0];
		if (attrib >= 130) {
			dex_attrib = dex_attribs[13] + 60 * (attrib - 130);
		} else if (attrib > 1) {
			int index = attrib / 10;
			dex_attrib = dex_attribs[index] + (dex_attribs[index + 1] - dex_attribs[index]) / 10 * (attrib % 10);
		}
		int dex_water = (int) (mag_power * 0.657 * water);
		int dex_all = dex_constitution + dex_attrib + dex_water;
		int def_constitution_per = (int) ((attrib - 2) * 0.3 + 5.0);
		int def_constitution = def_constitution_per * constitution;
		int[] def_attribs = { 100, 359, 727, 1177, 1712, 2281, 2971, 3746, 4604, 5546, 6571, 7569, 8751, 10016 };
		int def_attrib = def_attribs[0];
		if (attrib >= 130) {
			def_attrib = def_attribs[13] + 127 * (attrib - 130);
		} else if (attrib > 1) {
			int index2 = attrib / 10;
			def_attrib = def_attribs[index2] + (def_attribs[index2 + 1] - def_attribs[index2]) / 10 * (attrib % 10);
		}
		int def_water = (int) (constitution * 1.4 * water);
		int def_all = def_constitution + def_attrib + def_water;
		double[][] hs = { { 2.0, 8.0, 4.0, 2.0, 10.0 }, { 1.3, 8.0, 9.0, 2.0, 2.6 }, { 1.3, 6.0, 3.0, 2.0, 3.0 },
				{ 2.0, 1.7, 7.0, 2.0, 6.3 }, { 1.5, 2.1, 13.0, 2.0, 7.0 } };
		attributes[0] = (int) (def_all * hs[polar - 1][0]);
		attributes[1] = (int) (dex_all * hs[polar - 1][0]);
		attributes[2] = (int) (accurate_all * hs[polar - 1][1]);
		attributes[3] = (int) (mana_all * hs[polar - 1][2]);
		attributes[4] = (int) (parry_all * hs[polar - 1][3]);
		attributes[5] = (int) (wiz_all * hs[polar - 1][4]);
		return attributes;
	}
}
