package com.fengshen.server.process.xiaozi;

import java.util.*;
import java.util.concurrent.TimeUnit;

import com.fengshen.server.data.write.task.MSG_TASK_PROMPT;
import com.fengshen.server.domain.Chara;
import com.fengshen.server.game.GameUtil;
import com.fengshen.server.process.CommonCmd;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.fengshen.db.domain.RenwuMonster;
import com.fengshen.server.data.vo.Vo_61553_0;
import com.fengshen.server.data.vo.task.Vo_AUTO_WALK;
import com.fengshen.server.data.write.task.MSG_AUTO_WALK;
import com.fengshen.server.game.GameData;
import com.fengshen.server.game.GameHandler;
import com.fengshen.server.game.GameObjectChar;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.internal.ThreadLocalRandom;
import lombok.extern.slf4j.Slf4j;

/**
 * 问道小子请求挖宝
 *
 *
 */
@Service
@Slf4j
public class CMD_XIAOZI_AUTO_WABAO implements GameHandler {

	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {


		GameObjectChar gameObjectChar = GameObjectChar.getGameObjectChar();

		if(gameObjectChar == null) {
			return;
		}
		Chara chara = gameObjectChar.chara;
		log.info(chara.name+"假人开始挖宝...");
		Vo_61553_0 vo_61553_0 = chara.taskMap.get("超级宝藏");
		if (vo_61553_0 == null) {
			vo_61553_0 = new Vo_61553_0();
		}
		List<RenwuMonster> renwuMonsters = (List<RenwuMonster>) GameData.that.baseRenwuMonsterService
				.findByType(8);
		RenwuMonster renwuMonster = renwuMonsters
				.get(ThreadLocalRandom.current().nextInt(renwuMonsters.size()));
		vo_61553_0.count = 1;
		vo_61553_0.task_type = "超级宝藏";
		vo_61553_0.task_desc = "在游戏中根据超级藏宝图进行寻宝。";
		vo_61553_0.task_prompt = "前往#Z" + renwuMonster.getMapName() + "|" + renwuMonster.getMapName() + "("
				+ renwuMonster.getX() + "," + renwuMonster.getY() + ")#Z寻宝";
		vo_61553_0.refresh = 1;
		vo_61553_0.task_end_time = 1567909190;
		vo_61553_0.attrib = 1;
		vo_61553_0.reward = "#I道行|道行#I#I潜能|潜能#I#I金钱|金钱#I#I物品|召唤令·十二生肖#I#I宠物|十二生肖=F#I";
		vo_61553_0.show_name = "超级宝藏";
		vo_61553_0.task_extra_para = "";
		vo_61553_0.task_state = "1";
		log.info("挖宝位置:{}", vo_61553_0.task_prompt);
		gameObjectChar.send(new MSG_TASK_PROMPT(), vo_61553_0);
		gameObjectChar.send(new MSG_AUTO_WALK(), new Vo_AUTO_WALK(vo_61553_0.task_prompt, "挖宝"));
		//GameUtil.removemunber(chara, goods, 1);
		chara.taskMap.put(vo_61553_0.task_type, vo_61553_0);
		Map map = new LinkedHashMap<String, Object>();
		//	map.put("action:str", value);
		map.put("name:str", "wabao");
		map.put("mapName:str", renwuMonster.getMapName());
		map.put("xy:str", renwuMonster.getX() + "," + renwuMonster.getY());
		map.put("menuItem:str","wabao");
		gameObjectChar.sendOne(new CommonCmd(9888), map);
		gameObjectChar.setGatherType("chaoji_goon");
	}
	@Override
	public int cmd() {
		return 9333;
	}

}
