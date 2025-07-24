package com.fengshen.server.process.system;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.fengshen.server.data.GameReadTool;
import com.fengshen.server.domain.Chara;
import com.fengshen.server.game.GameHandler;
import com.fengshen.server.game.GameObjectChar;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * 设置自动加点.
 *
 */
@Slf4j
@Service
public class CMD_SET_RECOMMEND_ATTRIB implements GameHandler{

	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		Chara chara = GameObjectChar.getGameObjectChar().chara;
		int id = GameReadTool.readInt(buff);
		int con = GameReadTool.readByte(buff);
		int wiz = GameReadTool.readByte(buff);
		int str = GameReadTool.readByte(buff);
		int dex = GameReadTool.readByte(buff);
		int auto_add = GameReadTool.readByte(buff);
		int plan = GameReadTool.readByte(buff);
		Map<String, Object> map = null;
		if(id != 0) {
			map = chara.getPetAutoAddPoint();
		}else {
			map = chara.getUserAutoAddPoint();
		}
		map.put("con",con);
		map.put("wiz",wiz);
		map.put("str",str);
		map.put("dex",dex);
		map.put("auto_add",auto_add);
		map.put("plan",plan);
		log.info("自动加点、id:{},体质:{},灵力:{},力量:{},敏捷:{},自动开启状态:{},方案:{}",id,con,wiz,str,dex,auto_add,plan);
	}

	@Override
	public int cmd() {
		return 0x2294;
	}

}
