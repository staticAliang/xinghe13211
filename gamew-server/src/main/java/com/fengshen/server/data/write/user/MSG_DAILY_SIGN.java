package com.fengshen.server.data.write.user;

import org.springframework.stereotype.Service;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.data.vo.Vo_49169_0;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

@Service
public class MSG_DAILY_SIGN extends BaseWrite<Vo_49169_0> {
	@Override
	protected void writeO(final ByteBuf writeBuf, Vo_49169_0 object) {
		GameWriteTool.writeByte(writeBuf, object.monthDays);
		GameWriteTool.writeByte(writeBuf, object.signDays);
		GameWriteTool.writeByte(writeBuf, object.isCanSgin);
		GameWriteTool.writeByte(writeBuf, object.isCanReplenishSign);
		for (int i = 0; i < object.items.size(); i++) {
			GameWriteTool.writeString(writeBuf, object.items.get(i).getName());
			GameWriteTool.writeInt(writeBuf, object.items.get(i).getNum());
		}
	}

	@Override
	public int cmd() {
		return 49169;
	}
}
