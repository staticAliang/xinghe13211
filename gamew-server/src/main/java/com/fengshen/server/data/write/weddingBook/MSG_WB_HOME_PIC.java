package com.fengshen.server.data.write.weddingBook;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.data.vo.weddingBook.Vo_WB_HOME_PIC;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

public class MSG_WB_HOME_PIC extends BaseWrite<Vo_WB_HOME_PIC> {

	@Override
	protected void writeO(ByteBuf buff, Vo_WB_HOME_PIC object) {
		
		GameWriteTool.writeString(buff, object.getBookId());
		GameWriteTool.writeByte(buff, object.getFlag());
	}

	@Override
	public int cmd() {
		return 0xB1B9;
	}

}
