package com.fengshen.server.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fengshen.core.util.ExecutorsUtils;
import com.fengshen.db.domain.FixedTeam;
import com.fengshen.server.data.vo.Vo_16383_0;
import com.fengshen.server.data.vo.Vo_20467_0;
import com.fengshen.server.data.vo.Vo_20480_0;
import com.fengshen.server.data.vo.Vo_20481_0;
import com.fengshen.server.data.vo.Vo_20568_0;
import com.fengshen.server.data.vo.Vo_24505_0;
import com.fengshen.server.data.vo.Vo_4119_0;
import com.fengshen.server.data.vo.Vo_4121_0;
import com.fengshen.server.data.vo.Vo_45124_0;
import com.fengshen.server.data.vo.Vo_49189_0;
import com.fengshen.server.data.vo.Vo_61591_0;
import com.fengshen.server.data.vo.Vo_61593_0;
import com.fengshen.server.data.vo.Vo_8165_0;
import com.fengshen.server.data.vo.Vo_APPEAR;
import com.fengshen.server.data.vo.Vo_CONFIRM;
import com.fengshen.server.data.vo.Vo_TITLE;
import com.fengshen.server.data.vo.Vo_UPDATE_MOVE_SPEED;
import com.fengshen.server.data.vo.friend.Vo_FRIEND_ADD_CHAR;
import com.fengshen.server.data.vo.user.Vo_UPDATE_APPEARANCE;
import com.fengshen.server.data.write.M16383_0;
import com.fengshen.server.data.write.M20467_0;
import com.fengshen.server.data.write.M20480_0;
import com.fengshen.server.data.write.M20481_0;
import com.fengshen.server.data.write.M20568_0;
import com.fengshen.server.data.write.M24505_0;
import com.fengshen.server.data.write.M4119_0;
import com.fengshen.server.data.write.M4121_0;
import com.fengshen.server.data.write.M45124_0;
import com.fengshen.server.data.write.M49189_0;
import com.fengshen.server.data.write.M61591_0;
import com.fengshen.server.data.write.M61593_0;
import com.fengshen.server.data.write.M65529_0;
import com.fengshen.server.data.write.M8165_0;
import com.fengshen.server.data.write.MSG_CONFIRM;
import com.fengshen.server.data.write.MSG_TITLE;
import com.fengshen.server.data.write.MSG_UPDATE_MOVE_SPEED;
import com.fengshen.server.data.write.friend.MSG_FRIEND_ADD_CHAR;
import com.fengshen.server.data.write.user.MSG_UPDATE_APPEARANCE;
import com.fengshen.server.domain.Chara;
import com.fengshen.server.fight.FightManager;
import com.google.common.collect.Lists;

import lombok.extern.slf4j.Slf4j;
import tk.mybatis.mapper.entity.Example;

/**
 * 游戏队伍工具类
 * 
 * @author weilian
 *
 */
@Slf4j
public class GameTeamUtil {

