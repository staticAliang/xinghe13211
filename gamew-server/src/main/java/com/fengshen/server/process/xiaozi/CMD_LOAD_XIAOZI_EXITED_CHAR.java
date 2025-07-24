package com.fengshen.server.process.xiaozi;

import com.fengshen.server.game.*;
import org.springframework.stereotype.Service;

import com.fengshen.db.domain.Accounts;
import com.fengshen.db.domain.Characters;
import com.fengshen.server.data.GameReadTool;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

@Service
public class CMD_LOAD_XIAOZI_EXITED_CHAR implements GameHandler {

	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {

		final String uuid = GameReadTool.readString(buff);
		String char_name = GameReadTool.readString(buff);
		GameObjectChar session = GameObjectChar.getGameObjectChar();
		//账号信息
		Accounts account = session.getAccount();
		Characters characters = GameData.that.characterService.login(account.getId(), char_name);
		GameObjectChar oldSession = GameObjectCharMng.getGameObjectChar(characters.getId());
		if(oldSession != null) {
			//原来的下线
			oldSession.offline();
		}
		//正常登录
		try {
			GameCommonUtil.loadExistedChar(characters, session, char_name);
		} finally {
			GameObjectChar.getGameObjectChar().ctx =(ChannelHandlerContext) GameCore.xiaoziClientInfo.get(uuid).get("ctx");
		}
//		String char_name = GameReadTool.readString(buff);
//		GameObjectChar session = GameObjectChar.getGameObjectChar();
//		//账号信息
//		Accounts account = session.getAccount();
//		Characters characters = GameData.that.characterService.login(account.getId(), char_name);
//		GameObjectChar oldSession = GameObjectCharMng.getGameObjectChar(characters.getId());
//		if(oldSession != null) {
//			//原来的下线
//			oldSession.offline();
//		}
//		//正常登录
//		GameCommonUtil.loadExistedChar(characters, session, char_name);
	}

	@Override
	public int cmd() {
		return 41920;
	}

}
