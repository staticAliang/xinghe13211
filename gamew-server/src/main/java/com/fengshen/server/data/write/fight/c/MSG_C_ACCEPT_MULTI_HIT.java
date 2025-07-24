package com.fengshen.server.data.write.fight.c;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.data.vo.fight.Vo_ACCEPT_MAGIC_HIT.Info;
import com.fengshen.server.data.vo.fight.Vo_ACCEPT_MULTI_HIT;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

/**
 * 多重连击
 * 
 *
 */
public class MSG_C_ACCEPT_MULTI_HIT extends BaseWrite<Vo_ACCEPT_MULTI_HIT> {

	@Override
	protected void writeO(ByteBuf buff, Vo_ACCEPT_MULTI_HIT object) {
		
		GameWriteTool.writeInt(buff, object.getHitterId());
		GameWriteTool.writeInt(buff, object.getMainVictimId());
		GameWriteTool.writeInt(buff, object.getDamageType());
		GameWriteTool.writeShort(buff, object.getInfos().size());
		
		for(Info info:object.getInfos()) {
			GameWriteTool.writeInt(buff, info.id);
			GameWriteTool.writeShort(buff, info.missed);
		}
	}

	@Override
	public int cmd() {
		return 0x4DEB;
	}

}
