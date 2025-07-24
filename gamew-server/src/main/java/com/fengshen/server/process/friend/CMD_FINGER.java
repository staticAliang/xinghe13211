package com.fengshen.server.process.friend;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.fengshen.db.domain.FriendGroup;
import com.fengshen.server.data.GameReadTool;
import com.fengshen.server.data.vo.Vo_36871_0;
import com.fengshen.server.data.vo.Vo_53569_0;
import com.fengshen.server.data.vo.friend.Vo_FRIEND_ADD_CHAR;
import com.fengshen.server.data.write.M36871_0;
import com.fengshen.server.data.write.M53569_0;
import com.fengshen.server.data.write.friend.MSG_FINGER;
import com.fengshen.server.data.write.friend.MSG_FRIEND_ADD_CHAR;
import com.fengshen.server.data.write.friend.MSG_FRIEND_NOTIFICATION;
import com.fengshen.server.data.write.friend.MSG_FRIEND_UPDATE_LISTS;
import com.fengshen.server.data.write.user.MSG_CHAR_INFO_EX;
import com.fengshen.server.domain.Chara;
import com.fengshen.server.game.GameCommonUtil;
import com.fengshen.server.game.GameCore;
import com.fengshen.server.game.GameHandler;
import com.fengshen.server.game.GameObjectChar;
import com.fengshen.server.game.GameObjectCharMng;
import com.fengshen.server.game.GameUtil;
import com.fengshen.server.util.GameConfig;
import com.google.common.collect.Lists;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

/**
 * 搜索好友
 */
@Service
@Slf4j
public class CMD_FINGER implements GameHandler {

	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		
		String name = GameReadTool.readString(buff);
		int type = GameReadTool.readByte(buff);
		
		GameObjectChar gameObjectChar = GameObjectCharMng.getGameObjectChar(name);
		if(gameObjectChar == null) {
			GameUtil.sendMeTips("该玩家不在线！");
			return;
		}
		Chara chara = gameObjectChar.chara;
		
		
		List<Vo_FRIEND_ADD_CHAR> l = new ArrayList<>();
		Vo_FRIEND_ADD_CHAR d = new Vo_FRIEND_ADD_CHAR();
		Vo_FRIEND_ADD_CHAR buildFriend = GameCommonUtil.buildFriend(chara, d);
		l.add(buildFriend);
		GameObjectChar.send(new MSG_FINGER(), l);
		
		

		
		log.info("搜索好友, name={},type={}",name,type);
	}

	@Override
	public int cmd() {
		return 0x1072;
	}

}
