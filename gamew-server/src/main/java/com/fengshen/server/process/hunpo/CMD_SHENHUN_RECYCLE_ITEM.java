package com.fengshen.server.process.hunpo;

import java.util.Iterator;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.fengshen.server.data.GameReadTool;
import com.fengshen.server.data.vo.Vo_61677_0;
import com.fengshen.server.data.write.store.MSG_STORE_REMOVE;
import com.fengshen.server.domain.Chara;
import com.fengshen.server.domain.Goods;
import com.fengshen.server.game.GameHandler;
import com.fengshen.server.game.GameObjectChar;
import com.fengshen.server.game.GameUtil;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CMD_SHENHUN_RECYCLE_ITEM implements GameHandler {

	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		String itemIds = GameReadTool.readString2(buff);
		log.info("太阴之气分解,{}",itemIds);
		GameObjectChar gameObjectChar = GameObjectChar.getGameObjectChar();
		Chara chara = gameObjectChar.chara;
		int size = 0;
		String[] itemArr = itemIds.split("\\|");
		for(String item:itemArr) {
			Iterator<Goods> iterator = chara.tyzqStore.iterator();
			while(iterator.hasNext()) {
				Goods goods = iterator.next();
				if(goods.pos == Integer.valueOf(item)) {
					size++;
					iterator.remove();
					Vo_61677_0 vo_61677_0 = new Vo_61677_0("tyzq_store");
					vo_61677_0.pos = goods.pos;
					GameObjectChar.send(new MSG_STORE_REMOVE(), vo_61677_0);
					break;
				}
			}
		}
		GameUtil.sendMeTips(StringUtils.join("成功分解了","#R",size,"#n个太阴之气。"));
	}

	@Override
	public int cmd() {
		return 0x5305;
	}

}
