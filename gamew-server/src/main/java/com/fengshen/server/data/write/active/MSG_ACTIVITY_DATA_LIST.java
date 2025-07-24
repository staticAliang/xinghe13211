package com.fengshen.server.data.write.active;

import java.util.List;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.data.vo.active.Vo_ACTIVITY_DATA_LIST;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

/**
 * 玩家活动数据
 * 
 *
 */
public class MSG_ACTIVITY_DATA_LIST extends BaseWrite<List<Vo_ACTIVITY_DATA_LIST>> {

	@Override
	protected void writeO(ByteBuf buff, List<Vo_ACTIVITY_DATA_LIST> object) {
		GameWriteTool.writeShort(buff, object.size());
		for(Vo_ACTIVITY_DATA_LIST v:object) {
			GameWriteTool.writeString(buff, v.getKey());
			GameWriteTool.writeString(buff, v.getPara());
		}
	}

	@Override
	public int cmd() {
		return 0x5200;
	}

}
