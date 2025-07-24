package com.fengshen.server.data.write.system;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.data.vo.system.Vo_BANNER;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

/**
 * 横幅
 * 
 *
 */
public class MSG_BANNER extends BaseWrite<Vo_BANNER>{

	@Override
	protected void writeO(ByteBuf buff, Vo_BANNER object) {
		
		GameWriteTool.writeByte(buff, object.getType());
		GameWriteTool.writeString(buff, object.getTitle());
		GameWriteTool.writeString(buff, object.getContent());
		GameWriteTool.writeInt(buff, object.getTime());
		GameWriteTool.writeShort(buff, object.getOrder());
	}

	@Override
	public int cmd() {
		return 0xB075;
	}

}
