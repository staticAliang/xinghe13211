package com.fengshen.server.process.market;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fengshen.db.domain.SaleGood;
import com.fengshen.server.data.GameReadTool;
import com.fengshen.server.data.vo.Vo_20481_0;
import com.fengshen.server.data.vo.Vo_49179_0;
import com.fengshen.server.data.write.M20481_0;
import com.fengshen.server.data.write.market.M49179_0;
import com.fengshen.server.domain.Chara;
import com.fengshen.server.game.GameData;
import com.fengshen.server.game.GameHandler;
import com.fengshen.server.game.GameObjectChar;
import com.fengshen.server.game.GameUtil;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

/**
 * 集市请求修改价格
 * 
 *
 */
@Service
@Slf4j
public class CMD_STALL_CHANGE_PRICE implements GameHandler {
	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		String goods_gid = GameReadTool.readString(buff);
		int price = GameReadTool.readInt(buff);
		log.info("集市请求修改价格， goods_gid={},price={}",goods_gid,price);
		SaleGood saleGood = GameData.that.saleGoodService.findOneByGoodsId(goods_gid);
		saleGood.setPrice(price);
		GameData.that.saleGoodService.updateById(saleGood);
		Chara chara = GameObjectChar.getGameObjectChar().chara;
		chara.cash -= 20000;
		Vo_20481_0 vo_20481_0 = new Vo_20481_0();
		vo_20481_0.msg = "改价成功";
		vo_20481_0.time = (int) (System.currentTimeMillis() / 1000L);
		GameObjectChar.send(new M20481_0(), vo_20481_0);
		List<SaleGood> saleGoodList = (List<SaleGood>) GameData.that.saleGoodService.findByOwnerUuid(chara.uuid);
		Vo_49179_0 vo_49179_0 = GameUtil.a49179(saleGoodList, chara);
		GameObjectChar.send(new M49179_0(), vo_49179_0);
	}

	@Override
	public int cmd() {
		return 33054;
	}
}