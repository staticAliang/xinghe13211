package com.fengshen.server.process.user;

import org.springframework.stereotype.Service;

import com.fengshen.server.data.write.user.MSG_RECOMMEND_XMD;
import com.fengshen.server.domain.Chara;
import com.fengshen.server.game.GameHandler;
import com.fengshen.server.game.GameObjectChar;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

/**
 * 请求仙魔点自动加点配置
 * @author aaa
 *
 */
@Service
@Slf4j
public class CMD_REQUEST_RECOMMEND_XMD implements GameHandler {

	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		log.info("请求仙魔点自动加点配置");
		GameObjectChar gameObjectChar = GameObjectChar.getGameObjectChar();
		Chara chara = gameObjectChar.chara;
		//打开配置界面
		gameObjectChar.sendOne(new MSG_RECOMMEND_XMD(), new Integer[] {chara.upgradeAddType, chara.upgradeIsOpen});
	}

	@Override
	public int cmd() {
		return 0xD15E;
	}

}
