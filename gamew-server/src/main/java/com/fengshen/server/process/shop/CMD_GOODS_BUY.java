package com.fengshen.server.process.shop;

import org.springframework.stereotype.Service;

import com.fengshen.db.domain.GroceriesShop;
import com.fengshen.db.domain.MedicineShop;
import com.fengshen.db.domain.StoreInfo;
import com.fengshen.server.data.GameReadTool;
import com.fengshen.server.data.vo.ListVo_65527_0;
import com.fengshen.server.data.vo.Vo_20480_0;
import com.fengshen.server.data.vo.Vo_40964_0;
import com.fengshen.server.data.write.M20480_0;
import com.fengshen.server.data.write.M40964_0;
import com.fengshen.server.data.write.M65525_0;
import com.fengshen.server.data.write.M65527_0;
import com.fengshen.server.domain.Chara;
import com.fengshen.server.domain.Goods;
import com.fengshen.server.domain.GoodsLanSe;
import com.fengshen.server.game.GameData;
import com.fengshen.server.game.GameHandler;
import com.fengshen.server.game.GameObjectChar;
import com.fengshen.server.game.GameUtil;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

/**
 * 购买商品
 * 
 *
 */
@Service
@Slf4j
public class CMD_GOODS_BUY implements GameHandler {
	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		int shipper = GameReadTool.readInt(buff);
		int pos = GameReadTool.readShort(buff);
		int amount = GameReadTool.readShort(buff);
		int to_pos = GameReadTool.readShort(buff);
		log.info("购买商品,to_pos={}",to_pos);
		GameObjectChar gameObjectChar = GameObjectChar.getGameObjectChar();
		Chara chara = gameObjectChar.chara;
		if (shipper == 15908) {
			GroceriesShop groceriesShop = GameData.that.baseGroceriesShopService.findOneByGoodsNo(pos);
			StoreInfo storeInfo = GameData.that.baseStoreInfoService.findOneByName(groceriesShop.getName());
			if (chara.cash < storeInfo.getRebuildLevel()) {
				GameUtil.sendMeTips("金钱不足！");
				return;
			}
			if (pos < 2) {
				GameUtil.huodedaoju(gameObjectChar, storeInfo, amount);
			} else {
				Goods goods = new Goods();
				int pos2 = GameUtil.packPoint(chara);
				if (pos2 == -1) {
					return;
				}
				goods.pos = pos2;
				goods.goodsDaoju(storeInfo);
				goods.goodsInfo.degree_32 = 0;
				goods.goodsInfo.skill = 3;
				goods.goodsInfo.owner_id = amount;
				goods.goodsInfo.damage_sel_rate = 400976;
				goods.goodsInfo.silver_coin = 6000;
				goods.goodsInfo.degree_32 = 0;
				goods.goodsLanSe = new GoodsLanSe();
				if (pos == 2) {
					goods.goodsLanSe.wiz = 270;
				}
				if (pos == 3) {
					goods.goodsLanSe.accurate = 594;
				}
				if (pos == 4) {
					goods.goodsLanSe.mana = 392;
				}
				if (pos == 5) {
					goods.goodsLanSe.def = 900;
				}
				if (pos == 6) {
					goods.goodsLanSe.parry = 96;
				}
				if (pos == 7) {
					goods.goodsLanSe.dex = 594;
				}
				GameUtil.addwupin(goods, chara);
				GameObjectChar.send(new M65525_0(), chara.backpack);
			}
			Vo_20480_0 vo_20480_0 = new Vo_20480_0();
			vo_20480_0.msg = "你购买了" + storeInfo.getName() + "#n";
			vo_20480_0.time = 1562593376;
			GameObjectChar.send(new M20480_0(), vo_20480_0);

			Vo_40964_0 vo_40964_0 = new Vo_40964_0();
			vo_40964_0.type = 1;
			vo_40964_0.name = storeInfo.getName();
			vo_40964_0.param = "-1";
			vo_40964_0.rightNow = 0;
			GameObjectChar.send(new M40964_0(), vo_40964_0);

			chara.cash -= storeInfo.getRebuildLevel();
			ListVo_65527_0 listVo_65527_0 = GameUtil.a65527(chara);
			GameObjectChar.send(new M65527_0(), listVo_65527_0);
			//物品购买成功
			GameUtil.sendNotify(50029, "goods");
		}
		if (shipper == 15907) {
			MedicineShop medicineShop = GameData.that.baseMedicineShopService.findOneByGoodsNo(pos);
			StoreInfo storeInfo = GameData.that.baseStoreInfoService.findOneByName(medicineShop.getName());
			if (chara.cash < storeInfo.getRebuildLevel()) {
				GameUtil.sendMeTips("金钱不足！");
				return;
			}
			GameUtil.huodedaoju(gameObjectChar, storeInfo, amount);
			Vo_20480_0 vo_20480_0 = new Vo_20480_0();
			vo_20480_0.msg = "你购买了" + storeInfo.getName() + "#n";
			vo_20480_0.time = 1562593376;
			GameObjectChar.send(new M20480_0(), vo_20480_0);
			Vo_40964_0 vo_40964_0 = new Vo_40964_0();
			vo_40964_0.type = 1;
			vo_40964_0.name = storeInfo.getName();
			vo_40964_0.param = "-1";
			vo_40964_0.rightNow = 0;
			GameObjectChar.send(new M40964_0(), vo_40964_0);
			chara.cash -= storeInfo.getRebuildLevel();
			ListVo_65527_0 listVo_65527_0 = GameUtil.a65527(chara);
			GameObjectChar.send(new M65527_0(), listVo_65527_0);
		}
	}

	@Override
	public int cmd() {
		return 12356;
	}
}