package com.fengshen.server.process.equip;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Service;

import com.fengshen.server.data.vo.Vo_APPEAR;
import com.fengshen.server.data.vo.user.Vo_UPDATE_APPEARANCE;
import com.fengshen.server.data.write.M65525_0;
import com.fengshen.server.data.write.M65529_0;
import com.fengshen.server.data.write.user.MSG_UPDATE_APPEARANCE;
import com.fengshen.server.domain.Chara;
import com.fengshen.server.domain.Goods;
import com.fengshen.server.game.GameCommonUtil;
import com.fengshen.server.game.GameHandler;
import com.fengshen.server.game.GameObjectChar;
import com.fengshen.server.game.GameUtil;
import com.fengshen.server.util.BeanUtils;
import com.google.common.collect.Lists;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

/**
 *  装备切换
 * 
 *
 */
@Service
public class CMD_SWITCH_BACK_EQUIP implements GameHandler {

	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		GameObjectChar gameObjectChar = GameObjectChar.getGameObjectChar();
		if(gameObjectChar == null) {
			return;
		}
		if(gameObjectChar.privilege == 0 && 
				GameCommonUtil.rejectRequestTimeOut(gameObjectChar.chara, 
						"switchBackEquip", "装备切换过于频繁，请稍后再试。", 10)) {
        	return;
        }
		Chara chara = gameObjectChar.chara;
		//主装备(当前穿戴装备)
		List<Goods> mEquipGoods = new ArrayList<>();
		//副装备
		List<Goods> oEquipGoods = new ArrayList<>();
		//需要通知客户端删除的装备
		List<Goods> removeGoods = new ArrayList<>();
		for(Goods goods:chara.getOtherGoods()) {
			chara.weapon_icon = 0;
			if(goods.pos>0&&goods.pos<11) {
				mEquipGoods.add(BeanUtils.clone(goods));
				Goods remove = new Goods();
				remove.goodsBasics = null;
				remove.goodsInfo = null;
				remove.pos = goods.pos;
				removeGoods.add(remove);
			}
			if(goods.pos>10&&goods.pos<21) {
				oEquipGoods.add(BeanUtils.clone(goods));
			}
		}
		//先把通知客户端刷新当前装备栏
		for(Goods goods:removeGoods) {
			GameObjectChar.send(new M65525_0(), Lists.newArrayList(goods));
		}
		//把当前背包里面所有的装备先删除了
		Iterator<Goods> it = chara.otherGoods.iterator();
		while(it.hasNext()){
		    Goods g = it.next();
		    if(g.pos>0&&g.pos<21){
		        it.remove();
		    }
		}
		//把主装备设置到第二页
		for(Goods goods:mEquipGoods) {
			if(goods.pos>0&&goods.pos<11) {
				switch (goods.pos) {
				case 1:
					goods.pos = 11;
					break;
				case 2:
					goods.pos = 12;
					break;
				case 3:
					goods.pos = 13;
					break;
				case 4:
					goods.pos = 16;
					break;
				case 5:
					goods.pos = 15;
					break;
				case 6:
					goods.pos = 17;
					break;
				case 7:
					goods.pos = 18;
					break;
				case 8:
					goods.pos = 20;
					break;
				case 9:
					goods.pos = 19;
					break;
				case 10:
					goods.pos = 14;
					break;
				}
			}
		}
		//把副装备切换到第一页
		for(Goods goods:oEquipGoods) {
			if(goods.pos>10&&goods.pos<21) {
				switch (goods.pos) {
				case 11:
					goods.pos = 1;
					chara.weapon_icon = goods.goodsInfo.type;
					break;
				case 12:
					goods.pos = 2;
					break;
				case 13:
					goods.pos = 3;
					break;
				case 14:
					goods.pos = 10;
					break;
				case 15:
					goods.pos = 5;
					break;
				case 16:
					goods.pos = 4;
					break;
				case 17:
					goods.pos = 6;
					break;
				case 18:
					goods.pos = 7;
					break;
				case 19:
					goods.pos = 9;
					break;
				case 20:
					goods.pos = 8;
					break;
				}
			}
		}
		//在把新的东西放到背包去
		chara.otherGoods.addAll(mEquipGoods);
		chara.otherGoods.addAll(oEquipGoods);
		//切换装备页面
		chara.equipPage = chara.equipPage==0?1:0;
		if(chara.upgrade_state != 0) {
			chara.charaYuanyingInfo.equipPage = chara.equipPage;
			//每次切换都初始化一下
			chara.charaYuanyingInfo.equip.clear();
			for(Goods g:chara.otherGoods) {
	            if(g.pos>=1 && g.pos<=20) {
	            	chara.charaYuanyingInfo.equip.put(g.pos, g);
	            }
			}
		}else {
			chara.charaRealInfo.equipPage = chara.equipPage;
			//每次切换都初始化一下
			chara.charaRealInfo.equip.clear();
			for(Goods g:chara.otherGoods) {
	            if(g.pos>=1 && g.pos<=20) {
	            	chara.charaRealInfo.equip.put(g.pos, g);
	            }
			}
		}
		//更新装备数据
		int listSize = chara.otherGoods.size();
		int perSize = 100;
		for (int beginIndex = 0; beginIndex < listSize; beginIndex += perSize) {
			int endIndex = Math.min(beginIndex + perSize, listSize);
			GameObjectChar.send(new M65525_0(), chara.otherGoods.subList(beginIndex, endIndex));
		}
		//MSG_UPDATE_IMPROVEMENT
		GameUtil.a65511(gameObjectChar);
		//更新人物数据
		Vo_APPEAR vo_65529_0 = GameUtil.a65529(chara);
		GameObjectChar.send(new M65529_0(), vo_65529_0);
		//当前地图
		Vo_UPDATE_APPEARANCE vo_61661_0 = GameUtil.a61661(chara);
		GameObjectChar.getGameObjectChar().gameMap.send(new MSG_UPDATE_APPEARANCE(), vo_61661_0);
	}

	@Override
	public int cmd() {
		return 0x000E;
	}

}