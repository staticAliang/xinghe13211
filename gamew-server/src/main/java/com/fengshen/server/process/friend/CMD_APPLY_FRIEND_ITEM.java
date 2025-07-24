package com.fengshen.server.process.friend;

import java.util.Iterator;

import org.springframework.stereotype.Service;

import com.fengshen.core.util.ExecutorsUtils;
import com.fengshen.db.domain.Friend;
import com.fengshen.server.data.GameReadTool;
import com.fengshen.server.data.vo.friend.Vo_FRIEND_ADD_CHAR;
import com.fengshen.server.data.write.friend.MSG_APPLY_FRIEND_ITEM_RESULT;
import com.fengshen.server.data.write.inventory.MSG_INVENTORY_REMOVE;
import com.fengshen.server.domain.Chara;
import com.fengshen.server.domain.Goods;
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
 * a
 * @author weilian
 *
 */
@Service
@Slf4j
public class CMD_APPLY_FRIEND_ITEM implements GameHandler {

	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		String items = GameReadTool.readString(buff);
		String gid = GameReadTool.readString(buff);
		String name = GameReadTool.readString(buff);
		log.info("赠送：items:{},gid:{},name:{}",items,gid,name);
		int result = 1;
		GameObjectChar gameObjectChar = GameObjectChar.getGameObjectChar();
		Chara chara = gameObjectChar.chara;
		GameObjectChar friendGameObjetcChar = GameObjectCharMng.getGameObjectCharByUUid(gid);
		if(friendGameObjetcChar == null) {
			GameUtil.sendMeTips("对方不在线，无法赠送友好度道具。");
			result = 0;
		}
		//3|0|0 
		String[] posArr = items.split("\\|");
		int n1 = Integer.valueOf(posArr[0]);//百合
		int n2 = Integer.valueOf(posArr[2]); //男玫瑰，女巧克力
		Integer.valueOf(posArr[1]);//情缘盒
		//本次增加的好友度
		int friendScoreNum = 0;
		//背包信息
		Iterator<Goods> iterator = chara.backpack.iterator();
		if(n1 > 0) {
			while(iterator.hasNext()) {
				Goods goods = iterator.next();
				if(goods.goodsInfo.str.equals("百合")) {
					friendScoreNum+=88;
					n1--;
					gameObjectChar.sendOne(new MSG_INVENTORY_REMOVE(), goods.pos);
					iterator.remove();
				}
			}
		}
		Iterator<Goods> n2Iterator = chara.backpack.iterator();
		if(n2 > 0) {
			while(n2Iterator.hasNext()) {
				Goods goods = iterator.next();
				if(chara.sex == 1) { //玫瑰
					if(goods.goodsInfo.str.equals("玫瑰")) {
						friendScoreNum+=66;
						n2--;
						gameObjectChar.sendOne(new MSG_INVENTORY_REMOVE(), goods.pos);
						iterator.remove();
					}
				}else if(chara.sex == 2) { //巧克力
					if(goods.goodsInfo.str.equals("巧克力")) {
						friendScoreNum+=66;
						n2--;
						gameObjectChar.sendOne(new MSG_INVENTORY_REMOVE(), goods.pos);
						iterator.remove();
					}
				}
			}
		}
		//赠送方这边
		Example example = new Example(Friend.class);
		example.createCriteria().andEqualTo("friendGid", friendGameObjetcChar.chara.uuid)
		.andEqualTo("gid", chara.uuid);
		Friend giverFriend = GameData.that.friendService.selectOneByExample(example);
		giverFriend.setFriendScore(giverFriend.getFriendScore()+friendScoreNum);
		//被赠送方
		example = new Example(Friend.class);
		example.createCriteria().andEqualTo("friendGid", chara.uuid)
		.andEqualTo("gid", friendGameObjetcChar.chara.uuid);
		Friend receiveFriend = GameData.that.friendService.selectOneByExample(example);
		if(receiveFriend != null) {
			receiveFriend.setFriendScore(receiveFriend.getFriendScore()+friendScoreNum);
			//数据库异步操作
			ExecutorsUtils.getExecutorPools().execute(new Runnable() {
				@Override
				public void run() {
					//更新赠送方
					GameData.that.friendService.updateByPrimaryKeySelective(giverFriend);
					//更新被赠送方
					GameData.that.friendService.updateByPrimaryKeySelective(receiveFriend);
				}
			});
			gameObjectChar.sendOne(new MSG_APPLY_FRIEND_ITEM_RESULT(), new Object[] {result,gid});
			//通知客户端刷新界面,好友度
			Vo_FRIEND_ADD_CHAR vo = new Vo_FRIEND_ADD_CHAR();
			vo.groupBuf = giverFriend.getGroupId();
			vo.arena_rank = giverFriend.getFriendScore();
			vo.online = 1;
			GameCommonUtil.refreshFriend(vo, friendGameObjetcChar.chara);
			//消息提示
			GameUtil.sendMeTips("赠送成功你和对方的友好度提升了#R"+friendScoreNum+"#n点。");
			GameCommonUtil.sendTips("你的好友#Y"+chara.name+"#n给你使用了友好度道具，你和他(她)友好度提升了#R"+friendScoreNum+"#n点。", friendGameObjetcChar);
		}
	}

	@Override
	public int cmd() {
		return 0xB066;
	}

}
