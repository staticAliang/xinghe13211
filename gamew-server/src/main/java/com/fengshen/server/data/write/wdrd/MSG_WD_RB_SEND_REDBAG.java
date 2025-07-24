package com.fengshen.server.data.write.wdrd;

import org.springframework.stereotype.Service;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.data.vo.wdrd.Vo_WD_RB_SEND_REDBAG;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

@Service
public class MSG_WD_RB_SEND_REDBAG extends BaseWrite<Vo_WD_RB_SEND_REDBAG> {

	@Override
	protected void writeO(ByteBuf buff, Vo_WD_RB_SEND_REDBAG object) {
		GameWriteTool.writeString(buff, object.getRedbagGid());
		GameWriteTool.writeString(buff, object.getText());
		GameWriteTool.writeInt(buff, object.getStartTime());
		GameWriteTool.writeString(buff, object.getOwnerGid());
		GameWriteTool.writeString(buff, object.getOwnerName());
		
		GameWriteTool.writeInt(buff, object.getIcon());
		GameWriteTool.writeShort(buff, object.getLevel());
		GameWriteTool.writeShort(buff, object.getTimes());
		GameWriteTool.writeByte(buff, object.getIsNew());
	}
	

	@Override
	public int cmd() {
		return 0x82BF;
	}

}
