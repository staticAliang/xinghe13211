package com.fengshen.server.process.fashion;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.fengshen.db.domain.FasionCustomInfo;
import com.fengshen.db.domain.PackModification;
import com.fengshen.server.data.GameReadTool;
import com.fengshen.server.data.vo.Vo_41488_0;
import com.fengshen.server.data.vo.Vo_41488_0.Items;
import com.fengshen.server.data.vo.Vo_41505_0;
import com.fengshen.server.data.vo.Vo_61677_0;
import com.fengshen.server.data.vo.user.Vo_FASION_TEAM_ICON_LIST;
import com.fengshen.server.data.vo.user.Vo_UPDATE_APPEARANCE;
import com.fengshen.server.data.write.M41488_0;
import com.fengshen.server.data.write.M41505_0;
import com.fengshen.server.data.write.M61677_0;
import com.fengshen.server.data.write.fashion.MSG_PET_FASION_CUSTOM_LIST;
import com.fengshen.server.data.write.store.MSG_STORE_REMOVE;
import com.fengshen.server.data.write.user.MSG_FASION_TEAM_ICON_LIST;
import com.fengshen.server.data.write.user.MSG_UPDATE_APPEARANCE;
import com.fengshen.server.domain.Chara;
import com.fengshen.server.domain.Goods;
import com.fengshen.server.game.GameData;
import com.fengshen.server.game.GameHandler;
import com.fengshen.server.game.GameObjectChar;
import com.fengshen.server.game.GameUtil;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

/**
 * 打开时装自定义界面
 * 
 *
 */
@Service
public class C41487_0 implements GameHandler {
	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		String para = GameReadTool.readString(buff);
		Chara chara = GameObjectChar.getGameObjectChar().chara;
		Vo_61677_0 vo_61677_0 = new Vo_61677_0();
		if(chara.shizhuang != null) {
			vo_61677_0.store_type = "fasion_store";
			vo_61677_0.npcID = 0;
			vo_61677_0.list = chara.shizhuang;
			vo_61677_0.count = chara.shizhuang.size();
			GameObjectChar.send(new M61677_0(), vo_61677_0);
		}

//		vo_61677_0 = new Vo_61677_0();
//		vo_61677_0.store_type = "custom_store";
//		vo_61677_0.npcID = 0;
//		GameObjectChar.send(new M61677_SHIZHUANG(), vo_61677_0);

		vo_61677_0 = new Vo_61677_0();
		vo_61677_0.store_type = "effect_store";
		vo_61677_0.npcID = 0;
		vo_61677_0.list = chara.texiao;
		vo_61677_0.count = chara.texiao.size();
		GameObjectChar.send(new M61677_0(), vo_61677_0);
		
		
		

		for (int i = 0; i < chara.otherGoods.size(); ++i) {
			Goods goods = chara.otherGoods.get(i);
			if (goods.pos == 31) {
				PackModification packModification = GameData.that.basePackModificationService
						.findOneByStr(goods.goodsInfo.str);
				Vo_61677_0 vo_61677_2 = new Vo_61677_0();
				vo_61677_2.store_type = "fasion_store";
				vo_61677_2.npcID = 0;
				vo_61677_2.count = 1;
				vo_61677_2.isGoon = 0;
				vo_61677_2.pos = packModification.getPosition();
				GameObjectChar.send(new MSG_STORE_REMOVE(), vo_61677_2);
			}
		}
		Vo_UPDATE_APPEARANCE vo_61661_0 = GameUtil.a61661(chara);
		GameObjectChar.getGameObjectChar().gameMap.send(new MSG_UPDATE_APPEARANCE(), vo_61661_0);
		if ("team_icon".equals(para)) {
			List<Vo_FASION_TEAM_ICON_LIST> items = new ArrayList<>();
			List<FasionCustomInfo> fcs = GameData.that.fasionCustomInfoService.getFasionCustomInfoByCategory(5);
			Vo_61677_0 removeTeam = new Vo_61677_0();;
			removeTeam.store_type = "team_icon_store";
			removeTeam.npcID = 0;
			removeTeam.count = 1;
			removeTeam.isGoon = 0;
			for (FasionCustomInfo f : fcs) {
				if(f.getIcon()==chara.teamIcon) {
					removeTeam.pos = f.getPosition();
					continue;
				}
				items.add(new Vo_FASION_TEAM_ICON_LIST(f.getName(), f.getGoodsPrice()));
			}
			//如果为零则标识
			if(removeTeam.pos == 0) {
				removeTeam.pos = 39;
			}
			GameObjectChar.send(new MSG_FASION_TEAM_ICON_LIST(), items);
			Vo_41505_0 vo_41505_0 = new Vo_41505_0();
			vo_41505_0.type = "view_team";
			GameObjectChar.send(new M41505_0(), vo_41505_0);
			List<Goods> teamIconStore = chara.teamIconStore;
			vo_61677_0 = new Vo_61677_0();
			vo_61677_0.store_type = "team_icon_store";
			vo_61677_0.npcID = 0;
			
			List<Goods> showTeanIconStores = new ArrayList<>();
			for(Goods g:teamIconStore) {
				if(g.pos != removeTeam.pos) {
					showTeanIconStores.add(g);
				}
			}
			vo_61677_0.list = showTeanIconStores;
			vo_61677_0.count = showTeanIconStores.size();
			GameObjectChar.send(new M61677_0(), vo_61677_0);
			if(chara.teamIcon != 0) {
				GameObjectChar.send(new MSG_STORE_REMOVE(), removeTeam);
			}
		} else {
			Vo_41488_0 vo_41488_0 = new Vo_41488_0();
			vo_41488_0.flag = 1;
			vo_41488_0.label = 0;
			vo_41488_0.para = para;

			List<PackModification> findByCategory = GameData.that.basePackModificationService.findByCategory(1);
			List<Items> items = new ArrayList<>();
			for (PackModification p : findByCategory) {
				items.add(vo_41488_0.new Items(p.getAlias(), p.getGoodsPrice()));
			}
			vo_41488_0.items = items;
			GameObjectChar.send(new M41488_0(), vo_41488_0);
			GameObjectChar.send(new MSG_PET_FASION_CUSTOM_LIST(), vo_41488_0);
		}
	}

	@Override
	public int cmd() {
		return 41487;
	}
}