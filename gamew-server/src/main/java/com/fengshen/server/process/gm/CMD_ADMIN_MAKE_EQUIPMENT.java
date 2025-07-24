package com.fengshen.server.process.gm;

import org.springframework.stereotype.Service;

import com.fengshen.server.data.GameReadTool;
import com.fengshen.server.data.vo.Vo_4275_0;
import com.fengshen.server.data.write.M53443;
import com.fengshen.server.game.GameHandler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CMD_ADMIN_MAKE_EQUIPMENT implements GameHandler {
	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		String para1 = GameReadTool.readString(buff);
		String para2 = GameReadTool.readString(buff);
		String para3 = GameReadTool.readString(buff);
		String para4 = GameReadTool.readString(buff);
		String para5 = GameReadTool.readString(buff);
		String para6 = GameReadTool.readString(buff);
		String para7 = GameReadTool.readString(buff);
		String para8 = GameReadTool.readString(buff);
		String para9 = GameReadTool.readString(buff);
		log.info("gm生成指定装备类型, para1={},para2={},para3={},para4={},para5={},para6={},para7={},para8={},para9={}",
				para1,para2,para3,para4,para5,para6,para7,para8,para9);
		Object vo_4275_0 = new Vo_4275_0();
		@SuppressWarnings("unchecked")
		ByteBuf write = new M53443().write(vo_4275_0);
		ctx.writeAndFlush((Object) write);
	}

	@Override
	public int cmd() {
		return 53442;
	}
}
