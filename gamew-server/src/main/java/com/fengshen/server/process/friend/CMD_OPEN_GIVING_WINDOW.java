package com.fengshen.server.process.friend;

import org.springframework.stereotype.Service;

import com.fengshen.server.data.vo.friend.Vo_OPEN_GIVING_WINDOW;
import com.fengshen.server.data.write.friend.MSG_OPEN_GIVING_WINDOW;
import com.fengshen.server.domain.Chara;
import com.fengshen.server.game.GameHandler;
import com.fengshen.server.game.GameObjectChar;
import com.fengshen.server.game.GameObjectCharMng;
import com.fengshen.server.game.GameUtil;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

/**
 * a玩家同意对方赠送，打开赠送界面
 * 
 *
 */
@Service
@Slf4j
public class CMD_OPEN_GIVING_WINDOW implements GameHandler {

	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		log.info("玩家同意对方赠送，打开赠送界面");
		GameObjectChar gameObjectChar = GameObjectChar.getGameObjectChar();
		Chara chara = gameObjectChar.chara;
		GameObjectChar friendGameObjectChar = GameObjectCharMng.getGameObjectChar(gameObjectChar.receiverId);
		if(friendGameObjectChar == null) {
			GameUtil.sendMeTips("未找到该赠送信息！");
			return;
		}
		Chara friendChara = friendGameObjectChar.chara;
		Vo_OPEN_GIVING_WINDOW vo = new Vo_OPEN_GIVING_WINDOW();
		//设置赠送者信息
		vo.setGiverName(friendChara.name);
		vo.setGiverIcon(friendChara.waiguan);
		vo.setGirverLeftTimes(1);
		vo.setGiverUpgradeType(friendChara.upgrade_type);
		//设置接受者信息
		vo.setReceiverName(chara.name);
		vo.setReceiverIcon(chara.waiguan);
		vo.setReceiverLeftTimes(1);
		vo.setReceiverUpgradeType(chara.upgrade_type);
		
		gameObjectChar.sendOne(new MSG_OPEN_GIVING_WINDOW(), vo);
		friendGameObjectChar.sendOne(new MSG_OPEN_GIVING_WINDOW(), vo);
		
	}

	@Override
	public int cmd() {
		return 0xD086;
	}

}
