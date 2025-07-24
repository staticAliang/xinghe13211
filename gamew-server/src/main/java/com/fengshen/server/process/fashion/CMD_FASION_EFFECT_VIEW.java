package com.fengshen.server.process.fashion;

import org.springframework.stereotype.Service;

import com.fengshen.db.domain.PackModification;
import com.fengshen.server.data.vo.Vo_41505_0;
import com.fengshen.server.data.vo.Vo_45608_0;
import com.fengshen.server.data.vo.Vo_61677_0;
import com.fengshen.server.data.vo.Vo_APPEAR;
import com.fengshen.server.data.write.M41505_0;
import com.fengshen.server.data.write.M45608_0;
import com.fengshen.server.data.write.M61677_0;
import com.fengshen.server.data.write.M65529_0;
import com.fengshen.server.data.write.store.MSG_STORE_REMOVE;
import com.fengshen.server.domain.Chara;
import com.fengshen.server.game.GameData;
import com.fengshen.server.game.GameHandler;
import com.fengshen.server.game.GameObjectChar;
import com.fengshen.server.game.GameUtil;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

/**
 * 查看可购买特效列表
 * 
 * 
 *
 */
@Service
@Slf4j
public class CMD_FASION_EFFECT_VIEW implements GameHandler {
	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		log.info("查看可购买特效列表");
		Chara chara = GameObjectChar.getGameObjectChar().chara;
		Vo_61677_0 vo_61677_0 = new Vo_61677_0();
		vo_61677_0.store_type = "effect_store";
		vo_61677_0.npcID = 0;
		vo_61677_0.list = chara.texiao;
		vo_61677_0.count = chara.texiao.size();
		GameObjectChar.send(new M61677_0(), vo_61677_0);
		for (int i = 0; i < chara.backpack.size(); ++i) {
			if (chara.backpack.get(i).pos == 32) {
				PackModification packModification = GameData.that.basePackModificationService
						.findOneByStr(chara.backpack.get(i).goodsInfo.str);
				Vo_61677_0 vo_61677_2 = new Vo_61677_0();
				vo_61677_2.store_type = "effect_store";
				vo_61677_2.npcID = 0;
				vo_61677_2.count = 1;
				vo_61677_2.isGoon = 0;
				vo_61677_2.pos = packModification.getPosition();
				GameObjectChar.send(new MSG_STORE_REMOVE(), vo_61677_2);
			}
		}
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
		vo_41505_0.type = "fasion_effect_view";
		GameObjectChar.send(new M41505_0(), vo_41505_0);
		Vo_APPEAR vo_65529_0 = GameUtil.a65529(chara);
		GameObjectChar.send(new M65529_0(), vo_65529_0);
	}

	@Override
	public int cmd() {
		return 45607;
	}
}