package com.fengshen.server.process.friend;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.fengshen.db.domain.Characters;
import com.fengshen.db.domain.Friend;
import com.fengshen.server.data.GameReadTool;
import com.fengshen.server.data.vo.friend.Vo_FRIEND_MOVE_CHAR;
import com.fengshen.server.data.write.friend.MSG_FRIEND_MOVE_CHAR;
import com.fengshen.server.domain.Chara;
import com.fengshen.server.game.GameCommonUtil;
import com.fengshen.server.game.GameData;
import com.fengshen.server.game.GameHandler;
import com.fengshen.server.game.GameObjectChar;
import com.fengshen.server.game.GameObjectCharMng;
import com.google.common.collect.Lists;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import tk.mybatis.mapper.entity.Example;

/**
 * 移动好友分组
 * 
 *
 */
@Service
@Slf4j
public class CMD_MOVE_FRIEND_GROUP implements GameHandler{

	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		String formGroupId = GameReadTool.readString(buff);
		String toGroupId = GameReadTool.readString(buff);
		String friendGid = GameReadTool.readString2(buff);
		String friendName = GameReadTool.readString2(buff);
		
		log.info("移动好友分组----原始分组ID={},移动到分组Id={}\nfriendGid={},friendName={}",
				formGroupId,toGroupId,friendGid,friendName);
		Chara toChara = null;
		GameObjectChar toGameObject = GameObjectCharMng.getGameObjectCharByUUid(friendGid.replaceAll(";", ""));
		if(toGameObject == null) {
			//查询该用户信息
			Characters findOneByGid2 = GameData.that.baseCharactersService.findOneByGid2(friendGid.replaceAll(";", ""));
			toChara = JSONObject.parseObject(findOneByGid2.getData(),Chara.class);
		}else {
			toChara =  toGameObject.chara;
		}
		//通知客户端移动好友分组
		Vo_FRIEND_MOVE_CHAR move = new Vo_FRIEND_MOVE_CHAR();
		move.setFromId(formGroupId);
		move.setToId(toGroupId);
		move.setGids(Lists.newArrayList(friendGid.replaceAll(";", "")));
		GameObjectChar.send(new MSG_FRIEND_MOVE_CHAR(), move);
		GameCommonUtil.dialogOk("好友移动分组成功。");
		//更新
		Example update = new Example(Friend.class);
		update.createCriteria().andEqualTo("gid", GameObjectChar.getGameObjectChar().chara.uuid)
			.andEqualTo("friendGid", toChara.uuid);
		Friend friend = new Friend();
		friend.setGroupId(toGroupId);
		GameData.that.friendService.updateByExampleSelective(friend, update);
	}

	@Override
	public int cmd() {
		return 0xB082;
	}

}
