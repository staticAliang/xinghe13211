package com.fengshen.server.process.fashion;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.fengshen.db.domain.FasionCustomInfo;
import com.fengshen.db.domain.PackModification;
import com.fengshen.server.data.GameReadTool;
import com.fengshen.server.data.vo.Vo_41488_0;
import com.fengshen.server.data.vo.Vo_41505_0;
import com.fengshen.server.data.vo.Vo_61677_0;
import com.fengshen.server.data.vo.Vo_41488_0.Items;
import com.fengshen.server.data.write.M41488_0;
import com.fengshen.server.data.write.M41505_0;
import com.fengshen.server.data.write.M61677_0;
import com.fengshen.server.domain.Chara;
import com.fengshen.server.game.GameData;
import com.fengshen.server.game.GameHandler;
import com.fengshen.server.game.GameObjectChar;
import com.fengshen.server.game.GameUtil;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

/**
 * 自定义时装切换标签页
 * 
 *
 */
@Service
@Slf4j
public class CMD_FASION_CUSTOM_SWITCH implements GameHandler {
	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		int fasion_label = GameReadTool.readByte(buff);
		Chara chara = GameObjectChar.getGameObjectChar().chara;
		Vo_41488_0 vo_41488_0 = new Vo_41488_0();
		vo_41488_0.flag = 1;
		vo_41488_0.label = fasion_label;
		vo_41488_0.para = "CustomDressDlg";
		List<Items> items = new ArrayList<>();
		if(fasion_label == 1) {
			GameUtil.sendMeTips("请注意自定义时装消耗的是#Y积分#n,请知晓!");
			List<FasionCustomInfo> fcs = GameData.that.fasionCustomInfoService.getFasionCustomInfoByCategory(4);
			for(FasionCustomInfo fc:fcs) {
				items.add(vo_41488_0.new Items(fc.getName(),fc.getGoodsPrice()));
			}
			vo_41488_0.items = items;
			GameObjectChar.send(new M41488_0(), vo_41488_0);
			
			Vo_61677_0 vo_61677_0 = new Vo_61677_0();
			vo_61677_0.store_type = "custom_store";
			vo_61677_0.npcID = 0;
			vo_61677_0.list = chara.customShizhuang;
			vo_61677_0.count = chara.customShizhuang.size();
			GameObjectChar.send(new M61677_0(), vo_61677_0);
		}else {
			List<PackModification> findByCategory = GameData.that.basePackModificationService.findByCategory(1);
			for(PackModification p:findByCategory) {
				items.add(vo_41488_0.new Items(p.getAlias(),p.getGoodsPrice()));
			}
			items.add(vo_41488_0.new Items("龙凤呈祥服·新郎",88));
			vo_41488_0.items = items;
			GameObjectChar.send(new M41488_0(), vo_41488_0);
		}
		
		Vo_41505_0 vo_41505_0 = new Vo_41505_0();
		vo_41505_0.type = "";
		GameObjectChar.send(new M41505_0(), vo_41505_0);
		log.info("自定义时装切换标签页");
	}

	@Override
	public int cmd() {
		return 41489;
	}
}