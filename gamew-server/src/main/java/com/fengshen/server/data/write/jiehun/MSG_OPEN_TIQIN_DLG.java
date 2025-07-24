package com.fengshen.server.data.write.jiehun;

import java.util.List;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.data.vo.jiehun.Vo_OPEN_TIQIN_DLG;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

public class MSG_OPEN_TIQIN_DLG extends BaseWrite<List<Vo_OPEN_TIQIN_DLG>> {

	@Override
	protected void writeO(ByteBuf buff, List<Vo_OPEN_TIQIN_DLG> object) {
		GameWriteTool.writeByte(buff, object.size());
		for(Vo_OPEN_TIQIN_DLG vo:object) {
			GameWriteTool.writeByte(buff, vo.getGender());
			GameWriteTool.writeShort(buff, vo.getOrgIcon());
			GameWriteTool.writeShort(buff, vo.getWeaponIcon());
			GameWriteTool.writeInt(buff, vo.getSuitIcon());
			GameWriteTool.writeByte(buff, vo.getUpgrageType());
			GameWriteTool.writeString(buff, vo.getName());
			GameWriteTool.writeByte(buff, vo.getLightEffects().size()); 
			for(Integer lightEffectIcon:vo.getLightEffects()) {
				GameWriteTool.writeInt(buff, lightEffectIcon);
			}
		}
	}

	@Override
	public int cmd() {
		return 0xB06D;
	}

}