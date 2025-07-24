package com.fengshen.server.data.write.fight.c;

import java.util.Map;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.data.vo.fight.Vo_C_UPDATE_IMPROVEMENT;
import com.fengshen.server.domain.BuildFieldsNew;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

public class MSG_C_UPDATE_IMPROVEMENT extends BaseWrite<Vo_C_UPDATE_IMPROVEMENT> {

	@Override
	protected void writeO(ByteBuf buff, Vo_C_UPDATE_IMPROVEMENT object) {
		GameWriteTool.writeInt(buff, object.getId());
		for (Map.Entry<String, Object> entry : object.getBuildFields().entrySet()) {
			if (BuildFieldsNew.data.get(entry.getKey()) != null) {
				BuildFieldsNew.get(entry.getKey()).write(buff, entry.getValue());
			}
		}
	}

	@Override
	public int cmd() {
		return 0xFDDF;
	}

}
