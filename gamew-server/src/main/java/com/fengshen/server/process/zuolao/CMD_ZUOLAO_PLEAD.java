package com.fengshen.server.process.zuolao;

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
 * 坐牢求情
 * @author aaa
 *
 */
@Service
@Slf4j
public class CMD_ZUOLAO_PLEAD implements GameHandler {

	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		String gid = GameReadTool.readString(buff);
		String name = GameReadTool.readString(buff);
		log.info("坐牢请求，gid={},name={}",gid,name);
		GameObjectChar gameObjectChar = GameObjectChar.getGameObjectChar();
		Chara chara = gameObjectChar.chara;
		
		gameObjectChar.confirmData = gid;
		GameUtil.confirm(chara, "你确定为#Y"+name+"#n求情，求情需要完成官府交给你的任务，确定吗？", "zuolao_plead", 30);
	}

	@Override
	public int cmd() {
		return 0xB0AE;
	}

}
