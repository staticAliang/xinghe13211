package com.fengshen.server.process.friend;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.fengshen.db.domain.FriendGroup;
import com.fengshen.server.data.GameReadTool;
import com.fengshen.server.data.vo.friend.Vo_FRIEND_ADD_GROUP;
import com.fengshen.server.data.write.friend.MSG_FRIEND_ADD_GROUP;
import com.fengshen.server.domain.Chara;
import com.fengshen.server.game.GameCommonUtil;
import com.fengshen.server.game.GameData;
import com.fengshen.server.game.GameHandler;
import com.fengshen.server.game.GameObjectChar;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import tk.mybatis.mapper.entity.Example;


/**
 * 添加好友分组
 * @author weilian
 *
 */
@Component
public class CMD_ADD_FRIEND_GROUP implements GameHandler{

	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		
		Chara chara = GameObjectChar.getGameObjectChar().chara;
		String groupName = GameReadTool.readString(buff);
		//判断该分组是否有重复
		Example example = new Example(FriendGroup.class);
		example.createCriteria().andEqualTo("name", groupName).andEqualTo("gid", chara.uuid);
		if(GameData.that.friendGroupService.selectCountByExample(example)>0) {
			GameCommonUtil.dialogOk("与其他分组名称冲突。");
			return;
		}
		//最大分组序号
		Map<String,String> orginGroupNo = new LinkedHashMap<String,String>();
		orginGroupNo.put("2", "");
		orginGroupNo.put("3", "");
		orginGroupNo.put("4", "");
		orginGroupNo.put("7", "");
		orginGroupNo.put("8", "");
		//默认从2开始
		String groupId = "2";
		//获取当前玩家的所有分组
		List<FriendGroup> selectAll = GameData.that.friendGroupService.getFriendGroupsByGid(chara.getUuid());
		for(FriendGroup f:selectAll) {
			if(orginGroupNo.get(f.getGroupId()) != null) {
				orginGroupNo.remove(f.getGroupId());
			}
		}
		//排序
		for(Map.Entry<String, String> m:orginGroupNo.entrySet()) {
			groupId = m.getKey();
			break;
		}
		GameObjectChar.send(new MSG_FRIEND_ADD_GROUP(), new Vo_FRIEND_ADD_GROUP(groupId, groupName));
		//把分组保存到好友分组表
		FriendGroup friendGroup = new FriendGroup(groupName, groupId);
		friendGroup.setAddTime(new Date());
		friendGroup.setGid(chara.uuid);
		GameData.that.friendGroupService.insertSelective(friendGroup);
		GameCommonUtil.dialogOk("创建分组成功。");
	}

	@Override
	public int cmd() {
		return 0xB07A;
	}

}
