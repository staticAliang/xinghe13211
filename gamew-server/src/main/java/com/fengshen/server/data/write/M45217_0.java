package com.fengshen.server.data.write;

import org.springframework.stereotype.Service;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;


@Service
public class M45217_0 extends BaseWrite<Integer[]> {
    @Override
    protected void writeO(ByteBuf writeBuf, Integer[] object) {
    	//刷道总分
    	GameWriteTool.writeInt(writeBuf, object[0]);
    	//领取次数
    	GameWriteTool.writeInt(writeBuf, object[1]);
    	//状态
    	GameWriteTool.writeInt(writeBuf, object[2]);
    }

    @Override
    public int cmd() {
        return 45217;
    }
}

