package com.fengshen.server.data.write;

import io.netty.buffer.ByteBuf;

import org.springframework.stereotype.Service;

import com.fengshen.db.domain.ChargePoint;
import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.data.vo.Vo_53477_0;
import com.fengshen.server.netty.BaseWrite;

@Service
public class M53447_0 extends BaseWrite {
	@Override
	protected void writeO(ByteBuf buff, Object object) {
		Vo_53477_0 vo = (Vo_53477_0) object;

		GameWriteTool.writeInt(buff, vo.startTime);
		GameWriteTool.writeInt(buff, vo.endTime);
		GameWriteTool.writeInt(buff, vo.deadline);
		int point = vo.totalPoint > 60000 ? 60000 : vo.totalPoint;
		GameWriteTool.writeShort(buff, point);
		GameWriteTool.writeShort(buff, vo.totalPoint);
		GameWriteTool.writeByte(buff, vo.items.size());
		for (ChargePoint item : vo.items) {
			GameWriteTool.writeByte(buff, item.getNo());
			GameWriteTool.writeString(buff, item.getAwardstr());
			GameWriteTool.writeShort(buff, item.getPoint());
			GameWriteTool.writeShort(buff, item.getLeftNum() < 0 ? 0 : item.getLeftNum());
		}
	}

	@Override
	public int cmd() {
		return 53447;
	}
}
