package com.fengshen.server.process.system;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.fengshen.server.data.GameReadTool;
import com.fengshen.server.data.vo.Vo_61677_0;
import com.fengshen.server.data.write.M61677_0;
import com.fengshen.server.data.write.M65525_0;
import com.fengshen.server.domain.Chara;
import com.fengshen.server.domain.Goods;
import com.fengshen.server.game.GameCommonUtil;
import com.fengshen.server.game.GameHandler;
import com.fengshen.server.game.GameObjectChar;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

/**
 * 将背包物品存入仓库
 * 
 *
 */
@Service
@Slf4j
public class CMD_STORE implements GameHandler {
	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		int id = GameReadTool.readInt(buff);
		int from_pos = GameReadTool.readShort(buff);
		int to_pos = GameReadTool.readShort(buff);
		int amount = GameReadTool.readShort(buff);
		log.info("将背包物品存入仓库,id={},from_pos={},to_pos={},amount={}",id,from_pos,to_pos,amount);
		String container = GameReadTool.readString(buff);
		Chara chara = GameObjectChar.getGameObjectChar().chara;
		for (int i = 0; i < chara.backpack.size(); ++i) {
			if (chara.backpack.get(i).pos == from_pos) {
				List<Goods> listbeibao = new ArrayList<Goods>();
				Goods goods1 = new Goods();
				goods1.goodsBasics = null;
				goods1.goodsInfo = null;
				goods1.goodsLanSe = null;
				goods1.pos = from_pos;
				listbeibao.add(goods1);
				List<Goods> refreshGoods = null;
				Vo_61677_0 vo_61677_0 = null;
				if ("card_store".equals(container)) {
					// 变身卡
					vo_61677_0 = new Vo_61677_0("card_store");
					int addCard = GameCommonUtil.addCard(chara.backpack.get(i), chara);
					if (addCard == 0) {
						refreshGoods = chara.cardStore;
						chara.backpack.remove(chara.backpack.get(i));
						GameObjectChar.send(new M65525_0(), listbeibao);
					}
				} else {
					vo_61677_0 = new Vo_61677_0();
					GameCommonUtil.cangkuaddwupin(chara.backpack.get(i), chara);
					refreshGoods = chara.cangku;
				}
				// 刷新
				vo_61677_0.list = refreshGoods;
				GameObjectChar.send(new M61677_0(), vo_61677_0);
				break;
			}
		}
	}

	@Override
	public int cmd() {
		return 16504;
	}
}
