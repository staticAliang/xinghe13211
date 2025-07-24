package com.fengshen.server.data.write.task;

import org.springframework.stereotype.Service;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.data.vo.Vo_61553_0;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

// 这个类是用来发送任务的
@Service
public class MSG_TASK_PROMPT extends BaseWrite {
	@Override
	protected void writeO(final ByteBuf writeBuf, final Object object) {
		final Vo_61553_0 object2 = (Vo_61553_0) object;
		GameWriteTool.writeShort(writeBuf, object2.count);
		for (int i = 0; i < object2.count; ++i) {
			GameWriteTool.writeString(writeBuf, object2.task_type);
			GameWriteTool.writeString2(writeBuf, object2.task_desc);
			GameWriteTool.writeString2(writeBuf, object2.task_prompt);
			GameWriteTool.writeShort(writeBuf, object2.refresh);
			GameWriteTool.writeInt(writeBuf, object2.task_end_time);
			GameWriteTool.writeShort(writeBuf, object2.attrib);
			GameWriteTool.writeString2(writeBuf, object2.reward);
			GameWriteTool.writeString(writeBuf, object2.show_name);
			GameWriteTool.writeString(writeBuf, object2.task_extra_para);
			GameWriteTool.writeString(writeBuf, object2.task_state);
		}
	}

	@Override
	public int cmd() {
		return 61553;
	}
}
