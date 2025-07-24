package com.fengshen.server.process.fuling;

import org.springframework.stereotype.Service;

import com.fengshen.server.data.GameReadTool;
import com.fengshen.server.data.vo.fuling.Vo_SOUL_FULINGZHEN_DATA;
import com.fengshen.server.data.write.fuling.MSG_SOUL_FULINGZHEN_DATA;
import com.fengshen.server.data.write.pet.MSG_UPDATE_PETS;
import com.fengshen.server.domain.Chara;
import com.fengshen.server.domain.Petbeibao;
import com.fengshen.server.game.GameHandler;
import com.fengshen.server.game.GameObjectChar;
import com.fengshen.server.game.GameUtil;
import com.google.common.collect.Lists;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

/**
 * a取消真灵附身
 * @author aaa
 *
 */
@Service
@Slf4j
public class CMD_SOUL_CANCEL_ZHENLING_FUSHEN implements GameHandler {

	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		int objType = GameReadTool.readByte(buff);
		String id = GameReadTool.readString(buff);
		log.info("取消真灵附身，objType={},id={}",objType,id);
		GameObjectChar gameObjectChar = GameObjectChar.getGameObjectChar();
		Chara chara = gameObjectChar.chara;
		if(objType == 1) {
			//人物附身
			chara.zhenlingType = 0;
			//更新玩家信息
			GameUtil.sendUpdate(chara);
		}else if(objType == 2) {
			for(Petbeibao pet:chara.pets) {
				if(pet.petShuXing.get(0).auto_fight.equals(id)) {
					pet.petShuXing.get(0).zhenlingType = 0;
					gameObjectChar.sendOne(new MSG_UPDATE_PETS(), Lists.newArrayList(pet));
					break;
				}
			}
		}
		//附灵阵信息
		gameObjectChar.sendOne(new MSG_SOUL_FULINGZHEN_DATA(), new Vo_SOUL_FULINGZHEN_DATA(chara,1));
		GameUtil.sendMeTips("已取消真灵附身");
	}

	@Override
	public int cmd() {
		return 0xD378;
	}

}
