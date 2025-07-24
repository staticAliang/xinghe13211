package com.fengshen.server.process.pet;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.fengshen.db.domain.PackModification;
import com.fengshen.db.domain.Pet;
import com.fengshen.server.data.GameReadTool;
import com.fengshen.server.data.game.BasicAttributesUtils;
import com.fengshen.server.data.game.PetAttributesUtils;
import com.fengshen.server.data.vo.ListVo_65527_0;
import com.fengshen.server.data.vo.Vo_12269_0;
import com.fengshen.server.data.vo.Vo_20481_0;
import com.fengshen.server.data.vo.Vo_41505_0;
import com.fengshen.server.data.vo.Vo_8165_0;
import com.fengshen.server.data.write.CommonWrite;
import com.fengshen.server.data.write.M12269_0;
import com.fengshen.server.data.write.M20481_0;
import com.fengshen.server.data.write.M41505_0;
import com.fengshen.server.data.write.M45670_0;
import com.fengshen.server.data.write.M53607_0;
import com.fengshen.server.data.write.M65527_0;
import com.fengshen.server.data.write.M8165_0;
import com.fengshen.server.data.write.pet.MSG_PET_ICON_UPDATED;
import com.fengshen.server.data.write.pet.MSG_UPDATE_PETS;
import com.fengshen.server.domain.Chara;
import com.fengshen.server.domain.Goods;
import com.fengshen.server.domain.PetShuXing;
import com.fengshen.server.domain.Petbeibao;
import com.fengshen.server.game.GameCommonUtil;
import com.fengshen.server.game.GameCore;
import com.fengshen.server.game.GameData;
import com.fengshen.server.game.GameHandler;
import com.fengshen.server.game.GameObjectChar;
import com.fengshen.server.game.GameUtil;
import com.fengshen.server.util.GameConfig;
import com.google.common.collect.Lists;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

/**
 * 宠物羽化
 * 
 *
 */
@Service
@Slf4j
public class CMD_UPGRADE_PET implements GameHandler {
	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		String type = GameReadTool.readString(buff);
		int no = GameReadTool.readInt(buff);
		String pos = GameReadTool.readString(buff);
		String other_pet = GameReadTool.readString(buff); // 副宠的编号
		String cost_type = GameReadTool.readString(buff);
		String ids = GameReadTool.readString(buff);
		GameObjectChar gameObjectChar = GameObjectChar.getGameObjectChar();
		Chara chara = gameObjectChar.chara;
		log.info("宠物羽化，ids={},type={},no={},副宠编号={}",ids,type,no,other_pet);

		//宠物继承
		if("pet_inherit".equals(type)) {
			Petbeibao mpet = null;
			Petbeibao opet = null;
			for (Petbeibao p : chara.pets) {
				if (p.no == no) {
					mpet = p;
				}
				if(p.no == Integer.valueOf(other_pet)) {
					opet = p;
				}
			}
			//如果主宠和副宠不等于空
			if(mpet != null && opet != null) {
				gameObjectChar.confirmData = new Object[] {mpet,opet};
				GameUtil.confirm(chara, StringUtils.join("宠物继承需要花费#R",GameConfig.config.getBaseConfig().getPetInheritScore(),"#n积分确定吗？"), "pet_inherit");
			}
			return;
		}
		
