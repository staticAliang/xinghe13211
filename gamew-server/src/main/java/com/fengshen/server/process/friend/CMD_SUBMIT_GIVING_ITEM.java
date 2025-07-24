package com.fengshen.server.process.friend;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.fengshen.db.domain.Friend;
import com.fengshen.server.data.GameReadTool;
import com.fengshen.server.data.write.friend.MSG_UPDATE_GIVING_ITEM;
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
 * 提交赠送
 * @author aaa
 *
 */
@Service
@Slf4j
public class CMD_SUBMIT_GIVING_ITEM implements GameHandler {

	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		int type = GameReadTool.readByte(buff);
		int pos = GameReadTool.readInt(buff);
		log.info("提交赠送，type={},pos={}",type,pos);
		GameObjectChar gameObjectChar = GameObjectChar.getGameObjectChar();
		Chara chara = gameObjectChar.chara;
		GameObjectChar friendGameObjectChar = GameObjectCharMng.getGameObjectChar(gameObjectChar.receiverId);
		if(friendGameObjectChar == null) {
			GameUtil.sendMeTips("对方不在线，无法赠送！");
			return;
		}
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
		if(type == 1) {
			//道具
			for(Goods goods:gameObjectChar.chara.backpack) {
				if(goods.pos == pos) {
					JSONObject givingConfig = GameCommonUtil.isGivingItem(chara, goods);
					if(givingConfig != null) {
						//再次校验对方是否有赠送次数
						if(friendGameObjectChar.chara.getGivingCount+1>givingConfig.getIntValue("getGivingCount")) {
							GameCommonUtil.sendTips("你今日已无接收次数",friendGameObjectChar);
							GameCommonUtil.sendTips("对方今日已无接收次数");
							return;
						}
						//校验我方是否有接收次数	
						if(chara.sendGivingCount+1>givingConfig.getIntValue("sendGivingCount")) {
							GameCommonUtil.sendTips("你今日已无赠送次数");
							return;
						}
						friendGameObjectChar.givingType = 1;
						friendGameObjectChar.givingPos = pos;
						friendGameObjectChar.sendOne(new MSG_UPDATE_GIVING_ITEM(), new Object[] {0,goods});
					}
					break;
				}
			}
		}else if(type == 2) {
			//宠物
			GameUtil.sendMeTips("暂时无法赠送！");
		}
	}

	@Override
	public int cmd() {
		return 0xD088;
	}

}
