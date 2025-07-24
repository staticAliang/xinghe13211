package com.fengshen.server.process.fly;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Service;

import com.fengshen.db.domain.StoreInfo;
import com.fengshen.server.data.GameReadTool;
import com.fengshen.server.data.write.M65525_0;
import com.fengshen.server.data.write.fly.MSG_FLY_ARTIFACT_MAKE;
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
import lombok.extern.slf4j.Slf4j;

/**
 * 飞行法宝启灵
 * @author a
 *
 */
@Service
@Slf4j
public class CMD_FLY_ARTIFACT_MAKE implements GameHandler {

	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		String itemName = GameReadTool.readString(buff);
		String coinType = GameReadTool.readString(buff);
		log.info("单人飞行法宝启灵:{}{}",itemName,coinType);
		GameObjectChar gameObjectChar = GameObjectChar.getGameObjectChar();
		Chara chara = gameObjectChar.chara;
		int size = GameReadTool.readShort(buff);
		if(size < 3) {
			GameUtil.sendMeTips("梦荷部件不足，无法启灵！");
			return;
		}
		//校验部件是否充足
		if(GameCommonUtil.getGoodsNum(chara, "梦荷·震位")<0 || GameCommonUtil.getGoodsNum(chara, "梦荷·离位")<0 
				|| GameCommonUtil.getGoodsNum(chara, "梦荷·兑位")<0) {
			GameUtil.sendMeTips("梦荷部件不足，无法启灵！");
			return;
		}
		//计数器
		List<Goods> removeGoods = new ArrayList<>();
		//寻找3个部件
		Iterator<Goods> iterator = chara.backpack.iterator();
		while(iterator.hasNext()) {
			Goods goods = iterator.next();
			if(goods.goodsInfo.str.equals("梦荷·震位")) {
				removeGoods.add(goods);
			}else if(goods.goodsInfo.str.equals("梦荷·离位")) {
				removeGoods.add(goods);
			}else if(goods.goodsInfo.str.equals("梦荷·兑位")) {
				removeGoods.add(goods);
			}
			if(removeGoods.size()>=3) {
				break;
			}
		}
		if(removeGoods.size()<3) {
			GameUtil.sendMeTips("梦荷部件不足，无法启灵！");
			return;
		}
		for(Goods goods:removeGoods) {
			//删除部件
			GameUtil.removemunber(chara, goods, 1);
		}
		
		//合成为梦荷
		if("梦荷".equals(itemName)) {
			StoreInfo info = GameData.that.baseStoreInfoService.findOneByName("梦荷");
			Goods goods = new Goods();
			int pos2 = GameUtil.packPoint(chara);
			if (pos2 == -1) {
				GameUtil.sendMeTips("背包不足");
				return;
			}
			goods.pos = pos2;
			goods.goodsDaoju(info);
			goods.goodsInfo.owner_id = 1;
			goods.goodsInfo.damage_sel_rate = goods.pos;
			goods.goodsInfo.degree_32 = 0;
			goods.goodsInfo.open_nimbus = 1;
			goods.goodsInfo.amount = 21;
			GameUtil.addwupin(goods, chara);
			
			GameObjectChar.send(new MSG_FLY_ARTIFACT_MAKE(), buff);
			GameUtil.sendMeTips("成功启灵,获得梦荷！");
			return;
		}else if("御天梭".equals(itemName)) {
			StoreInfo info = GameData.that.baseStoreInfoService.findOneByName("御天梭");
			Goods goods = new Goods();
			int pos2 = GameUtil.packPoint(chara);
			if (pos2 == -1) {
				GameUtil.sendMeTips("背包不足");
				return;
			}
			goods.pos = pos2;
			goods.goodsDaoju(info);
			goods.goodsInfo.owner_id = 1;
			goods.goodsInfo.damage_sel_rate = goods.pos;
			goods.goodsInfo.degree_32 = 0;
			goods.goodsInfo.open_nimbus = 1;
			goods.goodsInfo.amount = 21;
			GameUtil.addwupin(goods, chara);
			
			GameObjectChar.send(new MSG_FLY_ARTIFACT_MAKE(), buff);
			GameUtil.sendMeTips("成功启灵,获得御天梭！");
			return;
		}
		//启灵
		for(Goods goods:chara.backpack) {
			if(goods.goodsInfo.str.equals(itemName) && 
					goods.goodsInfo.open_nimbus == 0 && 
					goods.goodsInfo.total_score == 34) {
				goods.goodsInfo.open_nimbus = 1;
				goods.goodsInfo.amount = 21;
				GameObjectChar.send(new M65525_0(), Lists.newArrayList(goods));
				break;
			}
		}
		GameObjectChar.send(new MSG_FLY_ARTIFACT_MAKE(), buff);
		GameUtil.sendMeTips("成功启灵！");
	}

	@Override
	public int cmd() {
		return 33574;
	}

}