		if ("pet_change_color".equals(type)) {
			// 宠物染色
			for (Petbeibao p : chara.pets) {
				if (p.no == no) {
					// 获取扣除的元宝
					JSONObject color = GameCore.petColorScheme.get(cost_type);
					int intValue = color.getIntValue("coin");
					if (chara.goldCoin - intValue < 0) {
						GameUtil.sendMeTips("元宝不足。");
						return;
					}
					if (Integer.valueOf(cost_type) == p.petShuXing.get(0).type) {
						// 变回原型
						p.petShuXing.get(0).dye_icon = 0;
					} else {
						p.petShuXing.get(0).dye_icon = Integer.valueOf(cost_type);
					}
					GameObjectChar.send(new MSG_UPDATE_PETS(), Lists.newArrayList(p));
					GameObjectChar.send(new MSG_PET_ICON_UPDATED(),
							new Object[] { "equip_fasion", Integer.valueOf(cost_type) });
					GameUtil.sendMeTips("宠物染色成功，花费了#R" + intValue + "金元宝");
					chara.goldCoin -= intValue;
					GameUtil.sendUpdate(chara);
					break;
				}
			}
			return;
		}
		// 宠物变幻人形
		if ("pet_fasion".equals(type)) {
			for (Petbeibao p : chara.pets) {
				if (p.no == no) {
					int fasion_id = 0;
					int fasion_visible = 0;
					// 表示当前这个时装主人也在穿
					if ("31".equals(cost_type)) {
						if(chara.special_icon == 60001 || chara.special_icon == 61001) {
							//找到当前31号的位置
							for(Goods goods:chara.otherGoods) {
								if(goods.pos == 31) {
									String name = goods.goodsInfo.str;
									PackModification findOneByStr = GameData.that.basePackModificationService.findOneByStr(name);
									if(findOneByStr != null) {
										fasion_id = Integer.valueOf(findOneByStr.getFasionType());
										log.info("宠物穿戴时装31.pos={}, fasion_id={}",goods.pos,findOneByStr.getFasionType());
									}
									break;
								}
							}
						}else {
							fasion_id = chara.special_icon;
						}
						fasion_visible = 1;
					} else if ("0".equals(cost_type)) {
						// 解除变形
						p.petShuXing.get(0).fasion_id = fasion_id;
						p.petShuXing.get(0).fasion_visible = fasion_visible;
						GameObjectChar.send(new MSG_UPDATE_PETS(), Lists.newArrayList(p));
						GameObjectChar.send(new MSG_PET_ICON_UPDATED(), new Object[] { "unequip_fasion", fasion_id });
						GameUtil.sendMeTips("宠物解除变幻成功");
						return;
					} else {
						// 查询时装位置
						PackModification findOneByPosition = GameData.that.basePackModificationService
								.findOneBySexForType(Integer.valueOf(cost_type), chara.sex == 2 ? 1 : 0);
						fasion_id = Integer.valueOf(findOneByPosition.getFasionType());
						fasion_visible = 1;
					}
					if (chara.cash - 5000000 < 0) {
						GameUtil.sendMeTips("金钱不足。");
						return;
					}
					p.petShuXing.get(0).fasion_id = fasion_id;
					p.petShuXing.get(0).fasion_visible = fasion_visible;
					// 如果改了名就恢复原来名称
					p.petShuXing.get(0).str = p.petShuXing.get(0).suit_polar;
					GameObjectChar.send(new MSG_UPDATE_PETS(), Lists.newArrayList(p));
					GameObjectChar.send(new MSG_PET_ICON_UPDATED(), new Object[] { "equip_fasion", fasion_id });
					GameUtil.sendMeTips("宠物变幻人形成功，花费了#O5,000,000#n文钱");
					chara.cash -= 5000000;
					GameUtil.sendUpdate(chara);
					break;
				}
			}
			Vo_41505_0 vo_41505_0 = new Vo_41505_0();
			vo_41505_0.type = "equip_fasion";
			GameObjectChar.send(new M41505_0(), vo_41505_0);
			return;
		}

