package com.fengshen.server.data.write;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.data.vo.Vo_61661_0;
import com.fengshen.server.netty.BaseWrite;
import io.netty.buffer.ByteBuf;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Map.Entry;

@Service
public class M61661_0_MSG_UPDATE_APPEARANCE extends BaseWrite<Vo_61661_0> {
    @Override
    protected void writeO(final ByteBuf writeBuf, final Vo_61661_0 object2) {
        GameWriteTool.writeInt(writeBuf, object2.id);
        GameWriteTool.writeShort(writeBuf, object2.x);
        GameWriteTool.writeShort(writeBuf, object2.y);
        GameWriteTool.writeShort(writeBuf, object2.dir);
        GameWriteTool.writeInt(writeBuf, object2.icon);
        GameWriteTool.writeInt(writeBuf, object2.weapon_icon);
        GameWriteTool.writeShort(writeBuf, object2.type);
        GameWriteTool.writeInt(writeBuf, object2.sub_type);
        GameWriteTool.writeInt(writeBuf, object2.owner_id);
        GameWriteTool.writeInt(writeBuf, object2.leader_id);
        GameWriteTool.writeString(writeBuf, object2.name);
        GameWriteTool.writeShort(writeBuf, object2.level);
        GameWriteTool.writeString(writeBuf, object2.title);
        GameWriteTool.writeString(writeBuf, object2.family);
        GameWriteTool.writeString(writeBuf, object2.partyname);
        GameWriteTool.writeInt(writeBuf, object2.status);
        GameWriteTool.writeInt(writeBuf, object2.special_icon);
        GameWriteTool.writeInt(writeBuf, object2.org_icon);
        GameWriteTool.writeInt(writeBuf, object2.suit_icon);
        GameWriteTool.writeInt(writeBuf, object2.suit_light_effect);
        GameWriteTool.writeInt(writeBuf, object2.guard_icon);
        GameWriteTool.writeInt(writeBuf, object2.pet_icon);
        GameWriteTool.writeInt(writeBuf, object2.shadow_icon);
        GameWriteTool.writeInt(writeBuf, object2.shelter_icon);
        GameWriteTool.writeInt(writeBuf, object2.mount_icon);
        GameWriteTool.writeString(writeBuf, object2.alicename);
        GameWriteTool.writeString(writeBuf, object2.gid);
        GameWriteTool.writeString(writeBuf, object2.camp);
        GameWriteTool.writeByte(writeBuf, object2.vip_type);
        GameWriteTool.writeByte(writeBuf, object2.isHide);
        GameWriteTool.writeByte(writeBuf, object2.moveSpeedPercent);
        GameWriteTool.writeInt(writeBuf, object2.score);
        GameWriteTool.writeByte(writeBuf, object2.opacity);
        GameWriteTool.writeInt(writeBuf, object2.masquerade);
        GameWriteTool.writeByte(writeBuf, object2.upgradestate);
        GameWriteTool.writeByte(writeBuf, object2.upgradetype);
        GameWriteTool.writeByte(writeBuf, object2.obstacle);
        if (object2.upgradetype == 3) {
            object2.effect.put("feishengEffect", 8043);
        } else if (object2.upgradetype == 3) {
            object2.effect.put("feishengEffect", 8045);
        }
        if (object2.effect == null) {
            GameWriteTool.writeShort(writeBuf, 0);
        } else {
            GameWriteTool.writeShort(writeBuf, object2.effect.size());
            for (final Entry<String, Integer> m : object2.effect.entrySet()) {
                GameWriteTool.writeInt(writeBuf, m.getValue());
            }
        }
        GameWriteTool.writeInt(writeBuf, 0);
        GameWriteTool.writeInt(writeBuf, 0);
        GameWriteTool.writeInt(writeBuf, 0);
        GameWriteTool.writeShort(writeBuf, 0);
        GameWriteTool.writeShort(writeBuf, 0);
        GameWriteTool.writeInt(writeBuf, object2.org_icon);
        GameWriteTool.writeString(writeBuf, (object2.customIcon == null) ? "" : object2.customIcon);
        GameWriteTool.writeShort(writeBuf, object2.teamIcon);

        GameWriteTool.writeShort(writeBuf, object2.extra_scale);//extra_scale
        GameWriteTool.writeShort(writeBuf, 0);//gather_suit_icons
        GameWriteTool.writeString(writeBuf, object2.ban_rule);//ban_rule
        GameWriteTool.writeString(writeBuf, object2.title_ban_rule);//title_ban_rule
        GameWriteTool.writeByte(writeBuf, object2.x_offset);
        GameWriteTool.writeByte(writeBuf, object2.y_offset);
        GameWriteTool.writeByte(writeBuf, object2.moveType);
        GameWriteTool.writeByte(writeBuf, object2.flyType);
        if (object2.moveIds == null) {
            object2.moveIds = new ArrayList<>();
        }
        GameWriteTool.writeShort(writeBuf, object2.moveIds.size());//move_ids
        for (Integer id : object2.moveIds) {
            GameWriteTool.writeInt(writeBuf, id);
        }


    }

    @Override
    public int cmd() {
        return 61661;
    }
}
