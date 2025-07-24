package com.fengshen.server.data.write.fight.c;

import java.util.Map;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.data.vo.Vo_41027_0;
import com.fengshen.server.domain.BuildFieldsNew;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

public class MSG_C_UPDATE_COMBAT_INFO extends BaseWrite<Vo_41027_0> {

	protected void writeO(ByteBuf buff, Vo_41027_0 object) {
		
		GameWriteTool.writeInt(buff, object.id);
		GameWriteTool.writeByte(buff, object.isSet);

		GameWriteTool.writeShort(buff, object.buildFields.size());
		for (Map.Entry<String, Object> entry : object.buildFields.entrySet()) {
			if (BuildFieldsNew.data.get(entry.getKey()) != null) {
				BuildFieldsNew.get(entry.getKey()).write(buff, entry.getValue());
			}
		}
	}

	public int cmd() {
		return 41027;
	}
}
