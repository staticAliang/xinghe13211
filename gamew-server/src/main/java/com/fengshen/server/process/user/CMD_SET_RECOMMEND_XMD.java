package com.fengshen.server.process.user;

import org.springframework.stereotype.Service;

import com.fengshen.server.data.GameReadTool;
import com.fengshen.server.domain.Chara;
import com.fengshen.server.game.GameHandler;
import com.fengshen.server.game.GameObjectChar;
import com.fengshen.server.game.GameUtil;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

/**
 * 设置仙魔点自动加点
 * @author aaa
 *
 */
@Service
@Slf4j
public class CMD_SET_RECOMMEND_XMD implements GameHandler {

	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		int addType = GameReadTool.readByte(buff);
		int isOpen = GameReadTool.readByte(buff);
		GameObjectChar gameObjectChar = GameObjectChar.getGameObjectChar();
		Chara chara = gameObjectChar.chara;
		chara.upgradeIsOpen = isOpen;
		chara.upgradeAddType = addType;
		GameUtil.sendMeTips("设置成功");
		log.info("设置仙魔点自动加点， addType={},isOpen={}",addType,isOpen);
	}

	@Override
	public int cmd() {
		return 0xD15C;
	}

}
