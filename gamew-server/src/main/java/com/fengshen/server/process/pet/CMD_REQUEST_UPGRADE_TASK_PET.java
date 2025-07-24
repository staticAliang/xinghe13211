package com.fengshen.server.process.pet;

import org.springframework.stereotype.Service;

import com.fengshen.server.data.vo.Vo_45315_0;
import com.fengshen.server.data.write.M45315_0;
import com.fengshen.server.domain.Chara;
import com.fengshen.server.game.GameHandler;
import com.fengshen.server.game.GameObjectChar;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

/**
 * 请求正在飞升的宠物
 * 
 *
 */
@Service
@Slf4j
public class CMD_REQUEST_UPGRADE_TASK_PET implements GameHandler {
	@Override
	public void process(final ChannelHandlerContext ctx, final ByteBuf buff) {
		log.info("请求正在飞升的宠物");
		Chara chara = GameObjectChar.getGameObjectChar().chara;
		Vo_45315_0 vo_45315_0 = new Vo_45315_0();
		vo_45315_0.id = chara.chongwuchanzhanId;
		GameObjectChar.send(new M45315_0(), vo_45315_0);
	}

	@Override
	public int cmd() {
		return 45314;
	}
}
