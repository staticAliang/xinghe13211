package com.fengshen.server.process.pet;

import org.springframework.stereotype.Service;

import com.fengshen.db.domain.StoreInfo;
import com.fengshen.server.data.GameReadTool;
import com.fengshen.server.data.vo.Vo_40964_0;
import com.fengshen.server.data.vo.user.Vo_UPDATE_APPEARANCE;
import com.fengshen.server.data.write.M40964_0;
import com.fengshen.server.data.write.pet.MSG_UPDATE_PETS;
import com.fengshen.server.data.write.user.MSG_UPDATE_APPEARANCE;
import com.fengshen.server.domain.Chara;
import com.fengshen.server.domain.Petbeibao;
import com.fengshen.server.game.GameCommonUtil;
import com.fengshen.server.game.GameData;
import com.fengshen.server.game.GameHandler;
import com.fengshen.server.game.GameObjectChar;
import com.fengshen.server.game.GameUtil;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

/**
 * 取出彩凤之魂
 * @author weilian
 *
 */
@Service
public class CMD_PET_DELETE_SOUL implements GameHandler{

	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		int no = GameReadTool.readByte(buff);
		GameObjectChar gameObjectChar = GameObjectChar.getGameObjectChar();
		Chara chara = gameObjectChar.chara;
		for(Petbeibao pet:chara.pets) {
			if(pet.no == no) {
				//只有注入了彩凤之魂的时候才能取出来
				if(pet.petShuXing.get(0).zhuruCaifeng == 1) {
					pet.petShuXing.get(0).zhuruCaifeng = 0;
					GameObjectChar.send(new MSG_UPDATE_PETS(), chara.pets);
					StoreInfo storeInfo = GameData.that.baseStoreInfoService.findOneByName("彩凤之魂");
					GameUtil.huodedaoju(gameObjectChar, storeInfo, 1);
					final Vo_40964_0 vo_40964_21 = new Vo_40964_0();
					vo_40964_21.type = 1;
					vo_40964_21.name = "彩凤之魂";
					vo_40964_21.param = "";
					vo_40964_21.rightNow = 0;
					GameObjectChar.send(new M40964_0(), vo_40964_21);
					//设置成默认坐骑状态
					chara.zuoqiwaiguan = pet.petShuXing.get(0).type + 1000;
					if(chara.upgrade_state != 0) {
						chara.zuowaiguan = GameCommonUtil.getYuanYingZuoqiWaiguan(chara, chara.zuoqiwaiguan);
					}else {
						chara.zuowaiguan = CMD_SELECT_CURRENT_MOUNT.typeMounts(pet.petShuXing.get(0).type + 1000, chara.polar,
								chara.sex - 1);
					}
					if(chara.zuoqiId != 0) {
						//更新人物外观数据
						final Vo_UPDATE_APPEARANCE vo_61661_0 = GameUtil.a61661(chara);
						gameObjectChar.gameMap.send(new MSG_UPDATE_APPEARANCE(), vo_61661_0);
					}
				}
				break;
			}
		}
	}

	@Override
	public int cmd() {
		return 0xB1A2;
	}

}
