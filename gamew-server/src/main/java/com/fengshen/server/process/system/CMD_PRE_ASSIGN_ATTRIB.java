package com.fengshen.server.process.system;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import com.fengshen.server.data.GameReadTool;
import com.fengshen.server.data.game.BasicAttributesUtils;
import com.fengshen.server.data.game.PetAttributesUtils;
import com.fengshen.server.data.vo.Vo_14337_0;
import com.fengshen.server.data.write.M14337_0;
import com.fengshen.server.domain.Chara;
import com.fengshen.server.domain.PetShuXing;
import com.fengshen.server.domain.Petbeibao;
import com.fengshen.server.game.GameHandler;
import com.fengshen.server.game.GameObjectChar;
import com.fengshen.server.game.GameUtil;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

// 这里在用户点击加点时会触发
@Service
@Slf4j
public class CMD_PRE_ASSIGN_ATTRIB implements GameHandler {
	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		int id = GameReadTool.readInt(buff);
		// 1人物属性点, 2人物相性点
		int type = GameReadTool.readByte(buff);
		int para1 = GameReadTool.readShort(buff);
		int para2 = GameReadTool.readShort(buff);
		int para3 = GameReadTool.readShort(buff);
		int para4 = GameReadTool.readShort(buff);
		int para5 = GameReadTool.readShort(buff);
		Chara chara = GameObjectChar.getGameObjectChar().chara;
		int level = chara.level;
		if (para1 > 3000) {
			para1 -= 65536;
		}
		if (para2 > 3000) {
			para2 -= 65536;
		}
		if (para3 > 3000) {
			para3 -= 65536;
		}
		if (para4 > 3000) {
			para4 -= 65536;
		}
		if (para5 > 3000) {
			para5 -= 65536;
		}
		if (0 == id) {
			Vo_14337_0 vo_14337_0 = new Vo_14337_0();
			if (type == 1) {
				int[] ints = BasicAttributesUtils.changeCalculationAttributes(level, para1, para2, para3, para4);
				vo_14337_0.id = 0;
				vo_14337_0.type = 1;
				vo_14337_0.life_plus = ints[0];
				vo_14337_0.max_life_plus = ints[0];
				vo_14337_0.mana_plus = ints[1];
				vo_14337_0.max_mana_plus = ints[1];
				vo_14337_0.phy_power_plus = ints[2];
				vo_14337_0.mag_power_plus = ints[3];
				vo_14337_0.speed_plus = ints[4];
				vo_14337_0.def_plus = ints[5];
				vo_14337_0.free = 0;
			} else if (type == 2) {
				int[] ints = BasicAttributesUtils.changeRelAttributes(level, chara.life, chara.mag_power,
						chara.phy_power, chara.speed, para1, para2, para3, para4, para5);
				vo_14337_0.id = 0;
				vo_14337_0.type = 1;
				vo_14337_0.life_plus = ints[0];
				vo_14337_0.max_life_plus = ints[0];
				vo_14337_0.mana_plus = ints[1];
				vo_14337_0.max_mana_plus = ints[1];
				vo_14337_0.phy_power_plus = ints[2];
				vo_14337_0.mag_power_plus = ints[3];
				vo_14337_0.speed_plus = ints[4];
				vo_14337_0.def_plus = ints[5];
				vo_14337_0.free = 0;
			}
			GameObjectChar.send(new M14337_0(), vo_14337_0);
		} else {
			for (int i = 0; i < chara.pets.size(); ++i) {
				Petbeibao petbeibao = chara.pets.get(i);
				if (petbeibao.id == id) {
					PetShuXing petShuXing = petbeibao.petShuXing.get(0);
					boolean fagong = petShuXing.rank > petShuXing.pet_mag_shape;
					int[] ints2 = PetAttributesUtils.petAttributes(fagong, petShuXing.skill, petShuXing.life + para1,
							petShuXing.mag_power + para2, petShuXing.phy_power + para3, petShuXing.speed + para4,
							petShuXing.pet_mana_shape, petShuXing.pet_speed_shape, petShuXing.pet_phy_shape,
							petShuXing.pet_mag_shape, petShuXing.rank);
					int[] ints3 = PetAttributesUtils.petAttributes(fagong, petShuXing.skill, petShuXing.life,
							petShuXing.mag_power, petShuXing.phy_power, petShuXing.speed, petShuXing.pet_mana_shape,
							petShuXing.pet_speed_shape, petShuXing.pet_phy_shape, petShuXing.pet_mag_shape,
							petShuXing.rank);
					Vo_14337_0 vo_14337_2 = new Vo_14337_0();
					vo_14337_2.id = id;
					vo_14337_2.type = 1;
					vo_14337_2.life_plus = ints2[0] - ints3[0];
					vo_14337_2.max_life_plus = ints2[0] - ints3[0];
					vo_14337_2.mana_plus = ints2[1] - ints3[1];
					vo_14337_2.max_mana_plus = ints2[1] - ints3[1];
					vo_14337_2.phy_power_plus = ints2[2] - ints3[2];
					vo_14337_2.mag_power_plus = ints2[3] - ints3[3];
					vo_14337_2.speed_plus = ints2[4] - ints3[4];
					vo_14337_2.def_plus = ints2[5] - ints3[5];
					vo_14337_2.free = 0;
					GameObjectChar.send(new M14337_0(), vo_14337_2);
				}
			}
		}
		// 点击加点
		if (chara.upgrade_state != 0) {
			GameUtil.sendUpdate(chara, "yuanyingAddPoint");
		} else {
			GameUtil.sendUpdate(chara);
		}
	}

	@Override
	public int cmd() {
		return 14338;
	}
}
