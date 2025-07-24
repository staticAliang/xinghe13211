package com.fengshen.server.process.fashion;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Service;

import com.fengshen.server.data.GameReadTool;
import com.fengshen.server.data.vo.Vo_41505_0;
import com.fengshen.server.data.vo.Vo_4197_0;
import com.fengshen.server.data.vo.Vo_61677_0;
import com.fengshen.server.data.vo.Vo_8165_0;
import com.fengshen.server.data.vo.user.Vo_UPDATE_APPEARANCE;
import com.fengshen.server.data.write.M41505_0;
import com.fengshen.server.data.write.M4197_0;
import com.fengshen.server.data.write.M61677_0;
import com.fengshen.server.data.write.M65525_0;
import com.fengshen.server.data.write.M8165_0;
import com.fengshen.server.data.write.MSG_DISAPPEAR;
import com.fengshen.server.data.write.user.MSG_UPDATE_APPEARANCE;
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
 * 卸下时装
 * 
 *
 */
@Service
@Slf4j
public class CMD_FASION_CUSTOM_UNEQUIP implements GameHandler {
	@Override
	public void process(final ChannelHandlerContext ctx, final ByteBuf buff) {
		int pos = GameReadTool.readShort(buff);
		log.info("卸下时装");
		GameObjectChar gameObjectChar = GameObjectChar.getGameObjectChar();
		Chara chara = gameObjectChar.chara;
		for (int i = 0; i < chara.otherGoods.size(); ++i) {
			if (chara.otherGoods.get(i).pos == pos) {
				List<Goods> listbeibao = new ArrayList<Goods>();
				Goods goods2 = new Goods();
				goods2.goodsBasics = null;
				goods2.goodsInfo = null;
				goods2.goodsLanSe = null;
				goods2.pos = chara.otherGoods.get(i).pos;
				listbeibao.add(goods2);
				GameObjectChar.send(new M65525_0(), listbeibao);
				chara.otherGoods.remove(chara.otherGoods.get(i));
			}
		}
		if (pos == 31) {
			chara.special_icon = 0;
		}
		if (pos == 32) {
			if (chara.effectIcons != null) {
				chara.effectIcons.remove("fasionEffectIcon");
			}
		}
		if (pos == 37) {
			Vo_4197_0 vo_4197_0 = new Vo_4197_0();
			vo_4197_0.id = 0;
			gameObjectChar.gameMap.send(new M4197_0(), vo_4197_0);
			gameObjectChar.gameMap.send(new MSG_DISAPPEAR(), chara.genchong_icon);
			chara.genchong_icon = 0;
		}
		Vo_61677_0 vo_61677_0 = new Vo_61677_0();
		// 自定义时装
		if (pos == 33 || pos == 34 || pos == 35 || pos == 36) {
			vo_61677_0 = new Vo_61677_0();
			vo_61677_0.store_type = "custom_store";
			vo_61677_0.npcID = 0;
			vo_61677_0.list = chara.customShizhuang;
			vo_61677_0.count = chara.customShizhuang.size();
			GameObjectChar.send(new M61677_0(), vo_61677_0);
			// 状态恢复默认
			chara.special_icon = 0;
			chara.customIcon = "";
		}
		// 背饰
		if (pos == 38) {
			List<String> itemList = new ArrayList<>();
			for (int i = 0; i < chara.otherGoods.size(); ++i) {
				Integer pos2 = chara.otherGoods.get(i).pos;
				if (pos2 == 33 || pos2 == 34 || pos2 == 35 || pos2 == 36) {
					itemList.add(chara.otherGoods.get(i).goodsInfo.str);
				}
			}
			GameCommonUtil.getFasionCustomEquipEx(chara, itemList.toArray(new String[itemList.size()]));
		}
		
		//队标
		if(pos == 39) {
			Iterator<Goods> iterator = chara.otherGoods.iterator();
			while(iterator.hasNext()) {
				Goods next = iterator.next();
				if(next.pos == 39) {
					chara.otherGoods.remove(next);
					break;
				}
			}
			List<Goods> teamIconStore = chara.teamIconStore;
			vo_61677_0 = new Vo_61677_0();
			vo_61677_0.store_type = "team_icon_store";
			vo_61677_0.npcID = 0;
			vo_61677_0.list = teamIconStore;
			vo_61677_0.count = teamIconStore.size();
			GameObjectChar.send(new M61677_0(), vo_61677_0);
			chara.teamIcon = 0;
		}
		
		vo_61677_0.store_type = "follow_pet_store";
		vo_61677_0.npcID = 0;
		vo_61677_0.list = chara.genchong;
		vo_61677_0.count = chara.genchong.size();
		GameObjectChar.send(new M61677_0(), vo_61677_0);
		vo_61677_0 = new Vo_61677_0();
		vo_61677_0.store_type = "fasion_store";
		vo_61677_0.npcID = 0;
		vo_61677_0.list = chara.shizhuang;
		vo_61677_0.count = chara.shizhuang.size();
		GameObjectChar.send(new M61677_0(), vo_61677_0);
		vo_61677_0 = new Vo_61677_0();
		vo_61677_0.store_type = "effect_store";
		vo_61677_0.npcID = 0;
		vo_61677_0.list = chara.texiao;
		vo_61677_0.count = chara.texiao.size();
		GameObjectChar.send(new M61677_0(), vo_61677_0);
		Vo_UPDATE_APPEARANCE vo_61661_0 = GameUtil.a61661(chara);
		gameObjectChar.gameMap.send(new MSG_UPDATE_APPEARANCE(), vo_61661_0);
		
		Vo_8165_0 vo_8165_0 = new Vo_8165_0();
		vo_8165_0.msg = "卸下成功！";
		vo_8165_0.active = 0;
		GameObjectChar.send(new M8165_0(), vo_8165_0);
		
		Vo_41505_0 vo_41505_0 = new Vo_41505_0();
		vo_41505_0.type = "unequip_fasion";
		GameObjectChar.send(new M41505_0(), vo_41505_0);
	}

	@Override
	public int cmd() {
		return 41501;
	}
}