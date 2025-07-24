package com.fengshen.server.process.friend;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.fengshen.db.domain.Characters;
import com.fengshen.db.domain.Friend;
import com.fengshen.server.data.GameReadTool;
import com.fengshen.server.data.write.friend.MSG_FRIEND_REMOVE_CHAR;
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
 * 删除好友
 * 
 *
 */
@Service
@Slf4j
public class CMD_REMOVE_FRIEND implements GameHandler {

	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		//需要删除好友所在的分组
		String group = GameReadTool.readString(buff);
		//删除的好友名字
		String name = GameReadTool.readString(buff);
		//删除好友
		Chara chara = GameObjectChar.getGameObjectChar().chara;
		Example example = new Example(Friend.class);
		example.createCriteria().andEqualTo("friendName", name).andEqualTo("gid", chara.uuid).andEqualTo("groupId", group);
		GameData.that.friendService.deleteByExample(example);
		//删除好友
		Characters delChar = GameData.that.baseCharactersService.findOneByName(name);
		Map<String,String> obj = new HashMap<>();
		obj.put("groupBuf", group);
		obj.put("charBuf", name);
		obj.put("gid", delChar.getGid());
		//通知客户端刷新列表
		GameObjectChar.send(new MSG_FRIEND_REMOVE_CHAR(), obj);
		GameCommonUtil.dialogOk(String.join("", "你删除了好友#Y", name,"，你与#Y", name,"#W之间的友好度消失。"));
		log.info("{},删除好友({})",chara.name,delChar.getName());
		
	}

	@Override
	public int cmd() {
		return 0x2068;
	}

}
