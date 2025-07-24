package com.fengshen.server.data.write.animate;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.data.vo.user.Vo_ANIMATE_IN_CHAR;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

public class MSG_ANIMATE_IN_UI extends BaseWrite<Vo_ANIMATE_IN_CHAR> {

	@Override
	protected void writeO(ByteBuf buff, Vo_ANIMATE_IN_CHAR object) {
		GameWriteTool.writeInt(buff, object.getId());
		GameWriteTool.writeInt(buff, object.getEffectNo());
		GameWriteTool.writeInt(buff, object.getOrder());
		GameWriteTool.writeInt(buff, object.getLocate());
		GameWriteTool.writeInt(buff, object.getLoops());
		GameWriteTool.writeInt(buff, object.getInterval());
		GameWriteTool.writeInt(buff, object.getDuring());
	}

	@Override
	public int cmd() {
		return 0xB07D;
	}

}
