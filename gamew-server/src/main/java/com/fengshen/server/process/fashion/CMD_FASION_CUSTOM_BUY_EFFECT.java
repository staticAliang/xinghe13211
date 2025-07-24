package com.fengshen.server.process.fashion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;

import com.fengshen.db.domain.PackModification;
import com.fengshen.server.data.GameReadTool;
import com.fengshen.server.data.vo.Vo_20481_0;
import com.fengshen.server.data.vo.Vo_41505_0;
import com.fengshen.server.data.vo.Vo_45608_0;
import com.fengshen.server.data.vo.Vo_61677_0;
import com.fengshen.server.data.vo.user.Vo_UPDATE_APPEARANCE;
import com.fengshen.server.data.write.M20481_0;
import com.fengshen.server.data.write.M41505_0;
import com.fengshen.server.data.write.M45608_0;
import com.fengshen.server.data.write.M61677_0;
import com.fengshen.server.data.write.M65525_0;
import com.fengshen.server.data.write.store.MSG_STORE_REMOVE;
import com.fengshen.server.data.write.user.MSG_UPDATE_APPEARANCE;
import com.fengshen.server.domain.Chara;
import com.fengshen.server.domain.Goods;
import com.fengshen.server.game.GameCommonUtil;
import com.fengshen.server.game.GameData;
import com.fengshen.server.game.GameHandler;
import com.fengshen.server.game.GameObjectChar;
import com.fengshen.server.game.GameUtil;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

