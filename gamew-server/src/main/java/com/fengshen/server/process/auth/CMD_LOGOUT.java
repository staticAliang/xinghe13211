package com.fengshen.server.process.auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fengshen.server.domain.Chara;
import com.fengshen.server.game.GameCommonUtil;
import com.fengshen.server.game.GameHandler;
import com.fengshen.server.game.GameObjectChar;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

/**
 * 角色退出游戏
 *
 */
@Service
public class CMD_LOGOUT implements GameHandler {

	private static final Logger log = LoggerFactory.getLogger(CMD_LOGOUT.class);

	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		// 设置参战
		Chara chara = GameObjectChar.getGameObjectChar().chara;
		log.info("角色退出游戏");
		// 设置登录状态
		if(chara != null) {
			GameCommonUtil.setOnline(chara,0);
			//下线刷新状态
			new Thread() {
				public void run() {
					GameCommonUtil.friendTips(chara, 2);
				}
			}.start();
			GameObjectChar.getGameObjectChar().offline();
		}
	}

	@Override
	public int cmd() {
		return 0x0004;
	}

}
