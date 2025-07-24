package com.fengshen.server.data.write;

import org.springframework.stereotype.*;

import com.fengshen.server.data.*;
import com.fengshen.server.data.vo.*;
import com.fengshen.server.netty.*;

import io.netty.buffer.*;

import java.util.List;

@Service
public class M32825_0 extends BaseWrite<List<Vo_32825_0>> {
	@Override
	protected void writeO(final ByteBuf writeBuf, List<Vo_32825_0> object) {
		
		GameWriteTool.writeShort(writeBuf, object.size());
		for(Vo_32825_0 v:object) {
			GameWriteTool.writeString(writeBuf, v.name);
			GameWriteTool.writeInt(writeBuf, v.startTime);
			GameWriteTool.writeInt(writeBuf, v.endTime);
		}
	}

	@Override
	public int cmd() {
		return 32825;
	}
}
