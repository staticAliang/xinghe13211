package com.fengshen.server.netty;

import java.util.concurrent.ThreadLocalRandom;

import com.fengshen.server.data.GameWriteTool;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public abstract class BaseWrite<T> {

	public static final String NO_ENC = "45143#45555#13143";

	private int beforeWrite(ByteBuf writeBuf) {
//		GameWriteTool.writeShort(writeBuf, 19802);
//		int random = ThreadLocalRandom.current().nextInt(10000)+40000;
//		GameWriteTool.writeShort(writeBuf, random);
//		int time = (int) (System.currentTimeMillis()/1000L);
//		GameWriteTool.writeInt(writeBuf, time);
//		int writerIndex = writeBuf.writerIndex();
//		GameWriteTool.writeShort(writeBuf, ThreadLocalRandom.current().nextInt(10000)+10000);
//		GameWriteTool.writeShort(writeBuf, this.cmd());
		
		
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
//		int writerIndex = 0;
//		ByteBuf writeBuf = Unpooled.buffer();
//		writerIndex = this.beforeWrite(writeBuf);
//		this.writeO(writeBuf, object);
//		this.afterWrite(writeBuf, writerIndex);
//        byte[] bytes = writeBuf.array();
//        byte[] header = new byte[10];
//        System.arraycopy(bytes, 0, header, 0, 10);
//        int l = header[9];
//        int h = header[8];
//        if (l < 0) {
//            l += 256;
//        }
//        if (h < 0) {
//            h += 256;
//        }
//        int bodyLen = (h * 256) + l;
//        byte[] body = new byte[bodyLen];
//        System.arraycopy(bytes, 10, body, 0, bodyLen);
//        byte[] encBody = GameEncryptionUtils.encryptPacket(header, body);
//        writeBuf.clear();
//        writeBuf.writeBytes(header);
//        writeBuf.writeBytes(encBody);
		
		
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
