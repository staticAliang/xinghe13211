package com.fengshen.server.data.write.user;

import org.springframework.stereotype.Service;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.data.vo.user.Vo_PLAY_CHAR_ACTION;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

@Service
public class MSG_PLAY_CHAR_ACTION extends BaseWrite<Vo_PLAY_CHAR_ACTION> {

	@Override
	protected void writeO(ByteBuf buff, Vo_PLAY_CHAR_ACTION object) {
		
		GameWriteTool.writeInt(buff, object.getId());
		GameWriteTool.writeInt(buff, object.getAction());
		GameWriteTool.writeInt(buff, object.getLoops());
		
	}

	@Override
	public int cmd() {
		return 0xB116;
	}

}