	/**
	 * 申请带队
	 * 
	 * @param gameObjectChar
	 */
	public static void changeTeamLeader(GameObjectChar gameObjectChar, int new_leader_id) {
		if (gameObjectChar == null) {
			return;
		}
		Chara chara = gameObjectChar.chara;
		if (chara.mapid == 38004) {
			GameUtil.sendMeTips("试道场内不允许,更换队长。");
			return;
		}
		GameTeam gameTeam = gameObjectChar.gameTeam;
		Vo_61593_0 vo_61593_0 = new Vo_61593_0();
		vo_61593_0.ask_type = "request_join";
		GameObjectChar.send(new M61593_0(), vo_61593_0);
		
		vo_61593_0 = new Vo_61593_0();
		vo_61593_0.ask_type = "request_team_leader";
		GameObjectChar.send(new M61593_0(), vo_61593_0, chara.id);
		int index = 0;
		for (int i = 0; i < gameTeam.zhanliduiyuan.size(); ++i) {
			if (gameTeam.zhanliduiyuan.get(i).id == new_leader_id) {
				log.info("新队长名字={}", gameTeam.zhanliduiyuan.get(i).str);
				break;
			}
			index++;
		}
		int duiwuIndex = 0;
		for (int i = 0; i < gameTeam.duiwu.size(); ++i) {
			if (gameTeam.duiwu.get(i).id == new_leader_id) {
				log.info("新队长名字={}", gameTeam.duiwu.get(i).name);
				break;
			}
			duiwuIndex++;
		}
		// 把升为队长的人设置到下标0
		Collections.swap(gameTeam.duiwu, duiwuIndex, 0);
		for (int i = 0; i < gameTeam.duiwu.size(); ++i) {
			if (Integer.valueOf(new_leader_id) == gameTeam.duiwu.get(i).id) {
				continue;
			}
			// 对队长的队伍给成员
			GameObjectChar teamGameObjectChar = GameObjectCharMng.getGameObjectChar(gameTeam.duiwu.get(i).id);
			teamGameObjectChar.gameTeam.duiwu = gameTeam.duiwu;
			Vo_20481_0 vo_20481_0 = new Vo_20481_0();
			vo_20481_0.msg = StringUtils.join("#Y" , gameTeam.duiwu.get(0).name , "#n成为队长。");
			vo_20481_0.time = (int) (System.currentTimeMillis() / 1000L);
			GameObjectChar.send(new M20481_0(), vo_20481_0, gameTeam.duiwu.get(i).id);

		}
		List<Vo_4121_0> zhanliduiyuan = gameTeam.zhanliduiyuan;
		// 把要升为队长的玩家设置为第一位即队长的位置
		Collections.swap(zhanliduiyuan, index, 0);
		// 设置队员信息
		for (int j = 0; j < zhanliduiyuan.size(); ++j) {
			GameObjectChar teamGameObjectChara = GameObjectCharMng.getGameObjectChar(zhanliduiyuan.get(j).id);
			if (teamGameObjectChara.gameTeam != null) {
				teamGameObjectChara.gameTeam.zhanliduiyuan = zhanliduiyuan;
			}
		}
		GameObjectChar newLeaderGame = GameObjectCharMng.getGameObjectChar(new_leader_id);
		Vo_20568_0 vo_20568_0 = new Vo_20568_0();
		vo_20568_0.gid = "";
		GameObjectChar.send(new M20568_0(), vo_20568_0);
		vo_20568_0 = new Vo_20568_0();
		vo_20568_0.gid = "";
		newLeaderGame.sendOne(new M20568_0(), vo_20568_0);
		Vo_20481_0 vo_20481_2 = new Vo_20481_0();
		vo_20481_2.msg = "你被提升为队长。";
		vo_20481_2.time = (int) (System.currentTimeMillis() / 1000L);
		newLeaderGame.sendOne(new M20481_0(), vo_20481_2);
		// 队伍信息
		GameUtil.a4119(gameTeam.duiwu);
		// 右侧队伍列表信息
		GameUtil.a4121(zhanliduiyuan);
		// 设置title
		Vo_TITLE vo_61671_0 = new Vo_TITLE();
		vo_61671_0.id = new_leader_id;
		vo_61671_0.list.add(2);
		if (zhanliduiyuan.size() == 5) {
			vo_61671_0.list.add(4);
		} else {
			vo_61671_0.list.add(3);
		}
		if(newLeaderGame.chara.isNameRed == 1) {
			vo_61671_0.list.add(7);
		}
		gameObjectChar.gameMap.send(new MSG_TITLE(), vo_61671_0);
		vo_61671_0 = new Vo_TITLE();
		vo_61671_0.id = chara.id;
		vo_61671_0.list.add(2);
		vo_61671_0.list.add(5);
		if(chara.isNameRed == 1) {
			vo_61671_0.list.add(7);
		}
		gameObjectChar.gameMap.send(new MSG_TITLE(), vo_61671_0);
		
		//找出队长的速度
		int speed = newLeaderGame.chara.yidongsudu;
		for (int i = 0; i < gameTeam.duiwu.size(); ++i) {
			Vo_UPDATE_MOVE_SPEED vo_45177_0 = new Vo_UPDATE_MOVE_SPEED();
			vo_45177_0.id = gameTeam.duiwu.get(i).id;
			vo_45177_0.moveSpeedPercent = speed;
			gameObjectChar.gameMap.send(new MSG_UPDATE_MOVE_SPEED(), vo_45177_0);
			GameObjectChar teamGameObjectChara = GameObjectCharMng.getGameObjectChar(gameTeam.duiwu.get(i).id);
			if (teamGameObjectChara != null && teamGameObjectChara.chara.id != newLeaderGame.chara.id) {
				GameCommonUtil.flyInit(teamGameObjectChara);
			}
		}
		GameCommonUtil.flyInit(newLeaderGame);
		//通知地图所有人加载飞行器
		for(GameObjectChar notify:newLeaderGame.gameMap.sessionList) {
			notify.gameMap.send(new MSG_UPDATE_APPEARANCE(), GameUtil.a61661(notify.chara));
		}
	}

