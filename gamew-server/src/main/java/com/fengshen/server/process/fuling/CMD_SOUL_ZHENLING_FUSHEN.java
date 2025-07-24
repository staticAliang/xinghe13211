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
import com.fengshen.server.util.GameConfig;
import com.google.common.collect.Lists;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

/**
 * a真灵附身
 * @author aaa
 *
 */
@Service
@Slf4j
public class CMD_SOUL_ZHENLING_FUSHEN implements GameHandler {

	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		
		int objType = GameReadTool.readByte(buff);
		String id = GameReadTool.readString(buff);
		int type = GameReadTool.readByte(buff);
		log.info("真灵附身，objType={},id={},type={}",objType,id,type);
		GameObjectChar gameObjectChar = GameObjectChar.getGameObjectChar();
		Chara chara = gameObjectChar.chara;
		if(chara.zhenlingLevel == 0) {
			GameUtil.sendMeTips("请先激活附灵阵");
		}
		if(objType == 1) {
			if(chara.level<GameConfig.config.getBaseConfig().getCharaFuLingLevel()) {
				GameUtil.sendMeTips("请升至#R"+GameConfig.config.getBaseConfig().getCharaFuLingLevel()+"#n级在附身！");
				return;
			}
			//人物附身
			chara.zhenlingType = type;
			//更新玩家信息
			GameUtil.sendUpdate(chara);
		}else if(objType == 2) {
			if(chara.level<GameConfig.config.getBaseConfig().getPetFuLingLevel()) {
				GameUtil.sendMeTips("请将宠物升至#R"+GameConfig.config.getBaseConfig().getCharaFuLingLevel()+"#n级在附身！");
				return;
			}
			//宠物
			for(Petbeibao pet:chara.pets) {
				if(pet.petShuXing.get(0).auto_fight.equals(id)) {
					if(pet.petShuXing.get(0).penetrate == 1 || 
							pet.petShuXing.get(0).penetrate == 5|| pet.petShuXing.get(0).penetrate == 6 
							|| pet.petShuXing.get(0).penetrate == 7 
							||pet.petShuXing.get(0).penetrate == 8) {
						GameUtil.sendMeTips("只有非野生阳间宠物才可附身");
						return;
					}
					pet.petShuXing.get(0).zhenlingType = type;
					if(type == 1) {
						pet.petShuXing.get(0).zhenlingLevel = chara.qinglongZhenlingLevel;
					}else if(type == 2) {
						pet.petShuXing.get(0).zhenlingLevel = chara.baihuhenlingLevel;
					}else if(type == 3) {
						pet.petShuXing.get(0).zhenlingLevel = chara.zhuqueZhenlingLevel;
					}else if(type == 4) {
						pet.petShuXing.get(0).zhenlingLevel = chara.xuanwuZhenlingLevel;
					}
					gameObjectChar.sendOne(new MSG_UPDATE_PETS(), Lists.newArrayList(pet));
					break;
				}
			}
		}
		//附灵阵信息
		gameObjectChar.sendOne(new MSG_SOUL_FULINGZHEN_DATA(), new Vo_SOUL_FULINGZHEN_DATA(chara,1));
		GameUtil.sendMeTips("成功附身");
	}

	@Override
	public int cmd() {
		return 0xD376;
	}

}
