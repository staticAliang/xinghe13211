package com.fengshen.server.data.write;

import org.springframework.stereotype.Service;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.data.vo.Vo_4321_0;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

@Service
public class M4321_0 extends BaseWrite<Vo_4321_0> {
	@Override
	protected void writeO(ByteBuf writeBuf, Vo_4321_0 object) {
		GameWriteTool.writeByte(writeBuf, object.flag);
		GameWriteTool.writeString(writeBuf, object.dist);
		GameWriteTool.writeString(writeBuf, object.name);
		GameWriteTool.writeInt(writeBuf, object.time);
		GameWriteTool.writeByte(writeBuf, object.lineNum);
		GameWriteTool.writeByte(writeBuf, object.corss_server_dist);
		GameWriteTool.writeByte(writeBuf, object.time_zone);

		GameWriteTool.writeInt(writeBuf, object.start_server_time);
		GameWriteTool.writeByte(writeBuf, object.forbid_redhand);
		GameWriteTool.writeByte(writeBuf, 0);
		GameWriteTool.writeByte(writeBuf, 0);
		GameWriteTool.writeString(writeBuf, "abcd335520");
	}

	@Override
	public int cmd() {
		return 4321;
	}
}
