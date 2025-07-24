package com.fengshen.server.process.zhenbao;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.fengshen.db.domain.GoldStallNineGoods;
import com.fengshen.server.data.GameReadTool;
import com.fengshen.server.data.vo.zhenbao.Vo_GOLD_STALL_BUY_RESUL;
import com.fengshen.server.data.write.zhenbao.MSG_GOLD_STALL_BUY_RESULT;
import com.fengshen.server.data.write.zhenbao.MSG_GOLD_STALL_MINE;
import com.fengshen.server.domain.Chara;
import com.fengshen.server.game.GameCommonUtil;
import com.fengshen.server.game.GameData;
import com.fengshen.server.game.GameHandler;
import com.fengshen.server.game.GameObjectChar;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import tk.mybatis.mapper.entity.Example;

/**
 * 珍宝请求修改价格
 * 
 */
@Service
public class CMD_GOLD_STALL_CHANGE_PRICE implements GameHandler {

	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		String goodsId = GameReadTool.readString(buff);
		int price = GameReadTool.readInt(buff);
		Chara chara = GameObjectChar.getGameObjectChar().chara;
		Example example = new Example(GoldStallNineGoods.class);
		example.createCriteria().andEqualTo("goodsId", goodsId);
		GoldStallNineGoods saleGood = GameData.that.zhenbao.selectOneByExample(example);
		if(saleGood == null) {
			GameCommonUtil.dialogOk("商品已失效。");
			return;
		}
		if(saleGood.getCgPriceCount() <= 0) {
			GameCommonUtil.dialogOk("改价次数不足。");
			return;
		}
		if(saleGood.getAppointeeName() == null || saleGood.getAppointeeName().equals("")) {
			saleGood.setPrice(price);
			saleGood.setUpdateTime(new Date());
			saleGood.setCgPriceCount(saleGood.getCgPriceCount()-1);
			GameData.that.zhenbao.updateByPrimaryKey(saleGood);
			Vo_GOLD_STALL_BUY_RESUL vo = new Vo_GOLD_STALL_BUY_RESUL();
			vo.setGoods_gid(saleGood.getGoodsId());
			vo.setResult(0);
			vo.setTips("改价成功。");
			vo.setType(3);
			GameObjectChar.send(new MSG_GOLD_STALL_BUY_RESULT(), vo);
			GameObjectChar.send(new MSG_GOLD_STALL_MINE(), GameCommonUtil.refreshMarketGold(chara));
		}
	}

	@Override
	public int cmd() {
		return 0x811C;
	}

}
