package com.fengshen.server.data.write.fight.c;

import java.util.List;
import java.util.Map;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.data.vo.fight.Vo_C_OPPONENT_INFO;
import com.fengshen.server.domain.BuildFieldsNew;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

public class MSG_C_OPPONENT_INFO extends BaseWrite<List<Vo_C_OPPONENT_INFO>>{

	@Override
	protected void writeO(ByteBuf buff, List<Vo_C_OPPONENT_INFO> object) {
		
		GameWriteTool.writeShort(buff, object.size());
		for(Vo_C_OPPONENT_INFO vo:object) {
			GameWriteTool.writeInt(buff, vo.getId());
			GameWriteTool.writeShort(buff, vo.getBuildFields().size());
			for (Map.Entry<String, Object> entry : vo.getBuildFields().entrySet()) {
				if (BuildFieldsNew.data.get(entry.getKey()) != null) {
					BuildFieldsNew.get(entry.getKey()).write(buff, entry.getValue());
				} 
			}
		}
	}

	@Override
	public int cmd() {
		return 0xFDBD;
	}

}
