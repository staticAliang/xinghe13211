package com.fengshen.server.data.write.zuolao;

import java.util.List;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.data.vo.zuolao.Vo_ZUOLAO_INFO;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

public class MSG_ZUOLAO_INFO extends BaseWrite<List<Vo_ZUOLAO_INFO>> {

	@Override
	protected void writeO(ByteBuf buff, List<Vo_ZUOLAO_INFO> object) {
		
		GameWriteTool.writeInt(buff, object.size());
		for(Vo_ZUOLAO_INFO info:object) {
			GameWriteTool.writeString(buff, info.getGid());
			GameWriteTool.writeString(buff, info.getName());
			GameWriteTool.writeShort(buff, info.getLevel());
			GameWriteTool.writeString(buff, info.getFamily());
			GameWriteTool.writeShort(buff, info.getPolar());
			GameWriteTool.writeString(buff, info.getServerName());
			GameWriteTool.writeInt(buff, info.getTime());
		}
	}

	@Override
	public int cmd() {
		return 0xB0AD;
	}

}
