package com.fengshen.server.process.task;

import org.springframework.stereotype.Service;

import com.fengshen.server.data.vo.Vo_61553_0;
import com.fengshen.server.data.write.task.MSG_TASK_PROMPT;
import com.fengshen.server.domain.Chara;
import com.fengshen.server.game.GameHandler;
import com.fengshen.server.game.GameObjectChar;
import com.fengshen.server.game.GameUtil;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;


/**
 * a领取门派转换任务
 * @author aaa
 *
 */
@Service
@Slf4j
public class CMD_CHANGE_POLAR_FETCH_TASK implements GameHandler {

	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		
		GameObjectChar gameObjectChar = GameObjectChar.getGameObjectChar();
		Chara chara = gameObjectChar.chara;
		if(chara.level<70) {
			GameUtil.sendMeTips("等级低于70无法领取任务");
			return;
		}
		//创建领取任务
		Vo_61553_0 vo_61553_0 = new Vo_61553_0();
		vo_61553_0.count = 1;
		vo_61553_0.task_type = "门派转换";
		vo_61553_0.task_desc = "完成门派转换任务后可以完成门派转换，转换时不可携带武器，装备亦不会一同转换，转换后不会重置属性点和相性点分配，转换后更换角色的形象，性别保留。";
		vo_61553_0.task_prompt = "找#P千面怪|E=门派转换#P完成门派转换";
		vo_61553_0.refresh = 1;
		vo_61553_0.task_end_time = 1567909190;
		vo_61553_0.attrib = 1;
		vo_61553_0.reward = "";
		vo_61553_0.show_name = "门派转换";
		vo_61553_0.task_extra_para = "";
		vo_61553_0.task_state = "";
		GameObjectChar.send(new MSG_TASK_PROMPT(), vo_61553_0, chara.id);
		chara.taskMap.put("门派转换", vo_61553_0);
		GameUtil.sendMeTips("门派转换任务领取成功，快去完成吧");
		log.info("领取门派转换任务");
	}

	@Override
	public int cmd() {
		return 0x5292;
	}

}
