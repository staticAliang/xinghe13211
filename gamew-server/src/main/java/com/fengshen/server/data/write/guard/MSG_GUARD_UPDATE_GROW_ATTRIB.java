package com.fengshen.server.data.write.guard;

import java.util.HashMap;
import java.util.Map;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.data.UtilObjMapshuxing;
import com.fengshen.server.domain.BuildFields;
import com.fengshen.server.domain.ShouHuShuXing;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

public class MSG_GUARD_UPDATE_GROW_ATTRIB extends BaseWrite<Object[]> {

	@Override
	protected void writeO(ByteBuf buff, Object[] object) {
		GameWriteTool.writeInt(buff, (Integer) object[0]);
		ShouHuShuXing shouhu = (ShouHuShuXing) object[1];
		Map<Object, Object> map = new HashMap<Object, Object>();
		if (shouhu != null) {
			map = UtilObjMapshuxing.guardArrtib(shouhu);
//			GameWriteTool.writeByte(buff, shouhu.no);
//			GameWriteTool.writeByte(buff, shouhu.type);
			GameWriteTool.writeShort(buff, map.size());
			for (final Map.Entry<Object, Object> entry : map.entrySet()) {
				if (BuildFields.data.get(entry.getKey()) != null) {
					BuildFields.get((String) entry.getKey()).write(buff, entry.getValue());
				} else {
					System.out.println(entry.getKey());
				}
			}
		}
	}

	@Override
	public int cmd() {
		return 0x9003;
	}

}
