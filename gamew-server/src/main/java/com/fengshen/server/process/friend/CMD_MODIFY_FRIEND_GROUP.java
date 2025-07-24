package com.fengshen.server.process.friend;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.fengshen.db.domain.Friend;
import com.fengshen.db.domain.FriendGroup;
import com.fengshen.server.data.GameReadTool;
import com.fengshen.server.data.write.friend.MSG_FRIEND_REFRESH_GROUP;
import com.fengshen.server.domain.Chara;
import com.fengshen.server.game.GameCommonUtil;
import com.fengshen.server.game.GameData;
import com.fengshen.server.game.GameHandler;
import com.fengshen.server.game.GameObjectChar;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import tk.mybatis.mapper.entity.Example;

/**
 * 修改好友分组名字
 * 
 *
 */
@Service
@Slf4j
public class CMD_MODIFY_FRIEND_GROUP implements GameHandler {

	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		String groupId = GameReadTool.readString(buff);
		String newName = GameReadTool.readString(buff);
		log.info("修改好友分组名字----groupId={},newName={}",groupId,newName);
		Chara chara = GameObjectChar.getGameObjectChar().chara;
		//修改该玩家这个分组所有的好友并的分组信息
		Example friendExample = new Example(Friend.class);
		friendExample.createCriteria().andEqualTo("groupId", groupId).andEqualTo("gid", chara.uuid);
		Friend friend = new Friend();
		friend.setGroupName(newName);
		friend.setUpdateTime(new Date());
		GameData.that.friendService.updateByExampleSelective(friend, friendExample);
		//修改该用户分组信息
		Example groupExample = new Example(FriendGroup.class);
		groupExample.createCriteria().andEqualTo("gid", chara.uuid).andEqualTo("groupId", groupId);
		FriendGroup friendGroup = new FriendGroup();
		friendGroup.setName(newName);
		friendGroup.setUpdateTime(new Date());
		GameData.that.friendGroupService.updateByExampleSelective(friendGroup, groupExample);
		//通知客户端修改分组名称
		GameObjectChar.send(new MSG_FRIEND_REFRESH_GROUP(), new String[] {groupId,newName});
		GameCommonUtil.dialogOk("好友分组修改成功。");
		
	}

	@Override
	public int cmd() {
		return 0xB08F;
	}

}
