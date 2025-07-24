package com.fengshen.server.data.write.shidao;

import java.util.List;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.data.vo.shidao.Vo_OPEN_SHIDWZDLG;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

public class MSG_OPEN_SHIDWZDLG extends BaseWrite<List<Vo_OPEN_SHIDWZDLG>> {

	@Override
	protected void writeO(ByteBuf buff, List<Vo_OPEN_SHIDWZDLG> object) {
		GameWriteTool.writeByte(buff, object.size());
		for (Vo_OPEN_SHIDWZDLG vo : object) {
			GameWriteTool.writeString(buff, vo.getGid());
			GameWriteTool.writeString(buff, vo.getName());
			GameWriteTool.writeShort(buff, vo.getLevel());
			GameWriteTool.writeByte(buff, vo.getPolar());
			GameWriteTool.writeShort(buff, vo.getIcon());
		}
	}

	@Override
	public int cmd() {
		return 0xB060;
	}

}
