package com.fengshen.server.data.write.friend;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.data.vo.friend.Vo_OPEN_GIVING_WINDOW;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

public class MSG_OPEN_GIVING_WINDOW extends BaseWrite<Vo_OPEN_GIVING_WINDOW> {

	@Override
	protected void writeO(ByteBuf buff, Vo_OPEN_GIVING_WINDOW object) {
		
		GameWriteTool.writeString(buff, object.getGiverName());
		GameWriteTool.writeInt(buff, object.getGiverIcon());
		GameWriteTool.writeByte(buff, object.getGiverUpgradeType());
		GameWriteTool.writeByte(buff, object.getGirverLeftTimes());
		GameWriteTool.writeByte(buff, object.getGirverLightEffects().size());
		
		for(Integer i:object.getGirverLightEffects()) {
			GameWriteTool.writeInt(buff, i);
		}
		
		GameWriteTool.writeString(buff, object.getReceiverName());
		GameWriteTool.writeInt(buff, object.getReceiverIcon());
		GameWriteTool.writeByte(buff, object.getReceiverUpgradeType());
		GameWriteTool.writeByte(buff, object.getReceiverLeftTimes());
		GameWriteTool.writeByte(buff, object.getReceiverLightEffects().size());
		
		for(Integer i:object.getReceiverLightEffects()) {
			GameWriteTool.writeInt(buff, i);
		}
	}

	@Override
	public int cmd() {
		return 0xD085;
	}

}
