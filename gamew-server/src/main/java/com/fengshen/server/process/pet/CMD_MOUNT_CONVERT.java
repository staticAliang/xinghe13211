package com.fengshen.server.process.pet;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.fengshen.db.domain.StoreInfo;
import com.fengshen.server.data.GameReadTool;
import com.fengshen.server.data.write.pet.MSG_UPDATE_PETS;
import com.fengshen.server.domain.Chara;
import com.fengshen.server.domain.PetShuXing;
import com.fengshen.server.domain.Petbeibao;
import com.fengshen.server.game.GameData;
import com.fengshen.server.game.GameHandler;
import com.fengshen.server.game.GameObjectChar;
import com.fengshen.server.game.GameUtil;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

/**
 * 宠物转化
 */
@Service
public class CMD_MOUNT_CONVERT implements GameHandler {
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		GameObjectChar gameObjectChar = GameObjectChar.getGameObjectChar();
		Chara chara = gameObjectChar.chara;
		int no = GameReadTool.readShort(buff);
		Petbeibao petbeibao = null;
		for (Petbeibao pet : chara.pets) {
			if (pet.no == no) {
				petbeibao = pet;
				//删除宠物
				GameData.that.charaPetService.deleteByPrimaryKey(pet.id);
				break;
			}
		}
		if(petbeibao == null) {
			return;
		}
		int capacity_level = ((PetShuXing) petbeibao.petShuXing.get(0)).hide_mount;
		if (capacity_level < 2 || capacity_level > 4) {
			GameUtil.sendTips("宠物阶级过高或过低无法转哈");
			return;
		}

		chara.pets.remove(petbeibao);
		petbeibao.no = 0;
		List<Petbeibao> list = new ArrayList<>();
		list.add(petbeibao);
		GameObjectChar.send(new MSG_UPDATE_PETS(), list);

		String name = capacity_level + "阶骑宠灵魄";
		StoreInfo info = GameData.that.baseStoreInfoService.findOneByName(name);
		GameUtil.huodedaoju(gameObjectChar, info, 1);
		
		GameUtil.sendTips("恭喜您获得了#R" + name + "#n");
	}

	public int cmd() {
		return 0x5024;
	}

}