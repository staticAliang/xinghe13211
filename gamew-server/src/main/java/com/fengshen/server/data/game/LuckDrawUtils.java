package com.fengshen.server.data.game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;
import java.util.Random;

import com.alibaba.fastjson.JSONObject;
import com.fengshen.core.util.Utils;
import com.fengshen.db.domain.Choujiang;
import com.fengshen.db.domain.ConfigInfo;
import com.fengshen.db.domain.LuckDrawItem;
import com.fengshen.db.domain.Pet;
import com.fengshen.db.domain.StoreInfo;
import com.fengshen.db.domain.ZhuangbeiInfo;
import com.fengshen.server.data.write.pet.MSG_UPDATE_PETS;
import com.fengshen.server.domain.Chara;
import com.fengshen.server.domain.GoodsLanSe;
import com.fengshen.server.domain.PetShuXing;
import com.fengshen.server.domain.Petbeibao;
import com.fengshen.server.domain.config.ChoujiangConfig;
import com.fengshen.server.game.GameCommonUtil;
import com.fengshen.server.game.GameData;
import com.fengshen.server.game.GameObjectChar;
import com.fengshen.server.game.GameUtil;
import com.fengshen.server.process.chat.CMD_CHAT_EX;
import com.fengshen.server.process.system.DrawApi;
import com.fengshen.server.util.GameConfig;
import com.mysql.jdbc.StringUtils;

import io.netty.util.internal.ThreadLocalRandom;
import tk.mybatis.mapper.entity.Example;

public class LuckDrawUtils {
	public static void main(String[] args) {

		String awardStr = "#I首饰|七星手链$指定$35#I";
		awardStr = awardStr.substring(2, awardStr.length() - 2);
		String[] award = awardStr.split("\\|");
		System.out.println(Arrays.toString(award[1].split("\\$")));
	}

	// 设置抽奖的奖品列表
	public static String[] luckDraw(boolean isSenior) {
		//0:名字 1:类型 2:描述 4等级
		String[] result = null;
		// 根据几率选出几等奖
		ChoujiangConfig config = GameConfig.choujiangConfig;
		int baseNumber = config.getBaseNumber();
		// 如果是小额的话难度会提升10000倍,大额的话.就保持默认
		if (!isSenior) {
			baseNumber = baseNumber * 100;
		}
		double randomLevel = ThreadLocalRandom.current().nextDouble(baseNumber == 0 ? 100 : baseNumber);
		int level = -1;
		if (randomLevel < config.getNo0()) {
			level = 0;
		} else if (randomLevel < config.getNo1()) {
			level = 1;
		} else if (randomLevel < config.getNo2()) {
			level = 2;
		} else {
			// 大额抽奖最低3等奖
			if (isSenior) {
				level = 3;
			} else {
				// 如果是大额抽奖,则不会出现
				if (randomLevel < config.getNo3()) {
					level = 3;
				} else {
					level = 4;
				}
			}
		}
		// 查询出当前等级的数量
		List<Choujiang> findByLevel = GameData.that.baseChoujiangService.findByLevel(level);
		// 随机取出一条数据
		Choujiang choujiang = findByLevel.get(ThreadLocalRandom.current().nextInt(findByLevel.size()));
		String awardStr = choujiang.getDesc();
		awardStr = awardStr.substring(2, awardStr.length() - 2);
		String[] award = awardStr.split("\\|");
		if ("物品".equals(award[0])) {
			String item = award[1];
			String value = award[1].split("#r")[1];
			String[] split = item.split("#");
			result = new String[] { split[0], "物品", choujiang.getDesc(), String.valueOf(choujiang.getLevel()),value};
		}else if ("宠物".equals(award[0])) {
			String nameAndType = award[1].split("\\$")[0];
			String[] str = nameAndType.split("\\(");
			String name = str[0]; // 宠物名字
			String petType = str[1].replace(")", ""); // 宠物名字
			result = new String[] { name, petType, choujiang.getDesc(), String.valueOf(choujiang.getLevel()) };
		}else if ("首饰".equals(award[0])) {
			// #I首饰|七星手链$指定$35#I
			result = new String[] { award[1], "首饰", choujiang.getDesc(), String.valueOf(choujiang.getLevel()) };
		}else if ("装备".equals(award[0])) {
			String[] equipType = award[1].split("\\$");
			result = new String[] { equipType[0], "装备", choujiang.getDesc(), String.valueOf(choujiang.getLevel()) };
		}else if("潜能".equals(award[0])) {
			//值， 类型，描述 等级
			String value = award[1].split("#r")[1];
			result = new String[] { value, "潜能", choujiang.getDesc(), String.valueOf(choujiang.getLevel())};
		}else if("经验".equals(award[0])) {
			String value = award[1].split("#r")[1];
			result = new String[] { value, "经验", choujiang.getDesc(), String.valueOf(choujiang.getLevel())};
		}else if("道行".equals(award[0])) {
			String value = award[1].split("#r")[1];
			result = new String[] { value, "道行", choujiang.getDesc(), String.valueOf(choujiang.getLevel())};
		}else if("积分".equals(award[0])) {
			String value = award[1].split("#r")[1];
			result = new String[] { value, "积分", choujiang.getDesc(), String.valueOf(choujiang.getLevel())};
		}else if("法宝".equals(award[0])) {
			String[] equipType = award[1].split("\\$");
			result = new String[] {"充值好礼法宝", equipType[0], equipType[1],equipType[2], String.valueOf(choujiang.getLevel())};
		}
		return result;
	}

