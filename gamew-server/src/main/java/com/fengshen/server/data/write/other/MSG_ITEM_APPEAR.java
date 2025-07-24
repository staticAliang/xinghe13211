package com.fengshen.server.data.write.other;

import java.util.Map.Entry;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.data.vo.other.Vo_ITEM_APPEAR;
import com.fengshen.server.domain.BuildFieldsNew;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

public class MSG_ITEM_APPEAR extends BaseWrite<Vo_ITEM_APPEAR> {

	@Override
	protected void writeO(ByteBuf buff, Vo_ITEM_APPEAR object) {
		
		GameWriteTool.writeInt(buff, object.getId());
		GameWriteTool.writeShort(buff, object.getX());
		GameWriteTool.writeShort(buff, object.getY());
		GameWriteTool.writeShort(buff, object.getDir());
		GameWriteTool.writeShort(buff, object.getIcon());
		GameWriteTool.writeShort(buff, object.getType());
		GameWriteTool.writeShort(buff, object.getAmout());
		GameWriteTool.writeString(buff, object.getName());
		
		GameWriteTool.writeShort(buff, object.getItemType());
//		
//		GameWriteTool.writeShort(buff, object.getBuildFields().size());
//		for(Entry<String, Object> f:object.getBuildFields().entrySet()) {
//			BuildFieldsNew.get(f.getKey()).write(buff, f.getValue());
//		}
//		GameWriteTool.writeString(buff, object.getBanRule());
//		GameWriteTool.writeString(buff, object.getTitleBanRule());
	}

	@Override
	public int cmd() {
		return 0xFFF1;
	}

}