	/**
	 * 离开队伍
	 * @param gameObjectChar 玩家
	 */
	public static void quitTeam(GameObjectChar gameObjectChar) {
		if (gameObjectChar == null) {
			return;
		}
		Chara chara = gameObjectChar.chara;
		GameTeam gameTeam = gameObjectChar.gameTeam;
		if (GameCommonUtil.isNotGameTeam(gameTeam) && chara.id == gameTeam.duiwu.get(0).id) {
			if (chara.isFight) {
				GameCommonUtil.sendTips("队长战斗中无法离队", gameObjectChar);
				return;
			}
			Vo_TITLE vo_61671_0 = new Vo_TITLE();
			vo_61671_0.id = chara.id;
			if (chara.isNameRed == 1) {
				vo_61671_0.list.add(7);
			}
			gameObjectChar.gameMap.send(new MSG_TITLE(), vo_61671_0);

			Vo_61593_0 vo_61593_0 = new Vo_61593_0();
			vo_61593_0.ask_type = "request_join";
			gameObjectChar.sendOne(new M61593_0(), vo_61593_0);

			vo_61593_0 = new Vo_61593_0();
			vo_61593_0.ask_type = "request_team_leader";
			gameObjectChar.sendOne(new M61593_0(), vo_61593_0);
			// 清空队伍
			gameObjectChar.sendOne(new M4121_0(), new ArrayList<Vo_4121_0>());

			if (GameCommonUtil.isNotGameTeam(gameObjectChar.gameTeam)) {
				// 把移动速度恢复默认
				for (int i = 0; i < gameObjectChar.gameTeam.duiwu.size(); ++i) {
					GameObjectChar teamGameObjectChar = GameObjectCharMng
							.getGameObjectChar(gameObjectChar.gameTeam.duiwu.get(i).id);
					// 恢复默认移动速度
					Vo_UPDATE_MOVE_SPEED vo_45177_0 = new Vo_UPDATE_MOVE_SPEED();
					vo_45177_0.id = teamGameObjectChar.chara.id;
					vo_45177_0.moveSpeedPercent = teamGameObjectChar.chara.yidongsudu;
					teamGameObjectChar.gameMap.send(new MSG_UPDATE_MOVE_SPEED(), vo_45177_0);
				}
			}
		}

		if ((GameCommonUtil.isNotGameTeam(gameTeam)) && (chara.id == ((Chara) gameTeam.duiwu.get(0)).id)) {
			if ((gameTeam.zhanliduiyuan != null) && (!gameTeam.zhanliduiyuan.isEmpty())) {
				for (int i = 0; i < gameTeam.zhanliduiyuan.size(); ++i) {
					GameObjectChar teamGameObjectChar = GameObjectCharMng
							.getGameObjectChar(gameTeam.zhanliduiyuan.get(i).id);
					Chara teamChara = teamGameObjectChar.chara;
					if (teamGameObjectChar != null) {
						teamGameObjectChar.sendOne(new M4119_0(), new ArrayList<Vo_4119_0>());
						teamGameObjectChar.sendOne(new M4121_0(), new ArrayList<Vo_4121_0>());
						Vo_20480_0 vo_20480_0 = new Vo_20480_0();
						vo_20480_0.msg = "队伍解散了。";
						vo_20480_0.time = (int) (System.currentTimeMillis() / 1000L);
						teamGameObjectChar.sendOne(new M20480_0(), vo_20480_0);
						Vo_TITLE vo_61671_0 = new Vo_TITLE();
						vo_61671_0.id = gameTeam.zhanliduiyuan.get(i).id;
						if (teamChara.isNameRed == 1) {
							vo_61671_0.list.add(7);
						}
						teamGameObjectChar.sendOne(new MSG_TITLE(), vo_61671_0);
						// 如果是在副本
						if (teamGameObjectChar.gameMap.isDugeno()) {
							teamChara.x = 136;
							teamChara.y = 17;
							GameLine.getGameMap(teamChara.line, "天墉城").join(teamGameObjectChar);
						}
					}
				}

				if (GameCommonUtil.isNotGameTeam(gameObjectChar.gameTeam)) {
					for (int i = 0; i < gameObjectChar.gameTeam.zhanliduiyuan.size(); ++i) {
						GameObjectChar teamGameObjectChar = GameObjectCharMng
								.getGameObjectChar(gameObjectChar.gameTeam.zhanliduiyuan.get(i).id);
						if (teamGameObjectChar.chara.id == chara.id) {
							continue;
						}
						teamGameObjectChar.gameTeam = null;
						GameCommonUtil.flyInit(teamGameObjectChar);
						Vo_UPDATE_APPEARANCE vo_61661_0 = GameUtil.a61661(teamGameObjectChar.chara);
						teamGameObjectChar.gameMap.send(new MSG_UPDATE_APPEARANCE(), vo_61661_0);
					}
				}

				Vo_61593_0 vo_61593_0 = new Vo_61593_0();
				vo_61593_0.ask_type = "request_join";
				gameObjectChar.sendOne(new M61593_0(), vo_61593_0);

				vo_61593_0 = new Vo_61593_0();
				vo_61593_0.ask_type = "request_team_leader";
				gameObjectChar.sendOne(new M61593_0(), vo_61593_0);

				gameObjectChar.sendOne(new M4121_0(), new ArrayList<Vo_4121_0>());
			}
		} else if (GameCommonUtil.isNotGameTeam(gameTeam)) {
			gameObjectChar.sendOne(new M4119_0(), new ArrayList<Vo_4119_0>());
			gameObjectChar.sendOne(new M4121_0(), new ArrayList<Vo_4121_0>());
			Vo_20480_0 vo_20480_0 = new Vo_20480_0();
			vo_20480_0.msg = "你离开了队伍";
			vo_20480_0.time = (int) (System.currentTimeMillis() / 1000L);
			gameObjectChar.sendOne(new M20480_0(), vo_20480_0);
			for (int j = 0; j < gameTeam.duiwu.size(); ++j) {
				Vo_UPDATE_APPEARANCE vo_61661_0 = GameUtil.a61661((Chara) gameTeam.duiwu.get(j), new String[0]);
				gameObjectChar.sendOne(new MSG_UPDATE_APPEARANCE(), vo_61661_0);
			}
			Vo_49189_0 vo_49189_0 = new Vo_49189_0();
			gameObjectChar.sendOne(new M49189_0(), vo_49189_0);
			Iterator<Vo_4121_0> teamItemZanLi = gameTeam.zhanliduiyuan.iterator();
			while (teamItemZanLi.hasNext()) {
				Vo_4121_0 next = (Vo_4121_0) teamItemZanLi.next();
				if (next.id == chara.id) {
					teamItemZanLi.remove();
				}
				GameObjectChar teamGame = GameObjectCharMng.getGameObjectChar(next.id);
				if (teamGame != null) {
					teamGame.moveIds.remove(Integer.valueOf(chara.id));
				}
			}
			List<Chara> duiwu = gameObjectChar.gameTeam.duiwu;
			Iterator<Chara> duiwus = gameTeam.duiwu.iterator();
			while (duiwus.hasNext()) {
				Chara next = duiwus.next();
				if (next.id == chara.id) {
					duiwus.remove();
				}
			}
			GameUtil.a4119(duiwu);
			GameUtil.a4121(gameObjectChar.gameTeam.zhanliduiyuan);
			Vo_20568_0 vo_20568_0 = new Vo_20568_0();
			vo_20568_0.gid = "";
			GameObjectChar leaderGameObjectChar = GameObjectCharMng
					.getGameObjectChar(((Chara) gameTeam.duiwu.get(0)).id);
			if (leaderGameObjectChar != null) {
				
				Vo_UPDATE_APPEARANCE vo_61661_0 = GameUtil.a61661(leaderGameObjectChar.chara);
				leaderGameObjectChar.gameMap.send(new MSG_UPDATE_APPEARANCE(), vo_61661_0);
				
				leaderGameObjectChar.sendOne(new M20568_0(), vo_20568_0);
				GameCommonUtil.flyInit(leaderGameObjectChar);
				for (int l = 0; l < duiwu.size(); ++l) {
					vo_20480_0 = new Vo_20480_0();
					vo_20480_0.msg = StringUtils.join(chara.name, "离开了队伍");
					vo_20480_0.time = (int) (System.currentTimeMillis() / 1000L);
					GameObjectChar teamGame = GameObjectCharMng.getGameObjectChar(((Chara) duiwu.get(l)).id);
					if (teamGame != null) {
						teamGame.sendOne(new M20480_0(), vo_20480_0);
						Vo_45124_0 vo_45124_0 = new Vo_45124_0();
						teamGame.sendOne(new M45124_0(), vo_45124_0);
					}
				}
				Vo_TITLE vo_61671_2 = new Vo_TITLE();
				vo_61671_2.id = chara.id;
				if (chara.isNameRed == 1) {
					vo_61671_2.list.add(7);
				}
				gameObjectChar.sendOne(new MSG_TITLE(), vo_61671_2);

				Vo_TITLE vo_61671_0 = new Vo_TITLE();
				vo_61671_0.id = ((Chara) gameTeam.duiwu.get(0)).id;
				vo_61671_0.count = 1;
				vo_61671_0.list.add(Integer.valueOf(3));
				GameObjectChar teamGameObject = GameObjectCharMng.getGameObjectChar(vo_61671_0.id);
				if (teamGameObject != null) {
					teamGameObject.gameMap.send(new MSG_TITLE(), vo_61671_0);
				}
				if (gameObjectChar.gameMap.isDugeno()) {
					chara.x = 136;
					chara.y = 17;
					GameLine.getGameMap(chara.line, "天墉城").join(gameObjectChar);
				}
				// 如果有坐骑
				Vo_UPDATE_MOVE_SPEED vo_45177_0 = new Vo_UPDATE_MOVE_SPEED();
				vo_45177_0.id = gameObjectChar.chara.id;
				vo_45177_0.moveSpeedPercent = gameObjectChar.chara.yidongsudu;
				gameObjectChar.gameMap.send(new MSG_UPDATE_MOVE_SPEED(), vo_45177_0);
			}
		}
		gameObjectChar.gameTeam = null;
		GameCommonUtil.flyInit(gameObjectChar);
		Vo_UPDATE_APPEARANCE vo_61661_0 = GameUtil.a61661(gameObjectChar.chara);
		gameObjectChar.gameMap.send(new MSG_UPDATE_APPEARANCE(), vo_61661_0);
	}
	
