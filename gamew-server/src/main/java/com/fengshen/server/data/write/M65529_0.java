package com.fengshen.server.data.write;

import java.util.Map.Entry;

import org.springframework.stereotype.Service;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.data.vo.Vo_APPEAR;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

/**
 * MSG_APPEAR
 */
@Service
public class M65529_0 extends BaseWrite<Vo_APPEAR> {
	@Override
	protected void writeO(final ByteBuf buff, Vo_APPEAR object) {
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
		GameWriteTool.writeString(buff, object.party);
		GameWriteTool.writeInt(buff, object.status);
		GameWriteTool.writeInt(buff, object.special_icon);
		GameWriteTool.writeInt(buff, object.org_icon);
		GameWriteTool.writeInt(buff, object.suit_icon);
		GameWriteTool.writeInt(buff, object.suit_light_effect);
		GameWriteTool.writeInt(buff, object.guard_icon);
		GameWriteTool.writeInt(buff, object.pet_icon);
		GameWriteTool.writeInt(buff, object.shadow_icon);
		GameWriteTool.writeInt(buff, object.shelter_icon);
		GameWriteTool.writeInt(buff, object.mount_icon);
		GameWriteTool.writeString(buff, object.alicename);
		GameWriteTool.writeString(buff, object.gid);
		GameWriteTool.writeString(buff, object.camp);
		GameWriteTool.writeByte(buff, object.vip_type);
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
			object.effectIcons.put("feishengEffect", 8043);
		} else if (object.upgradetype == 3) {
			object.effectIcons.put("feishengEffect", 8045);
		}
		// 特效
		if (object.effectIcons == null) {
			GameWriteTool.writeShort(buff, 0);
		} else {
			GameWriteTool.writeShort(buff, object.effectIcons.size());
			for (Entry<String, Integer> m : object.effectIcons.entrySet()) {
				GameWriteTool.writeInt(buff, m.getValue());
			}
		}
		GameWriteTool.writeInt(buff, object.share_mount_icon);
		GameWriteTool.writeInt(buff, object.share_mount_leader_id);
		// share_mount_shadow
		GameWriteTool.writeInt(buff, 0);
		// gather_count
		GameWriteTool.writeShort(buff, 0);
		GameWriteTool.writeShort(buff, object.gather_name_num);
		GameWriteTool.writeInt(buff, object.portrait);
		GameWriteTool.writeString(buff, object.customIcon == null ? "" : object.customIcon);
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
		return 65529;
	}
}
