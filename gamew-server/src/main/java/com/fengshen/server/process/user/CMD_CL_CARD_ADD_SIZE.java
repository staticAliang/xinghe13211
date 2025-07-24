package com.fengshen.server.process.user;

import org.springframework.stereotype.Service;

import com.fengshen.server.data.GameReadTool;
import com.fengshen.server.game.GameHandler;
import com.fengshen.server.game.GameObjectChar;
import com.fengshen.server.game.GameUtil;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

@Service
public class CMD_CL_CARD_ADD_SIZE implements GameHandler{

	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		int count = GameReadTool.readShort(buff);
		//变身卡购买空间--12个元宝=1个空间
		GameUtil.confirm(GameObjectChar.getGameObjectChar().chara, 
				"你确定花费#R"+(count*12)+"元宝#n购买#R"+count+"#n个卡套空间?", "addCardSize-"+count);
	}

	@Override
	public int cmd() {
		return 0x802A;
	}

}
