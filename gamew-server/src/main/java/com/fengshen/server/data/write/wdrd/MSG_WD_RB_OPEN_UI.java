package com.fengshen.server.data.write.wdrd;

import org.springframework.stereotype.Service;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.data.vo.wdrd.Vo_WD_RB_OPEN_UI;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

@Service
public class MSG_WD_RB_OPEN_UI extends BaseWrite<Vo_WD_RB_OPEN_UI> {

	@Override
	protected void writeO(ByteBuf buff, Vo_WD_RB_OPEN_UI object) {
		GameWriteTool.writeShort(buff, object.getReqLevel());
		GameWriteTool.writeInt(buff, object.getCoinMin());
		GameWriteTool.writeInt(buff, object.getCoinMax());
		GameWriteTool.writeShort(buff, object.getCountMax());
		GameWriteTool.writeInt(buff, object.getDurationTime());
		GameWriteTool.writeByte(buff, object.getTimes());
	}

	@Override
	public int cmd() {
		return 0x82BD;
	}

}
