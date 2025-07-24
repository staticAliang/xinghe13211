package com.fengshen.server.process.wdrb;

import org.springframework.stereotype.Service;

import com.fengshen.server.data.vo.wdrd.Vo_WD_RB_OPEN_UI;
import com.fengshen.server.data.write.wdrd.MSG_WD_RB_OPEN_UI;
import com.fengshen.server.game.GameHandler;
import com.fengshen.server.game.GameObjectChar;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CMD_WD_RB_OPEN_UI implements GameHandler {

	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		log.info("打开世界红包发送界面");
		GameObjectChar gameObjectChar = GameObjectChar.getGameObjectChar();
		Vo_WD_RB_OPEN_UI vo = new Vo_WD_RB_OPEN_UI();
		vo.setCoinMax(100000);
		vo.setCoinMin(10000);
		vo.setCountMax(1);
		vo.setDurationTime(10);
		vo.setReqLevel(35);
		vo.setTimes(1);
		gameObjectChar.sendOne(new MSG_WD_RB_OPEN_UI(), vo);
	}

	@Override
	public int cmd() {
		return 0x82BC;
	}

}
