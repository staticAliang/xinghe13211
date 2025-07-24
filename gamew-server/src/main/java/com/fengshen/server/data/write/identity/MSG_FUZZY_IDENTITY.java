package com.fengshen.server.data.write.identity;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.data.vo.identity.Vo_FUZZY_IDENTITY;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

/**
 * 实名认证
 * 
 *
 */
public class MSG_FUZZY_IDENTITY extends BaseWrite<Vo_FUZZY_IDENTITY> {

	@Override
	protected void writeO(ByteBuf buff, Vo_FUZZY_IDENTITY object) {
		
		GameWriteTool.writeByte(buff, object.getIsBindName());
		GameWriteTool.writeByte(buff, object.getIsBindPhone());
		GameWriteTool.writeString(buff, object.getBindName());
		GameWriteTool.writeString(buff, object.getBindId());
		GameWriteTool.writeString(buff, object.getBindPhone());
	}

	@Override
	public int cmd() {
		return 0xD0A9;
	}

}
