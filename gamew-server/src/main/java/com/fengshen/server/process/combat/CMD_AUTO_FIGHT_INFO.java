package com.fengshen.server.process.combat;

import org.springframework.stereotype.Service;

import com.fengshen.server.data.vo.ListVo_65527_0;
import com.fengshen.server.data.write.M65527_0;
import com.fengshen.server.domain.Chara;
import com.fengshen.server.game.GameHandler;
import com.fengshen.server.game.GameObjectChar;
import com.fengshen.server.game.GameUtil;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

// 这里对应巡逻的按钮
@Service
@Slf4j
public class CMD_AUTO_FIGHT_INFO implements GameHandler {
	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		Chara chara = GameObjectChar.getGameObjectChar().chara;
		ListVo_65527_0 vo_65527_0 = GameUtil.a65527(chara);
		GameObjectChar.send(new M65527_0(), vo_65527_0);
		log.info("自动巡逻");
	}

	@Override
	public int cmd() {
		return 24580;
	}
}
