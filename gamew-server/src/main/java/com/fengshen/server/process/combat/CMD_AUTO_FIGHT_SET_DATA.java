package com.fengshen.server.process.combat;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fengshen.server.data.GameReadTool;
import com.fengshen.server.domain.Chara;
import com.fengshen.server.domain.Petbeibao;
import com.fengshen.server.fight.FightManager;
import com.fengshen.server.fight.FightObject;
import com.fengshen.server.game.GameCommonUtil;
import com.fengshen.server.game.GameHandler;
import com.fengshen.server.game.GameObjectChar;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

/**
 * 设置角色和宠物的自动技能
 * 
 *
 */
@Service
@Slf4j
public class CMD_AUTO_FIGHT_SET_DATA implements GameHandler {
	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		int id = GameReadTool.readInt(buff);
		int auto_select = GameReadTool.readByte(buff);
		int multi_index = GameReadTool.readByte(buff);
		int action = GameReadTool.readByte(buff);
		int para = GameReadTool.readInt(buff);
		int multi_count = GameReadTool.readShort(buff);
		log.info("设置角色、宠物自动技能,id={},auto_select={},multi_index={},action={},para={},multi_count={}",
				id,auto_select,multi_index,action,para,multi_count);
		GameObjectChar gameObjectChar = GameObjectChar.getGameObjectChar();
		Chara chara = gameObjectChar.chara;
		if (chara.id == id) {
			//设置人物自动技能
			chara.autofight_supplement = auto_select;
			chara.autofight_skillaction = action;
			chara.autofight_skillno = para;
		} else {
			List<Petbeibao> pets = chara.pets;
			for (int j = 0; j < pets.size(); ++j) {
				Petbeibao petbeibao = pets.get(j);
				if (petbeibao.id == id) {
					petbeibao.autofight_skillaction = action;
					petbeibao.autofight_skillno = para;
					petbeibao.autofight_supplement = auto_select;
					break;
				}
			}
		}
		FightObject fightObject = FightManager.getFightObject(id);
		if(fightObject == null) {
			return;
		}
		fightObject.autofight_skillaction = action;
		fightObject.autofight_supplement = auto_select;
		fightObject.autofight_skillno = para;
		//加载战斗信息
		FightManager.changeAutoFightSkill(FightManager.getFightContainer(), fightObject, action, para);
		GameCommonUtil.fightCmdInfo(gameObjectChar);
	}

	@Override
	public int cmd() {
		return 32984;
	}
}
