package com.fengshen.server.process.pet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.fengshen.db.domain.Pet;
import com.fengshen.server.data.GameReadTool;
import com.fengshen.server.data.game.BasicAttributesUtils;
import com.fengshen.server.data.game.PetAndHelpSkillUtils;
import com.fengshen.server.data.game.PetAttributesUtils;
import com.fengshen.server.data.vo.Vo_12023_0;
import com.fengshen.server.data.vo.Vo_20481_0;
import com.fengshen.server.data.vo.Vo_32747_0;
import com.fengshen.server.data.vo.Vo_40991_0;
import com.fengshen.server.data.vo.Vo_8165_0;
import com.fengshen.server.data.vo.system.Vo_GENERAL_NOTIFY;
import com.fengshen.server.data.vo.user.Vo_UPDATE_APPEARANCE;
import com.fengshen.server.data.write.M12023_0;
import com.fengshen.server.data.write.M20481_0;
import com.fengshen.server.data.write.M32747_0;
import com.fengshen.server.data.write.M40991_0;
import com.fengshen.server.data.write.M65525_0;
import com.fengshen.server.data.write.M8165_0;
import com.fengshen.server.data.write.pet.MSG_UPDATE_PETS;
import com.fengshen.server.data.write.system.MSG_GENERAL_NOTIFY;
import com.fengshen.server.data.write.user.MSG_UPDATE_APPEARANCE;
import com.fengshen.server.domain.Chara;
import com.fengshen.server.domain.Goods;
import com.fengshen.server.domain.JiNeng;
import com.fengshen.server.domain.PetShuXing;
import com.fengshen.server.domain.Petbeibao;
import com.fengshen.server.domain.SkillCost;
import com.fengshen.server.game.GameCommonUtil;
import com.fengshen.server.game.GameData;
import com.fengshen.server.game.GameHandler;
import com.fengshen.server.game.GameObjectChar;
import com.fengshen.server.game.GameUtil;
import com.google.common.collect.Lists;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

