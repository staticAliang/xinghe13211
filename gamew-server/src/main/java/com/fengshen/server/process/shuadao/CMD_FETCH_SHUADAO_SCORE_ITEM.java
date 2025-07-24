package com.fengshen.server.process.shuadao;

import org.springframework.stereotype.Service;

import com.fengshen.server.data.GameReadTool;
import com.fengshen.server.game.GameHandler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

/**
 * 请求刷道积分道具
 * 
 *
 */
@Slf4j
@Service
public class CMD_FETCH_SHUADAO_SCORE_ITEM implements GameHandler {

	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		int type = GameReadTool.readByte(buff);
		int index = GameReadTool.readByte(buff);
//		Chara chara = GameObjectChar.getGameObjectChar().chara;
//		GameObjectChar.send(new M45217_0(), new Integer[] {chara.shuadaoScore,chara.shuadaoFetchState,1});
		log.info("请求刷道积分道具，type={},index={}",type,index);
	}

	@Override
	public int cmd() {
		return 0xB0A6;
	}

}
