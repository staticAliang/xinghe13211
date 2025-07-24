package com.fengshen.server.process.active;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.fengshen.server.data.vo.Vo_41480_0;
import com.fengshen.server.data.write.M41480_0;
import com.fengshen.server.domain.Chara;
import com.fengshen.server.game.GameHandler;
import com.fengshen.server.game.GameObjectChar;
import com.fengshen.server.game.GameUtil;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

/**
 * 请求神秘大礼数据 -- 砸蛋版本
 * 
 *
 */
@Service
@Slf4j
public class CMD_SHENMI_DALI_OPEN implements GameHandler {
	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		log.info("请求神秘大礼数据 -- 砸蛋版本");
		Chara chara = GameObjectChar.getGameObjectChar().chara;
		Date date = new Date(chara.updatetime);
		boolean isnow = GameUtil.isNow(date);
		if (!isnow && chara.online_time != 0L) {
			chara.online_time = 0L;
			for (int i = 0; i < chara.shenmiliwu.size(); ++i) {
				chara.shenmiliwu.get(i).online_time = 0;
				chara.shenmiliwu.get(i).name = "";
				chara.shenmiliwu.get(i).brate = 0;
			}
		}
		List<Vo_41480_0> list = new LinkedList<Vo_41480_0>();
		for (int j = 0; j < chara.shenmiliwu.size(); ++j) {
			Vo_41480_0 vo_41480_0 = new Vo_41480_0();
			vo_41480_0.online_time = (int) (chara.online_time / 1000L
					+ (System.currentTimeMillis() - chara.uptime) / 1000L);
			vo_41480_0.time = chara.shenmiliwu.get(j).time;
			vo_41480_0.name = chara.shenmiliwu.get(j).name;
			vo_41480_0.index = chara.shenmiliwu.get(j).index;
			vo_41480_0.brate = chara.shenmiliwu.get(j).brate;
			list.add(vo_41480_0);
		}
		GameObjectChar.send(new M41480_0(), list);
	}

	@Override
	public int cmd() {
		return 41479;
	}
}
