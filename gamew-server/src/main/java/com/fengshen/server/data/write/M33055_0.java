package com.fengshen.server.data.write;

import org.springframework.stereotype.*;

import com.fengshen.server.data.*;
import com.fengshen.server.data.vo.*;
import com.fengshen.server.netty.*;

import io.netty.buffer.*;

@Service
public class M33055_0 extends BaseWrite {
	@Override
	protected void writeO(final ByteBuf writeBuf, final Object object) {
		final Vo_33055_0 object2 = (Vo_33055_0) object;
		GameWriteTool.writeByte(writeBuf, object2.is_enable);
		GameWriteTool.writeByte(writeBuf, object2.enable_gold_stall_cash);
		GameWriteTool.writeByte(writeBuf, object2.sell_cash_aft_days);
		GameWriteTool.writeByte(writeBuf, object2.start_gold_stall_cash);
		GameWriteTool.writeByte(writeBuf, object2.enable_appoint);
		GameWriteTool.writeByte(writeBuf, object2.enable_autcion);
		GameWriteTool.writeInt(writeBuf, object2.close_time);
	}

	@Override
	public int cmd() {
		return 33055;
	}
}
