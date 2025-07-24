package com.fengshen.server.process.friend;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.fengshen.db.domain.Friend;
import com.fengshen.db.domain.FriendGroup;
import com.fengshen.server.data.GameReadTool;
import com.fengshen.server.data.vo.friend.Vo_FRIEND_MOVE_CHAR;
import com.fengshen.server.data.write.friend.MSG_FRIEND_MOVE_CHAR;
import com.fengshen.server.data.write.friend.MSG_FRINED_REMOVE_GROUP;
import com.fengshen.server.domain.Chara;
import com.fengshen.server.game.GameData;
import com.fengshen.server.game.GameHandler;
import com.fengshen.server.game.GameObjectChar;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import tk.mybatis.mapper.entity.Example;

/**
 * 删除一个好友分组
 * 
 *
 */
@Service
@Slf4j
public class CMD_REMOVE_FRIEND_GROUP implements GameHandler {

	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		String groupId = GameReadTool.readString(buff);
		log.info("删除一个好友分组, groupId={}", groupId);
		//获取到该分组下的所有好友并移动到默认分组(我的好友中去)
		Chara chara = GameObjectChar.getGameObjectChar().chara;
		//通知客户端删除分组
		GameObjectChar.send(new MSG_FRINED_REMOVE_GROUP(), groupId);
		Example example = new Example(Friend.class);
		example.createCriteria().andEqualTo("gid", chara.uuid).andEqualTo("groupId", groupId);
		List<Friend> friends = GameData.that.friendService.selectByExample(example);
		if(friends != null && !friends.isEmpty()) {
			List<String> gids = new ArrayList<>();
			for(Friend f:friends) {
				gids.add(f.getFriendGid());
			}
			//通知客户端移动好友分组
			Vo_FRIEND_MOVE_CHAR move = new Vo_FRIEND_MOVE_CHAR();
			move.setFromId(groupId);
			move.setToId("1");
			move.setGids(gids);
			GameObjectChar.send(new MSG_FRIEND_MOVE_CHAR(), move);
		}
		
		//删除分组
		Example delExample = new Example(FriendGroup.class);
		delExample.createCriteria().andEqualTo("gid", GameObjectChar.getGameObjectChar().chara.uuid).andEqualTo("groupId", groupId);
		GameData.that.friendGroupService.deleteByExample(example);
	}
	
	@Override
	public int cmd() {
		return 0xB07C;
	}

}
