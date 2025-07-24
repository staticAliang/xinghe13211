package com.fengshen.server.process.zhenbao;

import org.springframework.stereotype.Service;

import com.fengshen.server.data.write.zhenbao.MSG_GOLD_STALL_MINE;
import com.fengshen.server.domain.Chara;
import com.fengshen.server.game.GameCommonUtil;
import com.fengshen.server.game.GameHandler;
import com.fengshen.server.game.GameObjectChar;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

/**
 * 我的珍宝摊位信息
 * 
 *
 */
@Service
@Slf4j
public class CMD_GOLD_STALL_OPEN_MY implements GameHandler {

	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		log.info("珍宝摊位信息");
		Chara chara = GameObjectChar.getGameObjectChar().chara;
		GameObjectChar.send(new MSG_GOLD_STALL_MINE(), GameCommonUtil.refreshMarketGold(chara));
	}

	@Override
	public int cmd() {
		return 0x8100;
	}

}
