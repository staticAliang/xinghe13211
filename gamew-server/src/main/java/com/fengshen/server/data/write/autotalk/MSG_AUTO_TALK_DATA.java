package com.fengshen.server.data.write.autotalk;

import org.springframework.stereotype.Service;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.data.vo.autotalk.Vo_AUTO_TALK_DATA;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

@Service
public class MSG_AUTO_TALK_DATA extends BaseWrite<Vo_AUTO_TALK_DATA> {

	@Override
	protected void writeO(ByteBuf buff, Vo_AUTO_TALK_DATA object) {
		GameWriteTool.writeInt(buff, object.getId());
		GameWriteTool.writeString2(buff, object.getContent());
	}

	@Override
	public int cmd() {
		return 0x8091;
	}

}
