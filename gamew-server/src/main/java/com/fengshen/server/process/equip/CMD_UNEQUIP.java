package com.fengshen.server.process.equip;

import java.util.Iterator;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.fengshen.server.data.GameReadTool;
import com.fengshen.server.data.vo.Vo_APPEAR;
import com.fengshen.server.data.vo.user.Vo_UPDATE_APPEARANCE;
import com.fengshen.server.data.write.M65525_0;
import com.fengshen.server.data.write.M65529_0;
import com.fengshen.server.data.write.inventory.MSG_INVENTORY_REMOVE;
import com.fengshen.server.data.write.user.MSG_UPDATE_APPEARANCE;
import com.fengshen.server.domain.Chara;
import com.fengshen.server.domain.Goods;
import com.fengshen.server.game.GameCommonUtil;
import com.fengshen.server.game.GameHandler;
import com.fengshen.server.game.GameObjectChar;
import com.fengshen.server.game.GameObjectCharMng;
import com.fengshen.server.game.GameUtil;
import com.fengshen.server.util.BeanUtils;
import com.google.common.collect.Lists;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

/**
 * 卸下装备
 * 
 *
 */
@Service
@Slf4j
public class CMD_UNEQUIP implements GameHandler {
	@Override
	public void process(final ChannelHandlerContext ctx, final ByteBuf buff) {
		int from_pos = GameReadTool.readByte(buff);
		int to_pos = GameReadTool.readByte(buff);
		if (to_pos < 0) {
			to_pos = 129 + to_pos + 127;
		}
		log.info("卸下装备");
		GameObjectChar session = GameObjectChar.getGameObjectChar();
		Chara chara = session.chara;
		
		//真身娃娃装备
		Map<Integer, Goods> equips = null;
		if (chara.upgrade_state != 0) {
			equips = chara.charaYuanyingInfo.equip;
		} else {
			equips = chara.charaRealInfo.equip;
		}
		equips.remove(from_pos);
		//不管有没有找到都要删除
		GameObjectChar.send(new MSG_INVENTORY_REMOVE(), from_pos);
		
		//开始遍历
		Iterator<Goods> iterator = chara.otherGoods.iterator();
		while (iterator.hasNext()) {
			Goods goods = iterator.next();
			if (goods.pos == from_pos) {
				// 武器
				if (from_pos == 1) {
					chara.weapon_icon = 0;
				}
				//刷新被删除的物品
				GameObjectChar.send(new MSG_INVENTORY_REMOVE(), goods.pos);
				// 更新角色数据--背包人物界面
				Vo_APPEAR vo_65529_0 = GameUtil.a65529(chara);
				GameObjectChar.send(new M65529_0(), vo_65529_0);
				//更新信息
				if(chara.upgrade_state != 0) {
					chara.charaYuanyingInfo.equip.remove(from_pos);
				}else {
					chara.charaRealInfo.equip.remove(from_pos);
				}
				Goods newGoods = BeanUtils.clone(goods);
				//重新添加物品
				newGoods.pos = to_pos;
				//重新添加到背包
				chara.backpack.add(newGoods);
				//刷新下背包
				GameObjectChar.send(new M65525_0(), Lists.newArrayList(newGoods));
				//删除
				iterator.remove();
				//刷新人物属性信息
				GameUtil.a65511(session);
				break;
			}
		}
		if(from_pos == 40) {
			session.moveIds.clear();
			session.flyType = 0;
			session.moveType = 0;
			if (GameCommonUtil.isNotGameTeam(session.gameTeam, session.chara)) {
				for (Chara teamChara : session.gameTeam.duiwu) {
					GameObjectChar teamGameObjectChar = GameObjectCharMng.getGameObjectChar(teamChara.id);
					teamGameObjectChar.moveIds.clear();
					teamGameObjectChar.flyType = 0;
					teamGameObjectChar.moveType = 0;
					GameCommonUtil.flyInit(teamGameObjectChar);
					Vo_UPDATE_APPEARANCE vo_61661_0 = GameUtil.a61661(teamGameObjectChar.chara);
					teamGameObjectChar.gameMap.send(new MSG_UPDATE_APPEARANCE(), vo_61661_0);
				}
			}else {
				GameCommonUtil.flyInit(session);
				Vo_UPDATE_APPEARANCE vo_61661_0 = GameUtil.a61661(chara);
				session.gameMap.send(new MSG_UPDATE_APPEARANCE(), vo_61661_0);
			}
		}
	}

	@Override
	public int cmd() {
		return 8234;
	}
}
