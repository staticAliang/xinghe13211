package com.fengshen.server.process.hunpo;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;

import com.fengshen.server.data.GameReadTool;
import com.fengshen.server.data.vo.Vo_61677_0;
import com.fengshen.server.data.write.M61677_0;
import com.fengshen.server.data.write.inventory.MSG_INVENTORY_REMOVE;
import com.fengshen.server.data.write.store.MSG_STORE_REMOVE;
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
 * 穿戴太阴之气（魂窍注入）
 * @author aaa
 *
 */
@Service
@Slf4j
public class CMD_SHENHUN_EQUIP_TAIYINZHIQI implements GameHandler {

	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		int pos = GameReadTool.readByte(buff);
		int id = GameReadTool.readInt(buff);
		log.info("穿戴太阴之气（魂窍注入）{},{}",pos,id);
		GameObjectChar gameObjectChar = GameObjectChar.getGameObjectChar();
		Chara chara = gameObjectChar.chara;
		Iterator<Goods> otherGoods = chara.otherGoods.iterator();
		//穿戴
		if(pos > 0) {
			//校验是否可以穿戴
			boolean success = false;
			if(pos == 21) { //元性窍，神魂必须达到一阶或者是开启了神魂
				if(chara.shenHunDataSate>0 || chara.shenHunDataLaye>0) {
					//允许穿戴
					success = true;
				}
			}else if(pos == 22) {
				//元神窍，神魂必须达到3阶
				if(chara.shenHunDataSate>=3) {
					//允许穿戴
					success = true;
				}
			}else if(pos == 23) {
				//元气窍，神魂必须达到3阶
				if(chara.shenHunDataSate>=5) {
					//允许穿戴
					success = true;
				}
			}else if(pos == 24) {
				//元情窍，神魂必须达到3阶
				if(chara.shenHunDataSate>=7) {
					//允许穿戴
					success = true;
				}
			}else if(pos == 25) {
				//元精窍，神魂必须达到3阶
				if(chara.shenHunDataSate>=9) {
					//允许穿戴
					success = true;
				}
			}
			if(!success) {
				GameUtil.sendMeTips("条件不满足，无法穿戴！");
				return;
			}
			while(otherGoods.hasNext()) {
				Goods goods = otherGoods.next();
				if(goods.pos == pos) {
					//当前位置已注入，删除旧的
					List<Integer> allPos = Stream.iterate(5401, item->item+1).limit(99).collect(Collectors.toList());
					int newPos = GameCommonUtil.getAvaliablePos(chara.tyzqStore, allPos);
					if (newPos == -1) {
						break;
					}
					GameObjectChar.send(new MSG_INVENTORY_REMOVE(),goods.pos);
					goods.pos = newPos;
					//唯一码
					goods.goodsInfo.damage_sel_rate = newPos;
					//再次放入到仓库中
					chara.tyzqStore.add(goods);
					//刷新仓库
					Vo_61677_0 vo_61677_0 = new Vo_61677_0();
					vo_61677_0.list = chara.tyzqStore;
					vo_61677_0.store_type = "tyzq_store";
					GameObjectChar.send(new M61677_0(), vo_61677_0);
					//删除原来的
					otherGoods.remove();
					break;
				}
			}
			
			Iterator<Goods> iterator = chara.tyzqStore.iterator();
			while(iterator.hasNext()) {
				Goods goods = iterator.next();
				if(goods.goodsInfo.damage_sel_rate == id) {
					iterator.remove();
					Vo_61677_0 vo_61677_0 = new Vo_61677_0("tyzq_store");
					vo_61677_0.pos = goods.pos;
					GameObjectChar.send(new MSG_STORE_REMOVE(), vo_61677_0);
					//删除旧的
					GameObjectChar.send(new MSG_INVENTORY_REMOVE(), id);
					goods.pos = pos;
					goods.goodsInfo.damage_sel_rate = pos;
					chara.otherGoods.add(goods);
					GameUtil.a65511(gameObjectChar);
					GameUtil.sendMeTips("注入成功");
				}
			}
		}else {
			while(otherGoods.hasNext()) {
				Goods goods = otherGoods.next();
				if(goods != null && goods.pos == id) {
					//当前位置已注入，删除旧的
					List<Integer> allPos = Stream.iterate(5401, item->item+1).limit(99).collect(Collectors.toList());
					int newPos = GameCommonUtil.getAvaliablePos(chara.tyzqStore, allPos);
					if (newPos == -1) {
						break;
					}
					GameObjectChar.send(new MSG_INVENTORY_REMOVE(),goods.pos);
					
					goods.pos = newPos;
					//唯一码
					goods.goodsInfo.damage_sel_rate = newPos;
					//再次放入到仓库中
					chara.tyzqStore.add(goods);
					//刷新仓库
					Vo_61677_0 vo_61677_0 = new Vo_61677_0();
					vo_61677_0.list = chara.tyzqStore;
					vo_61677_0.store_type = "tyzq_store";
					GameObjectChar.send(new M61677_0(), vo_61677_0);
					//重新计算伤害
					GameUtil.a65511(gameObjectChar);
					//删除原来的
					otherGoods.remove();
					break;
				}
			}
		}
	}

	@Override
	public int cmd() {
		return 0x5306;
	}

}
