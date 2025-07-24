package com.fengshen.server.process.money;

import org.springframework.stereotype.Service;

import com.fengshen.server.game.GameHandler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

/**
 * 取钱
 * 
 *
 */
@Service
@Slf4j
public class CMD_WITHDRAW implements GameHandler {
	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		log.info("取钱");
//		int id = GameReadTool.readInt(buff);
//		int money = GameReadTool.readInt(buff);
//		Chara chara = GameObjectChar.getGameObjectChar().chara;
//		if (chara.balance < money) {
//			money = 0;
//			Vo_20481_0 vo_20481_0 = new Vo_20481_0();
//			vo_20481_0.msg = "钱庄没那么多钱！";
//			vo_20481_0.time = (int) (System.currentTimeMillis() / 1000L);
//			GameObjectChar.send(new M20481_0(), vo_20481_0);
//			return;
//		}
//		Chara chara2 = chara;
//		chara2.balance -= money;
//		GameUtil.addJinbi(chara2, money);
//		ListVo_65527_0 listVo_65527_0 = GameUtil.a65527(chara);
//		GameObjectChar.send(new M65527_0(), listVo_65527_0);
//		Vo_20481_0 vo_20481_2 = new Vo_20481_0();
//		vo_20481_2.msg = "#成功取出#cBA55DC" + money + "#n文钱#n。";
//		vo_20481_2.time = (int) (System.currentTimeMillis() / 1000L);
//		GameObjectChar.send(new M20481_0(), vo_20481_2);
	}

	@Override
	public int cmd() {
		return 8320;
	}
}