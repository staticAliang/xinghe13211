package com.fengshen.server.process.xiaozi;

import org.springframework.stereotype.Service;

import com.fengshen.server.data.GameReadTool;
import com.fengshen.server.game.GameCore;
import com.fengshen.server.game.GameHandler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

/**
 * 问道小子退出登录
 * @author weilian
 *
 */
@Service
@Slf4j
public class CMD_XIAOZI_LOGOUT implements GameHandler {

	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		String uid = GameReadTool.readString(buff);
		if(!"".equals(uid)) {
			GameCore.xiaoziClientInfo.remove(uid);
		}
		log.info("问道小子退出登录：{}",uid);
	}

	@Override
	public int cmd() {
		return 9000;
	}

}
