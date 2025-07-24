package com.fengshen.server.process.pet;

import com.fengshen.server.data.write.pet.MSG_UPDATE_PETS;
import com.fengshen.server.domain.PetShuXing;
import com.fengshen.server.netty.BaseWrite;
import org.springframework.stereotype.Service;

import com.fengshen.server.data.GameReadTool;
import com.fengshen.server.data.vo.Vo_4163_0;
import com.fengshen.server.data.write.M4163_0;
import com.fengshen.server.domain.Chara;
import com.fengshen.server.domain.Petbeibao;
import com.fengshen.server.game.GameHandler;
import com.fengshen.server.game.GameObjectChar;
import com.fengshen.server.game.GameUtil;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * 设置宠物的参战、休息、掠阵状态
 * 
 *
 */
@Service
@Slf4j
public class CMD_SELECT_CURRENT_PET implements GameHandler {
	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		int id = GameReadTool.readInt(buff); // 宠物ID
		// pet_status：0休息，1战斗，2掠阵
		int pet_status = GameReadTool.readShort(buff);
		log.info("设置宠物的参战、休息、掠阵状态");
		GameObjectChar gameObjectChar = GameObjectChar.getGameObjectChar();
		Chara chara = gameObjectChar.chara;
		for(Petbeibao pet:chara.pets) {
			if(pet.id == id && pet_status>0) {
				if((pet.petShuXing.get(0).skill-chara.level)>15) {
					GameUtil.sendMeTips("你和宠物物等级相差15级，无法操作");
					return;
				}
				//把洛书属性加在宠物上
//				List<PetShuXing> petShuXingList =  pet.petShuXing;
//				log.info("设置参战的宠物的属性1：" +petShuXingList.size());
//				for (PetShuXing petShuXing : petShuXingList) {
//					log.info("设置参战的宠物的属性2：" +petShuXing.accurate);
//					log.info("设置参战宠物物攻加1000：");
//					petShuXing.accurate = petShuXing.accurate + 1000;
//				}
//            List<PetShuXing> petShuXing = petbeibao.petShuXing;
//				List<Petbeibao> list = new ArrayList<>();
//				list.add(pet);
//				GameObjectChar.send((BaseWrite) new MSG_UPDATE_PETS(), list);
				//直接把人物的自动设置到宠物上
				pet.autofight_select = chara.autofight_select;
				if(pet.autofight_select == 1) {
					//判断是否自动过技能
					if(pet.autofight_skillaction == 0) {
						pet.autofight_skillaction = 1;
						//为0
						pet.autofight_skillno = 0;
					}
				}
				break;
			}
		}
		if (pet_status == 0) {
			if (chara.chongwuchanzhanId == id)
				chara.chongwuchanzhanId = 0;
			if (chara.chongwuluezhenId == id)
				chara.chongwuluezhenId = 0;
		} else if (pet_status == 1) {
			if (chara.chongwuluezhenId == id)
				chara.chongwuluezhenId = 0;
			chara.chongwuchanzhanId = id;
		} else if (pet_status == 2) {
			chara.chongwuluezhenId = id;
		}
		Vo_4163_0 vo_4163_0 = new Vo_4163_0();
		vo_4163_0.id = id;
		vo_4163_0.b = pet_status;
		GameObjectChar.send(new M4163_0(), vo_4163_0);
	}

	@Override
	public int cmd() {
		return 4162;
	}
}
