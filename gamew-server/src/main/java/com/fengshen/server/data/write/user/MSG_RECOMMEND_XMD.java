package com.fengshen.server.data.write.user;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

/**
 * 通知仙魔点自动加点配置
 * 0:加点方案（１：自动加仙道点；２：自动加魔道点。）
 * 1:isOpen
 * @author aaa
 *
 */
public class MSG_RECOMMEND_XMD extends BaseWrite<Integer[]> {

	@Override
	protected void writeO(ByteBuf buff, Integer[] object) {
		GameWriteTool.writeByte(buff, object[0]);
		GameWriteTool.writeByte(buff, object[1]);
	}

	@Override
	public int cmd() {
		return 0xD15B;
	}

}
