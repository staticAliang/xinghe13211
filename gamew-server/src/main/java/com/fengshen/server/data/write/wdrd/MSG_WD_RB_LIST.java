package com.fengshen.server.data.write.wdrd;

import org.springframework.stereotype.Service;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.data.vo.wdrd.Vo_WD_RB_LIST;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

@Service
public class MSG_WD_RB_LIST extends BaseWrite<Vo_WD_RB_LIST> {

	@Override
	protected void writeO(ByteBuf buff, Vo_WD_RB_LIST object) {
		GameWriteTool.writeString(buff, object.getRedbagGid());
		GameWriteTool.writeInt(buff, object.getTotalCoin());
		GameWriteTool.writeInt(buff, object.getSenderLevel());
		GameWriteTool.writeInt(buff, object.getSenderIcon());
		GameWriteTool.writeString(buff, object.getSenderName());
		GameWriteTool.writeString(buff, object.getMsg());
		GameWriteTool.writeInt(buff, object.getTime());
		GameWriteTool.writeShort(buff, object.getCount());
		GameWriteTool.writeByte(buff, object.getState());
		GameWriteTool.writeByte(buff, object.getIsSender());
		GameWriteTool.writeByte(buff, object.getIsRecv());
	}

	@Override
	public int cmd() {
		return 0x82C3;
	}

}
