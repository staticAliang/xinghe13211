package com.fengshen.server.process.pet;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.fengshen.db.domain.Pet;
import com.fengshen.server.data.GameReadTool;
import com.fengshen.server.data.game.BasicAttributesUtils;
import com.fengshen.server.data.vo.Vo_8165_0;
import com.fengshen.server.data.write.M8165_0;
import com.fengshen.server.data.write.pet.MSG_UPDATE_PETS;
import com.fengshen.server.domain.Chara;
import com.fengshen.server.domain.PetShuXing;
import com.fengshen.server.domain.Petbeibao;
import com.fengshen.server.game.GameData;
import com.fengshen.server.game.GameHandler;
import com.fengshen.server.game.GameObjectChar;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

/**
 * 重新洗宝宝
 * 
 *
 */
@Service
@Slf4j
public class CMD_SET_SHAPE_TEMP implements GameHandler {
	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		int no = GameReadTool.readByte(buff);
		int is_set = GameReadTool.readByte(buff);
		Chara chara = GameObjectChar.getGameObjectChar().chara;
		log.info("重新洗练宝宝，no={}，is_set={}",no,is_set);
		if (is_set == 1) {
			for (int i = 0; i < chara.pets.size(); ++i) {
				Petbeibao petbeibao = chara.pets.get(i);
				if (petbeibao.no == no) {
					Pet pet = GameData.that.basePetService.findOneByName(petbeibao.petShuXing.get(0).str);
					PetShuXing shuXing = petbeibao.petShuXing.get(0);
					shuXing.pet_mana_shape += shuXing.pet_mana_shape_temp;
					shuXing.pet_speed_shape += shuXing.pet_speed_shape_temp;
					shuXing.pet_phy_shape += shuXing.pet_phy_shape_temp;
					shuXing.pet_mag_shape += shuXing.pet_mag_shape_temp;
					shuXing.rank += shuXing.evolve_degree;
					shuXing.mana_effect += shuXing.pet_mana_shape_temp;
					shuXing.attack_effect += shuXing.pet_speed_shape_temp;
					shuXing.mag_effect += shuXing.pet_mag_shape_temp;
					shuXing.phy_absorb += shuXing.evolve_degree;
					shuXing.phy_effect += shuXing.pet_phy_shape_temp;
					shuXing.pet_mana_shape_temp = 0;
					shuXing.pet_speed_shape_temp = 0;
					shuXing.pet_phy_shape_temp = 0;
					shuXing.pet_mag_shape_temp = 0;
					shuXing.evolve_degree = 0;
					List<Petbeibao> list = new ArrayList<>();
					BasicAttributesUtils.petshuxing(petbeibao.petShuXing.get(0), petbeibao);
					shuXing.max_life = shuXing.def;
					shuXing.max_mana = shuXing.dex;
					list.add(petbeibao);
					GameObjectChar.send(new MSG_UPDATE_PETS(), list);

					Vo_8165_0 vo_8165_0 = new Vo_8165_0();
					vo_8165_0.msg = "你的#Y" + pet.getName() + "#n经过洗炼，基础成长已重新生成。";
					vo_8165_0.active = 0;
					GameObjectChar.send(new M8165_0(), vo_8165_0);
				}
			}
		}
	}

	@Override
	public int cmd() {
		return 32772;
	}
}