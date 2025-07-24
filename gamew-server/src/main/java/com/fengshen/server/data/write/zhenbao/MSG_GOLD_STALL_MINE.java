package com.fengshen.server.data.write.zhenbao;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.data.vo.zhenbao.Vo_GOLD_STALL_MINE;
import com.fengshen.server.data.vo.zhenbao.Vo_GOLD_STALL_MINE.Vo_GOLD_STALL_MINE_Items;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

public class MSG_GOLD_STALL_MINE extends BaseWrite<Vo_GOLD_STALL_MINE> {

	@Override
	protected void writeO(ByteBuf buff, Vo_GOLD_STALL_MINE object) {
		
		GameWriteTool.writeShort(buff, object.getDealNum());
		GameWriteTool.writeString(buff, object.getSellCash());
		GameWriteTool.writeShort(buff, object.getStallTotalNum());
		GameWriteTool.writeShort(buff, object.getItems().size());
		for(Vo_GOLD_STALL_MINE_Items v:object.getItems()) {
			GameWriteTool.writeString(buff, v.getName());
			GameWriteTool.writeString(buff, v.getGoodsId());
			GameWriteTool.writeInt(buff, v.getPrice());
			GameWriteTool.writeShort(buff, v.getPos());
			GameWriteTool.writeShort(buff, v.getStatus());
			GameWriteTool.writeInt(buff, v.getStartTime());
			GameWriteTool.writeInt(buff, v.getEndTime());
			GameWriteTool.writeShort(buff, v.getLevel());
			GameWriteTool.writeByte(buff, v.getUnidentified());
			GameWriteTool.writeShort(buff, v.getReq_level());
			GameWriteTool.writeString(buff, v.getExtra());
			GameWriteTool.writeByte(buff, v.getItem_polar());
			GameWriteTool.writeByte(buff, v.getCg_price_count());
			GameWriteTool.writeInt(buff, v.getInit_price());
			GameWriteTool.writeInt(buff, v.getFlag_num());
			GameWriteTool.writeByte(buff, v.getStall_item_type());
			
			GameWriteTool.writeInt(buff, v.getBuyout_price());
			GameWriteTool.writeByte(buff, v.getSell_type());
			GameWriteTool.writeString(buff, v.getAppointee_name());
		}
	}

	@Override
	public int cmd() {
		return 0x8101;
	}

}
