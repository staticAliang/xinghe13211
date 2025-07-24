package com.fengshen.server.process.equip;

import org.springframework.stereotype.Service;

import com.fengshen.server.data.GameReadTool;
import com.fengshen.server.data.write.equip.MSG_PRE_UPGRADE_EQUIP;
import com.fengshen.server.domain.Chara;
import com.fengshen.server.domain.Goods;
import com.fengshen.server.game.GameCommonUtil;
import com.fengshen.server.game.GameHandler;
import com.fengshen.server.game.GameObjectChar;
import com.fengshen.server.game.GameUtil;
import com.fengshen.server.util.BeanUtils;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

/**
 * 预览装备
 * 
 * 
 *
 */
@Service
@Slf4j
public class C32774_0 implements GameHandler {
	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		int pos = GameReadTool.readShort(buff);
		int type = GameReadTool.readByte(buff);
		String para = GameReadTool.readString(buff);
		log.info("预览装备={}",para);
		GameObjectChar gameObjectChar = GameObjectChar.getGameObjectChar();
		Chara chara = gameObjectChar.chara;
		// 装备进化
		if (15 == type) {
			for (Goods goods : GameCommonUtil.switchGoodsLocation(chara, pos)) {
				if (goods.pos == pos) {
					Goods previewGoods = BeanUtils.clone(goods);
					previewGoods.goodsInfo.attrib += 1;
					GameObjectChar.send(new MSG_PRE_UPGRADE_EQUIP(), new Object[] { previewGoods, type });
					GameUtil.a65511(gameObjectChar);
					break;
				}
			}
		}else if(23 == type){
			for (Goods goods : GameCommonUtil.switchGoodsLocation(chara, pos)) {
				if (goods.pos == pos) {
					Goods previewGoods = BeanUtils.clone(goods);
					previewGoods.goodsInfo.attrib -= 1;
					GameObjectChar.send(new MSG_PRE_UPGRADE_EQUIP(), new Object[] { previewGoods, type });
				}
			}
		}

	}

	@Override
	public int cmd() {
		return 32774;
	}
}
