package com.fengshen.server.process.chat;

import org.springframework.stereotype.Service;

import com.fengshen.server.data.GameReadTool;
import com.fengshen.server.domain.Chara;
import com.fengshen.server.game.GameCommonUtil;
import com.fengshen.server.game.GameHandler;
import com.fengshen.server.game.GameObjectChar;
import com.fengshen.server.game.GameUtil;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CMD_DECORATION_APPLY implements GameHandler {

	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		GameObjectChar gameObjectChar = GameObjectChar.getGameObjectChar();
		Chara chara = gameObjectChar.chara;
		int count = GameReadTool.readShort(buff);
		for(int i = 0;i<count;i++) {
			String type = GameReadTool.readString(buff);
			String name = GameReadTool.readString(buff);
			if("chat_head".equals(type)) {
				chara.useChatHead = name;
			}else if("chat_floor".equals(type)) {
				chara.useChatFloor = name;
			}
			log.info("使用装饰，type={};name={}",type,name);
		}
		GameCommonUtil.refreshChatStyle(gameObjectChar,3);
		GameUtil.sendMeTips("操作成功！");
	}

	@Override
	public int cmd() {
		return 0xA20B;
	}

}
