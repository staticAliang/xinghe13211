package com.fengshen.server.process.system;

import org.springframework.stereotype.Service;

import com.fengshen.server.data.GameReadTool;
import com.fengshen.server.data.vo.Vo_20481_0;
import com.fengshen.server.data.vo.Vo_8165_0;
import com.fengshen.server.data.write.M20481_0;
import com.fengshen.server.data.write.M45185_0;
import com.fengshen.server.data.write.M8165_0;
import com.fengshen.server.domain.Chara;
import com.fengshen.server.game.GameData;
import com.fengshen.server.game.GameHandler;
import com.fengshen.server.game.GameObjectChar;
import com.fengshen.server.game.GameObjectCharMng;
import com.fengshen.server.game.GameUtil;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

/**
 * 客户端请求震动提醒玩家
 * 
 *
 */
@Service
@Slf4j
public class CMD_SHOCK implements GameHandler {
	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		String type = GameReadTool.readString(buff);
		String para = GameReadTool.readString(buff);
		log.info("客户端请求震动提醒玩家");
		Chara chara = GameObjectChar.getGameObjectChar().chara;
		if ("team".equals(type)) {
			GameObjectChar toGameObject = GameObjectCharMng.getGameObjectCharByUUid(para);
			if (toGameObject != null) {
				Chara chara2 = toGameObject.chara;
				if (GameData.that.redisUtils.get("SHOCK_TIME_OUT_" + chara2.id) != null) {
					GameUtil.sendMeTips("已有人提醒,请勿频繁发送。");
					return;
				}
				Vo_8165_0 vo_8165_0 = new Vo_8165_0();
				vo_8165_0.msg = "已向#Y" + chara2.name + "#n发送震动提醒。";
				vo_8165_0.active = 0;
				GameObjectChar.send(new M8165_0(), vo_8165_0);
				Vo_20481_0 vo_20481_0 = new Vo_20481_0();
				vo_20481_0.msg = "#Y" + chara.name + "#n在队伍中向你发送了一次震动提醒";
				vo_20481_0.time = (int) (System.currentTimeMillis() / 1000L);
				GameObjectCharMng.getGameObjectChar(chara2.id).sendOne(new M20481_0(), vo_20481_0);
				GameObjectCharMng.getGameObjectChar(chara2.id).sendOne(new M45185_0(), null);
				// 1分钟之内无法再次发起震动
				GameData.that.redisUtils.set("SHOCK_TIME_OUT_" + chara2.id, "", 30);
			}
		}
	}

	@Override
	public int cmd() {
		return 45174;
	}
}