	/**
	 * 邀请对方加入队伍
	 * @param gameObjectChar 邀请者
	 * @param id 被邀请人id
	 */
	public static void inviteJoinTeam(GameObjectChar gameObjectChar, int id) {
		Chara chara = gameObjectChar.chara;
		//邀请人
		GameObjectChar inviteJoinGameObjectChar = GameObjectCharMng.getGameObjectChar(id);
		if(inviteJoinGameObjectChar == null) {
			GameUtil.sendMeTips("邀请人不在线！");
			return;
		}
		Chara inviteChara = inviteJoinGameObjectChar.chara;
		if (inviteJoinGameObjectChar.gameTeam != null
				&& inviteJoinGameObjectChar.gameTeam.duiwu != null
				&& inviteJoinGameObjectChar.gameTeam.duiwu.size() > 0) {
			Vo_8165_0 vo_8165_2 = new Vo_8165_0();
			vo_8165_2.msg = "对方已有队伍了！";
			vo_8165_2.active = 0;
			GameObjectChar.send(new M8165_0(), vo_8165_2);
			return;
		}
		if (gameObjectChar.gameTeam == null) {
			Vo_61593_0 vo_61593_0 = new Vo_61593_0();
			vo_61593_0.ask_type = "invite_join";
			GameObjectChar.send(new M61593_0(), vo_61593_0);
			// 邀请者创建一支队伍
			Vo_TITLE vo_61671_0 = new Vo_TITLE();
			vo_61671_0.id = chara.id;
			vo_61671_0.count = 1;
			vo_61671_0.list.add(3);
			gameObjectChar.gameMap.send(new MSG_TITLE(), vo_61671_0);
			// 创建队伍
			GameTeam gameTeam = new GameTeam();
			gameTeam.duiwu.add(chara);
			gameTeam.zhanliduiyuan.add(GameUtil.add4121(chara, 1));
			gameObjectChar.creator(gameTeam);
			List<Chara> duiwu = gameObjectChar.gameTeam.duiwu;
			// 队伍信息
			GameUtil.a4119(duiwu);
			// 更新队伍信息
			GameUtil.a4121(gameObjectChar.gameTeam.zhanliduiyuan);

			Vo_20568_0 vo_20568_0 = new Vo_20568_0();
			vo_20568_0.gid = "";
			GameObjectChar.send(new M20568_0(), vo_20568_0);

			Vo_20480_0 vo_20480_0 = new Vo_20480_0();
			vo_20480_0.msg = "你组建了一支队伍。";
			vo_20480_0.time = (int) (System.currentTimeMillis() / 1000L);
			GameObjectChar.send(new M20480_0(), vo_20480_0);
		}

		//如果自己队伍满了
		else if ((gameObjectChar.gameTeam.duiwu != null
				&& gameObjectChar.gameTeam.zhanliduiyuan.size() >= 5)) {
			Vo_8165_0 vo_8165_3 = new Vo_8165_0();
			vo_8165_3.msg = "队伍已满，无法邀请";
			vo_8165_3.active = 0;
			GameObjectChar.send(new M8165_0(), vo_8165_3);
			return;
		}
		//如果是固定队员的话
		if(!com.qcloud.cos.utils.StringUtils.isNullOrEmpty(gameObjectChar.chara.fixedTeamName) && 
				gameObjectChar.chara.fixedTeamName.equals(inviteChara.fixedTeamName)) {
			if(inviteChara.getSettings().get("ft_inv_team") != null 
					&& inviteChara.getSettings().get("ft_inv_team") == 1) {
				//固定队员开启邀请自动通过，则直接加入该队伍
				requestJoin(gameObjectChar,inviteJoinGameObjectChar,inviteChara.name);
				//固定队
				GameCommonUtil.sendTips("你开启了固定队伍特权4，其他成员邀请#R入队#n自动通过", inviteJoinGameObjectChar);
				return;
			}
		}
		// 其他情况
		List<Vo_FRIEND_ADD_CHAR> vo_61545_0List = GameUtil.a61545(chara);
		GameObjectChar.send(new MSG_FRIEND_ADD_CHAR(), vo_61545_0List);

		Vo_8165_0 vo_8165_3 = new Vo_8165_0();
		vo_8165_3.msg = "你已发出邀请，请耐心等待";
		vo_8165_3.active = 0;
		GameObjectChar.send(new M8165_0(), vo_8165_3);
		// 邀请对方组队
		GameUtil.addInvitationChara(chara, id, "invite_join");
		// 发送邀请通知
		vo_8165_3.msg = StringUtils.join("#Y" , chara.name , "#n邀请你加入其队伍，请打开队伍界面查看邀请信息。");
		vo_8165_3.active = 0;
		inviteJoinGameObjectChar.sendOne(new M8165_0(), vo_8165_3);
	}
	