		// 宠物幻化
		if (type.equals("pet_morph")) {
			// 先判断提交的副宠是否存在，如果不存在了则提示报错
			boolean hasFc = false;
			for (int l = 0; l < chara.pets.size(); ++l) {
				Petbeibao huahua = chara.pets.get(l);
				if (Integer.valueOf(other_pet) == huahua.no) {
					if(!huahua.petShuXing.get(0).str.equals("百年黑熊") && !huahua.petShuXing.get(0).str.equals("血幻豪猪")
							&&!huahua.petShuXing.get(0).str.equals("赤血幼猿") && !huahua.petShuXing.get(0).str.equals("魅影毒蝎")	
							) {
						GameUtil.sendMeTips("请提交正确的副宠");
						return;
					}
					hasFc = true;
					break;
				}
			}
			if (!hasFc) {
				Vo_8165_0 vo_8165_2 = new Vo_8165_0();
				vo_8165_2.msg = "您还没有提交副宠！";
				vo_8165_2.active = 0;
				GameObjectChar.send(new M8165_0(), vo_8165_2);
				return;
			}

			for (int i = 0; i < chara.pets.size(); ++i) {
				Petbeibao petbeibao = chara.pets.get(i);
				if (petbeibao.no == no) {
					Pet pet = GameData.that.basePetService.findOneByName(petbeibao.petShuXing.get(0).suit_polar);
					PetShuXing psx = petbeibao.petShuXing.get(0);
					// 先判断幻化次数15次是否达到，达到了则提示幻化完成！
					int totalHhcs = psx.morph_life_times + psx.morph_mana_times + psx.morph_speed_times
							+ psx.morph_phy_times + psx.morph_mag_times;
					if (totalHhcs == 15) {
						Vo_8165_0 vo_20481_74 = new Vo_8165_0();
						vo_20481_74.msg = "幻化次数#R达到上限15次#n";
						vo_20481_74.active = 0;
						GameObjectChar.send(new M8165_0(), vo_20481_74);
						break;
					}
					// 基础成长
					int[] ins = { pet.getLife(), pet.getMana(), pet.getSpeed(), pet.getPhyAttack(),
							pet.getMagAttack() };
					// 做增量计算，各个资质
					if (Integer.valueOf(cost_type) == 1) {
						if (psx.morph_life_times+1>3) {
							Vo_8165_0 vo_20481_74 = new Vo_8165_0();
							vo_20481_74.msg = "气血最大幻化次数3次！";
							vo_20481_74.active = 0;
							GameObjectChar.send(new M8165_0(), vo_20481_74);
							return;
						}
						++psx.morph_life_stat;
						if (psx.morph_life_stat > 0  && psx.morph_life_stat % 5 == 0) {
							psx.pet_mana_shape += (int) (Math.round(ins[0] * 0.06));
							++psx.morph_life_times;
							psx.morph_life_stat = 0;
							Vo_8165_0 vo_20481_74 = new Vo_8165_0();
							vo_20481_74.msg = "#R气血幻化成功#n";
							vo_20481_74.active = 0;
							GameObjectChar.send(new M8165_0(), vo_20481_74);
						}
					} else if (Integer.valueOf(cost_type) == 2) {
						if (psx.morph_mana_times +1> 3) {
							Vo_8165_0 vo_20481_74 = new Vo_8165_0();
							vo_20481_74.msg = "法力达到最大幻化次数3次！";
							vo_20481_74.active = 0;
							GameObjectChar.send(new M8165_0(), vo_20481_74);
							return;
						}
						++psx.morph_mana_stat;
						if (psx.morph_mana_stat > 0 && psx.morph_mana_stat % 5 == 0) {
							psx.pet_speed_shape += (int) (Math.round(ins[1] * 0.06));
							++psx.morph_mana_times;
							psx.morph_mana_stat = 0;
							Vo_8165_0 vo_20481_74 = new Vo_8165_0();
							vo_20481_74.msg = "#R幻化成功#n";
							vo_20481_74.active = 0;
							GameObjectChar.send(new M8165_0(), vo_20481_74);
						}
					} else if (Integer.valueOf(cost_type) == 3) {
						if (psx.morph_speed_times+1> 3) {
							Vo_8165_0 vo_20481_74 = new Vo_8165_0();
							vo_20481_74.msg = "速度最大幻化次数3次！";
							vo_20481_74.active = 0;
							GameObjectChar.send(new M8165_0(), vo_20481_74);
							return;
						}
						++psx.morph_speed_stat;
						if (psx.morph_speed_stat > 0 && psx.morph_speed_stat % 5 == 0) {
							psx.pet_phy_shape += (int) (Math.round(ins[2] * 0.06));
							++psx.morph_speed_times;
							psx.morph_speed_stat = 0;
							Vo_8165_0 vo_20481_74 = new Vo_8165_0();
							vo_20481_74.msg = "#R幻化成功#n";
							vo_20481_74.active = 0;
							GameObjectChar.send(new M8165_0(), vo_20481_74);
						}
					} else if (Integer.valueOf(cost_type) == 4) {
						if (psx.morph_phy_times+1 > 3) {
							Vo_8165_0 vo_20481_74 = new Vo_8165_0();
							vo_20481_74.msg = "物攻最大幻化次数3次！";
							vo_20481_74.active = 0;
							GameObjectChar.send(new M8165_0(), vo_20481_74);
							return;
						}
						++psx.morph_phy_stat;
						if (psx.morph_phy_stat > 0 && psx.morph_phy_stat % 5 == 0) {
							psx.pet_mag_shape += (int) (Math.round(ins[3] * 0.06));
							++psx.morph_phy_times;
							psx.morph_phy_stat = 0;
							Vo_8165_0 vo_20481_74 = new Vo_8165_0();
							vo_20481_74.msg = "#R幻化成功#n";
							vo_20481_74.active = 0;
							GameObjectChar.send(new M8165_0(), vo_20481_74);
						}
					} else if (Integer.valueOf(cost_type) == 5) {
						if (psx.morph_mag_times +1 > 3) {
							Vo_8165_0 vo_20481_74 = new Vo_8165_0();
							vo_20481_74.msg = "法攻最大幻化次数3次！";
							vo_20481_74.active = 0;
							GameObjectChar.send(new M8165_0(), vo_20481_74);
							return;
						}
						++psx.morph_mag_stat;
						if (psx.morph_mag_stat > 0 && psx.morph_mag_stat % 5 == 0) {
							psx.rank += (int) (Math.round(ins[4] * 0.06));
							++psx.morph_mag_times;
							psx.morph_mag_stat = 0;
							Vo_8165_0 vo_20481_74 = new Vo_8165_0();
							vo_20481_74.msg = "#R幻化成功#n";
							vo_20481_74.active = 0;
							GameObjectChar.send(new M8165_0(), vo_20481_74);
						}
					}
					// 计算总资质
					psx.resist_point = psx.pet_mana_shape + psx.pet_speed_shape + psx.pet_phy_shape + psx.pet_mag_shape
							+ psx.rank;
					List<Petbeibao> list = new ArrayList<>();
					BasicAttributesUtils.petshuxing(petbeibao.petShuXing.get(0), petbeibao);
					// 这里是计算妖石伤害
					for (PetShuXing yaoshi : petbeibao.petShuXing) {
						// 在宠物的基础信息里面操作
						if (yaoshi.no >= 12 && yaoshi.no <= 15) {
							petbeibao.petShuXing.get(0).wiz += yaoshi.wiz;
							petbeibao.petShuXing.get(0).parry += yaoshi.parry;
							petbeibao.petShuXing.get(0).def += yaoshi.def;
							petbeibao.petShuXing.get(0).dex += yaoshi.dex;
							petbeibao.petShuXing.get(0).mana += yaoshi.mana;
							petbeibao.petShuXing.get(0).accurate += yaoshi.accurate;
						}
					}
					petbeibao.petShuXing.get(0).max_life = petbeibao.petShuXing.get(0).def;
					petbeibao.petShuXing.get(0).max_mana = petbeibao.petShuXing.get(0).dex;
					
					list.add(petbeibao);
					GameObjectChar.send(new MSG_UPDATE_PETS(), list);
					
					
					
					GameObjectChar.send(new M45670_0(), null);
					break;
				}
			}
			if(gameObjectChar.privilege == 0) {
				// 清理掉副宠
				int other_pet_id = 0;
				for (int l = 0; l < chara.pets.size(); ++l) {
					if (Integer.valueOf(other_pet) == chara.pets.get(l).no) {
						other_pet_id = chara.pets.get(l).id;
						chara.pets.remove(chara.pets.get(l));
						//删除宠物
						GameData.that.charaPetService.deleteByPrimaryKey(other_pet_id);
					}
				}
				Vo_12269_0 vo_12269_0 = new Vo_12269_0();
				vo_12269_0.id = other_pet_id;
				vo_12269_0.owner_id = 0;
				GameObjectChar.send(new M12269_0(), vo_12269_0);
				//幻化成功
				GameObjectChar.send(new CommonWrite(0xD09F), null);
			}
			return;
		}

