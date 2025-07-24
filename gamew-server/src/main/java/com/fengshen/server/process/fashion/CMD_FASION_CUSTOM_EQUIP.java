package com.fengshen.server.process.fashion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Service;

import com.fengshen.core.util.ExecutorsUtils;
import com.fengshen.db.domain.FasionCustomInfo;
import com.fengshen.db.domain.PackModification;
import com.fengshen.server.data.GameReadTool;
import com.fengshen.server.data.vo.Vo_41505_0;
import com.fengshen.server.data.vo.Vo_4197_0;
import com.fengshen.server.data.vo.Vo_61677_0;
import com.fengshen.server.data.vo.Vo_8165_0;
import com.fengshen.server.data.vo.Vo_APPEAR;
import com.fengshen.server.data.vo.user.Vo_FASION_TEAM_ICON_LIST;
import com.fengshen.server.data.vo.user.Vo_UPDATE_APPEARANCE;
import com.fengshen.server.data.write.M41505_0;
import com.fengshen.server.data.write.M4197_0;
import com.fengshen.server.data.write.M61677_0;
import com.fengshen.server.data.write.M65525_0;
import com.fengshen.server.data.write.M65529_0;
import com.fengshen.server.data.write.M8165_0;
import com.fengshen.server.data.write.MSG_DISAPPEAR;
import com.fengshen.server.data.write.inventory.MSG_INVENTORY_REMOVE;
import com.fengshen.server.data.write.store.MSG_STORE_REMOVE;
import com.fengshen.server.data.write.user.MSG_FASION_TEAM_ICON_LIST;
import com.fengshen.server.data.write.user.MSG_UPDATE_APPEARANCE;
import com.fengshen.server.domain.Chara;
import com.fengshen.server.domain.Goods;
import com.fengshen.server.game.GameCommonUtil;
import com.fengshen.server.game.GameData;
import com.fengshen.server.game.GameHandler;
import com.fengshen.server.game.GameObjectChar;
import com.fengshen.server.game.GameUtil;
import com.google.common.collect.Lists;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

/**
 * 穿戴时装
 * 
 *
 */
