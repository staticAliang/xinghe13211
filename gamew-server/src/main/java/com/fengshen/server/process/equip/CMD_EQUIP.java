package com.fengshen.server.process.equip;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Service;

import com.fengshen.server.data.GameReadTool;
import com.fengshen.server.data.vo.Vo_45056_0;
import com.fengshen.server.data.vo.Vo_APPEAR;
import com.fengshen.server.data.vo.user.Vo_UPDATE_APPEARANCE;
import com.fengshen.server.data.write.M4155_0;
import com.fengshen.server.data.write.M45056_0;
import com.fengshen.server.data.write.M65525_0;
import com.fengshen.server.data.write.M65529_0;
import com.fengshen.server.data.write.user.MSG_UPDATE_APPEARANCE;
import com.fengshen.server.domain.Chara;
import com.fengshen.server.domain.Goods;
import com.fengshen.server.game.GameCommonUtil;
import com.fengshen.server.game.GameData;
import com.fengshen.server.game.GameHandler;
import com.fengshen.server.game.GameObjectChar;
import com.fengshen.server.game.GameObjectCharMng;
import com.fengshen.server.game.GameUtil;
import com.fengshen.server.game.GameUtilRenWu;
import com.google.common.collect.Lists;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

/**
 * 穿戴装备
 * 
 *
 */
@Service
@Slf4j
public class CMD_EQUIP implements GameHandler {
	@Override
	public void process(final ChannelHandlerContext ctx, final ByteBuf buff) {
		int pos = GameReadTool.readByte(buff);
		int equip_part = GameReadTool.readByte(buff);
		log.info("穿戴装备, pos={},equip_part={}",pos,equip_part);
		GameObjectChar gameObjectChar = GameObjectChar.getGameObjectChar();
		Chara chara = gameObjectChar.chara;
		if (pos < 0) {
			pos = 129 + pos + 127;

		}
		if (chara.current_task.equals("主线—浮生若梦_s2")) {
			GameUtil.renwujiangli(chara);
			chara.current_task = "主线—浮生若梦_s3";
			//创建主线任务
			GameUtilRenWu.createZhuXianFuShengRuoMengTask(chara, 
					GameData.that.baseRenwuService.findOneByCurrentTask(chara.current_task));
			//剧情对话消失
			Vo_45056_0 vo_45056_4 = GameUtil.a45056(chara);
			GameObjectChar.send(new M45056_0(), vo_45056_4);
			GameObjectChar.send(new M4155_0(), 0);
		}
		//如果带的是飞行器
		if(equip_part == 40) {
			
		}
		equip(pos, equip_part, gameObjectChar);
	}

	private void equip(int pos, int equip_part, GameObjectChar gameObjectChar) {
		Chara chara = gameObjectChar.chara;
		boolean has = false;
		Goods goodshas = new Goods();
		for (int i = 0; i < chara.otherGoods.size(); i++) {
			Goods goods = (Goods) chara.otherGoods.get(i);
			if (goods.pos == equip_part) {
				goodshas = goods;
				has = true;
			}
		}
		Iterator<Goods> iterator = chara.backpack.iterator();
		while (iterator.hasNext()) {
			Goods goods = iterator.next();
			if (goods.pos == pos) {
				// 性别判断只要帽子和衣服才会判断性别
				if(goods.goodsInfo.amount == 2 || goods.goodsInfo.amount == 3) {
					if (goods.goodsInfo.master != chara.sex ) {
						GameUtil.sendMeTips("性别不符");
						return;
					}
				}
				// 如果数量大于1，不允许穿戴
				else if (goods.goodsInfo.owner_id > 1) {
					return;
				}
				// 装备手镯类型
				else if ((equip_part == 6 || equip_part == 7) && goods.goodsInfo.amount != 6) {
					return;
				}
				// 装备类型
				else if (equip_part != 6 && equip_part != 7 && equip_part != 40 && goods.goodsInfo.amount != equip_part) {
					return;
				}
				// 级别判断.
				if (chara.upgrade_state != 0 && goods.goodsInfo.attrib > chara.upgrade_level) {
					GameUtil.sendMeTips("等级不符合");
					return;
				} else {
					if (goods.goodsInfo.attrib > 0 && goods.goodsInfo.attrib > chara.level) {
						GameUtil.sendMeTips("等级不符合");
						return;
					}
				}
				// 判断是否可以穿戴
				if (goods.goodsInfo.amount == 1) {
					if (goods.goodsInfo.metal != chara.polar) {
						GameUtil.sendMeTips("门派不符合");
						return;
					}
				}
				if(chara.upgrade_state != 0) {
					chara.charaYuanyingInfo.equip.put(equip_part,goods);
				}else {
					chara.charaRealInfo.equip.put(equip_part,goods);
				}
				goods.pos = equip_part;
				//把装备添加到其他去
				chara.otherGoods.add(goods);
				//删除背包这个信息
				iterator.remove();
				if (goods.pos != 1)
					break;
				chara.weapon_icon = goods.goodsInfo.type;
				break;
			}
		}
		//原来的装备还存在
		if (has) {
			goodshas.pos = pos;
			//放入到背包中去
			chara.backpack.add(goodshas);
			//删除
			chara.otherGoods.remove(goodshas);
			//刷新背包
			GameObjectChar.send(new M65525_0(), Lists.newArrayList(goodshas));
		} else {
			List<Goods> listbeibao = new ArrayList<>();
			Goods goods1 = new Goods();
			goods1.goodsBasics = null;
			goods1.goodsInfo = null;
			goods1.goodsLanSe = null;
			goods1.pos = pos;
			listbeibao.add(goods1);
			GameObjectChar.send(new M65525_0(), listbeibao);
		}
		//重新计算装备信息
		GameUtil.a65511(gameObjectChar);
		//更新人物数据
		Vo_APPEAR vo_65529_0 = GameUtil.a65529(chara);
		GameObjectChar.send(new M65529_0(), vo_65529_0);
		if(equip_part == 40) {
			GameCommonUtil.flyInit(gameObjectChar);
			if (GameCommonUtil.isNotGameTeam(gameObjectChar.gameTeam, gameObjectChar.chara)) {
				for (Chara teamChara : gameObjectChar.gameTeam.duiwu) {
					GameObjectChar teamGameObjectChar = GameObjectCharMng.getGameObjectChar(teamChara.id);
					Vo_UPDATE_APPEARANCE vo_61661_0 = GameUtil.a61661(teamGameObjectChar.chara);
					teamGameObjectChar.gameMap.send(new MSG_UPDATE_APPEARANCE(), vo_61661_0);
				}
			}else {
				Vo_UPDATE_APPEARANCE vo_61661_0 = GameUtil.a61661(chara);
				gameObjectChar.gameMap.send(new MSG_UPDATE_APPEARANCE(), vo_61661_0);
			}
		}
	}

	@Override
	public int cmd() {
		return 4136;
	}
}