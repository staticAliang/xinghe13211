package com.fengshen.server.process.shuadao;

import org.springframework.stereotype.Service;

import com.fengshen.server.data.GameReadTool;
import com.fengshen.server.data.write.M65527_0;
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
public class CMD_BUY_SHUADAO_RUYI_POINT implements GameHandler {

	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		int num = GameReadTool.readByte(buff);
		Chara chara = GameObjectChar.getGameObjectChar().chara;
		if(chara.cash < num*2000000) {
			GameCommonUtil.dialogOk("金钱不足");
			return;
		}
		chara.cash-=num*2000000;
		chara.ruyishuadao += num*200;
		GameCommonUtil.dialogOk("你已成功购买#R" +num*200+"#W点如意刷道令");
		log.info("购买如意刷道令点数:{}",num);
		GameObjectChar.send(new M65527_0(), GameUtil.a65527(chara));
		
	}

	@Override
	public int cmd() {
		return 0xB106;
	}

}
