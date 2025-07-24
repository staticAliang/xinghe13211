package com.fengshen.server.game;

import com.fengshen.server.data.GameReadTool;
import io.netty.buffer.ByteBuf;

import java.nio.charset.Charset;

public class testDemo {
    public static Charset DEFAULT_CHARSET;
    public static void main(String[] args) {
        Object msg = "4D 5A 00 00 00 3E A9 83 00 02 D3 86";
        ByteBuf buff = (ByteBuf) msg;
        int cmd = GameReadTool.readShort(buff);
        System.out.println(cmd);
    }
    public static int readShort(ByteBuf buff) {
        int readUnsignedShort = buff.readUnsignedShort();
        return readUnsignedShort;
    }

}
