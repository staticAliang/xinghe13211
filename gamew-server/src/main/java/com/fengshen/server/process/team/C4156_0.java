package com.fengshen.server.process.team;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fengshen.db.domain.Dialog;
import com.fengshen.db.domain.Party;
import com.fengshen.server.data.GameReadTool;
import com.fengshen.server.data.vo.Vo_20467_0;
import com.fengshen.server.data.vo.Vo_20480_0;
import com.fengshen.server.data.vo.Vo_20568_0;
import com.fengshen.server.data.vo.Vo_61593_0;
import com.fengshen.server.data.vo.Vo_8165_0;
import com.fengshen.server.data.vo.Vo_TITLE;
import com.fengshen.server.data.vo.friend.Vo_FRIEND_ADD_CHAR;
import com.fengshen.server.data.vo.party.Vo_PARTY_DIALOG.Vo_PARTY_DIALOG_Item;
import com.fengshen.server.data.write.M20467_0;
import com.fengshen.server.data.write.M20480_0;
import com.fengshen.server.data.write.M20568_0;
import com.fengshen.server.data.write.M61593_0;
import com.fengshen.server.data.write.M8165_0;
import com.fengshen.server.data.write.MSG_TITLE;
import com.fengshen.server.data.write.friend.MSG_FRIEND_ADD_CHAR;
import com.fengshen.server.domain.Chara;
import com.fengshen.server.game.GameCommonUtil;
import com.fengshen.server.game.GameData;
import com.fengshen.server.game.GameHandler;
import com.fengshen.server.game.GameObjectChar;
import com.fengshen.server.game.GameObjectCharMng;
import com.fengshen.server.game.GamePartyUtil;
import com.fengshen.server.game.GameTeam;
import com.fengshen.server.game.GameTeamUtil;
import com.fengshen.server.game.GameUtil;
import com.qcloud.cos.utils.StringUtils;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import tk.mybatis.mapper.entity.Example;

/**
 * 请求加入、队伍、帮派等等 CMD_REQUEST_JOIN
 * 
 *
 */