	/**
	 * 申请带队
	 * @param gameObjectChar 申请者
	 * @param peer_name 申请姓名
	 */
	public static void requestTeamLeader(GameObjectChar gameObjectChar, String peer_name) {
		
		Chara chara = gameObjectChar.chara;
		int leaderTeamId = gameObjectChar.gameTeam.duiwu.get(0).id;
		GameObjectChar toGameObjectChar = GameObjectCharMng.getGameObjectChar(leaderTeamId);
		if(toGameObjectChar == null) {
			return;
		}
		List<Vo_FRIEND_ADD_CHAR> vo_61545_0List = GameUtil.a61545(chara);
		GameObjectChar.send(new MSG_FRIEND_ADD_CHAR(), vo_61545_0List);
		Vo_24505_0 vo_24505_0 = GameUtil.a24505(chara);
		GameObjectChar.send(new M24505_0(), vo_24505_0);
		// 发送申请带队
		Vo_8165_0 vo_8165_0 = new Vo_8165_0();
		vo_8165_0.msg = "你的申请已发送";
		vo_8165_0.active = 0;
		GameObjectChar.send(new M8165_0(), vo_8165_0);
		//如果是一个固定队的
		if(!com.qcloud.cos.utils.StringUtils.isNullOrEmpty(gameObjectChar.chara.fixedTeamName) && 
				gameObjectChar.chara.fixedTeamName.equals(toGameObjectChar.chara.fixedTeamName)) {
			if (toGameObjectChar.chara.getSettings().get("ft_lead_team")!= null
		 		&& toGameObjectChar.chara.getSettings().get("ft_lead_team") == 1) {
					//固定队员为队长时，其他成员申请带队自动通过
					changeTeamLeader(toGameObjectChar, gameObjectChar.chara.id);
					//并通知之前队长
					GameCommonUtil.sendTips("你开启了固定队伍特权6，其他成员申请#R带队#n自动通过", toGameObjectChar);
					return;
				}
		}
		Vo_20467_0 vo_20467_1 = new Vo_20467_0();
		vo_20467_1.caption = "";
		vo_20467_1.content = "";
		vo_20467_1.peer_name = peer_name;
		vo_20467_1.ask_type = "request_team_leader";
		toGameObjectChar.sendOne(new M20467_0(), vo_20467_1);

		Vo_CONFIRM vo_45240_0 = new Vo_CONFIRM();
		vo_45240_0.tips = chara.name + "申请成为队长，是否同意？";
		vo_45240_0.down_count = 30;
		vo_45240_0.only_confirm = 0;
		vo_45240_0.confirm_type = "reject_count_down";
		vo_45240_0.confirmText = "";
		vo_45240_0.cancelText = "";
		vo_45240_0.show_dlg_mode = 3;
		vo_45240_0.countDownTips = "";
		vo_45240_0.para_str = "{}";
		toGameObjectChar.chara.currentConfirmItem = "request_team_leader";
		toGameObjectChar.sendOne(new MSG_CONFIRM(), vo_45240_0);
		toGameObjectChar.upduizhangid = chara.id;
	}
	
