package com.fengshen.server.process.user;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.fengshen.server.data.GameReadTool;
import com.fengshen.server.data.vo.account.Vo_L_LOGIN_PREVIEW_PLAYER;
import com.fengshen.server.game.GameHandler;
import com.fengshen.server.util.GameConfig;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CMD_L_LOGIN_PREVIEW_PLAYER implements GameHandler {

	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		
		String account = GameReadTool.readString(buff);
		log.info("登陆请求预览, 账号:{}",account);
		Vo_L_LOGIN_PREVIEW_PLAYER vo = new Vo_L_LOGIN_PREVIEW_PLAYER();
		vo.setAccount(account);
		vo.setIp(GameConfig.serverIp);
		vo.setCookie(UUID.randomUUID().toString());
		vo.setPort(GameConfig.port);
		vo.setServerName(GameConfig.lineName);
		vo.setGid(account);
		vo.setTime((int) (System.currentTimeMillis()/1000L));
//		ctx.writeAndFlush(new MSG_L_LOGIN_PREVIEW_PLAYER().write(vo));
		ctx.close();
	}

	@Override
	public int cmd() {
		return 0x5E30;
	}

}
