package com.fengshen.server.process.user;

import org.springframework.stereotype.Service;

import com.fengshen.server.data.vo.Vo_41051_0;
import com.fengshen.server.data.write.M41051_0;
import com.fengshen.server.game.GameHandler;
import com.fengshen.server.game.GameObjectChar;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

/**
 * 获取活动会员信息
 * 
 *
 */
@Service
public class CMD_GET_ACTIVE_BONUS_INFO implements GameHandler {
	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		Vo_41051_0 vo_41051_0 = new Vo_41051_0();
		vo_41051_0.count = 1;
		vo_41051_0.name0 = "month_charge_gift";
		vo_41051_0.amount0 = 0;
		vo_41051_0.startTime0 = 1577825999; // 2020/1/1 4:59:59
		vo_41051_0.endTime0 = 1577825999; // 2020/1/1 4:59:59
		GameObjectChar.send(new M41051_0(), vo_41051_0);
	}

	@Override
	public int cmd() {
		return 53496;
	}
}