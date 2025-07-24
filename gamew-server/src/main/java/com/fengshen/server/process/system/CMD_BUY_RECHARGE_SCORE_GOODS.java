package com.fengshen.server.process.system;

import org.springframework.stereotype.Service;

import com.fengshen.server.data.GameReadTool;
import com.fengshen.server.game.GameData;
import com.fengshen.server.game.GameHandler;
import com.fengshen.server.game.GameObjectChar;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;


/**
 * 购买积分道具
 */
@Service
public class CMD_BUY_RECHARGE_SCORE_GOODS implements GameHandler {
	
	
    public void process(ChannelHandlerContext ctx, ByteBuf buff) {
    	GameObjectChar gameObjectChar = GameObjectChar.getGameObjectChar();
        int no = GameReadTool.readByte(buff);
        int num = GameReadTool.readShort(buff);
		GameData.that.chargePointMng.buyGoods(gameObjectChar, no, num);
    }


    public int cmd() {
        return 53446;
    }
}