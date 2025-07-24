package com.fengshen.server.data.game;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;

import com.alibaba.fastjson.JSONObject;
import com.fengshen.server.data.UtilObjMapshuxing;
import com.fengshen.server.domain.Chara;
import com.fengshen.server.domain.Goods;
import com.fengshen.server.domain.GoodsGaiZao;
import com.fengshen.server.domain.GoodsGaiZaoGongMing;
import com.fengshen.server.domain.GoodsLanSe;
import com.fengshen.server.domain.config.EquipGaiZaoConfig;
import com.fengshen.server.util.GameConfig;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ForgingEquipmentUtils {
	public static int RESIST_POLAR_J = 1;
	public static int RESIST_POLAR_M = 2;
	public static int RESIST_POLAR_S = 3;
	public static int RESIST_POLAR_H = 4;
	public static int RESIST_POLAR_T = 5;
	public static int EQUIPMENT_APPRAISA_NORMAL = 1;
	public static int EQUIPMENT_APPRAISA_DELICATE = 2;
	public static int EQUIPMENT_APPRAISA_PINK = 3;
	public static int EQUIPMENT_APPRAISA_YELLOW = 4;
	public static int EQUIPMENT_APPRAISA_GREEN = 5;
	public static int EQUIPMENT_APPRAISA_REMAKE = 6;
	public static int EQUIPMENT_APPRAISA_REMAKE_FIVE = 7;
	public static int EQUIPMENT_APPRAISA_RESONANCE = 8;
	public static int EQUIPMENT_SYNTHESIS_JEWELRY = 9;
	public static int EQUIPMENT_LUCK_DRAW = 10;

	public static int[] appendAttrib(String key, int currentValue, int eq_attrib, int eqType) {
		return appendAttrib(key, currentValue, eq_attrib, 0, eqType);
	}

	private static int[] proportion(int currentProportion, int a, int maxValue, int newValue, int step) {
		int[] new_pro = new int[2];
		if (currentProportion + a >= 100000000 && newValue + step < maxValue) {
			new_pro[0] = newValue + step;
			new_pro[1] = 0;
			return new_pro;
		}
		new_pro[0] = newValue;
		new_pro[1] = currentProportion + a;
		return new_pro;
	}

	/**
	 * 改造装备， 客户要求 -----装备改造1-7百分60完成度爆 7-9堆满 9-10百分70完成度爆 10-11堆满 11-12武器百分55爆其余堆满
	 * 
	 * @param currentColor      当前改造等级
	 * @param currentProportion 改造度
	 * @param stoneCount 灵石的个数
	 * @return
	 */
	public static int[] remakeAttrib(int currentColor, int currentProportion, int stoneCount, int type) {
		int[] v_a = new int[2];
		EquipGaiZaoConfig config = GameConfig.equipGaiZaoConfig;
		int money = 108;
		if (type == 1) {
			money = 328;
		}
		if (currentProportion >= 100000000) {
			++currentColor;
		}
		// 如果当前的装备已经是改造12了，则不再增加
		if (currentColor >= 12) {
			currentColor = 12;
			v_a[0] = currentColor;
			v_a[1] = 0;
			return v_a;
		}
		int addNum = 1000000 * stoneCount;
		// 每次加多少完成度,根据道具个数
		if (currentColor == 0) {
			currentColor = 1;
		}
		if(GameConfig.equipGaiZao.get("n"+currentColor).equals("积分")) {
			//武器
			if (type == 1) {
				money = config.equipPrice;
			}else {
				money = config.defPrice;
			}
		}
		// 4-5
		if (currentColor == 1) {
			// 改造次数
			double num = config.n1 / money;
			double jindu = 15 / num;
			if ((currentProportion / 1000000) > 45) {
				if (new Random().nextBoolean()) {
					v_a[0] = currentColor + 1;
					v_a[1] = currentProportion;
				}
			} else {
				v_a[0] = currentColor;
				v_a[1] = (int) (currentProportion + (addNum * jindu));
			}
			if(config.n1 <= 0) {
				v_a[0] = currentColor+1;
			}
		} else if (currentColor == 2) {
			// 改造次数
			double num = config.n2 / money;
			double jindu = 25 / num;
			if ((currentProportion / 1000000) > 45) {
				if (new Random().nextBoolean()) {
					v_a[0] = currentColor + 1;
					v_a[1] = currentProportion;
				}
			} else {
				v_a[0] = currentColor;
				v_a[1] = (int) (currentProportion + (addNum * jindu));
			}
			if(config.n4 <= 0) {
				v_a[0] = currentColor+1;
			}
		} else if (currentColor == 3) {
			// 改造次数
			double num = config.n3 / money;
			double jindu = 35 / num;
			if ((currentProportion / 1000000) > 45) {
				if (new Random().nextBoolean()) {
					v_a[0] = currentColor + 1;
					v_a[1] = currentProportion;
				}
			} else {
				v_a[0] = currentColor;
				v_a[1] = (int) (currentProportion + (addNum * jindu));
			}
			if(config.n3 <= 0) {
				v_a[0] = currentColor+1;
			}
		} else if (currentColor == 4) {
			// 改造次数
			double num = config.n4 / money;
			double jindu = 45 / num;
			if ((currentProportion / 1000000) > 45) {
				if (new Random().nextBoolean()) {
					v_a[0] = currentColor + 1;
					v_a[1] = currentProportion;
				}
			} else {
				v_a[0] = currentColor;
				v_a[1] = (int) (currentProportion + (addNum * jindu));
			}
			if(config.n4 <= 0) {
				v_a[0] = currentColor+1;
			}
		} else if (currentColor == 5) {
			// 改造5-6
			double num = config.n5 / money;
			double jindu = 50 / num;
			if ((currentProportion / 1000000) > 50) {
				if (new Random().nextBoolean()) {
					v_a[0] = currentColor + 1;
					v_a[1] = currentProportion;
				}
			} else {
				v_a[0] = currentColor;
				v_a[1] = (int) (currentProportion + (addNum * jindu));
			}
			if(config.n5 <= 0) {
				v_a[0] = currentColor+1;
			}
		} else if (currentColor == 6) {
			// 改造6-7
			double num = config.n6 / money;
			double jindu = 55 / num;
			if ((currentProportion / 1000000) > 55) {
				if (new Random().nextBoolean()) {
					v_a[0] = currentColor + 1;
					v_a[1] = currentProportion;
				}
			} else {
				v_a[0] = currentColor;
				v_a[1] = (int) (currentProportion + (addNum * jindu));
			}
			if(config.n6 <= 0) {
				v_a[0] = currentColor+1;
			}
		} else if (currentColor == 7) {
			// 改造7-8
			double num = config.n7 / money;
			double jindu = 60 / num;
			if ((currentProportion / 1000000) > 60) {
				if (new Random().nextBoolean()) {
					v_a[0] = currentColor + 1;
					v_a[1] = currentProportion;
				}
			} else {
				v_a[0] = currentColor;
				v_a[1] = (int) (currentProportion + (addNum * jindu));
			}
			if(config.n7 <= 0) {
				v_a[0] = currentColor+1;
			}
		} else if (currentColor == 8) {
			// 改造8-9
			double num = config.n8 / money;
			double jindu = 65 / num;
			if ((currentProportion / 1000000) > 65) {
				if (new Random().nextBoolean()) {
					v_a[0] = currentColor + 1;
					v_a[1] = currentProportion;
				}
			} else {
				v_a[0] = currentColor;
				v_a[1] = (int) (currentProportion + (addNum * jindu));
			}
			if(config.n8 <= 0) {
				v_a[0] = currentColor+1;
			}
		} else if (currentColor == 9) {
			// 改造9-10
			double num = config.n9 / money;
			double jindu = 70 / num;
			if ((currentProportion / 1000000) > 70) {
				if (new Random().nextBoolean()) {
					v_a[0] = currentColor + 1;
					v_a[1] = currentProportion;
				}
			} else {
				v_a[0] = currentColor;
				v_a[1] = (int) (currentProportion + (addNum * jindu));
			}
			if(config.n9 <= 0) {
				v_a[0] = currentColor+1;
			}
		} else if (currentColor == 10) {
			// 改造10-11
			double num = config.n10 / money;
			double jindu = 70 / num;
			if ((currentProportion / 1000000) > 75) {
				if (new Random().nextBoolean()) {
					v_a[0] = currentColor + 1;
					v_a[1] = currentProportion;
				}
			} else {
				v_a[0] = currentColor;
				v_a[1] = (int) (currentProportion + (addNum * jindu));
			}
			if(config.n10 <= 0) {
				v_a[0] = currentColor+1;
			}
		} else if (currentColor == 11) {
			// 改造11-12
			double num = config.n11 / money;
			double jindu = 90 / num;
			if ((currentProportion / 1000000) > 90) {
				if (new Random().nextBoolean()) {
					v_a[0] = currentColor + 1;
					v_a[1] = currentProportion;
				}
			} else {
				v_a[0] = currentColor;
				v_a[1] = (int) (currentProportion + (addNum * jindu));
			}
			if(config.n11 <= 0) {
				v_a[0] = currentColor+1;
			}
		} else {
			v_a[0] = currentColor + 1;
			v_a[1] = currentProportion;
		}
		//如果当前的值大于百分百完成度，就直接加1
		if (v_a[1] >= 100000000) {
			v_a[1] = 100000000;
			v_a[0] = currentColor+1;
		}
		//如果配置的数据为0，那就直接改造成功
		return v_a;
	}

	public static int[] appendAttrib(String key, int currentValue, int eq_attrib, int currentProportion, int eqType) {
		String chineseName = getEquipmentKeyByName(key, false);
		int maxValue = getMaxValueByChineseName(chineseName, eq_attrib, eqType == 3, false);
		int[] v_a = new int[3];
		if (currentValue >= maxValue) {
			v_a[0] = maxValue;
			v_a[2] = (v_a[1] = 0);
			return v_a;
		}
		int step = 1;
		if (maxValue <= 30) {
			step = 1;
		} else if (maxValue <= 50) {
			step = 2;
		} else if (maxValue <= 100) {
			step = 5;
		} else if (maxValue <= 500) {
			step = 50;
		} else if (maxValue <= 1000) {
			step = 100;
		} else if (maxValue <= 2000) {
			step = 200;
		} else if (maxValue <= 4000) {
			step = 400;
		} else {
			step = 500;
		}
		int[] as = { 11200000, 8200000, 5300000, 1900000, 142222 };
		Random random = new Random();
		if (maxValue - currentValue <= step) {
			int v = random.nextInt(maxValue - currentValue + 1) + currentValue;
			v_a[0] = v;
			v_a[1] = as[4];
			int[] new_value = proportion(currentProportion, v_a[1], maxValue, v_a[0], step);
			v_a[0] = new_value[0];
			v_a[2] = new_value[1];
			return v_a;
		}
		if (maxValue - currentValue <= 2 * step && maxValue - currentValue > step) {
			int v = (random.nextInt(100) < 10) ? (maxValue - step) : currentValue;
			v_a[0] = v;
			v_a[1] = as[3];
			int[] new_value = proportion(currentProportion, v_a[1], maxValue, v_a[0], step);
			v_a[0] = new_value[0];
			v_a[2] = new_value[1];
			return v_a;
		}
		if (maxValue - currentValue <= 3 * step) {
			int v = (random.nextInt(100) < 30) ? (maxValue - 2 * step) : currentValue;
			v_a[0] = v;
			v_a[1] = as[2];
			int[] new_value = proportion(currentProportion, v_a[1], maxValue, v_a[0], step);
			v_a[0] = new_value[0];
			v_a[2] = new_value[1];
			return v_a;
		}
		List<Integer> vlist = new ArrayList<Integer>();
		for (int count = (maxValue + step - currentValue) / step, m = 0; m < count; ++m) {
			int value = currentValue + m * step;
			if (value >= maxValue) {
				value = maxValue;
			}
			vlist.add(value);
			if (value == maxValue) {
				break;
			}
		}
		int length = vlist.size() - 1;
		List<Integer> separates = new ArrayList<Integer>();
		List<Integer> percents = new ArrayList<Integer>();
		if (length == 2) {
			separates.add(1);
			percents.add(97);
			percents.add(3);
			v_a[1] = as[4];
		} else if (length == 3) {
			separates.add(1);
			separates.add(2);
			percents.add(78);
			percents.add(20);
			percents.add(2);
			v_a[1] = as[3];
		} else if (length == 4) {
			separates.add(2);
			separates.add(3);
			percents.add(78);
			percents.add(20);
			percents.add(2);
			v_a[1] = as[2];
		} else {
			separates.add(length / 2);
			separates.add(length - 3);
			separates.add(length - 1);
			percents.add(63);
			percents.add(30);
			percents.add(5);
			percents.add(2);
			v_a[1] = as[0];
		}
		int number = RateRandomNumber.produceRateRandomNumber(0, length, separates, percents);
		v_a[0] = vlist.get(number);
		int[] new_value2 = proportion(currentProportion, v_a[1], maxValue, v_a[0], step);
		v_a[0] = new_value2[0];
		v_a[2] = new_value2[1];
		return v_a;
	}

	public static String[] removeAttrib(String[] attribs) {
		return removeAttrib(attribs, false);
	}

	public static String[] removeAttrib(String[] attribs, boolean useChaos) {
		String[] strings = new String[2];
		int length = attribs.length;
		if (length == 0) {
			return null;
		}
		Random random = new Random();
		if (!useChaos && random.nextInt(10) <= 3) {
			return null;
		}
		int r = random.nextInt(length);
		String name = getEquipmentKeyByName(attribs[r], false);
		if (name.contentEquals("伤害_最低伤害")) {
			name = "伤害";
		}
		strings[0] = String.valueOf(r);
		strings[1] = name;
		return strings;
	}

	// 鉴定装备，出属性的地方.eqType是装备类型1、2、10.. eq_attrib是等级。appraisalType 鉴定类型，1是普通鉴定。2是精致鉴定
	// 这里会随机鉴定出3条蓝属性、1条粉属性、1条黄属性
	public static List<Hashtable<String, Integer>> appraisalEquipment(int eqType, int eq_attrib, int appraisalType) {
		return appraisalEquipment(eqType, eq_attrib, appraisalType, null, 0, 0);
	}

	// add tzhang 返回首饰的最大满属性值。eqType是首饰的类型
	public static List<Hashtable<String, Integer>> appraisalMaxALLEquipment(int eqType, int dst_eq_attrib,
			Hashtable<String, Integer> hashtable) {
		List<Hashtable<String, Integer>> hashtableList = new ArrayList<Hashtable<String, Integer>>();
		if (null == hashtable) {
			hashtable = new Hashtable<String, Integer>();
		}
		if (appraisalEquipment(eqType)) {
			hashtableList = appraisalEquipment(eqType, dst_eq_attrib, hashtable);
			// add tzhang 将刚刚炼化出来的属性设置为满值
			if (hashtableList.get(0) != null) {
				Hashtable<String, Integer> maps2 = hashtableList.get(0);
				for (String key1 : maps2.keySet()) {
					if (!key1.equals("groupNo")) {
						if (key1.equals("groupType")) {
							continue;
						}
						if (maps2.get(key1).toString().equals("0")) {
							continue;
						}
						hashtable.put(key1, getMaxValueByChineseName(getEquipmentKeyByName(key1, false), dst_eq_attrib,
								false, false));
					}
				}
				// add:e
				if (!hashtable.isEmpty() && dst_eq_attrib >= 90) {
					// 这里检测并设置之前等级(80-110)的属性
					Set<String> keys = hashtable.keySet();
					for (String key : keys) {
						String chineseName = getEquipmentKeyByName(key, false);
						int dst_max = getMaxValueByChineseName(chineseName, dst_eq_attrib, false, false);
						hashtable.put(key, dst_max);
					}
				}

			}
			hashtableList.get(0).putAll(hashtable);
		}
		return hashtableList;
	}
	// add:e

	/**
	 * 随机首饰属性
	 * 
	 * @param eqType
	 * @param dst_eq_attrib
	 * @param hashtable
	 * @return
	 */
	public static List<Hashtable<String, Integer>> shoushiRandomEquipment(int eqType, int dst_eq_attrib,
			Hashtable<String, Integer> hashtable) {
		List<Hashtable<String, Integer>> hashtableList = new ArrayList<Hashtable<String, Integer>>();
		if (null == hashtable) {
			hashtable = new Hashtable<String, Integer>();
		}
		if (appraisalEquipment(eqType)) {
			hashtableList = appraisalEquipment(eqType, dst_eq_attrib, hashtable);
			// add tzhang 将刚刚炼化出来的属性设置为满值
			if (hashtableList.get(0) != null) {
				Hashtable<String, Integer> maps2 = hashtableList.get(0);
				for (String key1 : maps2.keySet()) {
					if (!key1.equals("groupNo")) {
						if (key1.equals("groupType")) {
							continue;
						}
						if (maps2.get(key1).toString().equals("0")) {
							continue;
						}
						hashtable.put(key1, getMaxValueByChineseName(getEquipmentKeyByName(key1, false), dst_eq_attrib,
								false, false));
					}
				}
				// add:e
				if (!hashtable.isEmpty() && dst_eq_attrib >= 90) {
					// 这里检测并设置之前等级(80-110)的属性
					Set<String> keys = hashtable.keySet();
					for (String key : keys) {
						String chineseName = getEquipmentKeyByName(key, false);
						int current_max = getMaxValueByChineseName(chineseName, dst_eq_attrib - 10, false, false);
						hashtable.put(key, new Random().nextInt(current_max) + 1);
					}
				}

			}
			hashtableList.get(0).putAll(hashtable);
		}
		return hashtableList;
	}

	public static List<Hashtable<String, Integer>> appraisalALLEquipment(int eqType, int dst_eq_attrib,
			Hashtable<String, Integer> hashtable) {
		List<Hashtable<String, Integer>> hashtableList = new ArrayList<Hashtable<String, Integer>>();
		if (null == hashtable) {
			hashtable = new Hashtable<String, Integer>();
		}
		if (appraisalEquipment(eqType)) {
			hashtableList = appraisalEquipment(eqType, dst_eq_attrib, hashtable);
			if (!hashtable.isEmpty() && dst_eq_attrib >= 90) {
				Set<String> keys = hashtable.keySet();
				for (String key : keys) {
					String chineseName = getEquipmentKeyByName(key, false);
					//当前需要升级的首饰最大值
					int current_max = getMaxValueByChineseName(chineseName, dst_eq_attrib, false, false);
					//下一阶段
					int dst_max = getMaxValueByChineseName(chineseName, dst_eq_attrib+10, false, false);
					int dst_value = hashtable.get(key) * dst_max / current_max;
					hashtable.put(key, dst_value);
				}
			}
			hashtableList.get(0).putAll(hashtable);
		}
		return hashtableList;
	}

	public static boolean appraisalEquipment(int dst_eq_attrib) {
		Hashtable<Integer, Integer> hashtable = new Hashtable<Integer, Integer>();
		hashtable.put(35, 0);
		hashtable.put(50, 1);
		hashtable.put(60, 2);
		hashtable.put(70, 3);
		hashtable.put(80, 4);
		hashtable.put(90, 5);
		hashtable.put(100, 6);
		hashtable.put(110, 7);
		hashtable.put(120, 8);
		hashtable.put(130, 9);
		if (hashtable.contains(dst_eq_attrib)) {
			if (dst_eq_attrib <= 70) {
				return true;
			}
			if (dst_eq_attrib >= 70 && dst_eq_attrib <= 100) {
				Random random = new Random();
				return random.nextInt(100) < 30 - 8 * (dst_eq_attrib / 10 - 7);
			}
			if (dst_eq_attrib >= 110) {
				return true;
			}
		}
		return false;
	}

	public static List<Hashtable<String, Integer>> appraisalEquipment(int eqType, int eq_attrib,
			Hashtable<String, Integer> hashtable) {
		HashSet<String> only = new HashSet<String>();
		if (null != hashtable && !hashtable.isEmpty()) {
			only.addAll((Collection<? extends String>) hashtable.keySet());
		}
		return appraisalEquipment(eqType, eq_attrib, 9, only, 0, 0);
	}

	public static List<Hashtable<String, Integer>> appraisalGreenEquipment(int eqType, int eq_attrib, int polar, Chara... chara) {
		return appraisalEquipment(eqType, eq_attrib, 5, null, polar, 0, chara);
	}

	public static List<Hashtable<String, Integer>> appraisalRemakeEquipment(int eqType, int eq_attrib,
			int currentColor) {
		List<Hashtable<String, Integer>> hashtableList = new ArrayList<Hashtable<String, Integer>>();
		if (currentColor < 5) {
			hashtableList.addAll(appraisalEquipment(eqType, eq_attrib, 6, null, 0, currentColor));
		} else {
			List<Hashtable<String, Integer>> list = appraisalEquipment(eqType, eq_attrib, 6, null, 0, currentColor);
			if (!list.isEmpty()) {
				Hashtable<String, Integer> hashtable = list.get(0);
				if (null != hashtable) {
					List<Hashtable<String, Integer>> listf = appraisalEquipment(eqType, eq_attrib, 7, null, 0,
							currentColor);
					if (!list.isEmpty()) {
						Hashtable<String, Integer> hashtablef = listf.get(0);
						if (hashtablef.get("groupNo").equals(hashtable.get("groupNo"))) {
							hashtable.putAll(hashtablef);
							hashtableList.add(hashtable);
						}
					}
				}
			}
		}
		return hashtableList;
	}

	// 改造共鸣
	public static List<Hashtable<String, Integer>> appraisalRemakeEquipment(String resonance, int eqType, int eq_attrib,
			int currentColor) {
		List<Hashtable<String, Integer>> hashtableList = new ArrayList<Hashtable<String, Integer>>();
		if (null != resonance && resonance.length() != 0) {
			Hashtable<String, Integer> key_vlaue_tab = new Hashtable<String, Integer>();
			key_vlaue_tab.put("groupNo", 27);
			String equipmentKeyByName = getEquipmentKeyByName(resonance);
			int maxValueByChineseName = getMaxValueByChineseName(resonance, eq_attrib, eqType == 3, true);
			int num = maxValueByChineseName * currentColor / 4;
			key_vlaue_tab.put(equipmentKeyByName,num);
			log.info("改造------------>共鸣,数值:{}",num);
			hashtableList.add(key_vlaue_tab);
		}
		List<Hashtable<String, Integer>> hashtableList2 = appraisalRemakeEquipment(eqType, eq_attrib, currentColor);
		hashtableList.addAll(hashtableList2);
		return hashtableList;
	}

	public static List<Hashtable<String, Integer>> resonanceEquipMent(int eq_attrib, int currentColor, int stone,
			boolean addOrChange) {
		List<Hashtable<String, Integer>> hashtableList = new ArrayList<Hashtable<String, Integer>>();
		Random random = new Random();
		if (addOrChange || stone >= 3 || random.nextBoolean()) {
			hashtableList.addAll(appraisalEquipment(0, eq_attrib, 8, null, 0, currentColor));
			
		}
		return hashtableList;
	}

	public static List<Hashtable<String, Integer>> appraisalEquipment(int eqType, int eq_attrib, int appraisalType,
			int polar) {
		return appraisalEquipment(eqType, eq_attrib, appraisalType, null, polar, 0);
	}

	// 粉水晶炼化会传入到这里
	public static List<Hashtable<String, Integer>> appraisalEquipment(int eqType, int eq_attrib, int appraisalType,
			HashSet<String> repeatAttributes) {
		if (3 == appraisalType) {
			return appraisalEquipment(eqType, eq_attrib, appraisalType, repeatAttributes, 0, 0);
		}
		return new ArrayList<Hashtable<String, Integer>>();
	}

	// 黄水晶炼化会传入这里
	public static List<Hashtable<String, Integer>> appraisalYellowEquipment(int eqType, int eq_attrib,
			int appraisalType, HashSet<String> repeatAttributes, int stone) {
		if (4 == appraisalType) {
			Random random = new Random();
			int r = random.nextInt(100);
			if (r < 90 || stone >= 6) {
				return appraisalEquipment(eqType, eq_attrib, appraisalType, repeatAttributes, 0, 0);
			}
		}
		return new ArrayList<Hashtable<String, Integer>>();
	}

	// 鉴定装备
	private static List<Hashtable<String, Integer>> appraisalEquipment(int eqType, int eq_attrib, int appraisalType,
			HashSet<String> repeatAttributes, int polar, int color, Chara... chara) {
		List<Hashtable<String, Integer>> appraisalList = new ArrayList<Hashtable<String, Integer>>();
		if (10 == appraisalType) { // 鉴定鞋子
			Hashtable<Integer, String[]> hashtable = baseBlueSuit(eqType, eq_attrib);
			List<String> blueList = randomAttribute(hashtable.get(eqType), (new Random().nextInt(5) < 4) ? 1 : 2);
			Hashtable<String, Integer> key_vlaue_tab = new Hashtable<String, Integer>();
			key_vlaue_tab.put("groupNo", 2);
			for (String key : blueList) {
				key_vlaue_tab.put(getEquipmentKeyByName(key),
						getMaxValueByChineseName(key, eq_attrib, eqType == 3, false));
			}
			appraisalList.add(key_vlaue_tab);
		}
		// 普通鉴定
		else if (1 == appraisalType) {
			Hashtable<Integer, String[]> hashtable = baseBlueSuit(eqType, eq_attrib);
			List<String> blueList = randomAttribute(hashtable.get(eqType), new Random().nextInt(3) + 1);
			Hashtable<String, Integer> key_vlaue_tab = new Hashtable<String, Integer>();
			key_vlaue_tab.put("groupNo", 2);
			for (String key : blueList) {
				key_vlaue_tab.put(getEquipmentKeyByName(key),
						getValueByChineseName(key, eq_attrib, eqType == 3, false));
			}
			appraisalList.add(key_vlaue_tab);
		}
		// 精致鉴定
		else if (2 == appraisalType) {
			Random random = new Random();
			Hashtable<Integer, String[]> hashtable2 = baseBlueSuit(eqType, eq_attrib);
			// 暂时让随机鉴定的蓝属性都是三条
			List<String> blueList2 = randomAttribute(hashtable2.get(eqType), 3);
			Hashtable<String, Integer> key_vlaue_tab2 = new Hashtable<String, Integer>();
			for (String key2 : blueList2) {
				key_vlaue_tab2.put("groupNo", 2); // 蓝属性
				key_vlaue_tab2.put(getEquipmentKeyByName(key2),
						getValueByChineseName(key2, eq_attrib, eqType == 3, false));
			}
			appraisalList.add(key_vlaue_tab2);
			Hashtable<Integer, String[]> pink_hashtable = pinkSuit(eqType, eq_attrib);
			List<String> pinkList = randomAttribute(pink_hashtable.get(eqType), 2);
			if (pinkList.size() == 2) {
				Hashtable<String, Integer> key_vlaue_pTab = new Hashtable<String, Integer>();
				key_vlaue_pTab.put("groupNo", 3); // 粉属性
				key_vlaue_pTab.put(getEquipmentKeyByName(pinkList.get(0)),
						getValueByChineseName(pinkList.get(0), eq_attrib, eqType == 3, false));
				appraisalList.add(key_vlaue_pTab);
				if (random.nextInt(10) <= 7) {
					Hashtable<String, Integer> key_vlaue_yTab = new Hashtable<String, Integer>();
					key_vlaue_yTab.put("groupNo", 4); // 黄属性
					key_vlaue_yTab.put(getEquipmentKeyByName(pinkList.get(1)),
							getValueByChineseName(pinkList.get(1), eq_attrib, eqType == 3, false));
					appraisalList.add(key_vlaue_yTab);
				}
			}
		}
		// 粉水晶炼化会调用这里
		else if (3 == appraisalType) {
			Hashtable<Integer, String[]> pink_hashtable2 = pinkSuit(eqType, eq_attrib);
			String[] attributes = pink_hashtable2.get(eqType); // 获取该类型的炼化类型
			List<String> resultList = new ArrayList<>(attributes.length);
			Collections.addAll(resultList, attributes);
			// 这段代码是用来删除重复属性的
			if (null != repeatAttributes && !repeatAttributes.isEmpty()) {
				for (String repKey : repeatAttributes) {
					String name = getEquipmentKeyByName(repKey, false);
					if (resultList.contains(name)) {
						resultList.remove(name);
					}
				}
			}
			String[] arrayLeave = resultList.toArray(new String[resultList.size()]);
			List<String> pinkList2 = randomAttribute(arrayLeave, 1);
			Hashtable<String, Integer> key_vlaue_pTab2 = new Hashtable<String, Integer>();
			key_vlaue_pTab2.put("groupNo", (3 == appraisalType) ? 3 : 4);
			String attr = getEquipmentKeyByName(pinkList2.get(0));
			Integer value = getValueByChineseName(pinkList2.get(0), eq_attrib, eqType == 3, false);
			key_vlaue_pTab2.put(attr, value);
			appraisalList.add(key_vlaue_pTab2);

		}
		// 黄水晶炼化会调用这里
		else if (4 == appraisalType) {
			Hashtable<Integer, String[]> pink_hashtable2 = yellowSuit(eqType, eq_attrib);
			String[] attributes = pink_hashtable2.get(eqType); // 获取该类型的炼化类型
			List<String> resultList = new ArrayList<>(attributes.length);
			Collections.addAll(resultList, attributes);
			// 这段代码是用来删除重复属性的
			if (null != repeatAttributes && !repeatAttributes.isEmpty()) {
				for (String repKey : repeatAttributes) {
					String name = getEquipmentKeyByName(repKey, false);
					if (resultList.contains(name)) {
						resultList.remove(name);
					}
				}
			}
			String[] arrayLeave = resultList.toArray(new String[resultList.size()]);
			List<String> pinkList2 = randomAttribute(arrayLeave, 1);
			Hashtable<String, Integer> key_vlaue_pTab2 = new Hashtable<String, Integer>();
			key_vlaue_pTab2.put("groupNo", (3 == appraisalType) ? 3 : 4);
			String attr = getEquipmentKeyByName(pinkList2.get(0));
			Integer value = getValueByChineseName(pinkList2.get(0), eq_attrib, eqType == 3, false);
			key_vlaue_pTab2.put(attr, value);
			appraisalList.add(key_vlaue_pTab2);
		} else if (9 == appraisalType) {
			Hashtable<Integer, String[]> jewelry_hashtable = baseBlueSuit(eqType, eq_attrib);
			String[] attributes = jewelry_hashtable.get(eqType);
			List<String> resultList = new ArrayList<String>(attributes.length);
			Collections.addAll(resultList, attributes);
			if (null != repeatAttributes && !repeatAttributes.isEmpty()) {
				for (String repKey : repeatAttributes) {
					String name = getEquipmentKeyByName(repKey, false);
					if (resultList.contains(name)) {
						resultList.remove(name);
					}
				}
			}
			String[] arrayLeave = resultList.toArray(new String[resultList.size()]);
			List<String> pinkList2 = randomAttribute(arrayLeave, 1);
			Hashtable<String, Integer> key_vlaue_pTab2 = new Hashtable<String, Integer>();
			key_vlaue_pTab2.put("groupNo", 2);
			key_vlaue_pTab2.put(getEquipmentKeyByName(pinkList2.get(0)),
					getValueByChineseName(pinkList2.get(0), eq_attrib, eqType == 3, false));
			appraisalList.add(key_vlaue_pTab2);
		}
		// 绿色属性
		else if (5 == appraisalType) {
			int charaPolar = polar;
			if(chara!=null && chara.length != 0) {
				charaPolar = chara[0].polar;
			}
			Hashtable<Integer, String[]> green_hashtable = randomGreenSuit(polar, charaPolar);
			String[] attributes = green_hashtable.get(eqType);
			List<String> greenAttribute = randomAttribute(attributes, 1);
			Hashtable<String, Integer> key_vlaue_tab2 = new Hashtable<String, Integer>();
			key_vlaue_tab2.put("groupNo", 12);
			key_vlaue_tab2.put(getEquipmentKeyByName(greenAttribute.get(0)),
					getValueByChineseName(greenAttribute.get(0), eq_attrib, eqType == 3, true));
			appraisalList.add(key_vlaue_tab2);
			String basekey = getEquipmentKeyByName(baseGreenSuit(polar));
			Hashtable<String, Integer> key_vlaue_baseTab = new Hashtable<String, Integer>();
			key_vlaue_baseTab.put("groupNo", 8);
			key_vlaue_baseTab.put(basekey, getValueByChineseName(baseGreenSuit(polar), eq_attrib, eqType == 3, true));
			appraisalList.add(key_vlaue_baseTab);
		} else if (6 == appraisalType) {
			String keyName1 = "防御";
			String keyName2 = "防御";
			if (eqType == 1) {
				keyName1 = "物伤";
				keyName2 = "法伤";
			}
			Hashtable<String, Integer> key_vlaue_tab = new Hashtable<String, Integer>();
			key_vlaue_tab.put("groupNo", 10);
			key_vlaue_tab.put(getEquipmentKeyByName(keyName1),
					getMaxValueByChineseName(keyName1, eq_attrib, eqType == 3, true) * 2 / 12 * color);
			key_vlaue_tab.put(getEquipmentKeyByName(keyName2),
					getMaxValueByChineseName(keyName2, eq_attrib, eqType == 3, true) * 2 / 12 * color);
			appraisalList.add(key_vlaue_tab);
		} else if (7 == appraisalType) {
			Hashtable<String, Integer> key_vlaue_tab3 = new Hashtable<String, Integer>();
			key_vlaue_tab3.put("groupNo", 10);
			if ((eqType == 2 || eqType == 10 || eqType == 3) && eq_attrib >= 100) {
				key_vlaue_tab3.put(getEquipmentKeyByName("气血"),
						getMaxValueByChineseName("气血", eq_attrib, eqType == 3, true) * 2 / 12 * color);
			} else if (eqType == 1) {
				key_vlaue_tab3.put(getEquipmentKeyByName("所有属性"),
						getMaxValueByChineseName("所有属性", eq_attrib, eqType == 3, true) * 2 / 12 * color);
			}
			appraisalList.add(key_vlaue_tab3);
		}
		// 装备共鸣
		else if (8 == appraisalType) {
			List<String> greenAttribute2 = randomAttribute(randomGongMingAttr(), 1);
			Hashtable<String, Integer> key_vlaue_tab4 = new Hashtable<String, Integer>();
			key_vlaue_tab4.put("groupNo", 27);
			int maxValueByChineseName = getMaxValueByChineseName(greenAttribute2.get(0), eq_attrib, eqType == 3, true);
			int num = maxValueByChineseName * color / 4;
			String equipmentKeyByName = getEquipmentKeyByName(greenAttribute2.get(0));
			log.info("共鸣随机属性名:{}, 数值:{}, 最大值:{}",equipmentKeyByName, num, maxValueByChineseName);
			key_vlaue_tab4.put(equipmentKeyByName, num);
			appraisalList.add(key_vlaue_tab4);
		}
		return appraisalList;
	}
	
	public static String[] randomGongMingAttr() {
		String[] resonanceAttribute = { "物理必杀率", "物伤", "法伤", "法伤", "气血", "防御", "速度", "物伤", "力量", "灵力", "敏捷", "体质",
				"抗中毒", "抗冰冻", "抗昏睡", "抗遗忘", "抗混乱", "金抗性", "木抗性", "水抗性", "火抗性", "土抗性", "物理连击率", "反击率", "物理必杀率",
				"强力克金", "强力克木", "强力克水", "强力克火", "强力克土", "反击次数", "反震度", "反震率", "几率解除混乱状态", "几率解除昏睡状态", "几率解除冰冻状态",
				"几率解除中毒状态", "几率解除遗忘状态", "法攻技能消耗降低", "障碍技能消耗降低", "辅助技能消耗降低", "法术必杀率" };
		return resonanceAttribute;
	}

	// 随机从分属性里面选择炼化的属性
	public static List<String> randomAttribute(String[] pinkAttributes, int count) {
		int length = pinkAttributes.length;
		Random random = new Random();
		List<Integer> list = new ArrayList<Integer>();
		while (list.size() < count) {
			int i = random.nextInt(length);
			if (!list.contains(i)) {
				list.add(i);
			}
		}
		List<String> attributes = new ArrayList<String>();
		for (Integer index : list) {
			attributes.add(pinkAttributes[index]);
		}
		return attributes;
	}

	public static Hashtable<Integer, String[]> baseBlueSuit(int eqType, int eq_attrib) {
		String[] pinkAttribute_1 = { "力量", "灵力", "敏捷", "体质", "伤害_最低伤害", "所有技能上升", "所有相性", "金相性", "木相性", "水相性", "火相性",
				"土相性", "物理必杀率", "物理连击率", "反击率", "忽视所有抗异常", "忽视所有抗性" };
		String[] pinkAttribute_2 = { "力量", "灵力", "敏捷", "体质", "所有属性", "物理连击次数", "气血", "法力", "防御", "反震度", "反击次数" };
		String[] pinkAttribute_3 = { "力量", "灵力", "敏捷", "体质", "所有属性", "物理连击次数", "防御", "速度", "防御", "反震度", "反击次数" };
		String[] pinkAttribute_4 = { "力量", "灵力", "敏捷", "体质", "所有属性", "气血", "法力", "防御", "反震率", "反震度", "反击次数", "反击率",
				"抗中毒", "抗冰冻", "抗昏睡", "抗遗忘", "抗混乱", "金抗性", "木抗性", "水抗性", "火抗性", "土抗性", "所有抗异常", "所有抗性" };
		String[] pinkAttribute_5 = { "力量", "灵力", "敏捷", "体质", "所有相性", "抗中毒", "抗冰冻", "抗昏睡", "抗遗忘", "抗混乱", "所有抗异常",
				"所有技能上升" };
		String[] pinkAttribute_6 = { "力量", "灵力", "敏捷", "体质", "所有相性", "抗中毒", "抗冰冻", "抗昏睡", "抗遗忘", "抗混乱", "所有抗异常",
				"所有技能上升" };
		String[] pinkAttribute_7 = { "力量", "灵力", "敏捷", "体质", "所有相性", "所有技能上升", "忽视目标抗中毒", "忽视目标抗冰冻", "忽视目标抗昏睡",
				"忽视目标抗混乱", "忽视目标抗遗忘", "忽视目标抗金", "忽视目标抗木", "忽视目标抗水", "忽视目标抗火", "忽视目标抗土", "忽视所有抗异常" };
		Hashtable<Integer, String[]> hashtable = new Hashtable<Integer, String[]>();
		hashtable.put(1, pinkAttribute_1);
		hashtable.put(2, pinkAttribute_2);
		hashtable.put(10, pinkAttribute_3);
		hashtable.put(3, pinkAttribute_4);
		hashtable.put(4, pinkAttribute_5);
		hashtable.put(5, pinkAttribute_6);
		hashtable.put(6, pinkAttribute_7);
		return hashtable;
	}

	public void remakeBlueSuit(int eqType, int color, int eq_attrib) {
		if (eqType == 1) {
			if (color == 1) {
				getEquipmentKeyByName("物伤");
			} else if (color == 5) {
				getEquipmentKeyByName("所有属性");
			}
		} else if (color == 1) {
			getEquipmentKeyByName("防御");
		} else if (color == 5 && eq_attrib >= 100) {
			getEquipmentKeyByName("气血");
		}
	}

	public static String[] resonanceBlueSuit() {
		String[] resonanceAttribute = { "物理必杀率", "物伤", "法伤", "法伤", "气血", "防御", "速度", "物伤", "力量", "灵力", "敏捷", "体质",
				"抗中毒", "抗冰冻", "抗昏睡", "抗遗忘", "抗混乱", "金抗性", "木抗性", "水抗性", "火抗性", "土抗性", "所有技能上升", "物理连击率", "反击率", "物理必杀率",
				"强力克金", "强力克木", "强力克水", "强力克火", "强力克土", "反击次数", "反震度", "反震率", "几率解除混乱状态", "几率解除昏睡状态", "几率解除冰冻状态",
				"几率解除中毒状态", "几率解除遗忘状态", "法攻技能消耗降低", "障碍技能消耗降低", "辅助技能消耗降低", "法术必杀率" };
		return resonanceAttribute;
	}

	public static Hashtable<Integer, String[]> yellowSuit(int eqType, int eq_attrib) {
		String[] pinkAttribute_1 = { "力量", "灵力", "敏捷", "体质", "伤害_最低伤害", "所有技能上升", "所有相性", "金相性", "木相性", "水相性", "火相性",
				"土相性", "物理必杀率", "物理连击率", "反击率", "忽视所有抗异常", "忽视所有抗性" };
		String[] pinkAttribute_2 = { "力量", "灵力", "敏捷", "体质", "所有属性", "物理连击次数", "气血", "法力", "防御", "反震度", "反击次数" };
		String[] pinkAttribute_3 = { "力量", "灵力", "敏捷", "体质", "所有属性", "物理连击次数", "防御", "防御", "反震度", "反击次数", "速度" };
		String[] pinkAttribute_4 = { "力量", "灵力", "敏捷", "体质", "所有属性", "气血", "法力", "防御", "反震率", "反震度", "反击次数", "反击率",
				"抗中毒", "抗冰冻", "抗昏睡", "抗遗忘", "抗混乱", "金抗性", "木抗性", "水抗性", "火抗性", "土抗性", "所有抗异常", "所有抗性" };
		Hashtable<Integer, String[]> hashtable = new Hashtable<Integer, String[]>();
		hashtable.put(1, pinkAttribute_1);
		hashtable.put(2, pinkAttribute_2);
		hashtable.put(10, pinkAttribute_3);
		hashtable.put(3, pinkAttribute_4);
		return hashtable;
	}

	// 获取装备对应等级的粉属性
	public static Hashtable<Integer, String[]> pinkSuit(int eqType, int eq_attrib) {
		String[] pinkAttribute_1 = { "力量", "灵力", "敏捷", "体质", "伤害_最低伤害", "所有技能上升", "所有相性", "金相性", "木相性", "水相性", "火相性",
				"土相性", "物理必杀率", "物理连击率", "反击率", "忽视所有抗异常", "忽视所有抗性" };
		String[] pinkAttribute_2 = { "力量", "灵力", "敏捷", "体质", "所有属性", "物理连击次数", "气血", "法力", "防御", "反震度", "反击次数" };
		String[] pinkAttribute_3 = { "力量", "灵力", "敏捷", "体质", "所有属性", "物理连击次数", "防御", "速度", "防御", "反震度", "反击次数" };
		String[] pinkAttribute_4 = { "力量", "灵力", "敏捷", "体质", "所有属性", "气血", "法力", "防御", "反震率", "反震度", "反击次数", "反击率",
				"抗中毒", "抗冰冻", "抗昏睡", "抗遗忘", "抗混乱", "金抗性", "木抗性", "水抗性", "火抗性", "土抗性", "所有抗异常", "所有抗性" };
		Hashtable<Integer, String[]> hashtable = new Hashtable<Integer, String[]>();
		hashtable.put(1, pinkAttribute_1);
		hashtable.put(2, pinkAttribute_2);
		hashtable.put(10, pinkAttribute_3);
		hashtable.put(3, pinkAttribute_4);
		return hashtable;
	}

	public static String baseGreenSuit(int resist_polar) {
		String[] baseAttribute = { "法伤", "气血", "防御", "速度", "物伤" };
		return baseAttribute[resist_polar - 1];
	}

	public static Hashtable<Integer, String[]> randomGreenSuit(int resist_polar, int charaPolar) {
		String[] strList = { "强金法伤害", "强木法伤害", "强水法伤害", "强火法伤害", "强土法伤害"};
		String[] strList2 = { "强力遗忘", "强力中毒", "强力冰冻", "强力昏睡", "强力混乱" };
		String[] strList3 = { "忽视目标抗中毒", "忽视目标抗冰冻", "忽视目标抗昏睡", "忽视目标抗混乱", "忽视目标抗遗忘" };
		String[] strList4 = { "忽视目标抗金", "忽视目标抗木", "忽视目标抗水", "忽视目标抗火", "忽视目标抗土" };
		String[] randomAttribute = { "强力克金", "强力克木", "强力克水", "强力克火", "强力克土", strList[charaPolar-1], "强物理伤害",
				strList2[new Random().nextInt(5)], strList4[new Random().nextInt(5)], "几率解除混乱状态", "几率解除昏睡状态",
				"几率解除冰冻状态", "几率解除中毒状态", "几率解除遗忘状态", "忽视所有抗异常", "忽视所有抗性", strList3[resist_polar - 1],"忽视躲避攻击"};
		String[] randomAttributeNoEq = { "抗中毒", "抗冰冻", "抗昏睡", "抗遗忘", "抗混乱", "几率解除混乱状态", "几率解除昏睡状态", "几率解除冰冻状态",
				"几率解除中毒状态", "几率解除遗忘状态" };
		String[] randomAttributeNoH = { "法攻技能消耗降低", "障碍技能消耗降低", "辅助技能消耗降低" };
		String[] randomAttributeNoEq2 = { "几率躲避攻击", "法攻技能消耗降低", "障碍技能消耗降低", "辅助技能消耗降低" };
		Hashtable<Integer, String[]> hashtable = new Hashtable<Integer, String[]>();
		hashtable.put(1, randomAttribute);
		hashtable.put(2, randomAttributeNoH);
		hashtable.put(10, randomAttributeNoEq2);
		hashtable.put(3, randomAttributeNoEq);
		return hashtable;	
	}

	public static String getEquipmentKeyByName(String chineseName) {
		return getEquipmentKeyByName(chineseName, true);
	}

	/**
	 * 获取错位字段名
	 * @param chineseName 中文名
	 * @param isName      true 返回中文
	 * @return
	 */
	public static String getEquipmentKeyByName(String chineseName, boolean isName) {
		if(chineseName == null) {
			return null;
		}
		Hashtable<String, String> hashtable = new Hashtable<String, String>();
		hashtable.put("法伤", "mana");
		hashtable.put("气血", "def");
		hashtable.put("防御", "wiz");
		hashtable.put("速度", "parry");
		hashtable.put("物伤", "accurate");
		hashtable.put("法力", "dex");
		hashtable.put("抗中毒", "resist_frozen");
		hashtable.put("抗冰冻", "resist_sleep");
		hashtable.put("抗昏睡", "resist_forgotten");
		hashtable.put("抗遗忘", "resist_confusion");
		hashtable.put("抗混乱", "longevity");
		hashtable.put("金抗性", "resist_wood");
		hashtable.put("木抗性", "resist_water");
		hashtable.put("水抗性", "resist_fire");
		hashtable.put("火抗性", "resist_earth");
		hashtable.put("土抗性", "exp_to_next_level");
		hashtable.put("强力克金", "super_excluse_wood");
		hashtable.put("强力克木", "super_excluse_water");
		hashtable.put("强力克水", "super_excluse_fire");
		hashtable.put("强力克火", "super_excluse_earth");
		hashtable.put("强力克土", "B_skill_low_cost");
		hashtable.put("强金法伤害", "enhanced_wood");
		hashtable.put("强木法伤害", "enhanced_water");
		hashtable.put("强水法伤害", "enhanced_fire");
		hashtable.put("强火法伤害", "enhanced_earth");
		hashtable.put("强土法伤害", "mag_dodge");
		hashtable.put("强物理伤害", "ignore_mag_dodge");
		hashtable.put("几率躲避攻击", "jinguang_zhaxian_counter_att_rate");
		hashtable.put("忽视躲避攻击", "ignore_mag_dodge2");
		hashtable.put("法攻技能消耗降低", "C_skill_low_cost");
		hashtable.put("障碍技能消耗降低", "D_skill_low_cost");
		hashtable.put("辅助技能消耗降低", "super_poison");
		hashtable.put("忽视目标抗金", "ignore_resist_wood");
		hashtable.put("忽视目标抗木", "ignore_resist_water");
		hashtable.put("忽视目标抗水", "ignore_resist_fire");
		hashtable.put("忽视目标抗火", "ignore_resist_earth");
		hashtable.put("忽视目标抗土", "ignore_resist_forgotten");
		hashtable.put("伤害_最低伤害", "skill_low_cost");
		hashtable.put("金相性", "wood");
		hashtable.put("木相性", "water");
		hashtable.put("水相性", "fire");
		hashtable.put("火相性", "earth");
		hashtable.put("土相性", "resist_metal");
		hashtable.put("灵力", "mag_power");
		hashtable.put("力量", "phy_power");
		hashtable.put("敏捷", "speed");
		hashtable.put("体质", "life");
		hashtable.put("忽视所有抗性", "ignore_all_resist_except");
		hashtable.put("所有技能上升", "mstunt_rate");
		hashtable.put("忽视所有抗异常", "release_forgotten");
		hashtable.put("物理必杀率", "damage_sel");
		hashtable.put("物理连击率", "stunt_rate");
		hashtable.put("反击率", "double_hit_rate");
		hashtable.put("物理连击次数", "stunt");
		hashtable.put("反击次数", "life_recover");
		hashtable.put("反震率", "portrait");
		hashtable.put("反震度", "family");
		hashtable.put("所有抗性", "all_resist_except");
		hashtable.put("所有相性", "all_resist_polar");
		hashtable.put("所有属性", "all_polar");
		hashtable.put("所有抗异常", "all_skill");
		hashtable.put("几率解除混乱状态", "tao_ex");
		hashtable.put("几率解除昏睡状态", "release_confusion");
		hashtable.put("几率解除冰冻状态", "release_sleep");
		hashtable.put("几率解除中毒状态", "release_frozen");
		hashtable.put("几率解除遗忘状态", "release_poison");
		hashtable.put("忽视目标抗中毒", "ignore_resist_frozen");
		hashtable.put("忽视目标抗冰冻", "ignore_resist_sleep");
		hashtable.put("忽视目标抗昏睡", "ignore_resist_confusion");
		hashtable.put("忽视目标抗混乱", "super_excluse_metal");
		hashtable.put("忽视目标抗遗忘", "ignore_resist_poison");
		hashtable.put("强力遗忘", "super_confusion");
		hashtable.put("强力中毒", "super_sleep");
		hashtable.put("强力冰冻", "enhanced_metal");
		hashtable.put("强力昏睡", "super_forgotten");
		hashtable.put("强力混乱", "super_frozen");
		hashtable.put("法术必杀率", "mstunt_rate_225");
		if (isName) {
			if (!hashtable.containsKey(chineseName)) {
				return new StringBuffer(chineseName).append("中文 No Find").toString();
			}
			return hashtable.get(chineseName);
		} else {
			if (!hashtable.containsValue(chineseName)) {
				return new StringBuffer(chineseName).append("英文 No Find").toString();
			}
			String key = null;
			for (String getKey : hashtable.keySet()) {
				if (hashtable.get(getKey).equals(chineseName)) {
					key = getKey;
				}
			}
			return key;
		}
	}
	
	/**
	 * 获取错位字段名
	 * @param chineseName 中文名
	 * @param equipType 装备类型
	 * @param isName      key或者value
	 * @return
	 */
	public static String getEquipmentKeyByName(String chineseName, int equipType, boolean isName) {
		if(chineseName == null) {
			return null;
		}
		Hashtable<String, String> hashtable = new Hashtable<String, String>();
		hashtable.put("法伤", "mana");
		hashtable.put("气血", "def");
		hashtable.put("防御", "wiz");
		hashtable.put("速度", "parry");
		hashtable.put("物伤", "accurate");
		hashtable.put("法力", "dex");
		hashtable.put("抗中毒", "resist_frozen");
		hashtable.put("抗冰冻", "resist_sleep");
		hashtable.put("抗昏睡", "resist_forgotten");
		hashtable.put("抗遗忘", "resist_confusion");
		hashtable.put("抗混乱", "longevity");
		hashtable.put("金抗性", "resist_wood");
		hashtable.put("木抗性", "resist_water");
		hashtable.put("水抗性", "resist_fire");
		hashtable.put("火抗性", "resist_earth");
		hashtable.put("土抗性", "exp_to_next_level");
		hashtable.put("强力克金", "super_excluse_wood");
		hashtable.put("强力克木", "super_excluse_water");
		hashtable.put("强力克水", "super_excluse_fire");
		hashtable.put("强力克火", "super_excluse_earth");
		hashtable.put("强力克土", "B_skill_low_cost");
		hashtable.put("强金法伤害", "enhanced_wood");
		hashtable.put("强木法伤害", "enhanced_water");
		hashtable.put("强水法伤害", "enhanced_fire");
		hashtable.put("强火法伤害", "enhanced_earth");
		hashtable.put("强土法伤害", "mag_dodge");
		hashtable.put("强物理伤害", "ignore_mag_dodge");
		hashtable.put("几率躲避攻击", "jinguang_zhaxian_counter_att_rate");
		hashtable.put("忽视躲避攻击", "ignore_mag_dodge2");
		hashtable.put("法攻技能消耗降低", "C_skill_low_cost");
		hashtable.put("障碍技能消耗降低", "D_skill_low_cost");
		hashtable.put("辅助技能消耗降低", "super_poison");
		hashtable.put("忽视目标抗金", "ignore_resist_wood");
		hashtable.put("忽视目标抗木", "ignore_resist_water");
		hashtable.put("忽视目标抗水", "ignore_resist_fire");
		hashtable.put("忽视目标抗火", "ignore_resist_earth");
		hashtable.put("忽视目标抗土", "ignore_resist_forgotten");
		hashtable.put("伤害_最低伤害", "skill_low_cost");
		hashtable.put("金相性", "wood");
		hashtable.put("木相性", "water");
		hashtable.put("水相性", "fire");
		hashtable.put("火相性", "earth");
		hashtable.put("土相性", "resist_metal");
		hashtable.put("灵力", "mag_power");
		hashtable.put("力量", "phy_power");
		hashtable.put("敏捷", "speed");
		hashtable.put("体质", "life");
		hashtable.put("忽视所有抗性", "ignore_all_resist_except");
		hashtable.put("所有技能上升", "mstunt_rate");
		hashtable.put("忽视所有抗异常", "release_forgotten");
		hashtable.put("物理必杀率", "damage_sel");
		hashtable.put("物理连击率", "stunt_rate");
		hashtable.put("反击率", "double_hit_rate");
		hashtable.put("物理连击次数", "stunt");
		hashtable.put("反击次数", "life_recover");
		hashtable.put("反震率", "portrait");
		hashtable.put("反震度", "family");
		hashtable.put("所有抗性", "all_resist_except");
		hashtable.put("所有相性", "all_resist_polar");
		hashtable.put("所有属性", "all_polar");
		hashtable.put("所有抗异常", "all_skill");
		hashtable.put("几率解除混乱状态", "tao_ex");
		hashtable.put("几率解除昏睡状态", "release_confusion");
		hashtable.put("几率解除冰冻状态", "release_sleep");
		hashtable.put("几率解除中毒状态", "release_frozen");
		hashtable.put("几率解除遗忘状态", "release_poison");
		hashtable.put("忽视目标抗中毒", "ignore_resist_frozen");
		hashtable.put("忽视目标抗冰冻", "ignore_resist_sleep");
		hashtable.put("忽视目标抗昏睡", "ignore_resist_confusion");
		hashtable.put("忽视目标抗混乱", "super_excluse_metal");
		hashtable.put("忽视目标抗遗忘", "ignore_resist_poison");
		hashtable.put("强力遗忘", "super_confusion");
		hashtable.put("强力中毒", "super_sleep");
		hashtable.put("强力冰冻", "enhanced_metal");
		hashtable.put("强力昏睡", "super_forgotten");
		hashtable.put("强力混乱", "super_frozen");
		hashtable.put("法术必杀率", "mstunt_rate_225");
		if (isName) {
			if (!hashtable.containsKey(chineseName)) {
				return new StringBuffer(chineseName).append("中文 No Find").toString();
			}
			return hashtable.get(chineseName);
		} else {
			if (!hashtable.containsValue(chineseName)) {
				return new StringBuffer(chineseName).append("英文 No Find").toString();
			}
			String key = null;
			for (String getKey : hashtable.keySet()) {
				if (hashtable.get(getKey).equals(chineseName)) {
					key = getKey;
				}
			}
			return key;
		}
	}

	/**
	 * 获取首饰或者是装备字段
	 * 
	 * @param name     名字
	 * @param equiType 类型
	 * @param isKey    是否为key
	 * @return
	 */
	public static Hashtable<String, String> getShouShiFields(int equiType) {
		Hashtable<String, String> hashtable = new Hashtable<String, String>();
		if (equiType == 6) {
			hashtable.put("力量", "phy_power");
			hashtable.put("体质", "life");
			hashtable.put("灵力", "mag_power");
			hashtable.put("敏捷", "speed");
			hashtable.put("所有相性", "all_resist_polar");
			hashtable.put("所有技能上升", "mstunt_rate");
			hashtable.put("忽视目标抗金", "ignore_resist_wood");
			hashtable.put("忽视目标抗木", "ignore_resist_water");
			hashtable.put("忽视目标抗水", "ignore_resist_fire");
			hashtable.put("忽视目标抗火", "ignore_resist_earth");
			hashtable.put("忽视目标抗土", "ignore_resist_forgotten");
			hashtable.put("忽视目标抗中毒", "ignore_resist_frozen");
			hashtable.put("忽视目标抗冰冻", "ignore_resist_sleep");
			hashtable.put("忽视目标抗昏睡", "ignore_resist_confusion");
			hashtable.put("忽视目标抗混乱", "super_excluse_metal");
			hashtable.put("忽视目标抗遗忘", "ignore_resist_poison");
			hashtable.put("忽视所有抗异常", "release_forgotten");
			hashtable.put("所有技能上升", "mstunt_rate");
		} else if (equiType == 5 || equiType == 4) {
			// 玉佩、项链
			hashtable.put("力量", "phy_power");
			hashtable.put("体质", "life");
			hashtable.put("灵力", "mag_power");
			hashtable.put("敏捷", "speed");
			hashtable.put("金抗性", "resist_wood");
			hashtable.put("木抗性", "resist_water");
			hashtable.put("水抗性", "resist_fire");
			hashtable.put("火抗性", "resist_earth");
			hashtable.put("土抗性", "exp_to_next_level");
			hashtable.put("抗遗忘", "resist_confusion");
			hashtable.put("抗中毒", "resist_frozen");
			hashtable.put("抗冰冻", "resist_sleep");
			hashtable.put("抗昏睡", "resist_forgotten");
			hashtable.put("抗混乱", "longevity");
			hashtable.put("所有技能上升", "mstunt_rate");
			hashtable.put("所有抗异常", "all_skill");
			hashtable.put("所有相性", "all_resist_polar");
		}
		return hashtable;
	}

	/**
	 * 根据名字获取字段中文名
	 * 
	 * @param equiType
	 * @param name     英文字段
	 * @return
	 */
	public static String getShouShiRandomFieldByChinese(String name, int equiType) {
		// key:中文 value:英文
		Hashtable<String, String> shouShiFields = getShouShiFields(equiType);
		String valueName = null;
		for (Entry<String, String> value : shouShiFields.entrySet()) {
			if (name.equals(value.getValue())) {
				valueName = value.getKey();
				break;
			}
		}
		return valueName;
	}

	/**
	 * 根据名字获取字段中文名
	 * 
	 * @param equiType
	 * @param name     英文字段
	 * @return
	 */
	public static Map<String, String> getShouShiRandomFieldByChineseToMap(String name, int equiType) {
		// key:中文 value:英文
		Hashtable<String, String> shouShiFields = getShouShiFields(equiType);
		Map<String, String> map = null;
		for (Entry<String, String> value : shouShiFields.entrySet()) {
			if (name.equals(value.getValue())) {
				map = new HashMap<>();
				map.put("ch", value.getKey());
				map.put("en", value.getValue());
				break;
			}
		}
		return map;
	}

	/**
	 * 根据正确的key获取错位的key，去他妈的
	 * 
	 * @param key 正确的key
	 * @return isGetKey:官方版本正确位置， value错误位置
	 */
	public static String getErrorFieldByOriginField(String key, boolean isGetKey) {
		Hashtable<String, String> hashtable = new Hashtable<String, String>();
		hashtable.put("mag_power", "mana");
		hashtable.put("max_life", "def");
		hashtable.put("def", "wiz");
		hashtable.put("speed", "parry");
		hashtable.put("phy_power", "accurate");
		hashtable.put("max_mana", "dex");
		hashtable.put("resist_poison", "resist_frozen");
		hashtable.put("resist_frozen", "resist_sleep");
		hashtable.put("resist_sleep", "resist_forgotten");
		hashtable.put("resist_forgotten", "resist_confusion");
		hashtable.put("resist_confusion", "longevity");
		hashtable.put("resist_metal", "resist_wood");
		hashtable.put("resist_wood", "resist_water");
		hashtable.put("resist_water", "resist_fire");
		hashtable.put("resist_fire", "resist_earth");
		hashtable.put("resist_earth", "exp_to_next_level");
		hashtable.put("super_excluse_metal", "super_excluse_wood");
		hashtable.put("super_excluse_wood", "super_excluse_water");
		hashtable.put("super_excluse_water", "super_excluse_fire");
		hashtable.put("super_excluse_fire", "super_excluse_earth");
		hashtable.put("super_excluse_earth", "B_skill_low_cost");
		hashtable.put("enhanced_metal", "enhanced_wood");
		hashtable.put("enhanced_wood", "enhanced_water");
		hashtable.put("enhanced_water", "enhanced_fire");
		hashtable.put("enhanced_fire", "enhanced_earth");
		hashtable.put("enhanced_earth", "mag_dodge");
		hashtable.put("enhanced_phy", "ignore_mag_dodge");
		hashtable.put("mag_dodge", "jinguang_zhaxian_counter_att_rate");
		hashtable.put("ignore_mag_dodge", "ignore_mag_dodge2");
		hashtable.put("B_skill_low_cost", "C_skill_low_cost");
		hashtable.put("C_skill_low_cost", "D_skill_low_cost");
		hashtable.put("D_skill_low_cost", "super_poison");
		hashtable.put("ignore_resist_metal", "ignore_resist_wood");
		hashtable.put("ignore_resist_wood", "ignore_resist_water");
		hashtable.put("ignore_resist_water", "ignore_resist_fire");
		hashtable.put("ignore_resist_fire", "ignore_resist_earth");
		hashtable.put("ignore_resist_earth", "ignore_resist_forgotten");
		hashtable.put("power", "skill_low_cost");
		hashtable.put("metal", "wood");
		hashtable.put("wood", "water");
		hashtable.put("water", "fire");
		hashtable.put("fire", "earth");
		hashtable.put("earth", "resist_metal");
		hashtable.put("wiz", "mag_power");
		hashtable.put("str", "phy_power");
		hashtable.put("dex", "speed");
		hashtable.put("con", "life");
		hashtable.put("ignore_all_resist_polar", "ignore_all_resist_except");
		hashtable.put("all_skill", "mstunt_rate");
		hashtable.put("ignore_all_resist_except", "release_forgotten");
		hashtable.put("stunt_rate", "damage_sel");
		hashtable.put("double_hit_rate", "stunt_rate");
		hashtable.put("counter_attack_rate", "double_hit_rate");
		hashtable.put("double_hit", "stunt");
		hashtable.put("counter_attack", "life_recover");
		hashtable.put("damage_sel_rate", "portrait");
		hashtable.put("damage_sel", "family");
		hashtable.put("all_resist_polar", "all_resist_except");
		hashtable.put("all_polar", "all_resist_polar");
		hashtable.put("all_resist_except", "all_skill");
		hashtable.put("release_confusion", "tao_ex");
		hashtable.put("release_sleep", "release_confusion");
		hashtable.put("release_frozen", "release_sleep");
		hashtable.put("release_poison", "release_frozen");
		hashtable.put("release_forgotten", "release_poison");
		hashtable.put("ignore_resist_poison", "ignore_resist_frozen");
		hashtable.put("ignore_resist_frozen", "ignore_resist_sleep");
		hashtable.put("ignore_resist_sleep", "ignore_resist_confusion");
		hashtable.put("ignore_resist_confusion", "super_excluse_metal");
		hashtable.put("ignore_resist_forgotten", "ignore_resist_poison");
		hashtable.put("super_forgotten", "super_confusion");
		hashtable.put("super_poison", "super_sleep");
		hashtable.put("super_frozen", "enhanced_metal");
		hashtable.put("super_sleep", "super_forgotten");
		hashtable.put("super_confusion", "super_frozen");
		hashtable.put("mstunt_rate_225", "mstunt_rate");
		hashtable.put("all_attrib", "all_polar");
		if (isGetKey) {
			String keyName = null;
			for (String getKey : hashtable.keySet()) {
				if (getKey.equals(key)) {
					keyName = getKey;
					break;
				}
			}
			return keyName;

		} else {
			return hashtable.get(key);
		}
	}

	private static boolean contains(String[] values, String name) {
		List<String> resultList = new ArrayList<String>(values.length);
		Collections.addAll(resultList, values);
		return resultList.contains(name);
	}

	public static int peekValue(int skill) {
		if (skill < 10) {
			return skill / 2;
		}
		if (skill < 50) {
			return (int) (skill / 2.4);
		}
		Random random = new Random();
		if (skill < 100) {
			int skillCount = skill / 10;
			int r = random.nextInt(skillCount);
			if (r < skillCount / 2) {
				r = skillCount / 2;
			}
			return r * 10 + random.nextInt(9);
		}
		if (skill <= 1000) {
			int skillCount = skill / 100;
			int r = random.nextInt(skillCount);
			if (r < skillCount / 2) {
				r = skillCount / 2;
			}
			return r * 100 + random.nextInt(9) * 10;
		}
		int skillCount = skill / 100;
		int r = random.nextInt(skillCount);
		if (r < skillCount / 2) {
			r = skillCount / 2;
		}
		return r * 100 + random.nextInt(9) * 10;
	}

	// 生成技能对应的伤害
	private static int getValueByChineseName(String name, int eq_attrib, boolean isClothes, boolean green) {
		if ("伤害_最低伤害".contentEquals(name)) {
			int skill = maxSkill_Low_Cost(eq_attrib);
			return peekValue(skill);
		}
		if ("物伤#气血#法力#速度#法伤#防御".contains(name)) {
			int skill = getMaxValueGiven(name, eq_attrib, isClothes, green);
			int peekValue = peekValue(skill);
			return peekValue == 0 ? new Random().nextInt(30) + 1 : peekValue;
		}
		if ("所有属性".contentEquals(name)) {
			return getProbabilityValue(1, eq_attrib / 5);
		}
		String[] valuel_t = { "力量", "灵力", "敏捷", "体质" };
		if (contains(valuel_t, name)) {
			if (maxSpeed(eq_attrib) <= 2) {
				return maxSpeed(eq_attrib);
			}
			return getProbabilityValue(maxSpeed(eq_attrib) / 2, maxSpeed(eq_attrib));
		} else {
			String[] value10_30 = { "几率躲避攻击", "忽视目标抗金", "忽视目标抗木", "忽视目标抗水", "忽视目标抗火", "忽视目标抗土", "物理必杀率", "物理连击率", "反击率",
					"反震率", "反震度", "忽视目标抗中毒", "忽视目标抗冰冻", "忽视目标抗昏睡", "忽视目标抗混乱", "忽视目标抗遗忘", "强力遗忘", "强力中毒", "强力冰冻", "强力昏睡",
					"强力混乱"};
			if (contains(value10_30, name)) {
				return getProbabilityValue(10, 30);
			}
			String[] value5_20 = { "抗中毒", "抗冰冻", "抗昏睡", "抗遗忘", "抗混乱", "强力克金", "强力克木", "强力克水", "强力克火", "强力克土",
					"忽视所有抗异常" };
			if (contains(value5_20, name)) {
				return getProbabilityValue(5, 20);
			}
			String[] value5_21 = { "金抗性", "木抗性", "水抗性", "火抗性", "土抗性", "所有抗异常", "几率解除混乱状态", "几率解除昏睡状态", "几率解除冰冻状态",
					"几率解除中毒状态", "几率解除遗忘状态","忽视躲避攻击"};
			if (contains(value5_21, name)) {
				return getProbabilityValue(5, 15);
			}
			
			String[] value3_10 = { "强金法伤害", "强木法伤害", "强水法伤害", "强火法伤害", "强土法伤害", "强物理伤害", "法攻技能消耗降低", "障碍技能消耗降低",
					"辅助技能消耗降低", "忽视所有抗性", "所有抗性", "反击次数" };
			if (contains(value3_10, name)) {
				return getProbabilityValue(3, 10);
			}
			String[] value1_5 = { "所有相性", "金相性", "木相性", "水相性", "火相性", "土相性" };
			if (contains(value1_5, name)) {
				return getProbabilityValue(1, 5);
			}
			String[] value1_6 = { "所有技能上升" };
			if (contains(value1_6, name)) {
				return getProbabilityValue(2, 10);
			}
			String[] value1_7 = { "物理连击次数" };
			if (contains(value1_7, name)) {
				return getProbabilityValue(2, 12);
			}
			return 100;
		}
	}

	public static int getMaxValueGiven(String name, int eq_attrib, boolean clothes, boolean green) {
		String[] bv = { "速度", "气血", "法力", "防御" };
		String[] gv = { "物伤", "气血", "速度", "法伤", "防御" };
		int point = 0;
		if (green) {
			for (int i = 0; i < gv.length; ++i) {
				if (gv[i].contentEquals(name)) {
					point = i + 4;
					break;
				}
			}
		} else {
			for (int i = 0; i < bv.length; ++i) {
				if (bv[i].contentEquals(name)) {
					point = i;
					break;
				}
			}
		}
		int add = 0;
		if (eq_attrib % 10 != 0) {
			add = 10;
		}
		if (eq_attrib < 20) {
			int[] bgv = { 13, 45, 80, 15, 49, 211, 8, 28, 45 };
			int[] bgv_yf = { 0, 85, 140, 30, 0, 0, 0, 0, 0 };
			if (clothes) {
				bgv[1] = bgv_yf[1];
				bgv[2] = bgv_yf[2];
				bgv[3] = bgv_yf[3];
			}
			return bgv[point] + add;
		}
		if (eq_attrib >= 20 && eq_attrib < 30) {
			int[] bgv = { 26, 110, 180, 35, 113, 478, 16, 63, 102 };
			int[] bgv_yf = { 0, 190, 320, 65, 0, 0, 0, 0, 0 };
			if (clothes) {
				bgv[1] = bgv_yf[1];
				bgv[2] = bgv_yf[2];
				bgv[3] = bgv_yf[3];
			}
			return bgv[point] + add;
		}
		if (eq_attrib >= 30 && eq_attrib < 40) {
			int[] bgv = { 40, 190, 300, 55, 191, 806, 24, 107, 173 };
			int[] bgv_yf = { 0, 320, 500, 110, 0, 0, 0, 0, 0 };
			if (clothes) {
				bgv[1] = bgv_yf[1];
				bgv[2] = bgv_yf[2];
				bgv[3] = bgv_yf[3];
			}
			return bgv[point] + add;
		}
		if (eq_attrib >= 40 && eq_attrib < 50) {
			int[] bgv = { 50, 280, 460, 85, 191, 806, 24, 107, 173 };
			int[] bgv_yf = { 0, 480, 800, 170, 0, 0, 0, 0, 0 };
			if (clothes) {
				bgv[1] = bgv_yf[1];
				bgv[2] = bgv_yf[2];
				bgv[3] = bgv_yf[3];
			}
			return bgv[point] + add;
		}
		if (eq_attrib >= 50 && eq_attrib < 60) {
			int[] bgv = { 65, 380, 600, 120, 389, 1642, 41, 218, 354 };
			int[] bgv_yf = { 0, 650, 1100, 240, 0, 0, 0, 0, 0 };
			if (clothes) {
				bgv[1] = bgv_yf[1];
				bgv[2] = bgv_yf[2];
				bgv[3] = bgv_yf[3];
			}
			return bgv[point] + add;
		}
		if (eq_attrib >= 60 && eq_attrib < 70) {
			int[] bgv = { 80, 500, 800, 150, 509, 2149, 49, 286, 464 };
			int[] bgv_yf = { 0, 850, 1400, 300, 0, 0, 0, 0, 0 };
			if (clothes) {
				bgv[1] = bgv_yf[1];
				bgv[2] = bgv_yf[2];
				bgv[3] = bgv_yf[3];
			}
			return bgv[point] + add;
		}
		if (eq_attrib >= 70 && eq_attrib < 80) {
			int[] bgv = { 90, 600, 1000, 190, 644, 2717, 57, 362, 586 };
			int[] bgv_yf = { 0, 1100, 1800, 380, 0, 0, 0, 0, 0 };
			if (clothes) {
				bgv[1] = bgv_yf[1];
				bgv[2] = bgv_yf[2];
				bgv[3] = bgv_yf[3];
			}
			return bgv[point] + add;
		}
		if (eq_attrib >= 80 && eq_attrib < 90) {
			int[] bgv = { 100, 750, 1200, 240, 792, 3345, 65, 446, 722 };
			int[] bgv_yf = { 0, 1300, 2200, 480, 0, 0, 0, 0, 0 };
			if (clothes) {
				bgv[1] = bgv_yf[1];
				bgv[2] = bgv_yf[2];
				bgv[3] = bgv_yf[3];
			}
			return bgv[point] + add;
		}
		if (eq_attrib >= 90 && eq_attrib < 100) {
			int[] bgv = { 120, 950, 1500, 280, 956, 4033, 73, 537, 871 };
			int[] bgv_yf = { 0, 1600, 2600, 550, 0, 0, 0, 0, 0 };
			if (clothes) {
				bgv[1] = bgv_yf[1];
				bgv[2] = bgv_yf[2];
				bgv[3] = bgv_yf[3];
			}
			return bgv[point] + add;
		}
		if (eq_attrib >= 100 && eq_attrib < 110) {
			int[] bgv = { 130, 1300, 4400, 440, 1133, 4781, 82, 637, 1032 };
			int[] bgv_yf = { 0, 2400, 7500, 850, 0, 0, 0, 0, 0 };
			if (clothes) {
				bgv[1] = bgv_yf[1];
				bgv[2] = bgv_yf[2];
				bgv[3] = bgv_yf[3];
			}
			return bgv[point] + add;
		}
		if (eq_attrib >= 110 && eq_attrib < 120) {
			int[] bgv = { 140, 1600, 5000, 500, 1324, 5589, 90, 745, 1207 };
			int[] bgv_yf = { 0, 2800, 9000, 1000, 0, 0, 0, 0, 0 };
			if (clothes) {
				bgv[1] = bgv_yf[1];
				bgv[2] = bgv_yf[2];
				bgv[3] = bgv_yf[3];
			}
			return bgv[point] + add;
		}
		if (eq_attrib >= 120 && eq_attrib < 130) {
			int[] bgv = { 160, 1800, 5500, 550, 1530, 6457, 98, 861, 1395 };
			int[] bgv_yf = { 0, 3200, 10000, 1100, 0, 0, 0, 0, 0 };
			if (clothes) {
				bgv[1] = bgv_yf[1];
				bgv[2] = bgv_yf[2];
				bgv[3] = bgv_yf[3];
			}
			return bgv[point] + add;
		}
		if (eq_attrib >= 130) {
			int[] bgv = { 170, 2000, 6500, 650, 1750, 7386, 106, 984, 1595 };
			int[] bgv_yf = { 0, 3800, 11000, 1200, 0, 0, 0, 0, 0 };
			if (clothes) {
				bgv[1] = bgv_yf[1];
				bgv[2] = bgv_yf[2];
				bgv[3] = bgv_yf[3];
			}
			return bgv[point] + add;
		}
		return 100;
	}

	public static int getMaxValueByChineseName(String name, int eq_attrib, boolean isClothes) {
		return getMaxValueByChineseName(name, eq_attrib, isClothes, false);
	}

	public static int getMaxValueByChineseName(String name, int eq_attrib, boolean isClothes, boolean green) {
		if ("伤害_最低伤害".contentEquals(name)) {
			return maxSkill_Low_Cost(eq_attrib);
		}
		if ("物伤#气血#法力#速度#法伤#防御".contains(name)) {
			return getMaxValueGiven(name, eq_attrib, isClothes, green);
		}
		if ("所有属性".contentEquals(name)) {
			return eq_attrib / 5;
		}
		String[] valuel_t = { "力量", "灵力", "敏捷", "体质" };
		if (contains(valuel_t, name)) {
			return maxSpeed(eq_attrib);
		}
		String[] value10_30 = { "几率躲避攻击", "忽视目标抗金", "忽视目标抗木", "忽视目标抗水", "忽视目标抗火", "忽视目标抗土", "物理必杀率", "物理连击率", "反击率",
				"反震率", "反震度", "忽视目标抗中毒", "忽视目标抗冰冻", "忽视目标抗昏睡", "忽视目标抗混乱", "忽视目标抗遗忘", "强力遗忘", "强力中毒", "强力冰冻", "强力昏睡",
				"强力混乱", "法术必杀率" };
		if (contains(value10_30, name)) {
			return 30;
		}
		String[] value5_20 = { "抗中毒", "抗冰冻", "抗昏睡", "抗遗忘", "抗混乱", "强力克金", "强力克木", "强力克水", "强力克火", "强力克土", "忽视所有抗异常" };
		if (contains(value5_20, name)) {
			return 20;
		}
		String[] value5_21 = { "金抗性", "木抗性", "水抗性", "火抗性", "土抗性", "所有抗异常", "几率解除混乱状态", "几率解除昏睡状态", "几率解除冰冻状态",
				"几率解除中毒状态", "几率解除遗忘状态" };
		if (contains(value5_21, name)) {
			return 15;
		}
		String[] value3_10 = { "强金法伤害", "强木法伤害", "强水法伤害", "强火法伤害", "强土法伤害", "强物理伤害", "法攻技能消耗降低", "障碍技能消耗降低", "辅助技能消耗降低",
				"忽视所有抗性", "所有抗性", "反击次数" };
		if (contains(value3_10, name)) {
			return 10;
		}
		String[] value1_5 = { "所有相性", "金相性", "木相性", "水相性", "火相性", "土相性", "所有属性" };
		if (contains(value1_5, name)) {
			return 5;
		}
		String[] value1_6 = { "所有技能上升" };
		if (contains(value1_6, name)) {
			return 10;
		}
		String[] value1_7 = { "物理连击次数" };
		if (contains(value1_7, name)) {
			return 12;
		}
		return 100;
	}

	public static int getProbabilityValue(int min, int max) {
		List<Integer> separates = new ArrayList<Integer>();
		separates.add((max + min) / 2);
		separates.add(max - 1);
		List<Integer> percents = new ArrayList<Integer>();
		percents.add(30);
		percents.add(68);
		percents.add(2);
		int number = RateRandomNumber.produceRateRandomNumber(min, max, separates, percents);
		return number;
	}

	/**
	 * a获取最大伤害
	 * @param eq_attrib
	 * @return
	 */
	public static int maxSkill_Low_Cost(int eq_attrib) {
		JSONObject power = GameConfig.equipAttchMax.getJSONObject("power");
		JSONObject jsonObject = power.getJSONObject("1");
		int maxValue = jsonObject.getIntValue(String.valueOf(eq_attrib));
		return maxValue;
	}

	public static int maxSpeed(int eq_attrib) {
		return eq_attrib / 4;
	}

	public static int maxDex(int eq_attrib) {
		return 100;
	}

	public static int maxDefWiz(int eq_attrib) {
		return 100;
	}

	public Hashtable<String, String> demonStoneSynthesis(int type) {
		int skill = type % 10;
		Hashtable<String, String> hashtable = new Hashtable<String, String>();
		int silver_coin = 3000 + skill * 1000;
		int def = skill * skill * 100;
		int parry = skill * 32;
		int wiz = skill * skill * 30;
		int accurate = skill * skill * 66;
		int dex = skill * skill * 66;
		int[] manas = { 0, 43, 174, 392, 696, 1089, 1568, 2134, 2787, 3528 };
		if (skill > 9) {
			skill = 9;
		}
		int mana = manas[skill];
		hashtable.put("silver_coin", String.valueOf(silver_coin));
		hashtable.put("skill", String.valueOf(skill));
		hashtable.put("", String.valueOf(skill));
		int swType = type / 10;
		switch (swType) {
		case 10: {
			hashtable.put("def", String.valueOf(def));
			hashtable.put("str", "凝香幻彩");
			break;
		}
		case 12: {
			hashtable.put("parry", String.valueOf(parry));
			hashtable.put("str", "炫影霜星");
			break;
		}
		case 14: {
			hashtable.put("wiz", String.valueOf(wiz));
			hashtable.put("str", "风寂云清");
			break;
		}
		case 16: {
			hashtable.put("accurate", String.valueOf(accurate));
			hashtable.put("str", "枯月流魂");
			break;
		}
		case 18: {
			hashtable.put("mana", String.valueOf(mana));
			hashtable.put("str", "雷极弧光");
			break;
		}
		case 20: {
			hashtable.put("dex", String.valueOf(dex));
			hashtable.put("str", "冰落残阳");
			break;
		}
		}
		return hashtable;
	}

	public static int demonStoneValue(int type) {
		int skill = type % 10;
		int def = skill * skill * 100;
		int parry = skill * 32;
		int wiz = skill * skill * 30;
		int accurate = skill * skill * 66;
		int dex = skill * skill * 66;
		int[] manas = { 0, 43, 174, 392, 696, 1089, 1568, 2134, 2787, 3528 };
		if (skill > 9) {
			skill = 9;
		}
		int mana = manas[skill];
		int swType = type / 10;
		switch (swType) {
		case 10: {
			return def;
		}
		case 12: {
			return parry;
		}
		case 14: {
			return wiz;
		}
		case 16: {
			return accurate;
		}
		case 18: {
			return mana;
		}
		case 20: {
			return dex;
		}
		default: {
			return 100;
		}
		}
	}

	/**
	 * 随机3条蓝色属性
	 * 
	 * @param goods
	 * @return
	 */
	public static GoodsLanSe randomCount3BlueAttribute(Goods goods) {
		List<Hashtable<String, Integer>> appraisalEquipment = ForgingEquipmentUtils
				.appraisalEquipment(goods.goodsInfo.amount, goods.goodsInfo.attrib, 1);
		java.util.Map<Object, Object> goodsLanSe8 = UtilObjMapshuxing.GoodsLanSe(goods.goodsLanSe);
		for (Hashtable<String, Integer> app : appraisalEquipment) {
			int groupNo2 = app.get("groupNo");
			int groupNolanse2 = (int) goodsLanSe8.get("groupNo");
			if (groupNolanse2 == groupNo2) {
				for (Map.Entry<String, Integer> entry8 : app.entrySet()) {
					goodsLanSe8.put(entry8.getKey(), entry8.getValue());
				}
			}
		}
		return JSONObject.parseObject(JSONObject.toJSONString(goodsLanSe8), GoodsLanSe.class);
	}
	
	/**
	 * 重新构建改造信息包含共鸣
	 * @param equip
	 */
	public static void rebuildLevelInfo(Goods equip) {
		String filedName = null;
		for (Field field : equip.goodsGaiZaoGongMing.getClass().getFields()) {
			if (!field.getName().equals("groupNo")) {
				if (field.getName().equals("groupType")) {
					continue;
				}
				try {
					if (field.get(equip.goodsGaiZaoGongMing).equals(0)) {
						continue;
					}
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
				filedName = field.getName();
				break;
			}
		}
		if(filedName != null) {
			//改造共鸣中文名
			String equipmentKeyByName = ForgingEquipmentUtils.getEquipmentKeyByName(filedName, false);
			List<Hashtable<String, Integer>> hashtables2 = ForgingEquipmentUtils
					.appraisalRemakeEquipment(equipmentKeyByName, equip.goodsInfo.amount,
							equip.goodsInfo.attrib, equip.goodsInfo.color);
			for (Hashtable<String, Integer> maps : hashtables2) {
				if (equipmentKeyByName != null) {
					if (maps.get("groupNo") == 27) {
						maps.put("groupType", 2);
						GoodsGaiZaoGongMing goodsGaiZaoGongMing2 = JSONObject
								.parseObject(JSONObject.toJSONString(maps), GoodsGaiZaoGongMing.class);
						equip.goodsGaiZaoGongMing = goodsGaiZaoGongMing2;
					}
				}
				if (maps.get("groupNo") == 10) {
					maps.put("groupType", 2);
					GoodsGaiZao goodsGaiZao = JSONObject.parseObject(JSONObject.toJSONString(maps),
							GoodsGaiZao.class);
					equip.goodsGaiZao = goodsGaiZao;
				}
			}
		}
	}

	public static void main(String[] args) {
	}
}