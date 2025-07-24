package com.fengshen.server.data.write;

import org.springframework.stereotype.Service;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.data.vo.Vo_45143_0;
import com.fengshen.server.netty.BaseWriteNotEnc;

import io.netty.buffer.ByteBuf;

/**
 * 登录排队信息
 * 
 *
 */
@Service
public class M45143_0 extends BaseWriteNotEnc<Vo_45143_0> {
	@Override
	protected void writeO(ByteBuf writeBuf, Vo_45143_0 object2) {
		GameWriteTool.writeString(writeBuf, object2.line_name);
		GameWriteTool.writeInt(writeBuf, object2.expect_time);
		GameWriteTool.writeInt(writeBuf, object2.reconnet_time);
		GameWriteTool.writeInt(writeBuf, object2.waitCode);
		GameWriteTool.writeInt(writeBuf, object2.count);
		GameWriteTool.writeByte(writeBuf, object2.keep_alive);
		GameWriteTool.writeByte(writeBuf, object2.need_wait);
		GameWriteTool.writeByte(writeBuf, object2.indsider_lv);
		GameWriteTool.writeInt(writeBuf, object2.silverCoin);
		GameWriteTool.writeByte(writeBuf, object2.status);
		GameWriteTool.writeInt(writeBuf, (int) (System.currentTimeMillis() / 1000L));
	}

	@Override
	public int cmd() {
		return 45143;
	}
}
