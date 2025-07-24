package com.fengshen.server.data.write.party;

import java.util.Map;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.data.UtilObjMap;
import com.fengshen.server.data.vo.party.Vo_PARTY_DIALOG;
import com.fengshen.server.domain.BuildFieldsNew;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

public class MSG_DIALOG_PARTY extends BaseWrite<Vo_PARTY_DIALOG> {

	@Override
	protected void writeO(ByteBuf buff, Vo_PARTY_DIALOG object) {
		GameWriteTool.writeString(buff, object.caption);
		GameWriteTool.writeString(buff, object.content);
		GameWriteTool.writeString(buff, object.peer_name);
		GameWriteTool.writeString(buff, object.ask_type);
		GameWriteTool.writeShort(buff, 1);
		Map<Object, Object> map = UtilObjMap.partyJoinList(object.item);
		GameWriteTool.writeInt(buff, 0);
		GameWriteTool.writeShort(buff, map.size());
		for (final Map.Entry<Object, Object> entry2 : map.entrySet()) {
			if (BuildFieldsNew.data.get(entry2.getKey()) != null) {
				BuildFieldsNew.get((String) entry2.getKey()).write(buff, entry2.getValue());
			} else {
				System.out.println(entry2.getKey());
			}
		}
		GameWriteTool.writeByte(buff, 1);
		GameWriteTool.writeByte(buff, 0);
		GameWriteTool.writeByte(buff, object.flag);
	}

	@Override
	public int cmd() {
		return 0x4FF3;
	}

}
