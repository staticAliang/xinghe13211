package com.fengshen.server.data.write.user;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.data.vo.user.Vo_UPDATE_COMBAT_INFO_DYNAMIC;
import com.fengshen.server.domain.BuildFieldsNew;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

@Service
public class MSG_UPDATE_COMBAT_INFO_DYNAMIC extends BaseWrite<Vo_UPDATE_COMBAT_INFO_DYNAMIC>{

	@Override
	protected void writeO(ByteBuf buff, Vo_UPDATE_COMBAT_INFO_DYNAMIC object) {
		
		GameWriteTool.writeInt(buff, object.id);
		GameWriteTool.writeByte(buff, object.isSet);
		GameWriteTool.writeShort(buff, object.dataMap.size());
		for (Map.Entry<String, Object> entry : object.dataMap.entrySet()) {
			if (BuildFieldsNew.data.get(entry.getKey()) != null) {
				BuildFieldsNew.get(entry.getKey()).write(buff, entry.getValue());
			}
		}
	}

	@Override
	public int cmd() {
		return 0xA043;
	}

}