package com.fengshen.server.data.write;

import org.springframework.stereotype.*;

import com.fengshen.server.data.*;
import com.fengshen.server.data.vo.*;
import com.fengshen.server.netty.*;

import io.netty.buffer.*;

@Service
public class M45056_0 extends BaseWrite {
	@Override
	protected void writeO(final ByteBuf writeBuf, final Object object) {
		final Vo_45056_0 object2 = (Vo_45056_0) object;
		GameWriteTool.writeInt(writeBuf, object2.id);
		GameWriteTool.writeString(writeBuf, object2.name);
		GameWriteTool.writeShort(writeBuf, object2.portrait);
		GameWriteTool.writeShort(writeBuf, object2.pic_no);
		GameWriteTool.writeString2(writeBuf, object2.content);
		GameWriteTool.writeShort(writeBuf, object2.isComplete);
		GameWriteTool.writeByte(writeBuf, object2.isInCombat);
		GameWriteTool.writeShort(writeBuf, object2.playTime);
		GameWriteTool.writeString(writeBuf, object2.task_type);
	}

	@Override
	public int cmd() {
		return 45056;
	}
}
