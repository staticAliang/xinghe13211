package com.fengshen.server.process.gm;

import org.springframework.stereotype.Service;

import com.fengshen.server.data.GameReadTool;
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
public class CMD_ADMIN_WARN_PLAYER implements GameHandler {

	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		String name = GameReadTool.readString(buff);
		String gid = GameReadTool.readString(buff);
		String title = GameReadTool.readString(buff);
		String content = GameReadTool.readString(buff);
		int valid_day = GameReadTool.readByte(buff);
		if(GameObjectChar.getGameObjectChar().privilege == 1000) {
			GameObjectChar gameObjectCharByUUid = GameObjectCharMng.getGameObjectCharByUUid(gid);
			if(gameObjectCharByUUid == null) {
				GameUtil.sendMeTips("目标已下线");
				return;
			}
			GameCommonUtil.sendTips(content, gameObjectCharByUUid.chara.id);
			GameUtil.sendMeTips("成功发送警告！");
		}
		log.info("gm发送警告, name={},title={},valid_day={}",name,title,valid_day);
	}

	@Override
	public int cmd() {
		return 0xD03C;
	}

}
