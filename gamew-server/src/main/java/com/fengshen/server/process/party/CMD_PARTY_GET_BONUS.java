package com.fengshen.server.process.party;

import org.springframework.stereotype.Service;

import com.fengshen.server.data.GameReadTool;
import com.fengshen.server.data.constant.ClientButtonIdConst;
import com.fengshen.server.data.vo.system.Vo_GENERAL_NOTIFY;
import com.fengshen.server.data.write.system.MSG_GENERAL_NOTIFY;
import com.fengshen.server.game.GameHandler;
import com.fengshen.server.game.GameObjectChar;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

/**
 * 帮派福利
 * 
 *
 */
@Service
@Slf4j
public class CMD_PARTY_GET_BONUS implements GameHandler {

	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		//3帮派俸禄、4帮派功臣
		int type = GameReadTool.readByte(buff);
		GameObjectChar gameObjectChar = GameObjectChar.getGameObjectChar();
		Vo_GENERAL_NOTIFY notify = new Vo_GENERAL_NOTIFY();
		if(type == 3) {
			//查询出这人上周的活力值
			notify.notify = ClientButtonIdConst.NOTIFY_QUERY_PARTY_SALARY;
			notify.para = gameObjectChar.chara.contrib+"";
		}else if(type ==4) {
			
		}
		gameObjectChar.sendOne(new MSG_GENERAL_NOTIFY(), notify);
		log.info("帮派福利界面");
	}

	@Override
	public int cmd() {
		return 0x8012;
	}

}