		// 开启宠物羽化的功能
		if (type.equals("pet_open_eclosion")) {
			for (int i = 0; i < chara.pets.size(); ++i) {
				Petbeibao petbeibao = chara.pets.get(i);
				if (petbeibao.no == no) {
					Vo_20481_0 vo_20481_0 = new Vo_20481_0();
					vo_20481_0.msg = "恭喜，你的#Y" + petbeibao.petShuXing.get(0).str + "#n已成功#G开启羽化";
					vo_20481_0.time = (int) (System.currentTimeMillis() / 1000L);
					GameObjectChar.send(new M20481_0(), vo_20481_0);
					PetShuXing petShuXing = petbeibao.petShuXing.get(0);
					int quality = petbeibao.petShuXing.get(0).penetrate - 1;
					int[] appends = { petShuXing.pet_mana_shape, petShuXing.pet_speed_shape, petShuXing.pet_mag_shape,
							petShuXing.rank, petShuXing.pet_phy_shape };
					int[] ints = PetAttributesUtils.emergencePet(quality, petShuXing.attrib,
							petbeibao.petShuXing.get(0).eclosion_nimbus, petShuXing.max_eclosion_nimbus, 1, 0, 0,
							appends);
					if (ints[0] == 1) {
						petbeibao.petShuXing.get(0).eclosion_nimbus = 2;
					} else {
						petbeibao.petShuXing.get(0).eclosion_nimbus = 1;
					}
					petbeibao.petShuXing.get(0).max_eclosion_nimbus = ints[1];
					List<Petbeibao> list = new ArrayList<>();
					BasicAttributesUtils.petshuxing(petbeibao.petShuXing.get(0), petbeibao);
					list.add(petbeibao);
					GameObjectChar.send(new MSG_UPDATE_PETS(), list);
					GameUtil.removemunber(chara, "羽化丹", 1);
					GameObjectChar.send(new M53607_0(), null);
				}
			}
		}
		// 宠物羽化
		if (type.equals("pet_eclosion")) {
			for (int i = 0; i < chara.pets.size(); ++i) {
				Petbeibao petbeibao = chara.pets.get(i);
				if (petbeibao.no == no) {
					Vo_20481_0 vo_20481_0 = new Vo_20481_0();
					vo_20481_0.msg = "恭喜，你的#Y" + petbeibao.petShuXing.get(0).str + "#n获得灵气";
					vo_20481_0.time = (int) (System.currentTimeMillis() / 1000L);
					GameObjectChar.send(new M20481_0(), vo_20481_0);
					PetShuXing petShuXing = petbeibao.petShuXing.get(0);
					int quality = petbeibao.petShuXing.get(0).penetrate;
					if(quality == 1) {
						GameUtil.sendMeTips("野生宠物无法羽化");
						return;
					}
					String[] split = pos.split("\\|");
					if(split.length>6) {
						GameUtil.sendMeTips("最大使用6个羽化丹");
						return;
					}
					int pill = 0;
					int equiqmentMoney = 0;
					if (split.length == 1 && split[0] == "") {
						// 用金元宝买羽化丹羽化
						if (cost_type.equals("gold_coin")) {
							if(chara.goldCoin < 3108) {
								GameUtil.sendMeTips("金元宝不足。");
								return;
							}
							pill = 6;
							chara.goldCoin -= 3108;
						} else { // 用银元宝
							if(chara.silverCoin < 3108) {
								GameUtil.sendMeTips("银元宝不足。");
								return;
							}
							pill = 6;
							chara.silverCoin -= 3108;
						}
					} else {
						for (int j = 0; j < chara.backpack.size(); ++j) {
							Goods goods = chara.backpack.get(j);
							for (int k = 0; k < split.length; ++k) {
								if (goods.pos == Integer.parseInt(split[k])) {
									if (goods.goodsInfo.str.equals("羽化丹")) {
										++pill;
									}
								}
							}
						}
						if(pill<split.length) {
							GameUtil.sendMeTips("羽化丹不足");
							return;
						}
					}
					int[] appends2 = { petShuXing.mana_effect + 40, petShuXing.attack_effect + 40,
							petShuXing.mag_effect + 40, petShuXing.phy_absorb + 40, petShuXing.phy_effect + 40 };
					int[] ints2 = PetAttributesUtils.emergencePet(quality, petShuXing.attrib,
							petbeibao.petShuXing.get(0).status_yanchuan_shenjiao + 1, petShuXing.max_eclosion_nimbus,
							pill, equiqmentMoney, equiqmentMoney, appends2);
					if (ints2[0] == 1 && petbeibao.petShuXing.get(0).eclosion_nimbus == 1) {
						petbeibao.petShuXing.get(0).status_yanchuan_shenjiao++;
						petbeibao.petShuXing.get(0).max_eclosion_nimbus = 0;
						PetShuXing petShuXing2 = petbeibao.petShuXing.get(0);
						petShuXing2.pet_mana_shape += ints2[2] / 3;
						PetShuXing petShuXing3 = petbeibao.petShuXing.get(0);
						petShuXing3.pet_speed_shape += ints2[3] / 3;
						PetShuXing petShuXing4 = petbeibao.petShuXing.get(0);
						petShuXing4.pet_mag_shape += ints2[4] / 3;
						PetShuXing petShuXing5 = petbeibao.petShuXing.get(0);
						petShuXing5.rank += ints2[5] / 3;
						PetShuXing petShuXing6 = petbeibao.petShuXing.get(0);
						petShuXing6.pet_phy_shape += ints2[6] / 3;
					} else {
						petbeibao.petShuXing.get(0).max_eclosion_nimbus = ints2[1];
					}
					if (petbeibao.petShuXing.get(0).status_yanchuan_shenjiao > 2) {
						petbeibao.petShuXing.get(0).status_yanchuan_shenjiao = 2;
						PetShuXing petShuXing7 = petbeibao.petShuXing.get(0);
						++petShuXing7.eclosion_nimbus;
						petbeibao.petShuXing.get(0).max_eclosion_nimbus = 0;
					}
					List<Petbeibao> list2 = new ArrayList<>();
					BasicAttributesUtils.petshuxing(petbeibao.petShuXing.get(0), petbeibao);
					list2.add(petbeibao);
					GameObjectChar.send(new MSG_UPDATE_PETS(), list2);
					GameObjectChar.send(new M53607_0(), 1);
					ListVo_65527_0 vo_65527_0 = GameUtil.a65527(chara);
					GameObjectChar.send(new M65527_0(), vo_65527_0);
					if(split.length>0) {
						GameUtil.removemunber(chara, "羽化丹", pill);
					}
				}
			}
		}
		// 宠物点化
		if (type.equals("pet_enchant")) {
			for (int i = 0; i < chara.pets.size(); ++i) {
				Petbeibao petbeibao = chara.pets.get(i);
				if (petbeibao.no == no) {
					Vo_20481_0 vo_20481_0 = new Vo_20481_0();
					if (petbeibao.petShuXing.get(0).enchant_nimbus != 1) {
						return;
					}
					PetShuXing petShuXing = petbeibao.petShuXing.get(0);
					int quality = petbeibao.petShuXing.get(0).penetrate;
					if(quality == 1) {
						GameUtil.sendMeTips("野生宠物无法点化");
						return;
					}
					//如果是坐骑
					if(petShuXing.suit_light_effect>0) {
						quality = 3;
					}
					String[] split = pos.split("\\|");
					if(split.length>6) {
						GameUtil.sendMeTips("最大使用6个点化丹");
						return;
					}
					int pill = 0;
					for (int j = 0; j < chara.backpack.size(); ++j) {
						Goods goods = chara.backpack.get(j);
						for (int k = 0; k < split.length; ++k) {
							if (goods.pos == Integer.parseInt(split[k])) {
								if (goods.goodsInfo.str.equals("点化丹")) {
									++pill;
								}
							}
						}
					}
					if(pill < split.length) {
						GameUtil.sendMeTips("点化丹不足.");
						return;
					}
					vo_20481_0.msg = "恭喜，你的#Y" + petbeibao.petShuXing.get(0).str + "#n获得灵气";
					vo_20481_0.time = (int) (System.currentTimeMillis() / 1000L);
					GameObjectChar.send(new M20481_0(), vo_20481_0);
					
					int[] appends2 = { petShuXing.pet_mana_shape, petShuXing.pet_speed_shape, petShuXing.pet_mag_shape,
							petShuXing.rank, petShuXing.pet_phy_shape };
					int[] ints2 = PetAttributesUtils.dotPet(quality, petShuXing.attrib, petShuXing.max_enchant_nimbus,
							pill, 0, 0, appends2);
					if (ints2[0] == 1) {
						petbeibao.petShuXing.get(0).enchant_nimbus = 2;
					} else {
						petbeibao.petShuXing.get(0).enchant_nimbus = 1;
					}
					petbeibao.petShuXing.get(0).max_enchant_nimbus = ints2[1];
					// 气血
					petbeibao.petShuXing.get(0).pet_mana_shape = petbeibao.petShuXing.get(0).mana_effect + ints2[2]
							+ 40;
					// 法力
					petbeibao.petShuXing.get(0).pet_speed_shape = petbeibao.petShuXing.get(0).attack_effect + ints2[3]
							+ 40;
					// 物攻
					petbeibao.petShuXing.get(0).pet_mag_shape = petbeibao.petShuXing.get(0).mag_effect + ints2[4] + 40;
					// 法功
					petbeibao.petShuXing.get(0).rank = petbeibao.petShuXing.get(0).phy_absorb + ints2[5] + 40;
					// 速度
					petbeibao.petShuXing.get(0).pet_phy_shape = petbeibao.petShuXing.get(0).phy_effect + ints2[6] + 40;
					List<Petbeibao> list2 = new ArrayList<>();
					BasicAttributesUtils.petshuxing(petbeibao.petShuXing.get(0), petbeibao);
					list2.add(petbeibao);
					GameObjectChar.send(new MSG_UPDATE_PETS(), list2);
					GameUtil.removemunber(chara, "点化丹", pill);
					GameObjectChar.send(new M45670_0(), null);
				}
			}
		}
		// 开启宠物点化
		if (type.equals("pet_open_enchant")) {
			for (int i = 0; i < chara.pets.size(); ++i) {
				Petbeibao petbeibao = chara.pets.get(i);
				if (petbeibao.no == no) {
					if (petbeibao.petShuXing.get(0).enchant_nimbus != 0) {
						Vo_20481_0 vo_20481_0 = new Vo_20481_0();
						vo_20481_0.msg = "点化已完成";
						vo_20481_0.time = (int) (System.currentTimeMillis() / 1000L);
						GameObjectChar.send(new M20481_0(), vo_20481_0);
						return;
					}
					if(GameCommonUtil.getGoodsNum(chara, "点化丹")<=0) {
						GameUtil.sendMeTips("点化丹不足");
						return;
					}

					PetShuXing petShuXing = petbeibao.petShuXing.get(0);
					int quality = petbeibao.petShuXing.get(0).penetrate;
					if(quality == 1) {
						GameUtil.sendMeTips("野生宠物无法点化");
						return;
					}
					// 若未点化完成，则开启点化
					Vo_20481_0 vo_20481_0 = new Vo_20481_0();
					vo_20481_0.msg = "恭喜，你的#Y" + petbeibao.petShuXing.get(0).str + "#n已成功#G开启点化";
					vo_20481_0.time = (int) (System.currentTimeMillis() / 1000L);
					GameObjectChar.send(new M20481_0(), vo_20481_0);
					int[] appends = { petShuXing.pet_mana_shape, petShuXing.pet_speed_shape, petShuXing.pet_mag_shape,
							petShuXing.rank, petShuXing.pet_phy_shape };
					int[] ints = PetAttributesUtils.dotPet(quality, petShuXing.attrib, petShuXing.max_enchant_nimbus, 1,
							0, 0, appends);
					if (ints[0] == 1) {
						petbeibao.petShuXing.get(0).enchant_nimbus = 2;
					} else {
						petbeibao.petShuXing.get(0).enchant_nimbus = 1;
					}
					if (petbeibao.petShuXing.get(0).enchant_nimbus != 2) {
						petbeibao.petShuXing.get(0).max_enchant_nimbus = ints[1];
						petShuXing.pet_mana_shape += ints[2];
						petShuXing.pet_speed_shape += ints[3];
						petShuXing.pet_mag_shape += ints[4];
						petShuXing.rank += ints[5];
						petShuXing.pet_phy_shape += ints[6];
					}
					List<Petbeibao> list = new ArrayList<>();
					BasicAttributesUtils.petshuxing(petbeibao.petShuXing.get(0), petbeibao);
					list.add(petbeibao);
					GameObjectChar.send(new MSG_UPDATE_PETS(), list);
					GameUtil.removemunber(chara, "点化丹", 1);
					GameObjectChar.send(new M45670_0(), null);
				}
			}
		}
	}

	@Override
	public int cmd() {
		return 53314;
	}
}