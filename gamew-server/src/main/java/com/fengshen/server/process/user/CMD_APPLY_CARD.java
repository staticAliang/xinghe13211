package com.fengshen.server.process.user;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fengshen.server.data.GameReadTool;
import com.fengshen.server.domain.Chara;
import com.fengshen.server.domain.Goods;
import com.fengshen.server.domain.GoodsInfo;
import com.fengshen.server.game.GameHandler;
import com.fengshen.server.game.GameObjectChar;
import com.fengshen.server.game.GameUtil;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

/**
 * 使用变身卡
 *
 */
@Service
@Slf4j
public class CMD_APPLY_CARD implements GameHandler {

	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		int pos = GameReadTool.readShort(buff);
		int id = GameReadTool.readInt(buff);
		log.info("pos:{},id:{}", pos, id);
		GameObjectChar gameObjectChar = GameObjectChar.getGameObjectChar();
		Chara chara = gameObjectChar.chara;
		List<Goods> backpack = chara.backpack;
		GoodsInfo goodsInfo = null;
		if(pos>=2001 && pos<=2501) {
			//变身卡仓库
			for (int i = 0; i < chara.cardStore.size(); i++) {
				Goods goods2 = chara.cardStore.get(i);
				if (goods2.pos == pos) {
					goodsInfo = goods2.goodsInfo;
					break;
				}
			}
		}else {
			for (Goods g : backpack) {
				if (g.pos == pos) {
					// 获取背包商品信息
					goodsInfo = g.goodsInfo;
					break;
				}
			}
		}
		if(goodsInfo == null) {
			GameUtil.sendMeTips("未找到#Y这个商品信息。");
			return;
		}
		GameUtil.confirm(GameObjectChar.getGameObjectChar().chara, 
				"你确定要使用#R"+goodsInfo.str+"#n吗?", "applyCard-"+pos);
	}

	@Override
	public int cmd() {
		return 250;
	}

}
