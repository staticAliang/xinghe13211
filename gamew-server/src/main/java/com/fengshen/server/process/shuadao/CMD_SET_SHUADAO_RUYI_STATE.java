package com.fengshen.server.process.shuadao;

import org.springframework.stereotype.Service;

import com.fengshen.server.data.GameReadTool;
import com.fengshen.server.data.vo.Vo_45319_0;
import com.fengshen.server.data.write.MSG_REFRESH_RUYI_INFO;
import com.fengshen.server.domain.Chara;
import com.fengshen.server.game.GameCommonUtil;
import com.fengshen.server.game.GameHandler;
import com.fengshen.server.game.GameObjectChar;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

@Service
public class CMD_SET_SHUADAO_RUYI_STATE implements GameHandler {

	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		int state = GameReadTool.readByte(buff);
		Chara chara = GameObjectChar.getGameObjectChar().chara;
		if(state == 1) {
			//判断点数是否足够
			if(chara.ruyishuadao <= 0) {
				state = 0;
				GameCommonUtil.dialogOk("如意刷道令点数不足。");
			}else  {
				GameCommonUtil.dialogOk("成功开启如意刷道令。");
			}
		}else  {
			GameCommonUtil.dialogOk("你已关闭如意刷道令。");
		}
		chara.ruyishuadaoState = state;
		Vo_45319_0 vo_45319_0 = new Vo_45319_0();
		vo_45319_0.state = state;// 如意刷道令
		GameObjectChar.send(new MSG_REFRESH_RUYI_INFO(), vo_45319_0);
	}

	@Override
	public int cmd() {
		return 0xB105;
	}

}
