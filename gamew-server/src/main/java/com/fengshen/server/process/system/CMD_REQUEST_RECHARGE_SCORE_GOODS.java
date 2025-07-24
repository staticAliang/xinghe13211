package com.fengshen.server.process.system;

import org.springframework.stereotype.Service;

import com.fengshen.server.game.GameData;
import com.fengshen.server.game.GameHandler;
import com.fengshen.server.game.GameObjectChar;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

/**
 * 请求积分商品列表
 * 
 *
 */
@Service
@Slf4j
public class CMD_REQUEST_RECHARGE_SCORE_GOODS implements GameHandler {
	@Override
	public void process(final ChannelHandlerContext ctx, final ByteBuf buff) {
		log.info("请求积分商品列表");
		GameData.that.chargePointMng.sendChargePointGoods(GameObjectChar.getGameObjectChar());
	}

	@Override
	public int cmd() {
		return 53448;
	}
}