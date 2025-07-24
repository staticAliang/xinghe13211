package com.fengshen.server.process.pet;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

import com.fengshen.db.domain.Pet;
import com.fengshen.server.data.GameReadTool;
import com.fengshen.server.data.game.BasicAttributesUtils;
import com.fengshen.server.data.game.PetAttributesUtils;
import com.fengshen.server.data.vo.Vo_MSG_PET_UPGRADE_SUCC;
import com.fengshen.server.data.write.M_MSG_PET_UPGRADE_SUCC;
import com.fengshen.server.data.write.pet.MSG_UPDATE_PETS;
import com.fengshen.server.domain.Chara;
import com.fengshen.server.domain.Goods;
import com.fengshen.server.domain.Petbeibao;
import com.fengshen.server.game.GameData;
import com.fengshen.server.game.GameHandler;
import com.fengshen.server.game.GameObjectChar;
import com.fengshen.server.game.GameObjectCharMng;
import com.fengshen.server.game.GameUtil;
import com.fengshen.server.game.PetFlyMgr;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@org.springframework.stereotype.Service
public class CMD_SUBMIT_PET_UPGRADE_ITEM implements GameHandler {

    @Override
    public void process(ChannelHandlerContext paramChannelHandlerContext, ByteBuf paramByteBuf) {
        String data = GameReadTool.readString(paramByteBuf);
        String [] posStr =  data.split(",");
        Chara chara = GameObjectChar.getGameObjectChar().chara;

        List<Goods> goodsList = new ArrayList<>();
        for (int i = 0 ;i<  posStr.length; i++){
            for (Goods goods: chara.backpack) {
                if (goods.pos == Integer.valueOf(posStr[i])){
                    goodsList.add(goods);
                }
            }
        }

        if (!PetFlyMgr.checkEnough(chara, goodsList)) return;
        String []  items = PetFlyMgr.itemStr.split(",");
        for (int i = 0 ;i < items.length; i++) {
            String [] tempItem = items[i].split("#");
            for (Goods goods: goodsList){
                if (items[i].contains(goods.goodsInfo.str) ) {
                    GameUtil.removemunber(chara, goods, Integer.valueOf(tempItem[1]));
                }
            }
        }
        Petbeibao pet = chara.getPetByID(chara.flyPetID);
        if (null == pet) {
            GameUtil.sendTips("宠物飞升 没有找到飞升的宠物");
            return;
        }
        //修复属性错乱 ----开始
        Vo_MSG_PET_UPGRADE_SUCC vo = new Vo_MSG_PET_UPGRADE_SUCC();
		vo.id = pet.id;
		//气血
		vo.pet_life_shape[0] = pet.petShuXing.get(0).pet_mana_shape;
		//法力
		vo.pet_mana_shape[0] = pet.petShuXing.get(0).pet_speed_shape;
		//物攻
		vo.pet_mag_shape[0] = pet.petShuXing.get(0).pet_mag_shape;
		//法攻
		vo.pet_phy_shape[0] = pet.petShuXing.get(0).rank;
		//速度
		vo.pet_speed_shape[0] = pet.petShuXing.get(0).pet_phy_shape;

		final Pet petInfoByName = GameData.that.basePetService.findOneByName(pet.petShuXing.get(0).suit_polar);
		final int[] a49 = new int[5];
		a49[0] = petInfoByName.getLife();
		a49[1] = petInfoByName.getMana();
		a49[2] = petInfoByName.getPhyAttack();
		a49[3] = petInfoByName.getMagAttack();
		a49[4] = petInfoByName.getSpeed();
		
		 //0气血，1法力，2物攻，3法攻，4速度
		final int[] result = PetAttributesUtils.flyPet(pet.petShuXing.get(0).penetrate, a49);
		//气血
		pet.petShuXing.get(0).pet_mana_shape += result[0];
		//法力
		pet.petShuXing.get(0).pet_speed_shape += result[1];
		//物攻
		pet.petShuXing.get(0).pet_mag_shape += result[2];
		//法攻
		pet.petShuXing.get(0).rank += result[3];
		//速度
		pet.petShuXing.get(0).pet_phy_shape += result[4];
		
		
		System.out.println(Arrays.toString(result));
		
		//气血
		vo.pet_life_shape[1] = pet.petShuXing.get(0).pet_mana_shape;
		//法力
		vo.pet_mana_shape[1] = pet.petShuXing.get(0).pet_speed_shape;
		//物攻
		vo.pet_mag_shape[1] = pet.petShuXing.get(0).pet_mag_shape;
		//法攻
		vo.pet_phy_shape[1] = pet.petShuXing.get(0).rank;
		//速度
		vo.pet_speed_shape[1] = pet.petShuXing.get(0).pet_phy_shape;
        
		//修复属性错乱 ------结束
        

        pet.petShuXing.get(0).upgrade_level = PetFlyMgr.UPGRAD_TYPE.UPGRAD_TYPE_FINSH.ordinal();
        chara.flyPetID = 0;
        // 设置飞升成功的标志
        pet.petShuXing.get(0).limit_use_time = 1;

        GameObjectCharMng.getGameObjectChar(chara.id).sendOne(new M_MSG_PET_UPGRADE_SUCC(), vo);
        GameUtil.sendTips("宠物飞升 恭喜你飞升成功");

        BasicAttributesUtils.petshuxing(pet.petShuXing.get(0), pet);

        List<Petbeibao> list = new ArrayList<>();
        list.add(pet);
        GameObjectChar.send(new MSG_UPDATE_PETS(), list);
        PetFlyMgr.removeFlyTask(chara);
        //删除飞升task
        chara.taskMap.remove("宠物飞升");
        
    }



    @Override
    public int cmd() {
        return 0xB0FB;
    }
}
