package com.fengshen.server.data.write;

import org.springframework.stereotype.*;

import com.fengshen.server.data.*;
import com.fengshen.server.domain.*;
import com.fengshen.server.netty.*;

import io.netty.buffer.*;

import java.util.*;

@Service
public class M12016_0 extends BaseWrite {
	@Override
	protected void writeO(final ByteBuf writeBuf, final Object object) {
		final List<ShouHu> object2 = (List<ShouHu>) object;
		GameWriteTool.writeInt(writeBuf, object2.size());
		for (int i = 0; i < object2.size(); ++i) {
			GameWriteTool.writeInt(writeBuf, object2.get(i).id);
			GameWriteTool.writeShort(writeBuf, object2.get(i).listShouHuShuXing.size());
			for (int j = 0; j < object2.get(i).listShouHuShuXing.size(); ++j) {
				final ShouHuShuXing shouHuShuXing = object2.get(i).listShouHuShuXing.get(j);
				Map<Object, Object> map = new HashMap<Object, Object>();
				if (shouHuShuXing != null) {
					map = UtilObjMapshuxing.ShouHuShuXing(shouHuShuXing);
					map.remove("no");
					map.remove("type1");
					GameWriteTool.writeByte(writeBuf, shouHuShuXing.no);
					GameWriteTool.writeByte(writeBuf, shouHuShuXing.type1);
					GameWriteTool.writeShort(writeBuf, map.size());
					for (final Map.Entry<Object, Object> entry : map.entrySet()) {
						if (BuildFields.data.get(entry.getKey()) != null) {
							BuildFields.get((String) entry.getKey()).write(writeBuf, entry.getValue());
						} else {
							System.out.println(entry.getKey());
						}
					}
				}
			}
		}
	}

	@Override
	public int cmd() {
		return 12016;
	}
}
