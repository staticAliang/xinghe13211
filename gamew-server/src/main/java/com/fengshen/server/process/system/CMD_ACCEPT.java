package com.fengshen.server.process.system;

import java.util.Date;
import java.util.List;
import java.util.Map.Entry;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.fengshen.db.domain.Characters;
import com.fengshen.db.domain.Dialog;
import com.fengshen.db.domain.Party;
import com.fengshen.db.domain.PartyMember;
import com.fengshen.server.data.GameReadTool;
import com.fengshen.server.data.constant.PartyType;
import com.fengshen.server.data.vo.Vo_20480_0;
import com.fengshen.server.data.vo.Vo_20568_0;
import com.fengshen.server.data.vo.Vo_61591_0;
import com.fengshen.server.data.vo.Vo_61593_0;
import com.fengshen.server.data.vo.Vo_TITLE;
import com.fengshen.server.data.vo.Vo_UPDATE_MOVE_SPEED;
import com.fengshen.server.data.vo.friend.Vo_FRIEND_ADD_CHAR;
import com.fengshen.server.data.write.M20480_0;
import com.fengshen.server.data.write.M20568_0;
import com.fengshen.server.data.write.M61591_0;
import com.fengshen.server.data.write.M61593_0;
import com.fengshen.server.data.write.MSG_TITLE;
import com.fengshen.server.data.write.MSG_UPDATE_MOVE_SPEED;
import com.fengshen.server.data.write.friend.MSG_FRIEND_ADD_CHAR;
import com.fengshen.server.data.write.user.MSG_UPDATE_APPEARANCE;
import com.fengshen.server.domain.Chara;
import com.fengshen.server.game.GameCommonUtil;
import com.fengshen.server.game.GameCore;
import com.fengshen.server.game.GameData;
import com.fengshen.server.game.GameHandler;
import com.fengshen.server.game.GameLine;
import com.fengshen.server.game.GameObjectChar;
import com.fengshen.server.game.GameObjectCharMng;
import com.fengshen.server.game.GamePartyUtil;
import com.fengshen.server.game.GameTeamUtil;
import com.fengshen.server.game.GameUtil;
import com.qcloud.cos.utils.StringUtils;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import tk.mybatis.mapper.entity.Example;

/**
 * 同意操作
 * 
 * 
 *
 */
