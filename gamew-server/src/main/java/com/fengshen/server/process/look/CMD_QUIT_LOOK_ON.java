package com.fengshen.server.process.look;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.fengshen.server.data.vo.fight.Vo_C_END_COMBAT;
import com.fengshen.server.data.vo.user.Vo_UPDATE_DYNAMIC;
import com.fengshen.server.data.write.look.MSG_LC_END_LOOKON;
import com.fengshen.server.data.write.look.MSG_LC_LOOKON_NUM;
import com.fengshen.server.data.write.look.MSG_LC_START_LOOKON;
import com.fengshen.server.data.write.user.MSG_UPDATE_DYNAMIC;
import com.fengshen.server.domain.Chara;
import com.fengshen.server.fight.FightContainer;
import com.fengshen.server.fight.FightManager;
import com.fengshen.server.game.GameCommonUtil;
import com.fengshen.server.game.GameHandler;
import com.fengshen.server.game.GameObjectChar;
import com.fengshen.server.game.GameUtil;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CMD_QUIT_LOOK_ON implements GameHandler {

	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		log.info("退出观战");
		GameObjectChar gameObjectChar = GameObjectChar.getGameObjectChar();
		Chara chara = gameObjectChar.chara;
		FightContainer fc = FightManager.getFightContainer(gameObjectChar.lookCharId);
		//如果是队长退出观战
		if(GameCommonUtil.isNotGameTeam(gameObjectChar.gameTeam,chara)) {
			if(chara.id == gameObjectChar.gameTeam.duiwu.get(0).id) {
				if(fc != null) {
					//全队都退出战斗
					for(Chara teamChara:gameObjectChar.gameTeam.duiwu) {
						GameObjectChar teamGame = fc.lookCharas.get(teamChara.id);
						if(teamGame != null) {
							teamGame.sendOne(new MSG_LC_START_LOOKON(), new Integer[] {1,1});
							teamGame.sendOne(new MSG_LC_END_LOOKON(), new Vo_C_END_COMBAT(0));
							teamGame.isLook = 0;
							teamGame.lookCharId = 0;
							//更新状态
							Map<String, Object> dataMap = new HashMap<>();
							dataMap.put("auto_fight", teamGame.chara.autofight_select);
							teamGame.sendOne(new MSG_UPDATE_DYNAMIC(), new Vo_UPDATE_DYNAMIC(teamGame.chara.id, dataMap));
							GameCommonUtil.sendTips("退出观战...",teamGame);
						}
						//移除观战
						fc.lookCharas.remove(teamChara.id);
					}
				}
				GameCommonUtil.setCharaTitleFlag(chara);
				return;
			}else {
				GameUtil.sendMeTips("只有队长才可退出观战");
				return;
			}
		}
		GameObjectChar.send(new MSG_LC_START_LOOKON(), new Integer[] {1,1});
		GameObjectChar.send(new MSG_LC_END_LOOKON(), new Vo_C_END_COMBAT(1));
		//自己单独退出战斗
		gameObjectChar.isLook = 0;
		gameObjectChar.lookCharId = 0;
		//更新状态
		Map<String, Object> dataMap = new HashMap<>();
		dataMap.put("auto_fight", chara.autofight_select);
		GameObjectChar.send(new MSG_UPDATE_DYNAMIC(), new Vo_UPDATE_DYNAMIC(gameObjectChar.chara.id, dataMap));
		if(fc != null) {
			fc.lookCharas.remove(chara.id);
			//通知观战人数
			for(Map.Entry<Integer, GameObjectChar> lookGame:fc.lookCharas.entrySet()) {
				//观战人数
				lookGame.getValue().sendOne(new MSG_LC_LOOKON_NUM(), fc.lookCharas.size());
			}
		}
		GameCommonUtil.setCharaTitleFlag(chara);
		GameUtil.sendMeTips("退出观战...");
	}

	@Override
	public int cmd() {
		return 0x0092;
	}

}
