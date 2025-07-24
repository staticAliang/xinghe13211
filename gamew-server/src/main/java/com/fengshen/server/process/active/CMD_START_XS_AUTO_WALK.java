package com.fengshen.server.process.active;

import org.springframework.stereotype.Service;

import com.fengshen.server.data.vo.Vo_61553_0;
import com.fengshen.server.data.vo.task.Vo_AUTO_WALK;
import com.fengshen.server.data.write.task.MSG_AUTO_WALK;
import com.fengshen.server.game.GameHandler;
import com.fengshen.server.game.GameObjectChar;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

/**
 * 悬赏任务请求自动寻路
 * 
 *
 */
@Service
public class CMD_START_XS_AUTO_WALK implements GameHandler {

	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {

		GameObjectChar gameObjectChar = GameObjectChar.getGameObjectChar();
		if(gameObjectChar != null) {
			//获取当前用户悬赏任务
			Vo_61553_0 vo_61553_0 = gameObjectChar.chara.taskMap.get("悬赏任务");
			if(vo_61553_0 != null) {
				gameObjectChar.sendOne(new MSG_AUTO_WALK(), new Vo_AUTO_WALK(vo_61553_0.task_prompt));
			}
		}
	}

	@Override
	public int cmd() {
		return 0xD2A0;
	}

}