	/**
	 * npc抽奖
	 * 
	 * @return
	 */
	public static String[] npcLuckDraw() {
		String[] result = null;
		// 根据几率选出几等奖
		Example example = new Example(ConfigInfo.class);
		example.selectProperties("data");
		example.createCriteria().andEqualTo("keyName", "抽奖大使配置信息");
		ConfigInfo ci = GameData.that.configInfoService.selectOneByExample(example);
		ChoujiangConfig config = JSONObject.parseObject(ci.getData(), ChoujiangConfig.class);
		int baseNumber = config.getBaseNumber();
		double randomLevel = ThreadLocalRandom.current().nextDouble(baseNumber == 0 ? 100 : baseNumber);
		int level = 4;
		if (randomLevel < config.getNo0()) {
			level = 0;
		} else if (randomLevel < config.getNo1()) {
			level = 1;
		} else if (randomLevel < config.getNo2()) {
			level = 2;
		} else if (randomLevel < config.getNo3()) {
			level = 3;
		}
		// 查询出当前等级的数量
		List<LuckDrawItem> findByLevel = GameData.that.luckDrawItemService.getLuckByLevel(level);
		// 随机取出一条数据
		LuckDrawItem choujiang = findByLevel.get(ThreadLocalRandom.current().nextInt(findByLevel.size()));
		String awardStr = choujiang.getItem();
		awardStr = awardStr.substring(2, awardStr.length() - 2);
		String[] award = awardStr.split("\\|");
		if ("物品".equals(award[0])) {
			String item = award[1];
			String[] split = item.split("#");
			result = new String[] { split[0], "物品", choujiang.getItem(), String.valueOf(choujiang.getLevel())};
		} else if ("宠物".equals(award[0])) {
			String nameAndType = award[1].split("\\$")[0];
			String[] str = nameAndType.split("\\(");
			String name = str[0]; // 宠物名字
			String petType = str[1].replace(")", ""); // 宠物名字
			result = new String[] { name, petType, choujiang.getItem(), String.valueOf(choujiang.getLevel())};
		} else if ("首饰".equals(award[0])) {
			// #I首饰|七星手链$指定$35#I
			result = new String[] { award[1].split("\\$")[0], "首饰", choujiang.getItem(), String.valueOf(choujiang.getLevel())};
		} else if ("装备".equals(award[0])) {
			String[] equipType = award[1].split("\\$");
			result = new String[] { equipType[0], "装备", choujiang.getItem(), String.valueOf(choujiang.getLevel())};
		}else if("经验".equals(award[0]) || "潜能".equals(award[0]) || "道行".equals(award[0])) {
			String[] equipType = award[1].split("\\$");
			result = new String[] { equipType[0], award[0], choujiang.getItem(), String.valueOf(choujiang.getLevel())};
		}else if("积分".equals(award[0])) {
			String[] equipType = award[1].split("\\$");
			result = new String[] { equipType[0], award[0], choujiang.getItem(), String.valueOf(choujiang.getLevel())};
		}else if("法宝".equals(award[0])) {
			String[] equipType = award[1].split("\\$");
			result = new String[] { equipType[0], award[0], choujiang.getItem(), String.valueOf(choujiang.getLevel())};
		}
		
		return result;
	}

