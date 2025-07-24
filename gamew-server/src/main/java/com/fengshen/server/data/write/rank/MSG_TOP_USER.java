package com.fengshen.server.data.write.rank;

import java.util.List;
import java.util.Map;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.data.vo.rank.Vo_TOP_USER;
import com.fengshen.server.domain.BuildFieldsNew;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;
import lombok.extern.slf4j.Slf4j;

/**
 * 排行耪信息
 * 
 * 
 *
 */
@Slf4j
public class MSG_TOP_USER extends BaseWrite<Vo_TOP_USER> {

	@Override
	protected void writeO(ByteBuf buff, Vo_TOP_USER object) {
		
		GameWriteTool.writeShort(buff, object.getType());
		GameWriteTool.writeInt(buff, object.getCookie());
		GameWriteTool.writeShort(buff, object.getData().size());
		GameWriteTool.writeByte(buff, object.getRequestType());
		if (object.getRequestType() == 2) {
			GameWriteTool.writeShort(buff, object.getMinLevel());
			GameWriteTool.writeShort(buff, object.getMaxLevel());
		}
		List<Map<Object,Object>> data = object.getData();
		for(Map<Object,Object> map:data) {
			GameWriteTool.writeShort(buff, map.size());
			for (final Map.Entry<Object, Object> entry : map.entrySet()) {
				if (BuildFieldsNew.data.get(entry.getKey()) != null) {
					BuildFieldsNew.get((String) entry.getKey()).write(buff, entry.getValue());
				} else {
					log.info((String)entry.getKey());
				}
			}
		}
	}

	@Override
	public int cmd() {
		return 61653;
	}

}
