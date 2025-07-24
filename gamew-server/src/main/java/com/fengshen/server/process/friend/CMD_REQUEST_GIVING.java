package com.fengshen.server.process.friend;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.fengshen.db.domain.Friend;
import com.fengshen.server.data.GameReadTool;
import com.fengshen.server.data.vo.friend.Vo_REQUEST_GIVING;
import com.fengshen.server.data.write.friend.MSG_REQUEST_GIVING;
import com.fengshen.server.domain.Chara;
import com.fengshen.server.game.GameCommonUtil;
import com.fengshen.server.game.GameData;
import com.fengshen.server.game.GameHandler;
import com.fengshen.server.game.GameObjectChar;
import com.fengshen.server.game.GameObjectCharMng;
import com.fengshen.server.game.GameUtil;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import tk.mybatis.mapper.entity.Example;

/**
 * a请求赠送
 * 
 *
 */
@Service
@Slf4j
public class CMD_REQUEST_GIVING implements GameHandler {

	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		//好友gid
		String gid = GameReadTool.readString(buff);
		log.info("好友赠送:gid={}",gid);
		GameObjectChar gameObjectChar = GameObjectChar.getGameObjectChar();
		Chara chara = gameObjectChar.chara;
		GameObjectChar friendGameObjectChar = GameObjectCharMng.getGameObjectCharByUUid(gid);
		if(friendGameObjectChar == null) {
			GameUtil.sendMeTips("对方不在线！");
			return;
		}
		
		JSONObject givingConfig = GameCommonUtil.isGivingItem(chara, null);
		if(givingConfig == null) {
			return;
		}
		//判断今天赠送是否达到次数
		if(chara.sendGivingCount+1>givingConfig.getIntValue("sendGivingCount")) {
			GameUtil.sendMeTips("你今日已无赠送次数");
			return;
		}
		Chara friendChara = friendGameObjectChar.chara;
		//好友度是否达到要求
		Example example = new Example(Friend.class);
		example.createCriteria().andEqualTo("gid", chara.uuid).andEqualTo("friendGid", friendGameObjectChar.chara.uuid);
		Friend friend = GameData.that.friendService.selectOneByExample(example);
		if(friend == null) {
			GameUtil.sendMeTips("你和对方还不是好友呢");
			return;
		}
		if(chara.sendGivingCount == 0) {
			if(friend.getFriendScore() < 5000) {
				GameUtil.sendMeTips("你和对方好友度小于5000无法赠送");
				return;
			}
		}else if(chara.sendGivingCount == 1) {
			if(friend.getFriendScore() < 30000) {
				GameUtil.sendMeTips("你和对方好友度小于30000无法赠送");
				return;
			}
		}else if(chara.sendGivingCount == 2) {
			if(friend.getFriendScore() < 120000) {
				GameUtil.sendMeTips("你和对方好友度小于120000无法赠送");
				return;
			}
		}
		
		Vo_REQUEST_GIVING vo = new Vo_REQUEST_GIVING();
		vo.setGiverIcon(chara.waiguan);
		vo.setGiverLevel(chara.level);
		vo.setGiverName(chara.name);
		vo.setReceiveName(friendChara.name);
		vo.setReceiveLevel(friendChara.level);
		vo.setReceiveIcon(friendChara.waiguan);
		gameObjectChar.sendOne(new MSG_REQUEST_GIVING(), vo);
		friendGameObjectChar.sendOne(new MSG_REQUEST_GIVING(), vo);
		//对方id
		gameObjectChar.receiverId = friendGameObjectChar.chara.id;
		//对方id
		friendGameObjectChar.receiverId = chara.id;
	}

	@Override
	public int cmd() {
		return 0xD084;
	}

}
