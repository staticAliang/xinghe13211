package com.fengshen.server.data.write;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.data.vo.Vo_62209_0;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

// 设置角色的称号消息
@Service
public class M62209_0 extends BaseWrite<List<Vo_62209_0>> {
	@Override
	protected void writeO(final ByteBuf writeBuf, List<Vo_62209_0> object) {
		GameWriteTool.writeShort(writeBuf, object.size());
		for (int i = 0; i < object.size(); ++i) {
			GameWriteTool.writeString(writeBuf, object.get(i).stringformat);
			GameWriteTool.writeString(writeBuf, object.get(i).title);
			GameWriteTool.writeInt(writeBuf, object.get(i).titled_left_time);
		}
	}

	@Override
	public int cmd() {
		return 62209;
	}
}
