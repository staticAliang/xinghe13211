package com.fengshen.server.netty;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.atomic.AtomicInteger;

import com.fengshen.server.game.*;
import org.springframework.stereotype.Service;

import com.fengshen.core.util.SpringBeanUtils;
import com.fengshen.db.service.system.BlackListService;
import com.fengshen.server.data.GameReadTool;
import com.fengshen.server.exception.PetPackOverflowException;
import com.fengshen.server.util.GameConfig;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;
import io.netty.util.ReferenceCountUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ServerHandler extends ChannelInboundHandlerAdapter {

	public static final AttributeKey<GameObjectChar> akey;
	public static final AttributeKey<String> macKey;
	public AtomicInteger readIdleTimes = new AtomicInteger(0);

	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		//授权到期
		if (GameCore.isExpire.get()) {
			ctx.close();
		}
		// 查询是否封号
		InetSocketAddress ipSocket = (InetSocketAddress) ctx.channel().remoteAddress();
		BlackListService blackListService = SpringBeanUtils.getBean(BlackListService.class);
		String clientIp = ipSocket.getAddress().getHostAddress();
		if (blackListService.getCountByDataType(clientIp) > 0) {
			ctx.close();
		}
		log.info("客户端连接成功！,ip:{}, id:{}", clientIp, ctx.channel().id().asLongText());
	}

	@Override
	public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
	}

	// 当角色掉线或者退出游戏的时候，将它从全局管理器中移除
	public void channelInactive(final ChannelHandlerContext ctx) throws Exception {
	}

	/**
	 * 处理消息
	 * 
	 * @param cmd         协议
	 * @param buff        字节缓冲区
	 * @param gameHandler 处理器
	 * @param session     会话
	 * @param ctx         上下文
	 */
	public void processMsg(int cmd, ByteBuf buff, GameHandler gameHandler, GameObjectChar session,
			ChannelHandlerContext ctx) {
		// 用户退出游戏重新登录
		if (session != null && gameHandler != null) {
			try {
				gameHandler.process(ctx, buff);
			} catch (PetPackOverflowException e) {
				log.error("{}", e);
				GameCommonUtil.dialogOk("{}", session.chara.id);
			} catch (Exception e) {
				log.error("{}", e);
			}
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
				gameHandler.process(ctx, buff);
			}
		}

	}

	public void exceptionCaught(final ChannelHandlerContext ctx, final Throwable cause) {
		if (cause instanceof IOException) {
			return;
		}
		log.error("服务器出现错误,{}", cause);
	}

	static {
		akey = AttributeKey.newInstance("session");
		macKey = AttributeKey.valueOf("currMac");
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		ByteBuf buff = (ByteBuf) msg;
		int f1 = GameReadTool.readInt(buff);
		int f2 = GameReadTool.readShort(buff);
		int cmd = GameReadTool.readShort(buff);
		try {
			commonMsg(cmd, f1, f2, ctx, buff);
		} finally {
			ReferenceCountUtil.release(buff);
		}
	}

	public void commonMsg(int cmd, int f1, int f2, ChannelHandlerContext ctx, ByteBuf buff) {
		Attribute<GameObjectChar> attr = ctx.channel().attr(ServerHandler.akey);
		GameObjectChar session = null;
		if (attr != null && attr.get() != null) {
			session = attr.get();
			GameObjectChar.GAMEOBJECTCHAR_THREAD_LOCAL.set(session);
		}
		if (cmd != 4274 && cmd != 41008) {
			log.info("十进制CMD:{},16进制CMD:{}", cmd, Integer.toString(cmd, 16));
		}
		GameHandler gameHandler = GameCore.gameHandlerMap.get(cmd);
		BlackListService blackListService = SpringBeanUtils.getBean(BlackListService.class);
		InetSocketAddress ipSocket = (InetSocketAddress) ctx.channel().remoteAddress();
		String clientIp = ipSocket.getAddress().getHostAddress();
		// ip被封号
		if (blackListService.getCountByDataType(clientIp) > 0) {
			ctx.close();
		} else if (session != null && session.characters != null) {
			if (session.characters.getBlock() != null && session.characters.getBlock() == 1) {
				ctx.close();
				return;
			} else {
				if (session.tickCount.get() == 0) {
					// 进行初始化
					session.tickCount.set(f1);
				} else {
					//wpe
					if(session.privilege == 0 && GameConfig.config.getWpeConfig().getStatus() == 1) {
						if (f1 < session.tickCount.get()) {
							return;
						}
					}
					session.tickCount.set(f1);
				}
				//GameUtil.sendMeTips("接口编号，数据包标识："+cmd+" 转换16进制为："+Integer.toString(cmd, 16));
				//处理消息
				processMsg(cmd, buff, gameHandler, session, ctx);
			}
		} else {
			// 处理消息
			processMsg(cmd, buff, gameHandler, session, ctx);
		}
	}
}
