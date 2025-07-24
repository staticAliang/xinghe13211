package com.fengshen.server.process.fuling;

import org.springframework.stereotype.Service;

import com.fengshen.server.data.GameReadTool;
import com.fengshen.server.data.vo.fuling.Vo_SOUL_FULINGZHEN_DATA;
import com.fengshen.server.data.write.fuling.MSG_SOUL_FULINGZHEN_DATA;
import com.fengshen.server.domain.Chara;
import com.fengshen.server.game.GameHandler;
import com.fengshen.server.game.GameObjectChar;
import com.fengshen.server.game.GameUtil;
import com.fengshen.server.util.GameConfig;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CMD_SOUL_ACTIVE_FULINGZHEN implements GameHandler {

	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		log.info("激活附灵阵");
		GameObjectChar gameObjectChar = GameObjectChar.getGameObjectChar();
		if(gameObjectChar == null) {
			return;
		}
		Chara chara = gameObjectChar.chara;
		int oper = GameReadTool.readByte(buff);
		if(oper == 0) {
			if(chara.level<GameConfig.config.getBaseConfig().getCharaFuLingLevel()) {
				GameUtil.sendMeTips("请升至#R"+GameConfig.config.getBaseConfig().getCharaFuLingLevel()+"#n级在激活！");
				return;
			}
			//开始激活
			chara.zhenlingLevel = 1;
			chara.zhenlingStage = 1;
			//初始化加成
			chara.zhenlingPhy = 11;
			chara.zhenlingMag = 6;
			chara.zhenlingSpeed = 2;
			chara.zhenlingDef = 22;
			//更新角色信息
			GameUtil.sendUpdate(chara);
			//刷新附灵
			Vo_SOUL_FULINGZHEN_DATA data = new Vo_SOUL_FULINGZHEN_DATA();
			data.setChara(gameObjectChar.chara);
			data.setNextItemNum(1);
			gameObjectChar.sendOne(new MSG_SOUL_FULINGZHEN_DATA(), data);
			GameUtil.sendMeTips("成功激活附灵阵");
		}
	}

	@Override
	public int cmd() {
		return 0xD36C;
	}

}
