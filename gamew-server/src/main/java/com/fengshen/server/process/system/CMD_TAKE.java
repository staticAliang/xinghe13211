package com.fengshen.server.process.system;

import org.springframework.stereotype.Service;

import com.fengshen.server.data.GameReadTool;
import com.fengshen.server.data.vo.Vo_40964_0;
import com.fengshen.server.data.vo.Vo_61677_0;
import com.fengshen.server.data.write.M40964_0;
import com.fengshen.server.data.write.M61677_0;
import com.fengshen.server.data.write.M65525_0;
import com.fengshen.server.data.write.store.MSG_STORE_REMOVE;
import com.fengshen.server.domain.Chara;
import com.fengshen.server.domain.Goods;
import com.fengshen.server.game.GameCommonUtil;
import com.fengshen.server.game.GameHandler;
import com.fengshen.server.game.GameObjectChar;
import com.fengshen.server.game.GameObjectCharMng;
import com.fengshen.server.game.GameUtil;
import com.fengshen.server.util.BeanUtils;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

/**
 * 将仓库物品拿进背包
 * 
 *
 */
@Service
@Slf4j
public class CMD_TAKE implements GameHandler {
	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		int id = GameReadTool.readInt(buff);
		int from_pos = GameReadTool.readShort(buff);
		int to_pos = GameReadTool.readShort(buff);
		int amount = GameReadTool.readShort(buff);
		log.info("将仓库物品拿进背包:{}，{}，{}，{}", id, from_pos, to_pos, amount);
		Chara chara = GameObjectChar.getGameObjectChar().chara;
		Goods removeGoods = null;
		// 变身卡
		if (from_pos >= 2001 && from_pos <= 2501) {
			for (int i = 0; i < chara.cardStore.size(); ++i) {
				Goods goods = chara.cardStore.get(i);
				if (goods.pos == from_pos) {
					removeGoods = goods;
					break;
				}
			}
			int pos = GameUtil.packPoint(chara);
			if (pos == -1) {
				return;
			}
			if (removeGoods.goodsInfo.owner_id == 1) {
				removeGoods.pos = pos;
				chara.cardStore.remove(removeGoods);
				chara.backpack.add(removeGoods);
				// 刷新仓库
				Vo_61677_0 vo_61677_0 = new Vo_61677_0("card_store");
				vo_61677_0.pos = from_pos;
				GameObjectChar.send(new MSG_STORE_REMOVE(), vo_61677_0);
			} else {
				removeGoods.goodsInfo.owner_id -= 1;
				// 变身卡套
				Vo_61677_0 vo_61677_0 = new Vo_61677_0("card_store");
				vo_61677_0.list = chara.cardStore;
				GameObjectChar.send(new M61677_0(), vo_61677_0);
				Goods newGoods = BeanUtils.clone(removeGoods);
				newGoods.pos = pos;
				newGoods.goodsInfo.owner_id = 1;
				chara.backpack.add(newGoods);
			}
			GameUtil.sendMeTips("已将#R" + removeGoods.goodsInfo.str + "#n成功出去到包裹。");
			GameObjectChar gameObject = GameObjectCharMng.getGameObjectChar(chara.id);
			if (gameObject != null) {
				gameObject.sendOne(new M65525_0(), chara.backpack);
			}
		} else {
			for (int i = 0; i < chara.cangku.size(); ++i) {
				Goods goods = chara.cangku.get(i);
				if (goods.pos == from_pos) {
					removeGoods = goods;
					break;
				}
			}
			GameCommonUtil.addStoreGoodsToBackpack(removeGoods, chara);
		}
		if (removeGoods != null) {
			Vo_40964_0 vo_40964_0 = new Vo_40964_0();
			vo_40964_0.type = 1;
			vo_40964_0.name = removeGoods.goodsInfo.str;
			vo_40964_0.param = "156482";
			vo_40964_0.rightNow = 2;
			GameObjectChar.send(new M40964_0(), vo_40964_0);
		}
	}

	@Override
	public int cmd() {
		return 16502;
	}
}
