package com.fengshen.server.data.write;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.domain.BuildFieldsNew;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

// MSG_C_UPDATE 更新角色血气
@Service
public class M64981_Fight_Blood extends BaseWrite {
	@Override
	protected void writeO(final ByteBuf writeBuf, final Object object) {
		final List object2 = (List) object;
		GameWriteTool.writeInt(writeBuf, (Integer) object2.get(0));
		GameWriteTool.writeShort(writeBuf, 1);
		BuildFieldsNew.get("life").write(writeBuf, object2.get(1));
	}

	@Override
	public int cmd() {
		return 64981;
	}
}
