package com.fengshen.server.process.zhenbao;

import org.springframework.stereotype.Service;

import com.fengshen.db.domain.ConfigInfo;
import com.fengshen.db.service.system.ConfigInfoService;
import com.fengshen.server.data.GameReadTool;
import com.fengshen.server.domain.Chara;
import com.fengshen.server.game.GameCommonUtil;
import com.fengshen.server.game.GameData;
import com.fengshen.server.game.GameHandler;
import com.fengshen.server.game.GameObjectChar;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

/**
 * 珍宝逛摊
 * 
 *
 */
@Service
@Slf4j
public class CMD_GOLD_STALL_OPEN implements GameHandler {

	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		String key = GameReadTool.readString(buff);
		String pageStr = GameReadTool.readString(buff);
		/**
		 * 1;2;1;price
		 * 第一位:页码
		 * 第二位:1公示2:逛摊(出售中)
		 * 第三位:1:升序2:降序
		 * 第四位:排序的字段
		 */
		log.info("珍宝逛摊-----key={},pageStr={}",key,pageStr);
		GameObjectChar gameObjectChar = GameObjectChar.getGameObjectChar();
		Chara chara = gameObjectChar.chara;
		GameCommonUtil.openStallGold(chara, key, pageStr);
		ConfigInfoService configInfoService = GameData.that.configInfoService;
		ConfigInfo configInfo = configInfoService.getOneByKeyName("zhenbao_cost_type");
		if(configInfo != null) {
//			GameCommonUtil.dialogOk("请注意珍宝消耗的是#R"+configInfo.getData());
		}
	}

	@Override
	public int cmd() {
		return 0x8102;
	}

}
