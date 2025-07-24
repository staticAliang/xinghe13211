package com.fengshen.server.data.write.cangbaotu;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.data.vo.cangbaotu.Vo_AUTO_CANGBAOTU_READY_SEARCH;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

public class MSG_AUTO_CANGBAOTU_READY_SEARCH extends BaseWrite<Vo_AUTO_CANGBAOTU_READY_SEARCH> {

	@Override
	protected void writeO(ByteBuf buff, Vo_AUTO_CANGBAOTU_READY_SEARCH object) {
		
		GameWriteTool.writeString(buff, object.getPara());
		GameWriteTool.writeString(buff, object.getShangguAutoDesc());
		GameWriteTool.writeByte(buff, object.getHasSgyw());
		GameWriteTool.writeString(buff, object.getTips());
	}

	@Override
	public int cmd() {
		return 0xB38F;
	}

}
