package com.fengshen.server.data.write.user;

import java.util.List;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.data.vo.user.Vo_FASION_TEAM_ICON_LIST;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

/**
 * 可购买队标列表
 * 
 *
 */
public class MSG_FASION_TEAM_ICON_LIST extends BaseWrite<List<Vo_FASION_TEAM_ICON_LIST>>{

	@Override
	protected void writeO(ByteBuf buff, List<Vo_FASION_TEAM_ICON_LIST> object) {
		
		GameWriteTool.writeShort(buff, object.size());
		for(Vo_FASION_TEAM_ICON_LIST v:object) {
			GameWriteTool.writeString(buff, v.getName());
			GameWriteTool.writeInt(buff, v.getGoods_price());
		}
		
	}

	@Override
	public int cmd() {
		return 0x5270;
	}

}
