package com.fengshen.server.process.hunpo;

import org.springframework.stereotype.Service;

import com.fengshen.server.game.GameCommonUtil;
import com.fengshen.server.game.GameHandler;
import com.fengshen.server.game.GameObjectChar;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

/**
 * 请求刷新内丹数据
 * 
 *
 */
@Service
@Slf4j
public class CMD_REFRESH_NEIDAN_DATA implements GameHandler {
	
	
	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		GameObjectChar gameObjectChar = GameObjectChar.getGameObjectChar();
		log.info("请求刷新内丹数据");
		GameCommonUtil.refreshNeidan(gameObjectChar);
		
	}

	@Override
	public int cmd() {
		return 0xB17F;
	}

}
