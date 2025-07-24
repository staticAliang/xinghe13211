package com.fengshen.server.process.pet;

import org.springframework.stereotype.Service;

import com.fengshen.db.domain.StoreInfo;
import com.fengshen.server.data.GameReadTool;
import com.fengshen.server.data.vo.Vo_12269_0;
import com.fengshen.server.data.vo.Vo_20481_0;
import com.fengshen.server.data.vo.Vo_40964_0;
import com.fengshen.server.data.write.M12269_0;
import com.fengshen.server.data.write.M20481_0;
import com.fengshen.server.data.write.M40964_0;
import com.fengshen.server.domain.Chara;
import com.fengshen.server.game.GameData;
import com.fengshen.server.game.GameHandler;
import com.fengshen.server.game.GameObjectChar;
import com.fengshen.server.game.GameUtil;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

/**
 * 喂养宠物
 * 
 *
 */
@Service
@Slf4j
public class CMD_DROP_PET implements GameHandler {
	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		int id = GameReadTool.readInt(buff);
		log.info("喂养宠物,id={}",id);
		GameObjectChar gameObjectChar = GameObjectChar.getGameObjectChar();
		Chara chara = gameObjectChar.chara;
		int owner_id = 1;
		for (int i = 0; i < chara.pets.size(); ++i) {
			if (chara.pets.get(i).id == id) {
				if (chara.pets.get(i).petShuXing.get(0).skill >= 50) {
					owner_id = 10;
				}
				if (chara.pets.get(i).petShuXing.get(0).penetrate == 1) {
					owner_id = 0;
				}
				chara.pets.remove(chara.pets.get(i));
				
				Vo_20481_0 vo_20481_0 = new Vo_20481_0();
				vo_20481_0.msg = "获得了#R" + owner_id + "#n颗宠物经验丹。";
				vo_20481_0.time = (int) (System.currentTimeMillis() / 1000L);
				GameObjectChar.send(new M20481_0(), vo_20481_0);

				Vo_40964_0 vo_40964_0 = new Vo_40964_0();
				vo_40964_0.type = 1;
				vo_40964_0.name = "宠物经验丹";
				vo_40964_0.param = "1";
				vo_40964_0.rightNow = 0;
				GameObjectChar.send(new M40964_0(), vo_40964_0);
				StoreInfo storeInfo = GameData.that.baseStoreInfoService.findOneByName("宠物经验丹");
				GameUtil.huodedaoju(gameObjectChar, storeInfo, owner_id);
				// 删除宠物
				GameData.that.charaPetService.deleteByPrimaryKey(id);
				//失去宠物
				Vo_12269_0 vo_12269_0 = new Vo_12269_0();
				vo_12269_0.id = id;
				vo_12269_0.owner_id = 0;
				GameObjectChar.send(new M12269_0(), vo_12269_0);
				break;
			}
		}
		
	}

	@Override
	public int cmd() {
		return 4230;
	}
}