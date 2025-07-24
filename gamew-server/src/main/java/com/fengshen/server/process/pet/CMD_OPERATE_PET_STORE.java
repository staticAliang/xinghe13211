package com.fengshen.server.process.pet;

import java.util.Date;
import java.util.Iterator;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.fengshen.db.domain.CharaPet;
import com.fengshen.server.data.GameReadTool;
import com.fengshen.server.data.vo.Vo_12023_0;
import com.fengshen.server.data.vo.Vo_12269_0;
import com.fengshen.server.data.vo.Vo_40964_0;
import com.fengshen.server.data.vo.Vo_61677_0;
import com.fengshen.server.data.vo.pet.Vo_PET_STORE;
import com.fengshen.server.data.write.M12023_0;
import com.fengshen.server.data.write.M12269_0;
import com.fengshen.server.data.write.M40964_0;
import com.fengshen.server.data.write.pet.MSG_UPDATE_PETS;
import com.fengshen.server.data.write.store.MSG_PET_STORE;
import com.fengshen.server.data.write.store.MSG_STORE_REMOVE;
import com.fengshen.server.domain.Chara;
import com.fengshen.server.domain.Petbeibao;
import com.fengshen.server.game.GameCommonUtil;
import com.fengshen.server.game.GameData;
import com.fengshen.server.game.GameHandler;
import com.fengshen.server.game.GameObjectChar;
import com.fengshen.server.game.GameUtil;
import com.google.common.collect.Lists;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CMD_OPERATE_PET_STORE implements GameHandler {

	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		int type = GameReadTool.readByte(buff);
		int pos = GameReadTool.readShort(buff);
		int id = GameReadTool.readInt(buff);
		GameObjectChar gameObjectChar = GameObjectChar.getGameObjectChar();
		Chara chara = gameObjectChar.chara;
		log.info("存取宠物：type={}，pos={}，id={}", type, pos, id);
		//存宠物
		if(type == 1) {
			//获取可用位置
			int petStoreAvaliablePos = GameCommonUtil.getPetStoreAvaliablePos(chara);
			if(petStoreAvaliablePos == -1) {
				GameUtil.sendMeTips("宠物仓库已满！");
				return;
			}
			Iterator<Petbeibao> iterator = chara.pets.iterator();
			while(iterator.hasNext()) {
				Petbeibao pet = iterator.next();
				if(pet.no == pos) {
					chara.petStores.add(new Vo_PET_STORE(petStoreAvaliablePos,pet));
					//删除这个宠物
					iterator.remove();
					//设置归属者
					Vo_12269_0 vo = new Vo_12269_0();
					vo.id = pet.id;
					vo.owner_id = 0;
					gameObjectChar.sendOne(new M12269_0(), vo);
					GameData.that.charaPetService.deleteByPrimaryKey(pet.id);
					GameUtil.sendMeTips("你已将#R"+pet.petShuXing.get(0).suit_polar+"#n已放入仓库。");
					break;
				}
			}
		}else if(type == 2) {//取宠物
			Iterator<Vo_PET_STORE> iterator = chara.petStores.iterator();
			while(iterator.hasNext()) {
				Vo_PET_STORE store = iterator.next();
				if(store.getPos().equals(pos)) {
					Petbeibao pet = store.getPetbeibao();
					pet.no = GameUtil.getNo(chara);
					Petbeibao.isAddPet(chara, pet.petShuXing.get(0).penetrate);
					//储存到数据库
					CharaPet charaPet = new CharaPet();
					charaPet.setAddTime(new Date());
					charaPet.setOwnerName(chara.name);
					charaPet.setPetName(pet.petShuXing.get(0).str);
					charaPet.setUuid(chara.uuid);
					charaPet.setCid(chara.id);
					charaPet.setPet(JSONObject.toJSONString(pet));
					GameData.that.charaPetService.insertSelective(charaPet);
					pet.id = charaPet.getId();
					chara.pets.add(pet);
					//删除这个宠物
					iterator.remove();
					//设置归属者
					Vo_12269_0 vo = new Vo_12269_0();
					vo.id = pet.id;
					vo.owner_id = chara.id;
					gameObjectChar.sendOne(new M12269_0(), vo);
					GameObjectChar.send(new MSG_UPDATE_PETS(), Lists.newArrayList(pet));
					//宠物天书
					for(Vo_12023_0 book:pet.tianshu) {
						book.id = pet.id;
					}
					GameObjectChar.send(new M12023_0(), pet.tianshu);
					//更新宠物技能
					boolean isfagong = pet.petShuXing.get(0).rank > pet.petShuXing.get(0).pet_mag_shape;
					GameUtil.dujineng(1, pet.petShuXing.get(0).metal, pet.petShuXing.get(0).skill, isfagong, pet.id,
							chara, pet);
					//动画
					Vo_40964_0 vo_40964_21 = new Vo_40964_0();
					vo_40964_21.type = 2;
					vo_40964_21.name = "";
					vo_40964_21.param = String.valueOf(pet.petShuXing.get(0).type);
					vo_40964_21.rightNow = 0;
					GameObjectChar.send(new M40964_0(), vo_40964_21);
					Vo_61677_0 storeVo = new Vo_61677_0();
					storeVo.pos = pos;
					storeVo.store_type = "pet_store";
					storeVo.isGoon = 2;
					storeVo.npcID = 0;
					//删除该商品
		        	GameObjectChar.send(new MSG_STORE_REMOVE(), storeVo);
					
					GameUtil.sendMeTips("你已将#R"+pet.petShuXing.get(0).suit_polar+"#n取出。");
					break;
				}
			}
		}
		//加载宠物仓库信息
		gameObjectChar.sendOne(new MSG_PET_STORE(), chara.petStores);
	}

	@Override
	public int cmd() {
		return 0x801a;
	}

}
