package com.fengshen.server.data.write.house;

import org.springframework.stereotype.Service;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.data.vo.chara.Vo_HOUSE_DATA;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

/**
 * 居所数据
 *
 */
@Service
public class MSG_HOUSE_DATA extends BaseWrite<Vo_HOUSE_DATA> {

	@Override
	protected void writeO(ByteBuf buff, Vo_HOUSE_DATA object) {
		
		GameWriteTool.writeString(buff, object.getHouse_id());
		GameWriteTool.writeByte(buff, object.getHouse_type());
		GameWriteTool.writeString(buff, object.getHouse_prefix());
		GameWriteTool.writeShort(buff, object.getComfort());
		GameWriteTool.writeByte(buff, object.getCleanliness());
		GameWriteTool.writeByte(buff, object.getClean_costtime());
		GameWriteTool.writeByte(buff, object.getStore_type());
		
	}

	@Override
	public int cmd() {
		return 0xA09C;
	}

}
