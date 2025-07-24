package com.fengshen.server.data.write.zhenbao;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.data.vo.zhenbao.Vo_TRADING_GOODS_INFO;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

/**
 * 
 *
 */
@Deprecated
public class MSG_TRADING_GOODS_INFO extends BaseWrite<Vo_TRADING_GOODS_INFO> {

	@Override
	protected void writeO(ByteBuf buff, Vo_TRADING_GOODS_INFO object) {
		
		GameWriteTool.writeString(buff, object.getGoodsId());
		GameWriteTool.writeString(buff, object.getSellerGid());
		GameWriteTool.writeString(buff, object.getGoodsName());
		GameWriteTool.writeInt(buff, object.getGoodsType());
		GameWriteTool.writeInt(buff, object.getState());
		GameWriteTool.writeInt(buff, object.getEndTime());
		GameWriteTool.writeInt(buff, object.getPrice());
		GameWriteTool.writeInt(buff, object.getIcon());
		GameWriteTool.writeInt(buff, object.getLevel());
		GameWriteTool.writeInt(buff, object.getButout_price());
		GameWriteTool.writeByte(buff, object.getSell_buy_type());
		
		GameWriteTool.writeString(buff, object.getAppointee_name());
		GameWriteTool.writeString(buff, object.getAppointee_gid());
		GameWriteTool.writeString(buff, object.getPara());
		
	}

	@Override
	public int cmd() {
		return 000;
	}

}
