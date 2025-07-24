package com.fengshen.server.process.item;

import org.springframework.stereotype.Service;

import com.fengshen.server.data.GameReadTool;
import com.fengshen.server.domain.Chara;
import com.fengshen.server.domain.Goods;
import com.fengshen.server.game.GameCommonUtil;
import com.fengshen.server.game.GameHandler;
import com.fengshen.server.game.GameObjectChar;
import com.fengshen.server.game.GameUtil;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

/**
 * 提交血精
 * @author aaa
 *
 */
@Service
@Slf4j
public class CMD_SUBMIT_XUEJING_ITEM implements GameHandler {

	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		String itemsPos = GameReadTool.readString(buff);
		log.info("提交血精：{}",itemsPos);
		GameObjectChar gameObjectChar = GameObjectChar.getGameObjectChar();
		Chara chara = gameObjectChar.chara;
		String[] pos = itemsPos.split("\\|");
		for(String p:pos) {
			Goods goods = GameCommonUtil.getBackpackGoodsByPos(chara, Integer.valueOf(p));
			if(goods == null) {
				GameUtil.sendMeTips("材料不符合要求");
				return;
			}
			if(!goods.goodsInfo.str.equals("黑熊血精") && !goods.goodsInfo.str.equals("蝎后血精") && !goods.goodsInfo.str.equals("魔猪血精") 
					&& !goods.goodsInfo.str.equals("鬼猿血精") && !goods.goodsInfo.str.equals("魔皇血精")) {
				GameUtil.sendMeTips("请提交正确的材料！");
				return;
			}
			GameUtil.removemunber(chara, goods, 1, true);
		}
		//开启七杀成功
		gameObjectChar.isOpenQiShaFlag = 1;
		GameUtil.sendMeTips("成功开启七杀");
	}

	@Override
	public int cmd() {
		return 0x5026;
	}

}
