package com.fengshen.server.process.user;

import org.springframework.stereotype.Service;

import com.fengshen.db.domain.Friend;
import com.fengshen.server.data.GameReadTool;
import com.fengshen.server.data.vo.Vo_36871_0;
import com.fengshen.server.data.vo.Vo_53569_0;
import com.fengshen.server.data.vo.friend.Vo_FRIEND_ADD_CHAR;
import com.fengshen.server.data.write.M36871_0;
import com.fengshen.server.data.write.M53569_0;
import com.fengshen.server.data.write.friend.MSG_FRIEND_NOTIFICATION;
import com.fengshen.server.data.write.user.MSG_CHAR_INFO_EX;
import com.fengshen.server.domain.Chara;
import com.fengshen.server.game.GameCommonUtil;
import com.fengshen.server.game.GameCore;
import com.fengshen.server.game.GameData;
import com.fengshen.server.game.GameHandler;
import com.fengshen.server.game.GameObjectChar;
import com.fengshen.server.game.GameObjectCharMng;
import com.fengshen.server.util.GameConfig;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import tk.mybatis.mapper.entity.Example;

/**
 * 获取角色名片信息
 * 
 *
 */
@Service
@Slf4j
public class CMD_GET_CHAR_INFO implements GameHandler {
	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		String char_gid = GameReadTool.readString(buff);
		String dlg_type = GameReadTool.readString(buff);
		int offline = GameReadTool.readByte(buff);
		String para = GameReadTool.readString(buff);
		String user_dist = GameReadTool.readString(buff);
		log.info("获取角色名片信息, dlg_type={},offline={},para={},user_dist={}",
				dlg_type,offline,para,user_dist);
		GameObjectChar gameObject = GameObjectCharMng.getGameObjectCharByUUid(char_gid);
		Chara thisChara = GameObjectChar.getGameObjectChar().chara;
		String[] split = char_gid.split("\\|");
		if(split.length>1 && gameObject == null) {
			//其他人的名片
			gameObject = GameObjectCharMng.getGameObjectCharByUUid(split[0]);
		}
		if (gameObject == null) {
			// 不在线也要数据
			Vo_53569_0 vo_53569_0 = new Vo_53569_0();
			vo_53569_0.gid = char_gid;
			vo_53569_0.online = 2;
			GameObjectChar.send(new M53569_0(), vo_53569_0);
			return;
		}
		Chara chara = gameObject.chara;
		Example example = new Example(Friend.class);
		example.createCriteria().andEqualTo("gid", thisChara.uuid).andEqualTo("friendGid", chara.uuid);
		Friend friend = GameData.that.friendService.selectOneByExample(example);
		Vo_36871_0 vo_36871_0 = new Vo_36871_0();
		vo_36871_0.msg_type = "";
		vo_36871_0.icon = chara.waiguan;
		vo_36871_0.id = chara.id;
		vo_36871_0.level = chara.level;
		vo_36871_0.gid = char_gid;
		vo_36871_0.name = chara.name;
		vo_36871_0.party = chara.getPartyName();
		vo_36871_0.friend_score = friend != null ? friend.getFriendScore() : 0;
		vo_36871_0.setting_flag = 0;
		// 队长
		vo_36871_0.char_status = 1;
		if (GameCommonUtil.isNotGameTeam(GameObjectCharMng.getGameObjectChar(chara.id).gameTeam)
				&& GameObjectCharMng.getGameObjectChar(chara.id).gameTeam.duiwu.get(0).id == thisChara.id) {
			// 队长状态
			vo_36871_0.char_status = 3;
		} else if (GameCommonUtil.isNotGameTeam(GameObjectCharMng.getGameObjectChar(chara.id).gameTeam)) {
			//如果是组队状态并且还在战斗
			if (chara.isFight) {
				// 观战
				vo_36871_0.char_status = 6;
			}else {
				// 组队中
				vo_36871_0.char_status = 2;
			}
		}else if(chara.isFight) {
			vo_36871_0.char_status = 4;
		}
		vo_36871_0.vip = chara.vipType;
		vo_36871_0.serverId = GameCore.getGameLine(chara.line).lineNum + "线";
		vo_36871_0.account = chara.uuid;
		vo_36871_0.polar = chara.polar;
		vo_36871_0.isInThereFrend = friend != null ? 1 : 0;
		vo_36871_0.ringScore = 0;
		vo_36871_0.comeback_flag = 0;
		vo_36871_0.level = chara.level;
		GameObjectChar.send(new M36871_0(), vo_36871_0);
		GameObjectChar.send(new MSG_CHAR_INFO_EX(), vo_36871_0);

		// 刷新好友度
		if(friend != null) {
			Vo_FRIEND_ADD_CHAR vo = new Vo_FRIEND_ADD_CHAR();
			vo.groupBuf = friend.getGroupId();
			vo.arena_rank = friend.getFriendScore();
			vo.online = 1;
			GameCommonUtil.refreshFriend(vo, chara);
		}
		
		GameObjectChar.send(new MSG_FRIEND_NOTIFICATION(),
				new Object[] { chara.name, GameConfig.lineName, 1, chara.vipType });
	}

	@Override
	public int cmd() {
		return 33287;
	}
}
