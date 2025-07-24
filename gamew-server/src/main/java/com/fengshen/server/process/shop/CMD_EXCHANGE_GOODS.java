package com.fengshen.server.process.shop;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.fengshen.db.domain.CreepsStore;
import com.fengshen.db.domain.Pet;
import com.fengshen.server.data.GameReadTool;
import com.fengshen.server.data.game.BasicAttributesUtils;
import com.fengshen.server.data.vo.ListVo_65527_0;
import com.fengshen.server.data.vo.Vo_20481_0;
import com.fengshen.server.data.vo.Vo_40964_0;
import com.fengshen.server.data.write.M20481_0;
import com.fengshen.server.data.write.M40964_0;
import com.fengshen.server.data.write.M65527_0;
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
import lombok.extern.slf4j.Slf4j;


/**
 * 天技秘笈商店
 * 
 *
 */
@Service
@Slf4j
public class CMD_EXCHANGE_GOODS implements GameHandler {
	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		int type = GameReadTool.readByte(buff);
		String name = GameReadTool.readString(buff);
		int amount = GameReadTool.readShort(buff);
		log.info("天技秘笈商店，type={},name={},amount={}",type,name,amount);
		Chara chara = GameObjectChar.getGameObjectChar().chara;
		if (1 == type) {
			CreepsStore creepsStore = GameData.that.baseCreepsStoreService.findOneByName(name);
			Integer price = creepsStore.getPrice();
			Pet pet = GameData.that.basePetService.findOneByName(name);
			Petbeibao petbeibao = new Petbeibao();
			petbeibao.petCreate(pet, chara, 20, "商店购买");
			petbeibao.petShuXing.get(0).penetrate = 1;
			PetShuXing shuXing = petbeibao.petShuXing.get(0);
			chara.pets.add(petbeibao);
			shuXing.skill = pet.getLevelReq();
			shuXing.attrib = pet.getLevelReq();
			int polar_point = shuXing.skill * 4;
			int addpoint = this.subtraction(polar_point - shuXing.skill * 3);
			polar_point -= addpoint;
			shuXing.life = shuXing.skill + addpoint;
			addpoint = this.subtraction(polar_point);
			polar_point -= addpoint;
			shuXing.mag_power = shuXing.skill + addpoint;
			addpoint = this.subtraction(polar_point);
			polar_point -= addpoint;
			shuXing.phy_power = shuXing.skill + addpoint;
			addpoint = this.subtraction(polar_point);
			polar_point -= addpoint;
			shuXing.speed = shuXing.skill + addpoint;
			shuXing.polar_point = 0;
			List<Petbeibao> list = new ArrayList<>();
			BasicAttributesUtils.petshuxing(shuXing, petbeibao);
			shuXing.max_life = shuXing.def;
			shuXing.max_mana = shuXing.dex;
			list.add(petbeibao);
			GameObjectChar.send(new MSG_UPDATE_PETS(), list);
			chara.cash -= price;
			ListVo_65527_0 listVo_65527_0 = GameUtil.a65527(chara);
			GameObjectChar.send(new M65527_0(), listVo_65527_0);
			Vo_40964_0 vo_40964_0 = new Vo_40964_0();
			vo_40964_0.type = 2;
			vo_40964_0.name = name;
			vo_40964_0.param = String.valueOf(pet.getIcon());
			vo_40964_0.rightNow = 0;
			GameObjectChar.send(new M40964_0(), vo_40964_0);
			Vo_20481_0 vo_20481_0 = new Vo_20481_0();
			vo_20481_0.msg = "你购买了一只#Y" + name + "（野生）#n。";
			vo_20481_0.time = (int) (System.currentTimeMillis() / 1000L);
			GameObjectChar.send(new M20481_0(), vo_20481_0);
			boolean isfagong = petbeibao.petShuXing.get(0).rank > petbeibao.petShuXing.get(0).pet_mag_shape;
			GameUtil.dujineng(1, petbeibao.petShuXing.get(0).metal, petbeibao.petShuXing.get(0).skill, isfagong,
					petbeibao.id, chara, petbeibao);
		}
	}

	@Override
	public int cmd() {
		return 40966;
	}

	public int subtraction(int i) {
		Random r = new Random();
		return r.nextInt(i);
	}
}