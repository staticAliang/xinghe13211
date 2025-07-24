package com.fengshen.server.data.write;

import org.springframework.stereotype.*;

import com.fengshen.server.data.*;
import com.fengshen.server.data.vo.*;
import com.fengshen.server.netty.*;

import io.netty.buffer.*;

@Service
public class M45060_0 extends BaseWrite {
	@Override
	protected void writeO(final ByteBuf writeBuf, final Object object) {
		final Vo_45060_0 object2 = (Vo_45060_0) object;

		GameWriteTool.writeShort(writeBuf, object2.hasBonus);
		GameWriteTool.writeShort(writeBuf, object2.xy_higest);
		GameWriteTool.writeShort(writeBuf, object2.fm_higest);
		GameWriteTool.writeShort(writeBuf, object2.fx_higest);
		GameWriteTool.writeShort(writeBuf, object2.xy_higest);
		GameWriteTool.writeShort(writeBuf, object2.xy_higest);
		GameWriteTool.writeShort(writeBuf, object2.xy_higest);

		GameWriteTool.writeInt(writeBuf, object2.off_line_time);
		GameWriteTool.writeShort(writeBuf, object2.buy_one);
		GameWriteTool.writeShort(writeBuf, object2.buy_five);
		GameWriteTool.writeShort(writeBuf, object2.buy_time);
		GameWriteTool.writeShort(writeBuf, object2.max_buy_time);

		GameWriteTool.writeShort(writeBuf, object2.offlineStatus);
		GameWriteTool.writeShort(writeBuf, object2.max_turn);
		GameWriteTool.writeString(writeBuf, object2.lastTaskName);
		GameWriteTool.writeShort(writeBuf, object2.max_double);
		GameWriteTool.writeShort(writeBuf, object2.max_jiji);
		// 急急如律令状态
		GameWriteTool.writeShort(writeBuf, object2.jijiStatus);
		GameWriteTool.writeShort(writeBuf, object2.chongfengsan_time);
		GameWriteTool.writeShort(writeBuf, object2.max_chongfengsan_time);
		GameWriteTool.writeShort(writeBuf, object2.ziqihongmeng_time);
		GameWriteTool.writeShort(writeBuf, object2.max_ziqihongmeng_time);
		GameWriteTool.writeShort(writeBuf, object2.max_chongfengsan);
		// 宠风散状态
		GameWriteTool.writeShort(writeBuf, object2.chongfengsan_status);

		// 紫气鸿蒙
		GameWriteTool.writeShort(writeBuf, object2.max_ziqihongmeng);
		GameWriteTool.writeShort(writeBuf, object2.ziqihongmeng_status);
		GameWriteTool.writeShort(writeBuf, object2.hasDaofaBonus);

		// tasks
		GameWriteTool.writeShort(writeBuf, object2.count);
		GameWriteTool.writeString(writeBuf, object2.taskName);
		GameWriteTool.writeShort(writeBuf, object2.taskTime);
		GameWriteTool.writeString(writeBuf, object2.taskName1);
		GameWriteTool.writeShort(writeBuf, object2.taskTime1);
		GameWriteTool.writeString(writeBuf, object2.taskName2);
		GameWriteTool.writeShort(writeBuf, object2.taskTime2);
		GameWriteTool.writeByte(writeBuf, 0);

	}

	@Override
	public int cmd() {
		return 45060;
	}
}
