package com.fengshen.server.process.market;

import org.springframework.stereotype.Service;

import com.fengshen.server.data.GameReadTool;
import com.fengshen.server.game.GameHandler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

/**
 * a摆摊搜索 CMD_MARKET_SEARCH_ITEM
 * 
 * 
 *
 */
@Service
@Slf4j
public class CMD_MARKET_SEARCH_ITEM implements GameHandler {
	@Override
    public void process( ChannelHandlerContext ctx,  ByteBuf buff) {
        String key = GameReadTool.readString(buff);
        String eatra = GameReadTool.readString(buff);
        int type = GameReadTool.readByte(buff);
        log.info("集市搜索， key={},eatra={},type={}",key,eatra,type);
    }

	@Override
	public int cmd() {
		return 45096;
	}
}
