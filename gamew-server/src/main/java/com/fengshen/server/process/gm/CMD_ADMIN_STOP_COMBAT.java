package com.fengshen.server.process.gm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.fengshen.server.data.GameReadTool;
import com.fengshen.server.data.vo.fight.Vo_C_END_COMBAT;
import com.fengshen.server.data.vo.user.Vo_UPDATE_DYNAMIC;
import com.fengshen.server.data.write.look.MSG_LC_END_LOOKON;
import com.fengshen.server.data.write.user.MSG_UPDATE_DYNAMIC;
import com.fengshen.server.domain.Chara;
import com.fengshen.server.fight.FightContainer;
import com.fengshen.server.fight.FightManager;
import com.fengshen.server.fight.FightObject;
import com.fengshen.server.fight.FightTeam;
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
public class CMD_ADMIN_STOP_COMBAT implements GameHandler {

	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		String gid = GameReadTool.readString(buff);
		log.info("gm终止战斗,{}",gid);
		GameObjectChar toGameObjectChar = GameObjectCharMng.getGameObjectCharByUUid(gid);
		if(toGameObjectChar != null) {
			//如果当前对象正在强制PK,或者被PK中
			FightContainer fightContainer = FightManager.getFightContainer(toGameObjectChar.chara.id);
			List<GameObjectChar> charas = new ArrayList<>();
			if(fightContainer != null) {
				FightManager.listFight.remove(fightContainer);
				List<FightTeam> fightTeams = fightContainer.teamList;
				for(FightTeam team:fightTeams) {
					List<FightObject> fightObjectList = team.fightObjectList;
					for(FightObject fightObject:fightObjectList) {
						if(fightObject.type == 1) {
							GameObjectChar obj = GameObjectCharMng.getGameObjectChar(fightObject.id);
							if(obj != null) {
								charas.add(obj);
							}
						}
					}
				}
				//让观战人员也退出
				for(Map.Entry<Integer, GameObjectChar> lookGame:fightContainer.lookCharas.entrySet()) {
					GameObjectChar look = lookGame.getValue();
					//观战人数
					look.sendOne(new MSG_LC_END_LOOKON(), new Vo_C_END_COMBAT(1));
					look.isLook = 0;
					look.lookCharId = 0;
					GameCommonUtil.setCharaTitleFlag(look.chara);
					Map<String, Object> dataMap = new HashMap<>();
					dataMap.put("auto_fight", look.chara.autofight_select);
					look.sendOne(new MSG_UPDATE_DYNAMIC(), new Vo_UPDATE_DYNAMIC(look.chara.id, dataMap));
				}
			}else {
				if(GameCommonUtil.isNotGameTeam(toGameObjectChar.gameTeam,toGameObjectChar.chara)) {
					for(Chara team:toGameObjectChar.gameTeam.duiwu) {
						//如果队伍里面有他
						charas.add(GameObjectCharMng.getGameObjectChar(team.id));
					}
				}else {
					charas.add(toGameObjectChar);
				}
			}
			for(GameObjectChar game:charas) {
				GameCommonUtil.sendTips("#RGM#n强制结束了战斗", game);
			}
			GameCommonUtil.endCombat(charas,fightContainer, null);
			GameUtil.sendMeTips("操作成功");
		}
	} 

	@Override
	public int cmd() {
		return 0xD074;
	}

}
