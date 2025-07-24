package com.fengshen.server.data.write;

import java.util.List;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.data.vo.Vo_32747_0;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

public class MSG_UPDATE_SKILLS extends BaseWrite<List<Vo_32747_0>> {
	protected void writeO(ByteBuf writeBuf, List<Vo_32747_0> obj) {
		if (obj.size() > 0) {
			GameWriteTool.writeInt(writeBuf, Integer.valueOf(((Vo_32747_0) obj.get(0)).id));
		}
		GameWriteTool.writeShort(writeBuf, Integer.valueOf(obj.size()));
		for (int i = 0; i < obj.size(); i++) {
			Vo_32747_0 object1 = (Vo_32747_0) obj.get(i);

			GameWriteTool.writeShort(writeBuf, Integer.valueOf(object1.skill_no));

			GameWriteTool.writeShort(writeBuf, Integer.valueOf(object1.skill_attrib));

			GameWriteTool.writeShort(writeBuf, Integer.valueOf(object1.skill_level));

			GameWriteTool.writeShort(writeBuf, Integer.valueOf(object1.level_improved));

			GameWriteTool.writeShort(writeBuf, Integer.valueOf(object1.skill_mana_cost));

			GameWriteTool.writeInt(writeBuf, Integer.valueOf(object1.skill_nimbus));

			GameWriteTool.writeByte(writeBuf, Integer.valueOf(object1.skill_disabled));

			GameWriteTool.writeShort(writeBuf, Integer.valueOf(object1.range));

			GameWriteTool.writeShort(writeBuf, Integer.valueOf(object1.max_range));

			// 消耗信息
			GameWriteTool.writeShort(writeBuf, Integer.valueOf(object1.count1));

			for (int j = 0; j < object1.count1; j++) {
				GameWriteTool.writeString(writeBuf, object1.s1);

				GameWriteTool.writeInt(writeBuf, Integer.valueOf(object1.s2));
			}

			GameWriteTool.writeByte(writeBuf, Integer.valueOf(object1.isTempSkill));
		}
	}

	public int cmd() {
		return 32747;
	}
}
