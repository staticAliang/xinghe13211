package com.fengshen.server.data.write.zhenbao;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.data.vo.zhenbao.Vo_GOLD_STALL_GOODS_LIST;
import com.fengshen.server.data.vo.zhenbao.Vo_GOLD_STALL_MINE.Vo_GOLD_STALL_MINE_Items;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

public class MSG_GOLD_STALL_GOODS_LIST extends BaseWrite<Vo_GOLD_STALL_GOODS_LIST>{

	@Override
	protected void writeO(ByteBuf buff, Vo_GOLD_STALL_GOODS_LIST object) {
		GameWriteTool.writeShort(buff, object.getTotalPage());
		GameWriteTool.writeShort(buff, object.getCur_page());
		GameWriteTool.writeShort(buff, object.getItems().size());
		for (int i = 0; i < object.getItems().size(); ++i) {
			final Vo_GOLD_STALL_MINE_Items v = object.getItems().get(i);
			GameWriteTool.writeString(buff, v.getName());
			GameWriteTool.writeByte(buff, v.getIs_my_goods());
			GameWriteTool.writeString(buff, v.getGoodsId());
			GameWriteTool.writeInt(buff, v.getPrice());
			GameWriteTool.writeShort(buff, v.getStatus());
			GameWriteTool.writeInt(buff, v.getStartTime());
			GameWriteTool.writeInt(buff, v.getEndTime());
			GameWriteTool.writeShort(buff, v.getLevel());
			GameWriteTool.writeByte(buff, v.getUnidentified());
			GameWriteTool.writeShort(buff, v.getReq_level());
			GameWriteTool.writeString(buff, v.getExtra());
			GameWriteTool.writeByte(buff, v.getItem_polar());
			GameWriteTool.writeInt(buff, v.getBuyout_price());
			GameWriteTool.writeByte(buff, v.getSell_type());
			GameWriteTool.writeString(buff, v.getAppointee_name());
		}
		GameWriteTool.writeString(buff, object.getPath_str());
		GameWriteTool.writeString(buff, object.getSelect_gid());
		GameWriteTool.writeByte(buff, object.getSell_stage());
		GameWriteTool.writeString(buff, object.getSort_key());
		GameWriteTool.writeByte(buff, object.getIs_descending());
	}

	@Override
	public int cmd() {
		return 0x8103;
	}

}
