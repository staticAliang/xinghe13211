package com.fengshen.server.data.write.zhenbao;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.data.vo.zhenbao.Vo_GOLD_STALL_MINE.Vo_GOLD_STALL_MINE_Items;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

/**
 * 刷新某个商品
 * 
 *
 */
public class MSG_GOLD_STALL_UPDATE_GOODS_INFO extends BaseWrite<Vo_GOLD_STALL_MINE_Items>{

	@Override
	protected void writeO(ByteBuf buff, Vo_GOLD_STALL_MINE_Items object) {
		
		GameWriteTool.writeString(buff, object.getGoodsId());
		GameWriteTool.writeShort(buff, object.getStatus());
		GameWriteTool.writeInt(buff, object.getStartTime());
		GameWriteTool.writeInt(buff, object.getEndTime());
	}

	@Override
	public int cmd() {
		return 0x8105;
	}

}
