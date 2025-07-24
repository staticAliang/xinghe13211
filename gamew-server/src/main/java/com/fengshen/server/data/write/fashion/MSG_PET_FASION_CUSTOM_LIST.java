package com.fengshen.server.data.write.fashion;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.data.vo.Vo_41488_0;
import com.fengshen.server.data.vo.Vo_41488_0.Items;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

public class MSG_PET_FASION_CUSTOM_LIST extends BaseWrite<Vo_41488_0> {

	@Override
	protected void writeO(ByteBuf buff, Vo_41488_0 object) {

		GameWriteTool.writeByte(buff, object.flag);
		GameWriteTool.writeByte(buff, object.label);
		GameWriteTool.writeString(buff, object.para);
		GameWriteTool.writeShort(buff, object.items.size());
		for(Items item:object.items) {
			GameWriteTool.writeString(buff, item.getName());
			GameWriteTool.writeInt(buff, item.getPrice());
		}		
	}

	@Override
	public int cmd() {
		return 0x5106;
	}

}
