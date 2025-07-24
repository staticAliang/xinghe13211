package com.fengshen.server.data.write;

import org.springframework.stereotype.Service;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.data.vo.Vo_TITLE;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

/**
 * MSG_TITLE 1 战斗中 2 无状态 3组队 4 满队 5禁止移动 6观战 7红名 ' 10 脑袋挂一红令牌 11 脑袋挂一黑令牌
 */
@Service
public class MSG_TITLE extends BaseWrite<Vo_TITLE> {
	@Override
	protected void writeO(final ByteBuf writeBuf, Vo_TITLE object) {
		GameWriteTool.writeInt(writeBuf, object.id);
		GameWriteTool.writeByte(writeBuf, object.list.size());
		for (int i = 0; i < object.list.size(); i++) {
			GameWriteTool.writeByte(writeBuf,object.list.get(i));
		}
	}

	@Override
	public int cmd() {
		return 61671;
	}
}
