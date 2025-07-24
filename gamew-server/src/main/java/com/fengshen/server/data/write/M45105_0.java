package com.fengshen.server.data.write;

import org.springframework.stereotype.*;

import com.alibaba.fastjson.JSONObject;
import com.fengshen.db.domain.Characters;
import com.fengshen.server.data.*;
import com.fengshen.server.data.vo.*;
import com.fengshen.server.domain.*;
import com.fengshen.server.game.GameData;
import com.fengshen.server.game.GameObjectChar;
import com.fengshen.server.game.GameObjectCharMng;
import com.fengshen.server.netty.*;

import io.netty.buffer.*;

import java.util.*;

@Service
public class M45105_0 extends BaseWrite<Vo_45105_0> {
	@Override
	protected void writeO(final ByteBuf writeBuf, Vo_45105_0 object2) {
		GameWriteTool.writeString(writeBuf, object2.goodId);
		GameWriteTool.writeByte(writeBuf, object2.status);
		GameWriteTool.writeInt(writeBuf, object2.endTime);
		final Petbeibao list = object2.petbeibao;
		GameWriteTool.writeShort(writeBuf, list.petShuXing.size());
		GameObjectChar gameObject = GameObjectCharMng.getGameObjectCharByUUid(object2.gid);
		String owname = "无";
		if(gameObject == null) {
			//数据库查询
			Characters findOneByGid2 = GameData.that.baseCharactersService.findOneByGidSelectProperties(object2.gid, "name");
			Chara parseObject = JSONObject.parseObject(findOneByGid2.getData(),Chara.class);
			owname = parseObject.name;
		}else {
			owname = gameObject.chara.name;
		}
		for (int j = 0; j < list.petShuXing.size(); ++j) {
			final PetShuXing petShuXing = list.petShuXing.get(j);
			GameWriteTool.writeByte(writeBuf, list.petShuXing.get(j).no);
			GameWriteTool.writeByte(writeBuf, list.petShuXing.get(j).type1);
			Map<Object, Object> map = new HashMap<Object, Object>();
			map = UtilObjMapshuxing.PetShuXing(petShuXing, owname);
			map.remove("no");
			map.remove("type1");
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

	@Override
	public int cmd() {
		return 45105;
	}
}
