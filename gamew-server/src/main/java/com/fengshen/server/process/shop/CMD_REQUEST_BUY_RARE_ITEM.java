package com.fengshen.server.process.shop;

import org.springframework.stereotype.Service;

import com.fengshen.db.domain.RareShopItem;
import com.fengshen.server.data.GameReadTool;
import com.fengshen.server.data.vo.shop.Vo_RARE_SHOP_ITEMS_INFO;
import com.fengshen.server.data.write.shop.MSG_RARE_SHOP_ONE_ITEM_INFO;
import com.fengshen.server.domain.Chara;
import com.fengshen.server.game.GameData;
import com.fengshen.server.game.GameHandler;
import com.fengshen.server.game.GameObjectChar;
import com.fengshen.server.game.GameUtil;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import tk.mybatis.mapper.entity.Example;

@Service
@Slf4j
public class CMD_REQUEST_BUY_RARE_ITEM implements GameHandler {

	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		String barcode = GameReadTool.readString(buff);
		int num = GameReadTool.readInt(buff);
		log.info("稀有物品商店购买物品,{},{}",barcode,num);
		GameObjectChar gameObjectChar = GameObjectChar.getGameObjectChar();
		Chara chara = gameObjectChar.chara;
		Example example = new Example(RareShopItem.class);
		example.createCriteria().andEqualTo("barcode", barcode);
		RareShopItem rareShopItem = GameData.that.rareShopItemService.selectOneByExample(example);
		if(rareShopItem != null) {
			if(rareShopItem.getNum()<num) {
				GameUtil.sendMeTips("商品数量不足无法购买！");
				return;
			}
			int cost = rareShopItem.getCost()*num;
			if(chara.ctDataScoreCost<cost) {
				GameUtil.sendMeTips("擂台积分不足无法购买！");
				return;
			}
			boolean huodedaoju = GameUtil.huodedaoju(chara, rareShopItem.getName(), num);
			if(huodedaoju) {
				rareShopItem.setNum(rareShopItem.getNum()-num);
				chara.ctDataScoreCost-=cost;
				GameUtil.sendMeTips("你消耗#R"+cost+"#n点擂台积分购买了#Y"+rareShopItem.getName());
				//更新
				GameData.that.rareShopItemService.updateByPrimaryKeySelective(rareShopItem);
				//刷新单个物品
				GameObjectChar.send(new MSG_RARE_SHOP_ONE_ITEM_INFO(), new Vo_RARE_SHOP_ITEMS_INFO(barcode,rareShopItem.getName(),rareShopItem.getCost(),rareShopItem.getNum()));
				GameUtil.sendUpdate(chara);
			}
		}
	}

	@Override
	public int cmd() {
		return 0xB0FE;
	}

}
