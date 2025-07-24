package com.fengshen.server.process.gm;

import org.springframework.stereotype.Service;

import com.fengshen.server.game.GameHandler;
import com.fengshen.server.game.GameUtil;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CMD_ADMIN_SHADOW_SELF implements GameHandler {

	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
//		GameObjectChar gm = GameObjectChar.getGameObjectChar();
//		Chara chara = gm.chara;
//		Vo_APPEAR a65529 = GameUtil.a65529(chara);
//		if(gm.isHide == 1) {
//			//隐身状态
//			a65529.opacity = 0;
//			GameUtil.sendMeTips("取消隐身");
//			gm.isHide = 0;
//			
//		}else {
//			gm.isHide = 1;
//			a65529.opacity = 30;
//			GameUtil.sendMeTips("切换隐身");
//		}
//		a65529.isHide = gm.isHide;
//		gm.gameMap.sendNoMe(new M65529_0(), a65529,gm);
//		a65529.isHide = 0;
//		gm.sendOne(new M65529_0(), a65529);
		GameUtil.sendMeTips("此功能已关闭");
		log.info("gm切换隐身");
	}

	@Override
	public int cmd() {
		return 0xD038;
	}

}
