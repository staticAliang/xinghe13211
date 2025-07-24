package com.fengshen.server.process.friend;

import org.springframework.stereotype.Service;

import com.fengshen.db.domain.Friend;
import com.fengshen.db.domain.FriendGroup;
import com.fengshen.db.domain.MailboxRefresh;
import com.fengshen.server.data.GameReadTool;
import com.fengshen.server.data.constant.SysMsgType;
import com.fengshen.server.data.vo.Vo_MAILBOX_REFRESH;
import com.fengshen.server.data.vo.friend.Vo_BE_ADD_FRIEND;
import com.fengshen.server.data.write.M36871_0;
import com.fengshen.server.data.write.friend.MSG_BE_ADD_FRIEND;
import com.fengshen.server.data.write.friend.MSG_FRIEND_UPDATE_LISTS;
import com.fengshen.server.data.write.system.MSG_MAILBOX_REFRESH;
import com.fengshen.server.domain.Chara;
import com.fengshen.server.game.GameCommonUtil;
import com.fengshen.server.game.GameData;
import com.fengshen.server.game.GameHandler;
import com.fengshen.server.game.GameObjectChar;
import com.fengshen.server.game.GameObjectCharMng;
import com.fengshen.server.game.GameUtil;
import com.google.common.collect.Lists;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import tk.mybatis.mapper.entity.Example;

/**
 * 好友验证结果
 * 
 *
 */
@Service
@Slf4j
public class CMD_FRIEND_VERIFY_RESULT implements GameHandler {

	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {

		//要添加人的gid
		String verifyId = GameReadTool.readString(buff);
		//被添加人的名字
		String charName = GameReadTool.readString(buff);
		//自己的gid
		String gid = GameReadTool.readString(buff);
		int result = GameReadTool.readByte(buff);
		//同意
		if(result == 1) {
			//查询要添加的人是否在线.
			GameObjectChar toGameObject = GameObjectCharMng.getGameObjectCharByUUid(verifyId);
			if(toGameObject == null) {
				GameUtil.sendMeTips("对方不在线。");
				return;
			}
			//被添加的人
			Chara toChara = toGameObject.chara;
			//自己
			Chara chara = GameObjectChar.getGameObjectChar().chara;
			//查询是否已经添加为好友
			Example isAddFriendExample = new Example(Friend.class);
			isAddFriendExample.createCriteria().andEqualTo("gid", 
					GameObjectChar.getGameObjectChar().chara.uuid).andEqualTo("friendGid", toChara.uuid);
			int isAddFriend = GameData.that.friendService.selectCountByExample(isAddFriendExample);
			if(isAddFriend > 0) {
				GameUtil.sendMeTips("请勿重复添加");
				return;
			}
			//发送消息到对方提示有人加他为好友
			Vo_BE_ADD_FRIEND vo_BE_ADD_FRIEND = new Vo_BE_ADD_FRIEND();
			vo_BE_ADD_FRIEND.setGid(chara.getUuid());
			vo_BE_ADD_FRIEND.setName(chara.getName());
			vo_BE_ADD_FRIEND.setSetting(0);
			GameObjectCharMng.getGameObjectChar(toChara.id).sendOne(new MSG_BE_ADD_FRIEND(), vo_BE_ADD_FRIEND);
			//添加好友
			GameCommonUtil.addFriend(chara, toChara);
			//刷新好友列表
			GameObjectChar.send(new MSG_FRIEND_UPDATE_LISTS(), GameCommonUtil.createFriends(chara, Lists.newArrayList(new FriendGroup("我的好友", "1"))), chara.id);
			//刷新我在好友那边的信息
			GameObjectChar.send(new M36871_0(), GameCommonUtil.getCharaInfo(chara), toChara.id);
			//刷新好友在我这边的信息
			GameObjectChar.send(new M36871_0(), GameCommonUtil.getCharaInfo(toChara), chara.id);
		}
		final Vo_MAILBOX_REFRESH vo_40961_0 = new Vo_MAILBOX_REFRESH();
		vo_40961_0.count = 1;
		vo_40961_0.id = verifyId;
		vo_40961_0.type = SysMsgType.FRIEND_CHECK.getValue();
		vo_40961_0.sender = "";
		vo_40961_0.title = "";
		vo_40961_0.msg = "";
		vo_40961_0.attachment = "";
		vo_40961_0.create_time = (int) (System.currentTimeMillis() / 1000L);
		vo_40961_0.expired_time = (int) (System.currentTimeMillis() / 1000L) + 1000000;
		vo_40961_0.status = 3;
		GameObjectChar.send(new MSG_MAILBOX_REFRESH(), Lists.newArrayList(vo_40961_0));
		//删除这条记录
		Example example = new Example(MailboxRefresh.class);
		example.createCriteria().andEqualTo("gid", verifyId).andEqualTo("title", gid);
		GameData.that.mailboxRefreshService.deleteByExample(example);
		log.info("好友验证结果，gid:{},\nverifyId={},charName={},result={}",gid,verifyId,charName,result);
	}

	@Override
	public int cmd() {
		return 0x9004;
	}

}
