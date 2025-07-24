package com.fengshen.server.data.write.fight.lc;

import java.util.List;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.domain.BuildFieldsNew;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

public class MSG_LC_UPDATE extends BaseWrite<Object> {

	

	@Override
	protected void writeO(ByteBuf buff, Object object) {
		@SuppressWarnings("unchecked")
		List<Object> object2 = (List<Object>) object;
		GameWriteTool.writeInt(buff, (Integer) object2.get(0));
		GameWriteTool.writeShort(buff, 1);
		BuildFieldsNew.get("life").write(buff, object2.get(1));
	}
	
	@Override
	public int cmd() {
		return 0xF9D3;
	}
}