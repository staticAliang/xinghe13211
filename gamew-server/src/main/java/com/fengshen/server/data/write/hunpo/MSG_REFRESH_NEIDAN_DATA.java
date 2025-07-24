package com.fengshen.server.data.write.hunpo;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.data.vo.hunpo.Vo_REFRESH_NEIDAN_DATA;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

/**
 * 刷新内丹数据
 * 
 *
 */
public class MSG_REFRESH_NEIDAN_DATA extends BaseWrite<Vo_REFRESH_NEIDAN_DATA> {

	@Override
	protected void writeO(ByteBuf buff, Vo_REFRESH_NEIDAN_DATA object) {
		GameWriteTool.writeByte(buff, object.getIsTop());
		GameWriteTool.writeByte(buff, object.getNextState());
		GameWriteTool.writeByte(buff, object.getNextStage());
		GameWriteTool.writeShort(buff, object.getNextAttributePoint());
		GameWriteTool.writeShort(buff, object.getNextPolarPoint());
	}

	@Override
	public int cmd() {
		return 0xB180;
	}

}
