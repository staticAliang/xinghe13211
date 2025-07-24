package com.fengshen.server.data.write.zhenbao;

import java.util.List;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.data.vo.zhenbao.Vo_GOLD_STALL_RECORD;
import com.fengshen.server.data.vo.zhenbao.Vo_GOLD_STALL_RECORD_BASE;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

public class MSG_GOLD_STALL_RECORD extends BaseWrite<Vo_GOLD_STALL_RECORD>{

	@Override
	protected void writeO(ByteBuf buff, Vo_GOLD_STALL_RECORD object) {
		List<Vo_GOLD_STALL_RECORD_BASE> buyList = object.getBuyList();
		GameWriteTool.writeShort(buff, buyList.size());
		for(Vo_GOLD_STALL_RECORD_BASE buy:buyList) {
			GameWriteTool.writeString(buff, buy.getName());
			GameWriteTool.writeShort(buff, buy.getLevel());
			GameWriteTool.writeInt(buff, buy.getTime());
			GameWriteTool.writeInt(buff, buy.getEndTime());
			GameWriteTool.writeInt(buff, buy.getPrice());
			GameWriteTool.writeShort(buff, buy.getStatus());
			GameWriteTool.writeShort(buff, buy.getReqLevel());
			GameWriteTool.writeByte(buff, buy.getItemPolar());
			GameWriteTool.writeByte(buff, buy.getStallItemType());
			GameWriteTool.writeString(buff, buy.getRecordId());
			GameWriteTool.writeByte(buff, buy.getBuyType());
		}
		//出售记录
		List<Vo_GOLD_STALL_RECORD_BASE> sellCout = object.getSellCout();
		GameWriteTool.writeShort(buff, sellCout.size());
		for(Vo_GOLD_STALL_RECORD_BASE buy:sellCout) {
			GameWriteTool.writeString(buff, buy.getName());
			GameWriteTool.writeShort(buff, buy.getLevel());
			GameWriteTool.writeInt(buff, buy.getTime());
			GameWriteTool.writeInt(buff, buy.getEndTime());
			GameWriteTool.writeInt(buff, buy.getPrice());
			GameWriteTool.writeShort(buff, buy.getStatus());
			GameWriteTool.writeShort(buff, buy.getReqLevel());
			GameWriteTool.writeByte(buff, buy.getItemPolar());
			GameWriteTool.writeByte(buff, buy.getStallItemType());
			GameWriteTool.writeString(buff, buy.getRecordId());
			GameWriteTool.writeByte(buff, buy.getBuyType());
		}
	}

	@Override
	public int cmd() {
		return 0x810C;
	}

}
