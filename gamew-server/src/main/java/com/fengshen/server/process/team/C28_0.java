package com.fengshen.server.process.team;

import com.fengshen.server.data.vo.*;
import com.fengshen.server.data.write.*;
import org.springframework.stereotype.Service;

import com.fengshen.server.data.vo.user.Vo_UPDATE_APPEARANCE;
import com.fengshen.server.data.write.user.MSG_UPDATE_APPEARANCE;
import com.fengshen.server.domain.Chara;
import com.fengshen.server.game.GameCommonUtil;
import com.fengshen.server.game.GameHandler;
import com.fengshen.server.game.GameObjectChar;
import com.fengshen.server.game.GameObjectCharMng;
import com.fengshen.server.game.GameTeam;
import com.fengshen.server.game.GameUtil;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

import java.util.List;

// 队员回归队伍
@Service
public class C28_0 implements GameHandler {
	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		GameObjectChar gameObjectChar = GameObjectChar.getGameObjectChar();
		GameTeam gameTeam = gameObjectChar.gameTeam;
		Chara chara = gameObjectChar.chara;
		//如果没有队伍哪来的归队一说
		if(!GameCommonUtil.isNotGameTeam(gameTeam)) {
			//找到上个队伍队长
//			int upduizhangid = gameObjectChar.upduizhangid;
//			if(upduizhangid != 0) {
//				GameObjectChar upLeader = GameObjectCharMng.getGameObjectChar(upduizhangid);
//				if(upLeader != null && upLeader.chara.id != chara.id) {
//					//上个队长有队伍
//					if(GameCommonUtil.isNotGameTeam(upLeader.gameTeam)) {
//						//踢出这个人
//						Iterator<Chara> iterator = upLeader.gameTeam.duiwu.iterator();
//						while(iterator.hasNext()) {
//							Chara next = iterator.next();
//							//把暂离队伍人员删除出队伍列表
//							if(next.id == chara.id) {
//								iterator.remove();
//							}
//						}
//						while(iterator.hasNext()) {
//							Chara next = iterator.next();
//							GameObjectChar teamGame = GameObjectCharMng.getGameObjectChar(next.id);
//							if(teamGame != null && teamGame != upLeader) {
//								//把队长的队伍给其他队友
//								teamGame.gameTeam = upLeader.gameTeam;
//							}
//						}
//					}
//				}
//			}
			GameUtil.sendMeTips("请加入队伍，在操作！");
			return;
		}
		if (!gameTeam.duiwu.isEmpty()) {
			// 获取队伍队长是否在战斗,如果正在战斗的话则无法归队.
			if (gameTeam.duiwu.get(0).isFight) {
				GameUtil.sendMeTips("队伍正忙,请稍后归队.");
				return;
			}
		}
		
		if(gameObjectChar.chara.taskMap.get("萝卜桃子大收集") != null) {
			GameCommonUtil.sendTips("领取了萝卜桃子大收集任务，不允许快捷切换地图");
			return;
		}
 		
		if (gameObjectChar.chara.taskMap.get("坐牢") != null) {
			GameUtil.sendMeTips("正在坐牢，不允许操作");
			return;
		}
		
		gameObjectChar.gameTeam = GameObjectCharMng
				.getGameObjectChar(gameTeam.duiwu.get(0).id).gameTeam;
		//获取队长队伍信息
		GameObjectChar session1 = GameObjectCharMng.getGameObjectChar(gameTeam.duiwu.get(0).id);
		boolean isAdd = gameObjectChar.gameTeam.duiwu.stream().filter(c->c.id == chara.id).findAny().isPresent();
		//如果存在队伍里面
		if(isAdd) {
			GameUtil.sendMeTips("你已在队伍中！");
			return;
		}
		gameObjectChar.gameTeam.duiwu.add(chara);
		
