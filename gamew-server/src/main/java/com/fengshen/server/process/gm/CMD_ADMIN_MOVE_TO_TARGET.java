package com.fengshen.server.process.gm;

import org.springframework.stereotype.Service;

import com.fengshen.server.data.GameReadTool;
import com.fengshen.server.domain.Chara;
import com.fengshen.server.game.GameHandler;
import com.fengshen.server.game.GameLine;
import com.fengshen.server.game.GameObjectChar;
import com.fengshen.server.game.GameObjectCharMng;
import com.fengshen.server.game.GameUtil;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CMD_ADMIN_MOVE_TO_TARGET implements GameHandler {

	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		String gid = GameReadTool.readString(buff);
		GameObjectChar toGameObject = GameObjectCharMng.getGameObjectCharByUUid(gid);
		GameObjectChar gameObjectChar = GameObjectChar.getGameObjectChar();
		Chara chara = gameObjectChar.chara;
		if(toGameObject != null && gameObjectChar.privilege == 1000) {
			chara.x = toGameObject.chara.x;
			chara.y = toGameObject.chara.y;
			GameLine.getGameMapname(toGameObject.chara.line, toGameObject.gameMap.name).join(gameObjectChar);
			//发送提醒
			GameUtil.sendMeTips("尊敬的#RGM#n大大，您已接近目标");
		}
		log.info("接近目标");
	}

	@Override
	public int cmd() {
		return 0xD076;
	}

}