	/**
	 * 固定队伍设置key
	 * @param gameObjectChar 玩家
	 * @param level 对应的等级
	 * @return
	 */
	public static boolean fixedTeamSetKey(GameObjectChar gameObjectChar, int level) {
		Chara chara = gameObjectChar.chara;
		if(com.qcloud.cos.utils.StringUtils.isNullOrEmpty(chara.fixedTeamName)) {
			return false;
		}
		Example example = new Example(FixedTeam.class);
		example.createCriteria().andEqualTo("uid", chara.fixedTeamName);
		FixedTeam fixedTeam = GameData.that.fixedTeamService.selectOneByExample(example);
		if(fixedTeam == null) {
			return false;
		}
		if(fixedTeam.getLevel()<level) {
			GameCommonUtil.sendTips("固定队伍等级不符合！", gameObjectChar);
			return false;
		}
		GameCommonUtil.sendTips("设置成功", gameObjectChar);
		return true;
	}
	
	/**
	 * 同意加入队伍
	 * @param gameObjectChar 队长
	 * @param toGameObjectChar 加入人员
	 * @param peer_name 名字
	 */
	public static void requestJoin(GameObjectChar gameObjectChar, GameObjectChar toGameObjectChar, String peer_name) {
		Chara chara = gameObjectChar.chara;
		Chara toChara = toGameObjectChar.chara;
		String ask_type = "request_join";
		//只要一点击就让dalog先消失在处理一下请求。
		Vo_61591_0 vo_61591_0 = new Vo_61591_0();
		vo_61591_0.ask_type = ask_type;
		vo_61591_0.name = peer_name;
		GameObjectChar.send(new M61591_0(), vo_61591_0);
		if(!GameCommonUtil.isNotGameTeam(gameObjectChar.gameTeam)) {
			return;
		}
		if (toGameObjectChar != null
				&& toGameObjectChar.gameTeam != null
				&& toGameObjectChar.gameTeam.duiwu != null) {
			Vo_20481_0 vo_20481_0 = new Vo_20481_0();
			vo_20481_0.msg = StringUtils.join("#Y#<" , peer_name , "#>#n已有队伍");
			vo_20481_0.time = (int) (System.currentTimeMillis() / 1000);
			GameObjectChar.send(new M20481_0(), vo_20481_0);
			return;
		}
		// 判断队伍是否已满
		if (GameCommonUtil.isNotGameTeam(gameObjectChar.gameTeam)) {
			if (gameObjectChar.gameTeam.zhanliduiyuan.size()>=5) {
				GameUtil.sendMeTips("队伍已满,无法加人");
				toGameObjectChar.gameTeam = null;
				return;
			}
		}
		//判断对方是否在战斗
		if(toChara.isFight && FightManager.getFightContainer(toChara.id)!=null) {
			GameUtil.sendMeTips("对方正忙，无法加入队伍。");
			return;
		}
		//某些场景是不允许同意加入队伍的
		if(chara.mapName.contains("黑风洞、兰若寺、烈火涧")) {
			GameUtil.sendMeTips("不支持操作");
			return;
		}
		
		if(toChara.taskMap.get("萝卜桃子大收集") != null) {
			GameUtil.sendMeTips("#Y"+toChara.name+"#n领取了萝卜桃子大收集任务，无法加入队伍");
			gameObjectChar.gameTeam.liebiao.remove(Lists.newArrayList(toChara));
			return;
		}
		List<Vo_FRIEND_ADD_CHAR> vo_61545_0List = GameUtil.a61545(toChara);
		GameObjectChar.send(new MSG_FRIEND_ADD_CHAR(), vo_61545_0List);
		Vo_24505_0 vo_24505_0 = GameUtil.a24505(toChara);
		GameObjectChar.send(new M24505_0(), vo_24505_0);

		Vo_20480_0 vo_20480_0 = new Vo_20480_0();
		vo_20480_0.msg = StringUtils.join("#Y#<" , peer_name , "#>#n加入你的队伍");
		vo_20480_0.time = (int) (System.currentTimeMillis() / 1000);
		GameObjectChar.send(new M20480_0(), vo_20480_0);

		vo_61545_0List = GameUtil.a61545(toChara);
		GameObjectChar.send(new MSG_FRIEND_ADD_CHAR(), vo_61545_0List);
		vo_24505_0 = GameUtil.a24505(toChara);
		GameObjectChar.send(new M24505_0(), vo_24505_0);
		Vo_APPEAR vo_65529_0 = GameUtil.a65529(toChara);
		GameObjectChar.send(new M65529_0(), vo_65529_0);
		Vo_61593_0 vo_61593_0 = new Vo_61593_0();
		vo_61593_0.ask_type = "invite_join";
		toGameObjectChar.sendOne(new M61593_0(), vo_61593_0);

		vo_20480_0 = new Vo_20480_0();
		vo_20480_0.msg = StringUtils.join("你加入#Y#<" , chara.name , "#>#n的队伍");
		vo_20480_0.time = (int) (System.currentTimeMillis() / 1000);
		toGameObjectChar.sendOne(new M20480_0(), vo_20480_0);

		vo_61545_0List = GameUtil.a61545(chara);
		toGameObjectChar.sendOne(new MSG_FRIEND_ADD_CHAR(), vo_61545_0List);
		vo_24505_0 = GameUtil.a24505(chara);
		toGameObjectChar.sendOne(new M24505_0(), vo_24505_0);
		Vo_4121_0 add4121 = GameUtil.add4121(toChara, 1);
		if (!chara.isFight && FightManager.getFightContainer(chara.id)==null) {
			//如果不在战斗的话,就加入到队伍中
			gameObjectChar.gameTeam.duiwu.add(toChara);
		}else {
			//暂离状态
			add4121.memberteam_status = 2;
		}
		//切换到队长的地图
		if(chara.mapid != toGameObjectChar.chara.mapid) {
			GameLine.getGameMap(chara.line, chara.mapName).joinduiyuan(toGameObjectChar, chara);
		}
		//暂离队伍
		gameObjectChar.gameTeam.zhanliduiyuan.add(add4121);
		//设置去重结果
		toGameObjectChar.gameTeam = gameObjectChar.gameTeam;
		gameObjectChar.gameTeam.liebiao.remove(Lists.newArrayList(toChara));
		
		toGameObjectChar.gameTeam.liebiao.clear();

		//更新人物外观数据
		Vo_UPDATE_APPEARANCE vo_61661_0 = GameUtil.a61661(toChara);
		toGameObjectChar.sendOne(new MSG_UPDATE_APPEARANCE(), vo_61661_0);
		
		List<Chara> charas = gameObjectChar.gameTeam.duiwu;
		// 更新队伍界面信息
		GameUtil.a4119(charas);
		// 刷新队伍右侧信息
		GameUtil.a4121(gameObjectChar.gameTeam.zhanliduiyuan);
		Vo_20568_0 vo_20568_0 = new Vo_20568_0();
		vo_20568_0.gid = "";
		GameObjectChar.send(new M20568_0(), vo_20568_0);

		Vo_8165_0 vo_8165_0 = new Vo_8165_0();
		vo_8165_0.msg = StringUtils.join(toGameObjectChar.chara.name , "加入队伍");
		vo_8165_0.active = 0;
		gameObjectChar.sendOne(new M8165_0(), vo_8165_0);

		toGameObjectChar.sendOne(new M20568_0(), vo_20568_0);
		vo_61593_0 = new Vo_61593_0();
		vo_61593_0.ask_type = "invite_join";
		toGameObjectChar.sendOne(new M61593_0(), vo_61593_0);

		// 以下是设置队伍信息
		Vo_TITLE vo_61671_0 = new Vo_TITLE();
		// 如果是满队的状态
		if (gameObjectChar.gameTeam.zhanliduiyuan.size() >= 5) {
			vo_61671_0 = new Vo_TITLE();
			vo_61671_0.id = chara.id;
			vo_61671_0.list.add(4);
			if(chara.isNameRed == 1) {
				vo_61671_0.list.add(7);
			}
			gameObjectChar.gameMap.send(new MSG_TITLE(), vo_61671_0);
			// 设置队员
			vo_61671_0 = new Vo_TITLE();
			vo_61671_0.id = toChara.id;
			vo_61671_0.list.add(2);
			vo_61671_0.list.add(5);
			if(toChara.isNameRed == 1) {
				vo_61671_0.list.add(7);
			}
			gameObjectChar.gameMap.send(new MSG_TITLE(), vo_61671_0);
		} else {
			// 这是设置队长
			vo_61671_0 = new Vo_TITLE();
			vo_61671_0.id = chara.id;
			if (chara.isFight && chara.mapid == toChara.mapid) {
				// 设置队长
				vo_61671_0.list.add(1);
				vo_61671_0.list.add(3);
				if(chara.isNameRed == 1) {
					vo_61671_0.list.add(7);
				}
				gameObjectChar.gameMap.send(new MSG_TITLE(), vo_61671_0);
				// 设置队员
				vo_61671_0 = new Vo_TITLE();
				vo_61671_0.id = toChara.id;
				vo_61671_0.list.add(2);
				if(toChara.isNameRed == 1) {
					vo_61671_0.list.add(7);
				}
				gameObjectChar.gameMap.send(new MSG_TITLE(), vo_61671_0);
			} else {
				// 不在战斗
				vo_61671_0.list.add(3);
				if(chara.isNameRed == 1) {
					vo_61671_0.list.add(7);
				}
				gameObjectChar.gameMap.send(new MSG_TITLE(), vo_61671_0);
				// 设置队员
				vo_61671_0 = new Vo_TITLE();
				vo_61671_0.id = toChara.id;
				vo_61671_0.list.add(2);
				vo_61671_0.list.add(5);
				if(toChara.isNameRed == 1) {
					vo_61671_0.list.add(7);
				}
				gameObjectChar.gameMap.send(new MSG_TITLE(), vo_61671_0);
			}
		}
		GameCommonUtil.flyInit(toGameObjectChar);
		//通知地图所有人加载飞行器
		for(GameObjectChar notify:toGameObjectChar.gameMap.sessionList) {
			notify.gameMap.send(new MSG_UPDATE_APPEARANCE(), GameUtil.a61661(notify.chara));
		}
	}
	
