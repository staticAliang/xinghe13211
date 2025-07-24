package com.fengshen.server.data.write.system;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.data.vo.system.Vo_REMOVE_ANIMATE;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

/**
 * 移除动画
 * @author weilian
 *
 */
public class MSG_REMOVE_ANIMATE extends BaseWrite<Vo_REMOVE_ANIMATE> {

	@Override
	protected void writeO(ByteBuf buff, Vo_REMOVE_ANIMATE object) {
		GameWriteTool.writeInt(buff, object.getId());
		GameWriteTool.writeInt(buff, object.getType());
		GameWriteTool.writeInt(buff, object.getEffectNo());
	}

	@Override
	public int cmd() {
		return 0xB07F;
	}

}
