package com.fengshen.server.process.system;

import java.net.InetSocketAddress;

import org.springframework.stereotype.Service;

import com.fengshen.server.data.vo.Vo_41009_0;
import com.fengshen.server.data.write.M41009_0;
import com.fengshen.server.game.GameHandler;
import com.fengshen.server.game.GameObjectChar;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

/**
 * 请求更新服务器时间
 * 
 *
 */
@Service
@Slf4j
public class CMD_ASK_SERVER_TIME implements GameHandler {
	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		Vo_41009_0 vo_41009_0 = new Vo_41009_0();
		vo_41009_0.server_time = (int) (System.currentTimeMillis() / 1000L);
		vo_41009_0.time_zone = 8;
		InetSocketAddress ipSocket = (InetSocketAddress) ctx.channel().remoteAddress();
        String clientIp = ipSocket.getAddress().getHostAddress();
        vo_41009_0.ip = clientIp;
		GameObjectChar.send(new M41009_0(), vo_41009_0);
		log.info("请求更新服务器时间");
	}

	@Override
	public int cmd() {
		return 41008;
	}
}
