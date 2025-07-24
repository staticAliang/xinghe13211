package com.fengshen.server.data;

import java.nio.charset.Charset;
import java.util.List;

import io.netty.buffer.ByteBuf;

public class GameWriteTool {
	public static final Charset DEFAULT_CHARSET;
	public static final int INT = 4;

	public static final boolean writeArraySize(final ByteBuf buff, final List<?> list) {
		try {
			buff.writeByte(list.size());
			return true;
		} catch (NullPointerException e) {
			buff.writeByte(0);
			return false;
		}
	}

	public static final void writeInt(final ByteBuf buff, Integer value) {
		if (value == null) {
			value = 0;
		}
		buff.writeInt((int) value);
	}

	public static final void writeString(final ByteBuf buff, final String value) {
		if (value == null) {
			buff.writeByte(0);
			return;
		}
		final byte[] bytes = value.getBytes(GameWriteTool.DEFAULT_CHARSET);
		buff.writeByte(bytes.length);
		buff.writeBytes(bytes);
	}

	public static final void writeString2(final ByteBuf buff, final String value) {
		if (value == null) {
			buff.writeShort(0);
			return;
		}
		final byte[] bytes = value.getBytes(GameWriteTool.DEFAULT_CHARSET);
		buff.writeShort(bytes.length);
		buff.writeBytes(bytes);
	}

	public static final void writeLong(final ByteBuf buff, Long value) {
		if (value == null) {
			value = 0L;
		}
		buff.writeLong(value);
	}

	public static final void writeShort(final ByteBuf buff, Integer value) {
		if (value == null) {
			value = 0;
		}
		buff.writeShort((int) value);
	}

	public static final void writeByte(final ByteBuf buff, Integer value) {
		if (value == null) {
			value = 0;
		}
		buff.writeByte((int) value);
	}

	public static final void writeBoolean(final ByteBuf buff, final boolean value) {
		if (value) {
			buff.writeByte(1);
		} else {
			buff.writeByte(0);
		}
	}

	public static final void writeZero(final ByteBuf buff, final Integer length) {
		buff.writeZero((int) length);
	}

	public static final byte[] toArray(final ByteBuf buff) {
		final byte[] b = new byte[buff.readableBytes()];
		buff.readBytes(b);
		return b;
	}

	public static void writeBytes(final ByteBuf buff, final byte[] bytes) {
		buff.writeBytes(bytes);
	}

	public static void writeLenBuffer2(final ByteBuf buff, final byte[] bytes) {
		buff.writeShort(bytes.length);
		buff.writeBytes(bytes);
	}

	public static byte[] hexToByteArrayBySplit(String inHex, String flag) {// light
		String[] strArr = inHex.split(flag);
		byte[] result = new byte[strArr.length];
		for (int i = 0; i < strArr.length; i++) {
			result[i] = hexToByte(strArr[i]);
		}
		return result;
	}

	public static byte hexToByte(String inHex) {// light
		return (byte) Integer.parseInt(inHex, 16);
	}

	static {
		DEFAULT_CHARSET = Charset.forName("GBK");
	}
}