	/**
	 * 获得抽奖
	 * 
	 * @param strings
	 * @param chara
	 */
	public static void huodechoujiang(String[] strings, GameObjectChar gameObjectChar, String typeName) {
		if(strings != null && strings.length>1) {
			Chara chara = gameObjectChar.chara;
			String msg = org.apache.commons.lang3.StringUtils.join("#W喜从天降！恭喜#Y" , chara.name , "在#R",typeName,"#W中幸运的抽中了#R%s#W获得了#R%s#W");
			if (strings[1].equals("变异")) {
				try {
					Pet pet = GameData.that.basePetService.findOneByName(strings[0]);
					if(pet != null) {
						Petbeibao petbeibao = new Petbeibao();
						petbeibao.PetCreate(pet, chara, 0, 3, typeName);
						List<Petbeibao> list = new ArrayList<Petbeibao>();
						chara.pets.add(petbeibao);
						list.add(petbeibao);
						GameObjectChar.send(new MSG_UPDATE_PETS(), list);
					}
				} catch (Exception e) {
				}
			}
			else if (strings[1].equals("神兽")) {
				try {
					Pet pet = GameData.that.basePetService.findOneByName(strings[0]);
					if(pet != null) {
						Petbeibao petbeibao = new Petbeibao();
						petbeibao.PetCreate(pet, chara, 0, 4, typeName);
						List<Petbeibao> list = new ArrayList<Petbeibao>();
						chara.pets.add(petbeibao);
						list.add(petbeibao);
						GameObjectChar.send(new MSG_UPDATE_PETS(), list);
					}
				} catch (Exception e) {
				}
			}
			else if (strings[1].equals("精怪")) {
				try {
					int jieshu = GameUtil.stageMounts(strings[0]);
					Pet pet2 = GameData.that.basePetService.findOneByName(strings[0]);
					if(pet2 != null) {
						Petbeibao petbeibao2 = new Petbeibao();
						petbeibao2.PetCreate(pet2, chara, 0, 2, typeName);
						List<Petbeibao> list2 = new ArrayList<Petbeibao>();
						chara.pets.add(petbeibao2);
						list2.add(petbeibao2);
						petbeibao2.petShuXing.get(0).enchant_nimbus = 0;
						petbeibao2.petShuXing.get(0).max_enchant_nimbus = 0;
						petbeibao2.petShuXing.get(0).suit_light_effect = 1;
						petbeibao2.petShuXing.get(0).hide_mount = jieshu;
						PetShuXing shuXing = new PetShuXing();
						shuXing.no = 23;
						shuXing.type1 = 2;
						shuXing.accurate = 4 * (jieshu - 1);
						shuXing.mana = 4 * (jieshu - 1);
						shuXing.wiz = 3 * (jieshu - 1);
						shuXing.all_polar = 0;
						shuXing.upgrade_magic = 0;
						shuXing.upgrade_total = 0;
						petbeibao2.petShuXing.add(shuXing);
						GameObjectChar.send(new MSG_UPDATE_PETS(), list2);
					}
				} catch (Exception e) {
					
				}
			}
			else if (strings[1].equals("物品")) {
				StoreInfo info = GameData.that.baseStoreInfoService.findOneByName(strings[0]);
				int num = 1;
				//设置过数量
				if(strings.length>4) {
					num = Integer.valueOf(strings[4]);
				}
				GameUtil.huodedaoju(gameObjectChar, info, num);
			}
			else if (strings[1].equals("首饰")) {
				/**
				 * #I首饰|随机35级首饰$随机$35#I #I首饰|随机满属性$随机满属性$35#I #I首饰|随机满属性$随机满属性#I
				 * #I首饰|七星手链$指定$35#I #I首饰|随机首饰$指定$35#I
				 */
				String[] split = strings[0].split("\\$");
				String name = split[0];
				String type = "";
				if (split.length > 1) {
					type = split[1];
					if (Utils.isNumber(type)) {
						// 不是数字的时候表示随机
						type = "";
					}
				}
				// 随机首饰
				if (StringUtils.isNullOrEmpty(type) || type.startsWith("随机属性")) {
					// 解析出指定什么
					GameCommonUtil.randomShouShiAttri(chara, name);
				} else if (type.startsWith("满属性")) {
					// 解析出指定什么
					GameCommonUtil.randomShouShiAllAttri(chara, name);
				} else if (type.equals("所有相五")) {
					GameUtil.jifendengjishoushi(chara, new String[] { name });
				}
			}
			else if (strings[1].equals("装备")) {
				Random random = new Random();
				int[] eqType = { 1, 2, 10, 3 };
				int leixing = eqType[random.nextInt(4)];
				String zhuangbname = zhuangbname(chara, leixing);
				List<Hashtable<String, Integer>> hashtables = equipmentLuckDraw(chara.level, leixing);
				if (hashtables.size() > 0) {
					ZhuangbeiInfo zhuangbeiInfo = GameData.that.baseZhuangbeiInfoService.findOneByStr(zhuangbname);
					for (Hashtable<String, Integer> maps : hashtables) {
						if (maps.get("groupNo") == 2) {
							maps.put("groupType", 2);
							GoodsLanSe gooodsLanSe = JSONObject.parseObject(JSONObject.toJSONString((Object) maps),
									GoodsLanSe.class);
							GameUtil.getBlueEquipGoods(chara, zhuangbeiInfo, 0, 1, gooodsLanSe);
						}
					}
				}
			}
			else if("经验".equals(strings[1])) {
				GameUtil.huodejingyan(chara, Integer.valueOf(strings[0]),typeName);
			}
			else if("道行".equals(strings[1])) {
				GameUtil.adddaohang(chara, Integer.valueOf(strings[0])*1440,typeName);
			}
			else if("潜能".equals(strings[1])) {
				chara.pot+=Integer.valueOf(strings[0]);
			}
			else if("积分".equals(strings[1])) {
				GameUtil.addchargeScore(gameObjectChar, Integer.valueOf(strings[0]), typeName);
			}else if("法宝".equals(strings[1])) {
				String quanmingcheng = strings[2];
				quanmingcheng = quanmingcheng.substring(2, quanmingcheng.length() - 2);
				String[]  mingchengs = quanmingcheng.split("\\$");
				int fabaoLevel = Integer.parseInt(mingchengs[1]);
				int xinagxing =Integer.parseInt(mingchengs[2]);
//				switch (fabao) {
//				case "番天印":
//					xinagxing = 1;
//					break;
//				case "混元金斗":
//					xinagxing = 5;
//					break;
//				case "定海珠":
//					xinagxing = 4;
//					break;
//				case "九龙神火罩":
//					xinagxing = 4;
//					break;
//				}
				GameUtil.jifenhuodefabao(chara, strings[0], fabaoLevel, typeName, xinagxing);
			}else if("充值好礼法宝".equals(strings[0])) {
				String name = strings[1];
				int fabaoLevel = Integer.valueOf(strings[2]);
				if(fabaoLevel<=0) {
					fabaoLevel = 1;
				}else if(fabaoLevel>24) {
					fabaoLevel = 24;
				}
				//相性
				int polar = Integer.valueOf(strings[3]);
				if(polar <= 0 || polar>5) {
					//随机相性
					polar = ThreadLocalRandom.current().nextInt(4)+1;
				}
				GameUtil.jifenhuodefabao(chara, name, fabaoLevel, typeName, polar);
			}
			//默认是要广播的
			if("0".equals(strings[3])) {
				//特等奖
				msg = String.format(msg, "特等奖", strings[0]);
				GameUtil.sendYaoYan(msg);
			}else if("1".equals(strings[3])) {
				//一等奖
				msg = String.format(msg, "一等奖", strings[0]);
				GameUtil.sendYaoYan(msg);
			}
		}
	}
	/**
	 * 获得抽奖
	 *
	 * @param strings
	 * @param chara
	 */
	public static void fuDaiChouJiang(String[] strings, GameObjectChar gameObjectChar, String typeName) {
		if(strings != null && strings.length>1) {
			Chara chara = gameObjectChar.chara;
			String msg = org.apache.commons.lang3.StringUtils.join("#W喜从天降！恭喜#Y" , chara.name , "#n打开福袋获得了%s%s");
			if (strings[1].equals("变异")) {
				try {
					Pet pet = GameData.that.basePetService.findOneByName(strings[0]);
					if(pet != null) {
						Petbeibao petbeibao = new Petbeibao();
						petbeibao.PetCreate(pet, chara, 0, 3, typeName);
						List<Petbeibao> list = new ArrayList<Petbeibao>();
						chara.pets.add(petbeibao);
						list.add(petbeibao);
						GameObjectChar.send(new MSG_UPDATE_PETS(), list);
					}
				} catch (Exception e) {
				}
			}
			else if (strings[1].equals("神兽")) {
				try {
					Pet pet = GameData.that.basePetService.findOneByName(strings[0]);
					if(pet != null) {
						Petbeibao petbeibao = new Petbeibao();
						petbeibao.PetCreate(pet, chara, 0, 4, typeName);
						List<Petbeibao> list = new ArrayList<Petbeibao>();
						chara.pets.add(petbeibao);
						list.add(petbeibao);
						GameObjectChar.send(new MSG_UPDATE_PETS(), list);
					}
				} catch (Exception e) {
				}
			}
			else if (strings[1].equals("精怪")) {
				try {
					int jieshu = GameUtil.stageMounts(strings[0]);
					Pet pet2 = GameData.that.basePetService.findOneByName(strings[0]);
					if(pet2 != null) {
						Petbeibao petbeibao2 = new Petbeibao();
						petbeibao2.PetCreate(pet2, chara, 0, 2, typeName);
						List<Petbeibao> list2 = new ArrayList<Petbeibao>();
						chara.pets.add(petbeibao2);
						list2.add(petbeibao2);
						petbeibao2.petShuXing.get(0).enchant_nimbus = 0;
						petbeibao2.petShuXing.get(0).max_enchant_nimbus = 0;
						petbeibao2.petShuXing.get(0).suit_light_effect = 1;
						petbeibao2.petShuXing.get(0).hide_mount = jieshu;
						PetShuXing shuXing = new PetShuXing();
						shuXing.no = 23;
						shuXing.type1 = 2;
						shuXing.accurate = 4 * (jieshu - 1);
						shuXing.mana = 4 * (jieshu - 1);
						shuXing.wiz = 3 * (jieshu - 1);
						shuXing.all_polar = 0;
						shuXing.upgrade_magic = 0;
						shuXing.upgrade_total = 0;
						petbeibao2.petShuXing.add(shuXing);
						GameObjectChar.send(new MSG_UPDATE_PETS(), list2);
					}
				} catch (Exception e) {

				}
			}
			else if (strings[1].equals("物品")) {
				StoreInfo info = GameData.that.baseStoreInfoService.findOneByName(strings[0]);
				int num = 1;
				//设置过数量
				if(strings.length>4) {
					num = Integer.valueOf(strings[4]);
				}
				GameUtil.huodedaoju(gameObjectChar, info, num);
			}
			else if (strings[1].equals("首饰")) {
				/**
				 * #I首饰|随机35级首饰$随机$35#I #I首饰|随机满属性$随机满属性$35#I #I首饰|随机满属性$随机满属性#I
				 * #I首饰|七星手链$指定$35#I #I首饰|随机首饰$指定$35#I
				 */
				String[] split = strings[2].substring(2,strings[2].length()-2).split("\\|")[1].split("\\$");
				String name = split[0];
				String type = "";
				if (split.length > 1) {
					type = split[1];
					if (Utils.isNumber(type)) {
						// 不是数字的时候表示随机
						type = "";
					}
				}
				// 随机首饰
				if (StringUtils.isNullOrEmpty(type) || type.startsWith("随机属性")) {
					// 解析出指定什么
					GameCommonUtil.randomShouShiAttri(chara, name);
				} else if (type.startsWith("满属性")) {
					// 解析出指定什么
					GameCommonUtil.randomShouShiAllAttri(chara, name);
				} else if (type.equals("所有相五")) {
					GameUtil.jifendengjishoushi(chara, new String[] { name });
				}
			}
			else if (strings[1].equals("装备")) {
				Random random = new Random();
				int[] eqType = { 1, 2, 10, 3 };
				int leixing = eqType[random.nextInt(4)];
				String zhuangbname = zhuangbname(chara, leixing);
				List<Hashtable<String, Integer>> hashtables = equipmentLuckDraw(chara.level, leixing);
				if (hashtables.size() > 0) {
					ZhuangbeiInfo zhuangbeiInfo = GameData.that.baseZhuangbeiInfoService.findOneByStr(zhuangbname);
					for (Hashtable<String, Integer> maps : hashtables) {
						if (maps.get("groupNo") == 2) {
							maps.put("groupType", 2);
							GoodsLanSe gooodsLanSe = JSONObject.parseObject(JSONObject.toJSONString((Object) maps),
									GoodsLanSe.class);
							GameUtil.getBlueEquipGoods(chara, zhuangbeiInfo, 0, 1, gooodsLanSe);
						}
					}
				}
			}
			else if("经验".equals(strings[1])) {
				GameUtil.huodejingyan(chara, Integer.valueOf(strings[0]),typeName);
				msg = org.apache.commons.lang3.StringUtils.join("#W喜从天降！恭喜#Y" , chara.name , "#n打开福袋获得了%s#R经验#W：%s");
			}
			else if("道行".equals(strings[1])) {
				GameUtil.adddaohang(chara, Integer.valueOf(strings[0])*1440,typeName);
				msg = org.apache.commons.lang3.StringUtils.join("#W喜从天降！恭喜#Y" , chara.name , "#n打开福袋获得了%s#R道行#W：%s");
			}
			else if("潜能".equals(strings[1])) {
				chara.pot+=Integer.valueOf(strings[0]);
				msg = org.apache.commons.lang3.StringUtils.join("#W喜从天降！恭喜#Y" , chara.name , "#n打开福袋获得了%s#R潜能#W：%s");
			}
			else if("积分".equals(strings[0])) {
				GameUtil.addchargeScore(gameObjectChar, Integer.valueOf(strings[1]), typeName);
				msg = org.apache.commons.lang3.StringUtils.join("#W喜从天降！恭喜#Y" , chara.name , "#n打开福袋获得了%s%s：#W"+strings[1]+"#W");
			}else if("法宝".equals(strings[1])) {
				//#I法宝|番天印$24$24#I
				String item = strings[2];
				int fabaoLevel = Integer.parseInt(item.split("\\$")[1]);
				int xinagxing = Integer.parseInt(item.split("\\$")[2].split("\\#")[0]);
				GameUtil.jifenhuodefabao(chara, strings[0], fabaoLevel, typeName, xinagxing);
				msg = org.apache.commons.lang3.StringUtils.join("#W喜从天降！恭喜#Y" , chara.name , "#n打开福袋获得了%s#R"+fabaoLevel+"级法宝#W：%s");
			}else if("充值好礼法宝".equals(strings[0])) {
				String name = strings[1];
				int fabaoLevel = Integer.valueOf(strings[2]);
				if(fabaoLevel<=0) {
					fabaoLevel = 1;
				}else if(fabaoLevel>24) {
					fabaoLevel = 24;
				}
				//相性
				int polar = Integer.valueOf(strings[3]);
				if(polar <= 0 || polar>5) {
					//随机相性
					polar = ThreadLocalRandom.current().nextInt(4)+1;
				}
				GameUtil.jifenhuodefabao(chara, name, fabaoLevel, typeName, polar);
			}else if("洛书经验".equals(strings[0])) {
				GameUtil.addLuoshuJinYan(chara, Integer.valueOf(strings[1]), "福袋抽奖");
				msg = org.apache.commons.lang3.StringUtils.join("#W喜从天降！恭喜#Y" , chara.name , "#n打开福袋获得了%s%s：#R"+strings[1]+"#W");
			}else if("充值".equals(strings[0])) {
				String accountName =gameObjectChar.account.getName();
				DrawApi.huodechongzhi(chara, strings[1],accountName);
				msg = org.apache.commons.lang3.StringUtils.join("#W喜从天降！恭喜#Y" , chara.name , "#n打开福袋获得了%s%s：#R"+strings[1]+"#W");
			}else if("武学".equals(strings[0])) {
				GameCommonUtil.addWuXue(chara,Integer.valueOf(strings[1]), "福袋抽奖");
				msg = org.apache.commons.lang3.StringUtils.join("#W喜从天降！恭喜#Y" , chara.name , "#n打开福袋获得了%s%s：#R"+strings[1]+"#W");
			}
			//默认是要广播的
			if("0".equals(strings[3])) {
				//特等奖
				msg = String.format(msg, "特等奖","#R"+strings[0]+"#W");
				GameUtil.sendYaoYan(msg);
			}else if("1".equals(strings[3])) {
				//一等奖
				msg = String.format(msg, "一等奖","#R"+strings[0]+"#W");
				GameUtil.sendYaoYan(msg);
			}else if("2".equals(strings[3])) {
				//一等奖
				msg = String.format(msg, "二等奖","#R"+strings[0]+"#W");
				GameUtil.sendYaoYan(msg);
			}else if("3".equals(strings[3])) {
				//一等奖
				msg = String.format(msg, "三等奖","#R"+strings[0]+"#W");
				GameUtil.sendYaoYan(msg);
			}else if("4".equals(strings[3])) {
				//一等奖
				msg = String.format(msg, "四等奖","#R"+strings[0]+"#W");
				GameUtil.sendYaoYan(msg);
			}
		}
	}

