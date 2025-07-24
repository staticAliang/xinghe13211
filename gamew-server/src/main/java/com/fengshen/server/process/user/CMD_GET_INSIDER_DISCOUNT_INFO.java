package com.fengshen.server.process.user;

import org.springframework.stereotype.Service;

import com.fengshen.server.data.vo.Vo_53377_0;
import com.fengshen.server.data.write.M53377_0;
import com.fengshen.server.domain.Chara;
import com.fengshen.server.game.GameHandler;
import com.fengshen.server.game.GameObjectChar;
import com.fengshen.server.game.GameUtil;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

/**
 * 客户端请求会员折扣活动信息
 * @author weilian
 *
 */
@Service
@Slf4j
public class CMD_GET_INSIDER_DISCOUNT_INFO implements GameHandler {
	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		log.info("客户端请求会员折扣活动信息");
		Chara chara = GameObjectChar.getGameObjectChar().chara;
		Vo_53377_0 vo_53377_0 = new Vo_53377_0();
		vo_53377_0.dsicountMonthPrice = 3000;
		vo_53377_0.dsicountQuaterPrice = 9000;
		vo_53377_0.dsicountYearPrice = 36000;
		vo_53377_0.startTime = 1555016400;
		vo_53377_0.endTime = 1570827599;
		GameObjectChar.send(new M53377_0(), vo_53377_0);
		GameUtil.addVip(chara);
	}

	@Override
	public int cmd() {
		return 53378;
	}
}
