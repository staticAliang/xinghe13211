package com.fengshen.server.data.write;

import org.springframework.stereotype.Service;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.data.vo.Vo_CONFIRM;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

@Service
public class MSG_CONFIRM extends BaseWrite<Vo_CONFIRM> {
	@Override
	protected void writeO(final ByteBuf writeBuf, Vo_CONFIRM object2) {
		GameWriteTool.writeString2(writeBuf, object2.tips);
		GameWriteTool.writeInt(writeBuf, object2.down_count);
		GameWriteTool.writeByte(writeBuf, object2.only_confirm);
		GameWriteTool.writeString(writeBuf, object2.confirm_type);
		GameWriteTool.writeString(writeBuf, object2.confirmText);
		GameWriteTool.writeString(writeBuf, object2.cancelText);
		GameWriteTool.writeByte(writeBuf, object2.show_dlg_mode);
		GameWriteTool.writeString(writeBuf, object2.countDownTips);
		GameWriteTool.writeString2(writeBuf, object2.para_str);
		GameWriteTool.writeByte(writeBuf, 0);
	}

	@Override
	public int cmd() {
		return 45240;
	}
}
