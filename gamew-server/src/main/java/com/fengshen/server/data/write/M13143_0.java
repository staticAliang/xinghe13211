package com.fengshen.server.data.write;

import org.springframework.stereotype.Service;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.data.vo.Vo_13143_0;
import com.fengshen.server.netty.BaseWriteNotEnc;

import io.netty.buffer.ByteBuf;

@Service
public class M13143_0 extends BaseWriteNotEnc<Vo_13143_0> {
	@Override
	protected void writeO(ByteBuf writeBuf, Vo_13143_0 object) {
		GameWriteTool.writeLong(writeBuf, (long) object.result);
		GameWriteTool.writeShort(writeBuf, object.privilege);
		GameWriteTool.writeString(writeBuf, object.ip);
		GameWriteTool.writeShort(writeBuf, object.port);
		GameWriteTool.writeInt(writeBuf, object.seed);
		GameWriteTool.writeInt(writeBuf, object.auth_key);
		GameWriteTool.writeShort(writeBuf, object.id);
		GameWriteTool.writeString(writeBuf, object.serverName);
		GameWriteTool.writeByte(writeBuf, object.serverStatus);
		GameWriteTool.writeString(writeBuf, object.msg);
	}

	@Override
	public int cmd() {
		return 13143;
	}
}
