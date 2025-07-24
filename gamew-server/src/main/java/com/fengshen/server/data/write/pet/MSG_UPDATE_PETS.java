package com.fengshen.server.data.write.pet;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.data.UtilObjMapshuxing;
import com.fengshen.server.domain.BuildFields;
import com.fengshen.server.domain.PetShuXing;
import com.fengshen.server.domain.Petbeibao;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

@Service
public class MSG_UPDATE_PETS extends BaseWrite<List<Petbeibao>> {
	@Override
	protected void writeO(ByteBuf writeBuf, List<Petbeibao> list) {
		GameWriteTool.writeShort(writeBuf, list.size());
		for (int i = 0; i < list.size(); ++i) {
			GameWriteTool.writeByte(writeBuf, list.get(i).no);
			GameWriteTool.writeInt(writeBuf, list.get(i).id);
			GameWriteTool.writeShort(writeBuf, list.get(i).petShuXing.size());
			int zhuruCaifeng = list.get(i).petShuXing.get(0).zhuruCaifeng;
			for (int j = 0; j < list.get(i).petShuXing.size(); ++j) {
				PetShuXing petShuXing = list.get(i).petShuXing.get(j);
				petShuXing.zhuruCaifeng = zhuruCaifeng;
				GameWriteTool.writeByte(writeBuf, list.get(i).petShuXing.get(j).no);
				GameWriteTool.writeByte(writeBuf, list.get(i).petShuXing.get(j).type1);
				Map<Object, Object> map = new HashMap<Object, Object>();
				map = UtilObjMapshuxing.PetShuXing(petShuXing);
				map.remove("no");
				map.remove("type1");
				Iterator<Map.Entry<Object, Object>> it = map.entrySet().iterator();
				while (it.hasNext()) {
					Map.Entry<Object, Object> entry = it.next();
					if (!entry.getKey().equals("all_polar") && !entry.getKey().equals("upgrade_magic")) {
						if (entry.getKey().equals("upgrade_total")) {
							continue;
						}
						if (entry.getValue().equals(0) && (entry.getKey().equals("dex") || entry.getKey().equals("def")
								|| entry.getKey().equals("mana") || entry.getKey().equals("parry")
								|| entry.getKey().equals("accurate") || entry.getKey().equals("wiz"))) {
							it.remove();
						}
						if (!entry.getValue().equals("")) {
							continue;
						}
						it.remove();
					}
				}
				GameWriteTool.writeShort(writeBuf, map.size());
				for (Map.Entry<Object, Object> entry2 : map.entrySet()) {
					if (BuildFields.data.get(entry2.getKey()) != null) {
						BuildFields.get((String) entry2.getKey()).write(writeBuf, entry2.getValue());
					} else {
						System.out.println(entry2.getKey());
					}
				}
			}
		}
	}

	@Override
	public int cmd() {
		return 65507;
	}
}
