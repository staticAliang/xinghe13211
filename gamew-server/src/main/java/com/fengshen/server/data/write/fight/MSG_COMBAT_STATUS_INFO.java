package com.fengshen.server.data.write.fight;

import java.util.Map;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.data.vo.fight.Vo_COMBAT_STATUS_INFO;
import com.fengshen.server.domain.BuildFieldsNew;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

public class MSG_COMBAT_STATUS_INFO extends BaseWrite<Vo_COMBAT_STATUS_INFO>{

	@Override
	protected void writeO(ByteBuf buff, Vo_COMBAT_STATUS_INFO object) {
		
		GameWriteTool.writeInt(buff, object.getObjId());
		GameWriteTool.writeString(buff, object.getStatusType());
		GameWriteTool.writeShort(buff, object.getBuildFields().size());
		for (Map.Entry<String, Object> entry : object.getBuildFields().entrySet()) {
			if (BuildFieldsNew.data.get(entry.getKey()) != null) {
				BuildFieldsNew.get(entry.getKey()).write(buff, entry.getValue());
			}
		}
		GameWriteTool.writeByte(buff, object.getIsCanUseHYJJ());
		GameWriteTool.writeByte(buff, object.getZhenfaPolar());
	}

	@Override
	public int cmd() {
		return 0xA008;
	}

}