	public static List<Hashtable<String, Integer>> equipmentLuckDraw(int eq_attrib, int leixing) {
		if (eq_attrib < 70) {
			eq_attrib = 70;
		} else {
			eq_attrib = eq_attrib / 10 * 10;
		}
		List<Hashtable<String, Integer>> hashtables = ForgingEquipmentUtils.appraisalEquipment(leixing, eq_attrib, 10);
		String[] rareAttributes = { "all_resist_except", "all_resist_polar", "all_polar", "all_skill",
				"ignore_all_resist_except", "mstunt_rate", "release_forgotten" };
		for (Hashtable<String, Integer> hashtable : hashtables) {
			for (String key : rareAttributes) {
				if (hashtable.contains(key)) {
					Random random = new Random();
					String[] replaceAttributes = { "mag_power", "phy_power", "speed", "life" };
					List<Hashtable<String, Integer>> appraisalList = new ArrayList<Hashtable<String, Integer>>();
					Hashtable<String, Integer> key_vlaue_tab = new Hashtable<String, Integer>();
					key_vlaue_tab.put("groupNo", 2);
					key_vlaue_tab.put(replaceAttributes[random.nextInt(4)], eq_attrib / 4);
					appraisalList.add(key_vlaue_tab);
					return appraisalList;
				}
			}
		}
		return hashtables;
	}

	public static String zhuangbname(Chara chara, int leixing) {
		int eq_attrib = 0;
		if (chara.level < 70) {
			eq_attrib = 70;
		} else {
			eq_attrib = chara.level / 10 * 10;
		}
		List<ZhuangbeiInfo> byAttrib = (List<ZhuangbeiInfo>) GameData.that.baseZhuangbeiInfoService
				.findByAttrib(eq_attrib);
		for (int j = 0; j < byAttrib.size(); ++j) {
			if (leixing == 1 && byAttrib.get(j).getMetal() == chara.polar && byAttrib.get(j).getAmount() == leixing) {
				return byAttrib.get(j).getStr();
			}
			if ((leixing == 2 || leixing == 3) && byAttrib.get(j).getMaster() == chara.sex
					&& byAttrib.get(j).getAmount() == leixing) {
				return byAttrib.get(j).getStr();
			}
			if (leixing == 10 && byAttrib.get(j).getAmount() == leixing) {
				return byAttrib.get(j).getStr();
			}
		}
		return "";
	}


}