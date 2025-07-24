package com.fengshen.server.data.write;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.data.vo.Vo_32747_0;
import com.fengshen.server.domain.SkillCost;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

// MSG_UPDATE_SKILLS

@Service
public class M32747_0 extends BaseWrite<List<Vo_32747_0>> {
	@Override
	protected void writeO(ByteBuf writeBuf, List<Vo_32747_0> obj) {
		if (obj.size() > 0) {
			GameWriteTool.writeInt(writeBuf, obj.get(0).id);
		}
		GameWriteTool.writeShort(writeBuf, obj.size());
		for (int i = 0; i < obj.size(); ++i) {
			Vo_32747_0 object2 = obj.get(i);
			GameWriteTool.writeShort(writeBuf, object2.skill_no);
			GameWriteTool.writeShort(writeBuf, object2.skill_attrib1);
			GameWriteTool.writeShort(writeBuf, object2.skill_level);
			GameWriteTool.writeShort(writeBuf, object2.level_improved);
			GameWriteTool.writeShort(writeBuf, object2.skill_mana_cost);
			GameWriteTool.writeInt(writeBuf, object2.skill_nimbus);
			GameWriteTool.writeByte(writeBuf, object2.skill_disabled);
			GameWriteTool.writeShort(writeBuf, object2.range);
			GameWriteTool.writeShort(writeBuf, object2.max_range);
			if (object2.count1 > 0) {
				GameWriteTool.writeShort(writeBuf, object2.count1);
				GameWriteTool.writeString(writeBuf, object2.s1);
				GameWriteTool.writeInt(writeBuf, object2.s2);
			} else {
				GameWriteTool.writeShort(writeBuf, object2.skillCost.size());
				for (SkillCost cost : object2.skillCost) {
					GameWriteTool.writeString(writeBuf, cost.s1);
					GameWriteTool.writeInt(writeBuf, cost.s2);
				}
			}
			GameWriteTool.writeByte(writeBuf, 0);
		}
	}

	@Override
	public int cmd() {
		return 32747;
	}
}