@Service
@Slf4j
public class CMD_ACCEPT implements GameHandler {
	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		String peer_name = GameReadTool.readString(buff);
		String ask_type = GameReadTool.readString(buff);
		log.info("同意操作, peer_name={},ask_type={}",peer_name,ask_type);
		GameObjectChar gameObjectChar = GameObjectChar.getGameObjectChar();
		//如果当前对象为空,就没必要继续往下进行了
		if(gameObjectChar == null) {
			return;
		}
		Chara chara = gameObjectChar.chara;
		Characters characters = null;
		Chara toChara = null;
		GameObjectChar toGameObjectChar = GameObjectCharMng.getGameObjectChar(peer_name);
		if(toGameObjectChar != null) {
			toChara = toGameObjectChar.chara;
			characters = toGameObjectChar.characters;
		}
		// 加入帮派操作
		if ("party_remote".equals(ask_type) || "party_invite".equals(ask_type) || "party".equals(ask_type)) {
			if (toChara == null) {
				characters = GameData.that.characterService.findOneByNameSelectProperties(peer_name, "id", "level", "name", "gid", "polar", "data");
				if(characters == null) {
					return;
				}
				toChara = JSONObject.parseObject(characters.getData(),Chara.class);
				toChara.setLevel(characters.getLevel());
				toChara.setPolar(characters.getPolar());
				toChara.setName(characters.getName());
				toChara.setUuid(characters.getGid());
				toChara.setId(characters.getId());
			}
			// 清空请求
			Vo_61591_0 vo_61591_0 = new Vo_61591_0();
			vo_61591_0.name = peer_name;
			vo_61591_0.ask_type = ask_type;
			GameObjectChar.send(new M61591_0(), vo_61591_0);
			// 删除记录
			Example example = new Example(Dialog.class);
			example.createCriteria().andEqualTo("applyGid", toChara.uuid).andEqualTo("peerName", chara.getPartyName())
					.andEqualTo("askType", "party");
			GameData.that.dialogService.deleteByExample(example);
			if (!toChara.getPartyName().isEmpty()) {
				GameUtil.sendMeTips(StringUtils.join("#Y" , toChara.name , "#n已有帮派"));
				return;
			} else {
				// 判断帮派人数是否已满.
				Party getParty = GameData.that.partyService.findByPartyName(chara.getPartyName());
				if ((getParty.getPopulation() + 1) > GamePartyUtil.getPartyMaxPopulation(getParty.getPartyLevel())) {
					GameUtil.sendMeTips("贵帮人数已满");
					return;
				}
				// 为这个帮派添加成员
				PartyMember partyMember = new PartyMember();
				partyMember.setCharaId(toChara.id);
				Party party = GameCore.partyMap.get(chara.getPartyName());
				partyMember.setPartyId(party.getPartyId());
				partyMember.setJob("帮众" + ":" + PartyType.getKeyByValue("帮众"));
				partyMember.setName(toChara.name);
				partyMember.setPolar(toChara.polar);
				partyMember.setCharaGid(toChara.uuid);
				partyMember.setCreateTime(new Date());
				GameData.that.partyMemberService.insertSelective(partyMember);
				getParty.setPopulation(getParty.getPopulation() + 1);
				GameData.that.partyService.updateByPrimaryKeySelective(getParty);
				// 刷新缓存信息
				GameCore.partyMap.put(chara.getPartyName(), getParty);
				toChara.setPartyJob("帮众");
				toChara.setPartyName(party.getPartyName());
				// 如果加入是上个帮派的话,帮贡就恢复
				if (toChara.getUpPartyName().equals(party.getPartyName())) {
					toChara.contrib *= 2;
				}
				if (toGameObjectChar != null) {
					GameUtil.sendUpdate(toChara);
					// 刷新图标
					GamePartyUtil.partyIcon(toChara);
					String msg =  StringUtils.join(party.getPartyName() , "帮帮众");
					GameUtil.chenghaoxiaoxi(toChara, msg, msg);
					GameCommonUtil.sendTips(StringUtils.join("你已加入#Y" , party.getPartyName() , "#n帮派。"), toChara.id);
					GameCommonUtil.sendTips(StringUtils.join("你获得了#R" , party.getPartyName() ,"帮众#n的称谓。"), toChara.id);
				} else {
					// 不在线操作
					String name = StringUtils.join(party.getPartyName() , "帮帮众");
					toChara.chenghao.put(name, name);
					characters.setData(JSONObject.toJSONString(toChara));
					GameData.that.baseCharactersService.updateById(characters);
				}
				// 清除上个帮派
				toChara.upPartyName = "";
				//欢迎语
				GamePartyUtil.notifyPartyMsg(toChara.getPartyName(), StringUtils.join("热烈欢迎#Y",toChara.getName(),"#n加入帮派#50m"));
			}
			return;
		}
		
