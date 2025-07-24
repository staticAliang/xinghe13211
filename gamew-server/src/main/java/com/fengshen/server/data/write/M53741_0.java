package com.fengshen.server.data.write;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.data.UtilObjMapshuxing;
import com.fengshen.server.domain.BuildFields;
import com.fengshen.server.domain.Duiyuan;
import com.fengshen.server.domain.LieBiao;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

@Service
public class M53741_0 extends BaseWrite<List<LieBiao>> {
	@Override
	protected void writeO(ByteBuf writeBuf, List<LieBiao> object2) {
		GameWriteTool.writeString(writeBuf, object2.get(0).ask_type);
		GameWriteTool.writeShort(writeBuf, object2.size());
		for (LieBiao lieBiao : object2) {
			GameWriteTool.writeString(writeBuf, lieBiao.peer_name);
			GameWriteTool.writeShort(writeBuf, lieBiao.duiyuanList.size());
			for (Duiyuan duiyuan : lieBiao.duiyuanList) {
				GameWriteTool.writeInt(writeBuf, duiyuan.org_icon);
				Map<Object, Object> map = UtilObjMapshuxing.Duiyuan(duiyuan);
				map.remove("org_icon");
				map.remove("mapteamMembersCount");
				map.remove("mapcomeback_flag");
				Iterator<Map.Entry<Object, Object>> it = map.entrySet().iterator();
				while (it.hasNext()) {
					Map.Entry<Object, Object> entry = it.next();
					if (entry.getValue().equals(0) || entry.getKey().equals("")) {
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
				GameWriteTool.writeByte(writeBuf, duiyuan.mapteamMembersCount);
				GameWriteTool.writeByte(writeBuf, duiyuan.mapcomeback_flag);
				if(object2.get(0).ask_type.equals("invite_join") || object2.get(0).ask_type.equals("request_join")) {
					GameWriteTool.writeInt(writeBuf, (int) (System.currentTimeMillis()/1000L));
					GameWriteTool.writeInt(writeBuf, 0);
				}
				if(object2.get(0).ask_type.equals("csc_around_player") || object2.get(0).ask_type.equals("csc_around_team")) {
					GameWriteTool.writeString(writeBuf, "");
					GameWriteTool.writeString(writeBuf, "");
				}
			}
		}
	}

	@Override
	public int cmd() {
		return 53741;
	}
}
