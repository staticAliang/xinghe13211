package com.fengshen.server.data.write;

import io.netty.buffer.ByteBuf;

import org.springframework.stereotype.Service;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.domain.BuildFields;
import com.fengshen.server.netty.BaseWrite;

import java.util.List;

// MSG_C_UPDATE 更新角色法力
@Service
public class M64981_Fight_Mana extends BaseWrite<List<Object>> {
	@Override
	protected void writeO(ByteBuf writeBuf, List<Object> object) {
		GameWriteTool.writeInt(writeBuf, (Integer) object.get(0));
		GameWriteTool.writeShort(writeBuf, 1);
		BuildFields.get("max_mana").write(writeBuf, object.get(1));
	}

	@Override
	public int cmd() {
		return 64981;
	}
}
