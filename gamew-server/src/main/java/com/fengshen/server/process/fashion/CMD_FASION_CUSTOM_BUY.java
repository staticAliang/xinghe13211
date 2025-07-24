package com.fengshen.server.process.fashion;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Service;

import com.fengshen.db.domain.FasionCustomInfo;
import com.fengshen.server.data.GameReadTool;
import com.fengshen.server.data.vo.Vo_41505_0;
import com.fengshen.server.data.vo.Vo_61677_0;
import com.fengshen.server.data.vo.user.Vo_FASION_TEAM_ICON_LIST;
import com.fengshen.server.data.write.M41505_0;
import com.fengshen.server.data.write.M61677_0;
import com.fengshen.server.data.write.store.MSG_STORE_REMOVE;
import com.fengshen.server.data.write.user.MSG_FASION_TEAM_ICON_LIST;
import com.fengshen.server.domain.Chara;
import com.fengshen.server.domain.Goods;
import com.fengshen.server.game.GameCommonUtil;
import com.fengshen.server.game.GameData;
import com.fengshen.server.game.GameHandler;
import com.fengshen.server.game.GameObjectChar;
import com.fengshen.server.game.GameUtil;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

/**
 * 购买时装
 * 
 *
 */
@Service
@Slf4j
public class CMD_FASION_CUSTOM_BUY implements GameHandler {

	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {

		String name = GameReadTool.readString(buff);
		String para = GameReadTool.readString(buff);	
		GameObjectChar gameObjectChar = GameObjectChar.getGameObjectChar();
		Chara chara = gameObjectChar.chara;
		FasionCustomInfo fc = GameData.that.fasionCustomInfoService
				.getOneFasionCustomInfoByName(name);
		if(fc != null) {
			if(chara.goldCoin<fc.getGoodsPrice()) {
				GameUtil.sendMeTips("金元宝不足！");
				return;
			}
			if("team_icon".equals(para)) {
				//如果队标存在
				Iterator<Goods> teamIconStore = chara.teamIconStore.iterator();
				while(teamIconStore.hasNext()) {
					Goods next = teamIconStore.next();
					if(next.pos == fc.getPosition()) {
						GameUtil.sendMeTips("请勿重复购买！");
						return;
					}
				}
				//购买队标
				Goods goods = new Goods();
				goods.goodsInfo.owner_id = 1;
				goods.goodsInfo.value = 0;
				goods.goodsInfo.quality = "金色";
				goods.goodsInfo.alias = name;
				goods.goodsInfo.amount = 20;
				goods.goodsInfo.food_num = 2;
				goods.pos = fc.getPosition();
				goods.goodsInfo.master = chara.sex;
				goods.goodsInfo.recognize_recognized = 2;
				goods.goodsInfo.type = fc.getIcon();
				goods.goodsInfo.total_score = 29;
				goods.goodsInfo.damage_sel_rate = 1842075;
				goods.goodsInfo.str = fc.getName();
				goods.goodsInfo.metal = chara.polar;
				goods.goodsInfo.durability = 8;
				goods.goodsInfo.rebuild_level = 500;
				goods.goodsInfo.auto_fight = GameCommonUtil.UUID().toLowerCase();
				chara.teamIconStore.add(goods);
				//刷新自定义队标仓库
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
				gameObjectChar.sendOne(new MSG_FASION_TEAM_ICON_LIST(), items);
				Vo_41505_0 vo_41505_0 = new Vo_41505_0();
				vo_41505_0.type = "view_team";
				gameObjectChar.sendOne(new M41505_0(), vo_41505_0);
				
				Vo_61677_0 vo_61677_0 = new Vo_61677_0();
				vo_61677_0.store_type = "team_icon_store";
				vo_61677_0.npcID = 0;
				vo_61677_0.list = chara.teamIconStore;
				vo_61677_0.count = chara.teamIconStore.size();
				gameObjectChar.sendOne(new M61677_0(), vo_61677_0);
				if(chara.teamIcon != 0) {
					GameObjectChar.send(new MSG_STORE_REMOVE(), removeTeam);
				}
			}
			chara.goldCoin-=fc.getGoodsPrice();
			GameUtil.sendMeTips("你花费了#Y"+fc.getGoodsPrice()+"元宝#n购买了#R"+fc.getName()+"#n。");
		}
		
		log.info("购买队标，{}，{}",name,para);
	}

	@Override
	public int cmd() {
		return 0xA213;
	}

}