@Service
public class C4156_0 implements GameHandler {
	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		String peer_name = GameReadTool.readString(buff);
		int id = GameReadTool.readInt(buff);
		String ask_type = GameReadTool.readString(buff);
		GameObjectChar gameObjectChar = GameObjectChar.getGameObjectChar();
		Chara chara = gameObjectChar.chara;
		// 加入帮派操作
		if ("party_remote".equals(ask_type) || "party_invite".equals(ask_type) || "party".equals(ask_type)) {
			// 查询该帮派信息,获取到帮主信息.
			Party party = GameData.that.partyService.findByPartyName(peer_name);
			if (party == null) {
				return;
			} else if (party.getState() != 0) {
				GameUtil.sendMeTips("该帮派涉嫌违规，已被封停！");
				return;
			}
			// 如果等级和道行满足
			int minLevel = (party.getAutoAcceptLevel()-1) / 100000;
			int minTao = chara.tao / 360;
			if (chara.level < minLevel || minTao < party.getMinTao()) {
				GameUtil.sendMeTips("条件不满足");
				return;
			}
			if(!StringUtils.isNullOrEmpty(chara.getPartyName())) {
				GameUtil.sendMeTips("你已有帮派！");
				return;
			}
			// 查询该帮派是否开启自动接受
			if (party.getAutoAcceptLevel() % 10 == 1) {
				// 自动加入帮派
				GamePartyUtil.autoAcceptAddParty(party, gameObjectChar);
				return;
			}
			// 查询是否已经申请该帮派的信息
			Example isExample = new Example(Dialog.class);
			isExample.createCriteria().andEqualTo("applyGid", chara.uuid).andEqualTo("peerName", peer_name)
					.andEqualTo("askType", "party");
			if (GameData.that.dialogService.selectCountByExample(isExample) > 0) {
				GameCommonUtil.dialogOk("你已向该帮派发送入帮申请，请等待。");
				return;
			}
			if ("party_invite".equals(ask_type)) {
				// 邀请入帮peer_name为邀请人的名字
				GameUtil.sendMeTips("暂不支持邀请入帮.");
				return;
			}
			String leader = party.getLeader();
			JSONArray parseArray = JSONObject.parseArray(leader);
			// 需要通知的人。
			List<String> notifyGids = new ArrayList<>();
			for (int i = 0; i < parseArray.size(); i++) {
				JSONObject jsonObject = parseArray.getJSONObject(i);
				notifyGids.add(jsonObject.getString("gid"));
			}
			// 插入记录到数据库
			Dialog dialog = new Dialog();
			dialog.setApplyGid(chara.uuid);
			dialog.setAskType("party");
			dialog.setCreateTime(new Date());
			dialog.setPeerName(peer_name);
			Vo_PARTY_DIALOG_Item item = new Vo_PARTY_DIALOG_Item();
			item.setName(chara.name);
			item.setLevel(chara.level);
			item.setPolar(chara.polar);
			item.setTao(chara.tao + chara.taoPoint);
			item.setGender(chara.sex);
			item.setGid(chara.uuid);
			dialog.setExtJson(JSONObject.toJSONString(item));
			GameData.that.dialogService.insertSelective(dialog);
			GameCommonUtil.dialogOk("申请加入帮派成功，请等待...");
			// 通知帮派管理人员
			for (String gid : notifyGids) {
				GameObjectChar gameObjectCharByUUid = GameObjectCharMng.getGameObjectCharByUUid(gid);
				if (gameObjectCharByUUid != null) {
					GameCommonUtil.sendTips("有人申请加入帮派,请及时处理。", gameObjectCharByUUid.chara.id);
				}
			}
			return;
		}
		// 有些地图是不允许组队的.
		if (chara.mapName.equals("瑶池") || chara.mapName.equals("桐柏山") || chara.mapName.equals("黑风洞一层")
				|| chara.mapName.equals("黑风洞二层") || chara.mapName.equals("黑风洞三层") || chara.mapName.equals("兰若寺后山")
				|| chara.mapName.equals("兰若寺") || chara.mapName.equals("烈火涧") || chara.mapName.equals("烈火涧西面")
				|| chara.mapName.equals("烈火涧北面") || chara.mapName.equals("烈火涧东面") || chara.mapName.equals("飘渺仙府")
				|| chara.mapName.equals("仙府秘境") || chara.mapName.equals("仙府大殿")) {
			GameUtil.sendMeTips("此任务，不允许操作");
			return;
		}
		if (chara.taskMap.get("坐牢") != null) {
			GameUtil.sendMeTips("正在坐牢，不允许操作");
			return;
		}
		if ("request_team_leader".equals(ask_type)) {
			if(gameObjectChar.chara.taskMap.get("萝卜桃子大收集") != null) {
				GameCommonUtil.sendTips("你领取了萝卜桃子大收集任务，无法申请带队");
				return;
			}
			GameTeamUtil.requestTeamLeader(gameObjectChar, peer_name);
			return;
		}
		// 如果有队友点了请求组队，这是队长自己又创建队伍，则走下面的逻辑
		GameObjectChar requestGame = GameObjectCharMng.getGameObjectChar(id);
		if(requestGame == null) {
			return;
		}
		if (requestGame.chara.mapName.equals("试道场")) {
			GameUtil.sendMeTips("当前不允许操作");
			return;
		}
		// 请求组队，自己创建队伍也是走这里
		// 如果是自己一个人组队
		if (ask_type.equals("request_join")) {
			// 申请的队长在执行某些任务
			if (requestGame.chara.mapName.equals("瑶池") || requestGame.chara.mapName.equals("桐柏山")
					|| requestGame.chara.mapName.equals("黑风洞一层") || requestGame.chara.mapName.equals("黑风洞二层")
					|| requestGame.chara.mapName.equals("黑风洞三层") || requestGame.chara.mapName.equals("兰若寺后山")
					|| requestGame.chara.mapName.equals("兰若寺") || requestGame.chara.mapName.equals("烈火涧")
					|| requestGame.chara.mapName.equals("烈火涧西面") || requestGame.chara.mapName.equals("烈火涧北面")
					|| requestGame.chara.mapName.equals("烈火涧东面") || requestGame.chara.mapName.equals("飘渺仙府")
					|| requestGame.chara.mapName.equals("仙府秘境") || requestGame.chara.mapName.equals("仙府大殿")) {
				GameUtil.sendMeTips("不允许操作");
				return;
			}
			
			if (requestGame.gameTeam != null && id != chara.id) {
				if (requestGame.gameTeam.zhanliduiyuan.size() >= 5) {
					Vo_8165_0 vo_8165_3 = new Vo_8165_0();
					vo_8165_3.msg = "对方队伍已满！";
					vo_8165_3.active = 0;
					GameObjectChar.send(new M8165_0(), vo_8165_3);
					return;
				}
				GameObjectChar leaderGame = GameObjectCharMng.getGameObjectChar(requestGame.gameTeam.duiwu.get(0).id);
				//如果是一个固定队的
				if(!StringUtils.isNullOrEmpty(gameObjectChar.chara.fixedTeamName) && gameObjectChar.chara.fixedTeamName.equals(leaderGame.chara.fixedTeamName)) {
					if(leaderGame.chara.getSettings().get("ft_req_team") != null 
							&& leaderGame.chara.getSettings().get("ft_req_team") == 1) {
						//固定队员为队长时，其他成员申请带队自动通过
						GameTeamUtil.requestJoin(leaderGame, gameObjectChar, peer_name);
						//并通知之前队长
						GameCommonUtil.sendTips("你开启了固定队伍特权3，其他成员申请#R入队#n自动通过", leaderGame);
						return;
					}
				}
				
				Vo_8165_0 vo_8165_2 = new Vo_8165_0();
				vo_8165_2.msg = "你已发出申请，请耐心等待";
				vo_8165_2.active = 0;
				GameObjectChar.send(new M8165_0(), vo_8165_2);

				// 判断被申请者的申请列表中是否有自己的申请记录了
				Boolean has = false;
				for (int i = 0; i < requestGame.gameTeam.liebiao.size(); ++i) {
					for (int j = 0; j < requestGame.gameTeam.liebiao.get(i).size(); ++j) {
						if (requestGame.gameTeam.liebiao.get(i).get(j).id == chara.id) {
							has = true;
						}
					}
				}
				// 如果申请者不在对方的列表中，就给他自己新建一个列表
				if (!has) {
					List<Chara> list = new ArrayList<Chara>();
					list.add(chara);
					requestGame.gameTeam.liebiao.add(list);
				}

				// 给被邀请者发送申请请求
				Vo_20467_0 vo_20467_2 = new Vo_20467_0();
				vo_20467_2.caption = "";
				vo_20467_2.content = "";
				vo_20467_2.peer_name = chara.name;
				vo_20467_2.ask_type = "request_join";
				vo_20467_2.org_icon = GameUtil.getWaiguan(chara.polar, chara.sex, chara);
				vo_20467_2.iid_str = chara.uuid;
				vo_20467_2.skill = chara.level;
				vo_20467_2.str = chara.name;
				vo_20467_2.master = chara.sex;
				vo_20467_2.metal = chara.polar;
				vo_20467_2.req_str = "";
				vo_20467_2.passive_mode = GameUtil.getWaiguan(chara.polar, chara.sex, chara);
				vo_20467_2.party_contrib = chara.getPartyName() == null ? "" : chara.getPartyName();
				vo_20467_2.teamMembersCount = 1;
				vo_20467_2.comeback_flag = 0;
				GameObjectCharMng.getGameObjectChar(requestGame.gameTeam.duiwu.get(0).id)
						.sendOne(new M20467_0(), vo_20467_2);

				vo_8165_2 = new Vo_8165_0();
				vo_8165_2.msg = "有人申请组队，请查看";
				vo_8165_2.active = 0;
				GameObjectCharMng.getGameObjectChar(requestGame.gameTeam.duiwu.get(0).id)
						.sendOne(new M8165_0(), vo_8165_2);
				return;
			}
			//已经有队伍了
			if(gameObjectChar.gameTeam != null) {
				GameUtil.sendMeTips("你已有队伍！");
				return;
			}
			// 如果自己没有队伍，点击创建队伍
			Vo_61593_0 vo_61593_0 = new Vo_61593_0();
			vo_61593_0.ask_type = "invite_join";
			GameObjectChar.send(new M61593_0(), vo_61593_0);

			Vo_TITLE vo_61671_0 = new Vo_TITLE();
			vo_61671_0.id = chara.id;
			vo_61671_0.list.add(2);
			vo_61671_0.list.add(3);
			if(chara.isNameRed == 1) {
				vo_61671_0.list.add(7);
			}
			gameObjectChar.gameMap.send(new MSG_TITLE(), vo_61671_0);

			GameTeam gameTeam = new GameTeam();
			gameTeam.duiwu.add(chara);
			// 更新右侧组队信息
			gameTeam.zhanliduiyuan.add(GameUtil.add4121(chara, 1));
			gameObjectChar.creator(gameTeam);

			List<Chara> duiwu = gameObjectChar.gameTeam.duiwu;
			// 我的队伍
			GameUtil.a4119(duiwu);
			// 更新右侧组队信息
			GameUtil.a4121(gameObjectChar.gameTeam.zhanliduiyuan);

			Vo_20568_0 vo_20568_0 = new Vo_20568_0();
			vo_20568_0.gid = "";
			GameObjectChar.send(new M20568_0(), vo_20568_0);

			// 发送创建队伍成功的消息
			Vo_20480_0 vo_20480_0 = new Vo_20480_0();
			vo_20480_0.msg = "你自己组建了一支队伍。";
			vo_20480_0.time = (int) (System.currentTimeMillis() / 1000L);
			GameObjectChar.send(new M20480_0(), vo_20480_0);
		}

