package com.fengshen.server.data;

import java.nio.charset.Charset;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class GameReadTool {
	public static Charset DEFAULT_CHARSET;

	public static String readString(ByteBuf buff) throws IndexOutOfBoundsException {
		int lenght = readUnsignedByte(buff);
		if (lenght == 0) {
			return "";
		}
		if (lenght > 0 && lenght <= buff.readableBytes()) {
			byte[] strByte = new byte[lenght];
			buff.readBytes(strByte);
			return readString(strByte);
		}
		throw new IndexOutOfBoundsException("字符串长度不够 ! ");
	}

	public static String readString2(ByteBuf buff) throws IndexOutOfBoundsException {
		int lenght = buff.readUnsignedShort();
		if (lenght == 0) {
			return "";
		}
		if (lenght > 0 && lenght <= buff.readableBytes()) {
			byte[] strByte = new byte[lenght];
			buff.readBytes(strByte);
			return readString(strByte);
		}
		throw new IndexOutOfBoundsException("字符串长度不够 ! ");
	}

	private static String readString(byte[] bytes) {
		return new String(bytes, GameReadTool.DEFAULT_CHARSET);
	}

	public static String readString(byte[] bytes, int length) {
		return new String(bytes, 0, length, GameReadTool.DEFAULT_CHARSET);
	}

	public static int readUnsignedByte(ByteBuf buff) {
		short readUnsignedByte = buff.readUnsignedByte();
		return readUnsignedByte;
	}

	public static int readInt(ByteBuf buff) {
		int readInt = buff.readInt();
		return readInt;
	}

	public static long readLong(ByteBuf buff) {
		long readLong = buff.readLong();
		return readLong;
	}

	public static int readByte(ByteBuf buff) {
		short readUnsignedByte = buff.readUnsignedByte();
		return readUnsignedByte;
	}

	public static int readShort(ByteBuf buff) {
		int readUnsignedShort = buff.readUnsignedShort();
		return readUnsignedShort;
	}

	public static ByteBuf readLenBuffer2(ByteBuf buff) {
		int len = buff.readUnsignedShort();
		ByteBuf byteBuf = Unpooled.buffer(len);
		buff.readBytes(byteBuf);
		return byteBuf;
	}

	static {
		DEFAULT_CHARSET = Charset.forName("GBK");
	}
}