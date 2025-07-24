package com.fengshen.server.process.system;

import org.springframework.stereotype.Service;

import com.fengshen.server.data.GameReadTool;
import com.fengshen.server.data.vo.Vo_8131_0;
import com.fengshen.server.data.write.M8131_0;
import com.fengshen.server.game.GameHandler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

// 维护角色cookie
@Service
public class CMD_CHECK_SERVER implements GameHandler {
	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		ByteBuf type = GameReadTool.readLenBuffer2(buff);
		int cookie = GameReadTool.readInt(buff);
		Vo_8131_0 vo_8131_0 = new Vo_8131_0();
		byte[] bi = new byte[type.readableBytes()];
		type.readBytes(bi);
		vo_8131_0.buf = new String(bi);
		vo_8131_0.cookie = cookie + 1;
		new M8131_0().write(vo_8131_0);

	}

	@Override
	public int cmd() {
		return 4638;
	}
}
