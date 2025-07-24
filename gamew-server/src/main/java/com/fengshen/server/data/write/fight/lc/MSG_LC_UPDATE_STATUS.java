package com.fengshen.server.data.write.fight.lc;

import org.springframework.stereotype.Service;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.data.vo.Vo_11757_0;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

@Service
public class MSG_LC_UPDATE_STATUS extends BaseWrite<Vo_11757_0> {

	@Override
	protected void writeO(ByteBuf buff, Vo_11757_0 object) {
		GameWriteTool.writeInt(buff, object.id);
		GameWriteTool.writeShort(buff, object.list.size());
		for (Integer integer : object.list) {
			GameWriteTool.writeInt(buff, integer);
		}
	}

	@Override
	public int cmd() {
		return 0x29ED;
	}

}