		//以下是组队邀请
		if(toGameObjectChar == null) {
			Vo_61591_0 vo_61591_0 = new Vo_61591_0();
			vo_61591_0.ask_type = ask_type;
			vo_61591_0.name = peer_name;
			GameObjectChar.send(new M61591_0(), vo_61591_0);
			GameUtil.sendMeTips("该玩家不在线！");
			//清除自己的邀请找出这个人
			for(Entry<Integer, Chara> invitation:gameObjectChar.invitationCharas.entrySet()) {
				if(invitation.getValue().name.equals(peer_name)) {
					gameObjectChar.invitationCharas.remove(invitation.getValue().id);
					break;
				}
			}
			return;
		}
		if(chara.mapid == 38004) {
			GameUtil.sendMeTips("当前地图不支持此操作.");
			return;
		} else if (chara.mapName.equals("瑶池")||chara.mapName.equals("桐柏山") || 
				chara.mapName.equals("黑风洞一层") || chara.mapName.equals("黑风洞二层")
				|| chara.mapName.equals("黑风洞三层") || chara.mapName.equals("兰若寺后山") 
				|| chara.mapName.equals("兰若寺") || chara.mapName.equals("烈火涧")
				|| chara.mapName.equals("烈火涧西面") || chara.mapName.equals("烈火涧北面")
				|| chara.mapName.equals("烈火涧东面") || chara.mapName.equals("飘渺仙府")
				|| chara.mapName.equals("仙府秘境") || chara.mapName.equals("仙府大殿")) {
			GameUtil.sendMeTips("正在任务，无法进行组队.");
			return;
		}else if(chara.taskMap.get("坐牢") != null) {
			GameUtil.sendMeTips("正在坐牢,无法操作。");
			return;
		}
		//新版本客户端不知道为什么会有问题
		if(gameObjectChar.askType.equals("invite_join")) {
			gameObjectChar.askType = "";
			ask_type = "invite_join";
		}
		//申请加入队伍，队长点击确认按钮
		if ("request_join".equals(ask_type)) {
			GameTeamUtil.requestJoin(gameObjectChar, toGameObjectChar, peer_name);
		} else if("invite_join".equals(ask_type)){//同意别人邀请加入队伍.
			if (toGameObjectChar != null && !GameCommonUtil.isNotGameTeam(toGameObjectChar.gameTeam)) {
				return;
			}
			//防止多次点击
			if(gameObjectChar.gameTeam != null) {
				GameUtil.sendMeTips("你已有队伍了。");
				return;
			}
			//拿对方队长的信息
			toChara = toGameObjectChar.gameTeam.duiwu.get(0);
			//点击后无论如何先清空状态
			Vo_61591_0 vo_61591_2 = new Vo_61591_0();
			vo_61591_2.ask_type = ask_type;
			vo_61591_2.name = peer_name;
			GameObjectChar.send(new M61591_0(), vo_61591_2);
			//组队邀请
			if(toChara.mapid == 38004) {
				GameUtil.sendMeTips("#Y"+toChara.name+"#n处于试道场内无法加入他队伍。");
				gameObjectChar.gameTeam = null;
				return;
			}
			if(toChara.isFight) {
				GameUtil.sendMeTips("#Y"+toChara.name+"#n正忙无法加入。");
				gameObjectChar.gameTeam = null;
				return;
			}
			if(chara.isFight) {
				GameUtil.sendMeTips("战斗中无法操作");
				gameObjectChar.gameTeam = null;
				return;
			}
			// 这里的逻辑应该是，如果原来邀请者的队伍已经为空了，就将本次自己的队伍也置为空
			if (toGameObjectChar != null && toGameObjectChar.gameTeam == null) {
				gameObjectChar.gameTeam.duiwu = null;
				Vo_61593_0 vo_61593_2 = new Vo_61593_0();
				vo_61593_2.ask_type = ask_type;
				GameObjectChar.send(new M61593_0(), vo_61593_2);

				Vo_20568_0 vo_20568_2 = new Vo_20568_0();
				vo_20568_2.gid = "";
				GameObjectChar.send(new M20568_0(), vo_20568_2);
				return;
			}
			
			int requestTeamSize = toGameObjectChar.gameTeam.zhanliduiyuan.size();
			if(requestTeamSize >= 5) {
				GameUtil.sendMeTips("对方队伍人数已满,无法己加入。");
				if(gameObjectChar.gameTeam == null) {
					gameObjectChar.gameTeam = null;
				}
				return;
			}
			
			List<Vo_FRIEND_ADD_CHAR> vo_61545_0List = GameUtil.a61545(chara);
			GameObjectChar.send(new MSG_FRIEND_ADD_CHAR(), vo_61545_0List);


			Vo_61593_0 vo_61593_3 = new Vo_61593_0();
			vo_61593_3.ask_type = ask_type;
			GameObjectChar.send(new M61593_0(), vo_61593_3);

			Vo_20480_0 vo_20480_0 = new Vo_20480_0();
			vo_20480_0.msg = "你加入了#Y"+peer_name+"#n的队伍";
			vo_20480_0.time = (int) (System.currentTimeMillis() / 1000);
			GameObjectChar.send(new M20480_0(), vo_20480_0);

			Vo_TITLE vo_61671_0 = new Vo_TITLE();
			vo_61671_0.id = chara.id;
			vo_61671_0.list.add(2);
			vo_61671_0.list.add(5);
			if(chara.isNameRed == 1) {
				vo_61671_0.list.add(7);
			}
			GameObjectChar.send(new MSG_TITLE(), vo_61671_0);

			toGameObjectChar.gameTeam.duiwu.add(chara);
			toGameObjectChar.gameTeam.zhanliduiyuan.add(GameUtil.add4121(chara, 1));
			gameObjectChar.gameTeam = toGameObjectChar.gameTeam;
			gameObjectChar.gameTeam.liebiao.clear();
			if(chara.mapid != toGameObjectChar.chara.mapid) {
				//加入队员
				GameLine.getGameMap(chara.line, chara.mapName).joinduiyuan(gameObjectChar, toGameObjectChar.chara);
			}

			List<Chara> charas = toGameObjectChar.gameTeam.duiwu;
			GameUtil.a4119(charas);
			GameUtil.a4121(toGameObjectChar.gameTeam.zhanliduiyuan);

			Vo_20568_0 vo_20568_0 = new Vo_20568_0();
			vo_20568_0.gid = "";
			GameObjectChar.send(new M20568_0(), vo_20568_0);

			vo_61671_0 = new Vo_TITLE();
			vo_61671_0.id = toChara.id;
			vo_61671_0.list.add(2);
			if(toGameObjectChar.gameTeam.zhanliduiyuan.size()==5) {
				vo_61671_0.list.add(4);
			}else {
				vo_61671_0.list.add(3);
			}
			gameObjectChar.gameMap.send(new MSG_TITLE(), vo_61671_0);
			//已经加入队伍就清空所有的消息
			gameObjectChar.invitationCharas = null;
			
			//设置移动速度为队长速度
			log.info("同意队长邀请，队长移动速度:{}",toGameObjectChar.chara.yidongsudu);
			Vo_UPDATE_MOVE_SPEED vo_45177_0 = new Vo_UPDATE_MOVE_SPEED();
			vo_45177_0.id = chara.id;
			vo_45177_0.moveSpeedPercent = toGameObjectChar.chara.yidongsudu;
			gameObjectChar.gameMap.send(new MSG_UPDATE_MOVE_SPEED(), vo_45177_0);
			
			GameCommonUtil.flyInit(gameObjectChar);
			//通知地图所有人加载飞行器
			for(GameObjectChar notify:gameObjectChar.gameMap.sessionList) {
				notify.gameMap.send(new MSG_UPDATE_APPEARANCE(), GameUtil.a61661(notify.chara));
			}
			
			vo_20480_0 = new Vo_20480_0();
			vo_20480_0.msg = "#Y"+chara.name+"#n加入队伍中了。";
			vo_20480_0.time = (int) (System.currentTimeMillis() / 1000);
			for(Chara team:gameObjectChar.gameTeam.duiwu) {
				if(team.id != chara.id) {
					GameObjectChar.send(new M20480_0(), vo_20480_0, team.id);
				}
			}
		}
	}

	@Override
	public int cmd() {
		return 4132;
	}
}