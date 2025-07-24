package com.fengshen.server.data.write.sms;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.data.vo.sms.Vo_OPEN_SMS_VERIFY_DLG;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

public class MSG_OPEN_SMS_VERIFY_DLG extends BaseWrite<Vo_OPEN_SMS_VERIFY_DLG> {

	@Override
	protected void writeO(ByteBuf buff, Vo_OPEN_SMS_VERIFY_DLG object) {
		
		GameWriteTool.writeString(buff, object.getFuzzyPhone());
		GameWriteTool.writeInt(buff, object.getLastLakeCodeTime());
	}

	@Override
	public int cmd() {
		return 0x5032;
	}

}
