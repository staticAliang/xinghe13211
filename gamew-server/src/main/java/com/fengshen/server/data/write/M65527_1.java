package com.fengshen.server.data.write;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.domain.BuildFieldsNew;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

@Service
public class M65527_1 extends BaseWrite<List<Object>> {
	@Override
	protected void writeO(final ByteBuf writeBuf, List<Object> object) {
		GameWriteTool.writeInt(writeBuf, (Integer) object.get(0));
		GameWriteTool.writeShort(writeBuf, 1);
		BuildFieldsNew.get("life").write(writeBuf, object.get(1));
			
	}

	@Override
	public int cmd() {
		return 65527;
	}
}
