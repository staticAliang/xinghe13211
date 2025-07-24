package com.fengshen.server.process.fuling;

import org.springframework.stereotype.Service;

import com.fengshen.server.data.vo.fuling.Vo_SOUL_FULINGZHEN_DATA;
import com.fengshen.server.data.write.fuling.MSG_SOUL_FULINGZHEN_DATA;
import com.fengshen.server.domain.Chara;
import com.fengshen.server.game.GameCommonUtil;
import com.fengshen.server.game.GameHandler;
import com.fengshen.server.game.GameObjectChar;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

/**
 * 请求附灵数据
 *
 */
@Service
@Slf4j
public class CMD_SOUL_REQ_FULING_DATA implements GameHandler {

	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		GameObjectChar gameObjectChar = GameObjectChar.getGameObjectChar();
		if(gameObjectChar == null) {
			return;
		}
		log.info("请求附灵数据");
		Chara chara = gameObjectChar.chara;
		Vo_SOUL_FULINGZHEN_DATA data = new Vo_SOUL_FULINGZHEN_DATA();
		data.setChara(chara);
		//查询铸灵石的数量
		int num = GameCommonUtil.getGoodsNum(chara, "铸灵石");
		data.setNextItemNum(num);
		gameObjectChar.sendOne(new MSG_SOUL_FULINGZHEN_DATA(), data);
	}

	@Override
	public int cmd() {
		return 0xD374;
	}

}