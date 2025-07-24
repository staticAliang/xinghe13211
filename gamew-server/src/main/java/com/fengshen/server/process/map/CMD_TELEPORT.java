package com.fengshen.server.process.map;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.fengshen.core.util.ExecutorsUtils;
import com.fengshen.db.domain.Party;
import com.fengshen.server.data.GameReadTool;
import com.fengshen.server.data.vo.Vo_45056_0;
import com.fengshen.server.data.vo.Vo_61553_0;
import com.fengshen.server.data.write.M45056_0;
import com.fengshen.server.data.write.task.MSG_STOP_AUTO_WALK;
import com.fengshen.server.domain.Chara;
import com.fengshen.server.game.GameCommonUtil;
import com.fengshen.server.game.GameCore;
import com.fengshen.server.game.GameData;
import com.fengshen.server.game.GameHandler;
import com.fengshen.server.game.GameLine;
import com.fengshen.server.game.GameMap;
import com.fengshen.server.game.GameObjectChar;
import com.fengshen.server.game.GameObjectCharMng;
import com.fengshen.server.game.GamePartyUtil;
import com.fengshen.server.game.GameUtil;
import com.fengshen.server.util.GameActiveUtil;
import com.qiniu.util.StringUtils;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

/**
 * 传送
 * 
 *
 */
@Service
@Slf4j
public class CMD_TELEPORT implements GameHandler {
	@Override
	public void process(final ChannelHandlerContext ctx, final ByteBuf buff) {
		int mapid = GameReadTool.readInt(buff);
		int x = GameReadTool.readInt(buff);
		int y = GameReadTool.readInt(buff);
		GameReadTool.readByte(buff);
		log.info("传送{}",mapid);
		GameObjectChar gameObjectChar = GameObjectChar.getGameObjectChar();
		Chara chara = gameObjectChar.chara;
		chara.x = x;
		chara.y = y;
		// 判断是否在坐牢.
		if (chara.taskMap.get("坐牢") != null) {
			GameUtil.sendMeTips("正在坐牢，不允许操作");
			return;
		}
		if(gameObjectChar.privilege == 0 && chara.taskMap.get("萝卜桃子大收集") != null) {
			GameCommonUtil.sendTips("领取了萝卜桃子大收集任务，不允许快捷切换地图");
			return;
		}
		//如果当前角色在试道场不允许点击地图传送
		if(chara.mapid == 38004 && gameObjectChar.privilege == 0) {
			GameUtil.sendMeTips("该地图不允许地图传送，请到试道传送人处离开试道场内！");
			for (Map.Entry<String, Vo_61553_0> task : chara.taskMap.entrySet()) {
				Vo_61553_0 v = task.getValue();
				GameObjectChar.send(new MSG_STOP_AUTO_WALK(), v.task_type);
			}
			return;
		}
		if (mapid == 38004) {
			for (Map.Entry<String, Vo_61553_0> task : chara.taskMap.entrySet()) {
				Vo_61553_0 v = task.getValue();
				GameObjectChar.send(new MSG_STOP_AUTO_WALK(), v.task_type);
			}
			return;
		}
		// 帮派地图需创建动态地图
		if (mapid == 26000) {
			// 根据帮派名字创建动态地图
			String partyName = chara.getPartyName();
			if (!StringUtils.isNullOrEmpty(partyName)) {
				// 查询该帮派是否被封停
				Party party = GameCore.partyMap.get(chara.getPartyName());
				if (party == null) {
					// 去数据库查询最新的
					party = GameData.that.partyService.findByPartyName(partyName);
					GameCore.partyMap.put(party.getPartyName(), party);
				}
				if (party.getState() != 0) {
					GameCommonUtil.dialogOk("对不起该帮派因违反规定，被封停。");
					return;
				}
				// 判断是否有队伍
				if (GameCommonUtil.isNotGameTeam(gameObjectChar.gameTeam)) {
					for (Chara gc : gameObjectChar.gameTeam.duiwu) {
						if (!partyName.equals(gc.getPartyName())) {
							GameCommonUtil.dialogOk("#Y" + gc.name + "#n不是本帮人员无法传送。");
							return;
						}
					}
				} else {
					// 单人
					if (StringUtils.isNullOrEmpty(partyName)) {
						GameCommonUtil.dialogOk("你不是本帮人员，无法传送。");
						return;
					}
				}
				GameLine.enterPartyMap(mapid, partyName).join(gameObjectChar);
			}
		} else {
			GameMap gameMap = GameLine.getGameMap(chara.line, mapid);
			// 将玩家传送到指定的地图位置. 组队的状态下.必须是队长才可以带动整个队伍
			if (GameCommonUtil.isNotGameTeam(gameObjectChar.gameTeam)
					&& gameObjectChar.gameTeam.duiwu.get(0).id == chara.id) {
				for (Chara gameChara : gameObjectChar.gameTeam.duiwu) {
					gameChara.x = gameMap.x;
					gameChara.y = gameMap.y;
					gameMap.join(GameObjectCharMng.getGameObjectChar(gameChara.id));
				}
			} else {
				gameMap.join(gameObjectChar);
			}
		}
		// 拜师任务
		Vo_61553_0 zhuxian1 = chara.taskMap.get("主线—浮生若梦");
		int[] polarMapId = new int[] { 10000, 14000, 15000, 13000, 16000 };
		if (chara.current_task.equals("主线—浮生若梦_s21") && zhuxian1 != null
				&& chara.mapid == polarMapId[chara.polar - 1]) {
			// 揽仙镇、终南山、凤凰山、乾元山、骷髅山
			chara.current_task = "主线—浮生若梦_s21";
			zhuxian1.task_state = "1";
			String[] polar = { "五龙山云霄洞", "终南山玉柱洞", "凤凰山斗阙宫", "乾元山金光洞", "骷髅山白骨洞" };
			Vo_45056_0 vo_45056_3 = GameUtil.a45056(chara,
					"#R" + polar[chara.polar - 1] + "#n果然有仙道大家气象,若能在此修行,定能得证大道!", "主线—浮生若梦");
			GameObjectChar.send(new M45056_0(), vo_45056_3);
		}
		ExecutorsUtils.getExecutorPools().execute(new Runnable() {
			@Override
			public void run() {
//				Vo_UPDATE_APPEARANCE vo_61661_0 = GameUtil.a61661(chara);
//				gameObjectChar.gameMap.send(new MSG_UPDATE_APPEARANCE(), vo_61661_0);
				GamePartyUtil.partyIcon(chara);
//				GameCommonUtil.setGmStatus(GameObjectCharMng.getGameObjectChar(chara.id).gameMap.sessionList);
				GameCommonUtil.coreTaskNpcs(chara, gameObjectChar);
			}
		});
		//如果杀了人进入了安全区
		if(GameActiveUtil.isEnterSafeArea(gameObjectChar)) {
			return;
		}
	}

	@Override
	public int cmd() {
		return 32768;
	}
}