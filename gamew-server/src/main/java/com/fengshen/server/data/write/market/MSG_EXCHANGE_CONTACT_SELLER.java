package com.fengshen.server.data.write.market;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.data.vo.market.Vo_EXCHANGE_CONTACT_SELLER;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

/**
 * 通知客户端连续交易系统卖家结果
 * 
 *
 */
public class MSG_EXCHANGE_CONTACT_SELLER extends BaseWrite<Vo_EXCHANGE_CONTACT_SELLER>{

	@Override
	protected void writeO(ByteBuf buff, Vo_EXCHANGE_CONTACT_SELLER object) {
		GameWriteTool.writeString(buff, object.getType());
		GameWriteTool.writeString(buff, object.getGoodGid());
		GameWriteTool.writeString(buff, object.getPara());
		GameWriteTool.writeString(buff, object.getGid());
		GameWriteTool.writeString(buff, object.getName());
		GameWriteTool.writeInt(buff, object.getLevel());
		GameWriteTool.writeInt(buff, object.getIcon());
		GameWriteTool.writeByte(buff, object.getIsFriend());
		GameWriteTool.writeByte(buff, object.getIsOnline());
		GameWriteTool.writeString(buff, object.getGoodsName());
	}

	@Override
	public int cmd() {
		return 0x80BD;
	}

}
