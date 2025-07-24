package com.fengshen.server.process.leitai;

import org.springframework.stereotype.Service;

import com.fengshen.server.data.GameReadTool;
import com.fengshen.server.game.GameHandler;
import com.fengshen.server.game.GameUtil;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

/**
 * 擂台挑战某人
 * @author aaa
 *
 */
@Service
@Slf4j
public class CMD_KILL_COMPETE_TOURNAMENT_TARGET implements GameHandler {

	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		String gid = GameReadTool.readString(buff);
		GameUtil.sendMeTips("请点击切磋");
		log.info("擂台挑战某人,{}",gid);
	}

	@Override
	public int cmd() {
		return 0x5012;
	}

}
