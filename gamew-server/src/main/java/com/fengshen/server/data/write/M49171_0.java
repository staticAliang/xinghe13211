package com.fengshen.server.data.write;

import org.springframework.stereotype.*;

import com.fengshen.server.data.*;
import com.fengshen.server.data.vo.*;
import com.fengshen.server.netty.*;

import io.netty.buffer.*;
import java.util.*;

@Service
public class M49171_0 extends BaseWrite {
	@Override
	protected void writeO(ByteBuf writeBuf, Object object) {
		List<Vo_49171_0> object2 = (List<Vo_49171_0>) object;
		GameWriteTool.writeByte(writeBuf, object2.size());
		for (int i = 0; i < object2.size(); ++i) {
			GameWriteTool.writeByte(writeBuf, object2.get(i).isGot);
			GameWriteTool.writeShort(writeBuf, object2.get(i).limitLevel);
			GameWriteTool.writeByte(writeBuf, object2.get(i).vo491710s.size());
			for (int j = 0; j < object2.get(i).vo491710s.size(); ++j) {
				GameWriteTool.writeString(writeBuf, object2.get(i).vo491710s.get(j).name);
				GameWriteTool.writeInt(writeBuf, object2.get(i).vo491710s.get(j).number);
				GameWriteTool.writeInt(writeBuf, object2.get(i).vo491710s.get(j).level);
			}
		}
	}

	@Override
	public int cmd() {
		return 49171;
	}
}
