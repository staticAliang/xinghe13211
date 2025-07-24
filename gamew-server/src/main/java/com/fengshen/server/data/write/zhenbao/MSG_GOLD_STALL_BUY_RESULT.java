package com.fengshen.server.data.write.zhenbao;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.data.vo.zhenbao.Vo_GOLD_STALL_BUY_RESUL;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

public class MSG_GOLD_STALL_BUY_RESULT extends BaseWrite<Vo_GOLD_STALL_BUY_RESUL> {

	@Override
	protected void writeO(ByteBuf buff, Vo_GOLD_STALL_BUY_RESUL object) {
		GameWriteTool.writeString(buff, object.getGoods_gid());
		GameWriteTool.writeByte(buff, object.getType());
		GameWriteTool.writeByte(buff, object.getResult());
		GameWriteTool.writeString(buff, object.getTips());
	}

	@Override
	public int cmd() {
		return 0x811D;
	}

}
