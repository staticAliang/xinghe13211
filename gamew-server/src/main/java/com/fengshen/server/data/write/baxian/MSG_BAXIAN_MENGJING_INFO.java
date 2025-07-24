package com.fengshen.server.data.write.baxian;

import org.springframework.stereotype.Service;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.data.vo.baxian.Vo_BAXIAN_MENGJING_INFO;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

@Service
public class MSG_BAXIAN_MENGJING_INFO extends BaseWrite<Vo_BAXIAN_MENGJING_INFO> {
	@Override
	protected void writeO(ByteBuf writeBuf, Vo_BAXIAN_MENGJING_INFO object) {
		GameWriteTool.writeShort(writeBuf, object.times);
		GameWriteTool.writeShort(writeBuf, object.curCheckpoint);
		GameWriteTool.writeShort(writeBuf, object.openMax);
		GameWriteTool.writeShort(writeBuf, object.mainState);
		GameWriteTool.writeShort(writeBuf, object.isOpenDlg);
	}

	@Override
	public int cmd() {
		return 32803;
	}
}