// 给宠物或者坐骑吃东西或者注入彩凤的逻辑
@Service
@Slf4j
public class C8270_0 implements GameHandler {
	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		int no = GameReadTool.readByte(buff); // 宠物的no
		int pos = GameReadTool.readByte(buff); // 物品的位置
		String para = GameReadTool.readString(buff);
		Chara chara = GameObjectChar.getGameObjectChar().chara;
		// 这里是打入妖石
		if ("inset".equals(para)) {
			for (int i = 0; i < chara.backpack.size(); ++i) {
				// 获取妖石在背包中的位置
				if (pos == chara.backpack.get(i).pos) {
					Goods goods = chara.backpack.get(i);
					// 逐个遍历宠物
					for (int j = 0; j < chara.pets.size(); ++j) {
						Petbeibao petbeibao = chara.pets.get(j);
						int weizhi = this.weizhi(0,petbeibao.petShuXing);
						if (petbeibao.no == no) {
							if(petbeibao.petShuXing.get(0).skill/10 < goods.goodsInfo.skill) {
								GameUtil.sendMeTips("宠物等级不足，无法打入该妖石");
								return;
							}
							// 如果已经有了妖石，就不能重复打入
							for (int k = 0; k < petbeibao.petShuXing.size(); ++k) {
								if (petbeibao.petShuXing.get(k).str.equals(goods.goodsInfo.str)) {
									GameUtil.sendMeTips("不可重复打入！");
									return;
								}else if(petbeibao.petShuXing.get(k).no == weizhi) {
									GameUtil.sendMeTips("位置已被使用！");
									return;
								}
							}
							if(weizhi == -1) {
								GameUtil.sendMeTips("没有可用的位置！");
								return;
							}
							PetShuXing petShuXing = new PetShuXing();
							petShuXing.no = weizhi;
							petShuXing.type1 = 2;
							petShuXing.skill = goods.goodsInfo.skill;
							petShuXing.str = goods.goodsInfo.str;
							petShuXing.accurate = goods.goodsLanSe.accurate;
							petShuXing.wiz = goods.goodsLanSe.wiz;
							petShuXing.parry = goods.goodsLanSe.parry;
							petShuXing.def = goods.goodsLanSe.def;
							petShuXing.dex = goods.goodsLanSe.dex;
							petShuXing.mana = goods.goodsLanSe.mana;
							petShuXing.silver_coin = 8000;
							petbeibao.petShuXing.add(petShuXing);

							for (int k = 0; k < petbeibao.petShuXing.size(); ++k) {
								// 在宠物的基础信息里面操作
								if (petbeibao.petShuXing.get(k).no == 0) {
									PetShuXing petShuXing2 = petbeibao.petShuXing.get(k);
									petShuXing2.wiz += goods.goodsLanSe.wiz;
									petShuXing2.parry += goods.goodsLanSe.parry;
									petShuXing2.def += goods.goodsLanSe.def;
									petShuXing2.dex += goods.goodsLanSe.dex;
									petShuXing2.mana += goods.goodsLanSe.mana;
									petShuXing2.accurate += goods.goodsLanSe.accurate;
									break;
								}
							}

							List<Petbeibao> list = new ArrayList<>();
							list.add(petbeibao);
							GameObjectChar.send(new MSG_UPDATE_PETS(), list);

							Vo_8165_0 vo_8165_2 = new Vo_8165_0();
							vo_8165_2.msg = "打入妖石成功";
							vo_8165_2.active = 0;
							GameObjectChar.send(new M8165_0(), vo_8165_2);

							Vo_GENERAL_NOTIFY vo_9129_0 = new Vo_GENERAL_NOTIFY();
							vo_9129_0.notify = 12000;
							vo_9129_0.para = "383174";
							GameObjectChar.send(new MSG_GENERAL_NOTIFY(), vo_9129_0);
						}
					}
					--goods.goodsInfo.owner_id; // 减少妖石的数量
					if (goods.goodsInfo.owner_id == 0) {
						List<Goods> listbeibao = new ArrayList<Goods>();
						Goods goods2 = new Goods();
						goods2.goodsBasics = null;
						goods2.goodsInfo = null;
						goods2.goodsLanSe = null;
						goods2.pos = goods.pos;
						listbeibao.add(goods2);
						chara.backpack.remove(goods);
						GameObjectChar.send(new M65525_0(), listbeibao);
					}
					GameObjectChar.send(new M65525_0(), chara.backpack);
				}
			}
		}

