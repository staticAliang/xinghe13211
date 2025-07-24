package com.fengshen.server.data.write;

import org.springframework.stereotype.*;

import com.fengshen.server.data.*;
import com.fengshen.server.data.vo.*;
import com.fengshen.server.netty.*;

import io.netty.buffer.*;

@Service
public class M32855_0 extends BaseWrite {
	@Override
	protected void writeO(final ByteBuf writeBuf, final Object object) {
		final Vo_32855_0 object2 = (Vo_32855_0) object;
		GameWriteTool.writeByte(writeBuf, object2.enable);
		GameWriteTool.writeString(writeBuf, object2.url);
		GameWriteTool.writeByte(writeBuf, object2.sellCashAfterDays);
		GameWriteTool.writeByte(writeBuf, object2.isSellCash);
		GameWriteTool.writeInt(writeBuf, object2.recommendPrice);
		GameWriteTool.writeByte(writeBuf, 0);
	}

	@Override
	public int cmd() {
		return 32855;
	}
}
