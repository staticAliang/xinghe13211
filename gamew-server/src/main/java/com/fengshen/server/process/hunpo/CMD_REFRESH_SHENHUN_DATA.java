package com.fengshen.server.process.hunpo;

import org.springframework.stereotype.Service;

import com.fengshen.server.domain.Chara;
import com.fengshen.server.game.GameCommonUtil;
import com.fengshen.server.game.GameHandler;
import com.fengshen.server.game.GameObjectChar;
import com.fengshen.server.game.GameUtil;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

/**
 * 请求神魄数据
 * 
 *
 */
@Service
@Slf4j
public class CMD_REFRESH_SHENHUN_DATA implements GameHandler {
	
	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		log.info("请求神魄数据");
		GameUtil.sendMeTips("请注意魂魄消耗的是#R积分");
		Chara chara = GameObjectChar.getGameObjectChar().chara;
		GameCommonUtil.refreShenHun(chara);
	}
	

	@Override
	public int cmd() {
		return 0x5300;
	}

}