	/**
	 * 更新固定队伍信息
	 * @param gameObjectChar
	 */
	public static void updateFixedTeamData(GameObjectChar gameObjectChar) {
		Chara chara = gameObjectChar.chara;
		//如果当天大于500直接返回
		if(chara.fixedTeamPoint+1>500) {
			return;
		}
		ExecutorsUtils.getExecutorPools().execute(new Runnable() {
			@Override
			public void run() {
				//固定队伍加成
				if(GameCommonUtil.isNotGameTeam(gameObjectChar.gameTeam)) {
					if(!com.qcloud.cos.utils.StringUtils.isNullOrEmpty(chara.fixedTeamName)) {
						Example example = new Example(FixedTeam.class);
						example.createCriteria().andEqualTo("uid", chara.fixedTeamName);
						FixedTeam fixedTeam = GameData.that.fixedTeamService.selectOneByExample(example);
						Map<String,String> gidsMap = new HashMap<>();
						if(fixedTeam != null) {
							//最高等级8级
							if(fixedTeam.getLevel()<8) {
								JSONArray parseArray = JSONObject.parseArray(fixedTeam.getMembers());
								for (int i = 0; i < parseArray.size(); i++) {
									String string = parseArray.getJSONObject(i).getString("gid");
									gidsMap.put(string, string);
								}
								int fixedTeamSize = 0;
								for(Chara teamChara:gameObjectChar.gameTeam.duiwu) {
									if(gidsMap.get(teamChara.uuid) != null) {
										fixedTeamSize++;
									}
								}
								int oldFixedTeamSize = parseArray.size();
								//如果所有固定队伍都存在这个队伍
								if(fixedTeamSize == oldFixedTeamSize) {
									for(Chara teamChara:gameObjectChar.gameTeam.duiwu) {
										teamChara.fixedTeamPoint+=1;
										Vo_16383_0 vo_16383_2 = GameUtil.a16383(teamChara,"你所在的固定队获得#R1#n点亲密,当天上限500点！",0);
										GameObjectChar.send(new M16383_0(), vo_16383_2, teamChara.id);
									}
									//更新固定队伍亲密度
									FixedTeam updateFixedTeam = new FixedTeam();
									updateFixedTeam.setIntimacy(fixedTeam.getIntimacy()+1);
									if(fixedTeam.getIntimacy()+1>=(fixedTeam.getLevel()+1)*1000) {
										//升级
										updateFixedTeam.setLevel(fixedTeam.getLevel()+1);
									}
									updateFixedTeam.setId(fixedTeam.getId());
									GameData.that.fixedTeamService.updateByPrimaryKeySelective(updateFixedTeam);
								}
							}
						}
					}
				}
			}
		});
	}
}