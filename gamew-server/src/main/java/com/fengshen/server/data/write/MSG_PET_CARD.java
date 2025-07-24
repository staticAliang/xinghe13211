package com.fengshen.server.data.write;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.data.UtilObjMapshuxing;
import com.fengshen.server.domain.BuildFields;
import com.fengshen.server.domain.PetShuXing;
import com.fengshen.server.domain.Petbeibao;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;
import lombok.extern.slf4j.Slf4j;

/**
 * 宠物名片
 */
@Service
@Slf4j
public class MSG_PET_CARD extends BaseWrite<Object[]> {

	@Override
	protected void writeO(ByteBuf buff, Object[] info) {
		Petbeibao list = (Petbeibao) info[0];
		GameWriteTool.writeShort(buff, list.petShuXing.size());
		final PetShuXing petShuXing = list.petShuXing.get(0);
		GameWriteTool.writeByte(buff, list.petShuXing.get(0).no);
		GameWriteTool.writeByte(buff, list.petShuXing.get(0).type1);
		Map<Object, Object> map = new HashMap<Object, Object>();
		map = UtilObjMapshuxing.PetShuXing(petShuXing, (String) info[1]);
		map.remove("no");
		map.remove("type1");
		GameWriteTool.writeShort(buff, map.size());
		for (Map.Entry<Object, Object> entry : map.entrySet()) {
			if (BuildFields.data.get(entry.getKey()) != null) {
				BuildFields.get((String) entry.getKey()).write(buff, entry.getValue());
			} else {
				log.info((String) entry.getKey());
			}
		}
	}

	@Override
	public int cmd() {
		return 0x9017;
	}

}
