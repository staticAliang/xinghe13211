package com.fengshen.server.netty;

import java.util.concurrent.ThreadLocalRandom;

import com.fengshen.server.data.GameWriteTool;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * 不加密的类
 * @author aaa
 *
 * @param <T>
 */
public abstract class BaseWriteNotEnc<T> {


	private int beforeWrite(ByteBuf writeBuf) {
		GameWriteTool.writeShort(writeBuf, 19802);
		GameWriteTool.writeShort(writeBuf, 0);
		int time = (int) (System.currentTimeMillis()/1000L);
		GameWriteTool.writeInt(writeBuf, time);
		int writerIndex = writeBuf.writerIndex();
		GameWriteTool.writeShort(writeBuf, ThreadLocalRandom.current().nextInt(10000)+10000);
		GameWriteTool.writeShort(writeBuf, this.cmd());
		return writerIndex;
	}

	private void afterWrite(ByteBuf writeBuf, int writerIndex) {
		int len = writeBuf.writerIndex() - writerIndex - 2;
		writeBuf.markWriterIndex();
		writeBuf.writerIndex(writerIndex).writeShort(len);
		writeBuf.resetWriterIndex();
	}

	// 通过netty往前端写数据
	public ByteBuf write(T object, boolean... isEnc) {
		int writerIndex = 0;
		ByteBuf writeBuf = Unpooled.buffer();
		writerIndex = this.beforeWrite(writeBuf);
		this.writeO(writeBuf, object);
		this.afterWrite(writeBuf, writerIndex);
		return writeBuf;
	}

	protected abstract void writeO(ByteBuf buff, T object);

	public abstract int cmd();
}
