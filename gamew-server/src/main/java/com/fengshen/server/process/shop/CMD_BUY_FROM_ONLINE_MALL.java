package com.fengshen.server.process.shop;

import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Service;

import com.fengshen.db.domain.StoreGoods;
import com.fengshen.db.domain.StoreInfo;
import com.fengshen.db.domain.ZhuangbeiInfo;
import com.fengshen.server.data.GameReadTool;
import com.fengshen.server.data.vo.ListVo_65527_0;
import com.fengshen.server.data.vo.Vo_20480_0;
import com.fengshen.server.data.vo.Vo_40964_0;
import com.fengshen.server.data.vo.Vo_8165_0;
import com.fengshen.server.data.write.M20480_0;
import com.fengshen.server.data.write.M40964_0;
import com.fengshen.server.data.write.M65527_0;
import com.fengshen.server.data.write.M8165_0;
import com.fengshen.server.domain.Chara;
import com.fengshen.server.game.GameCommonUtil;
import com.fengshen.server.game.GameData;
import com.fengshen.server.game.GameHandler;
import com.fengshen.server.game.GameObjectChar;
import com.fengshen.server.game.GameUtil;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

/**
 * 在线商城购买商品
 * 
 *
 */
@Service
@Slf4j
public class CMD_BUY_FROM_ONLINE_MALL implements GameHandler {
	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		String barcode = GameReadTool.readString(buff);
		int amount = GameReadTool.readShort(buff);
		String coin_pwd = GameReadTool.readString(buff);
		String coin_type = GameReadTool.readString(buff);
		GameObjectChar gameObjectChar = GameObjectChar.getGameObjectChar();
		Chara chara = gameObjectChar.chara;
		if (barcode.equals("C0000001")) {
			if (chara.goldCoin < 300) {
				Vo_8165_0 vo_8165_0 = new Vo_8165_0();
				vo_8165_0.msg = "元宝不足";
				vo_8165_0.active = 0;
				GameObjectChar.send(new M8165_0(), vo_8165_0);
				return;
			}
			GameUtil.addCash(gameObjectChar, 3000000);
			chara.goldCoin -= 300;
			Vo_40964_0 vo_40964_0 = new Vo_40964_0();
			vo_40964_0.type = 3;
			vo_40964_0.name = "金钱";
			vo_40964_0.param = "3000000";
			vo_40964_0.rightNow = 0;
			GameObjectChar.send(new M40964_0(), vo_40964_0);
			Vo_20480_0 vo_20480_0 = new Vo_20480_0();
			vo_20480_0.msg = "你花费#R300#n个金元宝购买了#Y3,000,000#n文钱#n。";
			vo_20480_0.time = (int) (System.currentTimeMillis() / 1000L);
			GameObjectChar.send(new M20480_0(), vo_20480_0);
			Vo_8165_0 vo_8165_2 = new Vo_8165_0();
			vo_8165_2.msg = "购买成功";
			vo_8165_2.active = 0;
			GameObjectChar.send(new M8165_0(), vo_8165_2);
		} else if (barcode.equals("C0000002")) {
			if (chara.goldCoin < 600) {
				Vo_8165_0 vo_8165_0 = new Vo_8165_0();
				vo_8165_0.msg = "元宝不足";
				vo_8165_0.active = 0;
				GameObjectChar.send(new M8165_0(), vo_8165_0);
				return;
			}
			GameUtil.addCash(gameObjectChar, 6000000);
			chara.goldCoin -= 600;
			Vo_40964_0 vo_40964_0 = new Vo_40964_0();
			vo_40964_0.type = 3;
			vo_40964_0.name = "金钱";
			vo_40964_0.param = "6000000";
			vo_40964_0.rightNow = 0;
			GameObjectChar.send(new M40964_0(), vo_40964_0);
			Vo_20480_0 vo_20480_0 = new Vo_20480_0();
			vo_20480_0.msg = "你花费#R600#n个金元宝购买了#Y6,000,000#n文钱#n。";
			vo_20480_0.time = (int) (System.currentTimeMillis() / 1000L);
			GameObjectChar.send(new M20480_0(), vo_20480_0);
			Vo_8165_0 vo_8165_2 = new Vo_8165_0();
			vo_8165_2.msg = "购买成功";
			vo_8165_2.active = 0;
			GameObjectChar.send(new M8165_0(), vo_8165_2);
		} else if (barcode.equals("C0000003")) {
			if (chara.goldCoin < 1100) {
				Vo_8165_0 vo_8165_0 = new Vo_8165_0();
				vo_8165_0.msg = "元宝不足";
				vo_8165_0.active = 0;
				GameObjectChar.send(new M8165_0(), vo_8165_0);
				return;
			}
			GameUtil.addCash(gameObjectChar, 10000000);
			chara.goldCoin -= 1100;
			Vo_40964_0 vo_40964_0 = new Vo_40964_0();
			vo_40964_0.type = 3;
			vo_40964_0.name = "金钱";
			vo_40964_0.param = "6000000";
			vo_40964_0.rightNow = 0;
			GameObjectChar.send(new M40964_0(), vo_40964_0);
			Vo_20480_0 vo_20480_0 = new Vo_20480_0();
			vo_20480_0.msg = "你花费#R1100#n个金元宝购买了#Y10,000,000#n文钱#n。";
			vo_20480_0.time = (int) (System.currentTimeMillis() / 1000L);
			GameObjectChar.send(new M20480_0(), vo_20480_0);
			Vo_8165_0 vo_8165_2 = new Vo_8165_0();
			vo_8165_2.msg = "购买成功";
			vo_8165_2.active = 0;
			GameObjectChar.send(new M8165_0(), vo_8165_2);
		} else if (barcode.equals("C0000004")) {
			if (chara.goldCoin < 3300) {
				Vo_8165_0 vo_8165_0 = new Vo_8165_0();
				vo_8165_0.msg = "元宝不足";
				vo_8165_0.active = 0;
				GameObjectChar.send(new M8165_0(), vo_8165_0);
				return;
			}
			GameUtil.addCash(gameObjectChar, 30000000);
			chara.goldCoin -= 3300;
			Vo_40964_0 vo_40964_0 = new Vo_40964_0();
			vo_40964_0.type = 3;
			vo_40964_0.name = "金钱";
			vo_40964_0.param = "6000000";
			vo_40964_0.rightNow = 0;
			GameObjectChar.send(new M40964_0(), vo_40964_0);
			Vo_20480_0 vo_20480_0 = new Vo_20480_0();
			vo_20480_0.msg = "你花费#R3300#n个金元宝购买了#Y30,000,000#n文钱#n。";
			vo_20480_0.time = (int) (System.currentTimeMillis() / 1000L);
			GameObjectChar.send(new M20480_0(), vo_20480_0);
			Vo_8165_0 vo_8165_2 = new Vo_8165_0();
			vo_8165_2.msg = "购买成功";
			vo_8165_2.active = 0;
			GameObjectChar.send(new M8165_0(), vo_8165_2);
		} else if (barcode.equals("C0000005")) {
			if (chara.goldCoin < 7200) {
				Vo_8165_0 vo_8165_0 = new Vo_8165_0();
				vo_8165_0.msg = "元宝不足";
				vo_8165_0.active = 0;
				GameObjectChar.send(new M8165_0(), vo_8165_0);
				return;
			}
			GameUtil.addCash(gameObjectChar, 60000000);
			chara.goldCoin -= 7200;
			Vo_40964_0 vo_40964_0 = new Vo_40964_0();
			vo_40964_0.type = 3;
			vo_40964_0.name = "金钱";
			vo_40964_0.param = "6000000";
			vo_40964_0.rightNow = 0;
			GameObjectChar.send(new M40964_0(), vo_40964_0);
			Vo_20480_0 vo_20480_0 = new Vo_20480_0();
			vo_20480_0.msg = "你花费#R7200#n个金元宝购买了#Y60,000,000#n文钱#n。";
			vo_20480_0.time = (int) (System.currentTimeMillis() / 1000L);
			GameObjectChar.send(new M20480_0(), vo_20480_0);
			Vo_8165_0 vo_8165_2 = new Vo_8165_0();
			vo_8165_2.msg = "购买成功";
			vo_8165_2.active = 0;
			GameObjectChar.send(new M8165_0(), vo_8165_2);
		} else if(barcode.equals("C0000006")){
			GameUtil.addCash(gameObjectChar, 100000000);
			chara.goldCoin -= 12000;
			Vo_40964_0 vo_40964_0 = new Vo_40964_0();
			vo_40964_0.type = 3;
			vo_40964_0.name = "金钱";
			vo_40964_0.param = "100000000";
			vo_40964_0.rightNow = 0;
			GameObjectChar.send(new M40964_0(), vo_40964_0);
			Vo_20480_0 vo_20480_0 = new Vo_20480_0();
			vo_20480_0.msg = "你花费#R12000#n个金元宝购买了#Y100,000,000#n文钱#n。";
			vo_20480_0.time = (int) (System.currentTimeMillis() / 1000L);
			GameObjectChar.send(new M20480_0(), vo_20480_0);
			Vo_8165_0 vo_8165_2 = new Vo_8165_0();
			vo_8165_2.msg = "购买成功";
			vo_8165_2.active = 0;
			GameObjectChar.send(new M8165_0(), vo_8165_2);
		}else {
			StoreGoods oneByBarcode = GameData.that.baseStoreGoodsService.findOneByBarcode(barcode);
			if (oneByBarcode == null) {
				GameUtil.sendMeTips("没有找到该商品！");
				return;
			}
			// 需要消耗的元宝
			int costMoney = oneByBarcode.getCoin() * amount;
			String moneyType = "银";
			// 银元宝不足消耗的
			int costSilverCoin = 0;
			// 银元宝不足消耗的，用金元宝替代
			int costGoldCoin = 0;
			// 购买类型，0:银元宝,1:金元宝, 2:通用----for_sale
			// 如果玩家主动选择了金元宝那就直接扣除金元宝
			if ("silverCoin".equals(coin_type)) {
				if (chara.goldCoin < oneByBarcode.getCoin() * amount) {
					Vo_8165_0 vo_8165_0 = new Vo_8165_0();
					vo_8165_0.msg = "金元宝不足！";
					vo_8165_0.active = 0;
					GameObjectChar.send(new M8165_0(), vo_8165_0);
					return;
				}
				// 扣除金元宝
				chara.goldCoin -= costMoney;
				moneyType = "金";
			} else {// 如果用户未主动选择类型
					// 商品的类型为银元宝，如果银元宝不足则用金元宝代替。如果金银都不足则购买失败
				if (oneByBarcode.getForSale() == 0 || oneByBarcode.getForSale() == 2) {
					// 如果银元宝数量不足则用金元宝补充
					if (chara.silverCoin < oneByBarcode.getCoin() * amount) {
						// 不足部分用金元宝补充
						costGoldCoin = costMoney - chara.silverCoin;
						// 如果金元宝补充还不足的话.那就直接返回
						if (chara.goldCoin < costGoldCoin) {
							GameUtil.sendMeTips("金银元宝不足");
							return;
						}
						// 采用金元宝购买
						moneyType = "金";
						// 扣除补充的数量
						chara.goldCoin -= costGoldCoin;
						// 银元宝不用扣了，直接恢复为0了
						chara.silverCoin = 0;
						// 消耗的银元宝
						costSilverCoin = costMoney - costGoldCoin;
					} else {
						// 采用银元宝购买
						chara.silverCoin -= costMoney;
					}
				} else if (oneByBarcode.getForSale() == 1) { // 商品限制只允许金元宝购买
					if (chara.goldCoin < oneByBarcode.getCoin() * amount) {
						GameUtil.sendMeTips("金元宝不足");
						return;
					}
					moneyType = "金";
					chara.goldCoin -= costMoney;
				}
			}
			//首饰
			if (barcode.equals("R0004026") || barcode.equals("R0004025") || barcode.equals("R0004024")) {
				ZhuangbeiInfo oneByStr = GameData.that.baseZhuangbeiInfoService
						.findOneByStr(oneByBarcode.getName());
				// 添加首饰到背包
				GameCommonUtil.addEquipToBackpack(gameObjectChar, oneByStr, 0, amount);
				Vo_40964_0 vo_40964_2 = new Vo_40964_0();
				vo_40964_2.type = 1;
				vo_40964_2.name = oneByStr.getStr();
				vo_40964_2.param = "-1";
				vo_40964_2.rightNow = 0;
				GameObjectChar.send(new M40964_0(), vo_40964_2);
			} else {
				//添加物品
				StoreInfo storeInfo = GameData.that.baseStoreInfoService.findOneByName(oneByBarcode.getName());
				GameUtil.huodedaoju(gameObjectChar, storeInfo, amount);
				Vo_40964_0 vo_40964_2 = new Vo_40964_0();
				vo_40964_2.type = 1;
				vo_40964_2.name = storeInfo.getName();
				vo_40964_2.param = "-1";
				vo_40964_2.rightNow = 0;
				GameObjectChar.send(new M40964_0(), vo_40964_2);
			}
			if (costSilverCoin != 0) {
				// 表示用用金元宝补充了costSilverCoin costGoldCoin
				GameUtil.sendMeTips("你花费#R" + costSilverCoin + "#n个银元宝和#R" + costGoldCoin + "#n个金元宝购买了#R" + amount
						+ "#n个#Y" + oneByBarcode.getName() + "#n。");
			} else {
				GameUtil.sendMeTips("你花费#R" + costMoney + "#n个" + moneyType + "元宝购买了#R" + amount + "#n个#Y"
						+ oneByBarcode.getName() + "#n。");
			}
		}
		ListVo_65527_0 listVo_65527_2 = GameUtil.a65527(chara);
		GameObjectChar.send(new M65527_0(), listVo_65527_2);
	}

	@Override
	public int cmd() {
		return 8410;
	}
}