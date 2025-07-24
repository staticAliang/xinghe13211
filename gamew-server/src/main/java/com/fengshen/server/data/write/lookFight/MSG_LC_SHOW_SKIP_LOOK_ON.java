package com.fengshen.server.data.write.lookFight;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.game.GameUtil;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

/**
 * 显示观战按钮
 *
 */
@Component
public class MSG_LC_SHOW_SKIP_LOOK_ON extends BaseWrite{

	@Override
	protected void writeO(ByteBuf writeBuf, Object object) {
		GameWriteTool.writeByte(writeBuf, 1);
		GameWriteTool.writeBoolean(writeBuf, true);
		GameWriteTool.writeInt(writeBuf, 1);
	}

	@Override
	public int cmd() {
		return 53655;
	}

}