		for (int i = 0; i < gameObjectChar.gameTeam.duiwu.size(); ++i) {
			GameObjectChar session2 = GameObjectCharMng
					.getGameObjectChar(gameObjectChar.gameTeam.duiwu.get(i).id);
			session2.gameTeam = gameObjectChar.gameTeam;
		}
		// 如果当前回归队伍的地图和队伍的地图一致则无需刷新
		if (chara.mapid != session1.chara.mapid) {
			gameObjectChar.gameMap.joinduiyuan(gameObjectChar, session1.chara);
		}
		for (int i = 0; i < gameObjectChar.gameTeam.zhanliduiyuan.size(); ++i) {
			GameObjectChar session2 = GameObjectCharMng
					.getGameObjectChar(gameObjectChar.gameTeam.zhanliduiyuan.get(i).id);
			for (int j = 0; j < session2.gameTeam.zhanliduiyuan.size(); ++j) {
				if (session2.gameTeam.zhanliduiyuan.get(j).id == chara.id) {
					session2.gameTeam.zhanliduiyuan.get(j).memberteam_status = 1;
				}
			}
		}
		for (int i = 0; i < session1.gameTeam.duiwu.size(); ++i) {
			GameObjectChar team = GameObjectCharMng.getGameObjectChar(gameObjectChar.gameTeam.duiwu.get(i).id);
			if(team != null && team != gameObjectChar) {
				Vo_8165_0 vo_8165_0 = new Vo_8165_0();
				vo_8165_0.msg = chara.name + "回到队伍中";
				vo_8165_0.active = 0;
				team.sendOne(new M8165_0(), vo_8165_0);
			}
			
		}
		Vo_TITLE vo_61671_0 = null;
		Vo_20480_0 vo_20480_0 = new Vo_20480_0();
		vo_20480_0.msg = chara.name + "你回到了#Y#<" + session1.chara.name + "#>#n的队伍。";
		vo_20480_0.time = (int) (System.currentTimeMillis() / 1000L);
		GameObjectChar.send(new M20480_0(), vo_20480_0);
		GameUtil.a4119(gameTeam.duiwu);
		GameUtil.a4121(gameTeam.zhanliduiyuan);
		vo_61671_0 = new Vo_TITLE();
		vo_61671_0.id = chara.id;
		vo_61671_0.count = 2;
		vo_61671_0.list.add(2);
		vo_61671_0.list.add(5);
		gameObjectChar.gameMap.send(new MSG_TITLE(), vo_61671_0);