@Service
public class CMD_FASION_CUSTOM_BUY_EFFECT implements GameHandler {
	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		String item_name = GameReadTool.readString(buff);
		Chara chara = GameObjectChar.getGameObjectChar().chara;
		for (int i = 0; i < chara.backpack.size(); ++i) {
			if (chara.backpack.get(i).pos == 32) {
				chara.backpack.remove(chara.backpack.get(i));
				Vo_61677_0 vo_61677_0 = new Vo_61677_0();
				vo_61677_0.store_type = "effect_store";
				vo_61677_0.npcID = 0;
				vo_61677_0.list = chara.texiao;
				vo_61677_0.count = chara.texiao.size();
				GameObjectChar.send(new M61677_0(), vo_61677_0);
			}
		}
		PackModification packModification = GameData.that.basePackModificationService.findOneByAlias(item_name);
		if(chara.effectIcons == null) {
			chara.effectIcons = new HashMap<>();
		}
		chara.effectIcons.put("fasionEffectIcon", Integer.valueOf(packModification.getFasionType()));
		chara.goldCoin -= packModification.getGoodsPrice();
		Goods goods2 = new Goods();
		goods2.goodsInfo.owner_id = 1;
		goods2.goodsInfo.value = 2097924;
		goods2.goodsInfo.quality = "金色";
		goods2.goodsInfo.alias = item_name;
		goods2.goodsInfo.amount = 17;
		goods2.pos = packModification.getPosition();
		goods2.goodsInfo.food_num = 0;
		goods2.goodsInfo.master = chara.sex;
		goods2.goodsInfo.recognize_recognized = 2;
		goods2.goodsInfo.type = Integer.valueOf(packModification.getType());
		goods2.goodsInfo.total_score = 25;
		goods2.goodsInfo.damage_sel_rate = 1842075;
		goods2.goodsInfo.str = packModification.getStr();
		goods2.goodsInfo.metal = chara.polar;
		goods2.goodsInfo.attrib = 0;
		goods2.goodsInfo.durability = 8;
		goods2.goodsInfo.rebuild_level = 0;
		goods2.goodsInfo.auto_fight = GameCommonUtil.UUID().toLowerCase() + packModification.getPosition();
		chara.texiao.add(goods2);
		Vo_61677_0 vo_61677_2 = new Vo_61677_0();
		vo_61677_2.store_type = "effect_store";
		vo_61677_2.npcID = 0;
		vo_61677_2.list = chara.texiao;
		vo_61677_2.count = chara.texiao.size();
		GameObjectChar.send(new M61677_0(), vo_61677_2);
		Vo_61677_0 vo_61677_3 = new Vo_61677_0();
		vo_61677_3.store_type = "effect_store";
		vo_61677_3.npcID = 0;
		vo_61677_3.count = 1;
		vo_61677_3.isGoon = 0;
		vo_61677_3.pos = packModification.getPosition();
		GameObjectChar.send(new MSG_STORE_REMOVE(), vo_61677_3);
		goods2 = new Goods();
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
		goods2.goodsInfo.metal = 0;
		goods2.goodsInfo.durability = 8;
		goods2.goodsInfo.attrib = 0;
		goods2.goodsInfo.rebuild_level = 0;
		goods2.goodsInfo.auto_fight = GameCommonUtil.UUID().toLowerCase() + packModification.getPosition();
		chara.otherGoods.add(goods2);
		List<Goods> list = new ArrayList<Goods>();
		list.add(goods2);
		GameObjectChar.send(new M65525_0(), list);
		Vo_UPDATE_APPEARANCE vo_61661_0 = GameUtil.a61661(chara);
		GameObjectChar.getGameObjectChar().gameMap.send(new MSG_UPDATE_APPEARANCE(), vo_61661_0);
		vo_61661_0 = GameUtil.a61661(chara);
		GameObjectChar.send(new MSG_UPDATE_APPEARANCE(), vo_61661_0);
		Vo_20481_0 vo_20481_0 = new Vo_20481_0();
		vo_20481_0.msg = "你花费了 " + packModification.getGoodsPrice() + "个金元宝购买了#Y" + item_name + "#n。";
		vo_20481_0.time = (int) (System.currentTimeMillis() / 1000L);
		GameObjectChar.send(new M20481_0(), vo_20481_0);
		Vo_45608_0 vo_45608_0 = new Vo_45608_0();
		vo_45608_0.count = 13;
		vo_45608_0.name0 = "浪漫玫瑰";
		vo_45608_0.goods_price0 = 0;
		vo_45608_0.name1 = "星汉灿烂·永久";
		vo_45608_0.goods_price1 = 10888;
		vo_45608_0.name2 = "风花雪月·永久";
		vo_45608_0.goods_price2 = 10888;
		vo_45608_0.name3 = "轻羽飞扬·永久";
		vo_45608_0.goods_price3 = 8888;
		vo_45608_0.name4 = "繁花盛开·永久";
		vo_45608_0.goods_price4 = 6888;
		vo_45608_0.name5 = "踏雪无痕·永久";
		vo_45608_0.goods_price5 = 8888;
		vo_45608_0.name6 = "雨过天晴·永久";
		vo_45608_0.goods_price6 = 8888;
		vo_45608_0.name7 = "翩翩起舞";
		vo_45608_0.goods_price7 = 20888;
		vo_45608_0.name8 = "蝶影翩翩·永久";
		vo_45608_0.goods_price8 = 6888;
		vo_45608_0.name9 = "多彩泡泡";
		vo_45608_0.goods_price9 = 20888;
		vo_45608_0.name10 = "步步生莲·永久";
		vo_45608_0.goods_price10 = 6888;
		vo_45608_0.name11 = "星影特效";
		vo_45608_0.goods_price11 = 20888;
		vo_45608_0.name12 = "鸾凤宝玉";
		vo_45608_0.goods_price12 = 20888;
		vo_45608_0.count1 = 0;
		GameObjectChar.send(new M45608_0(), vo_45608_0);
		Vo_41505_0 vo_41505_0 = new Vo_41505_0();
		vo_41505_0.type = "equip_fasion";
		GameObjectChar.send(new M41505_0(), vo_41505_0);
	}

	@Override
	public int cmd() {
		return 45606;
	}
}