package com.fengshen.server.netty;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

public class ServerChannelInitializer extends ChannelInitializer<SocketChannel> {
	
	private static final Logger log;

	public void handlerAdded(final ChannelHandlerContext channelHandlerContext) throws Exception {
	}

	public void handlerRemoved(final ChannelHandlerContext channelHandlerContext) throws Exception {
	}

	public void exceptionCaught(final ChannelHandlerContext channelHandlerContext, final Throwable throwable)
			throws Exception {
		ServerChannelInitializer.log.error("", throwable);
	}

	protected void initChannel(final SocketChannel channel) throws Exception {
		channel.pipeline().addLast(new ChannelHandler[] { new LengthFieldBasedFrameDecoder(10240, 8, 2, 0, 4) });
		channel.pipeline().addLast(new ChannelHandler[] { new ServerHandler()});
	}

	static {
		log = LoggerFactory.getLogger(ServerChannelInitializer.class);
	}
}
