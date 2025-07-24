package com.fengshen.server.process.pet;

import org.springframework.stereotype.Service;

import com.fengshen.server.data.GameReadTool;
import com.fengshen.server.data.vo.Vo_40987_0;
import com.fengshen.server.data.write.M40987_0;
import com.fengshen.server.game.GameHandler;
import com.fengshen.server.game.GameObjectChar;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

/**
 * 洗天技
 * 
 *
 */
@Service
@Slf4j
public class CMD_PET_SPECIAL_SKILL implements GameHandler {
	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		int petId = GameReadTool.readInt(buff);
		String type = GameReadTool.readString(buff);
		log.info("洗天技，petId={},type",petId,type);
		Vo_40987_0 vo_40987_0 = new Vo_40987_0();
		vo_40987_0.petId = petId;
		vo_40987_0.count = 0;
		GameObjectChar.send(new M40987_0(), vo_40987_0);
	}

	@Override
	public int cmd() {
		return 40986;
	}
}
