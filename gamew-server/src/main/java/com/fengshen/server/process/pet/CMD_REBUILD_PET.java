package com.fengshen.server.process.pet;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.fengshen.db.domain.Pet;
import com.fengshen.server.data.GameReadTool;
import com.fengshen.server.data.game.BasicAttributesUtils;
import com.fengshen.server.data.game.PetAttributesUtils;
import com.fengshen.server.data.vo.ListVo_65527_0;
import com.fengshen.server.data.vo.Vo_8165_0;
import com.fengshen.server.data.write.M65527_0;
import com.fengshen.server.data.write.M8165_0;
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

@Service
@Slf4j
public class CMD_REBUILD_PET implements GameHandler{

	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		//宠物id
		long petId = GameReadTool.readInt(buff);
		//强化等级
		int rebLevel = GameReadTool.readShort(buff);
		//参数
		String para = GameReadTool.readString(buff);
		//使用类型  
		String useType = GameReadTool.readString(buff);
		log.info("重新强化宝宝， rebLevel={}",rebLevel);
		Chara chara = GameObjectChar.getGameObjectChar().chara;
		if ("phy".equals(para) || "mag".equals(para)) {
			for (int i = 0; i < chara.pets.size(); ++i) {
                final Petbeibao petbeibao = chara.pets.get(i);
                if (petbeibao.id == petId) {
                    final Pet pet = GameData.that.basePetService.findOneByName(petbeibao.petShuXing.get(0).str);
                    final int[] ints = PetAttributesUtils.upgradePet(true, pet.getMagAttack(), petbeibao.petShuXing.get(0).raw_name, petbeibao.petShuXing.get(0).life_add_temp);
                    //判断使用哪种类型的元宝
                    if (chara.goldCoin >= 216 && useType.equals("gold_coin")) {
                    	chara.goldCoin -= 216;
                    }
                    else { // 用银元宝
                        if (chara.silverCoin < 216) {
                        	//元宝不足
                        	GameUtil.sendMeTips("元宝不足");
                            return;
                        }
                        chara.silverCoin -= 216;
                    }
                    if (petbeibao.petShuXing.get(0).raw_name < ints[0]) {
                        final PetShuXing petShuXing2 = petbeibao.petShuXing.get(0);
                        petShuXing2.pet_life_shape_temp += ints[1];
                        final PetShuXing petShuXing3 = petbeibao.petShuXing.get(0);
                        petShuXing3.rank += ints[1];
                        petbeibao.petShuXing.get(0).life_add_temp = 0;
                        petbeibao.petShuXing.get(0).raw_name = ints[0];
                        final Vo_8165_0 vo_8165_3 = new Vo_8165_0();
                        vo_8165_3.msg = "恭喜强化成功！";
                        vo_8165_3.active = 0;
                        GameObjectChar.send(new M8165_0(), vo_8165_3);
                    }
                    else {
                        petbeibao.petShuXing.get(0).life_add_temp = ints[2];
                        final Vo_8165_0 vo_8165_3 = new Vo_8165_0();
                        vo_8165_3.msg = "成长完成度增加了！";
                        vo_8165_3.active = 0;
                        GameObjectChar.send(new M8165_0(), vo_8165_3);
                    }
                    List<Petbeibao> list3 = new ArrayList<>();
                    BasicAttributesUtils.petshuxing(petbeibao.petShuXing.get(0), petbeibao);
                    list3.add(petbeibao);
                    GameObjectChar.send(new MSG_UPDATE_PETS(), list3);
                    //扣除元宝
                    ListVo_65527_0 a65527 = GameUtil.a65527(chara);
                    GameObjectChar.send(new M65527_0(), a65527);
                }
            }
		}
		
		
	}

	@Override
	public int cmd() {
		//0xA01E
		return 40990;
	}

}
