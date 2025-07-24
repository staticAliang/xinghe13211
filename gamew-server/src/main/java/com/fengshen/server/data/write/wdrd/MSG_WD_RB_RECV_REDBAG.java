package com.fengshen.server.data.write.wdrd;

import org.springframework.stereotype.Service;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.data.vo.wdrd.Vo_WD_RB_RECV_REDBAG;
import com.fengshen.server.data.vo.wdrd.Vo_WD_RB_RECV_REDBAG.Info;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

@Service
public class MSG_WD_RB_RECV_REDBAG extends BaseWrite<Vo_WD_RB_RECV_REDBAG> {

	@Override
	protected void writeO(ByteBuf buff, Vo_WD_RB_RECV_REDBAG object) {
		
		GameWriteTool.writeByte(buff, object.getType());
		GameWriteTool.writeInt(buff, object.getCoin());
		GameWriteTool.writeString(buff, object.getRedbagGid());
		GameWriteTool.writeInt(buff, object.getTotalCoin());
		GameWriteTool.writeInt(buff, object.getSenderLevel());
		GameWriteTool.writeInt(buff, object.getSenderIcon());
		GameWriteTool.writeString(buff, object.getSenderName());
		GameWriteTool.writeString(buff, object.getMsg());
		GameWriteTool.writeInt(buff, object.getSendTime());
		GameWriteTool.writeShort(buff, object.getCount());
		GameWriteTool.writeByte(buff, object.getState());
		GameWriteTool.writeByte(buff, object.getIsSender());
		GameWriteTool.writeByte(buff, object.getIsRecv());
		GameWriteTool.writeShort(buff, object.getInfos().size());
		for(Info info:object.getInfos()) {
			GameWriteTool.writeString(buff, info.getName());
			GameWriteTool.writeInt(buff, info.getCoin());
			GameWriteTool.writeInt(buff, info.getTime());
		}
	}

	@Override
	public int cmd() {
		return 0x82C1;
	}

}
