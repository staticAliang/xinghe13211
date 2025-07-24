package com.fengshen.server.data.write;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.data.vo.Vo_53713_0;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

@Service
public class M53713_0 extends BaseWrite<List<Vo_53713_0>> {
	@Override
	protected void writeO(final ByteBuf writeBuf, final List<Vo_53713_0> object) {
		
		GameWriteTool.writeByte(writeBuf, object.size());
		for(Vo_53713_0 v:object) {
			GameWriteTool.writeString(writeBuf, v.getName());
			GameWriteTool.writeInt(writeBuf, v.getPrice());
		}
	}

	@Override
	public int cmd() {
		return 53713;
	}
}
