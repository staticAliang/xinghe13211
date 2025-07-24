package com.fengshen.server.process.dari;

import com.fengshen.db.util.RedisUtils;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 请求boos血量
 */
@Service
@Slf4j
public class CMD_WORLD_BOSS_LIFE implements GameHandler {
	@Autowired
	private RedisUtils redisUtils;

	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		GameObjectChar gameObjectChar = GameObjectChar.getGameObjectChar();
		vo_boos_life left = new vo_boos_life();
		left.setLife_str(redisUtils.get("dari_life_str"));
		left.setMax_life_str(redisUtils.get("dari_max_life_str"));
		gameObjectChar.sendOne(new MSG_WORLD_BOSS_LIFE(), left);
	}

	@Override
	public int cmd() {
		return 33012;
	}

}
