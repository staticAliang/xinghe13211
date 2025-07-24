package com.fengshen.server.process.friend;

import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.fengshen.db.domain.Characters;
import com.fengshen.db.domain.Friend;
import com.fengshen.db.domain.FriendGroup;
import com.fengshen.db.domain.MailboxRefresh;
import com.fengshen.server.data.GameReadTool;
import com.fengshen.server.data.vo.friend.Vo_BE_ADD_FRIEND;
import com.fengshen.server.data.write.M36871_0;
import com.fengshen.server.data.write.friend.MSG_ADD_FRIEND_VERIFY;
import com.fengshen.server.data.write.friend.MSG_BE_ADD_FRIEND;
import com.fengshen.server.data.write.friend.MSG_FRIEND_NOTIFICATION;
import com.fengshen.server.data.write.friend.MSG_FRIEND_UPDATE_LISTS;
import com.fengshen.server.domain.Chara;
import com.fengshen.server.game.GameCommonUtil;
import com.fengshen.server.game.GameData;
import com.fengshen.server.game.GameHandler;
import com.fengshen.server.game.GameObjectChar;
import com.fengshen.server.game.GameObjectCharMng;
import com.fengshen.server.util.GameConfig;
import com.google.common.collect.Lists;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import tk.mybatis.mapper.entity.Example;

/**
 *  添加好友
 * 
 *
 */
@Service
@Slf4j
public class CMD_ADD_FRIEND implements GameHandler {

	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		String group = GameReadTool.readString(buff);
		String name = GameReadTool.readString(buff);
		int icon = GameReadTool.readInt(buff);
		int level = GameReadTool.readShort(buff);
		
		if("6".equals(group)) {
			//临时好友邮件忽略
			log.info("临时分组请求添加好友,直接忽略");
			return;
		}
		//获取对方好友设置
		Characters findOneByNameBold = GameData.that.baseCharactersService.findOneByNameSelectProperties(name,"data"
				,"id");
		if(findOneByNameBold != null) {
			//被添加的人
			GameObjectChar toCharaObj = GameObjectCharMng.getGameObjectChar(findOneByNameBold.getId());
			if(toCharaObj != null) {
				//查询是否已经添加为好友
				Example isAddFriendExample = new Example(Friend.class);
				isAddFriendExample.createCriteria().andEqualTo("gid", 
						GameObjectChar.getGameObjectChar().chara.uuid).andEqualTo("friendGid", toCharaObj.chara.uuid);
				int isAddFriend = GameData.that.friendService.selectCountByExample(isAddFriendExample);
				if(isAddFriend == 0) {
					Chara chara = GameObjectChar.getGameObjectChar().chara;
					Chara toChara = toCharaObj.chara;
					//查询出双方，我的好友列表下的所有好友并刷新
					ArrayList<FriendGroup> friendGroups = Lists.newArrayList(new FriendGroup("我的好友", "1"));			
					//判断需要添加的人是不是我的好友,如果是的话.则直接添加成功,并告诉对方
					Example example = new Example(Friend.class);
					example.createCriteria().andEqualTo("gid", toChara.uuid).andEqualTo("friendGid", chara.uuid);
					int friendCount = GameData.that.friendService.selectCountByExample(example);
					//对方已经添加你为好友，再次点击是否添加对方为好友
					if(friendCount > 0) {
						GameCommonUtil.addFriend(chara,toChara);
						//刷新好友列表
						GameObjectChar.send(new MSG_FRIEND_UPDATE_LISTS(), GameCommonUtil.createFriends(chara, friendGroups), chara.id);
						
						//刷新双方信息
						GameObjectChar.send( new MSG_FRIEND_NOTIFICATION(), new Object[] {
								toChara.name, GameConfig.lineName, 1, toChara.vipType}, chara.id);
						GameObjectChar.send( new MSG_FRIEND_NOTIFICATION(), new Object[] {
								chara.name, GameConfig.lineName, 1, chara.vipType}, toChara.id);
						return;
					}
					//判断是否开启好友验证
					Integer verify_be_added = toChara.getSettings().get("verify_be_added");
					//判断拒绝多少级的人加好友
					Integer integer = toChara.getSettings().get("refuse_be_added");
					if(integer != null && integer == 1) {
						//拒绝多少级的好友
						int addLevel = toChara.setting_refuse_be_add_level;
						if(chara.level<addLevel) {
							GameCommonUtil.dialogOk("对方拒绝"+addLevel+"级以下添加好友");
							return;
						}
					}
					if(verify_be_added != null && verify_be_added == 1){
						//查询是否已经发送了验证
						Example exampleM = new Example(MailboxRefresh.class);
						exampleM.createCriteria().andEqualTo("title", toChara.uuid).andEqualTo("gid", chara.uuid);
						int count = GameData.that.mailboxRefreshService.selectCountByExample(exampleM);
						if(count >0) {
							GameCommonUtil.dialogOk(String.join("", "你已向#Y",toChara.getName(),"#W发送过好友申请，请耐心等待。"));
							return;
						}
						//发送好友验证
						Vo_BE_ADD_FRIEND vo = new Vo_BE_ADD_FRIEND();
						vo.setGid(toChara.getUuid()+"|fenge|" + chara.uuid);
						vo.setName(toChara.getName());
						vo.setSetting(0);
						GameObjectChar.send(new MSG_ADD_FRIEND_VERIFY(), vo);
						return;
					}else {
						GameCommonUtil.addFriend(chara,toChara);
						//发送消息到对方提示有人加他为好友
						Vo_BE_ADD_FRIEND vo_BE_ADD_FRIEND = new Vo_BE_ADD_FRIEND();
						vo_BE_ADD_FRIEND.setGid(chara.getUuid());
						vo_BE_ADD_FRIEND.setName(chara.getName());
						vo_BE_ADD_FRIEND.setSetting(0);
						GameObjectCharMng.getGameObjectChar(toChara.id).sendOne(new MSG_BE_ADD_FRIEND(), vo_BE_ADD_FRIEND);
						//刷新好友列表
						GameObjectChar.send(new MSG_FRIEND_UPDATE_LISTS(), GameCommonUtil.createFriends(chara, friendGroups), chara.id);
						//刷新我在好友那边的信息
						GameObjectChar.send(new M36871_0(), GameCommonUtil.getCharaInfo(chara), toChara.id);
						//刷新好友在我这边的信息
						GameObjectChar.send(new M36871_0(), GameCommonUtil.getCharaInfo(toChara), chara.id);
						log.info("添加好友无需验证");
					}
				}
			}
		}else {
			GameCommonUtil.dialogOk("对方不在线。");
		}
		
		log.info("添加好友==分组:{},{},{},{}",group,name,icon,level);
	}
	
	@Override
	public int cmd() {
		return 0x2066;
	}

}