		if ("".equals(para)) {
			for (int i = 0; i < chara.backpack.size(); ++i) {
				if (pos == chara.backpack.get(i).pos) {
					Goods goods = chara.backpack.get(i);
					String str = chara.backpack.get(i).goodsInfo.str;
					for (int j = 0; j < chara.pets.size(); ++j) {
						Petbeibao pet = chara.pets.get(j);
						if (pet.no == no) {
							PetShuXing petShuXing = pet.petShuXing.get(0);
							if (str.equals("彩凤之魂")) {
								// 彩凤标识
								petShuXing.zhuruCaifeng = 1;
								// 设置坐骑彩凤效果
								if (chara.upgrade_state == 0) {
									chara.zuoqiwaiguan = 31501;
								}
								// 如果骑了坐骑就刷新当前地图数据
								if (chara.zuoqiId != 0) {
									// 设置坐姿
									if (chara.upgrade_state != 0) {
										chara.zuowaiguan = GameCommonUtil.getYuanYingZuoqiWaiguan(chara,
												chara.zuoqiwaiguan);
									} else {
										chara.zuowaiguan = CMD_SELECT_CURRENT_MOUNT.typeMounts(
												petShuXing.type + 1000, chara.polar,
												chara.sex - 1);
									}
									// 更新人物外观数据-当前地图
									Vo_UPDATE_APPEARANCE vo_61661_0 = GameUtil.a61661(chara);
									GameObjectChar.getGameObjectChar().gameMap.send(new MSG_UPDATE_APPEARANCE(), vo_61661_0);
								}
								GameObjectChar.send(new MSG_UPDATE_PETS(), chara.pets, chara.id);
								GameUtil.sendMeTips(
										"恭喜，你的坐骑#Y" + petShuXing.str + "#n成功注入彩凤之魂");
								GameUtil.removemunber(chara, goods.goodsInfo.str, 1);
							}
							// 吃了天书
							else if ("魔引 狂暴 怒击 破天 反击 降魔斩 修罗术 云体 仙风 尽忠 惊雷 青木 寒冰 烈炎 碎石 超级魔引 超级狂暴 超级怒击 超级破天 超级反击 超级降魔斩 超级修罗术 超级云体 超级仙风 超级尽忠 超级惊雷 超级青木 超级寒冰 超级烈炎 超级碎石"
									.contains(str)) {
								if (pet.tianshu.size() >= 3) {
									GameUtil.sendMeTips("宠物天书满了,无法打入");
									return;
								}
								for(Vo_12023_0 v:pet.tianshu) {
									if(v.god_book_skill_name.equals(str)) {
										GameUtil.sendMeTips("宠物已领悟该天书，请勿再次打入！");
										return;
									}
								}
								List<Petbeibao> list2 = new ArrayList<>();
								list2.add(pet);
								GameObjectChar.send(new MSG_UPDATE_PETS(), list2);
								boolean isfagong = petShuXing.rank > petShuXing.pet_mag_shape;
								GameUtil.dujineng(1, petShuXing.metal,
										petShuXing.skill, isfagong, pet.id,
										chara, pet);
								Vo_12023_0 vo_12023_0 = new Vo_12023_0();
								vo_12023_0.owner_id = chara.id;
								vo_12023_0.id = pet.id;
								String name = goods.goodsInfo.str;
								if(name.contains("超级")){
									name = name.split("超级")[1];
									vo_12023_0.wiz += goods.goodsLanSe.wiz;
									vo_12023_0.parry += goods.goodsLanSe.parry;
									vo_12023_0.def += goods.goodsLanSe.def;
									vo_12023_0.dex += goods.goodsLanSe.dex;
									vo_12023_0.mana += goods.goodsLanSe.mana;
									vo_12023_0.accurate += goods.goodsLanSe.accurate;
								}
								vo_12023_0.name = goods.goodsInfo.str;
								vo_12023_0.type = goods.goodsInfo.type;
								vo_12023_0.god_book_skill_name = name;
								vo_12023_0.god_book_skill_level = (int) (chara.level * 1.6);
								vo_12023_0.god_book_skill_power = 6000;
								vo_12023_0.god_book_skill_disabled = 0;
								pet.tianshu.add(vo_12023_0);
								GameObjectChar.send(new M12023_0(), pet.tianshu);
								if(goods.goodsInfo.str.contains("超级")){
									PetShuXing petShuXing2 = new PetShuXing();
									petShuXing2.no = goods.goodsInfo.type;
									petShuXing2.type1 = 2;
									petShuXing2.skill = goods.goodsInfo.skill;
									petShuXing2.str = goods.goodsInfo.str;
									petShuXing2.accurate = goods.goodsLanSe.accurate;
									petShuXing2.wiz = goods.goodsLanSe.wiz;
									petShuXing2.parry = goods.goodsLanSe.parry;
									petShuXing2.def = goods.goodsLanSe.def;
									petShuXing2.dex = goods.goodsLanSe.dex;
									petShuXing2.mana = goods.goodsLanSe.mana;
									petShuXing2.silver_coin = 8000;
									pet.petShuXing.add(petShuXing2);
//									for (int k = 0; k < pet.petShuXing.size(); ++k) {
//										// 在宠物的基础信息里面操作
//										if (pet.petShuXing.get(k).no == goods.goodsInfo.type) {
//											PetShuXing petShuXing3 = pet.petShuXing.get(k);
//											petShuXing3.wiz += goods.goodsLanSe.wiz;
//											petShuXing3.parry += goods.goodsLanSe.parry;
//											petShuXing3.def += goods.goodsLanSe.def;
//											petShuXing3.dex += goods.goodsLanSe.dex;
//											petShuXing3.mana += goods.goodsLanSe.mana;
//											petShuXing3.accurate += goods.goodsLanSe.accurate;
//											break;
//										}
//									}
									BasicAttributesUtils.petshuxing(pet.petShuXing.get(0),pet);
									List<Petbeibao> list = new ArrayList<>();
									list.add(pet);
									GameObjectChar.send(new MSG_UPDATE_PETS(), list);
								}
								//pet.petShuXing.add(petShuXing);
								Vo_UPDATE_APPEARANCE vo_61661_0 = GameUtil.a61661(chara);
								GameObjectChar.getGameObjectChar().gameMap.send(new MSG_UPDATE_APPEARANCE(), vo_61661_0);
								Vo_20481_0 vo_20481_0 = new Vo_20481_0();
								vo_20481_0.msg = "恭喜，你的宠物#Y" + petShuXing.str + "#n领悟了新的天书技能";
								vo_20481_0.time = (int) (System.currentTimeMillis() / 1000L);
								GameObjectChar.send(new M20481_0(), vo_20481_0);
								GameUtil.removemunber(chara, goods.goodsInfo.str, 1);
							}else if("如意圈秘笈#神龙罩秘笈#乾坤罩秘笈".contains(str)) {
								int skillNo = 259;
								if("如意圈秘笈".equals(str)) {
									skillNo  = 254;
								}else if("神龙罩秘笈".equals(str)) {
									skillNo = 260;
								}
								//学习天技
								JiNeng tianji = null;
								if(pet.tianji == null) {
									pet.tianji = new ArrayList<>();
								}
								for(JiNeng jn:pet.tianji) {
									if(jn.getSkill_no() == skillNo) {
										//找到该技能
										tianji = jn;
										break;
									}
								}
								if(tianji == null) {
									//创建技能
									tianji = new JiNeng();
									tianji.id = pet.id;
									tianji.skill_no = skillNo;
									final JSONObject jsonObject = PetAndHelpSkillUtils.jsonArray(skillNo);
									tianji.skill_attrib1 = Integer.parseInt((String) jsonObject.get("skill_attrib"));
									int maxSkill = PetAndHelpSkillUtils.getMaxSkill(chara.level);
									tianji.skill_attrib = maxSkill;
									tianji.skill_level = 1;
									final int[] blueAndPointsLan = PetAndHelpSkillUtils.getBlueAndPointsLan(skillNo, tianji.skill_level);
									tianji.level_improved = 0;
									tianji.skill_mana_cost = blueAndPointsLan[0];
									tianji.skill_nimbus = 42949672;
									tianji.skill_disabled = 0;
									tianji.range = PetAndHelpSkillUtils.skillNummax(skillNo, tianji.skill_level);
									tianji.max_range = PetAndHelpSkillUtils.skillNummax(skillNo, tianji.skill_attrib);
									final int[] ints = PetAndHelpSkillUtils.skillNum(jsonObject, tianji.skill_level);
									tianji.skillRound = ints[1];
									tianji.count1 = 0;
									//消耗信息
									int[] petPartySkillCost = GameCommonUtil.getPetPartySkillCost(tianji.skill_level);
									tianji.skillCost.add(new SkillCost("cash",petPartySkillCost[0]));
									tianji.skillCost.add(new SkillCost("party/contrib",petPartySkillCost[1]));
									tianji.isTempSkill = 0;
									pet.tianji.add(tianji);
									GameUtil.sendMeTips("恭喜你的#R"+petShuXing.str+"#n领会了#Y"+str.replace("秘笈", "")+"#n。");
									//刷新技能信息
									GameUtil.dujineng(1, petShuXing.metal, petShuXing.skill, false, pet.id, chara, pet);
									GameUtil.removemunber(chara, str, 1);
								}else {
									GameUtil.sendMeTips("该宠物已领悟这个技能了，无需再次领会!");
								}
							}else if("风灵丸".equals(str)) {
								para = str;
							}
							break;
						}
					}
					break;
				}
			}
		}
		//强化法攻
		if ("mag".equals(para)) {
			if(GameCommonUtil.getGoodsNum(chara, "宠物强化丹") < 1) {
				GameUtil.sendMeTips("宠物强化丹不足");
				return;
			}
			for (int i = 0; i < chara.pets.size(); ++i) {
				Petbeibao petbeibao = chara.pets.get(i);
				if (petbeibao.no == no) {
					if(petbeibao.petShuXing.get(0).raw_name>=12) {
						GameUtil.sendMeTips("宠物强化已达极限");
						return;
					}
					if(petbeibao.petShuXing.get(0).penetrate != 2) {
						GameUtil.sendMeTips("只有宝宝才可以强化");
						return;
					}
					Pet pet = GameData.that.basePetService.findOneByName(petbeibao.petShuXing.get(0).str);
					int[] ints = PetAttributesUtils.upgradePet(true, pet.getMagAttack(),
							petbeibao.petShuXing.get(0).raw_name, petbeibao.petShuXing.get(0).life_add_temp);
					if (petbeibao.petShuXing.get(0).raw_name < ints[0]) {
						PetShuXing petShuXing2 = petbeibao.petShuXing.get(0);
						petShuXing2.pet_life_shape_temp += ints[1];
						PetShuXing petShuXing3 = petbeibao.petShuXing.get(0);
						petShuXing3.rank += ints[1];
						petbeibao.petShuXing.get(0).life_add_temp = 0;
						petbeibao.petShuXing.get(0).raw_name = ints[0];
						Vo_8165_0 vo_8165_3 = new Vo_8165_0();
						vo_8165_3.msg = "恭喜强化成功！";
						vo_8165_3.active = 0;
						GameObjectChar.send(new M8165_0(), vo_8165_3);
					} else {
						petbeibao.petShuXing.get(0).life_add_temp = ints[2];
						Vo_8165_0 vo_8165_3 = new Vo_8165_0();
						vo_8165_3.msg = "成长完成度增加了！";
						vo_8165_3.active = 0;
						GameObjectChar.send(new M8165_0(), vo_8165_3);
					}
					GameUtil.removemunber(chara, "宠物强化丹", 1);
					List<Petbeibao> list3 = new ArrayList<>();
					BasicAttributesUtils.petshuxing(petbeibao.petShuXing.get(0), petbeibao);
					list3.add(petbeibao);
					GameObjectChar.send(new MSG_UPDATE_PETS(), list3);
				}
			}
		}
		
