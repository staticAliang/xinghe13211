package com.fengshen.server.disruptor;

import com.fengshen.server.game.GameObjectChar;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GameEvent {
    private GameEventType logicEventType;
    private ByteBuf byteBuf;
    private ChannelHandlerContext context;
    private GameObjectChar session;
    private int cmd;
    private int f1;
    
    private int f2;
}