		vo_61671_0 = new Vo_TITLE();
		vo_61671_0.id = GameObjectCharMng.getGameObjectChar(gameTeam.duiwu.get(0).id).gameTeam.duiwu.get(0).id;
		if (GameObjectCharMng.getGameObjectChar(gameTeam.duiwu.get(0).id).gameTeam.duiwu.size() >= 5) {
			vo_61671_0.list.add(4);
		} else {
			vo_61671_0.list.add(3);
		}
		gameObjectChar.gameMap.send(new MSG_TITLE(), vo_61671_0);
		// 设置下移动速度为队长的移动速度
		Vo_UPDATE_MOVE_SPEED vo_45177_0 = new Vo_UPDATE_MOVE_SPEED();
		vo_45177_0.id = chara.id;
		vo_45177_0.moveSpeedPercent = gameTeam.duiwu.get(0).yidongsudu;
		gameObjectChar.gameMap.send(new MSG_UPDATE_MOVE_SPEED(), vo_45177_0);
		//归队
		GameCommonUtil.flyInit(gameObjectChar);
		//这里得通知下队友
		for (int i = 0; i < gameObjectChar.gameTeam.zhanliduiyuan.size(); ++i) {
			GameObjectChar teamGameObjectChar = GameObjectCharMng.getGameObjectChar(gameObjectChar.gameTeam.zhanliduiyuan.get(i).id);
			Vo_UPDATE_APPEARANCE vo_61661_0 = GameUtil.a61661(teamGameObjectChar.chara);
			teamGameObjectChar.gameMap.send(new MSG_UPDATE_APPEARANCE(), vo_61661_0);
		}
		//通知地图所有人加载飞行器
		for(GameObjectChar notify:gameObjectChar.gameMap.sessionList) {
			notify.gameMap.send(new MSG_UPDATE_APPEARANCE(), GameUtil.a61661(notify.chara));
		}
	}
	public static void goBackTeam(GameObjectChar gameObjectChar, Chara backChara, Chara teamLeaderChara) {
		GameTeam gameTeam = gameObjectChar.getGameTeam();
		for (int i = 0; i < gameObjectChar.gameTeam.zhanliduiyuan.size(); ++i) {
			final GameObjectChar session2 = GameObjectCharMng.getGameObjectChar(gameObjectChar.gameTeam.zhanliduiyuan.get(i).id);
			for (int j = 0; j < session2.gameTeam.zhanliduiyuan.size(); ++j) {
				Vo_4121_0 vo_4121_0 = session2.gameTeam.zhanliduiyuan.get(j);
				if (vo_4121_0.id == backChara.id) {
					vo_4121_0.memberteam_status = 1;
				}
			}
		}
		List<Chara> gameTemDuiwu = gameObjectChar.gameTeam.duiwu;
		for (int i = 0; i < gameTemDuiwu.size(); ++i) {
			Chara chara1 = gameTemDuiwu.get(i);
			final Vo_61661_0 vo_61661_0 = GameUtil.MSG_UPDATE_APPEARANCE_a61661(backChara);
			GameObjectCharMng.sendAllmapname(new M61661_0_MSG_UPDATE_APPEARANCE(), vo_61661_0, teamLeaderChara.mapName);
			final Vo_8165_0 vo_8165_0 = new Vo_8165_0();
			vo_8165_0.msg = backChara.name + "回到队伍中";
			vo_8165_0.active = 0;
			GameObjectCharMng.getGameObjectChar(chara1.id).sendOne(new M8165_0(), vo_8165_0);

			Vo_20480_0 vo_20480_0 = new Vo_20480_0();
			vo_20480_0.msg = backChara.name + "回到了队伍。";
			vo_20480_0.time = (int) (System.currentTimeMillis() / 1000L);
			GameObjectCharMng.getGameObjectChar(chara1.id).sendOne(new M20480_0(), vo_20480_0);
		}

		Vo_20480_0 vo_20480_0 = new Vo_20480_0();
		vo_20480_0.msg = backChara.name + "你回到了#Y#<" + teamLeaderChara.name + "#>#n的队伍。";
		vo_20480_0.time = (int) (System.currentTimeMillis() / 1000L);
		GameObjectChar.send(new M20480_0(), vo_20480_0);

		GameUtil.a4119(gameTeam.duiwu);
		GameUtil.a4121(gameTeam.zhanliduiyuan);
		Vo_TITLE vo_61671_0 = new Vo_TITLE();
		vo_61671_0.id = teamLeaderChara.id;
		vo_61671_0.count = 2;
		vo_61671_0.list.add(2);
		vo_61671_0.list.add(5);
		gameObjectChar.gameMap.send(new MSG_TITLE(), vo_61671_0);

		vo_61671_0 = new Vo_TITLE();
		vo_61671_0.id = GameObjectCharMng.getGameObjectChar(gameTeam.duiwu.get(0).id).gameTeam.duiwu.get(0).id;
		if (GameObjectCharMng.getGameObjectChar(gameTeam.duiwu.get(0).id).gameTeam.duiwu.size() >= 5) {
			vo_61671_0.list.add(4);
		} else {
			vo_61671_0.list.add(3);
		}
		gameObjectChar.gameMap.send(new MSG_TITLE(), vo_61671_0);
		// 设置下移动速度为队长的移动速度
		Vo_UPDATE_MOVE_SPEED vo_45177_0 = new Vo_UPDATE_MOVE_SPEED();
		vo_45177_0.id = teamLeaderChara.id;
		vo_45177_0.moveSpeedPercent = gameTeam.duiwu.get(0).yidongsudu;
		gameObjectChar.gameMap.send(new MSG_UPDATE_MOVE_SPEED(), vo_45177_0);
		//归队
		GameCommonUtil.flyInit(gameObjectChar);
		//这里得通知下队友
		for (int i = 0; i < gameObjectChar.gameTeam.zhanliduiyuan.size(); ++i) {
			GameObjectChar teamGameObjectChar = GameObjectCharMng.getGameObjectChar(gameObjectChar.gameTeam.zhanliduiyuan.get(i).id);
			Vo_UPDATE_APPEARANCE vo_61661_0 = GameUtil.a61661(teamGameObjectChar.chara);
			teamGameObjectChar.gameMap.send(new MSG_UPDATE_APPEARANCE(), vo_61661_0);
		}
		//通知地图所有人加载飞行器
		for(GameObjectChar notify:gameObjectChar.gameMap.sessionList) {
			notify.gameMap.send(new MSG_UPDATE_APPEARANCE(), GameUtil.a61661(notify.chara));
		}
	}

	@Override
	public int cmd() {
		return 28;
	}
}