		//强化物攻
		if ("phy".equals(para)) {
			if(GameCommonUtil.getGoodsNum(chara, "宠物强化丹") < 1) {
				GameUtil.sendMeTips("宠物强化丹不足");
				return;
			}
			for (int i = 0; i < chara.pets.size(); ++i) {
				Petbeibao petbeibao = chara.pets.get(i);
				if (petbeibao.no == no) {
					if(petbeibao.petShuXing.get(0).mag_rebuild_level>=12) {
						GameUtil.sendMeTips("宠物强化已达极限");
						return;
					}
					if(petbeibao.petShuXing.get(0).penetrate != 2) {
						GameUtil.sendMeTips("只有宝宝才可以强化");
						return;
					}
					Pet pet = GameData.that.basePetService.findOneByName(petbeibao.petShuXing.get(0).str);
					int[] ints = PetAttributesUtils.upgradePet(false, pet.getPhyAttack(),
							petbeibao.petShuXing.get(0).mag_rebuild_level,
							petbeibao.petShuXing.get(0).mag_rebuild_rate);
					if (petbeibao.petShuXing.get(0).mag_rebuild_level < ints[0]) {
						PetShuXing petShuXing4 = petbeibao.petShuXing.get(0);
						petShuXing4.mag_rebuild_add += ints[1];
						PetShuXing petShuXing5 = petbeibao.petShuXing.get(0);
						petShuXing5.pet_mag_shape += ints[1];
						petbeibao.petShuXing.get(0).mag_rebuild_rate = 0;
						petbeibao.petShuXing.get(0).mag_rebuild_level = ints[0];
						Vo_8165_0 vo_8165_3 = new Vo_8165_0();
						vo_8165_3.msg = "恭喜强化成功！";
						vo_8165_3.active = 0;
						GameObjectChar.send(new M8165_0(), vo_8165_3);
					} else {
						petbeibao.petShuXing.get(0).mag_rebuild_rate = ints[2];
						Vo_8165_0 vo_8165_3 = new Vo_8165_0();
						vo_8165_3.msg = "成长完成度增加了！";
						vo_8165_3.active = 0;
						GameObjectChar.send(new M8165_0(), vo_8165_3);
					}
					List<Petbeibao> list3 = new ArrayList<>();
					BasicAttributesUtils.petshuxing(petbeibao.petShuXing.get(0), petbeibao);
					list3.add(petbeibao);
					GameObjectChar.send(new MSG_UPDATE_PETS(), list3);
					GameUtil.removemunber(chara, "宠物强化丹", 1);
				}
			}
		}
		if ("reset".equals(para)) {
			if(GameCommonUtil.getGoodsNum(chara, "超级归元露") < 1) {
				GameUtil.sendMeTips("超级归元露不足");
				return;
			}
			for (int i = 0; i < chara.pets.size(); ++i) {
				Petbeibao petbeibao = chara.pets.get(i);
				if (petbeibao.no == no) {
					Pet pet = GameData.that.basePetService.findOneByName(petbeibao.petShuXing.get(0).str);
					PetShuXing shuXing = petbeibao.petShuXing.get(0);
					if(shuXing.penetrate != 1) {
						GameUtil.sendMeTips("只有野生宠物才能洗练");
						return;
					}
					shuXing.penetrate = 2;
					shuXing.skill = 1;
					shuXing.pot = 0;
					shuXing.resist_poison = 258;
					shuXing.mana_effect = pet.getLife() - 40 - this.subtraction();
					shuXing.attack_effect = pet.getMana() - 40 - this.subtraction();
					shuXing.mag_effect = pet.getPhyAttack() - 40 - this.subtraction();
					shuXing.phy_absorb = pet.getMagAttack() - 40 - this.subtraction();
					shuXing.phy_effect = pet.getSpeed() - 40 - this.subtraction();
					shuXing.pet_mana_shape = shuXing.mana_effect + 40;
					shuXing.pet_speed_shape = shuXing.attack_effect + 40;
					shuXing.pet_phy_shape = shuXing.phy_effect + 40;
					shuXing.pet_mag_shape = shuXing.mag_effect + 40;
					shuXing.rank = shuXing.phy_absorb + 40;
					shuXing.phy_power = 1;
					shuXing.mag_power = 1;
					shuXing.life = 1;
					shuXing.speed = 1;
					shuXing.polar_point = 4;
					shuXing.resist_point = shuXing.pet_mana_shape + shuXing.pet_speed_shape + shuXing.pet_phy_shape
							+ shuXing.pet_mag_shape + shuXing.rank;
					List<Petbeibao> list3 = new ArrayList<>();
					BasicAttributesUtils.petshuxing(petbeibao.petShuXing.get(0), petbeibao);
					shuXing.max_life = shuXing.def;
					shuXing.max_mana = shuXing.dex;
					list3.add(petbeibao);
					boolean isfagong2 = shuXing.rank > shuXing.pet_mag_shape;
					List<JiNeng> jiNengList = new ArrayList<JiNeng>();
					List<JSONObject> nomelSkills = PetAndHelpSkillUtils.getNomelSkills(1, shuXing.metal, 100,
							isfagong2);
					for (int l = 0; l < nomelSkills.size(); ++l) {
						JiNeng jiNeng = new JiNeng();
						JSONObject jsonObject = nomelSkills.get(l);
						jiNeng.id = petbeibao.id;
						jiNeng.skill_no = Integer.parseInt((String) jsonObject.get("skillNo"));
						jiNeng.skill_attrib = 0;
						jiNeng.skill_level = 0;
						jiNeng.level_improved = 0;
						jiNeng.skill_mana_cost = 0;
						jiNeng.skill_nimbus = 42949672;
						jiNeng.skill_disabled = 0;
						jiNeng.range = 0;
						jiNeng.max_range = 0;
						jiNengList.add(jiNeng);
					}
					List<Vo_32747_0> vo_32747_0List = GameUtil.a32747(jiNengList);
					GameObjectChar.send(new M32747_0(), vo_32747_0List);
					GameObjectChar.send(new MSG_UPDATE_PETS(), list3);
					Vo_40991_0 vo_40991_0 = new Vo_40991_0();
					vo_40991_0.result = 0;
					GameObjectChar.send(new M40991_0(), vo_40991_0);
					Vo_8165_0 vo_8165_4 = new Vo_8165_0();
					vo_8165_4.msg = "洗练成功，宠物#Y" + pet.getName() + "(野生)#n已洗炼成为1级#Y" + pet.getName() + "(宝宝)#n";
					vo_8165_4.active = 0;
					GameObjectChar.send(new M8165_0(), vo_8165_4);
					GameUtil.removemunber(chara, "超级归元露", 1);
				}
			}
		}

