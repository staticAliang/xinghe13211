package com.fengshen.server.process.friend;

import org.springframework.stereotype.Service;

import com.fengshen.server.data.constant.ClientButtonIdConst;
import com.fengshen.server.game.GameCommonUtil;
import com.fengshen.server.game.GameHandler;
import com.fengshen.server.game.GameObjectChar;
import com.fengshen.server.game.GameObjectCharMng;
import com.fengshen.server.game.GameUtil;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CMD_CANCEL_GIVING implements GameHandler {

	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		log.info("取消赠送");
		GameObjectChar gameObjectChar = GameObjectChar.getGameObjectChar();
		GameObjectChar friendGameObjectChar = GameObjectCharMng.getGameObjectChar(gameObjectChar.receiverId);
		if(friendGameObjectChar == null) {
			return;
		}
		GameCommonUtil.sendTips("对方关闭了赠送",friendGameObjectChar);
		//关闭对方的对话框
		GameUtil.sendNotify(ClientButtonIdConst.NOTIFY_CLOSE_DLG, "GiveApplyDlg",friendGameObjectChar);
		GameUtil.sendNotify(ClientButtonIdConst.NOTIFY_CLOSE_DLG, "GiveDlg",friendGameObjectChar);
		friendGameObjectChar.receiverId = 0;
		friendGameObjectChar.givingPos = 0;
		friendGameObjectChar.givingType = 0;
		gameObjectChar.receiverId = 0;
		gameObjectChar.givingPos = 0;
		gameObjectChar.givingType = 0;
	}

	@Override
	public int cmd() {
		return 0xD08C;
	}

}
