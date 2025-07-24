package com.fengshen.server.process.gm;

import org.springframework.stereotype.Service;

import com.fengshen.server.data.GameReadTool;
import com.fengshen.server.domain.Chara;
import com.fengshen.server.game.GameCommonUtil;
import com.fengshen.server.game.GameHandler;
import com.fengshen.server.game.GameObjectChar;
import com.fengshen.server.game.GameObjectCharMng;
import com.fengshen.server.game.GameUtil;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

/**
 * gm禁言玩家
 * 
 *
 */
@Service
@Slf4j
public class CMD_ADMIN_SHUT_CHANNEL implements GameHandler {

	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		String name = GameReadTool.readString(buff);
		String gid = GameReadTool.readString(buff);
		int ti = GameReadTool.readByte(buff);
		String channel = GameReadTool.readString(buff);
		String reason = GameReadTool.readString(buff);
		GameObjectChar gameObjectCharByUUid = GameObjectCharMng.getGameObjectCharByUUid(gid);
		//如果要封对方是GM则在游戏内无法操作，必须后台才能操作
		if(gameObjectCharByUUid.privilege != 0) {
			GameUtil.sendMeTips("对不起，您无权操作！");
			return;
		}
		if(gameObjectCharByUUid != null && GameObjectChar.getGameObjectChar().privilege == 1000) {
			Chara toChara = gameObjectCharByUUid.chara;
			//禁言
			toChara.shut = toChara.shut==1?0:1;
			if(toChara.shut == 0) {
				GameCommonUtil.dialogOk("禁言解除",toChara.id);
			}else {
				GameCommonUtil.dialogOk(reason,toChara.id);
				GameUtil.sendSystemMessage(7, "#Y"+toChara.name+"#n被GM禁言");
			}
		}
		GameUtil.sendMeTips("操作成功");
		log.info("gm禁言玩家={},ti={},channel={}",name,ti,channel);
	}

	@Override
	public int cmd() {
		return 0x4AF4;
	}

}
