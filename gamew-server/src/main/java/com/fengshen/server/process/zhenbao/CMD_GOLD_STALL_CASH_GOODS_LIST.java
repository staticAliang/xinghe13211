package com.fengshen.server.process.zhenbao;

import org.springframework.stereotype.Service;

import com.fengshen.server.game.GameHandler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

/**
 * 请求金钱商品列表
 * 
 * 对应--MSG_GOLD_STALL_CASH_GOODS_LIST
 *
 */
@Service
@Slf4j
public class CMD_GOLD_STALL_CASH_GOODS_LIST implements GameHandler {

	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {

		log.info("请求珍宝金钱商品列表");
	}

	@Override
	public int cmd() {
		return 0x8122;
	}

}
