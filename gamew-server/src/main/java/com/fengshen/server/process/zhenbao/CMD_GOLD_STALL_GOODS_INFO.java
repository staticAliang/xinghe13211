package com.fengshen.server.process.zhenbao;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.fengshen.db.domain.GoldStallNineGoods;
import com.fengshen.server.data.GameReadTool;
import com.fengshen.server.data.vo.Vo_45104_0;
import com.fengshen.server.data.vo.Vo_45105_0;
import com.fengshen.server.data.write.zhenbao.MSG_GOLD_STALL_GOODS_INFO_ITEM;
import com.fengshen.server.data.write.zhenbao.MSG_GOLD_STALL_GOODS_INFO_PET;
import com.fengshen.server.domain.Goods;
import com.fengshen.server.domain.Petbeibao;
import com.fengshen.server.game.GameCommonUtil;
import com.fengshen.server.game.GameData;
import com.fengshen.server.game.GameHandler;
import com.fengshen.server.game.GameObjectChar;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import tk.mybatis.mapper.entity.Example;

/**
 * 珍宝商品信息
 * 
 * 
 *
 */
@Service
public class CMD_GOLD_STALL_GOODS_INFO implements GameHandler {

	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		String goodsId = GameReadTool.readString(buff);
		GameReadTool.readByte(buff);
		Example example = new Example(GoldStallNineGoods.class);
		example.createCriteria().andEqualTo("goodsId", goodsId);
		GoldStallNineGoods saleGood = GameData.that.zhenbao.selectOneByExample(example);
		if (saleGood == null) {
			GameCommonUtil.dialogOk("该商品不存在。");
			return;
		}
		String goods = saleGood.getGoods();
		if (saleGood.getStallItemType() == 0 || saleGood.getStallItemType() == 3) {
			Goods goods2 = JSONObject.parseObject(goods, Goods.class);
			Vo_45104_0 vo_45104_0 = new Vo_45104_0();
			vo_45104_0.id = goodsId;
			vo_45104_0.status = 2;
			if(saleGood.getEndTime() != null) {
				vo_45104_0.endTime = saleGood.getEndTime();
			}
			vo_45104_0.goods = goods2;
			GameObjectChar.send(new MSG_GOLD_STALL_GOODS_INFO_ITEM(), vo_45104_0);
			return;
		} else if (saleGood.getStallItemType() == 2) {
			Petbeibao petbeibao = JSONObject.parseObject(goods, Petbeibao.class);
			Vo_45105_0 vo_45105_0 = new Vo_45105_0();
			vo_45105_0.goodId = goodsId;
			vo_45105_0.status = saleGood.getStatus();
			if(saleGood.getEndTime() != null) {
				vo_45105_0.endTime = saleGood.getEndTime();
			}
			vo_45105_0.petbeibao = petbeibao;
			vo_45105_0.gid = saleGood.getGid();
			GameObjectChar.send(new MSG_GOLD_STALL_GOODS_INFO_PET(), vo_45105_0);
		}

	}

	@Override
	public int cmd() {
		return 0x8112;
	}

}
