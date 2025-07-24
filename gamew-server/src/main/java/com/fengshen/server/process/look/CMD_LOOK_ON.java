package com.fengshen.server.process.look;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.fengshen.server.data.GameReadTool;
import com.fengshen.server.data.write.look.MSG_LC_LOOKON_NUM;
import com.fengshen.server.domain.Chara;
import com.fengshen.server.fight.FightContainer;
import com.fengshen.server.fight.FightManager;
import com.fengshen.server.game.GameCommonUtil;
import com.fengshen.server.game.GameHandler;
import com.fengshen.server.game.GameObjectChar;
import com.fengshen.server.game.GameObjectCharMng;
import com.fengshen.server.game.GameUtil;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CMD_LOOK_ON implements GameHandler {

	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		int id = GameReadTool.readInt(buff);
		int is_bad_resolution = GameReadTool.readByte(buff);
		log.info("点击观战,id={},is_bad_resolution={},",id,is_bad_resolution);
		GameObjectChar thisGameObjectChar = GameObjectChar.getGameObjectChar();
		Chara chara = thisGameObjectChar.chara;
		if("试道场".equals(chara.mapName)) {
			GameUtil.sendMeTips("试道场内不允许观战.");
			log.error("试道场内不允许观战:{}",chara.name);
			return;
		}
		FightContainer fc = FightManager.getFightContainer(id);
		if(fc != null) {
			GameUtil.sendMeTips("正在准备观战中...");
			List<GameObjectChar> charas = new ArrayList<>();
			if(GameCommonUtil.isNotGameTeam(thisGameObjectChar.gameTeam,chara)) {
				//有队伍的状态
				for(Chara teamChara:thisGameObjectChar.gameTeam.duiwu) {
					GameObjectChar teamGame = GameObjectCharMng.getGameObjectChar(teamChara.id);
					teamGame.lookCharId = id;
					if(teamGame != null) {
						charas.add(teamGame);
					}
				}
			}else {
				thisGameObjectChar.lookCharId = id;
				charas.add(thisGameObjectChar);
			}
			GameCommonUtil.lookFight(charas, fc, id);
			GameCommonUtil.setCharaTitleFlag(chara);
			//通知观战人数
			for(Map.Entry<Integer, GameObjectChar> lookGame:fc.lookCharas.entrySet()) {
				//观战人数
				lookGame.getValue().sendOne(new MSG_LC_LOOKON_NUM(), fc.lookCharas.size());
			}
		}
	}

	@Override
	public int cmd() {
		return 0x1090;
	}

}
