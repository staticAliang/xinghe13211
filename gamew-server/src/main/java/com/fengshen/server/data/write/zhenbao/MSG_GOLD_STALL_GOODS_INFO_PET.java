package com.fengshen.server.data.write.zhenbao;

import java.util.HashMap;
import java.util.Map;

import com.fengshen.db.domain.Characters;
import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.data.UtilObjMapshuxing;
import com.fengshen.server.data.vo.Vo_45105_0;
import com.fengshen.server.domain.BuildFields;
import com.fengshen.server.domain.PetShuXing;
import com.fengshen.server.domain.Petbeibao;
import com.fengshen.server.game.GameData;
import com.fengshen.server.game.GameObjectChar;
import com.fengshen.server.game.GameObjectCharMng;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

/**
 * 珍宝宠物名片
 * 
 *
 */
public class MSG_GOLD_STALL_GOODS_INFO_PET extends BaseWrite<Vo_45105_0> {

	@Override
	protected void writeO(ByteBuf buff, Vo_45105_0 object2) {

		GameWriteTool.writeString(buff, object2.goodId);
		GameWriteTool.writeByte(buff, object2.status);
		GameWriteTool.writeInt(buff, object2.endTime);
		final Petbeibao list = object2.petbeibao;
		GameWriteTool.writeShort(buff, list.petShuXing.size());
		GameObjectChar gameObject = GameObjectCharMng.getGameObjectCharByUUid(object2.gid);
		String owname = "无";
		if(gameObject == null) {
			//数据库查询
			Characters findOneByGid2 = GameData.that.baseCharactersService.findOneByGidSelectProperties(object2.gid, "name");
			if(findOneByGid2 != null) {
				owname = findOneByGid2.getName();
			}
		}else {
			owname = gameObject.chara.name;
		}
		for (int j = 0; j < list.petShuXing.size(); ++j) {
			final PetShuXing petShuXing = list.petShuXing.get(j);
			GameWriteTool.writeByte(buff, list.petShuXing.get(j).no);
			GameWriteTool.writeByte(buff, list.petShuXing.get(j).type1);
			Map<Object, Object> map = new HashMap<Object, Object>();
			map = UtilObjMapshuxing.PetShuXing(petShuXing, owname);
			map.remove("no");
			map.remove("type1");
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
		return 0x8113;
	}

}
