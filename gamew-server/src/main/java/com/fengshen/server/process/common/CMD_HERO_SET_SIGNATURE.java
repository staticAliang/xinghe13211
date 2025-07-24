package com.fengshen.server.process.common;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

import org.springframework.stereotype.Service;

import com.fengshen.server.data.GameReadTool;
import com.fengshen.server.game.GameHandler;
import com.fengshen.server.service.HeroPubService;

/**
 * 修改英雄会留言
 */
@Service
public class CMD_HERO_SET_SIGNATURE implements GameHandler {
    public void process(ChannelHandlerContext ctx, ByteBuf buff) {
        int id = GameReadTool.readInt(buff);
        String msg = GameReadTool.readString(buff);
        HeroPubService.changeNotice(id, msg);
    }

    public int cmd() {
        return 20690;
    }
}
