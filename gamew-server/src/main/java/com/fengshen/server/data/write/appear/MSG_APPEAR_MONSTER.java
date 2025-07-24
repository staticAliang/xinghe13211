package com.fengshen.server.data.write.appear;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.data.vo.Vo_APPEAR;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

/**
 * a怪物
 * @author aaa
 *
 */
public class MSG_APPEAR_MONSTER extends BaseWrite<Vo_APPEAR> {

	@Override
	protected void writeO(ByteBuf buff, Vo_APPEAR object) {

		Vo_APPEAR object2 = (Vo_APPEAR) object;
		GameWriteTool.writeInt(buff, object2.id);
		GameWriteTool.writeShort(buff, object2.x);
		GameWriteTool.writeShort(buff, object2.y);
		GameWriteTool.writeShort(buff, object2.dir);
		GameWriteTool.writeInt(buff, object2.icon);
		GameWriteTool.writeInt(buff, object2.weapon_icon);
		GameWriteTool.writeShort(buff, 0x0002);
		GameWriteTool.writeInt(buff, 0x0002);
		GameWriteTool.writeInt(buff, 0);
		GameWriteTool.writeInt(buff, 0);
		GameWriteTool.writeString(buff, object2.name);
		GameWriteTool.writeShort(buff, object2.level);
		GameWriteTool.writeString(buff, object2.title);
		GameWriteTool.writeString(buff, "");
		GameWriteTool.writeString(buff, "");
		//status
		GameWriteTool.writeInt(buff, 0);
		GameWriteTool.writeInt(buff, 0);
		GameWriteTool.writeInt(buff, object2.org_icon);
		GameWriteTool.writeInt(buff, object2.suit_icon);
		GameWriteTool.writeInt(buff, object2.suit_light_effect);
		GameWriteTool.writeInt(buff, 0);
		GameWriteTool.writeInt(buff, 0);
		GameWriteTool.writeInt(buff, 0);
		GameWriteTool.writeInt(buff, 0);
		GameWriteTool.writeInt(buff, 0);
		GameWriteTool.writeString(buff, object2.alicename);
		GameWriteTool.writeString(buff, "");
		GameWriteTool.writeString(buff, "");
		GameWriteTool.writeByte(buff, 0);
		GameWriteTool.writeByte(buff, object2.isHide);
		GameWriteTool.writeByte(buff, object2.moveSpeedPercent);
		GameWriteTool.writeInt(buff, 0);
		GameWriteTool.writeByte(buff, 0);
		GameWriteTool.writeInt(buff, 0);
		GameWriteTool.writeByte(buff, 0);
		GameWriteTool.writeByte(buff, 0);
		GameWriteTool.writeByte(buff, 0);
		// 特效
		GameWriteTool.writeShort(buff, 0);
		GameWriteTool.writeInt(buff, 0);
		GameWriteTool.writeInt(buff, 0);
		// share_mount_shadow
		GameWriteTool.writeInt(buff, 0);
		// gather_count
		GameWriteTool.writeShort(buff, 0);
		GameWriteTool.writeShort(buff, 0);
		GameWriteTool.writeInt(buff, object2.portrait);
		GameWriteTool.writeString(buff, "");
		GameWriteTool.writeShort(buff, 0);
		GameWriteTool.writeShort(buff, 0);

	}

	@Override
	public int cmd() {
		return 65529;
	}

}
