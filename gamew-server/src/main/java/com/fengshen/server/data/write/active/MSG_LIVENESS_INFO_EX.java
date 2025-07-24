package com.fengshen.server.data.write.active;

import java.util.List;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.data.vo.active.Vo_LIVENESS_INFO;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

public class MSG_LIVENESS_INFO_EX extends BaseWrite<List<Vo_LIVENESS_INFO>>{

	@Override
	protected void writeO(ByteBuf buff, List<Vo_LIVENESS_INFO> object) {
		GameWriteTool.writeShort(buff, object.size());
		for(Vo_LIVENESS_INFO v:object) {
			GameWriteTool.writeString(buff, v.getName());
			GameWriteTool.writeShort(buff, v.getCount());
			GameWriteTool.writeShort(buff, v.getActiveValue());
			GameWriteTool.writeString(buff,"");
		}
	}

	@Override
	public int cmd() {
		return 0xD0E3;
	}

}
