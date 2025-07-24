package com.fengshen.server.data.write.weddingBook;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.data.vo.weddingBook.Vo_WB_HOME_INFO;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

public class MSG_WB_HOME_INFO extends BaseWrite<Vo_WB_HOME_INFO> {

	@Override
	protected void writeO(ByteBuf buff, Vo_WB_HOME_INFO object) {

		GameWriteTool.writeString(buff, object.getBookId());
		GameWriteTool.writeInt(buff, object.getWedding_start_ti());
		GameWriteTool.writeInt(buff, object.getWedding_end_ti());
		GameWriteTool.writeString(buff, object.getHus_name());
		GameWriteTool.writeString(buff, object.getWife_name());
		GameWriteTool.writeString(buff, object.getHome_img());
	}

	@Override
	public int cmd() {
		return 0xB1A4;
	}

}
