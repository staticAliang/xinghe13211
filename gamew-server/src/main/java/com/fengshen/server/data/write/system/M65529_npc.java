package com.fengshen.server.data.write.system;

import org.springframework.stereotype.Service;

import com.fengshen.db.domain.Npc;
import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.data.constant.LocatePosition;
import com.fengshen.server.domain.CharaStatue;
import com.fengshen.server.netty.BaseWrite;
import com.fengshen.server.service.CharaStatueService;
import com.fengshen.server.service.HeroPubService;
import com.fengshen.server.util.NpcIds;

import io.netty.buffer.ByteBuf;

// 这里是在刷新NPC的外观
@Service
public class M65529_npc extends BaseWrite<Npc> {
	@Override
	protected void writeO(final ByteBuf writeBuf, final Npc npc) {
		String name = npc.getName();
		// 设置官府里面英雄的称号
		String chenhao = "";
		int direction = LocatePosition.getLocatePosition(npc.getExt()); // 角色朝向
		CharaStatue charaStatue = CharaStatueService.getCharStaure(name);
		
		if(NpcIds.isHeroPubNpc(npc.getId()) && charaStatue != null) {
			direction = LocatePosition.getLocatePosition(charaStatue.dir);
		}else if(NpcIds.isZhengDaoDianNpc(npc.getId())){
			if (charaStatue != null && charaStatue.sex == 1) {
				direction = 7;
			}else if (charaStatue != null && charaStatue.sex == 2) {
				direction = 3;
			}
		}
		if (npc.getName().equals("英雄会评议员_0")) {
			chenhao = HeroPubService.titles[0];
		}
		if (npc.getName().equals("英雄会评议员_1")) {
			chenhao = HeroPubService.titles[1];
		}
		if (npc.getName().equals("英雄会评议员_2")) {
			chenhao = HeroPubService.titles[2];
		}
		if (npc.getName().equals("英雄会评议员_3")) {
			chenhao = HeroPubService.titles[3];
		}
		if (npc.getName().equals("英雄会评议员_4")) {
			chenhao = HeroPubService.titles[4];
		}
		if (npc.getName().equals("英雄会评议员_5")) {
			chenhao = HeroPubService.titles[5];
		}
		if (npc.getName().equals("英雄会评议员_6")) {
			chenhao = HeroPubService.titles[6];
		}
		if (npc.getName().equals("木系掌门") || npc.getName().equals("水系掌门") || npc.getName().equals("火系掌门")
				|| npc.getName().equals("金系掌门")) {
			direction = 7;
		}
		if (npc.getName().equals("土系掌门")) {
			direction = 5;
		}
		// id
		GameWriteTool.writeInt(writeBuf, npc.getId());
		// x
		GameWriteTool.writeShort(writeBuf, npc.getX());
		// y
		GameWriteTool.writeShort(writeBuf, npc.getY());
		// dir
		GameWriteTool.writeShort(writeBuf, direction); // 朝向
		// icon
		GameWriteTool.writeInt(writeBuf, charaStatue == null ? npc.getIcon() : charaStatue.waiguan); // 人物外观
		// weapon_icon
		GameWriteTool.writeInt(writeBuf, charaStatue == null ? Integer.valueOf(0) : charaStatue.weapon_icon); // 武器图标
		// type
		GameWriteTool.writeShort(writeBuf, 0x0004); // 类型
		// sub_type
		GameWriteTool.writeInt(writeBuf, 0);
		// owner_id
		GameWriteTool.writeInt(writeBuf, 0);
		// leader_id
		GameWriteTool.writeInt(writeBuf, 0);
		// name
		GameWriteTool.writeString(writeBuf, charaStatue == null ? npc.getName() : charaStatue.name);
		// level
		GameWriteTool.writeShort(writeBuf, charaStatue == null ? Integer.valueOf(0) : charaStatue.level); // 等级
		// title
		GameWriteTool.writeString(writeBuf, charaStatue == null ? chenhao : charaStatue.chengHao);
		// family
		GameWriteTool.writeString(writeBuf, "");
		// party
		GameWriteTool.writeString(writeBuf, "");
		// status
		GameWriteTool.writeInt(writeBuf, 0);
		// special_icon
		GameWriteTool.writeInt(writeBuf, 0);
		// org_icon
		GameWriteTool.writeInt(writeBuf, 0);
		// suit_icon
		GameWriteTool.writeInt(writeBuf, charaStatue == null ? Integer.valueOf(0) : charaStatue.suit_icon);
		// suit_light_effect
		GameWriteTool.writeInt(writeBuf, charaStatue == null ? Integer.valueOf(0) : charaStatue.suit_light_effect);
		// guard_icon
		GameWriteTool.writeInt(writeBuf, 0);
		// pet_icon
		GameWriteTool.writeInt(writeBuf, 0);
		// shadow_icon
		GameWriteTool.writeInt(writeBuf, 0);
		// shelter_icon
		GameWriteTool.writeInt(writeBuf, 0);
		// mount_icon
		GameWriteTool.writeInt(writeBuf, 0);
		// alicename
		GameWriteTool.writeString(writeBuf, "");
		// gid
		GameWriteTool.writeString(writeBuf, "");
		// camp
		GameWriteTool.writeString(writeBuf, "");
		// vip_type
		GameWriteTool.writeByte(writeBuf, 0);
		// isHide
		GameWriteTool.writeByte(writeBuf, 0);
		// moveSpeedPercent
		GameWriteTool.writeByte(writeBuf, 0);
		// ct_data/score
		GameWriteTool.writeInt(writeBuf, 0);
		// opacity
		GameWriteTool.writeByte(writeBuf, 0);
		// masquerade
		GameWriteTool.writeInt(writeBuf, 0);
		// upgrade/state
		GameWriteTool.writeByte(writeBuf, 0);
		// upgrade/type
		GameWriteTool.writeByte(writeBuf, 0);
		// obstacle
		GameWriteTool.writeByte(writeBuf, 0);
		// light_effect_count
		GameWriteTool.writeShort(writeBuf, 0);
		// share_mount_icon
		GameWriteTool.writeInt(writeBuf, 0);
		// share_mount_leader_id
		GameWriteTool.writeInt(writeBuf, 0);
		// share_mount_shadow
		GameWriteTool.writeInt(writeBuf, 0);
		// gather_count
		GameWriteTool.writeShort(writeBuf, 0);
		// gather_name_num
		GameWriteTool.writeShort(writeBuf, 0);
		// portrait
		GameWriteTool.writeInt(writeBuf, charaStatue == null ? npc.getIcon() : charaStatue.waiguan);
		GameWriteTool.writeString(writeBuf, "");
		GameWriteTool.writeShort(writeBuf, 0);
		// extra_scale
		GameWriteTool.writeShort(writeBuf, 0);
		GameWriteTool.writeShort(writeBuf, 0);
		// ban_rule
		GameWriteTool.writeString(writeBuf, "");
		// title_ban_rule
		GameWriteTool.writeString(writeBuf, "");
		// x_offset/x_offset
		GameWriteTool.writeByte(writeBuf, 0);
		GameWriteTool.writeByte(writeBuf, 0);
		GameWriteTool.writeByte(writeBuf, 0);
		GameWriteTool.writeByte(writeBuf, 0);
		GameWriteTool.writeShort(writeBuf, 0);

	}

	@Override
	public int cmd() {
		return 65529;
	}
}