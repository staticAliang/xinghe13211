package com.fengshen.server.process.pet;

import org.springframework.stereotype.Service;

import com.fengshen.server.data.GameReadTool;
import com.fengshen.server.game.GameHandler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

/**
 * 设置宠物跟随
 * 
 *
 */
@Service
@Slf4j
public class CMD_SELECT_VISIBLE_PET implements GameHandler{

	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		int id = GameReadTool.readInt(buff);
		//1 表示正常宠物列表，2 表示鬼宠列表
		int type = GameReadTool.readByte(buff);
		log.info("设置宠物跟随,id={},type",id,type);
//		Chara chara = GameObjectChar.getGameObjectChar().chara;
//		Petbeibao currSetPet = null;
//		for(Petbeibao pet:chara.pets) {
//			if(pet.id == id) {
//				currSetPet = pet;
//			}
//		}
//		if(currSetPet != null) {
//			
//		}
	}

	@Override
	public int cmd() {
		return 0x1084;
	}

}