@Service
public class CMD_FASION_CUSTOM_EQUIP implements GameHandler {
	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		String equip_str = GameReadTool.readString(buff);
		PackModification packModification = GameData.that.basePackModificationService.findOneByStr(equip_str);
		GameObjectChar gameObjectChar = GameObjectChar.getGameObjectChar();
		Chara chara = gameObjectChar.chara;
		if(packModification != null) {
			if (packModification.getCategory() == 1) {
				for (int i = 0; i < chara.otherGoods.size(); ++i) {
					if (chara.otherGoods.get(i).pos == 31) {
						chara.otherGoods.remove(chara.otherGoods.get(i));
						Vo_61677_0 vo_61677_0 = new Vo_61677_0();
						vo_61677_0.store_type = "fasion_store";
						vo_61677_0.npcID = 0;
						vo_61677_0.list = chara.shizhuang;
						vo_61677_0.count = chara.shizhuang.size();
						GameObjectChar.send(new M61677_0(), vo_61677_0);
						break;
					}
				}
				Goods goods2 = new Goods();
				goods2.goodsInfo.owner_id = 1;
				goods2.goodsInfo.value = 2097924;
				goods2.goodsInfo.quality = "金色";
				goods2.goodsInfo.alias = packModification.getAlias();
				goods2.goodsInfo.amount = 16;
				goods2.pos = 31;
				goods2.goodsInfo.food_num = 2;
				goods2.goodsInfo.master = chara.sex;
				goods2.goodsInfo.recognize_recognized = 0;
				goods2.goodsInfo.type = Integer.valueOf(packModification.getType());
				goods2.goodsInfo.total_score = 25;
				goods2.goodsInfo.damage_sel_rate = 1842075;
				goods2.goodsInfo.str = packModification.getStr();
				goods2.goodsInfo.metal = chara.polar;
				goods2.goodsInfo.durability = 8;
				goods2.goodsInfo.rebuild_level = 500;
				goods2.goodsInfo.auto_fight = GameCommonUtil.UUID().toLowerCase();
				chara.otherGoods.add(goods2);
				List<Goods> list = new ArrayList<Goods>();
				list.add(goods2);
				GameObjectChar.send(new M65525_0(), list);
				chara.special_icon = Integer.valueOf(packModification.getFasionType());
				Vo_61677_0 vo_61677_2 = new Vo_61677_0();
				vo_61677_2.store_type = "fasion_store";
				vo_61677_2.npcID = 0;
				vo_61677_2.count = 1;
				vo_61677_2.isGoon = 0;
				vo_61677_2.pos = packModification.getPosition();
				GameObjectChar.send(new MSG_STORE_REMOVE(), vo_61677_2);
				//这里要删除自定义时装，时装和自定义只能存在一个
				Iterator<Goods> iterator = chara.otherGoods.iterator();
				while(iterator.hasNext()) {
					Goods goods = iterator.next();
					if(goods.pos == 33 || goods.pos == 34 || goods.pos == 35 
							|| goods.pos == 36 || goods.pos == 38) {
						//并且删除原来的配置
						iterator.remove();
						GameObjectChar.send(new MSG_INVENTORY_REMOVE(), goods.pos);
					}
				}
			}
			if (packModification.getCategory() == 2) {
				for (int i = 0; i < chara.backpack.size(); ++i) {
					if (chara.otherGoods.get(i).pos == 32) {
						chara.otherGoods.remove(chara.backpack.get(i));
						Vo_61677_0 vo_61677_0 = new Vo_61677_0();
						vo_61677_0.store_type = "effect_store";
						vo_61677_0.npcID = 0;
						vo_61677_0.list = chara.texiao;
						vo_61677_0.count = chara.texiao.size();
						GameObjectChar.send(new M61677_0(), vo_61677_0);
					}
				}
				Goods goods2 = new Goods();
				goods2.goodsInfo.owner_id = 1;
				goods2.goodsInfo.value = 262;
				goods2.goodsInfo.quality = "金色";
				goods2.goodsInfo.alias = packModification.getStr();
				goods2.goodsInfo.amount = 17;
				goods2.pos = 32;
				goods2.goodsInfo.food_num = 0;
				goods2.goodsInfo.merge_rate = 0;
				goods2.goodsInfo.master = 0;
				goods2.goodsInfo.recognize_recognized = 2;
				goods2.goodsInfo.type = Integer.valueOf(packModification.getType());
				goods2.goodsInfo.total_score = 23;
				goods2.goodsInfo.damage_sel_rate = 809382;
				goods2.goodsInfo.str = packModification.getStr();
				goods2.goodsInfo.attrib = 0;
				goods2.goodsInfo.durability = 8;
				goods2.goodsInfo.rebuild_level = 0;
				goods2.goodsInfo.auto_fight = GameCommonUtil.UUID().toLowerCase() + packModification.getType();
				chara.otherGoods.add(goods2);
				List<Goods> list = new ArrayList<Goods>();
				list.add(goods2);
				GameObjectChar.send(new M65525_0(), list);
				GameObjectChar.send(new M65525_0(), chara.backpack);
				Vo_61677_0 vo_61677_2 = new Vo_61677_0();
				vo_61677_2.store_type = "effect_store";
				vo_61677_2.npcID = 0;
				vo_61677_2.count = 1;
				vo_61677_2.isGoon = 0;
				vo_61677_2.pos = packModification.getPosition();
				GameObjectChar.send(new MSG_STORE_REMOVE(), vo_61677_2);
				if(chara.effectIcons == null) {
					chara.effectIcons = new HashMap<>();
				}
				chara.effectIcons.put("fasionEffectIcon", Integer.valueOf(packModification.getFasionType()));
			}
			if (packModification.getCategory() == 3) {
				Vo_4197_0 vo_4197_0 = new Vo_4197_0();
				vo_4197_0.id = 0;
				gameObjectChar.gameMap.send(new M4197_0(), vo_4197_0);
				gameObjectChar.gameMap.send(new MSG_DISAPPEAR(), chara.genchong_icon);
				for (int j = 0; j < chara.otherGoods.size(); ++j) {
					if (chara.otherGoods.get(j).pos == 37) {
						chara.otherGoods.remove(chara.otherGoods.get(j));
						Vo_61677_0 vo_61677_3 = new Vo_61677_0();
						vo_61677_3.store_type = "follow_pet_store";
						vo_61677_3.npcID = 0;
						vo_61677_3.list = chara.genchong;
						vo_61677_3.count = chara.genchong.size();
						GameObjectChar.send(new M61677_0(), vo_61677_3);
					}
				}
				Goods goods = new Goods();
				goods.goodsInfo.owner_id = 1;
				goods.goodsInfo.value = 262;
				goods.goodsInfo.quality = "金色";
				goods.goodsInfo.alias = packModification.getStr();
				goods.goodsInfo.amount = 17;
				goods.pos = 37;
				goods.goodsInfo.food_num = 0;
				goods.goodsInfo.merge_rate = 0;
				goods.goodsInfo.master = 0;
				goods.goodsInfo.recognize_recognized = 2;
				goods.goodsInfo.type = Integer.valueOf(packModification.getType());
				goods.goodsInfo.total_score = 23;
				goods.goodsInfo.damage_sel_rate = 809382;
				goods.goodsInfo.str = packModification.getStr();
				goods.goodsInfo.attrib = 0;
				goods.goodsInfo.durability = 8;
				goods.goodsInfo.rebuild_level = 0;
				goods.goodsInfo.auto_fight = GameCommonUtil.UUID().toLowerCase() + packModification.getType();
				chara.otherGoods.add(goods);
				List<Goods> list2 = new ArrayList<Goods>();
				list2.add(goods);
				GameObjectChar.send(new M65525_0(), list2);
				Vo_61677_0 vo_61677_4 = new Vo_61677_0();
				vo_61677_4.store_type = "follow_pet_store";
				vo_61677_4.npcID = 0;
				vo_61677_4.count = 1;
				vo_61677_4.isGoon = 0;
				vo_61677_4.pos = packModification.getPosition();
				GameObjectChar.send(new MSG_STORE_REMOVE(), vo_61677_4);
				chara.genchong_icon = Integer.valueOf(packModification.getFasionType());
				Vo_APPEAR vo_65529_0 = new Vo_APPEAR();
				vo_65529_0.id = Integer.valueOf(packModification.getFasionType());
				vo_65529_0.x = chara.x;
				vo_65529_0.y = chara.y;
				vo_65529_0.dir = 5;
				vo_65529_0.icon = chara.genchong_icon;
				vo_65529_0.type = 32768;
				vo_65529_0.sub_type = 2;
				vo_65529_0.owner_id = chara.id;
				vo_65529_0.name = packModification.getStr();
				vo_65529_0.org_icon = chara.genchong_icon;
				vo_65529_0.portrait = chara.genchong_icon;
				gameObjectChar.gameMap.send(new M65529_0(), vo_65529_0);
			}
		}else {
			//自定义时装和队标
			FasionCustomInfo fc = GameData.that.fasionCustomInfoService
					.getOneFasionCustomInfoByName(equip_str);
			if(fc != null) {
				if(fc.getCategory() == 4) {
					List<String> itemList = new ArrayList<>();
					for (int i = 0; i < chara.backpack.size(); ++i) {
						Integer pos2 = chara.backpack.get(i).pos;
						if(pos2 == 33 || pos2 == 34 || pos2 == 35 
								|| pos2 == 36 || pos2 == 38) {
							itemList.add(chara.backpack.get(i).goodsInfo.str);
						}
					}
					itemList.add(equip_str);
					GameCommonUtil.getFasionCustomEquipEx(chara, itemList.toArray(new String[itemList.size()]));
				}
				
				if(fc.getCategory() == 5) {
					//把原来的删除了
					Iterator<Goods> iterator = chara.backpack.iterator();
					while(iterator.hasNext()) {
						Goods next = iterator.next();
						if(next.pos == 39) {
							iterator.remove();
							break;
						}
					}
					Goods goods = new Goods();
					goods.goodsInfo.owner_id = 1;
					goods.goodsInfo.value = 0;
					goods.goodsInfo.quality = "金色";
					goods.goodsInfo.alias = fc.getName();
					goods.goodsInfo.amount = 20;
					goods.goodsInfo.food_num = 2;
					goods.pos = 39;
					goods.goodsInfo.master = 0;
					goods.goodsInfo.recognize_recognized = 2;
					goods.goodsInfo.type = fc.getIcon();
					goods.goodsInfo.total_score = 29;
					goods.goodsInfo.damage_sel_rate = 1842075;
					goods.goodsInfo.str = fc.getName();
					goods.goodsInfo.metal = chara.polar;
					goods.goodsInfo.durability = 8;
					goods.goodsInfo.rebuild_level = 500;
					goods.goodsInfo.auto_fight = GameCommonUtil.UUID().toLowerCase();
					chara.otherGoods.add(goods);
					GameObjectChar.send(new M65525_0(), Lists.newArrayList(goods));
					chara.teamIcon = fc.getIcon();
					ExecutorsUtils.getExecutorPools().execute(new Runnable() {
						@Override
						public void run() {
							List<Vo_FASION_TEAM_ICON_LIST> items = new ArrayList<>();
							List<FasionCustomInfo> fcs = GameData.that.fasionCustomInfoService.getFasionCustomInfoByCategory(5);
							Vo_61677_0 removeTeam = new Vo_61677_0();;
							removeTeam.store_type = "team_icon_store";
							removeTeam.npcID = 0;
							removeTeam.count = 1;
							removeTeam.isGoon = 0;
							for (FasionCustomInfo f : fcs) {
								if(f.getIcon()==chara.teamIcon) {
									removeTeam.pos = f.getPosition();
									continue;
								}
								items.add(new Vo_FASION_TEAM_ICON_LIST(f.getName(), f.getGoodsPrice()));
							}
							//如果为零则标识
							if(removeTeam.pos == 0) {
								removeTeam.pos = 39;
							}
							gameObjectChar.sendOne(new MSG_FASION_TEAM_ICON_LIST(), items);
							Vo_41505_0 vo_41505_0 = new Vo_41505_0();
							vo_41505_0.type = "view_team";
							gameObjectChar.sendOne(new M41505_0(), vo_41505_0);
							List<Goods> teamIconStore = chara.teamIconStore;
							Vo_61677_0 vo_61677_0 = new Vo_61677_0();
							vo_61677_0.store_type = "team_icon_store";
							vo_61677_0.npcID = 0;
							
							List<Goods> showTeanIconStores = new ArrayList<>();
							for(Goods g:teamIconStore) {
								if(g.pos != removeTeam.pos) {
									showTeanIconStores.add(g);
								}
							}
							vo_61677_0.list = showTeanIconStores;
							vo_61677_0.count = showTeanIconStores.size();
							gameObjectChar.sendOne(new M61677_0(), vo_61677_0);
							gameObjectChar.sendOne(new MSG_STORE_REMOVE(), removeTeam);
						}
					});
				}
			}
			
		}
		Vo_UPDATE_APPEARANCE vo_61661_0 = GameUtil.a61661(chara);
		gameObjectChar.gameMap.send(new MSG_UPDATE_APPEARANCE(), vo_61661_0);
		Vo_8165_0 vo_8165_0 = new Vo_8165_0();
		vo_8165_0.msg = "穿戴成功";
		vo_8165_0.active = 0;
		GameObjectChar.send(new M8165_0(), vo_8165_0);
		Vo_41505_0 vo_41505_0 = new Vo_41505_0();
		vo_41505_0.type = "equip_fasion";
		GameObjectChar.send(new M41505_0(), vo_41505_0);
	}

	@Override
	public int cmd() {
		return 41490;
	}
}