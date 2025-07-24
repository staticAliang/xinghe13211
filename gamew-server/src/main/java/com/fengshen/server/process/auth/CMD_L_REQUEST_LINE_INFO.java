package com.fengshen.server.process.auth;

import java.net.InetSocketAddress;

import org.springframework.stereotype.Service;

import com.fengshen.db.domain.Accounts;
import com.fengshen.server.data.GameReadTool;
import com.fengshen.server.data.vo.Vo_45143_0;
import com.fengshen.server.data.vo.Vo_45555_0;
import com.fengshen.server.data.write.M45143_0;
import com.fengshen.server.data.write.M45555_0;
import com.fengshen.server.game.GameData;
import com.fengshen.server.game.GameHandler;
import com.fengshen.server.util.GameConfig;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

/**
 * 请求线路信息
 * 
 *
 */
@Service
@Slf4j
public class CMD_L_REQUEST_LINE_INFO implements GameHandler {

	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		if (ctx == null) {
			return;
		}
		InetSocketAddress ipSocket = (InetSocketAddress) ctx.channel().remoteAddress();
		if (ipSocket == null) {
			ctx.close();
			return;
		}
		String account = GameReadTool.readString(buff);
		if(account == null) {
			ctx.close();
			return;
		}
		//查询账号是否存在
		Accounts accounts = GameData.that.baseAccountsService.findOneByToken(account.substring(6));
		if(accounts == null) {
			ctx.close();
			return;
		}
		String clientIp = ipSocket.getAddress().getHostAddress();
		log.info("客户端请求线路信息,account={},ip地址={}", account, clientIp);
		// add:e
		Vo_45143_0 vo_45143_0 = new Vo_45143_0();
		vo_45143_0.line_name = GameConfig.lineName + 1 + "线";
		vo_45143_0.expect_time = 1;
		vo_45143_0.reconnet_time = 0;
		vo_45143_0.waitCode = 1;
		vo_45143_0.count = 1;
		vo_45143_0.keep_alive = 1;
		vo_45143_0.need_wait = 0;
		vo_45143_0.indsider_lv = 255;
		vo_45143_0.silverCoin = 0;
		vo_45143_0.status = 0;

		ByteBuf write = new M45143_0().write(vo_45143_0);
		
		//开始登录流程
		Vo_45555_0 vo_45555_0 = new Vo_45555_0();
		vo_45555_0.type = "normal";
		vo_45555_0.cookie = "4c9PlAEAAwrOYofMNQm8WSQxAAtEhX9QhuSc5dzbTLcJqiCY1SsEdF58eERYyqEIGWmW1IAABhpQrnffhe5";
		ByteBuf write2 = new M45555_0().write(vo_45555_0);
		ctx.writeAndFlush(write2);
		ctx.writeAndFlush(write);
	}

	@Override
	public int cmd() {
		return 45144;
	}
}