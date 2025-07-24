package com.fengshen.server.process.fashion;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.fengshen.db.domain.PackModification;
import com.fengshen.server.data.GameReadTool;
import com.fengshen.server.data.vo.Vo_20481_0;
import com.fengshen.server.data.vo.Vo_41505_0;
import com.fengshen.server.data.vo.Vo_4197_0;
import com.fengshen.server.data.vo.Vo_61677_0;
import com.fengshen.server.data.vo.Vo_APPEAR;
import com.fengshen.server.data.vo.Vo_TITLE;
import com.fengshen.server.data.write.M20481_0;
import com.fengshen.server.data.write.M41505_0;
import com.fengshen.server.data.write.M4197_0;
import com.fengshen.server.data.write.M61677_0;
import com.fengshen.server.data.write.M65525_0;
import com.fengshen.server.data.write.M65529_0;
import com.fengshen.server.data.write.MSG_DISAPPEAR;
import com.fengshen.server.data.write.MSG_TITLE;
import com.fengshen.server.data.write.store.MSG_STORE_REMOVE;
import com.fengshen.server.domain.Chara;
import com.fengshen.server.domain.Goods;
import com.fengshen.server.game.GameData;
import com.fengshen.server.game.GameHandler;
import com.fengshen.server.game.GameObjectChar;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

/**
 * 购买跟随宠道具
 * 
 *
 */
@Service
@Slf4j
public class CMD_BUY_FASHION_PET implements GameHandler {
	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		String name = GameReadTool.readString(buff);
		Chara chara = GameObjectChar.getGameObjectChar().chara;
		log.info("购买跟随宠道具");
		PackModification packModification = GameData.that.basePackModificationService.findOneByAlias(name);
		chara.goldCoin -= packModification.getGoodsPrice();
		for (int i = 0; i < chara.backpack.size(); ++i) {
			if (chara.backpack.get(i).pos == 37) {
				chara.backpack.remove(chara.backpack.get(i));
				Vo_61677_0 vo_61677_0 = new Vo_61677_0();
				vo_61677_0.store_type = "follow_pet_store";
				vo_61677_0.npcID = 0;
				vo_61677_0.list = chara.genchong;
				vo_61677_0.count = chara.genchong.size();
				GameObjectChar.send(new M61677_0(), vo_61677_0);
			}
		}
		Vo_4197_0 vo_4197_0 = new Vo_4197_0();
		vo_4197_0.id = 0;
		GameObjectChar.getGameObjectChar().gameMap.send(new M4197_0(), vo_4197_0);
		GameObjectChar.getGameObjectChar().gameMap.send(new MSG_DISAPPEAR(), chara.genchong_icon);
		Goods goods = new Goods();
		goods.goodsInfo.owner_id = 1;
		goods.goodsInfo.value = 2097924;
		goods.goodsInfo.quality = "金色";
		goods.goodsInfo.alias = name;
		goods.pos = packModification.getPosition();
		goods.goodsInfo.food_num = 0;
		goods.goodsInfo.master = chara.sex;
		goods.goodsInfo.recognize_recognized = 2;
		goods.goodsInfo.type = Integer.valueOf(packModification.getType());
		goods.goodsInfo.total_score = 24;
		goods.goodsInfo.damage_sel_rate = 1842075;
		goods.goodsInfo.str = packModification.getStr();
		goods.goodsInfo.metal = chara.polar;
		goods.goodsInfo.attrib = 0;
		goods.goodsInfo.durability = 8;
		goods.goodsInfo.rebuild_level = 0;
		goods.goodsInfo.auto_fight = "5d65f0216e9552d52c521d59" + packModification.getPosition();
		chara.genchong.add(goods);
		chara.genchong_icon = Integer.valueOf(packModification.getFasionType());
		Vo_APPEAR vo_65529_0 = new Vo_APPEAR();
		vo_65529_0.id = Integer.valueOf(packModification.getFasionType());
		vo_65529_0.x = chara.x;
		vo_65529_0.y = chara.y;
		vo_65529_0.dir = 5;
		vo_65529_0.icon = Integer.valueOf(packModification.getFasionType());
		vo_65529_0.type = 32768;
		vo_65529_0.sub_type = 2;
		vo_65529_0.owner_id = chara.id;
		vo_65529_0.name = packModification.getStr();
		vo_65529_0.org_icon = Integer.valueOf(packModification.getFasionType());
		vo_65529_0.portrait = Integer.valueOf(packModification.getFasionType());
		GameObjectChar.getGameObjectChar().gameMap.send(new M65529_0(), vo_65529_0);
		Vo_61677_0 vo_61677_2 = new Vo_61677_0();
		vo_61677_2.store_type = "follow_pet_store";
		vo_61677_2.npcID = 0;
		vo_61677_2.list = chara.genchong;
		vo_61677_2.count = chara.genchong.size();
		GameObjectChar.send(new M61677_0(), vo_61677_2);
		Vo_61677_0 vo_61677_3 = new Vo_61677_0();
		vo_61677_3.store_type = "follow_pet_store";
		vo_61677_3.npcID = 0;
		vo_61677_3.count = 1;
		vo_61677_3.isGoon = 0;
		vo_61677_3.pos = packModification.getPosition();
		GameObjectChar.send(new MSG_STORE_REMOVE(), vo_61677_3);
		goods = new Goods();
		goods.goodsInfo.owner_id = 1;
		goods.goodsInfo.value = 3844;
		goods.goodsInfo.quality = "金色";
		goods.goodsInfo.alias = packModification.getStr();
		goods.pos = 37;
		goods.goodsInfo.food_num = 0;
		goods.goodsInfo.merge_rate = 0;
		goods.goodsInfo.master = 0;
		goods.goodsInfo.recognize_recognized = 2;
		goods.goodsInfo.type = Integer.valueOf(packModification.getType());
		goods.goodsInfo.total_score = 24;
		goods.goodsInfo.damage_sel_rate = 809382;
		goods.goodsInfo.str = packModification.getStr();
		goods.goodsInfo.metal = 0;
		goods.goodsInfo.durability = 8;
		goods.goodsInfo.attrib = 0;
		goods.goodsInfo.rebuild_level = 0;
		goods.goodsInfo.auto_fight = "5d65f0216e9552d52c521d59" + packModification.getPosition();
		chara.backpack.add(goods);
		List<Goods> list = new ArrayList<Goods>();
		list.add(goods);
		GameObjectChar.send(new M65525_0(), list);
		Vo_20481_0 vo_20481_0 = new Vo_20481_0();
		vo_20481_0.msg = "购买成功。";
		vo_20481_0.time = (int) (System.currentTimeMillis() / 1000L);
		GameObjectChar.send(new M20481_0(), vo_20481_0);
		Vo_TITLE vo_61671_0 = new Vo_TITLE();
		vo_61671_0.id = chara.id;
		vo_61671_0.count = 0;
		GameObjectChar.send(new MSG_TITLE(), vo_61671_0);
		Vo_41505_0 vo_41505_0 = new Vo_41505_0();
		vo_41505_0.type = "view_follow_pet";
		GameObjectChar.send(new M41505_0(), vo_41505_0);
	}

	@Override
	public int cmd() {
		return 53712;
	}
}