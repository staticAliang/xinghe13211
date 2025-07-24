package com.fengshen.server.process.user;

import org.springframework.stereotype.Service;

import com.fengshen.server.domain.Chara;
import com.fengshen.server.game.GameHandler;
import com.fengshen.server.game.GameObjectChar;
import com.fengshen.server.game.GameUtil;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

/**
 * 请求结拜信息
 * 
 *
 */
@Service
@Slf4j
public class CMD_REQUEST_BROTHER_INFO implements GameHandler {
	@Override
	public void process(final ChannelHandlerContext ctx, final ByteBuf buff) {
		log.info("请求结拜信息");
		Chara chara = GameObjectChar.getGameObjectChar().chara;
		if(!chara.isFight) {
			GameUtil.sendUpdate(chara,"openUserTab_yuanying");
		}
		
	}

	@Override
	public int cmd() {
		return 53490;
	}
}
