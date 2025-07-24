package com.fengshen.server.process.wdrb;

import org.springframework.stereotype.Service;

import com.fengshen.server.data.vo.wdrd.Vo_WD_RB_SEND_REDBAG;
import com.fengshen.server.data.write.wdrd.MSG_WD_RB_SEND_REDBAG;
import com.fengshen.server.domain.Chara;
import com.fengshen.server.game.GameCommonUtil;
import com.fengshen.server.game.GameHandler;
import com.fengshen.server.game.GameObjectChar;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CMD_WD_RB_SEND_REDBAG implements GameHandler {

	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		
		log.info("发送世界红包");
		GameObjectChar gameObjectChar = GameObjectChar.getGameObjectChar();
		Chara chara = gameObjectChar.chara;
		Vo_WD_RB_SEND_REDBAG red = new Vo_WD_RB_SEND_REDBAG();
		red.setRedbagGid(chara.uuid);
		red.setText("测试红包");
		red.setStartTime((int) (System.currentTimeMillis()/1000L));
		red.setOwnerGid(chara.uuid);
		red.setOwnerName(chara.name);
		red.setIcon(chara.waiguan);
		red.setLevel(chara.level);
		red.setTimes(35);
		red.setIsNew(1);
		gameObjectChar.gameMap.send(new MSG_WD_RB_SEND_REDBAG(), red);
	}

	@Override
	public int cmd() {
		return 0x82BE;
	}

}
