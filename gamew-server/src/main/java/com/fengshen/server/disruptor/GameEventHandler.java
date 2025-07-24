package com.fengshen.server.disruptor;

import java.net.InetSocketAddress;

import com.fengshen.server.game.GameUtil;
import org.springframework.stereotype.Component;

import com.fengshen.core.util.SpringBeanUtils;
import com.fengshen.db.service.system.BlackListService;
import com.fengshen.server.game.GameCore;
import com.fengshen.server.game.GameHandler;
import com.fengshen.server.game.GameObjectChar;
import com.fengshen.server.netty.ServerHandler;
import com.lmax.disruptor.EventHandler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.Attribute;
import io.netty.util.ReferenceCountUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class GameEventHandler implements EventHandler<GameEvent> {

	public void handlerGameMsg(GameEvent event) {
		ByteBuf buff = event.getByteBuf();
		int f1 = event.getF1();
		int f2 = event.getF2();
		int cmd = event.getCmd();
		ChannelHandlerContext ctx = event.getContext();
		// 用户退出游戏重新登录
		Attribute<GameObjectChar> attr = ctx.channel().attr(ServerHandler.akey);
		GameObjectChar session = null;
		if (attr != null && attr.get() != null) {
			session = attr.get();
			GameObjectChar.GAMEOBJECTCHAR_THREAD_LOCAL.set(session);
		}
		if (cmd != 4274 && cmd != 41008) {
			log.info("十进制CMD:{},16进制CMD:{}", cmd, Integer.toString(cmd, 16));
			//GameUtil.sendMeTips("接口编号，数据包标识："+cmd+" 转换16进制为："+Integer.toString(cmd, 16));
		}
		GameHandler gameHandler = GameCore.gameHandlerMap.get(cmd);
		BlackListService blackListService = SpringBeanUtils.getBean(BlackListService.class);
		InetSocketAddress ipSocket = (InetSocketAddress) ctx.channel().remoteAddress();
		String clientIp = ipSocket.getAddress().getHostAddress();
		//ip被封号
		if(blackListService.getCountByDataType(clientIp)>0) {
			ctx.close();
		}else if(session != null && session.characters != null) {
			if(session.characters.getBlock() != null && session.characters.getBlock() == 1) {
				ctx.close();
			}else {
				log.info("============f1:{},f2:{},角色:{}============", f1, f2,session.chara.name);
				//处理消息
				processMsg(cmd, buff, gameHandler, session, ctx);
			}
		}else {
			//处理消息
			processMsg(cmd, buff, gameHandler, session, ctx);
		}
	}
	
	/**
	 * 处理消息
	 * @param cmd 协议
	 * @param buff 字节缓冲区
	 * @param gameHandler 处理器
	 * @param session 会话
	 * @param ctx 上下文
	 */
	public void processMsg(int cmd, ByteBuf buff, GameHandler gameHandler, GameObjectChar session, ChannelHandlerContext ctx) {
		try {
			// 用户退出游戏重新登录
			if (session != null && gameHandler != null) {
				gameHandler.process(ctx, buff);
			} else if (cmd == 45144 || cmd == 9040 || cmd == 12290 || cmd == 9999 || cmd == 9996 || cmd == 9222
					|| cmd == 9111 || cmd == 9000 || cmd == 53404) {
				if (gameHandler != null) {
					// 这是处理登陆前没有token的时候
					gameHandler.process(ctx, buff);
				}
			} else if (cmd == 24112 && gameHandler != null) {
				log.info("登录预览请求");
				gameHandler.process(ctx, buff);
			} else {
				if (gameHandler != null) {
					// 记录一次ip
					InetSocketAddress ipSocket = (InetSocketAddress) ctx.channel().remoteAddress();
					String clientIp = ipSocket.getAddress().getHostAddress();
					log.error("非法异常记录一次,cmd={}----------ip={}", cmd, clientIp);
				}
			}
		} finally {
			ReferenceCountUtil.release(buff);
		}
	
	}

	@Override
	public void onEvent(GameEvent event, long sequence, boolean endOfBatch) throws Exception {
		handlerGameMsg(event);
	}

}