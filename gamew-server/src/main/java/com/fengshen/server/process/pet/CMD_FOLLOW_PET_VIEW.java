package com.fengshen.server.process.pet;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.fengshen.db.domain.PackModification;
import com.fengshen.server.data.vo.Vo_41505_0;
import com.fengshen.server.data.vo.Vo_53713_0;
import com.fengshen.server.data.vo.Vo_61677_0;
import com.fengshen.server.data.write.M41505_0;
import com.fengshen.server.data.write.M53713_0;
import com.fengshen.server.data.write.M61677_0;
import com.fengshen.server.data.write.store.MSG_STORE_REMOVE;
import com.fengshen.server.domain.Chara;
import com.fengshen.server.game.GameData;
import com.fengshen.server.game.GameHandler;
import com.fengshen.server.game.GameObjectChar;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

/**
 * 请求跟随宠道具列表
 * 
 *
 */
@Service
@Slf4j
public class CMD_FOLLOW_PET_VIEW implements GameHandler {
	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		log.info("请求跟随宠道具列表");
		Chara chara = GameObjectChar.getGameObjectChar().chara;
		Vo_61677_0 vo_61677_0 = new Vo_61677_0();
		vo_61677_0.store_type = "follow_pet_store";
		vo_61677_0.npcID = 0;
		vo_61677_0.list = chara.genchong;
		vo_61677_0.count = chara.genchong.size();
		GameObjectChar.send(new M61677_0(), vo_61677_0);
		for (int i = 0; i < chara.backpack.size(); ++i) {
			if (chara.backpack.get(i).pos == 37) {
				PackModification packModification = GameData.that.basePackModificationService
						.findOneByStr(chara.backpack.get(i).goodsInfo.str);
				Vo_61677_0 vo_61677_2 = new Vo_61677_0();
				vo_61677_2.store_type = "follow_pet_store";
				vo_61677_2.npcID = 0;
				vo_61677_2.count = 1;
				vo_61677_2.isGoon = 0;
				vo_61677_2.pos = packModification.getPosition();
				GameObjectChar.send(new MSG_STORE_REMOVE(), vo_61677_2);
			}
		}
		List<Vo_53713_0> vos = new ArrayList<>();
		List<PackModification> findByCategory = GameData.that.basePackModificationService.findByCategory(3);
		for (PackModification p : findByCategory) {
			vos.add(new Vo_53713_0(p.getAlias(), p.getGoodsPrice()));
		}
		GameObjectChar.send(new M53713_0(), vos);
		Vo_41505_0 vo_41505_0 = new Vo_41505_0();
		vo_41505_0.type = "view_follow_pet";
		GameObjectChar.send(new M41505_0(), vo_41505_0);
	}

	@Override
	public int cmd() {
		return 53714;
	}
}