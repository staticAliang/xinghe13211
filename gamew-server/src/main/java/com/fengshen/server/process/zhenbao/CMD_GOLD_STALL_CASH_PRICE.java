package com.fengshen.server.process.zhenbao;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.fengshen.db.domain.SaleClassifyGood;
import com.fengshen.server.data.GameReadTool;
import com.fengshen.server.data.write.zhenbao.MSG_GOLD_STALL_CASH_PRICE;
import com.fengshen.server.game.GameData;
import com.fengshen.server.game.GameHandler;
import com.fengshen.server.game.GameObjectChar;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

/**
 * 客户端请求金钱商品的标准价格
 * 
 *
 */
@Service
@Slf4j
public class CMD_GOLD_STALL_CASH_PRICE implements GameHandler {

	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {

		int name = GameReadTool.readInt(buff);
		SaleClassifyGood saleGood = GameData.that.baseSaleClassifyGoodService.findOneByStr("珍宝金钱_" + name);
		if(saleGood == null)
			return;
		int price = saleGood.getPrice();
		Map<String,Object> obj = new LinkedHashMap<String, Object>();
		obj.put("name", name);
		obj.put("class_str", "{150:" + (int)(price * 1.5) + ",140:" + (int)(price * 1.4) + ",130:" + (int)(price * 1.3) + ",120:" + (int)(price * 1.2) + ",110:" + price * 1.1 + ",100:" + price + ",90:" + (int)(price * 0.9) + ",80:" + (int)(price * 0.8) + ",70:" + (int)(price * 0.7) + ",60:" + (int)(price * 0.6) + ",50:" + (int)(price * 0.5) +"}");
		GameObjectChar.send(new MSG_GOLD_STALL_CASH_PRICE(), obj);
		log.info("客户端请求金钱商品标准价格------name={}",name);
	}

	@Override
	public int cmd() {
		return 0x8120;
	}

}
