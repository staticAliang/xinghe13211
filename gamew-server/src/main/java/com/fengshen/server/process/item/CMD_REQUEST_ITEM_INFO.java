package com.fengshen.server.process.item;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.fengshen.db.domain.SaleGood;
import com.fengshen.server.data.GameReadTool;
import com.fengshen.server.data.vo.Vo_45104_0;
import com.fengshen.server.data.vo.Vo_45105_0;
import com.fengshen.server.data.write.M45104_0;
import com.fengshen.server.data.write.M45105_0;
import com.fengshen.server.domain.Goods;
import com.fengshen.server.domain.Petbeibao;
import com.fengshen.server.game.GameData;
import com.fengshen.server.game.GameHandler;
import com.fengshen.server.game.GameObjectChar;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

/**
 * 请求物品信息
 * 
 *
 */
@Service
@Slf4j
public class CMD_REQUEST_ITEM_INFO implements GameHandler {
	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		String item_cookie = GameReadTool.readString(buff);
		String[] split = item_cookie.split("\\;");
		String goodsid = split[0];
		String pos = split[1];
		log.info("请求物品信息,pos={}",pos);
		SaleGood saleGood = GameData.that.saleGoodService.findOneByGoodsId(goodsid);
		if (saleGood == null) {
			return;
		}
		String goods = saleGood.getGoods();
		if (saleGood.getType() == 1) {
			Goods goods2 = JSONObject.parseObject(goods, Goods.class);
			Vo_45104_0 vo_45104_0 = new Vo_45104_0();
			vo_45104_0.id = goodsid;
			vo_45104_0.status = 2;
			vo_45104_0.endTime = saleGood.getEndTime();
			vo_45104_0.goods = goods2;
			GameObjectChar.send(new M45104_0(), vo_45104_0);
			return;
		}

		Petbeibao petbeibao = JSONObject.parseObject(goods, Petbeibao.class);
		Vo_45105_0 vo_45105_0 = new Vo_45105_0();
		vo_45105_0.goodId = goodsid;
		vo_45105_0.status = saleGood.getStatus();
		vo_45105_0.endTime = saleGood.getEndTime();
		vo_45105_0.petbeibao = petbeibao;
		vo_45105_0.gid = saleGood.getGid();
		GameObjectChar.send(new M45105_0(), vo_45105_0);
	}

	@Override
	public int cmd() {
		return 4301;
	}
}
