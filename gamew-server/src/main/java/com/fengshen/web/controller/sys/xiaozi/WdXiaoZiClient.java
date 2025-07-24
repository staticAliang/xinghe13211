package com.fengshen.web.controller.sys.xiaozi;

import com.fengshen.server.game.GameCore;
import com.fengshen.server.util.GameConfig;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
@Slf4j
public class WdXiaoZiClient {
    public boolean startClient(){
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        try{
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(eventLoopGroup);
            bootstrap.channel(NioSocketChannel.class);
            bootstrap.option(ChannelOption.SO_REUSEADDR, true);
            bootstrap.handler(new ChannelHandler(){
                @Override
                public void handlerAdded(ChannelHandlerContext channelHandlerContext) throws Exception {
                    final Map<String, Object> info = new HashMap<String, Object>();
                    info.put("online", 0);
                    info.put("ctx", channelHandlerContext);
                    info.put("name", "");
                    GameCore.xiaoziClientInfo.put(UUID.randomUUID().toString(), info);
                }

                @Override
                public void handlerRemoved(ChannelHandlerContext channelHandlerContext) throws Exception {

                }

                @Override
                public void exceptionCaught(ChannelHandlerContext channelHandlerContext, Throwable throwable) throws Exception {

                }
            });

            bootstrap.connect(GameConfig.serverIp,GameConfig.port).sync().channel();

        }catch (Exception e){
            log.info("问道小子启动失败{}",e);
            e.printStackTrace();
            return false;
        }
        return true;

    }


 /*   public static void main(String[] args) throws Exception{
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();

        try{
            Map<String,ChannelHandlerContext > map = new HashMap<>();
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(eventLoopGroup);
            bootstrap.channel(NioSocketChannel.class);
            bootstrap.option(ChannelOption.SO_REUSEADDR, true);
            bootstrap.handler(new ChannelHandler(){
                        @Override
                        public void handlerAdded(ChannelHandlerContext channelHandlerContext) throws Exception {
                            map.put("ctx",channelHandlerContext);
                        }

                        @Override
                        public void handlerRemoved(ChannelHandlerContext channelHandlerContext) throws Exception {

                        }

                        @Override
                        public void exceptionCaught(ChannelHandlerContext channelHandlerContext, Throwable throwable) throws Exception {

                        }
                    });

            Channel channel = bootstrap.connect(SERVER_HOST,BIND_PORT).sync().channel();


            while (true){
                ChannelHandlerContext ctx =  map.get("ctx");
                if(ctx != null){

                    final LinkedHashMap<String, Object> gameMap = new LinkedHashMap<String, Object>();
                    gameMap.put("uuid","123");
                    gameMap.put("name","小妮子");

                    ctx.writeAndFlush((new CommonWrite(9111)).write(gameMap, new boolean[0]));
                    break;
                }
                System.out.printf("");
            }

        }finally {
            eventLoopGroup.shutdownGracefully();
        }
    }*/


}