		// 邀请加入
		if ("invite_join".equals(ask_type)) {
			// add tzhang 如果被邀请者本身是队长
			if (requestGame.gameTeam != null
					&& requestGame.gameTeam.duiwu != null
					&& requestGame.gameTeam.duiwu.size() > 0) {
				Vo_8165_0 vo_8165_2 = new Vo_8165_0();
				vo_8165_2.msg = "对方已有队伍了！";
				vo_8165_2.active = 0;
				GameObjectChar.send(new M8165_0(), vo_8165_2);
				return;
			}
			if(requestGame.chara.taskMap.get("萝卜桃子大收集") != null) {
				GameCommonUtil.sendTips("#Y"+requestGame.chara.name+"#n领取了萝卜桃子大收集任务，无法邀请组队");
				return;
			}
			// 如果邀请者自己还处于未组队状态，则自己以他为队长创建一个队伍
			if (gameObjectChar.gameTeam == null) {
				Vo_61593_0 vo_61593_0 = new Vo_61593_0();
				vo_61593_0.ask_type = "invite_join";
				GameObjectChar.send(new M61593_0(), vo_61593_0);
				// 邀请者创建一支队伍
				Vo_TITLE vo_61671_0 = new Vo_TITLE();
				vo_61671_0.id = chara.id;
				vo_61671_0.list.add(3);
				if(chara.isNameRed == 1) {
					vo_61671_0.list.add(7);
				}
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
			// 如果当前对象所在的队伍人员数量已达到上限，则直接返回
			else if (gameObjectChar.gameTeam.zhanliduiyuan.size()>= 5) {
				Vo_8165_0 vo_8165_3 = new Vo_8165_0();
				vo_8165_3.msg = "队伍已满，无法邀请";
				vo_8165_3.active = 0;
				GameObjectChar.send(new M8165_0(), vo_8165_3);
				return;
			}
			// 其他情况
			List<Vo_FRIEND_ADD_CHAR> vo_61545_0List = GameUtil.a61545(chara);
			GameObjectChar.send(new MSG_FRIEND_ADD_CHAR(), vo_61545_0List);

			Vo_8165_0 vo_8165_3 = new Vo_8165_0();
			vo_8165_3.msg = "你已发出邀请，请耐心等待";
			vo_8165_3.active = 0;
			GameObjectChar.send(new M8165_0(), vo_8165_3);

			// 邀请对方组队
			GameUtil.addInvitationChara(chara, id, ask_type);

			// 发送邀请通知
			vo_8165_3.msg = "#Y" + chara.name + "#n邀请你加入其队伍，请打开队伍界面查看邀请信息。";
			vo_8165_3.active = 0;
			requestGame.sendOne(new M8165_0(), vo_8165_3);
			//新版邀请可能会有问题
			requestGame.askType = "invite_join";
		}
	}

	@Override
	public int cmd() {
		return 4156;
	}
}