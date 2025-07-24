package com.fengshen.server.process.autotalk;

import java.util.List;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.fengshen.server.data.GameReadTool;
import com.fengshen.server.data.vo.autotalk.Vo_AUTO_TALK_DATA;
import com.fengshen.server.data.write.autotalk.MSG_AUTO_TALK_DATA;
import com.fengshen.server.domain.AutoTalkVo;
import com.fengshen.server.domain.Chara;
import com.fengshen.server.domain.Petbeibao;
import com.fengshen.server.game.GameHandler;
import com.fengshen.server.game.GameObjectChar;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

/**
 * 自动喊话信息
 * @author aaa
 *
 */
@Service
@Slf4j
public class CMD_AUTO_TALK_DATA implements GameHandler {

	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		int id = GameReadTool.readInt(buff);
		log.info("请求自动喊话，{}",id);
		GameObjectChar gameObjectChar = GameObjectChar.getGameObjectChar();
		Chara chara = gameObjectChar.chara;
		for(Petbeibao pet:chara.pets) {
			//设置宠物的
			if(pet.id == id) {
				List<AutoTalkVo> autoTalk = pet.autoTalk;
				if(autoTalk != null) {
					GameObjectChar.send(new MSG_AUTO_TALK_DATA(), new Vo_AUTO_TALK_DATA(id, JSONObject.toJSONString(autoTalk)));
				}else {
					GameObjectChar.send(new MSG_AUTO_TALK_DATA(), new Vo_AUTO_TALK_DATA(id, ""));
				}
				return;
			}
		}
		List<AutoTalkVo> autoTalk = chara.autoTalk;
		if(autoTalk != null) {
			GameObjectChar.send(new MSG_AUTO_TALK_DATA(), new Vo_AUTO_TALK_DATA(id, JSONObject.toJSONString(autoTalk)));
		}else {
			GameObjectChar.send(new MSG_AUTO_TALK_DATA(), new Vo_AUTO_TALK_DATA(id, ""));
		}
	}

	@Override
	public int cmd() {
		return 0x8090;
	}

}
