package com.fengshen.server.data.write.user;

import java.util.Map.Entry;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.data.vo.user.Vo_UPDATE_APPEARANCE;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

public class MSG_UPDATE_APPEARANCE extends BaseWrite<Vo_UPDATE_APPEARANCE> {
	@Override
	protected void writeO(final ByteBuf buff, Vo_UPDATE_APPEARANCE object) {
		GameWriteTool.writeInt(buff, object.id);
		GameWriteTool.writeShort(buff, object.x);
		GameWriteTool.writeShort(buff, object.y);
		GameWriteTool.writeShort(buff, object.dir);
		GameWriteTool.writeInt(buff, object.icon);
		GameWriteTool.writeInt(buff, object.weapon_icon);
		GameWriteTool.writeShort(buff, object.type);
		GameWriteTool.writeInt(buff, object.sub_type);
		GameWriteTool.writeInt(buff, object.owner_id);
		GameWriteTool.writeInt(buff, object.leader_id);
		GameWriteTool.writeString(buff, object.name);
		GameWriteTool.writeShort(buff, object.level);
		GameWriteTool.writeString(buff, object.title);
		GameWriteTool.writeString(buff, object.family);
		GameWriteTool.writeString(buff, object.partyname);
		GameWriteTool.writeInt(buff, object.status);
		GameWriteTool.writeInt(buff, object.special_icon);
		GameWriteTool.writeInt(buff, object.org_icon);
		GameWriteTool.writeInt(buff, object.suit_icon);
		GameWriteTool.writeInt(buff, object.suit_light_effect);
		GameWriteTool.writeInt(buff, object.guard_icon);
		// 坐姿
		GameWriteTool.writeInt(buff, object.pet_icon);
		GameWriteTool.writeInt(buff, object.shadow_icon);
		GameWriteTool.writeInt(buff, object.shelter_icon);
		GameWriteTool.writeInt(buff, object.mount_icon);
		GameWriteTool.writeString(buff, object.alicename);
		GameWriteTool.writeString(buff, object.gid);
		GameWriteTool.writeString(buff, object.camp);
		GameWriteTool.writeByte(buff, object.vip_type);
		// 隐藏坐骑
		GameWriteTool.writeByte(buff, object.isHide);
		GameWriteTool.writeByte(buff, object.moveSpeedPercent);
		GameWriteTool.writeInt(buff, object.score);
		GameWriteTool.writeByte(buff, object.opacity);
		GameWriteTool.writeInt(buff, object.masquerade);
		GameWriteTool.writeByte(buff, object.upgradestate);
		GameWriteTool.writeByte(buff, object.upgradetype);
		GameWriteTool.writeByte(buff, object.obstacle);
		// 如果大飞了
		if (object.upgradetype == 3) {
			object.effect.put("feishengEffect", 8043);
		} else if (object.upgradetype == 4) {
			object.effect.put("feishengEffect", 8045);
		}
		// 特效
		if (object.effect == null) {
			GameWriteTool.writeShort(buff, 0);
		} else {
			GameWriteTool.writeShort(buff, object.effect.size());
			for (Entry<String, Integer> m : object.effect.entrySet()) {
				GameWriteTool.writeInt(buff, m.getValue());
			}
		}

		// share_mount_icon
		GameWriteTool.writeInt(buff, 0);
		// share_mount_leader_id
		GameWriteTool.writeInt(buff, 0);
		// share_mount_shadow
		GameWriteTool.writeInt(buff, 0);
		// gather_count
		GameWriteTool.writeShort(buff, 0);
		// gather_name_num
		GameWriteTool.writeShort(buff, 0);
		// portrait
		GameWriteTool.writeInt(buff, object.org_icon);
		// 自定义外观
		GameWriteTool.writeString(buff, object.customIcon == null ? "" : object.customIcon);
		// team_icon
		GameWriteTool.writeShort(buff, object.teamIcon);
		// extra_scale
		GameWriteTool.writeShort(buff, 0);
		GameWriteTool.writeShort(buff, 0);
		// ban_rule
		GameWriteTool.writeString(buff, "");
		// title_ban_rule
		GameWriteTool.writeString(buff, "");
		// x_offset/x_offset
		GameWriteTool.writeByte(buff, 0);
		GameWriteTool.writeByte(buff, 0);

		GameWriteTool.writeByte(buff, object.moveType);
		GameWriteTool.writeByte(buff, object.flyType);
		if(object.flyType>2) {
			GameWriteTool.writeShort(buff, object.moveIds.size());
			for (Integer id : object.moveIds) {
				GameWriteTool.writeInt(buff, id);
			}
		}else {
			GameWriteTool.writeShort(buff, 0);
		}
	}

	@Override
	public int cmd() {
		return 61661;
	}
}
