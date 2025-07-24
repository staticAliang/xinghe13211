package com.fengshen.server.data.write;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.data.vo.Vo_65505_0;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

/**
 * 进入地图
 * 
 *
 */
public class MSG_ENTER_ROOM extends BaseWrite<Vo_65505_0> {
	@Override
	protected void writeO(final ByteBuf buff, Vo_65505_0 object) {
		if(object == null) {
			return;
		}
		GameWriteTool.writeString(buff, object.map_name);
        GameWriteTool.writeShort(buff, object.zeros);
        GameWriteTool.writeByte(buff, object.zerob);
        GameWriteTool.writeShort(buff, object.map_id);
        GameWriteTool.writeByte(buff, object.x1);
        GameWriteTool.writeByte(buff, object.x2);
        GameWriteTool.writeByte(buff, object.y1);
        GameWriteTool.writeByte(buff, object.y2);
        GameWriteTool.writeString(buff, object.map_show_name);
        GameWriteTool.writeByte(buff, object.is_safe_zone);
        GameWriteTool.writeByte(buff, object.is_task_walk);
        GameWriteTool.writeByte(buff, object.wall_index);
        GameWriteTool.writeShort(buff, object.enter_effect_index);
    }

	@Override
	public int cmd() {
		return 65505;
	}
}
