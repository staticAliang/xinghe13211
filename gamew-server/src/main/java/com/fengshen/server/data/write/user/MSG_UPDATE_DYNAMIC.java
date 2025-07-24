package com.fengshen.server.data.write.user;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.data.vo.user.Vo_UPDATE_DYNAMIC;
import com.fengshen.server.domain.BuildFieldsNew;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;
import lombok.extern.slf4j.Slf4j;

/**
 * 动态数据
 * 
 * 
 *
 */
@Slf4j
@Service
public class MSG_UPDATE_DYNAMIC extends BaseWrite<Vo_UPDATE_DYNAMIC> {

	@Override
	protected void writeO(ByteBuf buff, Vo_UPDATE_DYNAMIC object) {
		GameWriteTool.writeInt(buff, object.getId());
		if (object.getDataMap() != null) {
			GameWriteTool.writeShort(buff, object.getDataMap().size());
			for (Map.Entry<String, Object> entry : object.getDataMap().entrySet()) {
				if (BuildFieldsNew.data.get(entry.getKey()) != null) {
					BuildFieldsNew.get(entry.getKey()).write(buff, entry.getValue());
				} else {
					log.info((String) entry.getKey());
				}
			}
		}else {
			GameWriteTool.writeShort(buff, 0);
		}

	}

	@Override
	public int cmd() {
		return 65527;
	}

}
