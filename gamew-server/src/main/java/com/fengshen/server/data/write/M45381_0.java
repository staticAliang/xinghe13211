package com.fengshen.server.data.write;

import org.springframework.stereotype.Service;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.data.vo.luck.Vo_NEW_LOTTERY_INFO;
import com.fengshen.server.data.vo.luck.Vo_NEW_LOTTERY_INFO.Item;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

@Service
public class M45381_0 extends BaseWrite<Vo_NEW_LOTTERY_INFO> {
	@Override
	protected void writeO(ByteBuf buff, Vo_NEW_LOTTERY_INFO object) {
		GameWriteTool.writeInt(buff, object.getStart_time());
		GameWriteTool.writeInt(buff, object.getEnd_time());
		GameWriteTool.writeByte(buff, object.getItems().size());
		
		for(Item item:object.getItems()) {
			GameWriteTool.writeByte(buff, item.getNo());
			GameWriteTool.writeString(buff, item.getName());
			GameWriteTool.writeString(buff, item.getDesc());
			GameWriteTool.writeByte(buff, item.getLevel());
		}
		
	}

	@Override
	public int cmd() {
		return 45381;
	}
}
