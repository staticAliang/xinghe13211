package com.fengshen.server.process.fixedteam;

import com.fengshen.db.domain.Characters;
import com.fengshen.db.domain.FixedTeam;
import com.fengshen.server.game.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.fengshen.server.data.GameReadTool;
import com.fengshen.server.domain.Chara;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class CMD_FIXED_TEAM_ONE_KEY implements GameHandler {

	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		String gid = GameReadTool.readString(buff);
		GameObjectChar gameObjectChar = GameObjectChar.getGameObjectChar();
		Chara chara = gameObjectChar.chara;
		String teamId = chara.fixedTeamName;
		List<Characters> characters = new ArrayList<>();
		if("".equals(gid)) {
			if(!StringUtils.isEmpty(teamId)){
				Example example = new Example(Characters.class);
				example.createCriteria().andEqualTo("fixedTeamName", teamId);
				characters= GameData.that.baseCharactersService.selectByExample(example);
			}
			for (Characters character : characters) {
				if(chara.uuid.equals(character.getGid())){
					continue;
				}
//			//一键组队
//			if("".equals(gid)) {
//
//			}else {
				//指定队员组队
				GameObjectChar toGameObjectChar = GameObjectCharMng.getGameObjectCharByUUid(character.getGid());
				if(toGameObjectChar == null) {
					GameUtil.sendMeTips("该成员不在线");
					return;
				}
				if(GameCommonUtil.isNotGameTeam(toGameObjectChar.gameTeam)) {
					GameUtil.sendMeTips("该成员已有队伍");
					return;
				}
				Chara toChara = toGameObjectChar.chara;
				//有些情况不允许组队
				if(chara.mapid == 38004) {
					GameUtil.sendMeTips("不允许操作");
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
				//对方要是在试道场不允许组队
				if(toChara.mapid == 38004) {
					GameUtil.sendMeTips("不允许操作");
					return;
				}
				//邀请固定队成员入队
				GameTeamUtil.inviteJoinTeam(gameObjectChar, toChara.id);

			}
		}else{
			//指定队员组队
			GameObjectChar toGameObjectChar = GameObjectCharMng.getGameObjectCharByUUid(gid);
			if(toGameObjectChar == null) {
				GameUtil.sendMeTips("该成员不在线");
				return;
			}
			if(GameCommonUtil.isNotGameTeam(toGameObjectChar.gameTeam)) {
				GameUtil.sendMeTips("该成员已有队伍");
				return;
			}
			Chara toChara = toGameObjectChar.chara;
			//有些情况不允许组队
			if(chara.mapid == 38004) {
				GameUtil.sendMeTips("不允许操作");
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
			//对方要是在试道场不允许组队
			if(toChara.mapid == 38004) {
				GameUtil.sendMeTips("不允许操作");
				return;
			}
			//邀请固定队成员入队
			GameTeamUtil.inviteJoinTeam(gameObjectChar, toChara.id);

		}





	}

	@Override
	public int cmd() {
		return 0xD208;
	}

}
