package com.fengshen.server.process.jiehun;

import org.springframework.stereotype.Service;

import com.fengshen.server.data.GameReadTool;
import com.fengshen.server.data.vo.Vo_61553_0;
import com.fengshen.server.data.write.jiehun.MSG_CLOSE_TIQIN_DLG;
import com.fengshen.server.game.GameCommonUtil;
import com.fengshen.server.game.GameHandler;
import com.fengshen.server.game.GameObjectChar;
import com.fengshen.server.game.GameObjectCharMng;
import com.fengshen.server.game.GameUtilRenWu;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

/**
 * a提亲结果
 * 
 *
 */
@Service
@Slf4j
public class CMD_RESPONSE_TIQIN implements GameHandler {

	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		int result = GameReadTool.readByte(buff);
		log.info("提亲结果：{}",result);
		GameObjectChar gameObjectChar = GameObjectChar.getGameObjectChar();
		GameObjectChar manGameObjectChar = GameObjectCharMng.getGameObjectChar(gameObjectChar.receiverId);
		if(manGameObjectChar != null) {
			if(result == 0) {
				//拒绝
				GameCommonUtil.sendTips("对方拒绝了你的提亲请求#15m，不要气馁再接再励#0m", manGameObjectChar);
			}else if(result == 1){
				//同意
				GameCommonUtil.sendTips("#Y"+gameObjectChar.chara.name+"#n愿意将终生托付与你，共结连理，比翼双飞，请到#Y月老#n处举行婚礼", manGameObjectChar);
				//给男方创建任务
				Vo_61553_0 vo_61553_0 = new Vo_61553_0();
				vo_61553_0.count = 1;
				vo_61553_0.task_type = "提亲";
				vo_61553_0.task_desc = "经过忠贞的考验，完成提亲，与爱人携手共步婚礼。";
				vo_61553_0.task_prompt = "找#P月老#P举行婚礼";
				vo_61553_0.refresh = 1;
				vo_61553_0.task_end_time = 1567909190;
				vo_61553_0.attrib = 1;
				vo_61553_0.reward = "#I技能|夫妻同心技能#I#I称谓|称谓'夫妻称谓'#I";
				vo_61553_0.show_name = "提亲";
				vo_61553_0.task_extra_para = gameObjectChar.chara.uuid;
				vo_61553_0.task_state = "s1";
				vo_61553_0.currentTask = "提亲s1";
				GameUtilRenWu.createTask(vo_61553_0, manGameObjectChar.chara);
			}
			manGameObjectChar.sendOne(new MSG_CLOSE_TIQIN_DLG(), null);
		}
	}

	@Override
	public int cmd() {
		return 0xB06C;
	}

}