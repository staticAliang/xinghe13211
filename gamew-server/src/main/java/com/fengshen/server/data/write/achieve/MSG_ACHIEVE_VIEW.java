package com.fengshen.server.data.write.achieve;

import com.fengshen.server.data.vo.achieve.Vo_ACHIEVE_VIEW;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

/**
 * a成就预览
 * 
 *
 */
public class MSG_ACHIEVE_VIEW extends BaseWrite<Vo_ACHIEVE_VIEW> {

	@Override
	protected void writeO(ByteBuf buff, Vo_ACHIEVE_VIEW object) {
	}

	@Override
	public int cmd() {
		return 0;
	}

}
