package com.fengshen.server.process.friend;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.fengshen.db.domain.Friend;
import com.fengshen.server.data.vo.Vo_40964_0;
import com.fengshen.server.data.write.M40964_0;
import com.fengshen.server.data.write.friend.MSG_COMPLETE_GIVING;
import com.fengshen.server.domain.Chara;
import com.fengshen.server.domain.Goods;
import com.fengshen.server.game.GameCommonUtil;
import com.fengshen.server.game.GameData;
import com.fengshen.server.game.GameHandler;
import com.fengshen.server.game.GameObjectChar;
import com.fengshen.server.game.GameObjectCharMng;
import com.fengshen.server.game.GameUtil;
import com.fengshen.server.util.BeanUtils;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import tk.mybatis.mapper.entity.Example;

/**
 * 接受赠送物品
 * @author aaa
 *
 */
@Service
@Slf4j
public class CMD_ACCEPT_GIVING implements GameHandler {

	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		log.info("同意赠送");
		//接收方
		GameObjectChar gameObjectChar = GameObjectChar.getGameObjectChar();
		Chara chara = gameObjectChar.chara;
		//赠送方
		GameObjectChar friendGameObjectChar = GameObjectCharMng.getGameObjectChar(gameObjectChar.receiverId);
		if(friendGameObjectChar == null) {
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
		//只对物品有效
		if(gameObjectChar.givingType == 1) {
			for(Goods goods:friendGameObjectChar.chara.backpack) {
				if(goods.pos == gameObjectChar.givingPos) {
					//可以赠送
					JSONObject givingConfig = GameCommonUtil.isGivingItem(chara, goods);
					if(givingConfig != null) {
						//再次校验对方是否有赠送次数
						if(friendGameObjectChar.chara.sendGivingCount+1>givingConfig.getIntValue("sendGivingCount")) {
							GameCommonUtil.sendTips("你今日已无赠送次数",friendGameObjectChar);
							return;
						}
						//校验我方是否有接收次数
						if(chara.getGivingCount+1>givingConfig.getIntValue("getGivingCount")) {
							GameCommonUtil.sendTips("你今日已无接收次数");
							return;
						}
						//把物品加入到接收方中
						Goods clone = BeanUtils.clone(goods);
						clone.goodsInfo.owner_id = 1;
						//克隆出新的
						if(GameCommonUtil.addGoodsToBackpack(clone, gameObjectChar)) {
							Vo_40964_0 vo_40964_9 = new Vo_40964_0();
							vo_40964_9.type = 1;
							vo_40964_9.name = clone.goodsInfo.str;
							vo_40964_9.param = "";
							vo_40964_9.rightNow = 0;
							GameObjectChar.send(new M40964_0(), vo_40964_9);
							//删除赠送者里面的物品
							GameUtil.removemunber(friendGameObjectChar, goods, 1);
							GameCommonUtil.sendTips("你获得了#Y"+friendGameObjectChar.chara.name+"#n赠送的#R"+goods.goodsInfo.str, gameObjectChar);
							GameCommonUtil.sendTips("#Y"+chara.name+"#n接受了你的赠送", friendGameObjectChar);
							chara.getGivingCount+=1;
							friendGameObjectChar.chara.sendGivingCount+=1;
							GameCommonUtil.addCharaTrail(chara, "好友赠送", friendGameObjectChar.chara.name+"->"+chara.name+"="+goods.goodsInfo.str, "好友赠送");
						}
					}
					break;
				}
			}
		}
		friendGameObjectChar.receiverId = 0;
		friendGameObjectChar.givingPos = 0;
		friendGameObjectChar.givingType = 0;
		gameObjectChar.receiverId = 0;
		gameObjectChar.givingPos = 0;
		gameObjectChar.givingType = 0;
		//关闭对方的对话框
		friendGameObjectChar.sendOne(new MSG_COMPLETE_GIVING(), 1);
	}

	@Override
	public int cmd() {
		return 0xD08A;
	}

}
