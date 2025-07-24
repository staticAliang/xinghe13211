package com.fengshen.server.process.shop;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.fengshen.db.domain.Pet;
import com.fengshen.server.data.GameReadTool;
import com.fengshen.server.data.vo.Vo_12269_0;
import com.fengshen.server.data.vo.Vo_20480_0;
import com.fengshen.server.data.vo.Vo_20481_0;
import com.fengshen.server.data.vo.Vo_40964_0;
import com.fengshen.server.data.vo.Vo_41045_0;
import com.fengshen.server.data.write.M12269_0;
import com.fengshen.server.data.write.M20480_0;
import com.fengshen.server.data.write.M20481_0;
import com.fengshen.server.data.write.M40964_0;
import com.fengshen.server.data.write.M41045_0;
import com.fengshen.server.data.write.pet.MSG_UPDATE_PETS;
import com.fengshen.server.domain.Chara;
import com.fengshen.server.domain.Petbeibao;
import com.fengshen.server.game.GameData;
import com.fengshen.server.game.GameHandler;
import com.fengshen.server.game.GameObjectChar;
import com.fengshen.server.game.GameUtil;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

/**
 * 变异宠物商店
 * 
 *
 */
@Service
@Slf4j
public class CMD_BUY_FROM_ELITE_PET_SHOP implements GameHandler {
	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		int type = GameReadTool.readByte(buff);
		String name = GameReadTool.readString(buff);
		log.info("变异宠物商店，{}，{}",type,name);
		GameObjectChar session = GameObjectChar.getGameObjectChar();
		Chara chara = session.chara;
		if (type == 1) {
			type = 3;
			int number = 0;
			for (int i = 0; i < chara.backpack.size(); ++i) {
				if (chara.backpack.get(i).goodsInfo.str.equals("召唤令·十二生肖")) {
					number += chara.backpack.get(i).goodsInfo.owner_id;
				}
			}
			if (number < 100) {
				Vo_20481_0 vo_20481_0 = new Vo_20481_0();
				vo_20481_0.msg = "召唤令数量不足，无法兑换";
				vo_20481_0.time = (int) (System.currentTimeMillis() / 1000L);
				GameObjectChar.send(new M20481_0(), vo_20481_0);
				return;
			}
			GameUtil.removemunber(chara, "召唤令·十二生肖", 100);
		} else {
			int number = 0;
			for (int i = 0; i < chara.backpack.size(); ++i) {
				if (chara.backpack.get(i).goodsInfo.str.equals("召唤令·上古神兽")) {
					number += chara.backpack.get(i).goodsInfo.owner_id;
				}
			}
			if (number < 100) {
				Vo_20481_0 vo_20481_0 = new Vo_20481_0();
				vo_20481_0.msg = "召唤令数量不足，无法兑换";
				vo_20481_0.time = (int) (System.currentTimeMillis() / 1000L);
				GameObjectChar.send(new M20481_0(), vo_20481_0);
				return;
			}
			type = 4;
			GameUtil.removemunber(chara, "召唤令·上古神兽", 100);
		}
		Pet pet = GameData.that.basePetService.findOneByName(name);
		Petbeibao petbeibao = new Petbeibao();
		petbeibao.PetCreate(pet, chara, 0, type, "兑换商店");
		List<Petbeibao> list = new ArrayList<Petbeibao>();
		chara.pets.add(petbeibao);
		list.add(petbeibao);
		GameObjectChar.send(new MSG_UPDATE_PETS(), list);
		Vo_12269_0 vo_12269_0 = new Vo_12269_0();
		vo_12269_0.id = petbeibao.id;
		vo_12269_0.owner_id = chara.id;
		GameObjectChar.send(new M12269_0(), vo_12269_0);
		Vo_40964_0 vo_40964_0 = new Vo_40964_0();
		vo_40964_0.type = 2;
		vo_40964_0.name = name;
		vo_40964_0.param = String.valueOf(petbeibao.petShuXing.get(0).type);
		vo_40964_0.rightNow = 0;
		GameObjectChar.send(new M40964_0(), vo_40964_0);
		Vo_20480_0 vo_20480_0 = new Vo_20480_0();
		vo_20480_0.msg = "恭喜你召唤了一只" + name;
		vo_20480_0.time = (int) (System.currentTimeMillis() / 1000L);
		GameObjectChar.send(new M20480_0(), vo_20480_0);
		Vo_20481_0 vo_20481_2 = new Vo_20481_0();
		vo_20481_2.msg = "恭喜你召唤了一只" + name;
		vo_20481_2.time = (int) (System.currentTimeMillis() / 1000L);
		GameObjectChar.send(new M20481_0(), vo_20481_2);
		Vo_41045_0 vo_41045_0 = new Vo_41045_0();
		vo_41045_0.flag = 3;
		vo_41045_0.id = petbeibao.id;
		GameObjectChar.send(new M41045_0(), vo_41045_0);
	}

	@Override
	public int cmd() {
		return 53252;
	}
}