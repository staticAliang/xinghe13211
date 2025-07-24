package com.fengshen.server.fight;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import com.fengshen.server.data.vo.Vo_11719_0;
import com.fengshen.server.data.vo.Vo_53717_0;
import com.fengshen.server.data.vo.fight.Vo_C_ACTION;
import com.fengshen.server.data.vo.fight.Vo_C_END_ACTION;
import com.fengshen.server.data.vo.fight.Vo_C_OPPONENT_INFO;
import com.fengshen.server.data.vo.fight.Vo_C_SET_CUSTOM_MSG;
import com.fengshen.server.data.write.M11719_0;
import com.fengshen.server.data.write.M53717_0;
import com.fengshen.server.data.write.M65525_0;
import com.fengshen.server.data.write.fight.c.MSG_C_ACTION;
import com.fengshen.server.data.write.fight.c.MSG_C_DIALOG_OK;
import com.fengshen.server.data.write.fight.c.MSG_C_END_ACTION;
import com.fengshen.server.data.write.fight.c.MSG_C_OPPONENT_INFO;
import com.fengshen.server.data.write.fight.c.MSG_C_SET_CUSTOM_MSG;
import com.fengshen.server.data.write.inventory.MSG_INVENTORY_REMOVE;
import com.fengshen.server.domain.Chara;
import com.fengshen.server.domain.Goods;
import com.fengshen.server.domain.GoodsBasics;
import com.fengshen.server.domain.GoodsInfo;
import com.fengshen.server.domain.JiNeng;
import com.fengshen.server.game.GameObjectChar;
import com.fengshen.server.game.GameObjectCharMng;
import com.fengshen.server.game.GameUtil;
import com.fengshen.server.util.GameConfig;
import com.google.common.collect.Lists;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UseItemSkill implements FightSkill {
	@Override
	public List<FightResult> doSkill(FightContainer fightContainer, FightRequest fightRequest, JiNeng jiNeng) {

		FightObject fightObject = FightManager.getFightObject(fightContainer, fightRequest.vid);
		List<FightResult> resultList = new ArrayList<FightResult>();
		if(fightObject == null) {
			Vo_C_ACTION vo_19959_0 = new Vo_C_ACTION();
			vo_19959_0.round = fightContainer.round;
			vo_19959_0.aid = fightRequest.id;
			vo_19959_0.action = 0;
			vo_19959_0.vid = 0;
			vo_19959_0.para = 0;
			FightManager.send(fightContainer, new MSG_C_ACTION(), vo_19959_0);
			FightManager.send(fightContainer, new MSG_C_END_ACTION(), new Vo_C_END_ACTION(fightRequest.id));
			return resultList;
		}
		FightObject thisFightObject = FightManager.getFightObject(fightContainer, fightRequest.id);
		// 如果自己已经死亡是无法使用物品的
		if(thisFightObject == null || thisFightObject.isDead()) {
			Vo_C_ACTION vo_19959_0 = new Vo_C_ACTION();
			vo_19959_0.round = fightContainer.round;
			vo_19959_0.aid = fightRequest.id;
			vo_19959_0.action = 0;
			vo_19959_0.vid = 0;
			vo_19959_0.para = 0;
			FightManager.send(fightContainer, new MSG_C_ACTION(), vo_19959_0);
			FightManager.send(fightContainer, new MSG_C_END_ACTION(), new Vo_C_END_ACTION(fightRequest.id));
			return null;
		}
		
		//如果itemType为8888
		if(fightRequest.item_type == 8888) {
			Vo_C_ACTION vo_19959_0 = new Vo_C_ACTION();
			vo_19959_0.round = fightContainer.round;
			vo_19959_0.aid = fightRequest.id;
			vo_19959_0.action = fightRequest.action;
			vo_19959_0.vid = fightRequest.vid;
			vo_19959_0.para = fightRequest.para;
			FightManager.send(fightContainer, new MSG_C_ACTION(), vo_19959_0);
			
			//通知客户端战斗操作结果
			Vo_53717_0 vo_53717_0 = new Vo_53717_0();
			vo_53717_0.attacker_id = fightRequest.id;
			vo_53717_0.victim_id = fightRequest.vid;
			vo_53717_0.type = 4;
			vo_53717_0.result = 1;
			vo_53717_0.itemName = "血玲珑";
			FightManager.send(fightContainer, new M53717_0(), vo_53717_0);
			
			
			Vo_11719_0 vo_11719_0 = new Vo_11719_0();
			vo_11719_0.id = fightRequest.vid;
			vo_11719_0.owner_id = fightRequest.vid;
			vo_11719_0.no = 1002;
			FightManager.send(fightContainer, new M11719_0(), vo_11719_0);
			
			int shengming = fightObject.addShengming((int) (fightObject.max_shengming*0.1));
			if (fightObject.state.get() == 2 || fightObject.state.get() == 3) {
				fightObject.state.set(1);
				fightObject.revive(fightContainer);
			} else {
				FightResult fightResult = new FightResult();
				fightResult.id = fightRequest.vid;
				fightResult.vid = fightRequest.vid;
				fightResult.point = shengming;
				FightManager.send_LIFE_DELTA(fightContainer, fightResult);
			}
			log.info("自动怪物加血.......执行人:{},受益人={}",thisFightObject.str,fightObject.str);
			FightManager.send(fightContainer, new MSG_C_END_ACTION(), new Vo_C_END_ACTION(fightRequest.id));
			return resultList;
		}
		
		int id = thisFightObject.fid;
		if(thisFightObject.type == 2) {
			id = thisFightObject.cid;
		}
		//需要
		GameObjectChar gameObjectChar = GameObjectCharMng.getGameObjectChar(id);
		if(gameObjectChar == null) {
			FightManager.defenseAction(fightContainer, fightRequest);
			return resultList;
		}
		Chara chara = gameObjectChar.chara;
		
		Vo_C_ACTION vo_19959_0 = new Vo_C_ACTION();
		vo_19959_0.round = fightContainer.round;
		vo_19959_0.aid = fightRequest.id;
		vo_19959_0.action = fightRequest.action;
		vo_19959_0.vid = fightRequest.vid;
		vo_19959_0.para = fightRequest.para;
		FightManager.send(fightContainer, new MSG_C_ACTION(), vo_19959_0);
		String itemName = "";
		int point = 0;
		int no = 0;
		switch (fightRequest.item_type) {
		case 9050:
			// 血玲珑
			itemName = "血玲珑";
			if (fightObject.guaiwulevel < 120) {
				// 小于120级气血每次最大恢复39000+最大气血百分之10
				point += (39000 + fightObject.max_shengming * 0.2);
			} else {
				// 大于120级气血每次最大恢复50000+最大气血百分之20
				point += 50000 + (fightObject.max_shengming * 0.4);
			}
			no = 1002;
			break;
		case 9073:
			itemName = "高级血玲珑";
			if (fightObject.guaiwulevel < 120) {
				point += (39000 + fightObject.max_shengming * 0.2);
			} else {
				point += 50000 + (fightObject.max_shengming * 0.4);
			}
			no = 1002;
			break;
		case 9121:
			itemName = "中级血玲珑";
			if (fightObject.guaiwulevel < 120) {
				// 小于120级气血每次最大恢复39000+最大气血百分之10
				point += 39000 + (fightObject.max_shengming * 0.2);
			} else {
				// 大于120级气血每次最大恢复50000+最大气血百分之20
				point += 50000 + (fightObject.max_shengming * 0.4);
			}
			no = 1002;
			break;
		case 9051:
			// 法玲珑
			itemName = "法玲珑";
			if (fightObject.guaiwulevel < 120) {
				point += 26000 + (fightObject.max_mofa * 0.2);
			} else {
				point += 30000 + (fightObject.max_mofa * 0.4);
			}
			no = 1003;
			break;
		case 9122:
			// 法玲珑
			itemName = "中级法玲珑";
			if (fightObject.guaiwulevel < 120) {
				point += 26000 + (fightObject.max_mofa * 0.2);
			} else {
				point += 30000 + (fightObject.max_mofa * 0.4);
			}
			no = 1003;
			break;
		case 9074:
			// 法玲珑
			itemName = "高级法玲珑";
			if (fightObject.guaiwulevel < 120) {
				point += 26000 + (fightObject.max_mofa * 0.2);
			} else {
				point += 30000 + (fightObject.max_mofa * 0.4);
			}
			no = 1003;
			break;
		}
		//通知客户端战斗操作结果
		Vo_53717_0 vo_53717_0 = new Vo_53717_0();
		vo_53717_0.attacker_id = fightRequest.id;
		vo_53717_0.victim_id = fightRequest.vid;
		vo_53717_0.type = 4;
		vo_53717_0.result = 1;
		vo_53717_0.itemName = itemName;
		FightManager.send(fightContainer, new M53717_0(), vo_53717_0);
		
		
		Vo_11719_0 vo_11719_0 = new Vo_11719_0();
		vo_11719_0.id = fightRequest.vid;
		vo_11719_0.owner_id = fightRequest.vid;
		vo_11719_0.no = no;
		FightManager.send(fightContainer, new M11719_0(), vo_11719_0);

		FightResult fightResult = new FightResult();
		if(itemName.endsWith("法玲珑")) {
			List<Goods> backpack = gameObjectChar.chara.backpack;
			for (Goods g : backpack) {
				if (g.pos == fightRequest.para) {
					GoodsBasics goodsBasics = g.goodsBasics;
					GoodsInfo goodsInfo = g.goodsInfo;
					if (itemName.indexOf("法玲珑") != -1) {
						if(goodsBasics.max_mana < point) {
							point = fightObject.max_mofa;
						}
						int mofa = fightObject.addMoFa(point);
						// 如果当前的容量小于目标数,那就直接把point变成目标数
						if (goodsBasics.max_mana <= point) {
							// 删除这个道具
							GameUtil.removemunber(gameObjectChar.chara, g, 1);
							gameObjectChar.sendTips(itemName + "已用尽");
						} else {
							//当需要加的点数小于当前最大数，那就让点数变成需要加的点
							if(point > mofa) {
								point = mofa;
							}
							goodsBasics.max_mana -= point;
							goodsInfo.phy_rebuild_level = "剩余法力："
									+ NumberFormat.getNumberInstance(Locale.CHINA).format(goodsBasics.max_mana);
							// 刷新数据
							gameObjectChar.sendOne(new M65525_0(), Lists.newArrayList(g));
						}
						fightResult.id = fightRequest.vid;
						fightResult.vid = fightRequest.vid;
						fightResult.point = mofa;
						FightManager.send_LIFE_DELTA(fightContainer, fightResult);
					}
					break;
				}
			}
		}else if(itemName.endsWith("血玲珑")) {
			Goods goods = null;
			List<Goods> backpack = gameObjectChar.chara.backpack;
			for (Goods g : backpack) {
				if (g.pos == fightRequest.para) {
					goods = g;
					GoodsBasics goodsBasics = goods.goodsBasics;
					//如果当前容量小于要加的血量，则直接覆盖
					if (goodsBasics.max_life < point) {
						point = goodsBasics.max_life;
					}
					break;
				}
			}
			if(goods != null) {
				log.info("加血量:{}",point);
				int shengming = fightObject.addShengming(point);
				if (fightObject.state.get() == 2) {
					fightObject.state.set(1);
					fightObject.revive(fightContainer);
				} else {
					fightResult.id = fightRequest.vid;
					fightResult.vid = fightRequest.vid;
					fightResult.point = shengming;
					FightManager.send_LIFE_DELTA(fightContainer, fightResult);
				}
				// 刷新道具容量
				GoodsBasics goodsBasics = goods.goodsBasics;
				GoodsInfo goodsInfo = goods.goodsInfo;
				// 如果当前的容量小于目标数,那就直接把point变成目标数
				if (goodsBasics.max_life <= point) {
					// 删除这个道具
					GameUtil.removemunber(gameObjectChar.chara, goods, 1);
					gameObjectChar.sendTips(itemName + "已用尽");
				} else {
					//如果目标数大于当前需要加的最大值那就让他一样
					if(point>shengming) {
						point = shengming;
					}
					goodsBasics.max_life -= point;
					goodsInfo.phy_rebuild_level = "剩余血量："
							+ NumberFormat.getNumberInstance(Locale.CHINA).format(goodsBasics.max_life);
					// 刷新数据
					gameObjectChar.sendOne(new M65525_0(), Lists.newArrayList(goods));
				}
			}
			log.info("气血恢复={},使用道具={},最大生命值:{}", point, itemName, fightObject.max_shengming);
		}else if(fightRequest.item_type == 9031) {
			if(GameConfig.menuAuths.indexOf("hyjj") != -1) {
				Iterator<Goods> iterator = chara.backpack.iterator();
				while(iterator.hasNext()) {
					Goods g = iterator.next();
					if (g.pos == fightRequest.para) {
						//5回合,当前使用的话就得扣掉一个回合
						fightContainer.hyjjRound = 5;
						fightContainer.hyjjUseCid = chara.id;
						g.goodsInfo.durability2+=1;
						FightTeam fightTeamDM = FightManager.getFightTeamDM(fightContainer, gameObjectChar.chara.id);
						List<FightObject> fightObjects = fightTeamDM.fightObjectList;
						List<Vo_C_OPPONENT_INFO> showLifes = new ArrayList<>();
						for(FightObject fight:fightObjects) {
							Vo_C_OPPONENT_INFO info = new Vo_C_OPPONENT_INFO(fight.fid);
							info.getBuildFields().put("life", fight.shengming);
							info.getBuildFields().put("max_life", fight.max_shengming);
							showLifes.add(info);
						}
						//显示敌方气血
						FightManager.sendTeam(fightContainer, FightManager.getFightTeam(fightContainer, chara.id).fightObjectList,new MSG_C_OPPONENT_INFO(), showLifes);
						Vo_C_SET_CUSTOM_MSG msg = new Vo_C_SET_CUSTOM_MSG();
						msg.setId(id);
						msg.setMsg("你使用了#R火眼金睛#n，长按对方形象可显示气血。");
						msg.setServerName(GameConfig.lineName);
						msg.setShowTime(2);
						msg.setChannel(17);
						msg.setVipType(0);
						gameObjectChar.sendOne(new MSG_C_SET_CUSTOM_MSG(), msg);
						if(g.goodsInfo.durability2>=g.goodsInfo.max_durability2) {
							if(g.goodsInfo.owner_id>1) {
								g.goodsInfo.owner_id-=1;
								g.goodsInfo.durability2 = 0;
								gameObjectChar.sendOne(new M65525_0(), Lists.newArrayList(g));
							}else {
								//删除物品
								iterator.remove();
								gameObjectChar.sendOne(new MSG_INVENTORY_REMOVE(), g.pos);
								gameObjectChar.sendOne(new MSG_C_DIALOG_OK(), "火眼金睛已耗尽");
							}
						}else {
							//刷新物品
							gameObjectChar.sendOne(new M65525_0(), Lists.newArrayList(g));
						}
						break;
					}
				
				}
			}
		}
		FightManager.send(fightContainer, new MSG_C_END_ACTION(), new Vo_C_END_ACTION(fightRequest.id));
		return resultList;
	}

	@Override
	public int getStateType() {
		return 0;
	}
}