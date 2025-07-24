package com.fengshen.server.data.write.identity;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.data.vo.identity.Vo_UPDATE_ANTIADDICTION_STATUS;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

public class MSG_UPDATE_ANTIADDICTION_STATUS extends BaseWrite<Vo_UPDATE_ANTIADDICTION_STATUS> {

	@Override
	protected void writeO(ByteBuf buff, Vo_UPDATE_ANTIADDICTION_STATUS object) {

		GameWriteTool.writeByte(buff, object.getIs_startup());
		GameWriteTool.writeInt(buff, object.getTotal_online());
		GameWriteTool.writeInt(buff, object.getLast_online());
		GameWriteTool.writeByte(buff, object.getAdult_status());
		GameWriteTool.writeShort(buff, object.getPlayer_age());
		GameWriteTool.writeByte(buff, object.getIs_guest());
		GameWriteTool.writeShort(buff, object.getAge1());
		GameWriteTool.writeShort(buff, object.getAge2());
		GameWriteTool.writeInt(buff, object.getYoung_coin_cost_limit());
		GameWriteTool.writeInt(buff, object.getMax_online_time());
		GameWriteTool.writeShort(buff, object.getLimit_day());
	}

	@Override
	public int cmd() {
		return 0x5201;
	}

}