		if ("refine".equals(para)) {
			if(GameCommonUtil.getGoodsNum(chara, "超级归元露") < 1) {
				GameUtil.sendMeTips("超级归元露不足");
				return;
			}
			for (int i = 0; i < chara.pets.size(); ++i) {
				Petbeibao petbeibao = chara.pets.get(i);
				if (petbeibao.no == no) {
					Pet pet = GameData.that.basePetService.findOneByName(petbeibao.petShuXing.get(0).str);
					PetShuXing shuXing = petbeibao.petShuXing.get(0);
					//只有宝宝才允许替换
					if(shuXing.penetrate != 2) {
						GameUtil.sendMeTips("只有宝宝才能洗练");
						return;
					}
					shuXing.pet_mana_shape_temp = pet.getLife() - this.subtraction() - shuXing.mana_effect - 40;
					shuXing.pet_speed_shape_temp = pet.getMana() - this.subtraction() - shuXing.attack_effect - 40;
					shuXing.pet_phy_shape_temp = pet.getSpeed() - this.subtraction() - shuXing.phy_effect - 40;
					shuXing.pet_mag_shape_temp = pet.getPhyAttack() - this.subtraction() - shuXing.mag_effect - 40;
					shuXing.evolve_degree = pet.getMagAttack() - this.subtraction() - shuXing.phy_absorb - 40;
					if (shuXing.mana_effect + 40 == pet.getLife()) {
						shuXing.pet_mana_shape_temp = 0;
					}
					if (shuXing.attack_effect + 40 == pet.getMana()) {
						shuXing.pet_speed_shape_temp = 0;
					}
					if (shuXing.phy_absorb + 40 == pet.getMagAttack()) {
						shuXing.evolve_degree = 0;
					}
					if (shuXing.mag_effect + 40 == pet.getPhyAttack()) {
						shuXing.pet_mag_shape_temp = 0;
					}
					if (shuXing.phy_effect + 40 == pet.getSpeed()) {
						shuXing.pet_phy_shape_temp = 0;
					}
					
					List<Petbeibao> list3 = new ArrayList<>();
					BasicAttributesUtils.petshuxing(petbeibao.petShuXing.get(0), petbeibao);
					list3.add(petbeibao);
					GameObjectChar.send(new MSG_UPDATE_PETS(), list3);
					Vo_40991_0 vo_40991_2 = new Vo_40991_0();
					vo_40991_2.result = 0;
					GameObjectChar.send(new M40991_0(), vo_40991_2);
					Vo_8165_0 vo_8165_2 = new Vo_8165_0();
					vo_8165_2.msg = "你的#Y" + pet.getName() + "#n经过洗炼，基础成长已重新生成。";
					vo_8165_2.active = 0;
					GameObjectChar.send(new M8165_0(), vo_8165_2);
					GameUtil.removemunber(chara, "超级归元露", 1);
				}
			}
		}
		//使用风灵丸
		if("风灵丸".equals(para)) {
			int count = 0;
			Iterator<Goods> iterator = chara.backpack.iterator();
			while(iterator.hasNext()) {
				Goods goods = iterator.next();
				if("风灵丸".equals(goods.goodsInfo.str)) {
					goods.goodsInfo.owner_id+=count;
					//删除这个道具
				}
			}
			GameUtil.sendMeTips("你的坐骑使用风灵丸成功");
			return;
		}
	}

	@Override
	public int cmd() {
		return 8270;
	}

	public int subtraction() {
		Random r = new Random();
		return r.nextInt(10);
	}

	// 生成妖石PetShuxing的no
	public int weizhi(int weizhi, List<PetShuXing> shuXings) {
		//妖石默认的位置
		List<Integer> defaultPosition = Lists.newArrayList(12,13,14);
		for (int i = 0; i < shuXings.size(); ++i) {
			int no = shuXings.get(i).no;
			if (defaultPosition.contains(no)) {
				defaultPosition.remove((Object)no);
			}
		}
		Collections.sort(defaultPosition);
		return defaultPosition.isEmpty()?-1:defaultPosition.get(0);
	}
}