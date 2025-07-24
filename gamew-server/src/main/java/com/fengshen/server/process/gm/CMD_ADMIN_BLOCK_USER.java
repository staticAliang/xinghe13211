package com.fengshen.server.process.gm;

import org.springframework.stereotype.Service;

import com.fengshen.server.data.GameReadTool;
import com.fengshen.server.data.write.MSG_KICK_OFF;
import com.fengshen.server.game.GameHandler;
import com.fengshen.server.game.GameObjectChar;
import com.fengshen.server.game.GameObjectCharMng;
import com.fengshen.server.game.GameUtil;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CMD_ADMIN_BLOCK_USER implements GameHandler {

	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		String name = GameReadTool.readString(buff);
		String gid = GameReadTool.readString(buff);
		int ti = GameReadTool.readInt(buff);
		String reason = GameReadTool.readString(buff);
		GameObjectChar gameObjectCharByUUid = GameObjectCharMng.getGameObjectCharByUUid(gid);
		//如果要封对方是GM则在游戏内无法操作，必须后台才能操作
		if(gameObjectCharByUUid.privilege != 0) {
			GameUtil.sendMeTips("对不起，您无权操作！");
			return;
		}
		if(gameObjectCharByUUid != null && GameObjectChar.getGameObjectChar().privilege == 1000) {
			gameObjectCharByUUid.sendOne(new MSG_KICK_OFF(), reason);
			gameObjectCharByUUid.characters.setBlock(1);
			gameObjectCharByUUid.offline();
			GameUtil.sendSystemMessage(7, "#Y"+reason+"#n被GM#n封号");
		}
		log.info("gm封闭角色, ti={},name={}",ti,name);
		log.info("");
	}

	@Override
	public int cmd() {
		return 0xD03A;
	}

}
